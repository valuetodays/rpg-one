package com.billy.scriptParser.cmd;

public class JettyStarterTest {

	public static void main(String[] args) {
		String webapp = "script-parser/src/main/webapp";
		new JettyServerStarter(webapp, 8088, "/").start();
	}
}
