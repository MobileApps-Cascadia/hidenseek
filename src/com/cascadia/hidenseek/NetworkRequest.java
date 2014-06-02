package com.cascadia.hidenseek;


public abstract class NetworkRequest {

	public NetworkRequest() {
		nRequest = new NetworkTask() {
			@Override
			protected void onPostExecute(String result) {
				processPostExecute(result);
			}
		};
	}
	
	protected final void doRequest(Request r) {
		nRequest.execute(r);
	}
	
	protected abstract void processPostExecute(String jsonResponse);
	
	private NetworkTask nRequest;
	protected final String baseUrl = "http://216.186.69.45/services/hidenseek/";

}
