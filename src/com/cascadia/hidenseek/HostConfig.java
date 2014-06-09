package com.cascadia.hidenseek;

import java.util.ArrayList;
import java.util.List;

import com.cascadia.hidenseek.network.GetPlayerListRequest;
import com.cascadia.hidenseek.network.PutStartRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class HostConfig extends Activity {

	String username, counttime, seektime;
	ListView list;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_config);
		
		initSettings();
		
		list=(ListView)findViewById(R.id.configPlayerList);
		
		TextView playersText = (TextView) findViewById(R.id.configPlayersTitle);
		playersText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setPlayerList();
			}
		});
		
        ImageButton btnConfigMatch = (ImageButton) findViewById(R.id.btnConfigBegin);
        btnConfigMatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	//Set the match count time and seek time as specified, etc.
            	EditText countTime = (EditText) findViewById(R.id.configCountTimeInput);
            	EditText seekTime = (EditText) findViewById(R.id.configSeekTimeInput);
            	Match m = LoginManager.GetMatch();
            	m.SetCountTime(Integer.parseInt(countTime.getText().toString()));
            	m.SetSeekTime(Integer.parseInt(seekTime.getText().toString()));
            	PutStartRequest request = new PutStartRequest() {
					@Override
					protected void onException(Exception e) {
						
					}
					@Override
					protected void onComplete(Match m) {
		    			Intent intent = new Intent(HostConfig.this, Active.class);
		    			startActivity(intent);
					}
				};
				request.DoRequest(m);
            }
        });	
		setPlayerList();		
	}
	
	private void setPlayerList() {
		if(LoginManager.GetMatch() == null) {
			String[] titles = {"Failed to update match list.", "(null match)"};
			CustomList adapter = new CustomList(HostConfig.this, titles);
			list.setAdapter(adapter);
			return;
		}
		GetPlayerListRequest request = new GetPlayerListRequest() {
			
			@Override
			protected void onException(Exception e) {
				String[] titles = {"Failed to update match list."};
				CustomList adapter = new CustomList(HostConfig.this, titles);
				list.setAdapter(adapter);
			}
			
			@Override
			protected void onComplete(Match match) {
				String[] titles = new String[match.players.size()];
				int i = 0;
				for(Player p : match.players) {
					titles[i] = p.GetName();
					i++;
				}
				CustomList adapter = new CustomList(HostConfig.this, titles);
				list.setAdapter(adapter);
				
			}
		};
		request.DoRequest(LoginManager.GetMatch());
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.host_config, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Get any stored preferences and put them in the fields when form is loaded
	 */
	private void initSettings(){		
		counttime = getSharedPreferences("HideNSeek_shared_pref", MODE_PRIVATE).getString("Counttime", "");
		EditText cTime = (EditText)findViewById(R.id.configCountTimeInput);
		cTime.setText(counttime);
		
		seektime = getSharedPreferences("HideNSeek_shared_pref", MODE_PRIVATE).getString("Seektime", "");
		EditText sTime = (EditText)findViewById(R.id.configSeekTimeInput);
		sTime.setText(seektime);
	}
}
