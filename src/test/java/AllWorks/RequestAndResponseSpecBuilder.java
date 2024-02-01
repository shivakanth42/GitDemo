package AllWorks;


import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import files.payload;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class RequestAndResponseSpecBuilder {
	
	@Test
	public static void RSpecification() {
		
		//Creating RequestSpecBuilder object and storing required data into it
		RequestSpecification reqspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").
		setContentType(ContentType.JSON).build();
		
		//Calling above created object in given method with "spec()" method
		String jsonreq = given().spec(reqspec).body(payload.AddPlace()).
		when().post("/maps/api/place/add/json").
		then().log().all().assertThat().statusCode(200).contentType(ContentType.JSON).extract().response().asString();
		
		System.out.println(jsonreq);
		JsonPath js = new JsonPath(jsonreq);
		String placeId = js.get("place_id");
		
		//Getting json response for the added place using spec() method and placeid queryparameter, in given
		given().spec(reqspec).queryParam("place_id", placeId).
		when().get("/maps/api/place/get/json").
		then().log().all().assertThat().statusCode(200);
		
		
		

	
		
	}

}
