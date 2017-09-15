package com.example.admin.tripapplication.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
                fTransaction.replace(id, fragment, who).addToBackStack(who).commit();
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
}
