package com.example.works;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class WorksResourceTest {

  @Test
  void testWorksSubEndpoint() {
    given()
            .when().get("/works/sub")
            .then()
            .statusCode(200)
            .body(is("Hello from Quarkus REST"));
  }

}
