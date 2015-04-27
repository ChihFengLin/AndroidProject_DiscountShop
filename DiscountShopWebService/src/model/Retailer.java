package model;

public class Retailer extends Login {
	private String email;
	private String username;
	private String password;
	private String retailerName;
	private String address;
	private int zipCode;
	private double latitude;
	private double longitude;
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/*Constructor*/
	public Retailer() {
		super();
	}
	
	public Retailer(String Email, String UserName, String PassWord, String RetailerName, String Address, int ZipCode) {
		this.email = Email;
		this.username = UserName;
		this.password = PassWord;
		this.retailerName = RetailerName;
		this.address = Address;
		this.zipCode = ZipCode;
	}
	public Retailer(String Email, String UserName, String PassWord, String RetailerName, String Address, int ZipCode, double latitude, double longitude) {
		this.email = Email;
		this.username = UserName;
		this.password = PassWord;
		this.retailerName = RetailerName;
		this.address = Address;
		this.zipCode = ZipCode;
		this.latitude=latitude;
		this.longitude=longitude;
	}
	
	
	public void setEmail(String Email) {
		this.email = Email;
	}
	
	public void setUsername(String UserName) {
		this.username = UserName;
	}
	
	public void setPassword(String PassWord) {
		this.password = PassWord;
	}
	
	public void setRetailerName(String RetailerName) {
		this.retailerName = RetailerName;
	}
	
	public void setAddress(String Address) {
		this.address = Address;
	}
	
	public void setZipCode(int ZipCode) {
		this.zipCode = ZipCode;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getRetailerName() {
		return this.retailerName;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public int getZipCode() {
		return this.zipCode;
	}
}
