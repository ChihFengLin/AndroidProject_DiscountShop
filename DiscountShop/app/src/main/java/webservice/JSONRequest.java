package webservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JSONRequest extends IntentService {

    private final String webServiceUrl="http://www.codeee.com:8080/DiscountShopWebService/";
    ///////////////
    public static final String IN_MSG="requestType";
    private String inMessage;
    public static final String OUT_MSG= "outputMessage";
    private String process_response_filter;

    public JSONRequest() {
        super("JSONRequest");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        process_response_filter=intent.getStringExtra("processType");
        inMessage=intent.getStringExtra(IN_MSG).trim();
        switch(inMessage){
            case "getLoginInfo":
            String loginType=intent.getStringExtra("loginType");
            String username=intent.getStringExtra("username");
                getLoginInfo(loginType,username);
                break;
            case "createUser":
            String userType=intent.getStringExtra("userType");
            //consumer creating
            if(userType.equals("consumer")){
            String email=intent.getStringExtra("email");
            String consumer_username = intent.getStringExtra("username");
            String password=intent.getStringExtra("password");
            createConsumer("consumer",email,consumer_username,password);
            }
            //retailer creating
            else{
            String email=intent.getStringExtra("email");
            String retailer_username = intent.getStringExtra("username");
            String password=intent.getStringExtra("password");
            String retailerName=intent.getStringExtra("retailerName");
            String address=intent.getStringExtra("address");
            String zipCode=intent.getStringExtra("zipCode");
            createRetailer("retailer",email,retailer_username,password,retailerName,address,zipCode);
            }
             break;

            case "update":
            String updateType=intent.getStringExtra("updateType");
            if(updateType.equals("address")) {
                String username_update = intent.getStringExtra("username");
                String newAddress = intent.getStringExtra("newAddress");
                Log.v("username_update","test:"+username_update);
                Log.v("newAddress","test:"+newAddress);
                update("address",username_update,"newAddress",newAddress);
            }
                break;


            default:
                break;
        }

    }


// To get Login info: first, send http request; then receive response and finally broadcast the result
    private void getLoginInfo(String loginType,String username){
        //add name value pair for the login info request
        List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("loginType",loginType));
        nameValuePairs.add(new BasicNameValuePair("username",username));

        //prepare to make HTTP request
        String url=webServiceUrl+"LoginServlet";
        // make http request and broadcast the request
        requestBroadcastProcess(url,nameValuePairs);

    }
//create consumer, http request
    private void createConsumer(String userType,String email,String username, String password){
        //add email, username, password for the create consumer request
        List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("userType",userType));
        nameValuePairs.add(new BasicNameValuePair("email",email));
        nameValuePairs.add(new BasicNameValuePair("username",username));
        nameValuePairs.add(new BasicNameValuePair("password",password));
        //prepare to make HTTP request
        String url=webServiceUrl+"CreateUserServlet";
        // make http request and broadcast the request
        requestBroadcastProcess(url,nameValuePairs);
    }
//create retailer, http request
    private void createRetailer(String userType,String email,String username, String password, String retailerName, String address, String zipCode){
        List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(7);
        nameValuePairs.add(new BasicNameValuePair("userType",userType));
        nameValuePairs.add(new BasicNameValuePair("email",email));
        nameValuePairs.add(new BasicNameValuePair("username",username));
        nameValuePairs.add(new BasicNameValuePair("password",password));
        nameValuePairs.add(new BasicNameValuePair("retailerName",retailerName));
        nameValuePairs.add(new BasicNameValuePair("address",address));
        nameValuePairs.add(new BasicNameValuePair("zipCode",zipCode));
        //prepare to make HTTP request
        String url=webServiceUrl+"CreateUserServlet";
        // make http request and broadcast the request
        requestBroadcastProcess(url,nameValuePairs);
    }

    //update, http request
    private void update(String updateType,String username, String updateKey, String updateValue){
        if(updateType.equals("address")) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("updateType", "address"));
            nameValuePairs.add(new BasicNameValuePair("username", username));
            nameValuePairs.add(new BasicNameValuePair(updateKey, updateValue));
            //prepare to make HTTP request
            String url=webServiceUrl+"UpdateServlet";
            // make http request and broadcast the request
            requestBroadcastProcess(url,nameValuePairs);
        }

    }


    // integration of sendHttpRequest and broadcastResponse functions
    private void requestBroadcastProcess(String url, List<NameValuePair> nameValuePairs){
        String response=sendHttpRequest(url,nameValuePairs);
        broadcastResponse(response);

    }

// send Http request to servlet by name value pair
    private String sendHttpRequest(String url,List<NameValuePair> nameValuePairs){
        int REGISTRATION_TIMEOUT = 15 * 1000;
        int WAIT_TIMEOUT = 60 * 1000;
        HttpClient httpclient = new DefaultHttpClient();
        HttpParams params = httpclient.getParams();
        HttpResponse response;
        String content =  "";
        try {

            //http request parameters
            HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

            //http POST
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //send the request and receive the repponse
            response = httpclient.execute(httpPost);

            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                content = out.toString();
            }

            else{
                //Closes the connection.
                Log.w("HTTP1:", statusLine.getReasonPhrase());
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (ClientProtocolException e) {
            Log.w("HTTP2:",e );
        } catch (IOException e) {
            Log.w("HTTP3:",e );
        }catch (Exception e) {
            Log.w("HTTP4:",e );
        }

        //send back the JSON response String
        return content;
    }

    //broadcast message that we have received the response
    //from the web service
    private void broadcastResponse(String response){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(process_response_filter);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(IN_MSG,inMessage);
        broadcastIntent.putExtra(OUT_MSG,response);
        sendBroadcast(broadcastIntent);
    }

}
