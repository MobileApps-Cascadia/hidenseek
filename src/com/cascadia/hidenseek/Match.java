package com.cascadia.hidenseek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Match {

	public enum Status {
		Active,
		Pending,
		Complete
	}
	
	public enum MatchType {
		HideNSeek,
		Sandbox;
		
		/**
		 * Returns Sandbox if string is equal to "sandbox" (ignoring case),
		 * else returns HideNSeek
		 */
		public static MatchType Parse(String s) {
			if(s.equalsIgnoreCase("sandbox")){
				return Sandbox;
			}
			else return HideNSeek;
		}
		
	}
	
	
	public Match(String name, String password, MatchType type) {
		this.name = name;
		this.password = password;
		this.type = type;
	}
	
	/**
	 * Parses the given JSON string
	 * @param jsonStr A string formatted in JSON as prescribed by the 
	 * Hide-n-Seek API for the /matches/ request
	 * @return A list of Matches. Returns null in case of parsing error.
	 */
	public static List<Match> ParseToList(String jsonStr) {
		List<Match> toReturn = new ArrayList<Match>();
		JSONArray jArray;
		
		try {
			jArray = new JSONObject(jsonStr).getJSONArray("matches");
			for(int i = 0; i < jArray.length(); i++) {
				toReturn.add(parse(jArray.getJSONObject(i)));
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return toReturn;
	}
	
	/**
	 * Parses the given JSON string
	 * @param jsonStr A string formatted in JSON as prescribed by the 
	 * Hide-n-Seek API for a /matches/match id request
	 * @return A Match. Returns null in case of parsing error.
	 */
	public static Match Parse(String jsonStr) {
		try {
			return parse(new JSONObject(jsonStr));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String ToJSONPost() {
		JSONObject jObject = new JSONObject();
		try {
			jObject.put("name", name);
			jObject.put("password", password);
			return jObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void ProcessPostResponse(String jsonStr) {
		try {
			matchId = new JSONObject(jsonStr).getInt("id");
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	private static Match parse(JSONObject jObject) throws JSONException 
	{
		Match toReturn = new Match(jObject.getString("name"),
					  jObject.getString("password"),
					  MatchType.Parse(jObject.getString("type")));
		toReturn.matchId = jObject.getInt("id");
		toReturn.countTime = jObject.getInt("countTime");
		toReturn.seekTime = jObject.getInt("seekTime");
		try {
		toReturn.startTime = dateTimeFormat.parse(jObject.getString("startTime"));
		} catch (ParseException e) {
			//Assume that the exception means there is no starTtime set.
		}
		return toReturn;
	}
	
	public int GetId() {
		return matchId;
	}
	public int GetCountTime() {
		return countTime;
	}
	public void SetCountTime(int time) {
		countTime = time;
	}
	public int GetseekTime() {
		return seekTime;
	}
	public void SetseekTime(int time) {
		seekTime = time;
	}
	public MatchType GetType() {
		return type;
	}
	public Status GetStatus() {
		return status;
	}
	private int matchId = -1; //Does not get set by constructor; set by 
	private String name;
	private String password;
	private int countTime;
	private int seekTime;
	private Status status;
	private MatchType type;
	private Date startTime;
	
	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
	
	public List<Player> players = new ArrayList<Player>();
}
