package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.vo.BoardPhoto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/delete-file")
public class BoardFileDeleteServlet extends HttpServlet {
    private BoardService boardService;
    private StorageService storageService;

    @Override
    public void init() {
        boardService = (BoardService) getServletContext().getAttribute("boardService");
        storageService = (StorageService) getServletContext().getAttribute("storageService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int fileNo = Integer.parseInt(request.getParameter("no"));

        try {
            BoardPhoto boardPhoto = boardService.getBoardPhoto(fileNo);
            if (boardPhoto == null) {
                throw new Exception("삭제하려는 파일이 존재하지 않습니다.");
            }
            storageService.delete(boardPhoto.getFilename());
            boardService.deletePhoto(fileNo);
            response.sendRedirect("/board/view?no=" + boardPhoto.getBoardNo());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e);
            request.setAttribute("message", "게시글 첨부파일 삭제 오류!");
            request.setAttribute("refresh", "2;url=/board/list");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
