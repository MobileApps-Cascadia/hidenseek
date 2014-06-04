package com.cascadia.hidenseek;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.Build;

public class Active extends FragmentActivity {
	GoogleMap googleMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active);
		
		googleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapview)).getMap();
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.setMyLocationEnabled(true);
		googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener(){
			@Override
			public void onMyLocationChange(Location location){
				LatLng point = new LatLng(location.getLatitude(),location.getLongitude());
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point,18));
			}
		});		
	}
	
	public void onPause(){
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		final String TAG_ERROR_DIALOG_FRAGMENT="errorDialog";
		int status=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if(status == ConnectionResult.SUCCESS){
			//no problems just work
		}
		else if(GooglePlayServicesUtil.isUserRecoverableError(status)){
			ErrorDialogFragment.newInstance(status).show(getSupportFragmentManager(), TAG_ERROR_DIALOG_FRAGMENT);
		}
		else{
			Toast.makeText(this,"Google Maps V2 is not available!",Toast.LENGTH_LONG).show();
			finish();
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class ErrorDialogFragment extends DialogFragment{
		static final String ARG_STATUS="status";
		
		static ErrorDialogFragment newInstance (int status){
			Bundle args=new Bundle();
			args.putInt(ARG_STATUS, status);
			ErrorDialogFragment result = new ErrorDialogFragment();
			result.setArguments(args);
			return(result);
		}
		
		public void show(FragmentManager supportFragmentManager,
				String TAG_ERROR_DIALOG_FRAGMENT) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
	        Bundle args=getArguments();
	        return GooglePlayServicesUtil.getErrorDialog(args.getInt(ARG_STATUS),
	                                                            getActivity(), 0);
	    }

	    @Override
	    public void onDismiss(DialogInterface dlg) {
	        if (getActivity() != null) {
	            getActivity().finish();
	        }
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.active, menu);
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