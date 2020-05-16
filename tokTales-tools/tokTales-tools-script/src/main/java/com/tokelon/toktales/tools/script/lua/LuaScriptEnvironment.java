package com.tokelon.toktales.tools.script.lua;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tokelon.toktales.tools.script.IExtendableResourceFinder;
import com.tokelon.toktales.tools.script.IScript;
import com.tokelon.toktales.tools.script.IScriptEnvironment;
import com.tokelon.toktales.tools.script.IScriptModule;
import com.tokelon.toktales.tools.script.ScriptErrorException;

public class LuaScriptEnvironment implements IScriptEnvironment {
	// TODO: Expose debug functionality to outside or disable it


	private final LuaScriptState scriptState;

	private final Logger logger;

	public LuaScriptEnvironment() {
		this(LoggerFactory.getILoggerFactory());
	}

	public LuaScriptEnvironment(ILoggerFactory loggerFactory) {
		this.logger = loggerFactory.getLogger(getClass().getName());

		scriptState = new LuaScriptState();


		/* Debug stuff */
		//mScriptState.getGlobals().debuglib.traceback(level);
		//mScriptState.getGlobals().get("debug").get("getlocal").call(LuaValue.valueOf(1), LuaValue.valueOf(1));

		//mScriptState.getGlobals().get("debug").set("debug", new DebugDebug());
		scriptState.getGlobals().get("debug").set("debugEnable", new DebugDebugEnable());
		scriptState.getGlobals().get("debug").set("debugDisable", new DebugDebugDisable());
	}


	protected synchronized LuaScriptState getScriptState() {
		return scriptState;
	}


	@Override
	public IExtendableResourceFinder getResourceFinder() {
		return scriptState.getResourceFinder();
	}


	@Override
	public synchronized LuaScript loadScript(InputStream source, String sourcename) throws ScriptErrorException {
		try {
			LuaValue luaValue = scriptState.load(source, sourcename);

			LuaScript luaScript = new LuaScript(luaValue);

			return luaScript;
		}
		catch (LuaError le) {
			throw new ScriptErrorException(le);
		}

	}


	@Override
	public synchronized LuaScript loadScript(String scriptCode) throws ScriptErrorException {
		try {
			LuaValue luaValue = scriptState.load(scriptCode);

			LuaScript luaScript = new LuaScript(luaValue);

			return luaScript;
		}
		catch (LuaError le) {
			throw new ScriptErrorException(le);
		}

	}


	@Override
	public synchronized void runScript(IScript script) throws ScriptErrorException {
		if(!(script instanceof LuaScript)) {
			throw new ScriptErrorException("Script type is not supported");
		}
		LuaScript luaScript = (LuaScript) script;

		luaScript.runScript();
	}





	@Override
	public IScriptModule loadModule(InputStream source, String sourcename) throws ScriptErrorException {
		LuaScript script = loadScript(source, sourcename);

		return moduleFromScript(script);
	}


	@Override
	public IScriptModule loadModule(String scriptCode) throws ScriptErrorException {
		LuaScript script = loadScript(scriptCode);

		return moduleFromScript(script);
	}


	@Override
	public IScriptModule moduleFromScript(IScript script) throws ScriptErrorException {
		if(!(script instanceof LuaScript)) {
			throw new ScriptErrorException("Script type is not supported");
		}
		LuaScript luaScript = (LuaScript) script;

		LuaValue scriptValue = luaScript.getScriptValue();



		LuaValue moduleValue;
		try {
			// Get the module value
			moduleValue = scriptValue.call();
		}
		catch(LuaError le) {
			throw new ScriptErrorException(le);
		}


		// Check the module value
		if(moduleValue.isnil()) {	// If the script did not return a module (table)
			throw new ScriptErrorException("Script did not return a module");
		}
		else if(!moduleValue.istable()) {	// If the script did not return a table
			throw new ScriptErrorException("Script returned what is not a module");
		}


		/* Debug
		if(!mScriptState.getGlobals().get(functionName).isnil() || !mScriptState.getGlobals().get(functionName + "()").isnil()) {
			throw new ScriptErrorException("SCRIPT Leaked to Globals!!!");
		}
		*/



		// Return the module
		LuaScriptModule luaModule = new LuaScriptModule(moduleValue, this);	// Assigns this environment to the module

		return luaModule;
	}


	@Override
	public void setOutputStreams(PrintStream outStream, PrintStream errStream) {
		Globals globals = getScriptState().getGlobals();

		globals.STDOUT = outStream;
		globals.STDERR = errStream;
	}



	// This would toggle debugging when calling debug()
	private final class DebugDebug extends ZeroArgFunction {

		private final LuaFunction hookFunc = new DebugHook();
		private boolean hookSet = false;

		@Override public LuaValue call() {
			if(hookSet) {
				scriptState.getGlobals().get("debug").get("sethook").call();
			}
			else {
				scriptState.getGlobals().get("debug").get("sethook").call(hookFunc, LuaValue.valueOf("l"));
			}
			hookSet = !hookSet;

			LuaThread runningThread = scriptState.getGlobals().running;
			Object callstack = runningThread.callstack;
			return NONE;
		}
	}



	private final class DebugDebugEnable extends ZeroArgFunction {
		private final LuaFunction hookFunc = new DebugHook();

		@Override
		public LuaValue call() {
			scriptState.getGlobals().get("debug").get("sethook").call(hookFunc, LuaValue.valueOf("l"));
			return null;
		}
	}

	private final class DebugDebugDisable extends ZeroArgFunction {
		@Override
		public LuaValue call() {
			scriptState.getGlobals().get("debug").get("sethook").call();
			return null;
		}
	}


	private final class DebugHook extends TwoArgFunction {

		private Map<String, LuaValue> returns = new HashMap<String, LuaValue>();

		@Override
		public LuaValue call(LuaValue arg1, LuaValue arg2) {
			// arg1 is the event
			String event = arg1.tojstring();
			if(event.equals("line")) {
				String lineNum = arg2.tojstring();
				logger.debug("Line: {}", lineNum);

				LuaThread runningThread = scriptState.getGlobals().running;
				Object callstack = runningThread.callstack;


				returns.clear();
				Varargs returnValues;
				int stackCounter = 1;
				do {
					returnValues = scriptState.getGlobals().get("debug").get("getlocal").invoke(LuaValue.valueOf(1), LuaValue.valueOf(stackCounter));

					if(!returnValues.isnil(1)) {
						if(returnValues.arg1().isfunction()) {
							returns.put(returnValues.arg1().tojstring(), null);
							logger.debug("{}: {}", stackCounter, returnValues.arg1());
						}
						else {
							returns.put(returnValues.arg(1).tojstring(), returnValues.arg(2));
							logger.debug("{}: {} = {}", stackCounter, returnValues.arg(1), returnValues.arg(2));
						}
					}


					stackCounter++;
				} while(!returnValues.isnil(1));


				lineNum.toString();		// Breakpoint here to iterate over lines
			}

			return null;
		}
	}

}
