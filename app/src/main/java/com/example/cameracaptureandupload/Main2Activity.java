package com.example.cameracaptureandupload;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends Activity implements View.OnClickListener {

    final static int TAKE_PICTURE = 1;
    private Button captureButton;
    private Button selectImagesButton;
    private ImageView imagesView;
    private LinearLayout imageViewLayout;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    File dirFile, indirFile;
    Uri uri;
    Uri photoURI;
    Context mCon = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        captureButton = findViewById(R.id.btnCapture);
        selectImagesButton = findViewById(R.id.btnSelect);
        imagesView = findViewById(R.id.imageView);
        imageViewLayout = findViewById(R.id.imgViewLayout);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            captureButton.setEnabled(false);
            selectImagesButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


        captureButton.setOnClickListener(this);
        selectImagesButton.setOnClickListener(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureButton.setEnabled(true);
                selectImagesButton.setEnabled(true);

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {

                    try {

                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap thumbnail = BitmapFactory.decodeFile(indirFile.getAbsolutePath(), bmOptions);


                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                        if (!indirFile.exists()) {
                            indirFile.createNewFile();
                        }

                        String strPath1 = String.valueOf(indirFile);

                        FileOutputStream fo;
                        try {
                            indirFile.createNewFile();
                            fo = new FileOutputStream(indirFile);
                            fo.write(bytes.toByteArray());
                            fo.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        imagesView.setImageBitmap(thumbnail);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                       /* try {
                            FileOutputStream in = new FileOutputStream(indirFile);





                            BitmapFactory.Options options = new BitmapFactory.Options();

                            String imagepath = indirFile.getPath();

                            options.inJustDecodeBounds = true;
                            // image path `String` where your image is located
                            BitmapFactory.decodeFile(imagepath, options);

                            uri = Uri.fromFile(indirFile.getAbsoluteFile());

                            String imageFile = indirFile.getName();

                            imagesView.setImageBitmap(bitmap);


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
*/


                }
                break;
        }
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btnCapture:

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                dirFile = new File(Environment.getExternalStorageDirectory(), "TestImages");
                if (!dirFile.exists()) {

                    dirFile.mkdir();

                }
                indirFile = new File(dirFile, System.currentTimeMillis() + ".jpg");
                photoURI = FileProvider.getUriForFile(mCon,BuildConfig.APPLICATION_ID+".fileprovider"
                        ,indirFile);



//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, indirFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, TAKE_PICTURE);

                break;


            case R.id.btnSelect:

                break;

        }
    }

    /*private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case 0: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(currentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(context.getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {

                        }
                    }
                    break;
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }*/



  /*  try {

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap thumbnail = BitmapFactory.decodeFile(destination.getAbsolutePath(), bmOptions);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        if (!destination.exists()) {
            destination.createNewFile();
        }

        String strPath1 = String.valueOf(destination);

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (strPath1.equalsIgnoreCase("")) {
            docTextView.setText(R.string.strNoFileChosen);
        } else {
            encodedBase64 = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
// Log.d("check", "encodedBase64=" + encodedBase64);
//type="image";
            docName = strPath1.substring(strPath1.lastIndexOf('/') + 1);

            docTextView.setText(strPath1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }*/


}