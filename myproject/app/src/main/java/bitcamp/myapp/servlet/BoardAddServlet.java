package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardPhoto;
import bitcamp.myapp.vo.Member;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50 * 5)
@WebServlet("/board/add")
public class BoardAddServlet extends HttpServlet {

    private StorageService storageService;
    private BoardService boardService;
    @Override
    public void init(){
        storageService = (StorageService) getServletContext().getAttribute("storageService");
        boardService = (BoardService) getServletContext().getAttribute("boardService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/board/form.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Board board = new Board();
            List<BoardPhoto> boardPhotos = new ArrayList<>();

            // 폼 데이터 처리
            board.setTitle(request.getParameter("title"));
            board.setContent(request.getParameter("content"));

            Member loginUser = (Member) request.getSession().getAttribute("loginUser");
            board.setWriter(loginUser);

            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (!part.getName().equals("files") || part.getSize() == 0) {
                    continue;
                }
                // 업로드 할 때 사용할 파일명 준비
                String filename = UUID.randomUUID().toString();
                try (InputStream fileIn = part.getInputStream()){
                    storageService.upload(filename, fileIn);
                    BoardPhoto boardPhoto = new BoardPhoto();
                    boardPhoto.setFilename(filename);
                    boardPhotos.add(boardPhoto);
                }
            }
            board.setPhotos(boardPhotos);
            try {
                boardService.write(board);
            } catch (Exception e) {
                for (BoardPhoto photo : boardPhotos) {
                    storageService.delete(photo.getFilename());
                }
            }
            response.sendRedirect("/home");

        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);

            javax.servlet.RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("exception", stringWriter.toString());   // jsp 파일로 오류 전달
            requestDispatcher.forward(request, response);   // 관련 요청, 응답 데이터 소멸처리
        }
    }
}