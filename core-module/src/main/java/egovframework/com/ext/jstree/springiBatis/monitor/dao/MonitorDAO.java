package egovframework.com.ext.jstree.springiBatis.monitor.dao;

import egovframework.com.cmm.service.impl.EgovComiBatisAbstractDAO;
import egovframework.com.ext.jstree.springiBatis.monitor.vo.P_JqGrid;
import egovframework.com.ext.jstree.springiBatis.monitor.vo.T_JqGridCellData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MonitorDAO")
public class MonitorDAO extends EgovComiBatisAbstractDAO {

	@SuppressWarnings("unchecked")
	public List<T_JqGridCellData> getJqGridCellData(P_JqGrid p_JstreeMonitor) {

		return (List<T_JqGridCellData>) list("monitor.getJstreeMonitor", p_JstreeMonitor);
	}
}