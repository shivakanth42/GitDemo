package desirialization_Pojo.TestCases;

import static io.restassured.RestAssured.*;

import java.io.PrintStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import desirialization_Pojo.GetterAndSetter.CoursesMain;
import io.restassured.path.json.JsonPath;

public class LogAllInTextFile {
	
	@Test
	public static void oAuthTestCase() {
		
		
		
		//To pass formparams to the API
		String res = given().formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
		formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").
		formParams("grant_type", "client_credentials").
		formParams("scope", "trust").when().log().all().
		post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		
		//To get accessToken number
		JsonPath js = new JsonPath(res);
		String accessToken = js.getString("access_token");
		System.out.println("\n"+accessToken+"\n");
		
		//ToGet Json Response as java object using pojo classes
		CoursesMain jo = given().queryParams("access_token", accessToken).when().log().all().
		get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(CoursesMain.class);
		
		//To retrive req data from nestedJson
		String reqCourse = "Cypress";
		int waCount = jo.getCourses().getWebAutomation().size();
		for(int i=0; i<waCount; i++) {
			if(jo.getCourses().getWebAutomation().get(i).getCourseTitle().equalsIgnoreCase(reqCourse)) {
				System.out.println("\n"+jo.getCourses().getWebAutomation().get(i).getPrice()+"\n");
			}
		}
		
		//Retrive titles from API and store in an new ArrayList
		String[] expectedArray = {"Rest Assured Automation using Java","SoapUI Webservices testing"};
		ArrayList<String> a = new ArrayList<String>();
		int APICount = jo.getCourses().getApi().size();
		for(int j=0; j<APICount; j++) {
			a.add(jo.getCourses().getApi().get(j).getCourseTitle());
		}
		//Convert given Array to ArrayList and compare with actual ArrayList using Arrays.asList(given Array) for easy
		List<String> expectedArrayList = Arrays.asList(expectedArray);
		Assert.assertTrue(a.equals(expectedArrayList));
		System.out.println("Assertion successfull");
	}
	
	
}
