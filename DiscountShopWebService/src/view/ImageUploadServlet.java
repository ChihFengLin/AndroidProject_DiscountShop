package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Consumer;
import model.Item;
import model.Retailer;

import com.google.gson.JsonObject;

import controller.AddItem;
import controller.CreateUser;

/**
 * Servlet implementation class ImageUploadServlet
 */
@WebServlet("/ImageUploadServlet")
public class ImageUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ImageUploadServlet() {
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

		// base64 coming from Android
		String base64 = request.getParameter("base64").trim();
		// imageName coming from Android
		String imageName = request.getParameter("imageName").trim();

		//

		// get user info and populate user
		boolean successStatus = false;
		Item item = new Item("test", 3.3f, imageName, base64);
		AddItem addItem= new AddItem();
		
		// if success
		if (successStatus) {
			JsonObject myObj = new JsonObject();
			myObj.addProperty("success", true);
			out.println(myObj.toString());
		}
		// if failure
		else {
			JsonObject myObj = new JsonObject();
			myObj.addProperty("success", false);
			out.println(myObj.toString());
		}
		out.close();
	}
}
