import helpers.MovieHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;

import java.util.Properties;

public class BaseTest {
    public RequestSpecification requestSpecification;
    public MovieHelper movieHelper;

    private void init() throws Exception {
        Properties props = new Properties();
        props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        RestAssured.baseURI = props.getProperty("api.uri");
        RestAssured.port = Integer.valueOf(props.getProperty("api.port"));
        requestSpecification = RestAssured.given().contentType(ContentType.JSON)
                .headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                .queryParam("apikey", props.getProperty("api.key"));
        movieHelper = new MovieHelper();
    }

    @Before
    public void setUp() throws Exception {
        init();
    }


    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(500);
    }
}
