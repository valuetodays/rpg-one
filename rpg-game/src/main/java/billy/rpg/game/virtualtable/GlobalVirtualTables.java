package billy.rpg.game.virtualtable;

import billy.rpg.game.script.LabelBean;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class GlobalVirtualTables {
	private GlobalVirtualTables(){}
	
	/////////////////////////////////////////
	private static Map<String, Boolean> globalVariables = new HashMap<>();
	
	public static Boolean getGlobalVariable(String variable){
		Boolean f = globalVariables.get(variable);
		return f != null && f.booleanValue();
	}
	public static Boolean containsVariable(String variable){
		return getGlobalVariable(variable);
	}
	public static Boolean putGlobalVariable(String variable){
		return globalVariables.put(variable, true);
	}
	public static void printGlobalVariablesListString(){
		System.out.println(JSON.toJSONString(globalVariables));
	}
	
	/////////////////////////////////////////
	private static Map<String, LabelBean> globalLabels 	= new HashMap<>();
	
	public static void addLabel(LabelBean func){
		globalLabels.put(func.getTitle(), func);
	}
	public static Boolean containsLabel(String funcTitle){
		if(null != globalLabels.get(funcTitle)){
			return true;
		}
		else{
			return false;
		}
	}
	public static LabelBean getLabel(String funcTitle){
		return globalLabels.get(funcTitle + ":");
	}
	public static void toLabelListString(){
		System.out.println(JSON.toJSONString(globalLabels));
	}
	
	
}
