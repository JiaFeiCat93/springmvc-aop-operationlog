package com.qetch.springmvc.service;

import java.util.List;

import com.qetch.springmvc.domain.OperationLog;

public interface OperationLogService {
	
	int saveLog(OperationLog operationLog);
	
	List<OperationLog> getAllLogs();
}
