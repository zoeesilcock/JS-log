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