package com.cascadia.hidenseek;

import java.util.ArrayList;
import java.util.List;

import com.cascadia.hidenseek.Match.Status;
import com.cascadia.hidenseek.network.GetMatchListRequest;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.os.Build;

public class JoinLogin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_login);
		initList();

	}
	
	private void initList() {
		GetMatchListRequest request = new GetMatchListRequest() {
			
			@Override
			protected void onException(Exception e) { }
			
			@Override
			protected void onComplete(List<Match> matches) {
				ListView l = (ListView) findViewById(R.id.configPlayerList);
				ArrayList<String> gameTitles = new ArrayList<String>();
				for(Match m : matches) {
					if(m.GetStatus() == Status.Pending) {
						//TODO: put it in the ListView using the following string
						String title = m.GetId() + ' ' + m.GetName();
						gameTitles.add(title);
					}
				}
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(JoinLogin.this,android.R.layout.simple_list_item_single_choice, gameTitles);
				l.setAdapter(arrayAdapter);				
			}
		};
		request.DoRequest();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.join_login, menu);
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

}
