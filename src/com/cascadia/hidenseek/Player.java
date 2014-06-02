package com.cascadia.hidenseek;

public class Player {

	public enum Role {
		Hider,
		Seeker,
		Supervisor
	}
	
	public Player(int id) {
		playerId = id;
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
	
	private String name;
	private Role role;
	private int playerId;

}
