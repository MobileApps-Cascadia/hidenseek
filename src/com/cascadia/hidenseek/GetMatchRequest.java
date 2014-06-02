package com.cascadia.hidenseek;

import com.cascadia.hidenseek.NetworkBase.RequestType;

public abstract class GetMatchRequest extends NetworkRequest {

	public void DoRequest(int id) {
		Request r = new Request();
		r.url = baseUrl + "matches/" + id;
		r.type = RequestType.GET;
		doRequest(r);
	}
	
	protected abstract void onComplete(Match matche);
	
	@Override
	protected final void processPostExecute(String s) {
		onComplete(Match.Parse(s));
	}

}
