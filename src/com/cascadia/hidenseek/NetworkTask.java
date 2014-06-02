package com.cascadia.hidenseek;

import java.io.IOException;

import android.os.AsyncTask;

public abstract class NetworkTask extends AsyncTask<Request, Void, String> {

	@Override
	protected String doInBackground(Request ... arg0) {
		try {
			return nbase.Request(arg0[0]);
		} catch (IOException e) {
			return null;
		}
	}
	
	protected static NetworkBase nbase = new NetworkBase();

}
