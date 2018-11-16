package billy.rpg.game.core.script;

public class TriggerBean {
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
