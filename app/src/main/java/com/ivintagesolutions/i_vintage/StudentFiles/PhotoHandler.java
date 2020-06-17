package com.ivintagesolutions.i_vintage.StudentFiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoHandler implements Camera.PictureCallback {

    private final Context context;
    String std_id;
    String filename;

    public PhotoHandler(Context context, String std_id) {
        this.context = context;
        this.std_id = std_id;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        File pictureFileDir = getDir();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

            Log.d(Test_Main.DEBUG_TAG, "Can't create directory to save image.");
            Toast.makeText(context, "Can't create directory to save image.",
                    Toast.LENGTH_LONG).show();
            return;

        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = dateFormat.format(new Date());
        String photoFile = std_id + "_" + date + ".jpg";

        filename = pictureFileDir.getPath() + File.separator + photoFile;

        File pictureFile = new File(filename);

        Bitmap picture = BitmapFactory.decodeByteArray(data, 0, data.length);

        Log.e("BITMAP",">>>>>>>>>>>>" +picture);


        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
          // scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
          /*  picture.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            picture.recycle();*/
            fos.write(data);
            fos.close();
            Toast.makeText(context, "New Image saved:" + photoFile,
                    Toast.LENGTH_LONG).show();

        } catch (Exception error) {
            Log.d(Test_Main.DEBUG_TAG, "File" + filename + "not saved: "
                    + error.getMessage());
            Toast.makeText(context, "Image could not be saved.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private File getDir() {
        File ivintageDir = new File(Environment.getExternalStorageDirectory().getPath() + "/.Ivintage/"+std_id);
        ivintageDir.mkdirs();

        return new File(ivintageDir, "My_images");

    }

}
