package model;


public class Retailer {
    private String email;
    private String username;
    private String password;
    private String retailerName;
    private String address;
    private int zipCode;

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

