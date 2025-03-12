package bitcamp.myapp.servlet;

import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.vo.Member;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        MemberService memberService = (MemberService) getServletContext().getAttribute("memberService");
        Member member = memberService.get(email, password);
        if (member == null) {
            response.sendRedirect("/auth/login-form");
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/auth/login.jsp").include(request, response);
    }

}
