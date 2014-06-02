package com.cascadia.hidenseek;

import com.cascadia.hidenseek.NetworkBase.RequestType;

public abstract class PostMatchRequest extends NetworkRequest {

	public void DoRequest(Match toPost) {
		m = toPost;
		Request r = new Request();
		r.url = baseUrl + "matches/";
		r.type = RequestType.POST;
		r.jsonArgs = m.ToJSONPost();
		doRequest(r);
	}
	
	protected abstract void onComplete(Match match);
	
	@Override
	protected final void processPostExecute(String s) {
		m.ProcessPostResponse(s);
		onComplete(m);
	}
	
	private Match m;
}
