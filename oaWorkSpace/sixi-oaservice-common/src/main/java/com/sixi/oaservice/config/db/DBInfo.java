package com.sixi.oaservice.config.db;

import lombok.Data;

/**
 * 数据库配置信息
 * @author suger
 *
 */
@Data
public class DBInfo {
	private String driver;
	private String url;
	private String databaseName;
	private String username;
	private String password;
}
