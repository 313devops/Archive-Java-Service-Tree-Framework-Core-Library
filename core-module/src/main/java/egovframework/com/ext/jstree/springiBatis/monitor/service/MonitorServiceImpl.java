package egovframework.com.ext.jstree.springiBatis.monitor.service;

import egovframework.com.ext.jstree.springiBatis.monitor.dao.MonitorDAO;
import egovframework.com.ext.jstree.springiBatis.monitor.vo.P_JqGrid;
import egovframework.com.ext.jstree.springiBatis.monitor.vo.T_JqGridCellData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("MonitorService")
public class MonitorServiceImpl implements MonitorService {
	
	@Resource(name="MonitorDAO")
	private MonitorDAO monitorDAO;
	
	@Override
	public List<T_JqGridCellData> getJqGridCellData(P_JqGrid p_JqGrid) {
		return monitorDAO.getJqGridCellData(p_JqGrid);
	}
}