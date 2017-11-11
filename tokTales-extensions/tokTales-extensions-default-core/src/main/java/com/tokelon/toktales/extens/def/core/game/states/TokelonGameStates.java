package com.tokelon.toktales.extens.def.core.game.states;

public final class TokelonGameStates {

	// TODO: Use this OR TokelonStates
	
	public static final String STATE_INITIAL = "tokelon_state_initial";
	public static final String STATE_CONSOLE = "tokelon_state_console";
	
	public static final String STATE_LOCAL_MAP = "tokelon_state_local_map";
	public static final String STATE_INVENTORY = "tokelon_state_inventory";
	public static final String STATE_GLOBAL_MAP = "tokelon_state_global_map";
	public static final String STATE_MAIN_MENU = "tokelon_state_main_menu";
	public static final String STATE_CUTSCENE = "tokelon_state_cutscene";
	
	
	enum State {
		
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
