package com.zoeetrope.jslog;

public class JSLogItem {

	private int id;
	private int icon;
	private String title;
	private String content;
	private boolean displayed = false;
	
	public JSLogItem(int id, int icon, String title, String content) {
		this.id = id;
		this.icon = icon;
		this.title = title;
		this.content = content;
	}
	
	public int getId() {
		return this.id;
	}

	public int getIcon() {
		return this.icon;
	}

	public String getTitle() {
		return this.title;
	}

	public String getContent() {
		return this.content;
	}
	
	public boolean isDisplayed() {
		return this.displayed;
	}
	
	public void setDisplayed() {
		this.displayed = true;
	}

}
