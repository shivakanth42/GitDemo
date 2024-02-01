package AllWorks;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;



public class DeleteBook {
	
			static String id = "";
			@Test
			public static void Add() {
				
				RestAssured.baseURI="http://216.10.245.166";
				String resp = given().log().all().header("Content-Type", "application/json").
				body(payload.AddBook()).
				when().post("Library/Addbook.php").
				then().assertThat().statusCode(200).extract().response().asString();
				
				JsonPath js = new JsonPath(resp);
				id = js.get("ID");
				System.out.println("\n"+id);		
			}
		
			@Test
			public static void DeletePostedBook() {
				
				RestAssured.baseURI="http://216.10.245.166";
				String resp = given().log().all().header("Content-Type", "application/json").
				body("{\r\n"
						+ " \r\n"
						+ "\"ID\" : \""+id+"\"\r\n"
						+ " \r\n"
						+ "}").
				when().post("Library/DeleteBook.php").
				then().assertThat().statusCode(200).extract().response().asString();
				
				JsonPath js = new JsonPath(resp);
				String id = js.get("ID");
				System.out.println("\n"+id);		
			}
			
			

}
