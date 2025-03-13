package bitcamp.myapp.vo;

import java.io.Serializable;

public class BoardPhoto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int no;
    private int boardNo;
    private String filename; // Changed from fileName to filename

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(int boardNo) {
        this.boardNo = boardNo;
    }

    public String getFilename() { // Changed from getFileName to getFilename
        return filename;
    }

    public void setFilename(String filename) { // Changed from setFileName to setFilename
        this.filename = filename;
    }
}