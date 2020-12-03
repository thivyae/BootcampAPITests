package apitests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import mocks.WiremockHelper;
import org.junit.jupiter.api.BeforeAll;


public class BaseTest {

    static WiremockHelper wireMockServer = null;
    static RequestSpecification requestSpecification;


    @BeforeAll
    public static void createRequestSpecification() {
        wireMockServer = new WiremockHelper(38081);
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://localhost:3000/")
                .build();
    }
}
