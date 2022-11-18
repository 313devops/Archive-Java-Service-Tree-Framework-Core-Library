package egovframework.com.ext.jstree.support.mvc;

import egovframework.com.ext.jstree.springiBatis.core.service.SupportService;
import egovframework.com.ext.jstree.springiBatis.core.vo.ComprehensiveTree;
import egovframework.com.ext.jstree.support.util.ParameterParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
@RequestMapping(value = {"/auth-anon/com/ext/jstree/support"})
public class MakeDBController extends GenericAbstractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "SupportService")
    SupportService supportService;

    @Resource(name = "egov.dataSource")
    DataSource dataSource;

    @ResponseBody
    @RequestMapping(
            value = {"/makeDB.do"},
            method = {RequestMethod.GET}
    )
    public ModelAndView makeDefaultDB(ComprehensiveTree comprehensiveTree, ModelMap model, HttpServletRequest request) throws Exception {

        ParameterParser parser = new ParameterParser(request);

        logger.info("MakeDBController :: makeDefaultDB :: tableName = " + parser.get("name"));

        comprehensiveTree.setSqlMapSelector("support");

        if(supportService.isExist_Default_DB(comprehensiveTree) == 1){
            logger.error("already exist JSTF table : " + comprehensiveTree.getC_title());
        }else{
            supportService.setDefault_DDL_Table(comprehensiveTree);
            supportService.setDefault_DDL_Sequence(comprehensiveTree);
            supportService.setDefault_DML_Table(comprehensiveTree);
        }

        String C_title_org = comprehensiveTree.getC_title();
        comprehensiveTree.setC_title(comprehensiveTree.getC_title() + "_LOG");
        if(supportService.isExist_Default_DB(comprehensiveTree) == 1){
            logger.error("already exist log table : " + comprehensiveTree.getC_title());
        }else{
            comprehensiveTree.setC_title(C_title_org);
            supportService.setDefaultLog_DDL_Table(comprehensiveTree);
            makeTrigger(comprehensiveTree);
        }

        ModelAndView modelAndView =  new ModelAndView("jsonView");
        modelAndView.addObject("result", "good");
        return modelAndView;
    }

    private void makeTrigger(ComprehensiveTree comprehensiveTree) throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        String sql =
            "CREATE OR REPLACE TRIGGER \"TRIG_" + comprehensiveTree.getC_title() + "\"\n" +
            "BEFORE DELETE OR INSERT OR UPDATE\n" +
            "ON " + comprehensiveTree.getC_title() + " REFERENCING NEW AS NEW OLD AS OLD\n" +
            "FOR EACH ROW\n" +
            "DECLARE\n" +
            "tmpVar NUMBER;\n" +
            "/******************************************************************************\n" +
            "   NAME:       TRIGGER_COMPREHENSIVETREE\n" +
            "   PURPOSE:    \n" +
            " \n" +
            "   REVISIONS:\n" +
            "   Ver        Date        Author           Description\n" +
            "   ---------  ----------  ---------------  ------------------------------------\n" +
            "   1.0        2012-08-29             1. Created this trigger.\n" +
            " \n" +
            "   NOTES:\n" +
            " \n" +
            "   Automatically available Auto Replace Keywords:\n" +
            "      Object Name:     TRIGGER_COMPREHENSIVETREE\n" +
            "      Sysdate:         2012-08-29\n" +
            "      Date and Time:   2012-08-29, 오후 5:26:44, and 2012-08-29 오후 5:26:44\n" +
            "      Username:         (set in TOAD Options, Proc Templates)\n" +
            "      Table Name:      T_ARMS_REQADD (set in the \"New PL/SQL Object\" dialog)\n" +
            "      Trigger Options:  (set in the \"New PL/SQL Object\" dialog)\n" +
            "******************************************************************************/\n" +
            "BEGIN\n" +
            "  tmpVar := 0;\n" +
            "   IF UPDATING  THEN    \n" +
            "       insert into " + comprehensiveTree.getC_title() + "_LOG (C_ID,C_PARENTID,C_POSITION,C_LEFT,C_RIGHT,C_LEVEL,C_TITLE,C_TYPE,C_METHOD,C_STATE,C_DATE)\n" +
            "       values (:old.C_ID,:old.C_PARENTID,:old.C_POSITION,:old.C_LEFT,:old.C_RIGHT,:old.C_LEVEL,:old.C_TITLE,:old.C_TYPE,'update','변경이전데이터',sysdate);\n" +
            "       insert into " + comprehensiveTree.getC_title() + "_LOG (C_ID,C_PARENTID,C_POSITION,C_LEFT,C_RIGHT,C_LEVEL,C_TITLE,C_TYPE,C_METHOD,C_STATE,C_DATE)\n" +
            "       values (:new.C_ID,:new.C_PARENTID,:new.C_POSITION,:new.C_LEFT,:new.C_RIGHT,:new.C_LEVEL,:new.C_TITLE,:new.C_TYPE,'update','변경이후데이터',sysdate);\n" +
            "    END IF;\n" +
            "   IF DELETING THEN\n" +
            "       insert into " + comprehensiveTree.getC_title() + "_LOG (C_ID,C_PARENTID,C_POSITION,C_LEFT,C_RIGHT,C_LEVEL,C_TITLE,C_TYPE,C_METHOD,C_STATE,C_DATE)\n" +
            "       values (:old.C_ID,:old.C_PARENTID,:old.C_POSITION,:old.C_LEFT,:old.C_RIGHT,:old.C_LEVEL,:old.C_TITLE,:old.C_TYPE,'delete','삭제된데이터',sysdate);\n" +
            "   END IF;   \n" +
            "   IF INSERTING  THEN\n" +
            "       insert into " + comprehensiveTree.getC_title() + "_LOG (C_ID,C_PARENTID,C_POSITION,C_LEFT,C_RIGHT,C_LEVEL,C_TITLE,C_TYPE,C_METHOD,C_STATE,C_DATE)\n" +
            "       values (:new.C_ID,:new.C_PARENTID,:new.C_POSITION,:new.C_LEFT,:new.C_RIGHT,:new.C_LEVEL,:new.C_TITLE,:new.C_TYPE,'insert','삽입된데이터',sysdate);\n" +
            "   END IF;\n" +
            " \n" +
            "  EXCEPTION\n" +
            "    WHEN OTHERS THEN\n" +
            "      -- Consider logging the error and then re-raise\n" +
            "      RAISE;\n" +
            "END TRIG_" + comprehensiveTree.getC_title() + ";";
        statement.execute(sql);
    }
}
