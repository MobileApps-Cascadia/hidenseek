package com.cascadia.hidenseek;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkBase {

	public NetworkBase() {
	}
	
	public enum RequestType {
		GET,
		PUT,
		PUT_NoArgs,
		POST
	}
	
	public String Request(Request r) throws IOException {
		switch(r.type) {
		case GET:
			return Get(r.url);
		case POST:
			return Post(r.url, r.jsonArgs);
		case PUT:
			return Put(r.url, r.jsonArgs);
		case PUT_NoArgs:
			return Put(r.url);
		default:
			return null; //suppress errors
		}
	}
	
	//Send a GET request to the given url and returns the received text
	public String Get(String url) throws IOException {
		return get(url, false);
	}
	
	//Sends a PUT request with a text body to the given url and returns
	//the received text
	public String Put(String url, String jsonArgs) throws IOException {
		return post(url, jsonArgs, true);
	}
	
	//Sends a PUT request without a text body to the given url and returns
	//the received text
	public String Put(String url) throws IOException {
		return get(url, true);
	}
	
	//Sends a POST request with a text body to the given url and returns
	//the received text
	public String Post(String url, String jsonArgs) throws IOException {
		return post(url, jsonArgs, false);
	}
	
	
	//Sends a request to the given strUrl with the given jsonArgs as text body
	//and returns the returned text info.
	//Sends the message as a POST request unless isPut is set to true, in
	//which case the message is sent as a PUT request.	
	private String post(String strUrl, String jsonArgs, boolean isPut)
			throws IOException {
		URL url;
		String toReturn = "";
		try {
			url = new URL(strUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return "";
		}
		connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setDoOutput(true);
			//The above automatically sets the request method to "POST";
			//change it to "PUT" if requested.
			if(isPut) {
				connection.setRequestMethod("PUT");
			}
			//Sets connection to stream in chunks; 0 means use system default
			connection.setChunkedStreamingMode(0);
			writeOutStream(jsonArgs);
			toReturn = getInStream();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return toReturn;
	}
	
	//Sends a request to the given strUrl and returns the returned text info.
	//Sends the message as a GET request unless isPut is set to true, in
	//which case the message is sent as a PUT request.
	private String get(String strUrl, boolean isPut) throws IOException {
		URL url;
		String toReturn;
		try {
			url = new URL(strUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return "";
		}
		connection = (HttpURLConnection) url.openConnection();
		try {
			//Automatically set to "GET"; change it to "PUT" if requested.
			if(isPut) {
				connection.setRequestMethod("PUT");
			}
			toReturn = getInStream();
		} finally {
			connection.disconnect();
		}
		return toReturn;
	}

	//Assumes that this.connection is open and attempts to write the given
	//output to it
	private void writeOutStream(String output) throws IOException {
		connection.getOutputStream().write(output.getBytes());
	}
	
	//Assumes that this.connection is open and attempts to read a stream from it
	private String getInStream() throws IOException {
		String toReturn = convertStreamToString(connection.getInputStream());
		return toReturn;
	}
	
	//Method from http://stackoverflow.com/questions
	//					/309424/read-convert-an-inputstream-to-a-string
	//Gets a string from an input stream
	private String convertStreamToString(InputStream istream) {
	    Scanner s = new Scanner(istream).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	private HttpURLConnection connection;

}
