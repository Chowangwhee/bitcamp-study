package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import java.util.List;

public class BoardService {

    private BoardDao boardDao;

    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public List<Board> list() throws Exception {
        return boardDao.findAll();
    }
}
