package intents;

import android.content.Context;
import android.content.Intent;

import product_exp.view.ConsumerRegister;
import product_exp.view.RetailerLogin;
import product_exp.view.RetailerRegister;


public class goToSignup implements ClickInterface {
    public goToSignup(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo) {
        Class<?> myclass = packageContext.getClass();
        Intent signUp = new Intent();
        if (myclass.equals(RetailerLogin.class)) {
            signUp.setClass(packageContext, RetailerRegister.class);
        } else {
            signUp.setClass(packageContext, ConsumerRegister.class);
        }
        packageContext.startActivity(signUp);
    }
}
