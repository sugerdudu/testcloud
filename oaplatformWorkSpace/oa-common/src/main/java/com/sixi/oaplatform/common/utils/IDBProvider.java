package com.sixi.oaplatform.common.utils;

import java.util.List;
import java.util.Map;

/**
 * DB封装接口
 * @author suger
 *
 * @param <T>
 */
public interface IDBProvider {

	/**
	 * 获取单个对象
	 * @param sql
	 * @param params
	 * @param cla
	 * @return
	 */
	public <T> T getInfo(String sql, Map<String, Object> params, Class<T> cla);

	public Map<String, Object> getInfo(String sql, Map<String, Object> paramMap);

	public <T> List<T> getList(String sql, Map<String, Object> paramMap, Class<T> elementType);
	public List<Map<String, Object>> getList(String sql, Map<String, Object> paramMap);
}
