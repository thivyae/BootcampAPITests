package apitests;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrdersTests {

    String url = "http://localhost:3000/";
    String email = "test@test1.com";
    String password = "password";

    //post call validation
    @Test
    public void createCustomerSuccessfully() {
        RestAssured.baseURI = url;
        JSONObject body = new JSONObject();
        body.put("email", email);
        body.put("password", password);
        Response response = given().
                when().
                header("Content-Type", "application/json").
                body(body.toJSONString()).
                post("user/signup");
        System.out.println(response.asString());
        Assert.assertEquals("201", response.getStatusCode());
    }


}
