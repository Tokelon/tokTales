package com.tokelon.toktales.core.game.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.util.SynchronizedPool;
import com.tokelon.toktales.core.util.SynchronizedPool.PoolObjectFactory;

public class ActionScheduler implements IActionScheduler {
	// TODO: Important - Make a method for increasing (and resetting) the action counter
	// And avoid overflow


	private final ReentrantLock lock = new ReentrantLock();
	

	private final LinkedBlockingQueue<IActionTaker> actionQueue = new LinkedBlockingQueue<IActionTaker>();
	
	private final SynchronizedPool<ActionTakerMeta> metaPool = new SynchronizedPool<ActionTakerMeta>(new ActionTakerMetaFactory(), 5);
	
	private final Map<IActionTaker, ActionTakerMeta> metaMap = new HashMap<IActionTaker, ActionTakerMeta>();
	
		
	private long actionCounter = 0;
	
	private IActionTaker currentActionTaker;
	
	private Thread currentActionThread;
	
	
	private final ILogger logger;
	
	public ActionScheduler(ILogging logging) {
		this.logger = logging.getLogger(getClass());
	}
	

	@Override
	public void requestActionOrError(IActionTaker actionTaker) {
		if(!requestAction(actionTaker)) {
			throw new ActionRequestException("Failed to get action due to a timeout or cancel");
		}
	}

	@Override
	public boolean requestAction(IActionTaker actionTaker) {
		//Prog.gHub().gLog().d(TAG, "Request action: " +actionTaker);

		if(actionTaker.hasCancel()) {
			throw new IllegalArgumentException("Cannot request action for cancelled action taker");
		}



		/* LOCK
		 * 
		 */
		//Prog.gHub().gLog().d(TAG, "Before UPPER lock");
		lock.lock();
		//Prog.gHub().gLog().d(TAG, "Inside UPPER lock");

		try {
			if(Thread.currentThread().equals(currentActionThread)) {
				return true;
			}



			boolean gotAction = false;
			if(currentActionTaker == null && actionQueue.isEmpty()) {
				actionTaker.setAction(++actionCounter);
				gotAction = true;
				currentActionThread = Thread.currentThread();
				currentActionTaker = actionTaker;
			}
			else {
				actionQueue.add(actionTaker);

				ActionTakerMeta meta;
				if(metaMap.containsKey(actionTaker)) {
					meta = metaMap.get(actionTaker);
				}
				else {
					meta = metaPool.newObject();
					metaMap.put(actionTaker, meta);
				}
				meta.counter++;


				//Prog.gHub().gLog().d(TAG, "GONNA WAIT");
				boolean done = false;
				while(!done) {
					if(actionTaker.hasTimeout()) {
						try {
							meta.condition.await(actionTaker.getTimeout(), TimeUnit.MILLISECONDS);


							done = true;	// We are done whether it was a timeout or we got notified
							gotAction = actionTaker.hasAction();	// If it was a timeout this will be false, otherwise true
						} catch (InterruptedException e) {
							if(actionTaker.hasCancel()) {
								done = true;
								gotAction = false;
							}
							else {
								//Prog.getLog().w(TAG, "Waiting action taker was interrupted. Ignoring");
								logger.warn("WARNING: Waiting action taker was interrupted. Ignoring");
							}
						}

					}
					else {
						try {
							meta.condition.await();


							done = actionTaker.hasAction();		// This will wait until we actually get the action
							gotAction = true;
						} 
						catch (InterruptedException e) {
							if(actionTaker.hasCancel()) {
								done = true;
								gotAction = false;
							}
							else {
								//Prog.getLog().w(TAG, "Waiting action taker was interrupted. Ignoring");
								logger.warn("WARNING: Waiting action taker was interrupted. Ignoring");
							}
						}

					}
				}

				//Prog.gHub().gLog().d(TAG, "HAVE WAITED");
				meta.counter--;
				if(meta.counter == 0) {	// If no more actions are waiting on this action taker, remove it and free its meta 
					metaMap.remove(actionTaker);
					metaPool.free(meta);
				}

				if(gotAction) {
					currentActionThread = Thread.currentThread();
					currentActionTaker = actionTaker;
				}
			}

			return gotAction;
		}
		finally {
			/* UNLOCK
			 * 
			 */
			lock.unlock();
			//Prog.gHub().gLog().d(TAG, "After UPPER lock");
		}
	}

	
	
	@Override
	public void finishAction(IActionTaker actionTaker) {
		//Prog.gHub().gLog().d(TAG, "Finish action: " +actionTaker);
		
		//if(Thread.currentThread().equals(currentActionThread)) {		// Cannot do this! This will never be false
		
		
		
		/* LOCK
		 * 
		 */
		//Prog.gHub().gLog().d(TAG, "Before LOWER lock");
		lock.lock();			
		//Prog.gHub().gLog().d(TAG, "Inside LOWER lock");
		
		try {

			if(actionTaker != currentActionTaker) {
				// This happens when there is an action inside another action
				//Prog.gHub().gLog().w(TAG, "Finish action was called by unknown action taker. Skipping");

				return;
			}


			actionTaker.removeAction();
			currentActionTaker = null;
			currentActionThread = null;

			if(!actionQueue.isEmpty()) {

				IActionTaker nextActionTaker = actionQueue.remove();


				//currentActionTaker = nextActionTaker;		//Did this up
				nextActionTaker.setAction(++actionCounter);


				/* Signal ONE of the threads that are waiting on this action taker
				 */
				ActionTakerMeta meta = metaMap.get(nextActionTaker);
				meta.condition.signal();

			}
		}
		finally {
			/* UNLOCK
			 * 
			 */
			lock.unlock();
			//Prog.gHub().gLog().d(TAG, "After LOWER lock");
		}
	}

	
	private final class ActionTakerMeta {

		private Condition condition;
		private int counter;
		
		public ActionTakerMeta(Condition condition) {
			this.condition = condition;
		}
		
	}
	
	private final class ActionTakerMetaFactory implements PoolObjectFactory<ActionTakerMeta> {

		@Override
		public ActionTakerMeta createObject() {
			return new ActionTakerMeta(lock.newCondition());
		}
	}
	
}
