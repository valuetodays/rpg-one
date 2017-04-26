package com.billy.scriptParser.bean.script;

import com.billy.scriptParser.cmd.CmdBase;

import java.util.List;


public class LabelBean {
	private String scriptNo; 
	private String title;
	private List<CmdBase> cmds;
	
	
	public String getScriptNo() {
		return scriptNo;
	}
	public void setScriptNo(String scriptNo) {
		this.scriptNo = scriptNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<CmdBase> getCmds() {
		return cmds;
	}
	public void setCmds(List<CmdBase> cmds) {
		this.cmds = cmds;
	}
	@Override
	public String toString() {
		return "Label [scriptNo=" + scriptNo + ", title=" + title
				+ ", cmds=" + cmds + "]";
	}
	

}
