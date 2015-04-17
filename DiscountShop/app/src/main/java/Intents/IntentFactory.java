package intents;

import android.content.Context;

import product_exp.view.ConsumerLogin;
import product_exp.view.ConsumerRegister;
import product_exp.view.DisplayItemDetail;
import product_exp.view.ItemListPage;
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

        } else if (cl.equals(ItemListPage.class)) {
            onclick = new goToListItem(packageContext, cl, one, two);
        } else if (cl.equals(RetailerItemListPage.class)) {
            onclick = new AddItem(packageContext, cl, one, two);
        } else if (cl.equals(RetailerAddItem.class)){
            onclick = new goToAddItem(packageContext, cl, one, two);
        }else if(cl.equals(MainActivity.class)) {
            onclick = new goToMain(packageContext, cl, one, two);
        } else if(cl.equals(DisplayItemDetail.class)) {
            onclick = new goToItemDetailDisplay(packageContext, cl, one, two);
        } else if(cl.equals(ConsumerLogin.class)){
            onclick = new goToCon(packageContext, cl, one, two);
        } else if(cl.equals(RetailerLogin.class)){
            onclick = new goToRet(packageContext, cl, one, two);
        } else if (cl.equals(RetailerSettings.class)) {
            onclick = new goToRetailerSetting(packageContext, cl, one, two);
        } else if (cl.equals( RetailerUpdateItem.class)) {
            onclick = new goToRetailerUpdate(packageContext, cl, one, two);
        } else if(cl.equals(RetailerRegister.class) || cl.equals(ConsumerRegister.class)) {
            onclick = new goToSignup(packageContext, cl, one, two);
        } else {
            System.out.println("DS: blala");
        }
        return onclick;
    }
}
