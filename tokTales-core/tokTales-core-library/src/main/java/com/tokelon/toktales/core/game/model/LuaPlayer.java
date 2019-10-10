package com.tokelon.toktales.core.game.model;

import com.tokelon.toktales.tools.script.lua.ILuaObject;

class LuaPlayer implements ILuaObject {

	// TODO: Use or remove
	
	// TODO: Synchronization


	private int x = 0;
	private int y = 0;
	private String direction = "down";
	private String name = "";

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
	
	public void setDirection(String value) {
		this.direction = value;
	}
	public String getDirection() {
		return direction;
	}

	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	public int getY() {
		return y;
	}
	
	
}
