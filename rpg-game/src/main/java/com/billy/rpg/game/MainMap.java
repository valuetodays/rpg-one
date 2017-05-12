package com.billy.rpg.game;

import com.billyrupeng.WalkUtil;


public class MainMap {
	private int height = 5;
	private int width = 5;
	private int posX = 6;
	private int posY = 6;
	private int nextPosX;
	private int nextPosY;
	private int curFrame;
	private int direction;
	public static final int DIRECTION_DOWN = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_RIGHT = 2;
	public static final int DIRECTION_UP = 3;
	
	


	public void setHeight(int height) {
		this.height = height;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getPosX() {
		return posX;
	}
	public int getNextPosX() {
        return nextPosX;
    }
    public int getNextPosY() {
        return nextPosY;
    }
    public void initPos(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}
    
    private void increaseCurFrame() {
        curFrame++;
        if (curFrame == 3) {
            curFrame = 0;
        }
    }
    
	public int getCurFrame() {
		return curFrame;
	}
	public void increaseX(){
		if (direction == DIRECTION_RIGHT) {
			increaseCurFrame();
		}
		direction = DIRECTION_RIGHT;
	    nextPosX = posX+1;
		if ( (posX < width-1) &&  WalkUtil.isWalkable(posX+1, posY)) {
			posX++;
		}
	}
	public void decreaseX(){
		if (direction == DIRECTION_LEFT) {
			increaseCurFrame();
		}
		direction = DIRECTION_LEFT;
	    nextPosX = posX-1;
		if (posX > 0 && WalkUtil.isWalkable(posX-1, posY)) {
			posX--;
		}
	}

	public int getPosY() {
		return posY;
	}
	public void increaseY(){
		if (direction == DIRECTION_DOWN) {
			increaseCurFrame();
		}
		direction = DIRECTION_DOWN;
	    nextPosY = posY+1;
		if(posY < height-1 && WalkUtil.isWalkable(posX, posY+1)){
			posY++;
		}
	}
	public void decreaseY(){
		if (direction == DIRECTION_UP) {
			increaseCurFrame();
		}
		direction = DIRECTION_UP;
        nextPosY = posY-1;
		if(posY > 0  && WalkUtil.isWalkable(posX, posY-1)){
			posY--;
		}
	}

	@Override
	public String toString() {
		return "[height=" + height + ", width=" + width + ", posX=" + posX + ", posY=" + posY + ", nextPosX="
				+ nextPosX + ", nextPosY=" + nextPosY + ", curFrame=" + curFrame + ", direction=" + direction + "]";
	}
	public int getDirection() {
		return direction;
	}
	public boolean isX() {
		return (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT);
	}
	public boolean isY() {
		return (direction == DIRECTION_UP || direction == DIRECTION_DOWN);
	}

	
}
