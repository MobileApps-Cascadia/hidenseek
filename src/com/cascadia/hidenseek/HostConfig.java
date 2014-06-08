package com.cascadia.hidenseek;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class HostConfig extends Activity {

	ListView list;
	//Add players here
	String[] web = {
		"Billie Jo", "Mike", "Tre Cool"
	} ;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_config);
		
		CustomList adapter = new CustomList(HostConfig.this, web);
		list=(ListView)findViewById(R.id.configPlayerList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	            //add later? or probably it's a radio listener
	
	        }
        });
		
        ImageButton btnConfigMatch = (ImageButton) findViewById(R.id.btnConfigBegin);
        btnConfigMatch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
    			Intent intent = new Intent(HostConfig.this, Active.class);
    			startActivity(intent);
            }
        });			
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
}
