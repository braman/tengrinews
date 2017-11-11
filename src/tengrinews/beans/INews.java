package tengrinews.beans;

import java.util.List;

import javax.ejb.Local;

import tengrinews.dto.NewsDTO;

@Local
public interface INews {
    List<NewsDTO> getTop10News();
}
