package tengrinews.dto;

import java.io.Serializable;

public class NewsDTO implements Serializable {

    private Long newsId;
    private String title;
    private String description;
    
    
    public NewsDTO() {}
    public NewsDTO(Long newsId, String title, String description) {
        super();
        this.newsId = newsId;
        this.title = title;
        this.description = description;
    }

    public Long getNewsId() {
        return newsId;
    }
    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
