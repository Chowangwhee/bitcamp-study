package bitcamp.myapp.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Hello")
public class HelloServlet implements Servlet {

    private ServletConfig config;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.config = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.setContentType("text/html; charset=UTF-8");
        PrintWriter out = servletResponse.getWriter();
        out.println("<html><body><h1>HAHAHAHA</h1></body></html>");
    }

    @Override
    public String getServletInfo() {
        return "HelloServlet!!!";
    }

    @Override
    public void destroy() {

    }
}
