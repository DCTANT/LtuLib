package com.tstdct.lib.crash;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dechert on 2018-02-27.
 * Company: www.chisalsoft.co
 */

public class M_CrashModel {
	private Map<String,String> essentialInfo =new HashMap<>();
	private Map<String,String> otherInfo =new HashMap<>();
	private String crashLines;

	public Map<String, String> getEssentialInfo() {
		return essentialInfo;
	}

	public void setEssentialInfo(Map<String, String> essentialInfo) {
		this.essentialInfo = essentialInfo;
	}

	public Map<String, String> getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(Map<String, String> otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getCrashLines() {
		return crashLines;
	}

	public void setCrashLines(String crashLines) {
		this.crashLines = crashLines;
	}
}
