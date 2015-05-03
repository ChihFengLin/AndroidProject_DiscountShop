package exception;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import product_exp.view.R;


public class DiscountShopException extends Exception {

                public enum myExceptions {NOT_CONNECTED, GPS_NOT_ENABLED, AUTH_FAILED, WIFI_NOT_ENABLED;}

    private myExceptions exceptionCaught;
    private String errstring;
    private Context context;

    public DiscountShopException(myExceptions exceptionCaught, String errmsg, Context con) {
        super(errmsg);//parent class constructor

        //set autoexception fields
        this.setExceptionCaught(exceptionCaught);
        this.errstring = errmsg;
        this.context = con;

        logAutoException(this); // log to file
    }

    public String getErrmsg() {
        return this.errstring;
    }

    public myExceptions getExceptionCaught() {
        return exceptionCaught;
    }

    public void setExceptionCaught(myExceptions exceptionCaught) {
        this.exceptionCaught = exceptionCaught;
    }


    public void logAutoException(Exception e) {
        e.printStackTrace();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); // build the  date format
        String dateTimeString = dateTimeFormat.format(cal.getTime()); //build the date time string
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("DiscountShopExceptionlogs.txt", true))); // append to autoexceptionlogs.txt
            writer.println("DiscountShop > " + dateTimeString + ":" + e.getMessage()); // write to file
            writer.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void fix(myExceptions errcode) {
        fixException f1 = new fixException();
        switch (errcode) {
            case GPS_NOT_ENABLED:
                f1.gpsFix(this.context);
                break;
            case NOT_CONNECTED:
                f1.networkFix(this.context);
                break;
            case WIFI_NOT_ENABLED:
                f1.wifiFix(this.context);
            default:
                break;
        }
        return;
    }

    class fixException {

        void networkFix(Context con) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(con);
            dialog.setMessage(con.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(con.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    Dialog dialog = (Dialog) paramDialogInterface;
                    Context context = dialog.getContext();
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(con.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }

        //function to fix GPS exception
        void gpsFix(Context con) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(con);
            dialog.setMessage(con.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(con.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    Dialog dialog = (Dialog) paramDialogInterface;
                    Context context = dialog.getContext();
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(con.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
        //function to fix GPS exception

        void wifiFix(Context con) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(con);
            dialog.setMessage(con.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(con.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    Dialog dialog = (Dialog) paramDialogInterface;
                    Context context = dialog.getContext();
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(con.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }

    }




}