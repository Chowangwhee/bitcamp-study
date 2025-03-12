package bitcamp.myapp.listener;

import bitcamp.myapp.dao.MemberDao;
import bitcamp.myapp.service.MemberService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

    private Connection con;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // 1. JDBC Driver 로딩(java.sql.Driver 구현체 로딩)
//            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Driver 구현 객체 생성
//            Driver driver = new com.mysql.jdbc.Driver();

            // 3. Driver 객체를 JDBC 드라이버 관리자에 등록
//            DriverManager.registerDriver(driver);

            // com.mysql.cj.jdbc.Driver 클래스의 역할에 포함되기 때문에 생략 가능

            // 4. DB에 연결
            con = DriverManager.getConnection(
                    "jdbc:mysql://db-32lmqb-kr.vpc-pub-cdb.ntruss.com:3306/bitcamp",
                    "student",
                    "bitcamp123!@#");

            ServletContext ctx = sce.getServletContext();
            MemberDao memberDao = new MemberDao(con);
            MemberService memberService = new MemberService(memberDao);
            ctx.setAttribute("memberService", memberService);

            System.out.println("웹 애플리케이션 실행환경 준비 완료!");

        } catch (Exception e) {
            System.out.println("웹 애플리케이션 실행환경 준비 중 오류 발생!");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println("웹 애플리케이션 실행환경 종료 중 오류 발생!");
            e.printStackTrace();
        }
    }
}
