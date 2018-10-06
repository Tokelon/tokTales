package com.tokelon.toktales.core.game.model.map;

import com.tokelon.toktales.core.util.IAttributed;

public interface IMapLayer extends IAttributed {

	
	public static final int ATTRIBUTED_PLAYER = 1;
	public static final int ATTRIBUTED_FRINGE = 2;
	public static final int ATTRIBUTED_INVISIBLE = 3;
	
	public static final int ATTRIBUTED_PLAYER_FRINGE = 4;
	public static final int ATTRIBUTED_PLAYER_UNFRINGE = 5;
	
	
	public String getName();

}
