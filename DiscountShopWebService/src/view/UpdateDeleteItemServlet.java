package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import controller.Delete;
import controller.Update;

/**
 * Servlet implementation class UpdateItemServlet
 */
@WebServlet("/UpdateDeleteItemServlet")
public class UpdateDeleteItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateDeleteItemServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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

		// update type comming from Android
		String deleteUpdate = request.getParameter("deleteUpdate").trim();
		// get user info and populate user
		boolean successStatus = false;
		
		boolean isDelete = deleteUpdate.equals("delete");
		if (isDelete) {
			//DeleteItem deleteItem=new DeleteItem();
			Delete delete = new Delete();
			
			String retailerName = request.getParameter("retailerName").trim();
			String itemName = request.getParameter("itemName").trim();

			successStatus = delete.deleteItem(retailerName, itemName);
		} else {
			Update update = new Update();

			String retailerName = request.getParameter("retailerName").trim();
			String itemName = request.getParameter("itemName").trim();
			String newItemName = request.getParameter("newItemName").trim();
			String newItemPrice = request.getParameter("newItemPrice").trim();
			System.out.println("new Item Price: "+newItemPrice);
			successStatus = update.updateItem(retailerName, itemName,
					newItemName, newItemPrice);
		}

		// if success
		if (successStatus) {
			JsonObject myObj = new JsonObject();
			myObj.addProperty("success", true);
			if (isDelete)
				myObj.addProperty("isDelete", true);
			else
				myObj.addProperty("isDelete", false);

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
