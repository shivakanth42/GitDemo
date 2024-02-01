package AllWorks;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class AddBookWithFile {
	
	
	//Passing text file into body which contains json data
	static String id = "";
	@Test
	public static void Add() throws IOException {
		
		RestAssured.baseURI="http://216.10.245.166";
		String resp = given().log().all().header("Content-Type", "application/json").
		body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Dell\\Downloads\\API Testing\\AddBook.json")))).
		when().post("Library/Addbook.php").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(resp);
		id = js.get("ID");
		System.out.println("\n"+id);		
	}
	

}
