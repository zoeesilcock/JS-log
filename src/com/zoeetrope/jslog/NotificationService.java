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

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {
	
	ScheduledThreadPoolExecutor schedule;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Start the schedule and run it once every second.
		this.schedule = new ScheduledThreadPoolExecutor(5);
		this.schedule.scheduleAtFixedRate(new LogExtractor(this), 0, 1, TimeUnit.SECONDS);
		
		return START_STICKY;
	}
	
	public void displayItem(JSLogItem item) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		
		long now = System.currentTimeMillis();
		Context context = getApplicationContext();
		
		// Prepare the details view.
		Intent notificationIntent = new Intent(this, Details.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		notificationIntent.setAction("JSLogItem" + System.currentTimeMillis());
		notificationIntent.putExtra("jslog.itemId", item.getId());
		notificationIntent.putExtra("jslog.itemMessage", item.getMessage());
		notificationIntent.putExtra("jslog.itemLocation", item.getLocation());
		notificationIntent.putExtra("jslog.itemIcon", item.getIcon());
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, 
				notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		Notification notification = new Notification(item.getIcon(), item.getMessage(), now);
		notification.setLatestEventInfo(context, item.getMessage(), item.getLocation(), contentIntent);
		
		mNotificationManager.notify(item.getId(), notification);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		this.schedule.shutdownNow();
	}

}
