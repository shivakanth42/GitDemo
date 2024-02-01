package AllWorks;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import files.payload;


public class UpdatePlace {

	public static void main(String[] args) {

		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String res = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.AddPlace())
		.when().post("maps/api/place/add/json").
		then().log().all().assertThat().statusCode(200).extract().asString();
		
		
		
		JsonPath js = new JsonPath(res);
		
		String id = js.getString("place_id");
		System.out.println(id);
		System.out.println("ID printed above");
		
		String newAddress = "Sai Balaji Homes";
		given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+id+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200);
		
		System.out.println("\nPlace updated above");
		
	
		
		System.out.println("Code Executed");
		
	}

}
