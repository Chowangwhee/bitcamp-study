package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardPhoto;
import bitcamp.myapp.vo.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLBoardDao implements BoardDao {

    private Connection con;

    public MySQLBoardDao(Connection con) {
        this.con = con;
    }

    @Override
    public List<Board> findAll() {
        String sql = "SELECT b.board_id, b.title, b.create_date, b.view_count, m.member_id, m.name " +
                "FROM ed_board b " +
                "JOIN ed_member m " +
                "ON b.member_id = m.member_id " +
                "ORDER BY b.board_id DESC";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            ArrayList<Board> list = new ArrayList<>();
            while (rs.next()) {
                Board board = new Board();
                board.setNo(rs.getInt("board_id"));
                board.setTitle(rs.getString("title"));
                board.setCreateDate(rs.getDate("create_date"));
                board.setViewCount(rs.getInt("view_count"));

                Member member = new Member();
                member.setNo(rs.getInt("member_id"));
                member.setName(rs.getString("name"));
                board.setWriter(member);
                list.add(board);
            }
            return list;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Board findByNo(int no) {
        String sql = "SELECT b.board_id, b.title, b.content, b.create_date, b.view_count, m.member_id, m.name " +
                "FROM ed_board b " +
                "JOIN ed_member m " +
                "ON b.member_id = m.member_id " +
                "WHERE b.board_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, no);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Board board = new Board();
                board.setNo(rs.getInt("board_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setCreateDate(rs.getDate("create_date"));
                board.setViewCount(rs.getInt("view_count"));

                Member member = new Member();
                member.setNo(rs.getInt("member_id"));
                member.setName(rs.getString("name"));
                board.setWriter(member);
                board.setPhotos(this.findPhotosByBoardNo(no));
                return board;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<BoardPhoto> findPhotosByBoardNo(int boardNo) {
        String sql = "SELECT af_id, filename FROM ed_attach_file WHERE board_id = ?"; // Changed to filename
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, boardNo);
            try (ResultSet rs = stmt.executeQuery()) {
                List<BoardPhoto> photos = new ArrayList<>();
                while (rs.next()) {
                    BoardPhoto photo = new BoardPhoto();
                    photo.setNo(rs.getInt("af_id"));
                    photo.setBoardNo(boardNo);
                    photo.setFilename(rs.getString("filename")); // Changed to filename
                    photos.add(photo);
                }
                return photos;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateViewCount(int no) {
        String sql = "UPDATE ed_board " +
                "SET view_count = view_count + 1 " +
                "WHERE board_id = " + no;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Board board) {
        String sql = "UPDATE ed_board " +
                "SET title = ?, content = ? " +
                "WHERE board_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, board.getTitle());
            stmt.setString(2, board.getContent());
            stmt.setInt(3, board.getNo());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int no) {
        String sql = "DELETE FROM ed_board WHERE board_id = " + no;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void insert(Board board) {
        String sql = String.format(
                "INSERT INTO ed_board(title, content, create_date, view_count, member_id) " +
                        "VALUES('%s', '%s', '%s', %d, %d)",
                board.getTitle(), board.getContent(),
                new Date(System.currentTimeMillis()), 0, board.getWriter().getNo()
        );
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            int boardId = 0;
            try (ResultSet rs = stmt.executeQuery("SELECT last_insert_id()")) {
                rs.next();
                boardId = rs.getInt(1);
            }
            if (board.getPhotos().size() > 0) {
                this.insertPhotos(boardId, board.getPhotos());
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void insertPhotos(int boardId, List<BoardPhoto> photos) {
        String sql = "INSERT INTO ed_attach_file(board_id, filename) VALUES(?, ?)"; // Changed to filename
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (BoardPhoto photo : photos) {
                stmt.setInt(1, boardId);
                stmt.setString(2, photo.getFilename()); // Changed to getFilename
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deletePhoto(int photoNo) {
        String sql = "DELETE FROM ed_attach_file WHERE af_id = " + photoNo;
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int insertPhoto(int boardNo, String filename) { // Changed to filename
        String sql = "INSERT INTO ed_attach_file(board_id, filename) VALUES(?, ?)"; // Changed to filename
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, boardNo);
            stmt.setString(2, filename); // Changed to filename
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserting photo failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}