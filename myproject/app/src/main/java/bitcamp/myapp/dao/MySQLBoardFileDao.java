package bitcamp.myapp.dao;

import bitcamp.myapp.vo.BoardPhoto;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLBoardFileDao implements BoardFileDao {

    private Connection con;
    private SqlSessionFactory sqlSessionFactory;

    public MySQLBoardFileDao(Connection con, SqlSessionFactory sqlSessionFactory) {
        this.con = con;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void insert(int boardId, List<BoardPhoto> photos) {
        String sql = "INSERT INTO ed_attach_file(board_id, filename) VALUES(?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (BoardPhoto photo : photos) {
                stmt.setInt(1, boardId);
                stmt.setString(2, photo.getFilename());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int photoNo) {
        String sql = "DELETE FROM ed_attach_file WHERE af_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, photoNo);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int insert(int boardNo, String filename) {
        String sql = "INSERT INTO ed_attach_file(board_id, filename) VALUES(?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, boardNo);
            stmt.setString(2, filename);
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

    @Override
    public List<BoardPhoto> findPhotosByBoardNo(int boardNo) {
        String sql = "SELECT af_id, filename FROM ed_attach_file WHERE board_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, boardNo);
            try (ResultSet rs = stmt.executeQuery()) {
                List<BoardPhoto> photos = new ArrayList<>();
                while (rs.next()) {
                    BoardPhoto photo = new BoardPhoto();
                    photo.setNo(rs.getInt("af_id"));
                    photo.setBoardNo(boardNo);
                    photo.setFilename(rs.getString("filename"));
                    photos.add(photo);
                }
                return photos;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public BoardPhoto findByNo(int fileNo) {
        String sql = "SELECT af_id, board_id, filename FROM ed_attach_file WHERE af_id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, fileNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                BoardPhoto photo = new BoardPhoto();
                photo.setNo(rs.getInt("af_id"));
                photo.setBoardNo(rs.getInt("board_id"));
                photo.setFilename(rs.getString("filename"));
                return photo;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}