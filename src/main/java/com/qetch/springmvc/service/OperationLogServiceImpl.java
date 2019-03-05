package com.qetch.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.qetch.springmvc.domain.OperationLog;

@Service
public class OperationLogServiceImpl implements OperationLogService {
	private List<OperationLog> logList = new ArrayList<>();
	
	@Override
	public int saveLog(OperationLog operationLog) {
		logList.add(operationLog);
		return 1;
	}
	
	@Override
	public List<OperationLog> getAllLogs() {
		return logList;
	}
}
