package com.example.myprofit;

import static android.provider.Telephony.Mms.Part.FILENAME;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageStorageHelper {
//    public static void saveImageUri(Context context, String uri) {
//        try (FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)) {
//            fos.write(uri.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String loadImageUri(Context context) {
//        try (FileInputStream fis = context.openFileInput(FILENAME)) {
//            StringBuilder stringBuilder = new StringBuilder();
//            int character;
//            while ((character = fis.read()) != -1) {
//                stringBuilder.append((char) character);
//            }
//            return stringBuilder.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
