package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Consumer;
import model.Login;
import model.Retailer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import controller.CreateUser;
import controller.RetrieveLoginInfo;

//already act as a web service
//////////////////////////////
@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		//login type comming from Android
		String userType=request.getParameter("userType").trim();
		//get user info and populate user
		boolean successStatus=false;
		CreateUser createUser=new CreateUser();
		if(userType.equals("consumer")){
			String email=request.getParameter("email").trim();
			String username=request.getParameter("username").trim();
			String password=request.getParameter("password").trim();
			Consumer consumer=new Consumer(email,username,password);
		
			successStatus=createUser.createConsumer(consumer);
		}
		else{
			String email=request.getParameter("email").trim();
			String username=request.getParameter("username").trim();
			String password=request.getParameter("password").trim();
			String retailerName=request.getParameter("retailerName").trim();
			String address=request.getParameter("address").trim();
			int zipCode=Integer.parseInt(request.getParameter("zipCode").trim());
			System.out.println("create retailer username input: "+username);
			System.out.println("create retialer password input: "+password);
			Retailer retailer= new Retailer(email,username,password,retailerName,address,zipCode);
			successStatus=createUser.createRetailer(retailer);
		}
		
		// if success
		if(successStatus){
			JsonObject myObj=new JsonObject();
			myObj.addProperty("success", true);
			out.println(myObj.toString());
		}
		// if failure
		else{
			JsonObject myObj=new JsonObject();
			myObj.addProperty("success", false);
			out.println(myObj.toString());
		}
		out.close();
	}

}
