package com.together.framework.dao.jpa.base.impl;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.together.common.CommonUtils;
import com.together.common.ReflectionUtils;
import com.together.common.StringUtils;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.dao.common.SQLCondition;
import com.together.framework.dao.jpa.base.ICoreBaseDao;

/**
 * 基于JPA的数据库操作核心接口实现类<p>
 * @author LingMin 
 * @date 2014-06-17<br>
 * @version 1.0<br>
 * @param <E>  数据库实体对象<br>
 * @param <ID> 数据库实体关键字<br>
 */
@SuppressWarnings({"unchecked","rawtypes"})
public abstract class CoreBaseDaoImpl<E, ID extends java.io.Serializable, DAOImpl extends ICoreBaseDao<E, ID>> implements ICoreBaseDao<E, ID> {
	/** 日志书写对象 **/
	protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
	/** JDBC数据库操作类 **/
	//@Resource(name="com.hgmk.framework.jdbcUtils")
	//protected IJDBCBaseDao jdbcTemplate;
	/** 持久化对象实体类 **/
	private java.lang.Class<E> persistentClass;
	/** 数据库实体管理器 **/
	@PersistenceContext
	protected javax.persistence.EntityManager entityManager;
	
	/**
	 * 手动开启数据库事务模式<p>
	 */
	public void beginTransaction() {
		entityManager.getTransaction().begin();
	}
	
	/**
	 * 手动方式提交数据库事务<p>
	 */
	public void commitTransaction() {
		entityManager.getTransaction().commit();
	}
	
	/**
	 * 手动方式回滚数据库事务<p>
	 */
	public void rollbackTransaction() {
		entityManager.getTransaction().rollback();
	}
	
	/**
	 * 获取泛型对应实体的Class对象<p>
	 * @return 泛型对应实体的Class对象<br>
	 */
	public Class<E> getEntityClass() {
		if (persistentClass == null) {
			java.lang.reflect.Type type = getClass().getGenericSuperclass();
			persistentClass = (Class<E>) ((java.lang.reflect.ParameterizedType) type).getActualTypeArguments()[0];
		}
		return persistentClass;
	}
	
	/**
	 * 根据实体对象的注入信息获取表名<p>
	 */
	public String getTableNameFromEntity() {
		return getEntityClass().getAnnotation(javax.persistence.Table.class).name();
	}
	
//	/**
//	 * 根据编码规则基于MYSQL数据库生成新的编码信息<p>
//	 */
//	public String generateNextNumberForMySQL(NumberRuler ruler) {
//		String rtnS = "";
//		// 合法性验证
//		if (CommonUtils.isNotEmptyObject(ruler)) {
//			String dateStr = ruler.isHasDate() ? DateUtils.getCurrentDateBySpecifiedFormatter("yyyyMMdd") : "";
//			// 查询当前数据表中是否包含当前日期的编码信息
//			int dateLen = dateStr.length();
//			StringBuffer JPQLBuf = new StringBuffer("SELECT COUNT(*) FROM ").append(getEntityClass().getName())
//					.append(" WHERE SUBSTRING(NUMBERS, LENGTH(NUMBERS) - ").append((ruler.getSerialLen() - 1) + dateLen)
//					.append(", ").append(dateLen).append(")").append(" = '").append(dateStr).append("'");
//			// 当编码存在时，则生成新编码，否则重新开始生成
//			if (count(JPQLBuf.toString()) > 0) {
//				JPQLBuf = new StringBuffer("SELECT MAX(CAST(RIGHT(NUMBERS, ").append(ruler.getSerialLen())
//						.append(") AS SIGNED))").append(" FROM ").append(getTableNameFromEntity())
//						.append(" WHERE SUBSTRING(NUMBERS, LENGTH(NUMBERS) - ").append((ruler.getSerialLen() - 1) + dateLen)
//						.append(", ").append(dateLen).append(")").append(" = '").append(dateStr).append("'");
//				int maxNum = (java.lang.Integer) jdbcTemplate.queryForInt(JPQLBuf.toString());
//				// 生成新编码
//				rtnS = StringUtils.getLegalString(ruler.getPrefix()).toUpperCase().concat(ruler.getSeprator())
//						.concat(StringUtils.isNotEmpty(dateStr) ? dateStr.concat(ruler.getSeprator()) : dateStr)
//						.concat(StringUtils.stringLeftPadding(String.valueOf(maxNum + 1),ruler.getSerialLen(), "0"));
//			} else {
//				rtnS = StringUtils.getLegalString(ruler.getPrefix()).toUpperCase().concat(ruler.getSeprator())
//						.concat(StringUtils.isNotEmpty(dateStr) ? dateStr.concat(ruler.getSeprator()) : dateStr)
//						.concat(StringUtils.stringLeftPadding("1",ruler.getSerialLen(), "0"));
//			}
//			logger.warn("the number of ".concat(getTableNameFromEntity()).concat("which is ").concat(rtnS).concat(" has been generated"));
//		}
//		return rtnS;
//	}
//	
//	/**
//	 * 根据编码规则基于ORACLE数据库生成新的编码信息<p>
//	 */
//	public String generateNextNumberForOracle(NumberRuler ruler) {
//		String rtnS = "";
//		// 合法性验证
//		if (CommonUtils.isNotEmptyObject(ruler)) {
//			String dateStr = ruler.isHasDate() ? DateUtils.getCurrentDateBySpecifiedFormatter("yyyyMMdd") : "";
//			// 查询当前数据表中是否包含当前日期的编码信息
//			int dateLen = dateStr.length();
//			StringBuffer JPQLBuf = new StringBuffer("SELECT COUNT(*) FROM ").append(getEntityClass().getName())
//					.append(" WHERE SUBSTR(NUMBERS, LENGTH(NUMBERS) - ").append((ruler.getSerialLen() - 1) + dateLen)
//					.append(", ").append(dateLen).append(")");
//			if (dateLen == 0) {
//				JPQLBuf.append(" IS NULL");
//			} else {
//				JPQLBuf.append(" = '").append(dateStr).append("'");
//			}
//			if (count(JPQLBuf.toString()) > 0) {
//				JPQLBuf = new StringBuffer("SELECT MAX(CAST(SUBSTR(NUMBERS, -").append(ruler.getSerialLen()).append(") AS NUMBER(8, 0)))")
//						.append(" FROM ").append(getTableNameFromEntity()).append(" WHERE SUBSTR(NUMBERS, LENGTH(NUMBERS) - ")
//						.append((ruler.getSerialLen() - 1) + dateLen).append(", ").append(dateLen).append(")");
//				if (dateLen == 0) {
//					JPQLBuf.append(" IS NULL");
//				} else {
//					JPQLBuf.append(" = '").append(dateStr).append("'");
//				}
//				int maxNum = (java.lang.Integer) jdbcTemplate.queryForInt(JPQLBuf.toString());
//				rtnS = StringUtils.getLegalString(ruler.getPrefix()).toUpperCase().concat(ruler.getSeprator())
//						.concat(StringUtils.isNotEmpty(dateStr) ? dateStr.concat(ruler.getSeprator()) : dateStr)
//						.concat(StringUtils.stringLeftPadding(String.valueOf(maxNum + 1),ruler.getSerialLen(), "0"));
//			} else {
//				rtnS = StringUtils.getLegalString(ruler.getPrefix()).toUpperCase().concat(ruler.getSeprator())
//						.concat(StringUtils.isNotEmpty(dateStr) ? dateStr.concat(ruler.getSeprator()) : dateStr)
//						.concat(StringUtils.stringLeftPadding("1",ruler.getSerialLen(), "0"));
//			}
//			logger.debug("the number of ".concat(getTableNameFromEntity()).concat("which is ").concat(rtnS).concat(" has been generated"));
//		}
//		return rtnS;
//	}
	
	/**
	 * 根据给定标识和实体类返回持久化对象的实例，如果没有符合条件的持久化对象实例则返回NULL<p>
	 */
	public E get(ID key) throws org.springframework.dao.DataAccessException {
		return entityManager.find(getEntityClass(), key);
	}
	
	/**
	 * 将自由状态（Transient）的对象（根据配置）生成一个标识并赋值，然后将其持久化<p>
	 */
	public ID save(final E entity) throws org.springframework.dao.DataAccessException {
		entityManager.persist(entity);
		// 获取关键字
		ID key = (ID) ReflectionUtils.getFieldValue(entity, "id");
		logger.warn("the entity name of ".concat(getEntityClass().getName()).concat(" which's key is ").concat(
		StringUtils.getLegalString((String)key)).concat(" has been saved or updated !"));
		return key;
	}
	
	/**
	 * 根据给定的detached（游离状态）对象实例的标识更新对应的持久化实例<p>
	 */
	public ID update(final E entity) throws org.springframework.dao.DataAccessException {
		entityManager.merge(entity);
		// 获取关键字
		ID key = (ID) ReflectionUtils.getFieldValue(entity, "id");
		logger.warn("the entity name of ".concat(getEntityClass().getName()).concat(" which's key is ").concat(
			StringUtils.getLegalString((String)key)).concat(" has been saved or updated !"));
		return key;
	}
	
	/**
	 * 根据给定的detached（游离状态）对象实例的标识更新对应的持久化实例<p>
	 */
	public ID saveOrUpdate(E entity) throws org.springframework.dao.DataAccessException {
		ID key = (ID) ReflectionUtils.getFieldValue(entity, "id");
		if (CommonUtils.isEmptyObject(key)) {
			key = save(entity);
		} else {
			key = update(entity);
		}
		return key;
	}
	
	/**
	 * 根据指定的游离状态对象从数据库中移除对应的持久化（persistent）对象实例<p>
	 */
	public void delete(final E t)throws org.springframework.dao.DataAccessException {
		entityManager.remove(entityManager.merge(t));
		entityManager.flush();
		entityManager.clear();
		logger.warn("the entity of ".concat(getEntityClass().getName()).concat(" which's key is ").concat(
			StringUtils.getLegalString((String) ReflectionUtils.getFieldValue(t, "id"))).concat(" has been deleted !"));
	}
	
	/**
	 * 根据关键字删除指定的数据记录<p>
	 */
	public void delete(final ID key) throws org.springframework.dao.DataAccessException {
		delete(get(key));
	}
	
	/**
	 * 根据实体class类型与关键字返回实体对象，如果没有符合条件的持久化对象实例则返回NULL<p>
	 */
	public Object get(Class obj, ID key) throws org.springframework.dao.DataAccessException {
		return entityManager.find(obj, key);
	}
	
	/**
	 * 以JPQL的方式统计满足条件的数据记录数量<p>
	 */
    public int count(final String jpql) throws org.springframework.dao.DataAccessException {
    	return ((java.lang.Long) findByJPQL(jpql).get(0)).intValue();
    }
	
    /**
     * 根据条件查询记录的总条数<p>
     */
    public int count(final SQLCondition condition) throws org.springframework.dao.DataAccessException {
    	StringBuffer JPQLBuf = new StringBuffer("SELECT COUNT(*) FROM ").append(getEntityClass().getName());
		if (CommonUtils.isNotEmptyObject(condition)) {
			JPQLBuf.append(condition.getSearchCondition());
		}
		return count(JPQLBuf.toString());
    }
    
    /**
     * 根据条件批量删除指定数据记录<p>
     */
    public void delete(final SQLCondition condition) throws org.springframework.dao.DataAccessException {
    	// 合法性验证
    	if (CommonUtils.isNotEmptyObject(condition)) {
			batchDelete(list(condition));
    	}
    }
	
	/**
	 * 以JPA的方式批量删除指定的实体对象集合<p>
	 */
	public void batchDelete(final java.util.List<E> list) throws org.springframework.dao.DataAccessException {
		// 当数据集合不为空时，执行批量保存
		if (CommonUtils.isNotEmptyList(list)) {
			ID key = null;
			for (int i = 0; i < list.size(); i ++) {
				key = (ID) ReflectionUtils.getFieldValue(list.get(i), "id");
				if (StringUtils.isNotEmpty((String) key)) {
					delete(key);
					logger.warn("the entity name of ".concat(getEntityClass().getName()).concat(" which's key is ")
						.concat(StringUtils.getLegalString((String) key)).concat(" has been deleted !"));
				}
				// 当数量达到上限时，分批提交事务
				if (i % FrameworkConstants.BATCH_SIZE == 0) {
					entityManager.flush();
					entityManager.clear();
				}
			}
		}
	}
	
	/**
	 * 以JPA的方式执行数据库查询，返回查询结果的关键字数组<p>
	 */
	public String[] getEntryObjectKeys(SQLCondition condition) throws org.springframework.dao.DataAccessException {
		String[] rtnKeyArray = null;
		// 合法性验证
		if (CommonUtils.isNotEmptyObject(condition)) {
			StringBuffer JPQLBuf = new StringBuffer("SELECT id FROM ").append(getEntityClass().getName());
			if (CommonUtils.isNotEmptyObject(condition)) {
				JPQLBuf.append(condition.toString());
			}
			// 执行数据库查询
			java.util.List<Object> resultList = findByJPQL(JPQLBuf.toString());
			if (CommonUtils.isNotEmptyList(resultList)) {
				rtnKeyArray = resultList.toArray(new String[0]);
			}
		}
		return rtnKeyArray;
	}
	
	/**
	 * 以JPA的方式批量保存|更新实体对象集合<p>
	 */
	public void batchSaveOrUpdate(final java.util.List<E> list) throws org.springframework.dao.DataAccessException {
		// 当数据集合不为空时，执行批量保存
		if (CommonUtils.isNotEmptyList(list)) {
			for (int i = 0; i < list.size(); i ++) {
				saveOrUpdate(list.get(i));
				// 当数量达到上限时，分批提交事务
				if (i != 0 && i % FrameworkConstants.BATCH_SIZE == 0) {
					entityManager.flush();
					entityManager.clear();
				}
			}
		}
	}
	
	/**
	 * 以JPA的方式执行数据库查询<p>
	 */
	public java.util.List<E> list(final SQLCondition condition) throws org.springframework.dao.DataAccessException {
		StringBuffer JPQLBuf = new StringBuffer("FROM ").append(getEntityClass().getName());
		// 查询条件的合法性验证
		if (CommonUtils.isNotEmptyObject(condition)) {
			JPQLBuf.append(condition.toString());
		}
		return (java.util.List<E>) findByJPQL(JPQLBuf.toString());
	}
	
	/**
	 * 以JPA的方式执行数据库查询<p>
	 */
	public java.util.List<Object> findByJPQL(final String jpql, final Object... params) throws org.springframework.dao.DataAccessException {
		java.util.List<Object> rtnList = null;
		// 合法性验证
		if (StringUtils.isNotEmpty(jpql)) {
			Query query = entityManager.createQuery(jpql);
			// 查询参数填充
			if (CommonUtils.isNotEmptyObject(params)) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i + 1, params[i]);
				}
			}
			rtnList = query.getResultList();
		}
		return rtnList;
	}
	
    /**
	 * 以JPA分页的方式执行数据库查询<p>
	 */
	public java.util.List<E> list(final int start, final int limit, final SQLCondition condition) throws org.springframework.dao.DataAccessException {
		StringBuffer JPQLBuf = new StringBuffer("FROM ").append(getEntityClass().getName());
		// 查询条件的合法性验证
		if (CommonUtils.isNotEmptyObject(condition)) {
			JPQLBuf.append(condition.toString());
		}
		return (java.util.List<E>) list(start, limit, JPQLBuf.toString());
	}
	
	/**
	 * 判断当前关键字是否存在于指定表的指定列中<p>
	 */
	public boolean isExistenceInDBTable(final String table, final String field, final String key) throws org.springframework.dao.DataAccessException {
		boolean rtnB = false;
		// 合法性验证
		if (StringUtils.isNotEmpty(table) && StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(key)) {
			// 查询语句拼接
			StringBuffer JPQLBuf = new StringBuffer(
					"SELECT COUNT(*) FROM ")
					.append(table.toUpperCase())
					.append(" WHERE ")
					.append(field.toUpperCase()).append(" = '")
					.append(key).append("'");
			Object resultObj = entityManager.createNativeQuery(JPQLBuf.toString()).getSingleResult();
			if(resultObj instanceof java.math.BigInteger){
				rtnB = ((java.math.BigInteger) resultObj).longValue() > 0;
			}else if(resultObj instanceof java.math.BigDecimal){
				rtnB = ((java.math.BigDecimal) resultObj).longValue() > 0;
			}
		}
		return rtnB;
	}
	
	/**
	 * 以JPQL的方式查询分页数据信息<p>
	 */
	public java.util.List<Object> list(final int start, final int limit, final String jpql, final Object... params) throws org.springframework.dao.DataAccessException {
		java.util.List<Object> rtnList = null;
		// 合法性验证
		if (StringUtils.isNotEmpty(jpql)) {
			Query query = entityManager.createQuery(jpql);
			// 查询参数填充
			if (CommonUtils.isNotEmptyObject(params)) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i + 1, params[i]);
				}
			}
			query.setFirstResult(start);
			query.setMaxResults(limit);
			rtnList = query.getResultList();
		}
		return rtnList;
	}
}