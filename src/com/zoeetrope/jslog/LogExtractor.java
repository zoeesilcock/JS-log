package com.zoeetrope.jslog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogExtractor implements Runnable {
	
	private NotificationService service;
//	private ArrayList<JSLogItem> items = new ArrayList<JSLogItem>();
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
//            		this.items.add(item);
	            	service.displayItem(item);
	        		
	        		this.id++;
        		}
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
