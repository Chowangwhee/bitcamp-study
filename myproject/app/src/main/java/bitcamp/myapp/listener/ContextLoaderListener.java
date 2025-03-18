package bitcamp.myapp.listener;

import bitcamp.myapp.dao.*;
import bitcamp.myapp.service.*;
import bitcamp.transaction.TransactionProxyFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

    private Connection con;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Load database properties
            Properties dbProperties = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
                if (input == null) {
                    throw new RuntimeException("database.properties file not found in classpath.");
                }
                dbProperties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Error loading database.properties file.", e);
            }

            // 4. DB에 연결
            con = DriverManager.getConnection(
                    dbProperties.getProperty("db.url"),
                    dbProperties.getProperty("db.username"),
                    dbProperties.getProperty("db.password"));

            ServletContext ctx = sce.getServletContext();

            MemberDao memberDao = new DefaultMemberDao(con);
            BoardDao boardDao = new MySQLBoardDao(con);
            BoardFileDao boardFileDao = new MySQLBoardFileDao(con);

            TransactionProxyFactory transactionProxyFactory = new TransactionProxyFactory(con);

            DefaultMemberService memberService = new DefaultMemberService(memberDao);
            ctx.setAttribute("memberService", transactionProxyFactory.createProxy(memberService, MemberService.class));

            DefaultBoardService boardService = new DefaultBoardService(boardDao, boardFileDao, con);
            ctx.setAttribute("boardService", transactionProxyFactory.createProxy(boardService, BoardService.class));

            NCPObjectStorageService storageService = new NCPObjectStorageService();
            ctx.setAttribute("storageService", transactionProxyFactory.createProxy(storageService, StorageService.class));

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