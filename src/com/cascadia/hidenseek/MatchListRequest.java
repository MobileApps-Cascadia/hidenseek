package com.cascadia.hidenseek;

import java.io.IOException;
import java.util.List;

import com.cascadia.hidenseek.NetworkBase.RequestType;

import android.os.AsyncTask;

public abstract class MatchListRequest extends NetworkRequest {
	
	public void DoRequest() {
		Request r = new Request();
		r.url = baseUrl + "matches/";
		r.type = RequestType.GET;
		doRequest(r);
	}
	
	protected abstract void onComplete(List<Match> matches);
	
	@Override
	protected final void processPostExecute(String s) {
		onComplete(Match.ParseToList(s));
	}

}
