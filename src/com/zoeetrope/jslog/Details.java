package com.zoeetrope.jslog;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Details extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.details);
		
		Button closeButton = (Button) findViewById(R.id.close);
		Button removeButton = (Button) findViewById(R.id.remove);
		Button settingsButton = (Button) findViewById(R.id.settings);
		
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Details.this.finish();
			}
		});
		
		removeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle extras = Details.this.getIntent().getExtras();
				NotificationManager mNotificationManager = (NotificationManager) 
						getSystemService(Context.NOTIFICATION_SERVICE);
				
				mNotificationManager.cancel(extras.getInt("jslog.itemId"));
				
				Details.this.finish();
			}
		});
		
		settingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent settingsIntent = new Intent(Details.this.getApplicationContext(), Settings.class);
				
				Details.this.startActivity(settingsIntent);
			}
		});
		
		this.populateView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		this.populateView();
	}
	
	private void populateView() {
		Bundle extras = this.getIntent().getExtras();
		
		ImageView icon = (ImageView) findViewById(R.id.icon);
		TextView message = (TextView) findViewById(R.id.message);
		TextView location = (TextView) findViewById(R.id.location);
		
		icon.setImageResource(extras.getInt("jslog.itemIcon"));
		message.setText(extras.getString("jslog.itemMessage"));
		location.setText(extras.getString("jslog.itemLocation"));
	}
	
}
