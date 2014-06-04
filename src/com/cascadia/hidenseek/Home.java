package com.cascadia.hidenseek;

import java.io.IOException;
import java.util.List;

import com.cascadia.hidenseek.Match.MatchType;
import com.cascadia.hidenseek.network.GetMatchRequest;
import com.cascadia.hidenseek.network.PostMatchRequest;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.os.Build;

public class Home extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		
        ImageButton btnHost = (ImageButton) findViewById(R.id.btnHostHome);
        btnHost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(Home.this, HostLogin.class);
    			startActivity(intent);
            }
        });
        ImageButton btnJoin = (ImageButton) findViewById(R.id.btnJoinHome);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(Home.this, JoinLogin.class);
    			startActivity(intent);
            }
        });
        
        try {
			
			PostMatchRequest pr = new PostMatchRequest() {
				
				@Override
				protected void onComplete(Match match) {
					GetMatchRequest nr = new GetMatchRequest() {
						
						@Override
						protected void onComplete(Match m) {
							return;
						}

						@Override
						protected void onException(Exception e) {
							
						}
					};
					nr.DoRequest(match.GetId());
					return;
				}

				@Override
				protected void onException(Exception e) {
					
				}
			};
			Match m = new Match();
			m = new Match("Aaron's Match", "Aaron's Password", MatchType.Sandbox);
			pr.DoRequest(m);
			
        } catch(RuntimeException e) {
        	e.printStackTrace();
		} catch(Exception e) {
        	e.printStackTrace();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
