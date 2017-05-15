package com.billy.rpg.game.scriptParser.bean.script;

public class TalkBean {
	private int num;
	private String labelTitle;
	

	public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getLabelTitle() {
		return labelTitle;
	}
	public void setLabelTitle(String labelTitle) {
		this.labelTitle = labelTitle;
	}
	
    @Override
    public String toString() {
        return "TalkBean [num=" + num + ", labelTitle=" + labelTitle + "]";
    }

	
	
}
