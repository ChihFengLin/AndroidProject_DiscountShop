package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Item;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import controller.Read;

/**
 * Servlet implementation class GetWholeItemListServlet
 */

@WebServlet("/GetWholeItemListServlet")
public class GetWholeItemListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetWholeItemListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
			
		//String confirm= request.getParameter("confirm").trim();
		Read read = new Read();
		List<Item> wholeItemList = null;
		
		wholeItemList = read.getWholeItemList();	
			
		if (wholeItemList.size() == 0 || wholeItemList == null) {
			JsonObject myObj=new JsonObject();
			myObj.addProperty("success", false);
			out.println(myObj.toString());
		} else {
			Gson gson = new GsonBuilder().create();
			JsonElement arrayListToJson = gson.toJsonTree(wholeItemList);
			//JsonElement searchItemListObj=gson.toJsonTree(searchItemList);
			JsonObject myObj=new JsonObject();
			myObj.addProperty("success", true);
			myObj.add("wholeItemList", arrayListToJson);
			out.println(myObj.toString());
		}
		
		out.close();
		
	}

}
