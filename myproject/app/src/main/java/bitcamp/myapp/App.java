package bitcamp.myapp;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication // Gradle 에서 bootRun 작업을 수행할 때 실행시킬 클래스를 지정하는 어노테이션
@PropertySource("file:${user.home}/config/bitcamp-study.properties")
//@EnableTransactionManagement // @Transactional 어노테이션 활성화
@MapperScan("bitcamp.myapp.dao") // DAO 구현체 자동생성을 설정하는 방법 1: 어노테이션으로 설정하기
public class App {

//   DAO 구현체 자동 생성을 설정하는 방법 2: 자바 코드로 설정하기
//   - 다음 메서드를 통해 DAO 인터페이스의 구현체를 자동으로 생성해주는 객체를 준비한다
//  @Bean
//  public MapperScannerConfigurer mapperScannerConfigurer() {
//    MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//    mapperScannerConfigurer.setBasePackage("bitcamp.myapp.dao");
//    // 단, SQL 매퍼 파일(*Dao.xml)의 namespace 가 인터페이스의 Full-Qualified name 과 일치해야 한다
//    // 그리고 SQL id 와 인터페이스의 메서드 이름과 일치해야 한다
//    // 메서드의 파라미터 값이 두 개 이상일 경우, @Param 어노테이션으로 프로퍼티 이름을 명시해야 한다
//    return mapperScannerConfigurer;
//  }

  public static void main(String[] args) {

    System.out.println("App 실행!");
    SpringApplication.run(App.class, args);

  }

}