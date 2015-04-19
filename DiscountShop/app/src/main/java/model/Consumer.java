package model;


import entities.ConsumerInterface;

public class Consumer extends Login implements ConsumerInterface {

    private String email;


    /* Constructor */
    public Consumer() {
        super();
    }

    public Consumer(String Email) {
        super();
        this.email = Email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }


    public String getEmail() {
        return this.email;
    }

}
