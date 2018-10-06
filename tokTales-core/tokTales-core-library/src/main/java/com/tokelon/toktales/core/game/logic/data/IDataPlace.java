package com.tokelon.toktales.core.game.logic.data;

public interface IDataPlace<T extends IDataRead, U extends IDataWrite> {

	
	/*
	 * Only use when you explicitly want to read or set certain data in the place.
	 */
	
	public T dataRead();
	
	public U dataWrite();
	
	
	

	// Just to remember how it works if you want to use the write interface with an extended implementation
	/*
	public interface MyDataWrite extends IBLKMap.DataWrite {
		
		public void setBlockAt(int posx, int posy, IBlock block);
	}
	
	private class MyDataWriteImpl implements MyDataWrite {
		public void setBlockAt(int posx, int posy, IBlock block) {
			if(posx < 0 || posx >= width || posy < 0 || posy >= height || block==null) {
				throw new IllegalArgumentException("Constrains: 0 <= posx < width, 0 <= posy < height, block != null");
			}
			
			mapData[posy][posx] = block;
		}

		@Override
		public IBlock editBlockAt(int posx, int posy) {
			return getBlockAt(posx, posy);	// OK??
		}
	}
	*/
	
}
