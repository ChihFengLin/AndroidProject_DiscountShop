package view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Login;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controller.RetrieveLoginInfo;


//already act as a web service
//////////////////////////////
public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = -7003289728882047706L;

	public LoginServlet(){
		super();
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		//username coming from Android
		String username= request.getParameter("username").trim();
		//login type comming from Android
		String loginType=request.getParameter("loginType").trim();
		//get login info based on username and loginType
		RetrieveLoginInfo loginInfo = new RetrieveLoginInfo();
		Login login = loginInfo.getInfo(loginType,username);
		
		// if invalid username
		if(login.getUsername()==null){
			JsonObject myObj=new JsonObject();
			myObj.addProperty("success", false);
			out.println(myObj.toString());
		}
		// if a valid username was sent
		else{
			Gson gson = new Gson();
			//create json from login object
			JsonElement loginObj=gson.toJsonTree(login);
			//create a new JSON object
			JsonObject myObj=new JsonObject();
			//add property as success;
			myObj.addProperty("success", true);
			//add login object
			myObj.add("loginInfo", loginObj);
			//convert the JSON to string and send back
			out.println(myObj.toString());
		}
		out.close();
		

	}
}
