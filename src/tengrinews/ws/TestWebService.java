package tengrinews.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import tengrinews.beans.INewsLocal;
import tengrinews.dto.NewsDTO;

@WebService(serviceName = "TestService")
public class TestWebService {

    @EJB INewsLocal newsLocal;
    
    @WebMethod
    public String sayHello(@WebParam(name = "name") String name) {
        return "Hello, " + name + "!";
    }
    
    @WebMethod
    public List<NewsDTO> getTop10News() {
        return newsLocal.getTop10News();
    }

}
