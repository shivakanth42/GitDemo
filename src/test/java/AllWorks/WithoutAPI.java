package AllWorks;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class WithoutAPI {
	static JsonPath js = new JsonPath(payload.noApi());
	static int coursecount = js.getInt("courses.size()");
	
	//Print No of courses returned by API
	public static void A() {
		
		
		
		System.out.println(coursecount);		
	}
	
	//Print Purchase Amount
	public static void B() {
		
				System.out.println(js.getInt("dashboard.purchaseAmount".toString()));
			
	}
	
	//Print Title of the first course
	public static void C() {
		
		System.out.println(js.get("courses[0].title").toString());
		
	}
	
	//Print All course titles and their respective Prices
	public static void D() {
		for(int i=0; i<coursecount; i++) {
			System.out.println(js.get("courses["+i+"].title").toString());
			System.out.println(js.getInt("courses["+i+"].price"));
		}
		}
	
	//Print no of copies sold by RPA Course
	public static void E() {
		
		for(int i=0; i<coursecount; i++) {
			if(js.get("courses["+i+"].title").toString().equalsIgnoreCase("RPA")) {
				System.out.println(js.getInt("courses["+i+"].copies"));
				break;
			}
		}
		
	}
	
	//Verify if Sum of all Courses with prices matches with Purchase Amount
	public static void F() {
		int count = 0;
		for(int i=0; i<coursecount; i++) {
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int coursesprice = price * copies;
			count = count+coursesprice;
			
		}System.out.println(count);
		Assert.assertEquals(count, 910);
		
		
	}
	
	
	public static void main(String[] args) {
		WithoutAPI.A();
		WithoutAPI.B();
		WithoutAPI.C();
		WithoutAPI.D();
		WithoutAPI.E();
		WithoutAPI.F();
	}	

}
