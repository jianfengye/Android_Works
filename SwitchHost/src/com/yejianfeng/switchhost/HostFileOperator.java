package com.yejianfeng.switchhost;

public class HostFileOperator {
	
	// 获取所有host
	public String[] GetHostsName()
	{
		return null;
	}
	
	// 增加一个host
	public boolean AddHost(String hostName, String hostContent)
	{
		return true;
	}
	
	// 删除一个host
	public boolean DeleteHost(String hostName)
	{
		return true;
	}
	
	// 修改一个host
	public boolean ModifyHost(String hostName, String hostContent)
	{
		this.DeleteHost(hostName);
		return this.AddHost(hostName, hostContent);
	}
}
