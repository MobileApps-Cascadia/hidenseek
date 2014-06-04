package com.cascadia.hidenseek;

import com.cascadia.hidenseek.Match.MatchType;

import android.content.Intent;

public class HostLoginManager {

	public HostLoginManager() {	} 
	
	public static Match ValidateLogin(String matchName, String password, int matchType ) {
		if(matchType != 1) {
			return null;
		}
		
		m = new Match(matchName, password, MatchType.Sandbox);
		return m;
	}
	
	private static Match m;
	

}
