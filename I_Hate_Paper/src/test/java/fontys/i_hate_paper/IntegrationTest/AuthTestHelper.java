package fontys.i_hate_paper.IntegrationTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public final class AuthTestHelper {

    private AuthTestHelper() {}

    public static RequestSpecification loginAndBuildSpec(int port) {
        RestAssured.port = port;

        String username = "test_" + System.currentTimeMillis();
        String password = "Password123!";

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of("username", username, "password", password))
                .when()
                .post("/api/users")
                .then()
                .statusCode(200);

        var loginResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of("username", username, "password", password))
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(loginResponse.getCookies())
                .build();
    }
}

