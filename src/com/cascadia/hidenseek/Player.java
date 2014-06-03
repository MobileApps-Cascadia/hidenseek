package com.cascadia.hidenseek;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cascadia.hidenseek.Match.Status;

public class Player {

	public enum Role {
		Hider,
		Seeker,
		Supervisor;
		
		public static Role Parse(String s) {
			if(s.equalsIgnoreCase("hider")){
				return Hider;
			} else if (s.equalsIgnoreCase("seeker")) {
				return Seeker;
			} else return Supervisor;
		}
		
		public String GetApiString() {
			switch(this) {
			case Hider:
				return "hider";
			case Seeker:
				return "seeker";
			default:
				return "supervisor";
			}
		}
	}
	
	public Player(String name, Match match) {
		this.name = name;
		this.associatedMatch = match;
	}

	public static List<Player> ParseToList(String jsonStr, Match associatedMatch)
			throws JSONException {
		List<Player> toReturn = new ArrayList<Player>();
		JSONArray jArray = new JSONObject(jsonStr).getJSONArray("matches");
		for(int i = 0; i < jArray.length(); i++) {
			toReturn.add(parse(jArray.getJSONObject(i), associatedMatch));
		}
		return toReturn;
	}
	
	public String ToJSONPost(String password) throws JSONException {
		JSONObject jObject = new JSONObject();
		jObject.put("name", name);
		jObject.put("matchId", associatedMatch.GetId());
		jObject.put("password", password);
		return jObject.toString();
	}
	
	public boolean ProcessPostResponse(String jsonStr) {
		try {
			playerId = new JSONObject(jsonStr).getInt("id");
			if(playerId == 0) {
				return false;
			}
			else return true;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private static Player parse(JSONObject jObject, Match associatedMatch)
			throws JSONException {
		Player toReturn = new Player(jObject.getString("name"), associatedMatch);
		toReturn.playerId = jObject.getInt("id");
		toReturn.role = Role.Parse(jObject.getString("role"));
		//TODO: gps etc.
		return toReturn;
	}
	
	public String GetName() {
		return name;
	}
	
	public Role GetRole() {
		return role;
	}
	
	public void SetRole(Role r) {
		role = r;
	}
	
	public int GetId() {
		return playerId;
	}
	
	private Match associatedMatch;
	private String name;
	private Role role;
	private int playerId = -1;
}
