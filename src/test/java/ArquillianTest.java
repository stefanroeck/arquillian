import jeetest.Person;
import jeetest.PersonService;
import jeetest.RestEndpoint;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ArquillianTest {

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "jee.war")
                .addPackage(PersonService.class.getPackage().getName())
                //.addAsWebInfResource("jbossas-ds.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("web.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString());
        return war;
    }

    /*
@Deployment
   public static WebArchive getDeployment() {

  File[] files = Maven.resolver().loadPomFromFile("pom.xml")
  .importRuntimeDependencies().resolve().withTransitivity().asFile();

  WebArchive war = ShrinkWrap
  .create(WebArchive.class, "clienttest.war")
  .addAsLibraries(files)
  .addAsManifestResource("META-INF/beans.xml")
  .addAsResource("META-INF/persistence.xml");
  System.out.println(war.toString(true));
   return war;
  }


     */

    @Inject
    PersonService bean;


    @Test
    public void fetchPersonsFromBean(){
        assertEquals(2, bean.fetchAllPersons().size());
    }

    @Test
    public void fetchPersonsViaRest(){
        Client client = ClientBuilder.newClient();

        Response response = client.target("http://localhost:8181/jee/api/person").request(MediaType.APPLICATION_JSON_TYPE).get();

        System.out.println(response);
        assertEquals(200, response.getStatus());
    }

}
