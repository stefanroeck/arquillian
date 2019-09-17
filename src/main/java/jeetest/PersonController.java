package jeetest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/person")
@Stateless
public class PersonController {

    @Inject
    PersonService bean;

    @GET
    public List<Person> hello() {
        return bean.fetchAllPersons();
    }
}
