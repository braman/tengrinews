package tengrinews.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import tengrinews.dto.NewsDTO;

@Stateless
@Path("/news")
public class NewsBean implements INews {

    static List<NewsDTO> someNews = new ArrayList<NewsDTO>();
    
    static {
        someNews.add(new NewsDTO("test1", "test-desc-1"));
        someNews.add(new NewsDTO("test2", "test-desc-2"));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/top10")
    public List<NewsDTO> getTop10News() {
        return someNews;
    }
    
}
