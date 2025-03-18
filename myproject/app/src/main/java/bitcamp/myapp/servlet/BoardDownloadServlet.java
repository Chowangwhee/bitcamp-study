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

@WebServlet("/board/download")
public class BoardDownloadServlet extends HttpServlet {
    private StorageService storageService;
    private BoardService boardService;

    @Override
    public void init() throws ServletException {
        storageService = (StorageService) getServletContext().getAttribute("storageService");
        boardService = (BoardService) getServletContext().getAttribute("boardService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int fileNo = Integer.parseInt(request.getParameter("fileNo"));

        try {
            BoardPhoto boardPhoto = boardService.getBoardPhoto(fileNo);

            if (boardPhoto == null) {
                throw new Exception("첨부파일이 존재하지 않습니다!");
            }

            String filename = boardPhoto.getFilename();
            String contentType = request.getServletContext().getMimeType(filename);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            storageService.download(filename, response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e);
            request.setAttribute("message", "첨부파일 다운로드 오류!");
            request.setAttribute("refresh", "2;url=/board/list");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}