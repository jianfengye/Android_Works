package com.yejianfeng.switchhost;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HostItemAdapter extends ArrayAdapter<HostItem> {
	private int resource;
	
	public HostItemAdapter(Context context, int resource, List<HostItem> objects) {
		super(context, resource, objects);
		this.resource = resource;
	}
	
	public HostItemAdapter(Context context, int resource, int textViewResourceId, List<HostItem> objects) {
		super(context, resource, textViewResourceId, objects);
		this.resource = resource;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		HostItem item = getItem(position);
		
		RelativeLayout hostView;
		if (convertView == null) {
			hostView = new RelativeLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li;
			li = (LayoutInflater)getContext().getSystemService(inflater);
			li.inflate(this.resource, hostView, true);
		} else {
			hostView = (RelativeLayout) convertView;
		}
		
		TextView hostItem = (TextView) hostView.findViewById(R.id.hostitem_name);
		ImageView imageItem = (ImageView) hostView.findViewById(R.id.host_selected);
		
		if (item.checkIsCur() == false) {
			imageItem.setVisibility(View.INVISIBLE);
		} else {
			imageItem.setVisibility(View.VISIBLE);
		}
		hostItem.setText(item.getHostName());
		return hostView;
	}
}
