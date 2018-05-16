package com.sixi.oaservice.config.db;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库配置信息
 * @author suger
 *
 */
@Component
@Data
@ConfigurationProperties(prefix = "dbconfig")
public class DBConfigInfo {
	private DBInfo oamysql;
	private DBInfo oasqlserver;
	private DBInfo dyysqlserver;
	private DBInfo alidatasqlserver;
	private DBInfo oapg;
	private DBInfo cwpg;
	private DBInfo cwmysql;
}
