package com.yejianfeng.switchhost;

public class HostItem {
	private String hostName;
	private boolean isCur;
	
	public String getHostName()
	{
		return this.hostName;
	}
	
	public boolean checkIsCur()
	{
		return this.isCur;
	}
	
	public HostItem(String hostName, boolean isCur)
	{
		this.hostName = hostName;
		this.isCur = isCur;
	}
	
	public HostItem(String hostName)
	{
		this(hostName, false);
	}
	
	public void setIsCur(boolean isCur)
	{
		this.isCur = isCur;
	}
}
