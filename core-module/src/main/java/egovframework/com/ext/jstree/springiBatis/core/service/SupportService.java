package egovframework.com.ext.jstree.springiBatis.core.service;

import egovframework.com.ext.jstree.springiBatis.core.vo.ComprehensiveTree;

public interface SupportService extends CoreService {
    
    public <T extends ComprehensiveTree> void setDefault_DDL_Table(T comprehensiveTree) throws Exception;

    public <T extends ComprehensiveTree> void setDefault_DDL_Sequence(T comprehensiveTree) throws Exception;

    public <T extends ComprehensiveTree> void setDefault_DML_Table(T comprehensiveTree) throws Exception;

    public <T extends ComprehensiveTree> void setDefaultLog_DDL_Table(T comprehensiveTree) throws Exception;

    public <T extends ComprehensiveTree> int isExist_Default_DB(T comprehensiveTree) throws Exception;
}
