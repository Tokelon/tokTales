package com.tokelon.toktales.extensions.core.values;

import com.tokelon.toktales.core.values.GameStateValues;

public class GameStateExtensionsValues extends GameStateValues {

	protected GameStateExtensionsValues() {
		throw new UnsupportedOperationException();
	}
	
	
	
	public static final String STATE_LOCAL_MAP = "tokelon_extensions_states_state_local_map";
	public static final String STATE_CONSOLE = "tokelon_extensions_states_state_console";
	
	
	private static final String STATE_INVENTORY = "tokelon_extensions_states_state_inventory";
	private static final String STATE_GLOBAL_MAP = "tokelon_extensions_states_state_global_map";
	private static final String STATE_MAIN_MENU = "tokelon_extensions_states_state_main_menu";
	private static final String STATE_CUTSCENE = "tokelon_extensions_states_state_cutscene";
	
	

	@SuppressWarnings("unused") // TODO: Use?
	private enum State {
		
		LOCAL_MAP(STATE_LOCAL_MAP),
		INVENTORY(STATE_INVENTORY),
		GLOBAL_MAP(STATE_GLOBAL_MAP),
		MAIN_MENU(STATE_MAIN_MENU),
		CUTSCENE(STATE_CUTSCENE),
		;
		
		
		private final String stateName;
		
		private State(String name) {
			stateName = name;
		}
		
		public String getName() {
			return stateName;
		}
	}
	
}
