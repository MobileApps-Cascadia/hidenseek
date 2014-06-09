package com.cascadia.hidenseek.network;

import com.cascadia.hidenseek.Player;
import com.cascadia.hidenseek.network.NetworkBase.RequestType;

public abstract class PostPlayerRequest extends NetworkRequest {

	public void DoRequest(Player toPost, String password) {
		p = toPost;
		Request r = new Request();
		r.url = baseUrl + "matches/" + toPost.GetAssociatedMatch().GetId() + "/players/";
		r.type = RequestType.POST;
		r.jsonArgs = p.ToJSONPost(password);
		doRequest(r);
	}
	
	//To be overwritten
	protected void onComplete(Player player) { }
	
	@Override
	protected final void processPostExecute(String s) {
		p.ProcessPostResponse(s);
		onComplete(p);
	}
	
	private Player p;

}
