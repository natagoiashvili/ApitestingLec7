
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.LOMBOKUserRequest;

public class PasswordTest {


    @Test
    public void createUserNegativePasswordTest() {

        // Body Lombok Builder-ით
        LOMBOKUserRequest newUser = LOMBOKUserRequest.builder()
                .userName("user" + System.currentTimeMillis())
                .password("test1234") // დაუშვებელი პაროლი
                .build();

        Response response = RestAssured
                .given()
                .baseUri("https://bookstore.toolsqa.com")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(newUser)
                .when()
                .post("/Account/v1/User")
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertEquals(
                response.jsonPath().getString("message"),
                "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), " +
                        "one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.");

        System.out.println("Message:" + response.jsonPath().getString("message"));

    }

    public class UserPositiveTest {

        @Test
        public void UserPositiveTest() {

            //Lombok Builder
            LOMBOKUserRequest requestBody = LOMBOKUserRequest
                    .builder()
                    .userName("user" + System.currentTimeMillis())
                    .password("Test1234!")
                    .build();
            Response response = RestAssured
                    .given()
                    .baseUri("https://bookstore.toolsqa.com")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body(requestBody)
                    .when()
                    .post("/Account/v1/User")
                    .then()
                    .extract()
                    .response();

            //
            String userId = response.jsonPath().getString("userID");
            System.out.println("user id is: " + userId);
            Assert.assertEquals(response.statusCode(), 201, "messige");
            Assert.assertNotNull(userId, "UserID is not null!");
        }
    }
}
