package com.cascadia.hidenseek;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.json.config.handlers.ValidationConfigType;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class Match {

	public Match(int id) {
		matchId = id;
	}
	
	public List<Match> ParseToList(String JsonObj) {
		JsonParserFactory factory = JsonParserFactory.getInstance();
		JSONParser parser = factory.newJsonParser(ValidationConfigType.JSON);
		parser.setValidating(true);
		Map map = parser.parseJson(JsonObj);
		
		
		return null;
		
	}
	
	public int GetId() {
		return matchId;
	}

	private int matchId;
	public List<Player> players = new ArrayList<Player>();
}
