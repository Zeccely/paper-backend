package fontys.i_hate_paper.IntegrationTest;

import static io.restassured.RestAssured.*;
import static java.util.Optional.empty;
import static org.hamcrest.Matchers.*;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerIT {

    @LocalServerPort
    Integer serverPort;
    //RequestSpecification authSpec;


    @BeforeEach
    void setup() {
        baseURI = "http://localhost";
        port = serverPort;
        //authSpec = AuthTestHelper.loginAndBuildSpec(port);
    }

    @Test
    void Should_Get_All_Categories_List() {
        given()
                .when().get("/api/categories")
                .then()
                .statusCode(200)
                .body("$", isA(List.class))
                .body("$", is(not(empty())));
    }
}
