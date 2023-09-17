package API;


import org.testng.annotations.Test;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
public class Trello {
	public static String baseURI = "https://api.trello.com";
	public static String key ="64c40ecdacc039d2e007553f2aa0f06e";
	public static String token = "ATTA980f05f13f247d6a43554013f4ff752eede3f702ed34f1a2a4e93072278142444A582A4D";
	public static String ID;
	public static int rowcount;
	public static String boardname,colour,desc;
	
	
	@Test
	public void datadrivenapi() throws IOException
	{
		//step 1; i have to access the file and store it in a variable
		//java
		File excel = new File("/Users/abhishek.mooly.con/git/repository2/RESTAPI/data (1).xlsx");
		
		//Step 2: i have to read the file 
		//java
		FileInputStream fis = new FileInputStream(excel);
		
		//step 3: as two method xssf if you are working with xlsx format you can also use xls for then the methods id hssf
		//Apache POI method are xssf and hssf
		XSSFWorkbook  wb = new XSSFWorkbook(fis);
		
		//step4 : go to the excel sheet and read the data finish 
		
		XSSFSheet sh = wb.getSheet("Sheet1");
		
		//i want to access some data 
		rowcount = sh.getLastRowNum();
		
		for(int i=1;i<=rowcount;i++)
		{		
			boardname = sh.getRow(i).getCell(0).getStringCellValue();
			colour = sh.getRow(i).getCell(1).getStringCellValue();
			desc = sh.getRow(i).getCell(2).getStringCellValue();
			create_a_board();
			get_a_Board();
			Update_a_Board();
		    Delete_a_Board();
			
		}
		
	}
	
	//u have learnt postman
	//wht is the first thing i do in post man
	
	
	//Rest Assured contains 3 methods
	//1) given() - pre-codition //which contains // headers (Content-type),queryparameter, body,authorization
	//2) When() Action method //it contains what http method i am using (get, post, put, delete) the resource
	//3) Then() Output which is nothing but my result
	
	@Test(priority = 0,enabled = false)
	public void create_a_board()
	{
		//step1 //i have to add the baseURL
		//there are predefined libraries in restassured
		RestAssured.baseURI = baseURI;
		Response res=  given()
		.queryParam("key", key)
		.queryParam("token", token)
		.queryParam("name", "IDE")
	.header("Content-Type", "application/json")
	
		
		.when()
		.post("/1/boards/")
		
		.then()
		.assertThat().statusCode(200)
		// i want to fetch the response 
		.extract().response();
		// i have to covert this in to json format 
		//String responsejson = res.asString();
		//tp convert to json format 
		JsonPath Js= new JsonPath(res.asPrettyString());
		//System.out.println(js.get("id"));
		// i can fetch any object in the response
		ID = Js.get("id");
		//System.outprintln(js.get("pref.background"));
		
		//it will be printed in my console
		//System.out.println(res.asPrettyString());
		 //we want to print the response // the need the id so that i can perform the future operation

		
	}


	  @Test(priority = 1,enabled = false)
	   
	  public void get_a_Board()
	  {	  
	  
	  //   String FirstName = "Abhishek";
	  //   String LastName = "Mohan";
	  //   //The concept i am using here is concatination
	  //   String FullName = FirstName + " "+LastName;
	      //step1 // i have to add the baseURL
	         //there are predefined libraries in restassured
	  
	         RestAssured.baseURI = baseURI;
	         Response    res = given()
	        		 .queryParam("key" , key)
	        		 .queryParam("token", token)
	        	.header("Content-Type" , "applications/json")	 
	            
	        	.when()
	        	//so in java when i want to add some value to the url
	        	//i use concatination (+)
	        	//ID is a variable so the reason i didn'y enclose it in the double codes
	        	.get("/1/boards/"+ID)
	        	.then()
	        	.assertThat().statusCode(200)
	        	.extract().response();
	         System.out.println(res.asPrettyString());
	         
}


	
@Test(priority = 2,enabled = false)
public void Update_a_Board()
{
	//step1 //i have to add the baseURL
	        //there are predefined libraries in restassured
	        RestAssured.baseURI = baseURI;
	        Response  res = given()
	        		.queryParam("key" , key)
	        		.queryParam("token", token)
	        		.queryParam("name" , boardname)
	        		.queryParam("desc", "createdon sunday morning sep")
	        		.header("Content-Type", "application/json")
	        		
	        		.when()
	        		.put("/1/boards/"+ID)
	        		
	        		.then()
	        		.assertThat().statusCode(200)
	        		.extract().response();
	        System.out.println(res.asPrettyString());
	        
}       
	        		
  @Test(priority = 3,enabled = false)
  public void Delete_a_Board()
  
  {
	  //step1 //i have to add the baseURL
	        //there are predefined libraries in restassured
	        RestAssured.baseURI = baseURI;
	        Response  res = given()
	        		.queryParam("key" , key)
	        		.queryParam("token", token)
	        		.header("Content-Type", "application/json")
		          
		            .when()
		            .delete("/1/boards/"+ID)
		            
		            .then()
		            .assertThat().statusCode(200)
		            .extract().response();
		          System.out.println(res.asPrettyString());
    }	          

  }







