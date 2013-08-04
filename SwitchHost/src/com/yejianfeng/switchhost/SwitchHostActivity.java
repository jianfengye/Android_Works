package com.yejianfeng.switchhost;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SwitchHostActivity extends Activity {

	// 储存所有host
	private ArrayList<HostItem> hosts;
	private HostItemAdapter arrayAdapter;
	private HostFileOperator operator;
	
	public static final String CUR_HOSTNAME = "CUR_HOSTNAME";
	public static final String DEFAULT_HOSTNAME = "default";
	public static final String CUR_SHARE_PREFERENCE = "cur_share_preference";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// 各种初始化操作
		this.operator = new HostFileOperator(this.getApplicationContext());
		
		// 如果没有default文件，则将当前host设置为host
		if (false == this.operator.isExistHost(this.DEFAULT_HOSTNAME)) {
			String curHostContent = this.operator.getActivityHost();
			this.operator.AddHost(this.DEFAULT_HOSTNAME, curHostContent);
		}
		
		ListView hostListView = (ListView) findViewById(R.id.HostListView);
		String[] hostNames = this.operator.GetHostsName();
		
		SharedPreferences prefs = this.getSharedPreferences(SwitchHostActivity.CUR_SHARE_PREFERENCE, Context.MODE_PRIVATE);
		String curHostName = prefs.getString(SwitchHostActivity.CUR_HOSTNAME,SwitchHostActivity.DEFAULT_HOSTNAME);
		
		this.hosts = new ArrayList<HostItem>();
		
		// 将hosts绑定到listView
		this.arrayAdapter = new HostItemAdapter(this, R.layout.hostitem, R.id.hostName, hosts);
		hostListView.setAdapter(this.arrayAdapter);
		
		registerForContextMenu(hostListView); 
		
		// 需要root权限
		try {
			Process process = Runtime.getRuntime().exec("su");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 每次启动的时候都加载一次数据
	// TODO: 这里似乎可以改善
	protected void onStart()
	{
		super.onStart();
		String[] hostNames = this.operator.GetHostsName();
		
		SharedPreferences prefs = this.getSharedPreferences(SwitchHostActivity.CUR_SHARE_PREFERENCE, Context.MODE_PRIVATE);
		String curHostName = prefs.getString(SwitchHostActivity.CUR_HOSTNAME,SwitchHostActivity.DEFAULT_HOSTNAME);
		
		this.hosts.clear();
		for (int i=0; i<hostNames.length; i++) {
			HostItem tempItem = new HostItem(hostNames[i]);
			if (hostNames[i].equals(curHostName)) {
				tempItem.setIsCur(true);
			}
			this.hosts.add(tempItem);
		}
		this.arrayAdapter.notifyDataSetChanged();
	}
	
	public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.host_menu, menu);
	}
	
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		String curHostName;
		
		RelativeLayout hostView;
		
		hostView = (RelativeLayout) info.targetView;
		TextView hostitemName = (TextView)hostView.findViewById(R.id.hostitem_name);
		
		curHostName = hostitemName.getText().toString();
		switch(item.getItemId()) {
		case R.id.set:
			this.operator.setActivityHost(curHostName);
			SharedPreferences prefs = this.getSharedPreferences(SwitchHostActivity.CUR_SHARE_PREFERENCE, Context.MODE_PRIVATE);
			Editor editor = prefs.edit();
			editor.putString(this.CUR_HOSTNAME, curHostName);
			editor.commit();
			new Builder(this).setTitle("提示").setMessage("设置成功").show();
			this.onStart();
			return true;
		case R.id.detail:
			Intent intent = new Intent();
    		intent.setClass(this, ModifyHostActivity.class);
    		intent.putExtra("curHostName", curHostName);
    		startActivity(intent);
			return true;
		case R.id.delete:
			this.operator.DeleteHost(curHostName);
			new Builder(this).setTitle("提示").setMessage("删除成功").show();
			this.onStart();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_preference, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.add:
    		Intent intent = new Intent();
    		intent.setClass(this, AddHostActivity.class);
    		startActivity(intent);
    		return true;
    	case R.id.quit:
    		finish();
    		return true;
    	default:
    		return super.onContextItemSelected(item);
    	}
    }
}
