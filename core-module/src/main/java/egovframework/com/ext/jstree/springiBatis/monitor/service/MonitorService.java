package egovframework.com.ext.jstree.springiBatis.monitor.service;

import egovframework.com.ext.jstree.springiBatis.monitor.vo.P_JqGrid;
import egovframework.com.ext.jstree.springiBatis.monitor.vo.T_JqGridCellData;

import java.util.List;

public interface MonitorService {

	public List<T_JqGridCellData> getJqGridCellData(P_JqGrid p_JqGrid);
}
