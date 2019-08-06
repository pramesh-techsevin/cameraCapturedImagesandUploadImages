package com.example.cameracaptureandupload;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button captureButton;
    private Button selectImagesButton;
    private ImageView imagesView;
    private LinearLayout imageViewLayout;
    final int CAMERA_PIC_REQUEST = 1337;
    ImageView imageview;
    Bitmap imageBitMap;
    private Uri file;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;


    File destination;
    Uri selectedImage;
    public static String selectedPath1 = "NONE";
    private static final int PICK_Camera_IMAGE = 2;
    private static final int SELECT_FILE1 = 1;
    public static Bitmap bmpScale;
    public static String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imagesView.setImageURI(file);
            }
        } else if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                imagesView.setImageBitmap(photo);
            }
        }*/
//        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE1:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = data.getData();

                    if (requestCode == SELECT_FILE1) {

//                        selectedPath1 = getPath(selectedImage);
                        // mimagepath.setText(selectedPath1);
                        // Toast.makeText(Camera.this, "" + selectedPath1 + "",
                        // 500).show();

                        if (selectedPath1 != null) {

                            BitmapFactory.Options options = new BitmapFactory.Options();

                            options.inJustDecodeBounds = true;
                            // image path `String` where your image is located
                            BitmapFactory.decodeFile(selectedPath1, options);


                            // Log.d("setpath ", "setpath " + selectedPath1);
                            ;

                        }
                    }
                }

                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {

                    try {
                        FileInputStream in = new FileInputStream(destination);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    imagePath = destination.getAbsolutePath();


                    // Toast.makeText(Camera.this, "" + imagePath +
                    // "",Toast.LENGTH_LONG).show();

                    break;

                }

        }
    }

/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                */
/*File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }*//*

                try {
                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(data.toUri()),
                            bitmapOptions);
                    imagesView.setImageBitmap(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
//                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path","path of image from gallery......******************........."+ picturePath);
                imagesView.setImageBitmap(thumbnail);
            }
        }
    }
*/


    /* private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }*/
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnCapture:


//                String name = dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss");
                String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                destination = new File(Environment
                        .getExternalStorageDirectory(), name + ".jpg");

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(destination));
                startActivityForResult(intent, PICK_Camera_IMAGE);



               /* Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);*/



                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = Uri.fromFile(getOutputMediaFile());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
                startActivityForResult(intent, 100);*/


                break;


            case R.id.btnSelect:


                break;


        }


    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case CAMERA_PIC_REQUEST:
                imageBitMap = (Bitmap) data.getExtras().get("data");
                imageview = (ImageView) findViewById(R.id.imageView); //sets imageview as the bitmap
                imageview.setImageBitmap(imageBitMap);

                break;


        }
    }*/




//    ----------------------------------------------------------------------




   /* private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }*/

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }*/
}
