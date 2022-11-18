package egovframework.com.ext.jstree.springiBatis.core.dao;

import egovframework.com.ext.jstree.springiBatis.core.vo.ComprehensiveTree;

public interface SupportDao {

    public <T extends ComprehensiveTree> void ddlExecute(T comprehensiveTree) throws Exception;

    public <T extends ComprehensiveTree> void ddlSequenceExecute(T comprehensiveTree) throws Exception;

    public <T extends ComprehensiveTree> void dmlExecute(T comprehensiveTree) throws Exception;

    public <T extends ComprehensiveTree> void ddlLogExecute(T comprehensiveTree) throws Exception;

    public <T extends ComprehensiveTree> int isExistTable(T comprehensiveTree) throws Exception;

}
