package egovframework.com.ext.jstree.springiBatis.core.service;

import egovframework.com.ext.jstree.springiBatis.core.dao.SupportDao;
import egovframework.com.ext.jstree.springiBatis.core.vo.ComprehensiveTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("supportService")
public class SupportServiceImpl extends CoreServiceImpl implements SupportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "supportDao")
    private SupportDao supportDao;

    @Override
    @Transactional(readOnly = false, rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
    public <T extends ComprehensiveTree> void setDefault_DDL_Table(T comprehensiveTree) throws Exception {
        supportDao.ddlExecute(comprehensiveTree);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
    public <T extends ComprehensiveTree> void setDefault_DDL_Sequence(T comprehensiveTree) throws Exception {
        supportDao.ddlSequenceExecute(comprehensiveTree);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
    public <T extends ComprehensiveTree> void setDefault_DML_Table(T comprehensiveTree) throws Exception {
        supportDao.dmlExecute(comprehensiveTree);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
    public <T extends ComprehensiveTree> void setDefaultLog_DDL_Table(T comprehensiveTree) throws Exception {
        supportDao.ddlLogExecute(comprehensiveTree);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
    public <T extends ComprehensiveTree> void setDefaultLog_DDL_Trigger(T comprehensiveTree) throws Exception {
        supportDao.ddlLogTriggerExecute(comprehensiveTree);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
    public <T extends ComprehensiveTree> int isExist_Default_DB(T comprehensiveTree) throws Exception {
        return supportDao.isExistTable(comprehensiveTree);
    }

}
