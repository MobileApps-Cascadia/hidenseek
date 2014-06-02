package com.cascadia.hidenseek;

import com.cascadia.hidenseek.NetworkBase.RequestType;

public class Request {

	public RequestType type = RequestType.GET;
	public String url;
	public String jsonArgs;

}
