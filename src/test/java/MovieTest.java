import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.Movie;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieTest extends BaseTest {
    /*
     *
     *  In the JUnit, we should create the test cases as loosely coupled as possible.
     *  However, in the case, i should pass the imdbId parameter between tests.
     *
     *  I have a lot of ideas to solve that but to find the best case is the issue here for me.
     *  I created a class named Helper to save necessary information about the Movie tests.
     *
     */
    @Test
    public void stage1_shouldHaveExpectedMovieInTheResponse(){
        final String expectedResult = "Harry Potter and the Sorcerer's Stone";
        Response response = requestSpecification
                .queryParam("s", "Harry Potter")
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        response.then()
                .assertThat()
                .body("Search.Title", not(isEmptyOrNullString()))
                .body("Search.Year", not(isEmptyOrNullString()))
                .body("Search", hasItems(hasEntry("Title", expectedResult)));


        // Extract the response as a movie class and set it to Movie kind List.
        List<Movie> movies = response.jsonPath().getList("Search", Movie.class);
        movieHelper.setMovieList(movies);
    }

    @Test
    public void stage2_shouldHaveMovieInformationInTheResponseByImdbID(){
        Movie movie = movieHelper.getMovieByTitle("Harry Potter and the Sorcerer's Stone");

        Response response = requestSpecification
                .queryParam("i", movie.getImdbId())
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();


        response.then()
                .assertThat()
                .body("Title", not(isEmptyOrNullString()))
                .body("Year", not(isEmptyOrNullString()))
                .body("Released", not(isEmptyOrNullString()));


    }
}
