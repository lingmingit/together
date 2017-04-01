package com.together.framework.service.base.impl;


import javax.faces.model.SelectItem;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.together.common.CommonUtils;
import com.together.common.ReflectionUtils;
import com.together.common.StringUtils;
import com.together.framework.dao.common.ConditionParameter;
import com.together.framework.dao.common.SQLCondition;
import com.together.framework.dao.enums.CompareTypeEnum;
import com.together.framework.dao.jpa.base.ICoreBaseDao;
import com.together.framework.service.base.ICoreBaseService;

/**
 * 基于JPA的业务逻辑层操作核心接口实现类<p>
 * @author LingMin 
 * @date 2014-06-17<br>
 * @version 1.0<br>
 * @param <E>  实体对象<br>
 * @param <ID> 实体关键字<br>
 */
@SuppressWarnings({"rawtypes"})
public abstract class CoreBaseServiceImpl<E, ID extends java.io.Serializable> implements ICoreBaseService<E, ID> {
	/** JPA数据层操作对象 **/
	private ICoreBaseDao<E, ID> jpaBaseDao;
	/** ServletContext对象 **/
	protected javax.servlet.ServletContext servletContext;
	/** ApplicationContext对象 **/
	protected org.springframework.context.ApplicationContext applicationContext;
	
	/**
	 * 根据关键字获取指定的数据记录<p>
	 */
	public E get(final ID key) {
		return jpaBaseDao.get(key);
	}
	
	/**
	 * 新增指定的数据记录<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
   // @CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public ID save(E entity) {
		return jpaBaseDao.saveOrUpdate(entity);
	}
	
	/**
	 * 根据关键字与实体CLASS对象返回指定的对象<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public Object get(Class clazz,ID key) {
		return jpaBaseDao.get(clazz, key);
	}
	
	/**
	 * 根据给定的detached（游离状态）对象实例的标识更新对应的持久化实例<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
    //@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public ID update(E entity) {
		return jpaBaseDao.update(entity);
	}
	
	/**
	 * 根据关键字删除指定的数据记录<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
   // @CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void delete(final ID key) {
		jpaBaseDao.delete(key);
	}

	/**
	 * 以JPQL的方式统计满足条件的数据记录数量<p>
	 * @param jpql JPQL语句<br>
	 * @return 数据记录数量<br>
	 */
    public int count(final String jpql) {
    	return jpaBaseDao.count(jpql);
    }
    
	/**
	 * 根据持久层对象获取对应的表名<p>
	 */
	public String getTableNameFromEntity() {
		return jpaBaseDao.getTableNameFromEntity();
	}
	
    /**
     * 根据条件查询记录的总条数<p>
     * @param condition 查询条件<br>
     * @return 查询结果集<p>
     */
    public int count(final SQLCondition condition, final String difference) {
    	return jpaBaseDao.count(condition);
    }
    
    /**
     * 根据条件批量删除指定数据记录<p>
     */
    @Transactional(propagation=Propagation.REQUIRED)
    //@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
    public void delete(final SQLCondition condition) {
    	jpaBaseDao.delete(condition);
    }
    
    /**
	 * 以JPA的方式执行数据库查询<p>
	 */
	public java.util.List<E> list(SQLCondition condition) {
		return jpaBaseDao.list(condition);
	}
	
	/**
	 * 以JPA的方式批量删除指定的实体对象集合<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
    //@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void batchDelete(final java.util.List<E> list) {
		jpaBaseDao.batchDelete(list);
	}
	
	/**
	 * 以JPA的方式批量保存|更新实体对象集合<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
    //@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void batchSaveOrUpdate(java.util.List<E> list) {
		jpaBaseDao.batchSaveOrUpdate(list);
	}

	/**
	 * 根据指定的关键字数组查询对应的对象集合<p>
	 */
	public java.util.List<E> getEntityByKeys(final ID[] keys) {
		java.util.List<E> rtnList = null;
		// 合法性验证
		if (CommonUtils.isNotEmptyObjectArray(keys)) {
			// 初始化查询条件
			SQLCondition condition = new SQLCondition();
			condition.put(new ConditionParameter("id", keys, CompareTypeEnum.COMPARE_IN));
			rtnList = jpaBaseDao.list(condition);
		}
		return rtnList;
	}
	
	/**
     * 以JPA的方式执行数据库查询，返回查询结果的关键字数组<p>
     */
    public String[] getEntryObjectKeys(final SQLCondition condition) {
    	return jpaBaseDao.getEntryObjectKeys(condition);
    }
    
    
    /**
	 * 判断当前指定字段 在数据库中是否存在<p>
	 */
	public boolean isExistenceField(final ID key, final String fieldName , final String value) {
		boolean rtnB = false;
		// 合法性验证
		if (StringUtils.isNotEmpty(value) && ReflectionUtils.isExistFieldForDeep(jpaBaseDao.getEntityClass(), fieldName)) {
			SQLCondition condition = new SQLCondition();
			condition.put(new ConditionParameter(fieldName, value, CompareTypeEnum.COMPARE_EQUEAL));
			if (StringUtils.isNotEmpty((String) key)) {
				condition.put(new ConditionParameter("id", key, CompareTypeEnum.COMPARE_NOT_EQUEAL));
			}
			rtnB = count(condition, jpaBaseDao.getEntityClass().getName()) > 0;
		}
		return rtnB;
	}
	
	/**
	 * 判断当前指定编码在数据库中是否存在<p>
	 */
	public boolean isExistenceNumber(final ID key, final String number) {
		boolean rtnB = false;
		// 合法性验证
		if (StringUtils.isNotEmpty(number) && ReflectionUtils.isExistFieldForDeep(jpaBaseDao.getEntityClass(), "numbers")) {
			SQLCondition condition = new SQLCondition();
			condition.put(new ConditionParameter("numbers", number, CompareTypeEnum.COMPARE_EQUEAL));
			if (StringUtils.isNotEmpty((String) key)) {
				condition.put(new ConditionParameter("id", key, CompareTypeEnum.COMPARE_NOT_EQUEAL));
			}
			rtnB = count(condition, jpaBaseDao.getEntityClass().getName()) > 0;
		}
		return rtnB;
	}
	
	/**
	 * 判断指定的结点下是否包含数据信息<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public boolean isExistenceRecord(String groupFiled, String groupKey) {
		boolean rtnB = false;
		// 合法性验证
		if (StringUtils.isNotEmpty(groupFiled)) {
			SQLCondition condition = new SQLCondition();
			condition.put(new ConditionParameter(
				groupFiled.concat(".id"), groupKey, CompareTypeEnum.COMPARE_EQUEAL)
			);
			// 执行数据库查询
			rtnB = jpaBaseDao.count(condition) >= 1;
		}
		return rtnB;
	}
	
	/**
	 * 根据数据库类型及编码规则动态生成编码字符串<p>
	 */
//	public String generateNextNumber(DatabaseEnum dbtype, NumberRuler ruler) {
//		String rtnS = null;
//    	if (DatabaseEnum.DATABASE_MYSQL.equals(dbtype)) {
//    		rtnS = jpaBaseDao.generateNextNumberForMySQL(ruler);
//    	} else if (DatabaseEnum.DATABASE_ORACLE.equals(dbtype)) {
//    		rtnS = jpaBaseDao.generateNextNumberForOracle(ruler);
//    	}
//    	return rtnS;
//	}
	
	/**
	 * 判断当前关键字是否存在于指定表的指定列中<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public boolean isExistenceInDBTable(String table, String field, String id)  {
    	return jpaBaseDao.isExistenceInDBTable(table, field, id);
    }
	
	/**
     * 判断当前关键字是否存在于指定表的指定列中<p>
     */
	//@Cacheable(value="com.hgmk.hibernateCache")
    public boolean isExistenceInDBTables(String[] tables, String[] fields, String key)  {
    	boolean rtnB = false;
    	if (CommonUtils.isNotEmptyObjectArray(tables) && CommonUtils.isNotEmptyObjectArray(fields)
    			&& tables.length == fields.length && StringUtils.isNotEmpty(key)) {
    		for(int i = 0; i < tables.length; i ++) {
    			rtnB = isExistenceInDBTable(tables[i], fields[i], key);
    			if (rtnB) {
    				break;
    			}
    		}
    	}
    	return rtnB;
    }
    
    /**
	 * 以JPA的方式执行带参数的数据库查询<p>
	 * @param jpql JPQL语句<br>
	 * @param param 参数<br>
	 * @return 查询结果集<br>
	 */
    //@Cacheable(value="com.hgmk.hibernateCache")
	public java.util.List<Object> findByJPQL(final String jpql, final Object... params) {
		return jpaBaseDao.findByJPQL(jpql, params);
	}
    
    /**
	 * 以JPA分页的方式执行数据库查询<p>
	 */
	public java.util.List<E> list(final int start, final int limit, final SQLCondition condition) {
		return jpaBaseDao.list(start, limit, condition);
	}
	
	/**
	 * 以JPQL的方式查询分页数据信息<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public java.util.List<Object> list(final int start, final int limit, final String jpql, final Object... params) {
		return jpaBaseDao.list(start, limit, jpql, params);
	}
	
	/**
     * 根据条件查询数据并将指定的字段组成下拉列表信息<p>
     */
    public javax.faces.model.SelectItem[] getSelectItem(SQLCondition condition, String disField) {
    	return getSelectionFromList(list(condition), disField);
    }
	
    /**
     * 将List数组转换为下拉列表信息数组<p>
     */
    public SelectItem[] getSelectionFromList(java.util.List<E> list, String disField) {
    	java.util.List<SelectItem> itemList = new java.util.ArrayList<SelectItem>();;
		// 合法性验证
		if (CommonUtils.isNotEmptyList(list) && StringUtils.isNotEmpty(disField)) {
			// 循环组装下拉信息
			for (E record : list) {
				// 真实值
				String value = (String) ReflectionUtils.invokeMethod(record, "get".concat(StringUtils.firstCharToUpperCase("id")), null, null);
				// 显示值
				String label = "";
				if (ReflectionUtils.isExistFieldForDeep(jpaBaseDao.getEntityClass(), disField)) {
					label = (String) ReflectionUtils.invokeMethod(record, "get".concat(StringUtils.firstCharToUpperCase(disField)), null, null);
				}
				// 描  述
				String description = "";
				if (ReflectionUtils.isExistFieldForDeep(jpaBaseDao.getEntityClass(), "description")) {
					description = (String) ReflectionUtils.invokeMethod(record, "get".concat(StringUtils.firstCharToUpperCase("description")), null, null);
				}
				// 组装下拉信息
				itemList.add(new SelectItem(StringUtils.getLegalString(value), StringUtils.getLegalString(label), StringUtils.getLegalString(description)));
			}
		}
		return itemList.toArray(new javax.faces.model.SelectItem[0]);
    }
	
	/**
	 * 构造函数:初始化数据库层核心操作对象<p>
	 * @param jpaBaseDao 数据库层核心操作对象<br>
	 */
	public CoreBaseServiceImpl(ICoreBaseDao<E, ID> jpaBaseDao) {
		this.jpaBaseDao = jpaBaseDao;
	}
	
	/**
	 * 根据BEAN配置名与CLASS名获取SPRING中的BEAN对象<p>
	 * @param BEAN配置名<br>
	 * @return BEAN对象<br>
	 */
	public Object getSpringBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * 根据BEAN的CLASS对象获取SPRING中的BEAN对象<p>
	 */
	public Object getSpringBean(Class<?> beanClass) {
		return BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, beanClass);
	}
	
	/**
	 * 设置WEB层的上下文环境<p>
	 */
	public void setServletContext(javax.servlet.ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 设置SPRING的上下文环境<p>
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
