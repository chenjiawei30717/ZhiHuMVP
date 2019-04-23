package me.fangx.zhihu.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.zhihu.R;
import me.fangx.zhihu.utils.MultipartEntity;

import static android.app.Activity.RESULT_OK;

public class PhotoTakeFragment extends BaseFragment {
    @Bind(R.id.phototake_record_voice)
    ImageButton phototake_record_voice;
    @Bind(R.id.phototake_record_loading)
    ImageView phototake_record_loading;

    private String mCurrentPhotoPath;

//    private static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private static String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//    };

    @Override
    protected void initViewsAndEvents() {
        phototake_record_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AndPermission.with(getActivity())
                        .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                // Ensure that there's a camera activity to handle the intent
                                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                    // Create the File where the photo should go
                                    System.out.println("222222222222222222222222");
                                    File photoFile = null;
                                    try {
                                        photoFile = createImageFile();
                                    } catch (IOException ex) {
                                        // Error occurred while creating the File

                                    }
                                    // Continue only if the File was successfully created
                                    if (photoFile != null) {
                                        System.out.println("1111111111111111111111111111111111111111111111");
                                        System.out.println(photoFile.getAbsolutePath());
                                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                                Uri.fromFile(photoFile));
                                        startActivityForResult(takePictureIntent, 114);
                                    }
                                }
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
//                        Uri packageURI = Uri.parse("package:" + getPackageName());
                                Log.e("getURI之前","1");
                                Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
                                Log.e("getURI之后","1");
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);

                                Toast.makeText(getActivity(), "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
                            }
                        }).start();





            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.layout_phototake;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }










    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.err.println(requestCode+"      "+resultCode);
        // 扫描二维码/条码回传
        if(requestCode==114 && resultCode == RESULT_OK){

            System.out.println("4444444444444444444444444444444444444");
            Log.i("resultCode","1212121212");
            setPic();
        }
    }



    private void setPic() {
//        ImageView mImageView = (ImageView)findViewById(R.id.);
        // Get the dimensions of the View
        int targetW = phototake_record_loading.getWidth();
        int targetH = phototake_record_loading.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;





        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        Matrix mtx = new Matrix();
//        mtx.postRotate(90);
        // Rotating Bitmap

        Log.e("bitmap",String.valueOf(bitmap.getWidth()));
        Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);

        if (rotatedBMP != bitmap)
            bitmap.recycle();

        phototake_record_loading.setImageBitmap(rotatedBMP);

        try {
            Log.e("bitmap",String.valueOf(bitmap.getWidth()));
            sendPhoto(rotatedBMP);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        System.out.println("3333333333333333333333333");
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/ricoh";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdirs();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("photo path", "photo path = " + mCurrentPhotoPath);
        System.out.println(storageDir + "/" + imageFileName + ".jpg");
        System.out.println(mCurrentPhotoPath);
        return image;
    }

    private void sendPhoto(Bitmap bitmap) throws Exception {
        new UploadTask().execute(bitmap);
    }

    private class UploadTask extends AsyncTask<Bitmap, Void, Void> {

        protected Void doInBackground(Bitmap... bitmaps) {
            if (bitmaps[0] == null)
                return null;
            getActivity().setProgress(0);

            Bitmap bitmap = bitmaps[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
            InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream

            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {
                HttpPost httppost = new HttpPost(
                        "http://172.31.76.184:8080/RICOH/sendphoto"); // server

                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("myFile",
                        System.currentTimeMillis() + ".jpg", in);


                httppost.setEntity(reqEntity);

//                Log.i(TAG, "request " + httppost.getRequestLine());
                HttpResponse response = null;
                try {
                    Log.e("response",String.valueOf(bitmap.getWidth()));
                    response = httpclient.execute(httppost);
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub

                            try {
                                Thread.sleep(1000);
                                handler.sendEmptyMessage(0);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }).start();

                    e.printStackTrace();
                }
                try {
                    if (response != null)
//                        Log.i(TAG, "response " + response.getStatusLine().toString());
                        System.out.println("response " + response.getStatusLine().toString());
                } finally {
                }
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }



            return null;
        }


            private Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 2:
                        Toast.makeText(getActivity(), "修改失败！", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "网络错误！上传失败！", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//            Toast.makeText(MainActivity.this, R.string.uploaded, Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(),"图片上传成功",Toast.LENGTH_SHORT).show();
//            imageView.setVisibility(View.VISIBLE);
        }
    }
}
