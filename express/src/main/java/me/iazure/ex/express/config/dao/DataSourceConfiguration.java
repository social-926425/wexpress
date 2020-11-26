package me.iazure.ex.express.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import me.iazure.ex.express.unit.DESUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
@MapperScan("me.iazure.ex.express.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUserNmae;
    @Value("${jdbc.password}")
    private String jdbcPassWord;

    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(DESUtil.getDecryptString(jdbcDriver));
        dataSource.setJdbcUrl(DESUtil.getDecryptString(jdbcUrl));
        dataSource.setUser(DESUtil.getDecryptString(jdbcUserNmae));
        dataSource.setPassword(DESUtil.getDecryptString(jdbcPassWord));
        dataSource.setMaxPoolSize(30);
        dataSource.setMinPoolSize(10);
        dataSource.setAutoCommitOnClose(false);
        dataSource.setAcquireRetryAttempts(2);
        dataSource.setCheckoutTimeout(10000);
        return dataSource;
    }


}
