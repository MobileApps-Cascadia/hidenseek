package com.cascadia.hidenseek;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{
	
	private final Activity context;
	private final String[] web;
	public CustomList(Activity context,
	String[] web) {
		super(context, R.layout.list_single, web);
		this.context = context;
		this.web = web;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		RadioGroup rGroup = (RadioGroup) rowView.findViewById(R.id.radioGroup1);
		txtTitle.setText(web[position]);
	
		return rowView;
	}
}



