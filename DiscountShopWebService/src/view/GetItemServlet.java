package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Item;
import model.Login;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import controller.RetrieveItem;
import controller.RetrieveLoginInfo;

/**
 * Servlet implementation class GetItemServlet
 */
@WebServlet("/GetItemServlet")
public class GetItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetItemServlet() {
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

		// imageName coming from Android
		String imageName = request.getParameter("imageName").trim();
		RetrieveItem retrieveItem = new RetrieveItem();
		Item item = retrieveItem.getItem(imageName);

		// if invalid username
		if (item.getItemName() == null) {
			JsonObject myObj = new JsonObject();
			myObj.addProperty("success", false);
			out.println(myObj.toString());
		}
		// if a valid username was sent
		else {
			Gson gson = new Gson();
			// create json from login object
			JsonElement itemObj = gson.toJsonTree(item);
			// create a new JSON object
			JsonObject myObj = new JsonObject();
			// add property as success;
			myObj.addProperty("success", true);
			// add login object
			myObj.add("itemInfo", itemObj);
			// convert the JSON to string and send back
			out.println(myObj.toString());
		}
		out.close();

	}

}
