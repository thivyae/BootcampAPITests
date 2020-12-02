package apitests;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;


public class OrdersTests {


    String url = "http://localhost:3000/";
    String email = "test@test1234.com";
    String password = "password";


    //post call validation
    @Test
    public void createCustomerSuccessfully() {


        JSONObject body = new JSONObject();
        body.put("email", email);
        body.put("password", password);
        Response response = given().
                baseUri(url).
                when().
                header("Content-Type", "application/json").
                body(body.toJSONString()).
                post("user/signup");
        System.out.println(response.asString());
        Assert.assertEquals(201, response.getStatusCode());
    }


    @Test
    public void verifyUserIsAbleToLoginAndCreateOrder() throws ParseException {
        String authToken = loginAndFetchAuthToken();
        String productId = "5c81088b25534400197f0648";
        int quantity = 1;
        addProductToCart(authToken, productId, quantity)
                .then()
                .assertThat().statusCode(201)
                .body("message", is("Order stored"))
                .body("createdOrder.products[0].productId", is(productId))
                .body("createdOrder.products[0].quantity", is(quantity));
    }

    @Test
    public void verifyUserIsAbleToGetDetailsOfAnOrder() {

    }

    private String loginAndFetchAuthToken() throws ParseException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        Response response = given()
                .baseUri(url)
                .when()
                .contentType(ContentType.JSON)
                .body(requestBody.toJSONString()).log().all()
                .post("user/login")
                .then()
                .log().all()
                .statusCode(200).extract().response();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.asString());
        String authToken = jsonObject.get("token").toString();
        System.out.println("authToken====================" + authToken);
        return authToken;
    }

    private Response addProductToCart(String token, String productId, int quantity) {
        JSONObject requestBody = new JSONObject();

        JSONArray productList = new JSONArray();

        JSONObject Product = new JSONObject();
        Product.put("productId", productId);
        Product.put("quantity", quantity);

        productList.add(Product);

        requestBody.put("products", productList);

        Response orderResponse = given().when()
                .baseUri(url)
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody.toJSONString())
                .log().all()
                .post("/orders/").then().log().all().extract().response();
        return orderResponse;
    }

}
