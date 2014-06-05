package com.cascadia.hidenseek;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Build;

public class HostLogin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_login);
		initSpinner();
		
        ImageButton btnHost = (ImageButton) findViewById(R.id.loginBtnHost);
        btnHost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EditText mName = (EditText) findViewById(R.id.loginMatchNameInput);
            	Spinner mType = (Spinner) findViewById(R.id.loginMatchTypeSelect);
            	EditText mPassword = (EditText) findViewById(R.id.loginPasswordInput);
            	
            	Match m = HostLoginManager.ValidateLogin(mName.getText().toString(),
			            			mPassword.getText().toString(),
			            			mType.getSelectedItemPosition());
            	if(m == null) {
            		//TEMP: start activity without sending any info to the server
        			Intent intent = new Intent(HostLogin.this, HostConfig.class);
        			startActivity(intent);
        			return;
            	}
            	PostMatchRequest pm = new PostMatchRequest() {
					
            		@Override
            		protected void onComplete(Match m) {
            			Intent intent = new Intent(HostLogin.this, HostConfig.class);
            			startActivity(intent);
            		}
            		
					@Override
					protected void onException(Exception e) {
						e.printStackTrace();
					}
				};
				pm.DoRequest(m);
            }
        });
	}

	private void initSpinner() {
		Spinner s = (Spinner) findViewById(R.id.loginMatchTypeSelect);
		ArrayAdapter<CharSequence> adapter =
				ArrayAdapter.createFromResource(this, R.array.login_match_types,
												android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.host_login, menu);
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
