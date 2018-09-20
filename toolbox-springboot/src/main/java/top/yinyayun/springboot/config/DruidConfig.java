package top.yinyayun.springboot.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据源管理
 * 
 * @author yinyayun
 *
 */
@Configuration
@MapperScan(basePackages = "top.yinyayun.springboot.dao", sqlSessionFactoryRef = "sqlSessionFactory1")
public class DruidConfig {

	@Primary
	@Bean(name = "sqlSessionFactory1")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource1") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		Resource[] resources = new PathMatchingResourcePatternResolver()
				.getResources("classpath:top/yinyayun/springboot/dao/xml/*.xml");
		sqlSessionFactoryBean.setMapperLocations(resources);
		return sqlSessionFactoryBean.getObject();
	}

	@Primary
	@Bean(name = "transactionManager1")
	public DataSourceTransactionManager dataSourceTransactionManager() {
		return new DataSourceTransactionManager(druidDataSource());
	}

	@Primary
	@Bean(name = "dataSource1")
	@ConfigurationProperties(prefix = "spring.datasource.1")
	public DataSource druidDataSource() {
		DruidDataSource druid = new DruidDataSource();
		return druid;
	}

}
