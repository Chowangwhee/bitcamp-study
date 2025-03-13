package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import java.util.List;

public class DefaultBoardService implements BoardService {

    private BoardDao boardDao;

    public DefaultBoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public List<Board> list() throws Exception {
        return boardDao.findAll();
    }

    public Board get(int no) throws Exception {
        return boardDao.findByNo(no);
    }

    public void updateViewCount(int no) throws Exception {
        boardDao.updateViewCount(no);
    }

    public void modify(Board board) throws Exception {
        boardDao.update(board);
    }

    public void remove(int no) throws Exception {
        boardDao.delete(no);
    }

    public void write(Board board) throws Exception {
        boardDao.insert(board);
    }
    public void deletePhoto(int photoNo) throws Exception {
        boardDao.deletePhoto(photoNo);
    }

    public int addPhoto(int boardNo, String filePath) throws Exception {
        return boardDao.insertPhoto(boardNo, filePath);
    }

}