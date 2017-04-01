package com.together.framework.service.base;

import com.together.framework.dao.common.SQLCondition;


/**
 * 基于JPA的业务逻辑层操作核心接口类<p>
 * @author LingMin 
 * @date 2014-06-17<br>
 * @version 1.0<br>
 * @param <E>  实体对象<br>
 * @param <ID> 实体关键字<br>
 */
@SuppressWarnings("rawtypes")
public interface ICoreBaseService <E, ID extends java.io.Serializable> extends org.springframework.context.ApplicationContextAware, org.springframework.web.context.ServletContextAware {
	/**
	 * 根据关键字获取指定的数据记录<p>
	 * @param key 关键字<br>
	 * @return 实体对象<br>
	 */
	public E get(final ID key);
	
	/**
	 * 新增指定的数据记录<p>
	 * @param entity 实体对象<br>
	 */
	public ID save(E entity);
	
	/**
	 * 根据给定的detached（游离状态）对象实例的标识更新对应的持久化实例<p>
	 * @param entity 游离状态对象<br>
	 */
	public ID update(E entity);
	
	/**
	 * 根据关键字删除指定的数据记录<p>
	 * @param key 关键字<br>
	 */
	public void delete(final ID key);
	
	/**
	 * 以JPQL的方式统计满足条件的数据记录数量<p>
	 * @param jpql JPQL语句<br>
	 * @return 数据记录数量<br>
	 */
    public int count(final String jpql);
	
	/**
     * 根据关键字与实体CLASS对象返回指定的对象<p>
     * @param clazz CLASS对象<br>
     * @param key 关键字<br>
     * @return 实体对象<br>
     */
	public Object get(Class clazz,ID key);
	
	/**
	 * 根据持久层对象获取对应的表名<p>
	 * @return 数据库表名<br>
	 */
	public String getTableNameFromEntity();
	
	/**
     * 根据条件查询记录的总条数<p>
     * @param condition 查询条件<br>
     * @return 查询结果集<p>
     */
    public int count(final SQLCondition condition, final String difference);
    
    /**
     * 根据条件批量删除指定数据记录<p>
     * @param condition 条件对象<br>
     */
    public void delete(final SQLCondition condition);
    
    /**
	 * 以JPA的方式执行数据库查询<p>
	 * @param condition 查询条件对象<br>
	 * @return 查询结果集<br>
	 */
	public java.util.List<E> list(SQLCondition condition);
	
	/**
	 * 以JPA的方式批量删除指定的实体对象集合<p>
	 * @param list 实体对象集合<br>
	 */
	public void batchDelete(final java.util.List<E> list);
	
	/**
	 * 以JPA的方式批量保存|更新实体对象集合<p>
	 * @param list 实体对象集合<br>
	 */
	public void batchSaveOrUpdate(java.util.List<E> list);
	
	/**
	 * 根据指定的关键字数组查询对应的对象集合<p>
	 * @param keys 关键字数组<br>
	 * @return 对象数组<br>
	 */
	public java.util.List<E> getEntityByKeys(final ID[] keys);
	
	/**
     * 以JPA的方式执行数据库查询，返回查询结果的关键字数组<p>
     * @param condition 查询条件对象<br>
     * @return 查询结果集<p>
     */
    public String[] getEntryObjectKeys(final SQLCondition condition);
    
    /**
	 * 判断当前指定编码在数据库中是否存在<p>
	 * @param key 关键字<br>
	 * @param number 编码<br>
	 * @return true:存在 false:不存在<br>
	 */
	public boolean isExistenceNumber(final ID key, final String number);
	
	 /**
	 * 判断当前指定字段 在数据库中是否存在<p>
	 */
	public boolean isExistenceField(final ID key, final String fieldName , final String value);
	/**
	 * 判断指定的结点下是否包含数据信息<p>
	 * @param groupFiled 分组字段名称<br>
	 * @param groupKey 分组字段关键字<br>
	 * @return true:包含 false:未包含<br>
	 */
	public boolean isExistenceRecord(String groupFiled, String groupKey);
    
    /**
     * 根据数据库类型及编码规则动态生成编码字符串<p>
     * @param dbtype 数据库类型<br>
     * @param ruler 编码规则对象<br>
     * @return 编码字符串<br>
     */
    //public String generateNextNumber(DatabaseEnum dbtype, NumberRuler ruler);
	
	/**
     * 判断当前关键字是否存在于指定表的指定列中<p>
     * @param table 表名<br>
     * @param field 列名<br>
     * @param key 关键字<br>
     * @return true:存在 false:不存在<br>
     */
    public boolean isExistenceInDBTable(String table, String field, String key);
    
	/**
     * 判断当前关键字是否存在于指定表的指定列中<p>
     * @param tables 表集合<br>
     * @param fields 列集合<br>
     * @param key 关键字<br>
     * @return true:存在 false:不存在<br>
     */
    public boolean isExistenceInDBTables(String[] tables, String[] fields, String key);
    
	/**
	 * 以JPA的方式执行带参数的数据库查询<p>
	 * @param jpql JPQL语句<br>
	 * @param param 参数<br>
	 * @return 查询结果集<br>
	 */
	public java.util.List<Object> findByJPQL(final String jpql, final Object... params);
	
    /**
	 * 以JPA分页的方式执行数据库查询<p>
	 * @param start 开始记录索引位置<br>
	 * @param limit 每页显示记录数量<br>
	 * @param condition 查询条件对象<br>
	 * @return 查询结果集合<br>
	 */
	public java.util.List<E> list(final int start, final int limit, final SQLCondition condition);
	
    /**
     * 根据条件查询数据并将指定的字段组成下拉列表信息<p>
     * @param condition 查询条件<br>
     * @param disField  显示字段<br>
     * @return 下拉列表信息数组<br>
     */
    public javax.faces.model.SelectItem[] getSelectItem(SQLCondition condition, String disField);
	
    /**
     * 将List数组转换为下拉列表信息数组<p>
     * @param list 实体对象集合<br>
     * @param disField 显示属性名称<br>
     * @return 下拉列表信息数组<br>
     */
	public javax.faces.model.SelectItem[] getSelectionFromList(java.util.List<E> list, String disField);
	
    /**
     * 以JPQL的方式查询分页数据信息<p>
	 * @param start 开始记录索引位置<br>
	 * @param limit 每页显示记录数量<br>
     * @param jpql JPQL语句<br>
     * @param params 查询条件<br>
     * @return 查询结果集合<br>
     */
    public java.util.List<Object> list(final int start, final int limit, final String jpql, final Object... params);
}