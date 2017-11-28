package tengrinews.beans;

import java.util.List;

import javax.ejb.Local;

import tengrinews.dto.NewsDTO;

@Local
public interface INewsLocal {
    List<NewsDTO> getTop10News();
    void scheduleNews(Long newsId);
    String createNews(NewsDTO dto);
    NewsDTO viewNews(Long id);
}
