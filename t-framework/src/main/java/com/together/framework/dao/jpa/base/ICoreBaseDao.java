package com.together.framework.dao.jpa.base;

import com.together.framework.dao.common.SQLCondition;

/***
 * 基于JPA的数据库操作核心接口类<p>
 * @author LingMin 
 * @date 2014-6-17<br>
 * @version 1.0<br>
 * @param <E>  数据库实体对象<br>
 * @param <ID> 数据库实体关键字<br>
 */
public interface ICoreBaseDao<E, ID extends java.io.Serializable> {

	/**
	 * 手动开启数据库事务模式<p>
	 */
	public void beginTransaction();
	
	/**
	 * 手动方式提交数据库事务<p>
	 */
	public void commitTransaction();
	
	/**
	 * 手动方式回滚数据库事务<p>
	 */
	public void rollbackTransaction();
	
	/**
	 * 获取持久层对象信息<p>
	 * @return 持久层对象信息<br>
	 */
	public Class<E> getEntityClass();
	
	/**
	 * 根据持久层对象获取对应的表名<p>
	 * @return 数据库表名<br>
	 */
	public String getTableNameFromEntity();
	
//	/**
//	 * 根据编码规则基于MYSQL数据库生成新的编码信息<p>
//	 * @param ruler 编码规则<br>
//	 * @return 编码字符串<br>
//	 */
//	public String generateNextNumberForMySQL(NumberRuler ruler);
//	
//	/**
//	 * 根据编码规则基于ORACLE数据库生成新的编码信息<p>
//	 * @param ruler 编码规则<br>
//	 * @return 编码字符串<br>
//	 */
//	public String generateNextNumberForOracle(NumberRuler ruler);
	
	/**
	 * 根据给定标识和实体类返回持久化对象的实例，如果没有符合条件的持久化对象实例则返回NULL<p>
	 * @param key 关键字<br>
	 * @return 持久化对象<br>
	 */
	public E get(ID key) throws org.springframework.dao.DataAccessException;
	
	/**
	 * 将自由状态（Transient）的对象（根据配置）生成一个标识并赋值，然后将其持久化<p>
	 * @param entity 游离状态对象<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
	public ID save(final E entity) throws org.springframework.dao.DataAccessException;
	
	/**
	 * 根据给定的detached（游离状态）对象实例的标识更新对应的持久化实例<p>
	 * @param entity 游离状态对象<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
	public ID update(final E entity) throws org.springframework.dao.DataAccessException;
	
    /**
     * 根据给定的detached（游离状态）对象实例的标识更新对应的持久化实例<p>
     * @param entity 游离状态对象<br>
     * @return 实体化对象关键字<br>
     * @throws org.springframework.dao.DataAccessException 异常<br>
     */
	public ID saveOrUpdate(E entity) throws org.springframework.dao.DataAccessException;
	
	/**
	 * 根据指定的游离状态对象从数据库中移除对应的持久化（persistent）对象实例<p>
	 * @param entity 游离状态对象<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
	public void delete(final E entity) throws org.springframework.dao.DataAccessException;
	
	/**
	 * 根据关键字删除指定的数据记录<p>
	 * @param key 关键字<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
	public void delete(final ID key) throws org.springframework.dao.DataAccessException;
	
	/***
	 * 根据实体class类型与关键字返回实体对象，如果没有符合条件的持久化对象实例则返回NULL<p>
	 * @param entityClass 实体class类型<br>
	 * @param key 关键字<br>
	 * @return 实体对象<br>
	 */
	public Object get(Class obj,ID key) throws org.springframework.dao.DataAccessException;
	
	/**
	 * 以JPQL的方式统计满足条件的数据记录数量<p>
	 * @param jpql JPQL语句<br>
	 * @return 数据记录数量<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
    public int count(final String jpql) throws org.springframework.dao.DataAccessException;
    
    /**
     * 根据条件查询记录的总条数<p>
     * @param condition 查询条件<br>
     * @return 查询结果集<p>
     * @throws org.springframework.dao.DataAccessException 异常<br>
     */
    public int count(final SQLCondition condition) throws org.springframework.dao.DataAccessException;
    
    /**
     * 根据条件批量删除指定数据记录<p>
     * @param condition 条件对象<br>
     * @throws org.springframework.dao.DataAccessException 异常<br>
     */
    public void delete(final SQLCondition condition) throws org.springframework.dao.DataAccessException;
	
	/**
	 * 以JPA的方式批量删除指定的实体对象集合<p>
	 * @param list 实体对象集合<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
	public void batchDelete(final java.util.List<E> list) throws org.springframework.dao.DataAccessException;
	
	/**
	 * 以JPA的方式批量保存|更新实体对象集合<p>
	 * @param list 实体对象集合<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
	public void batchSaveOrUpdate(final java.util.List<E> list) throws org.springframework.dao.DataAccessException;
	
	/**
	 * 以JPA的方式执行数据库查询<p>
	 * @param condition 查询条件对象<br>
	 * @return 查询结果集<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
	public java.util.List<E> list(final SQLCondition condition) throws org.springframework.dao.DataAccessException;
	
	/**
     * 以JPA的方式执行数据库查询，返回查询结果的关键字数组<p>
     * @param condition 查询条件对象<br>
     * @return 查询结果集<p>
     */
    public String[] getEntryObjectKeys(final SQLCondition condition) throws org.springframework.dao.DataAccessException;
    
	/**
	 * 以JPA的方式执行带参数的数据库查询<p>
	 * @param jpql JPQL语句<br>
	 * @param param 参数<br>
	 * @return 查询结果集<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
	public java.util.List<Object> findByJPQL(final String jpql, final Object... params) throws org.springframework.dao.DataAccessException;
	
    /**
	 * 以JPA分页的方式执行数据库查询<p>
	 * @param start 开始记录索引位置<br>
	 * @param limit 每页显示记录数量<br>
	 * @param condition 查询条件对象<br>
	 * @return 查询结果集合<br>
	 * @throws org.springframework.dao.DataAccessException 异常<br>
	 */
	public java.util.List<E> list(final int start, final int limit, final SQLCondition condition) throws org.springframework.dao.DataAccessException;
	
	
	/**
     * 判断当前关键字是否存在于指定表的指定列中<p>
     * @param table 表名<br>
     * @param field 列名<br>
     * @param key 关键字<br>
     * @return true:存在 false:不存在<br>
     * @throws org.springframework.dao.DataAccessException 异常<br>
     */
    public boolean isExistenceInDBTable(final String table, final String field, final String key) throws org.springframework.dao.DataAccessException;
    
    /**
     * 以JPQL的方式查询分页数据信息<p>
	 * @param start 开始记录索引位置<br>
	 * @param limit 每页显示记录数量<br>
     * @param jpql JPQL语句<br>
     * @param params 查询条件<br>
     * @return 查询结果集合<br>
     * @throws org.springframework.dao.DataAccessException 异常<br>
     */
    public java.util.List<Object> list(final int start, final int limit, final String jpql, final Object... params) throws org.springframework.dao.DataAccessException;
}
