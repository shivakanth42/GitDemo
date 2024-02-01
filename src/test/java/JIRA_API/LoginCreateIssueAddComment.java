package JIRA_API;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.Map;

public class LoginCreateIssueAddComment {
	
	@Test
	public static void JIRA() {
		
		//Login
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter session = new SessionFilter();  //No login required for the below tasks by using this "SessionFilter'
		//relaxedHTTPSValidation() method is used to aunthenticate http validation in realtime
		String res = given().relaxedHTTPSValidation().log().all().header("Content-Type", "application/json").
		body("{ \"username\": \"kshivakanth333\", \"password\": \"9160006942@S\" }").
		filter(session).
		when().post("/rest/auth/1/session").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = new JsonPath(res);
		String name = js.get("session.name");
		String value = js.get("session.value");
		String JsonId = name+"="+value;
		System.out.println(JsonId+"\n");
		
		//Create Issue
		String issue = given().log().all().header("cookie", JsonId).header("Content-Type", "application/json").
		body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": {\r\n"
				+ "            \"key\": \"RAAPI\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"Automation Bug\",\r\n"
				+ "        \"description\": \"Creating an issue Automation\",\r\n"
				+ "		\"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n"
				+ "        }\r\n"
				+ "    }      \r\n"
				+ "       \r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue").
		then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js1 = new JsonPath(issue);
		String issueId = js1.get("id");
		System.out.println(issueId+"\n");
		
		//Add Comment
		String expectedComment = "Automation Comment";
		String comment = given().log().all().header("cookie", JsonId).header("Content-Type", "application/json").
		body("{\r\n"
				+ "    \"body\": \""+expectedComment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").
		when().post("/rest/api/2/issue/"+issueId+"/comment").
		then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js2 = new JsonPath(comment);
		String commentId = js2.get("id");
		System.out.println(commentId+"\n");
		System.out.println(expectedComment+"\n");
		
		
		//Attaching text file to issue using header and multipart "multiPart("file", new File(path))"
		String file = given().log().all().header("X-Atlassian-Token", "no-check").header("Content-Type", "multipart/form-data").
		multiPart("file", new File("attachment.txt")).filter(session).
		when().post("/rest/api/2/issue/"+issueId+"/attachments").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
		//Get issue
		String getissue = given().log().all().filter(session).queryParam("fields", "comment").when().get("/rest/api/2/issue/"+issueId+"").then().
		log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js3 = new JsonPath(getissue);
		
		int commentscount = js3.getInt("fields.comment.comments.size()");
		System.out.println(commentscount+"\n");
		
		for(int i=0; i<commentscount; i++) {
			String commentWiseId = js3.get("fields.comment.comments["+i+"].id").toString();
			if(commentWiseId.equalsIgnoreCase(commentId)) {
				String actualComment = js3.get("fields.comment.comments["+i+"].body");
				Assert.assertEquals(actualComment, expectedComment);
				System.out.println("Actual comment is: "+actualComment);
				System.out.println("Expected comment is: "+expectedComment);
			}
		}
		
	}

}
