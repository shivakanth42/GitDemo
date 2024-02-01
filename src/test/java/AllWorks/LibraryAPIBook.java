package AllWorks;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.*;

import files.payload;

public class LibraryAPIBook {
	
	static String id = "";

	//WithoutArgs
	@Test(enabled = false)
	public static void AddBookWithNoArgs() {
		
		RestAssured.baseURI="http://216.10.245.166";
		String resp = given().log().all().header("Content-Type", "application/json").
		body(payload.AddBook()).
		when().post("Library/Addbook.php").
		then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(resp);
		id = js.get("ID");
		System.out.println("\n"+id);		
	}
	
	
	//WithArgs
	@Test(enabled = false)
	public static void AddBookArgs() {
		
		RestAssured.baseURI="http://216.10.245.166";
		String resp = given().log().all().header("Content-Type", "application/json").
		body(payload.AddBook("siva", "shashi")).    //Pass input as args here what ever you want
		when().post("Library/Addbook.php").
		then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(resp);
		id = js.get("ID");
		System.out.println("\n"+id);		
	}
	
	
	//Testing with DataProvider TestData using below created DataProvider annotation
	@Test(dataProvider = "BooksData", enabled = false)
	public static void AddBookWithDataProvider(String isbn, String aisle) {
		
		RestAssured.baseURI="http://216.10.245.166";
		String resp = given().log().all().header("Content-Type", "application/json").
		body(payload.AddBook(isbn, aisle)).    //Pass input as args here what ever you want
		when().post("Library/Addbook.php").
		then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(resp);
		id = js.get("ID");
		System.out.println("\n"+id);		
	}
	
	
	//Creating multidimentional Object array for multiple testdata using DataProvider
	@DataProvider(name="BooksData")
	public Object[][] TestData() {
		
		return new Object[][] {{"a", "1"}, {"b", "2"}, {"c", "3"}};	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
