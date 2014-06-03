package com.cascadia.hidenseek;

import java.util.List;

import com.cascadia.hidenseek.NetworkBase.RequestType;


public abstract class GetMatchListRequest extends NetworkRequest {
	
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
