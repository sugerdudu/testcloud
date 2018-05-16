package com.sixi.oaservice.config.db;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * dyy sqlserver 数据库配置
 * @author suger
 *
 */
@Configuration
public class DBInit_DYYSQLServer {
	private final String MapperPath="classpath*:com/sixi/oaservice/mapper/dyysqlserver/**/*.xml";
	private final String DataSourceBean="dataSource_dyysqlserver";
	private final String SQLSessionFactoryBean="sqlSessionFactory_dyysqlserver";
	private final String PackageName="com.sixi.oaservice.mapper.dyysqlserver";
	private final String txStr = "dyySqlServer";

	/**
	 * 初始数据库
	 * @param dbinfo
	 * @return
	 */
	@Bean(DataSourceBean)
	//@Profile("dev")
	public DataSource dataSource(DBConfigInfo dbinfo){
		System.out.println("init bean:"+DataSourceBean);
		return DBConfigUtils.getDruid(dbinfo.getDyysqlserver());
	}
	
	/**
	 * 指定 mybaties mapper 的sql配置
	 * @param driver
	 * @return
	 */
	@Bean(SQLSessionFactoryBean)
	public SqlSessionFactoryBean sqlSessionFactory(@Qualifier(DataSourceBean) DataSource driver){
		System.out.println(SQLSessionFactoryBean);
		return DBConfigUtils.getSQLSessionFactory(driver, MapperPath);
	}
	
	/**
	 * 指定扫描 mybaties mapper 的包名
	 * @return
	 * @throws Exception
	 */
	@Bean("MapperScanner_"+SQLSessionFactoryBean)
	public MapperScannerConfigurer mapperScannerConfigurer() throws Exception{
		System.out.println("MapperScanner_"+SQLSessionFactoryBean);
		return DBConfigUtils.getMapperScannerConfigurer(SQLSessionFactoryBean, PackageName);
	}

	/**
	 *事物指定数据库
	 * @param dataSource
	 * @return
	 */
	@Bean(txStr)
	public PlatformTransactionManager txManager(@Qualifier(DataSourceBean) DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
