/**
 * Copyright 2011 Zoee Silcock (zoeetrope.com)
 * 
 * This file is part of JSLog.
 * 
 * JSLog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JSLog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JSLog.  If not, see <http://www.gnu.org/licenses/>.
 */
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
