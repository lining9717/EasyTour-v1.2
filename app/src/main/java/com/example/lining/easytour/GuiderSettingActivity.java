package com.example.lining.easytour;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiderSettingActivity extends AppCompatActivity {
//    /*PO*/
//    //定义图片的Uri
//    private Uri photoUri;
//    //图片文件路径
//    private String picPath;
//    private GuiderSettingActivity context;
//    private PopupWindow mPopWindow;
//    private ImageView showImageView;
//
//    /*PHOTOTEST*/
//    public static final int TAKE_PHOTO = 1;
//
//    public static final int CHOOSE_PHOTO = 2;
//
//    public static final int CROP_PHOTO = 3;
//
//    private File cameraFile;
//    private  String cachPath;
//    private File cacheFile;
//
//    private Uri imageUri;
//    //相机
//    private Button camera;
//    //相册
//    private Button album;
//    //裁剪后显示照片
//    private ImageView imageView;
//    //动态获取权限监听
//    private static PermissionListener mgSettingListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider_setting);
        init();
    }

    private void init() {
//        this.context = this;
//        showImageView = (ImageView) findViewById(R.id.g_setting_iv_icon);
//
//        cachPath=getDiskCacheDir(this)+ "/crop_image.jpg";
//        cacheFile =getCacheFile(new File(getDiskCacheDir(this)),"crop_image.jpg");
//        imageView= (ImageView) findViewById(R.id.g_setting_iv_icon);
    }

    public void btnUpdate(View view) {
        Toast.makeText(GuiderSettingActivity.this,"Updating the new information",Toast.LENGTH_SHORT).show();
    }
//
//    public void ivGChangeUserIcon(View view) {
//        showUploadAvatarDialog();
//    }
//    private void showUploadAvatarDialog() {
//        View view = getLayoutInflater().inflate(R.layout.popu_photo, null);
//        int screenWith = context.getWindowManager().getDefaultDisplay().getWidth();
//        int screenHeiht = context.getWindowManager().getDefaultDisplay().getHeight();
//        mPopWindow = new PopupWindow(view, screenWith, screenHeiht);
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
//        // 我觉得这里是API的一个bug
//        mPopWindow.setBackgroundDrawable(dw);
//        mPopWindow.setTouchable(true);
//        mPopWindow.setFocusable(true);
//        mPopWindow.showAtLocation(showImageView, Gravity.BOTTOM, 0, 0);
//        mPopWindow.setOutsideTouchable(true);
//        TextView takePhoto = (TextView) view.findViewById(R.id.tv_take_photo_popu);
//        TextView photoAlbum = (TextView) view.findViewById(R.id.tv_photo_album_popu);
//        TextView cancel = (TextView) view.findViewById(R.id.tv_photo_cancle_popu);
//
//
//        takePhoto.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mPopWindow.isShowing()) {
//                    mPopWindow.dismiss();
//                    takePhotoForCamera();
//                }
//                return false;
//            }
//        });
//        photoAlbum.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mPopWindow.isShowing()) {
//                    mPopWindow.dismiss();
//                    takePhotoForAlbum();
//                }
//                return false;
//            }
//        });
//
//        cancel.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mPopWindow.isShowing()) {
//                    mPopWindow.dismiss();
//                }
//                return false;
//            }
//        });
//    }
//    private void takePhotoForAlbum() {
//
//        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
//        requestRuntimePermission(permissions, new PermissionListener() {
//            @Override
//            public void onGranted() {
//                openAlbum();
//            }
//
//            @Override
//            public void onDenied(List<String> deniedPermission) {
//                //没有获取到权限，什么也不执行，看你心情
//            }
//        });
//    }
//    private void openAlbum() {
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
//        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
//    }
//    private void takePhotoForCamera() {
//        String[] permissions={Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        requestRuntimePermission(permissions, new PermissionListener() {
//            @Override
//            public void onGranted() {
//                openCamera();
//            }
//
//            @Override
//            public void onDenied(List<String> deniedPermission) {
//                //有权限被拒绝，什么也不做好了，看你心情
//            }
//        });
//
//    }
//    private void openCamera() {
//
//        cameraFile = getCacheFile(new File(getDiskCacheDir(this)),"output_image.jpg");
//
//
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            imageUri = Uri.fromFile(cameraFile);
//        } else {
//
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            imageUri = FileProvider.getUriForFile(GuiderSettingActivity.this, "com.wing.phototest.fileprovider", cameraFile);
//
//        }
//        // 启动相机程序
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, TAKE_PHOTO);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_CANCELED) {
//            return;
//        }
//        switch (requestCode) {
//            case TAKE_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    try {
//
//                        // 将拍摄的照片显示出来
//
//                        startPhotoZoom(cameraFile,350);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//
//            case CHOOSE_PHOTO:
//                if (resultCode == RESULT_OK) {
//                    // 判断手机系统版本号
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        // 4.4及以上系统使用这个方法处理图片
//                        handleImageOnKitKat(data);
//                    } else {
//                        // 4.4以下系统使用这个方法处理图片
//                        handleImageBeforeKitKat(data);
//                    }
//                }
//                break;
//
//            case CROP_PHOTO:
//                try {
//                    if (resultCode==RESULT_OK){
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(cachPath))));
//                        imageView.setImageBitmap(bitmap);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                break;
//        }
//    }
//    private  File getCacheFile(File parent, String child) {
//        // 创建File对象，用于存储拍照后的图片
//        File file = new File(parent, child);
//
//        if (file.exists()) {
//            file.delete();
//        }
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return file;
//    }
//    @TargetApi(19)
//    private void handleImageOnKitKat(Intent data) {
//        Uri uri = data.getData();
//        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
//        String imagePath= uriToPath(uri);
////        displayImage(imagePath); // 根据图片路径显示图片
//
//        Log.i("TAG","file://"+imagePath+"选择图片的URI"+uri);
//        startPhotoZoom(new File(imagePath),350);
//    }
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private String uriToPath(Uri uri) {
//        String path=null;
//        if (DocumentsContract.isDocumentUri(this, uri)) {
//            // 如果是document类型的Uri，则通过document id处理
//            String docId = DocumentsContract.getDocumentId(uri);
//            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
//                String id = docId.split(":")[1]; // 解析出数字格式的id
//                String selection = MediaStore.Images.Media._ID + "=" + id;
//                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
//                path = getImagePath(contentUri, null);
//            }
//        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            // 如果是content类型的Uri，则使用普通方式处理
//            path = getImagePath(uri, null);
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            // 如果是file类型的Uri，直接获取图片路径即可
//            path = uri.getPath();
//        }
//        return  path;
//    }
//    private void handleImageBeforeKitKat(Intent data) {
//        Uri uri = data.getData();
//        String imagePath = getImagePath(uri, null);
////        displayImage(imagePath);
//        Log.i("TAG","file://"+imagePath+"选择图片的URI"+uri);
//        startPhotoZoom(new File(imagePath),350);
//    }
//    private String getImagePath(Uri uri, String selection) {
//        String path = null;
//        // 通过Uri和selection来获取真实的图片路径
//        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//    /**
//     * 剪裁图片
//     */
//    private void startPhotoZoom(File file, int size) {
//        Log.i("TAG",getImageContentUri(this,file)+"裁剪照片的真实地址");
//        try {
//            Intent intent = new Intent("com.android.camera.action.CROP");
//            intent.setDataAndType(getImageContentUri(this,file), "image/*");//自己使用Content Uri替换File Uri
//            intent.putExtra("crop", "true");
//            intent.putExtra("aspectX", 1);
//            intent.putExtra("aspectY", 1);
//            intent.putExtra("outputX", 180);
//            intent.putExtra("outputY", 180);
//            intent.putExtra("scale", true);
//            intent.putExtra("return-data", false);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(cacheFile));//定义输出的File Uri
//            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//            intent.putExtra("noFaceDetection", true);
//            startActivityForResult(intent, CROP_PHOTO);
//        } catch (ActivityNotFoundException e) {
//            String errorMessage = "Your device doesn't support the crop action!";
//            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public String getDiskCacheDir(Context context) {
//        String cachePath = null;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                || !Environment.isExternalStorageRemovable()) {
//            cachePath = context.getExternalCacheDir().getPath();
//        } else {
//            cachePath = context.getCacheDir().getPath();
//        }
//        return cachePath;
//    }
//    public static Uri getImageContentUri(Context context, File imageFile) {
//        String filePath = imageFile.getAbsolutePath();
//        Cursor cursor = context.getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                new String[] { MediaStore.Images.Media._ID },
//                MediaStore.Images.Media.DATA + "=? ",
//                new String[] { filePath }, null);
//
//        if (cursor != null && cursor.moveToFirst()) {
//            int id = cursor.getInt(cursor
//                    .getColumnIndex(MediaStore.MediaColumns._ID));
//            Uri baseUri = Uri.parse("content://media/external/images/media");
//            return Uri.withAppendedPath(baseUri, "" + id);
//        } else {
//            if (imageFile.exists()) {
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.DATA, filePath);
//                return context.getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            } else {
//                return null;
//            }
//        }
//    }
//    public void requestRuntimePermission(String[] permissions, PermissionListener listener) {
//
//        mgSettingListener = listener;
//        List<String> permissionList = new ArrayList<>();
//        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(GuiderSettingActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
//                permissionList.add(permission);
//            }
//        }
//        if (!permissionList.isEmpty()) {
//            ActivityCompat.requestPermissions(GuiderSettingActivity.this, permissionList.toArray(new String[permissionList.size()]), 1);
//        } else {
//            mgSettingListener.onGranted();
//        }
//    }
//    @TargetApi(23)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0) {
//                    List<String> deniedPermissions = new ArrayList<>();
//                    for (int i = 0; i < grantResults.length; i++) {
//                        int grantResult = grantResults[i];
//                        String permission = permissions[i];
//                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                            deniedPermissions.add(permission);
//                        }
//                    }
//                    if (deniedPermissions.isEmpty()) {
//                        mgSettingListener.onGranted();
//                    } else {
//                        mgSettingListener.onDenied(deniedPermissions);
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//    }
//    public interface PermissionListener {
//        /**
//         * 成功获取权限
//         */
//        void onGranted();
//
//        /**
//         * 为获取权限
//         * @param deniedPermission
//         */
//        void onDenied(List<String> deniedPermission);
//
//    }
}
