package egovframework.com.ext.jstree.springHibernate.core.interceptor;

import egovframework.com.ext.jstree.support.util.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteTableInterceptor extends EmptyInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RouteTableInterceptor.class);

    @Override
    public String onPrepareStatement(String sql) {

        String prepedStatement = super.onPrepareStatement(sql);
        try {
            if (null == SessionUtil.getAttribute("replaceTableName")) {
                System.out.println("Normal JSTF Framework Excute");
            } else {
                String replaceTableName = (String) SessionUtil.getAttribute("replaceTableName");
                logger.info("CustomInterceptor :: prepedStatement - before =>" + prepedStatement);
                System.out.println("CustomInterceptor :: replaceTableName =>" + replaceTableName);
                if (StringUtils.isNotEmpty(replaceTableName)) {
                    prepedStatement = prepedStatement.replaceAll("from T_ARMS_REQ", "from " + replaceTableName);
                    System.out.println("CustomInterceptor :: prepedStatement - after =>" + prepedStatement);
                }
                return prepedStatement;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prepedStatement;
    }
}