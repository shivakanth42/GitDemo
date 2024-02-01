package sesirialization_Pojo;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TestCase_Serialization {
	
	
	@Test
	public static void Serialization() {
			
		
		PojoClasses p = new PojoClasses();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);	
		p.setLocation(l);
		
		List<String> t = new ArrayList<String>();
		t.add("shoe park");
		t.add("shop");
		p.setTypes(t);
		
		String res = given().log().all().queryParam("key", "qaclick123").body(p).
		when().post("https://rahulshettyacademy.com/maps/api/place/add/json").then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		System.out.println(res);
		
		
		
	}

}
