package org.usb.testing;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
//import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.json.simple.JSONObject;


import static io.restassured.RestAssured.given;

import java.io.File;

public class APITest {
	
	@BeforeClass
	@Parameters("bu")
	public void setup(String bu) {
		//JsonPath js = new JsonPath(new File("config.json"));
		//String bu = js.getString("baseURL");
		//RestAssured.baseURI = bu;
		RestAssured.baseURI = bu;//js.getString("baseURL");//"https://reqres.in/";//context.getCurrentXmlTest().getParameter("BaseURI");
        RestAssured.basePath = "api/users";
	}
	
	
	@BeforeMethod
	 public void beforeMethod() {
	   System.out.println("This will execute before every Method");
	 }
	
	@Test(description = "Test method to fetch all users!!")
	public void getAllUsers() {
		
		Response response = given()
				.queryParam("page","2")
				.get();
		JsonPath js = new JsonPath(response.asString());
		System.out.println(js.toString());
		Assert.assertEquals(js.getString("total_pages"), "2");
        Assert.assertEquals(response.getHeader("server"), "cloudflare");
		
	}
	
	@Test(enabled = false)
	public void getUserById() {
		SoftAssert softassert = new SoftAssert();
		Response response = given().get("/2");
		JsonPath js = new JsonPath(response.asString());
		System.out.println(js.toString());
		softassert.assertEquals(js.getString("data.first_name"), "Janet");
		Assert.assertEquals(js.getString("data.last_name"), "Weaver");
	}
	
	@Test(priority = 1)
	public void createUser() {
		JSONObject data = new JSONObject();
        data.put("email","sai.yamanaka@reqres.in");
        data.put("first_name","Saii");
        data.put("last_name","Yamanaka");
		Response response = given().contentType(ContentType.JSON).body(data.toString()).post();
		JsonPath js = new JsonPath(response.asString());
		System.out.println(js.toString());
		//Assert.assertEquals(js.getString("data.first_name"), "Janet");
	}
	
	@AfterMethod
	 public void afterMethod() {
	   System.out.println("This will execute after every Method");
	 }
	
	@AfterClass
	public void afterClassMethod() {
		System.out.println("Test Cases run successfully!");
	}

}
