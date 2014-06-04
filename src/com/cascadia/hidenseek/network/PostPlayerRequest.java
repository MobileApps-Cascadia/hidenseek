package com.cascadia.hidenseek.network;

import com.cascadia.hidenseek.Match;
import com.cascadia.hidenseek.Player;
import com.cascadia.hidenseek.network.NetworkBase.RequestType;

public abstract class PostPlayerRequest extends NetworkRequest {

	public void DoRequest(Player toPost, Match associatedMatch, String password) {
		p = toPost;
		m = associatedMatch;
		Request r = new Request();
		r.url = baseUrl + "matches/" + m.GetId() + "/players/";
		r.type = RequestType.POST;
		r.jsonArgs = p.ToJSONPost(password);
		doRequest(r);
	}
	
	protected abstract void onComplete(Player player);
	
	@Override
	protected final void processPostExecute(String s) {
		p.ProcessPostResponse(s, m);
		onComplete(p);
	}
	
	private Player p;
	private Match m;

}
