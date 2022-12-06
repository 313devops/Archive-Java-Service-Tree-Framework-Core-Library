package egovframework.com.ext.jstree.springHibernate.core.interceptor;

import egovframework.com.ext.jstree.support.util.StringUtils;
import org.hibernate.EmptyInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class RouteTableInterceptor extends EmptyInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RouteTableInterceptor.class);

    @Override
    public String onPrepareStatement(String sql) {

        String prepedStatement = super.onPrepareStatement(sql);

        try {
            HttpServletRequest httpServletRequest = SessionUtil.getUrl();
            String servletPath = httpServletRequest.getServletPath();

            ///auth-user/api/arms/reqAdd/T_ARMS_REQADD_145/getChildNode.do

            if(StringUtils.contains(servletPath,"T_ARMS_REQADD_")){
                if(StringUtils.contains(servletPath,"getMonitor.do")){
                    String replaceTableName = (String) SessionUtil.getAttribute("getMonitor");
                    return replaceStatement(prepedStatement, replaceTableName);
                }
                if(StringUtils.contains(servletPath,"getNode.do")){
                    String replaceTableName = (String) SessionUtil.getAttribute("getNode");
                    return replaceStatement(prepedStatement, replaceTableName);
                }
                if(StringUtils.contains(servletPath,"getChildNode.do")){
                    String replaceTableName = (String) SessionUtil.getAttribute("getChildNode");
                    return replaceStatement(prepedStatement, replaceTableName);
                }
                if(StringUtils.contains(servletPath,"getChildNodeWithParent.do")){
                    String replaceTableName = (String) SessionUtil.getAttribute("getChildNodeWithParent");
                    return replaceStatement(prepedStatement, replaceTableName);
                }
                if(StringUtils.contains(servletPath,"addNode.do")){
                    String replaceTableName = (String) SessionUtil.getAttribute("addNode");
                    return replaceStatement(prepedStatement, replaceTableName);
                }
                if(StringUtils.contains(servletPath,"updateNode.do")){
                    String replaceTableName = (String) SessionUtil.getAttribute("updateNode");
                    return replaceStatement(prepedStatement, replaceTableName);
                }
                if(StringUtils.contains(servletPath,"moveNode.do")){
                    String replaceTableName = (String) SessionUtil.getAttribute("moveNode");
                    return replaceStatement(prepedStatement, replaceTableName);
                }
                if(StringUtils.contains(servletPath,"getHistory.do")){
                    String replaceTableName = (String) SessionUtil.getAttribute("getHistory");
                    return replaceStatement(prepedStatement, replaceTableName);
                }
            }
            logger.info("RouteTableInterceptor :: servletPath -> " + servletPath);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return prepedStatement;
    }

    private String replaceStatement(String prepedStatement, String replaceTableName) {
        logger.info("RouteTableInterceptor :: prepedStatement - before =>" + prepedStatement);
        logger.info("RouteTableInterceptor :: replaceTableName =>" + replaceTableName);
        if (StringUtils.isNotEmpty(replaceTableName)) {
            prepedStatement = prepedStatement.replaceAll("T_ARMS_REQADD", replaceTableName);
            logger.info("RouteTableInterceptor :: prepedStatement - after =>" + prepedStatement);
        }
        return prepedStatement;
    }

    public static String setArmsReplaceTableName(HttpServletRequest request, String tableName) throws Exception {
        if(StringUtils.equals(tableName, "T_ARMS_REQADD")){
            String servletPath = request.getServletPath();
            if(StringUtils.contains(servletPath,"T_ARMS_REQADD_")){
                if(StringUtils.contains(servletPath,"getMonitor.do")){
                    tableName = (String) SessionUtil.getAttribute("getMonitor");
                }
                if(StringUtils.contains(servletPath,"getNode.do")){
                    tableName = (String) SessionUtil.getAttribute("getNode");
                }
                if(StringUtils.contains(servletPath,"getChildNode.do")){
                    tableName = (String) SessionUtil.getAttribute("getChildNode");
                }
                if(StringUtils.contains(servletPath,"getChildNodeWithParent.do")){
                    tableName = (String) SessionUtil.getAttribute("getChildNodeWithParent");
                }
                if(StringUtils.contains(servletPath,"addNode.do")){
                    tableName = (String) SessionUtil.getAttribute("addNode");
                }
                if(StringUtils.contains(servletPath,"updateNode.do")){
                    tableName = (String) SessionUtil.getAttribute("updateNode");
                }
                if(StringUtils.contains(servletPath,"moveNode.do")){
                    tableName = (String) SessionUtil.getAttribute("moveNode");
                }
                if(StringUtils.contains(servletPath,"getHistory.do")){
                    tableName = (String) SessionUtil.getAttribute("getHistory");
                }
            }
        }
        return tableName;
    }
}