package tengrinews.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import tengrinews.beans.INewsLocal;
import tengrinews.dto.NewsDTO;

@Path("/news")
public class NewsResource {

    @EJB INewsLocal newsLocal;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/top10")
    public List<NewsDTO> getTop10News() {
        return newsLocal.getTop10News();
    }
    
    
    @POST
    @PUT
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String createNews(NewsDTO dto) {
        return newsLocal.createNews(dto);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public NewsDTO viewNews(Long id) {
        return newsLocal.viewNews(id);
    }
}
