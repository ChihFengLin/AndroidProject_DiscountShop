package model;


import entities.RetailerInterface;

public class Retailer extends Login implements RetailerInterface{

    private String email;
    private String retailerName;
    private String address;
    private int zipCode;

    /*Constructor*/
    public Retailer() {
        super();
    }

    public Retailer(String Email, String RetailerName, String Address, int ZipCode) {
        this.email = Email;

        this.retailerName = RetailerName;
        this.address = Address;
        this.zipCode = ZipCode;
    }


    public void setEmail(String Email) {
        this.email = Email;
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

