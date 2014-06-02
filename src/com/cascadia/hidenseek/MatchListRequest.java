package com.cascadia.hidenseek;

import java.io.IOException;
import java.util.List;

import android.os.AsyncTask;

public abstract class MatchListRequest extends NetworkRequest {
	
	public DoRequest() {
		
	}
	@Override
	protected void onPostExecute(String result) {
		//process
		//call OnComplete
	}
	
	public abstract void OnComplete(List<Match> matches);
	
	protected static NetworkBase nbase = new NetworkBase();


}
