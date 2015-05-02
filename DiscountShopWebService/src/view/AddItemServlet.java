package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Item;
import model.Location;

import com.google.gson.JsonObject;

import controller.Create;
import controller.Read;

// for future usage

@WebServlet("/AddItemServlet")
public class AddItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddItemServlet() {
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

		// retailer tag coming from Android
		String retailerTag = request.getParameter("retailerTag").trim();
		// itemName coming from Android
		String itemName = request.getParameter("itemName").trim();
		// itemPrice coming from Android
		String itemPrice = request.getParameter("itemPrice").trim();
		// image coming from Android
		String image = request.getParameter("image").trim();

		// get location from retailers table
		Read read = new Read();
		Location location = read.getLocation(retailerTag);

		Item item = new Item(retailerTag, itemName,
				Float.parseFloat(itemPrice), image, location.getLat(),
				location.getLng());

		boolean successStatus = false;
		Create create = new Create();
		successStatus = create.insertItem(item);
		
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
