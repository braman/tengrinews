package tengrinews.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.NoSuchEntityException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import tengrinews.annotation.Loggable;
import tengrinews.aop.LoggerInterceptor;
import tengrinews.dto.NewsDTO;
import tengrinews.entity.NewsEntity;

@Stateless
@Path("/news")
@Loggable(true)
//@WebService
@SecurityDomain("tengrinews")
@Interceptors({LoggerInterceptor.class})
public class NewsBean implements INews {

    static List<NewsDTO> someNews = new ArrayList<NewsDTO>();
    
    static {
        someNews.add(new NewsDTO(1L, "test1", "test-desc-1"));
        someNews.add(new NewsDTO(2L, "test2", "test-desc-2"));
    }
    
    
    Logger log = Logger.getLogger(getClass());
    
    @EJB INews itself;
    
    @Resource
    SessionContext ctx;
    
    
    @PersistenceContext
    EntityManager em;
    
    @Resource
    private TimerService timerService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/top10")
    @RolesAllowed("role_top10news")
    public List<NewsDTO> getTop10News() {
        String username = Optional.ofNullable(ctx.getCallerPrincipal()).map(p -> p.getName()).orElse("unknown");

        log.info("username is " + username);
        
        return someNews;
    }
    
    @POST
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    @RolesAllowed("role_create_news")
    public String createNews(NewsDTO dto) {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setTitle(dto.getTitle());
        newsEntity.setDescription(dto.getDescription());
        
        em.persist(newsEntity);
        
        return "OK";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed("role_view_news")
    public NewsDTO viewNews(Long id) {
        Query q = em.createQuery("From NewsEntity n WHERE n.id=?1")
            .setParameter(1, id);
        
        try {
            NewsEntity entity = (NewsEntity) q.getSingleResult();
            NewsDTO dto = new NewsDTO();
            dto.setNewsId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            
            return dto;
        } catch (NoSuchEntityException e) {
            log.error("news with id " + id + " was not found!");
        }
        
        return null;
    }
    
    @Override
    @RolesAllowed({"can_schedule_news", "can_publish_news"})
    @WebMethod
    public void scheduleNews(Long newsId) {
        TimerConfig tf = new TimerConfig(newsId, false);
        timerService.createIntervalTimer(new Date(), 3000, tf);
    }
    
    @Timeout
    public void onTimeout(Timer timer) {
        log.info("new event with newsId = " + timer.getInfo());
    }
    
}
