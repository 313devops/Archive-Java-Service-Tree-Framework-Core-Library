package egovframework.com.ext.jstree.strutsiBatis.monitor.dao;

import egovframework.com.ext.jstree.strutsiBatis.core.dao.I_GenericDao;
import egovframework.com.ext.jstree.strutsiBatis.monitor.vo.P_JqGrid;
import egovframework.com.ext.jstree.strutsiBatis.monitor.vo.T_JqGridCellData;
import org.apache.log4j.Logger;

import java.util.List;

public interface I_DB_ComprehensiveTree extends
		I_GenericDao<T_JqGridCellData, P_JqGrid> {

	static Logger logger = Logger.getLogger(I_DB_ComprehensiveTree.class);

	List<T_JqGridCellData> getJstreeMonitor(P_JqGrid p_JqGrid,
											String determineDBSetting);

}
