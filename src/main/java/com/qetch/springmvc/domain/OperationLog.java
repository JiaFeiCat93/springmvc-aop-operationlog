package com.qetch.springmvc.domain;

public class OperationLog {
	private String logId;
	private String time;
	private String ip;
	private String userName;
	private String logType;
	private String logPage;
	private String logOperateParam;
	private String logDesc;
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getLogPage() {
		return logPage;
	}
	public void setLogPage(String logPage) {
		this.logPage = logPage;
	}
	public String getLogOperateParam() {
		return logOperateParam;
	}
	public void setLogOperateParam(String logOperateParam) {
		this.logOperateParam = logOperateParam;
	}
	public String getLogDesc() {
		return logDesc;
	}
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
	@Override
	public String toString() {
		return "OperationLog [logId=" + logId + ", time=" + time + ", ip=" + ip + ", userName=" + userName
				+ ", logType=" + logType + ", logPage=" + logPage + ", logOperateParam=" + logOperateParam
				+ ", logDesc=" + logDesc + "]";
	}
}
