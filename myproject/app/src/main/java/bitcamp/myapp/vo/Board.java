package bitcamp.myapp.vo;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    private static final long serialVersionUID = 1L;

    private int no;
    private String title;
    private String content;
    private Date createDate;
    private int viewCount;
    private Member writer;
    private List<BoardPhoto> photos = new ArrayList<>();
    public Board() {}
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Member getWriter() {
        return writer;
    }

    public void setWriter(Member writer) {
        this.writer = writer;
    }

    public List<BoardPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<BoardPhoto> photos) {
        this.photos = photos;
    }

}
