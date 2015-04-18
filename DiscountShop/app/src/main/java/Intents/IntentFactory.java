package intents;

import android.content.Context;

import product_exp.view.ConsumerItemListPage;
import product_exp.view.ConsumerLogin;
import product_exp.view.ConsumerRegister;
import product_exp.view.DisplayItemDetail;
import product_exp.view.MainActivity;
import product_exp.view.RetailerAddItem;
import product_exp.view.RetailerItemListPage;
import product_exp.view.RetailerLogin;
import product_exp.view.RetailerRegister;
import product_exp.view.RetailerSettings;
import product_exp.view.RetailerUpdateItem;



public class IntentFactory {
    public static ClickInterface goToNext(Context packageContext, Class<?> cl, Object one, Object two) {

        ClickInterface onclick = null;
        if (cl == null) {
            //its a service
            if (packageContext.getClass().equals(ConsumerLogin.class) || packageContext.getClass().equals(RetailerLogin.class)) {
                onclick = new LoginReq(packageContext, cl, one, two);
            } else {
                onclick = new UserCreate(packageContext,cl,one,two);
            }

        } else if (cl.equals(ConsumerItemListPage.class)) {
            onclick = new GoToListItem(packageContext, cl, one, two);
        } else if (cl.equals(RetailerItemListPage.class)) {
            onclick = new AddItem(packageContext, cl, one, two);
        } else if (cl.equals(RetailerAddItem.class)){
            onclick = new GoToAddItem(packageContext, cl, one, two);
        }else if(cl.equals(MainActivity.class)) {
            onclick = new GoToMain(packageContext, cl, one, two);
        } else if(cl.equals(DisplayItemDetail.class)) {
            onclick = new GoToItemDetailDisplay(packageContext, cl, one, two);
        } else if(cl.equals(ConsumerLogin.class)){
            onclick = new GoToCon(packageContext, cl, one, two);
        } else if(cl.equals(RetailerLogin.class)){
            onclick = new GoToRet(packageContext, cl, one, two);
        } else if (cl.equals(RetailerSettings.class)) {
            onclick = new GoToRetailerSetting(packageContext, cl, one, two);
        } else if (cl.equals( RetailerUpdateItem.class)) {
            onclick = new GoToRetailerUpdate(packageContext, cl, one, two);
        } else if(cl.equals(RetailerRegister.class) || cl.equals(ConsumerRegister.class)) {
            onclick = new GoToSignup(packageContext, cl, one, two);
        } else {
            System.out.println("DS: blala");
        }
        return onclick;
    }
}
