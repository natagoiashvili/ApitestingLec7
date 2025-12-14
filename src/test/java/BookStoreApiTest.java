import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BookStoreApiTest {

    @DataProvider(name = "isbnDataProvider")
    public Object[][] createData() {
        return new Object[][]{
                {"9781449331818"},
                {"9781449337711"},
                {"9781449365035"},
                {"9781491904244"}
        };
    }

    //იყენებს DataProvider-ს
    @Test(dataProvider = "isbnDataProvider")
    public void testGetBookByISBN(String isbn) {

        Response response = given()
                .baseUri("https://bookstore.toolsqa.com")
                .queryParam("ISBN", isbn)
                .header("Accept", "application/json")
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .statusCode(200)
                .extract()

                .response();

        System.out.println("RESPONSE BODY: " + response.asPrettyString());
        String returnedIsbn = response.jsonPath().getString("isbn");
        Assert.assertEquals(returnedIsbn, isbn, "Returned isbn does not match request!");
    }
}