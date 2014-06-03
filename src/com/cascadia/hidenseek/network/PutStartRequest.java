package com.cascadia.hidenseek.network;

import com.cascadia.hidenseek.Match;
import com.cascadia.hidenseek.network.NetworkBase.RequestType;

public abstract class PutStartRequest extends NetworkRequest {

	public void DoRequest(Match toStart) {
		m = toStart;
		Request r = new Request();
		r.url = baseUrl + "matches/" + toStart.GetId() + "/start/";
		r.type = RequestType.PUT;
		r.jsonArgs = m.ToJSONPost();
		doRequest(r);
	}
	
	protected abstract void onComplete(Match match);
	
	@Override
	protected final void processPostExecute(String s) {
		m.StartMatch();
		onComplete(m);
	}
	
	private Match m;

}
