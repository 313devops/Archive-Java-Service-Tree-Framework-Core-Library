package egovframework.com.ext.jstree.strutsiBatis.monitor.service;

import egovframework.com.ext.jstree.strutsiBatis.core.dao.I_GenericDao;
import egovframework.com.ext.jstree.strutsiBatis.monitor.vo.P_JqGrid;
import egovframework.com.ext.jstree.strutsiBatis.monitor.vo.T_JqGridCellData;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface I_S_GetJstreeMonitor extends
		I_GenericDao<T_JqGridCellData, P_JqGrid> {

	static Logger logger = Logger.getLogger(I_S_GetJstreeMonitor.class);

	public void setRequest(HttpServletRequest request);

	public List<T_JqGridCellData> getJqGridCellData(P_JqGrid p_JqGrid);
}
