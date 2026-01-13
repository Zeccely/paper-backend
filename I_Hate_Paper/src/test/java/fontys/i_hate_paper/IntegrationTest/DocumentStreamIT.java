package fontys.i_hate_paper.IntegrationTest;

import static io.restassured.RestAssured.*;
import static java.util.Optional.empty;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

//TODO: thing that logs you in built in intellij
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentStreamIT {
    //BDD Behaviour driven development. Given this occurrence if I click this (when) I will get that (then)

    @LocalServerPort
    Integer serverPort;
    byte[] pdfBytes = "fake pdf content".getBytes();


    @BeforeEach
    void setup() {
        baseURI = "http://localhost";
        port = serverPort;
    }

    @Test
    public void Should_Send_Document_And_Reply_With_Document_Data(){

        given()
                .multiPart("file", "test.pdf", pdfBytes, "multipart/form-data")
                .multiPart("title", "Test doc")
                .multiPart("description", "hello")
                .multiPart("categoryName", "Bills")
                .multiPart("deadline", "2026-01-11")
                .when().post("/api/documents").then().statusCode(200)
                        .body("fileUUID", notNullValue())
                        .body("documentStoredName", notNullValue())
                        .body("title", equalTo("Test doc"))
                        .body("categoryName", equalToIgnoringCase("Bills"))
                        .body("is_Handled", equalTo(false));
    }

    @Test
    public void Should_Retrieve_All_Document_Data_List(){
        given()
                .when()
                .get("/api/documents")
                .then()
                .statusCode(200)
                .body("$", is(not(empty())))
                .body("$", isA(List.class));
    }

    @Test
    public void Should_Retrieve_Specific_Document_From_UUID(){

        String uuid =
        given()
                .multiPart("file", "test.pdf", pdfBytes, "multipart/form-data")
                .multiPart("title", "Test doc")
                .multiPart("description", "hello")
                .multiPart("categoryName", "Bills")
                .multiPart("deadline", "2026-01-11")
                .when().post("/api/documents").then().statusCode(200)
                .body("fileUUID", notNullValue())
                .body("documentStoredName", notNullValue())
                .body("title", equalTo("Test doc"))
                .body("categoryName", equalToIgnoringCase("Bills"))
                .body("is_Handled", equalTo(false))
                .extract().path("fileUUID");

        given()
                .when()
                .get("/api/documents/{uuid}",uuid)
                .then()
                .statusCode(200);

    }

}
