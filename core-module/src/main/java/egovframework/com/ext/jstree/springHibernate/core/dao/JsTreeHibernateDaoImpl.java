package egovframework.com.ext.jstree.springHibernate.core.dao;

import egovframework.com.ext.jstree.springHibernate.core.vo.JsTreeHibernateSearchDTO;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@SuppressWarnings("unchecked")
@Repository("jsTreeHibernateDao")
public class JsTreeHibernateDaoImpl<T extends JsTreeHibernateSearchDTO> extends
		JsTreeHibernateAbstractDao<T, Serializable> implements JsTreeHibernateDao<T, Serializable>{

	private Class<T> clazz;

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazzToSet) {
		this.clazz = clazzToSet;
	}

	@Override
	protected Class<T> getEntityClass() {
		return getClazz();
	}
}
