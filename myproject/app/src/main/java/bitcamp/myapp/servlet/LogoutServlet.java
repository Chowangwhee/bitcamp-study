package bitcamp.myapp.servlet;

import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getSession().invalidate();
            response.sendRedirect("/home");
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("exception", stringWriter.toString());   // jsp 파일로 오류 전달
            requestDispatcher.forward(request, response);   // 관련 요청, 응답 데이터 소멸처리
        }
    }


}
