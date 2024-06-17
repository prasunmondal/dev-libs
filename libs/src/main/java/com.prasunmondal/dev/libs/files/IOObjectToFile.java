package com.prasunmondal.dev.libs.files;

import android.content.Context;

import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOObjectToFile {
    public static void deleteObjectFromFile(String fileName) {
        try {
//            File file = new File(fileName);
//            System.out.println("Delete Detaisl: ");
//            System.out.println(file.getAbsolutePath());
//            System.out.println(file.exists());
//            file.delete();
//            System.err.println("Delete Success: ");

            fileName = "/" + fileName;
            System.out.println(fileName);

            File fdelete = new File(fileName);
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    System.out.println("file Deleted :" + fileName);
                } else {
                    System.out.println("file not Deleted :" + fileName);
                }
            } else {
                System.out.println("file Deleted not exist:");
            }
        } catch (Exception e) {
            System.err.println("Delete error: ");
            e.printStackTrace();
        }
    }

    public synchronized void WriteObjectToFile(Context context, String fileName, Object object) throws IOException {
        LogMe.log("Writing to file: " + fileName);
        LogMe.log("Writing to file content: " + object.toString());
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(object);
        } catch (Exception e) {
            System.out.println("Error while writing object to file: " + fileName);
            System.out.println(e);
            throw e;
        } finally {
            assert os != null;
            os.close();
            fos.close();
        }
    }

    public Object ReadObjectFromFile(Context context, String fileName) {
        LogMe.log("Reading from file: " + fileName);
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object object = is.readObject();
            is.close();
            fis.close();
            return object;
        } catch (Exception e) {
            System.out.println("Error while reading object from file");
            System.out.println(e);
        }

        return null;
    }
}
