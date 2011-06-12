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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity  {
	
	public static Intent serviceIntent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.preferences);
        
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean enableNotifications = settings.getBoolean("notification_preference", false);
        
        if(enableNotifications) {
	       	Settings.serviceIntent = new Intent(Settings.this, NotificationService.class);
	       	startService(Settings.serviceIntent);
        }
        
        CheckBoxPreference notificationPreference = (CheckBoxPreference) findPreference("notification_preference");
        OnPreferenceChangeListener preferenceChange = new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if((Boolean) newValue) {
					Settings.serviceIntent = new Intent(Settings.this, NotificationService.class);
		           	startService(Settings.serviceIntent);
				} else {
					stopService(Settings.serviceIntent);
				}
				
				return true;
			}
		};
		
        notificationPreference.setOnPreferenceChangeListener(preferenceChange);
    }
}