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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogExtractor implements Runnable {
	
	private NotificationService service;
	private int id = 0;
	
	public LogExtractor(NotificationService service) {
		this.service = service;
		
		try {
			// Clear the logcat so we don't display any old entries.
			Runtime.getRuntime().exec("logcat -c");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		extractFromLog();
	}
	
	private void extractFromLog() {
		String line = "";

		try {
			// Exctract any new entries from the logcat.
			Process process = Runtime.getRuntime().exec("logcat -d browser:V *:S");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			// Clear the extracted entries from the list.
			Runtime.getRuntime().exec("logcat -c");
			
            while ((line = bufferedReader.readLine()) != null){
            	// This regex splits the line up into it's component parts.
            	Pattern pattern = Pattern.compile("./browser \\(.*\\): Console: (.*) (http.*):(.*)");
            	Matcher matcher = pattern.matcher(line);
            	int icon = R.drawable.info;
            	
            	if(line.charAt(0) != '-' && matcher.find()) {
            		// Determine which icon to display with the notification.
            		if(line.charAt(0) == 'E') {
	        			icon = R.drawable.error;
	        		} else if(line.charAt(0) == 'W') {
	        			icon = R.drawable.warning;
	        		}
            		
            		// Build the JSLogItem with the data extracted from the line.
            		JSLogItem item = new JSLogItem(this.id, icon, matcher.group(1), 
            				"Line: " + matcher.group(3) + " of " + matcher.group(2));
	            	service.displayItem(item);
	        		
	        		this.id++;
        		}
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
