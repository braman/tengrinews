package tengrinews.dto;

import java.io.Serializable;

public class NewsDTO implements Serializable {

    private String title;
    private String description;
    
    
    public NewsDTO() {}
    public NewsDTO(String title, String description) {
        super();
        this.title = title;
        this.description = description;
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
