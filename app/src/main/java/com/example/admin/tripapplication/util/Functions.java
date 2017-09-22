package com.example.admin.tripapplication.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.tripapplication.R;
import com.example.admin.tripapplication.model.firebase.User;
import com.facebook.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Functions {

    private static final String TAG = "Functions";

    //Set the fragments as singleinstance
    public static void setFragment(Fragment new_fragment, int id, FragmentManager manager, Context context){
        String who = ""+new_fragment.getClass().getName();
        android.support.v4.app.FragmentTransaction fTransaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(who);

        if (fragment == null) {
            fTransaction.replace(id, new_fragment, who).addToBackStack(who).commit();
        } else {
            if (!fragment.isAdded()) {
                ArrayList<String> list = new ArrayList<>();

                for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
                    list.add(manager.getBackStackEntryAt(i).getName());
                }

                while (manager.getBackStackEntryCount() > 0) {
                    manager.popBackStackImmediate();
                }
                for (int i = 0; i < list.size(); i++) {
                    Fragment ft = Functions.string_to_fragment(list.get(i));

                    if (!list.get(i).equals(who)) {
                        manager.beginTransaction().replace(id, ft, list.get(i)).addToBackStack(list.get(i)).commit();
                    }
                }
                fTransaction.replace(id, fragment, who).addToBackStack(who).setBreadCrumbTitle(who).commit();
            } else {
//                Toast.makeText(context, "already on the Screen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static Fragment string_to_fragment(String s){
        Fragment ft = null;
        try {
            ft = (Fragment) Class.forName(s).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ft;
    }

    public static void OpenPDFAndroid(int id,Context context){
        CopyRawToSDCard(id, Environment.getExternalStorageDirectory() + "/miarchivo.pdf" ,context);

        File pdfFile = new File(Environment.getExternalStorageDirectory(),"/miarchivo.pdf" );//File path
        if (pdfFile.exists()){ //Revisa si el archivo existe!
            Uri path = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //define el tipo de archivo
            intent.setDataAndType(path, "application/pdf");
            intent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
            //Inicia pdf viewer
            context.startActivity(intent);
        } else {
            Toast.makeText(context.getApplicationContext(), "No existe archivo! ", Toast.LENGTH_SHORT).show();
        }
    }

    public static void CopyRawToSDCard(int id, String path,Context context) {
        InputStream in = context.getResources().openRawResource(id);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            byte[] buff = new byte[1024];
            int read = 0;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            in.close();
            out.close();
            Log.i(TAG, "copyFile, success!");
        } catch (FileNotFoundException e) {
            Log.e(TAG, "copyFile FileNotFoundException " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "copyFile IOException " + e.getMessage());
        }
    }

    public static Date getCurrentDateDate(){
        Date date = null;
        try {
            date = (new SimpleDateFormat("MM/dd/yyyy")).parse(Calendar.MONTH+"/"+Calendar.DAY_OF_MONTH+"/"+Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String month_format = String.format("%02d", (mMonth + 1));
        String day_format = String.format("%02d", mDay);
        String year_format = String.format("%04d", mYear);

        String today = month_format + "/" + day_format + "/" + year_format;
        return today;
    }

    public static String getCurrentTime(){
        final Calendar c = Calendar.getInstance();
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMinute = c.get(Calendar.MINUTE);

        String hour_format = String.format("%02d", mHour);
        String minute_format = String.format("%02d", mMinute);

        String current_time = hour_format + ":" + minute_format;
        return current_time;
    }

    public static Double getCurrentHour(){
        final Calendar c = Calendar.getInstance();
        final int mHour = c.get(Calendar.HOUR_OF_DAY);

        String hour_format = String.format("%02d", mHour);

        return Double.parseDouble(hour_format);
    }

    public static Double getCurrentMinute(){
        final Calendar c = Calendar.getInstance();
        final int mMinute = c.get(Calendar.MINUTE);

        String minute_format = String.format("%02d", mMinute);

        return Double.parseDouble(minute_format);
    }


    //GetImg from fb, google and firebase stored as string
    public static String getImg(User user, FirebaseAuth mAuth) {
        if(user != null && user.getImageURL() != null && !user.getImageURL().isEmpty()) {
            return user.getImageURL();
        } else if (Profile.getCurrentProfile() != null) {
            return "https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large";
        } else if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getPhotoUrl() != null && !mAuth.getCurrentUser().getPhotoUrl().toString().isEmpty()) {
            return mAuth.getCurrentUser().getPhotoUrl().toString();
        }
        return "";
    }

    public static double degreesToRadians(double degrees) {
        return degrees * ((double)Math.PI) / 180;
    }

    public static double distanceInKmBetweenEarthCoordinates(double lat1, double lon1, double lat2, double lon2) {
        double earthRadiusKm = 6371;

        double dLat = degreesToRadians(lat2-lat1);
        double dLon = degreesToRadians(lon2-lon1);

        lat1 = degreesToRadians(lat1);
        lat2 = degreesToRadians(lat2);

        double a = (double) Math.sin(dLat/2) * (double) Math.sin(dLat/2) +
                (double) Math.sin(dLon/2) * (double) Math.sin(dLon/2) * (double) Math.cos(lat1) * (double) Math.cos(lat2);
        double c = 2 * (double) Math.atan2((double) Math.sqrt(a), (double) Math.sqrt(1-a));
        return earthRadiusKm * c;
    }

    public static Bitmap drawableToBitmap (Drawable drawable, int color) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
