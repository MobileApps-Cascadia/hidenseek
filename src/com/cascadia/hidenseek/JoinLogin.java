package com.cascadia.hidenseek;

import java.util.ArrayList;
import java.util.List;

import com.cascadia.hidenseek.Match.Status;
import com.cascadia.hidenseek.network.GetMatchListRequest;
import com.cascadia.hidenseek.network.GetMatchRequest;
import com.cascadia.hidenseek.network.PostPlayerRequest;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.os.Build;

public class JoinLogin extends Activity {

	String username;
	ListView l;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_login);
		l = (ListView) findViewById(R.id.configPlayerList);
		initSettings();
		initList();
		
		ImageButton btnJoin = (ImageButton) findViewById(R.id.btnJoinJoin);
		btnJoin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				joinMatch();
			}
		});
		
	}
	
	/**
	 * initList creates a list of all the matches that are in a pending state
	 */
	private void initList() {
		GetMatchListRequest request = new GetMatchListRequest() {
			
			@Override
			protected void onException(Exception e) { }		
			
			@Override
			protected void onComplete(List<Match> matches) {
				//Gets the list of matches and puts in listview
				ArrayList<String> gameTitles = new ArrayList<String>();
				for(Match m : matches) {
					if(m.GetStatus() == Status.Pending) {
						String title = m.GetId() + " - " + m.GetName();
						gameTitles.add(title);
					}
				}
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(JoinLogin.this,android.R.layout.simple_list_item_single_choice, gameTitles);
				l.setAdapter(arrayAdapter);				
			}
		};
		request.DoRequest();
		
	}

	private void joinMatch() {
		String entry = l.getItemAtPosition(l.getCheckedItemPosition()).toString();
		String intString = entry.replaceFirst(" - .*", "");
		int matchId = Integer.parseInt(intString);
		
		GetMatchRequest gmRequest = new GetMatchRequest() {
			
			@Override
			protected void onException(Exception e) {
				e.printStackTrace();
			}
			
			@Override
			protected void onComplete(Match match) {
				EditText mPassword = (EditText) findViewById(R.id.JoinPasswordInput);
				EditText pName = (EditText) findViewById(R.id.TextPlayerNameInput);
				Player p = new Player(pName.getText().toString(), match);
				PostPlayerRequest ppRequest = new PostPlayerRequest() {
					@Override
					protected void onException(Exception e) {
						// TODO Auto-generated method stub
						e.printStackTrace();
					}
					
					@Override
					protected void onComplete(Player p) {
						//TODO: don't keep going if the password was wrong.
						LoginManager.ValidateJoinLogin(p);
						Intent intent = new Intent(JoinLogin.this, HostConfig.class);
	        			startActivity(intent);
					}
				};
				ppRequest.DoRequest(p, mPassword.getText().toString());
			}
		};
		gmRequest.DoRequest(matchId);
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
	
	/**
	 * Get any stored preferences and put them in the fields when form is loaded
	 */
	private void initSettings(){		
		username = getSharedPreferences("HideNSeek_shared_pref", MODE_PRIVATE).getString("Username","");
		EditText uName = (EditText)findViewById(R.id.TextPlayerNameInput);
		uName.setText(username);
	}
}
