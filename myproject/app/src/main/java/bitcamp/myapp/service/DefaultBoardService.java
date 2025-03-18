package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardPhoto;
import bitcamp.transaction.Transactional;

import java.sql.Connection;
import java.util.List;

public class DefaultBoardService implements BoardService {

    private BoardDao boardDao;
    private BoardFileDao boardFileDao;
    private Connection con;

    public DefaultBoardService(BoardDao boardDao, BoardFileDao boardFileDao, Connection con) {
        this.boardDao = boardDao;
        this.boardFileDao = boardFileDao;
        this.con = con;
    }

    @Override
    public List<Board> list() {
        return boardDao.findAll();
    }

    @Override
    public Board get(int no) {
        Board board = boardDao.findByNo(no);
        if (board != null) {
            board.setPhotos(boardFileDao.findPhotosByBoardNo(no));
        }
        return board;
    }

    @Transactional
    @Override
    public void updateViewCount(int no) {
        boardDao.updateViewCount(no);
    }

    @Transactional
    @Override
    public void modify(Board board) {
        boardDao.update(board);
    }

    @Transactional
    @Override
    public void remove(int no) {
        boardDao.delete(no);
    }

    @Transactional
    @Override
    public void write(Board board) {
        int boardId = boardDao.insert(board); // Get the generated boardId
        if (!board.getPhotos().isEmpty()) {
            boardFileDao.insert(boardId, board.getPhotos());
        }
    }

    @Transactional
    @Override
    public void deletePhoto(int photoNo) {
        boardFileDao.delete(photoNo);
    }

    @Override
    public int addPhoto(int boardNo, String filename) {
        return boardFileDao.insert(boardNo, filename);
    }

    @Override
    public BoardPhoto getBoardPhoto(int fileNo) {
        return boardFileDao.findByNo(fileNo);
    }
}