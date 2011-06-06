package com.zoeetrope.jslog;

public class JSLogItem {

	private int id;
	private int icon;
	private String message;
	private String location;
	private boolean displayed = false;
	
	public JSLogItem(int id, int icon, String message, String location) {
		this.id = id;
		this.icon = icon;
		this.message = message;
		this.location = location;
	}
	
	public int getId() {
		return this.id;
	}

	public int getIcon() {
		return this.icon;
	}

	public String getMessage() {
		return this.message;
	}

	public String getLocation() {
		return this.location;
	}
	
	public boolean isDisplayed() {
		return this.displayed;
	}
	
	public void setDisplayed() {
		this.displayed = true;
	}

}
