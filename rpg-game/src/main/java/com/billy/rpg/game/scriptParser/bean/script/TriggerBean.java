package com.billy.rpg.game.scriptParser.bean.script;

public class TriggerBean {
	private String position;
	private String labelTitle;
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getLabelTitle() {
		return labelTitle;
	}
	public void setLabelTitle(String labelTitle) {
		this.labelTitle = labelTitle;
	}
	@Override
	public String toString() {
		return "Trigger [position=" + position + ", labelTitle=" + labelTitle
				+ "]";
	}
	
	
	
}
