package bitcamp.myapp.servlet;

import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String saveEmail = request.getParameter("saveEmail");

            MemberService memberService = (MemberService) getServletContext().getAttribute("memberService");
            Member member = memberService.get(email, password);
            if (member == null) {
                response.sendRedirect("/auth/login-form");
                return;
            }
            if (saveEmail != null) {
                Cookie emailCookie = new Cookie("email", email);
                emailCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(emailCookie);
            } else {
                Cookie emailCookie = new Cookie("email", "");
                emailCookie.setMaxAge(0);
                response.addCookie(emailCookie);
            }
            request.getSession().setAttribute("loginUser", member);
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
