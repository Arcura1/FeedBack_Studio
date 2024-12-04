package org.example.feedbackstudio.note.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PdfInfo")
public class PdfInfoEntity {
    @Id
    private String id;
    private String title;
    private String content;
    private String Url;
    private Number Xsize;
    private Number Ysize;
    private Number PageSize;

    @DBRef
    private HomeworkEntity homeworkEntity;

    public PdfInfoEntity() {

    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = (id != null) ? id : null;  // Null kontrolü
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = (title != null) ? title : null;  // Null kontrolü
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = (content != null) ? content : null;  // Null kontrolü
    }

    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = (url != null) ? url : null;  // Null kontrolü
    }

    public Number getXsize() {
        return Xsize;
    }
    public void setXsize(Number xsize) {
        Xsize = (xsize != null) ? xsize : null;  // Null kontrolü
    }

    public Number getYsize() {
        return Ysize;
    }
    public void setYsize(Number ysize) {
        Ysize = (ysize != null) ? ysize : null;  // Null kontrolü
    }

    public Number getPageSize() {
        return PageSize;
    }
    public void setPageSize(Number pageSize) {
        PageSize = (pageSize != null) ? pageSize : null;  // Null kontrolü
    }

    public HomeworkEntity getHomeworkEntity() {
        return homeworkEntity;
    }
    public void setHomeworkEntity(HomeworkEntity homeworkEntity) {
        this.homeworkEntity = (homeworkEntity != null) ? homeworkEntity : null;  // Null kontrolü
    }
}
