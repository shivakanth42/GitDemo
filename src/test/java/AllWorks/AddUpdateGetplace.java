package AllWorks;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;


public class AddUpdateGetplace {


	static String id = "";
	static String newAddress = "";

	//Add Place
	@Test(priority = 1)
	public static void AddPlace() {

		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String res = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.AddPlace())
				.when().post("maps/api/place/add/json").
				then().log().all().assertThat().statusCode(200).extract().response().asString();


		JsonPath js = new JsonPath(res);

		id = js.getString("place_id");
		System.out.println(id);
	}


	//Update Place
	@Test(priority = 2)
	public static void UpdatePlace() {
		newAddress = "Steet 6, CalF, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+id+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").
		when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	}


	//Get Place
	@Test(priority = 3)
	public static void GetPlace() {
		String afterUpdate = given().log().all().queryParam("key", "qaclick123")
				.queryParam("place_id", id).
				when().get("maps/api/place/get/json").
				then().log().all().assertThat().statusCode(200).extract().response().asString();


		JsonPath js1 = new JsonPath(afterUpdate);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);

		System.out.println("All Code Executed");

	}

}
