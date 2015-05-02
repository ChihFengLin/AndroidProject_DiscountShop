package controller;



import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import controller.GeocodeResponse.Result;
import model.Location;

public class GeoParser {

	public Location addressParser(String address){
		//address = "1111 Lockheed Martin Way, Sunnyvale, CA 94089, United States";
		GeoCoder gc = new GeoCoder();
		Location location=null;
		try {
			GeocodeResponse resp = gc.getLocation(address);
			System.out.println("STATUS = "+resp.getStatus());
			List<Result> results = resp.getResults();
			System.out.println("size = "+results.size());
			
			for (int i=0; i<results.size(); i++){
				location = results.get(i).getGeometry().getLocation();
				System.out.println(" lat = "+ location.getLat());
				System.out.println(" long = "+ location.getLng());
			}
			
			
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
		
	
	}
}
