package com.example.alvaf1.changes2018;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static boolean checkImageFileExist(String fileName, Context context) {

        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imagePath = new File(directory, fileName);
        if (imagePath.exists()) {
            return true;
        }

        return false;
    }

    public static String saveImageFile(Bitmap bitmap, String imageName, Context context) {

        Log.d("Utils", "saveImageFileWithBitmap = " + imageName);

        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagePath = new File(directory, imageName);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imagePath);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
            System.gc();
        }

        Log.d("Utils", imagePath.getAbsolutePath());

        return imagePath.getAbsolutePath();
    }

    public static String getPath(Uri uri, Context context) {
        String result = "";

        if (uri.toString().contains("storage")) {
            return uri.toString();
        }

        Cursor cursor = null;
        try {
            String[] data = {MediaStore.Images.Media.DATA};

            cursor = context.getContentResolver().query(uri, data, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return result;
    }


    public static ArrayList<Item> loadDataLocal(Context context) {
    ArrayList<Item> arrayList = new ArrayList<>();
    File file = new File(context.getFilesDir(), Constants.ITEM_DATA);
    if (file.exists()) {
        try {
            FileInputStream fileInputStream = context.openFileInput(Constants.ITEM_DATA);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            arrayList=(ArrayList<Item>)objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    return arrayList;
}

    public static Boolean writeDataLocal(ArrayList<Item> arrayList, Context context){
        boolean result=false;
        try
        {
            FileOutputStream fileOutputStream=context.openFileOutput(Constants.ITEM_DATA, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.flush();
            objectOutputStream.close();
            result =true;
            Log.d("File writing ","is OK");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

