package egovframework.com.ext.jstree.springiBatis.core.service;

import egovframework.com.ext.jstree.springiBatis.core.vo.ComprehensiveTree;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CoreService {

	public <T extends ComprehensiveTree> T getNode(T comprehensiveTree) throws Exception;

	public <T extends ComprehensiveTree> List<T> getChildNode(T comprehensiveTree) throws Exception;

	public <T extends ComprehensiveTree> List<String> searchNode(T comprehensiveTree) throws Exception;

	public <T extends ComprehensiveTree> T addNode(T comprehensiveTree) throws Exception;

	public <T extends ComprehensiveTree> int removeNode(T comprehensiveTree) throws Exception;

	public <T extends ComprehensiveTree> int alterNode(T comprehensiveTree) throws Exception;

	public <T extends ComprehensiveTree> int alterNodeType(T comprehensiveTree) throws Exception;

	public <T extends ComprehensiveTree> T moveNode(T comprehensiveTree, HttpServletRequest request) throws Exception;
}