package intents;
import android.content.Context;
import android.content.Intent;
import product_exp.view.ConsumerSetting;

public class GoToConsumerSetting implements ClickInterface{

    private Context mContext;
    public GoToConsumerSetting(Context packageContext, Class<?> cl, Object inputOne, Object inputTwo){
        mContext = packageContext;
        //String username = (String)inputTwo;
        Intent main = new Intent();
        main.setClass(packageContext, ConsumerSetting.class);
        //main.putExtra("username",username);
        mContext.startActivity(main);
    }
}
