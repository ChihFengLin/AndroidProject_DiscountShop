package entities;


public interface RetailerInterface {

    public void setEmail(String Email);
    public void setUsername(String UserName);
    public void setPassword(String PassWord);
    public void setRetailerName(String RetailerName);
    public void setAddress(String Address);
    public void setZipCode(int ZipCode);
    public String getEmail();
    public String getUsername();
    public String getPassword();
    public String getRetailerName();
    public String getAddress();
    public int getZipCode();
}
