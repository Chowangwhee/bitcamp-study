package bitcamp.myapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication // Gradle 에서 bootRun 작업을 수행할 때 실행시킬 클래스를 지정하는 어노테이션
@PropertySource("file:${user.home}/config/bitcamp-study.properties")
public class App {

  @Bean // Spring IoC 컨테이너는 이 메서드를 호출한 후 리턴된 객체를 메소드 이름으로 보관한다
  public DataSource dataSource(
          @Value("${jdbc.classname}") String driverClassName,
          @Value("${jdbc.url}") String url,
          @Value("${jdbc.username}") String username,
          @Value("${jdbc.password}") String password
  ) {
    System.out.println("dataSource() 호출 됨!");
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  public static void main(String[] args) {

    System.out.println("App 실행!");
    SpringApplication.run(App.class, args);

  }

}