package com.rupeng.game;

import java.io.File;
import java.io.FilenameFilter;

class EndsWithFilenameFilter implements FilenameFilter {
	private String endsWith;
	
	public EndsWithFilenameFilter(String endsWith) {
		this.endsWith = endsWith;
	}

	public boolean accept(File dir, String name) {
		return name.endsWith(endsWith);
	}

}
