package tengrinews.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.logging.Logger;

import tengrinews.annotation.Loggable;
import tengrinews.aop.LoggerInterceptor;
import tengrinews.dto.NewsDTO;
import tengrinews.entity.NewsEntity;

@Stateless
@Loggable(true)
@SecurityDomain("tengrinews")
@Interceptors({LoggerInterceptor.class})
public class NewsBean implements INewsLocal {

    static List<NewsDTO> someNews = new ArrayList<NewsDTO>();
    
    static {
        someNews.add(new NewsDTO(1L, "test1", "test-desc-1"));
        someNews.add(new NewsDTO(2L, "test2", "test-desc-2"));
    }
    
    
    Logger log = Logger.getLogger(getClass());
    
    @EJB INewsLocal itself;
    
    @Resource
    SessionContext ctx;
    
    
    @PersistenceContext
    EntityManager em;
    
    @Resource
    private TimerService timerService;
    
//    @RolesAllowed({"role_top10news"})
    @PermitAll
    public List<NewsDTO> getTop10News() {
        String username = Optional.ofNullable(ctx.getCallerPrincipal()).map(p -> p.getName()).orElse("unknown");

        log.info("username is " + username);
        
        return someNews;
    }
    
    @RolesAllowed("role_create_news")
    public String createNews(NewsDTO dto) {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setTitle(dto.getTitle());
        newsEntity.setDescription(dto.getDescription());
        
        em.persist(newsEntity);
        
        return "OK";
    }

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
    public void scheduleNews(Long newsId) {
        TimerConfig tf = new TimerConfig(newsId, false);
        timerService.createIntervalTimer(new Date(), 3000, tf);
    }
    
    @Timeout
    public void onTimeout(Timer timer) {
        log.info("new event with newsId = " + timer.getInfo());
    }
    
}
