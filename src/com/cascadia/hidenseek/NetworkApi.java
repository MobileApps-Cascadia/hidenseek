package com.cascadia.hidenseek;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.json.parsers.JsonParserFactory;

public class NetworkApi {
	
	public NetworkApi() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Match> getMatches() throws IOException {
		String response = nbase.Get(baseUrl + "matches");
		List<Match> toReturn = new ArrayList<Match>();
		return toReturn;
	}

	private final String baseUrl = "http://216.186.69.45/services/hidenseek/";
	private NetworkBase nbase = new NetworkBase();
}
