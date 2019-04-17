package com.at.arouter.common.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;


import com.at.arouter.common.R;
import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.common.util.FileUtils;
import com.at.arouter.common.util.GetPathFromUri;
import com.at.arouter.common.util.PictureUtil;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.common.util.Transformation;
import com.at.arouter.common.util.permission.PermissionListener;
import com.at.arouter.common.util.permission.PermissionsUtil;

import java.io.File;
import java.io.IOException;

/**
 * desc:  选择图片
 * author:  yangtao
 * <p>
 * creat: 2018/8/30 16:05
 */


public class OpenPictureActivity extends BaseActivity {

    private static final int SHOW_MAP_DEPOT = 1; // 显示Android图库
    private static final int OPEN_CAMERA = 2; // 打开Android照相机

    protected static final String IMAGE_UNSPECIFIED = "image/*";
    protected Bitmap bitmap;
    protected String picturePath; // 路径
    protected static String firstPicturePath; // 记录 - 第一次选择完图片的路径
    protected static String secondPicturePath; // 记录 - 第二次选择完图片的路径
    protected static String thirdPicturePath; // 记录 - 第三次选择完图片的路径
    protected int selector;

    protected File output;
    protected Uri imageUri;

    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {

    }


    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return null;
    }


    @Override
    protected void clearData() {
        firstPicturePath = null;
        secondPicturePath = null;
        thirdPicturePath = null;
        Transformation.recycleBitmap(bitmap);
        bitmap = null;
    }

    // Storage Permissions
    protected static final int REQUEST_EXTERNAL_STORAGE = 1;
    protected static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 动态申请图库权限
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     */
    public void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
            Log.d(getClass().getSimpleName(), "没权限");
        } else {
            Log.d(getClass().getSimpleName(), "打开图库");
            showPicture();
        }
    }

    protected static final int REQUEST_OPEN_CAMERA = 2;
    protected static String[] PERMISSIONS_CAMERA_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    /**
     * 动态申请相机权限
     */
    public void verifyCameraPermissions() {
        if (PermissionsUtil.hasPermission(this, PERMISSIONS_CAMERA_STORAGE)) {
            takePhoto();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    takePhoto();
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    ToastUtil.show(getString(R.string.refuse_permission));
                }
            }, "相机", PERMISSIONS_CAMERA_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            boolean permission = false;
            for (int item : grantResults) {
                if (item == PackageManager.PERMISSION_GRANTED) {
                    permission = true;
                } else {
                    permission = false;
                    break;
                }
            }
            if (permission) {
                showPicture();
            } else ToastUtil.show(mActivity.getResources().getString(R.string.not_set_permission));

        }
        if (requestCode == REQUEST_OPEN_CAMERA) {
            boolean permission = false;
            for (int item : grantResults) {
                if (item == PackageManager.PERMISSION_GRANTED) {
                    permission = true;
                } else permission = false;
            }
            if (permission) {
                takePhoto();
            } else ToastUtil.show(getString(R.string.not_set_permission));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(getClass().getSimpleName(), "requestCode:" + requestCode +
                " resultCode:" + requestCode +
                " data:" + data);
        Uri uri = null;
        switch (requestCode) {
            case SHOW_MAP_DEPOT:
                if (data == null) return;
                if (resultCode == Activity.RESULT_OK) uri = data.getData();
                Log.d(getClass().getSimpleName(), "_________++++++++++++++++_________" + uri);
                if (uri == null) return;
                picturePath = GetPathFromUri.getPath(this, uri);
                if (TextUtils.isEmpty(picturePath)) {
                    ToastUtil.show(getString(R.string.err_path));
                    return;
                }
                Log.d(getClass().getSimpleName(), "++++++默认++++++");
                rotatePicture7(picturePath,uri);//显示用到需要旋转
                picturePath = FileUtils.compressImageUpload(picturePath);//压缩图片
                break;
            case OPEN_CAMERA:
                if (resultCode == RESULT_OK) {
                    String url = FileUtils.getRealFilePathFromUri(getApplicationContext(), Uri.fromFile(output));
                    if (TextUtils.isEmpty(url)) {
                        ToastUtil.show("拍照失败！");
                    } else {
                        //通知图片生成成功
                        rotatePicture7(url,imageUri);//显示用到需要旋转
                        picturePath = FileUtils.compressImageUpload(url);//压缩图片

                    }
                }

                break;
        }
        Log.d(getClass().getSimpleName(), "得出结果码(resultCode)是：" + resultCode);
        Log.d(getClass().getSimpleName(), "Intent(data)是：" + data);
    }

    protected void getBitmap() {

        compressBitmap(picturePath);

        if (firstPicturePath == null) {
            firstPicturePath = picturePath;
        } else if (secondPicturePath == null) {
            secondPicturePath = picturePath;
        } else if (thirdPicturePath == null) {
            thirdPicturePath = picturePath;
        } else Log.d(getClass().getSimpleName(), "第四次路径？？？？:" + picturePath);
        Log.d(getClass().getSimpleName(), "第一次路径：" + firstPicturePath);
        Log.d(getClass().getSimpleName(), "第二次路径：" + secondPicturePath);
        Log.d(getClass().getSimpleName(), "第三次路径：" + thirdPicturePath);
        Log.d(getClass().getSimpleName(), "本次路径(执行)：" + picturePath);

    }

    private void rotatePicture7(String path,Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        options.inJustDecodeBounds = false;
        try {
            Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
            int degree = PictureUtil.readPictureDegree(path);
            if (degree != 0) {
                bitmap = PictureUtil.rotatingImageView(degree, bmp);
            } else {
                bitmap = bmp;
            }
            if (bitmap != null) {
                Log.e("bitmap 有数据...", "" + uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void compressBitmap(String path) {
        // 缩放图片, width, height 按相同比例缩放图片
        // 缩放图片, width, height 按相同比例缩放图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        // options 设为true时，构造出的bitmap没有图片，只有一些长宽等配置信息，但比较快，
        // 设为false时，才有图片
        /*options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(path, options);
        int scale = (int) (options.outWidth / (float) 300);
        if (scale <= 0) scale = 1;*/
        options.inSampleSize = 4;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        int degree = PictureUtil.readPictureDegree(path);
        if (degree != 0) bitmap = PictureUtil.rotatingImageView(degree, bitmap);
        mCount += 1;
        if (imageUri != null && mCount > 1 && bitmap != null) {
            if (firstPicturePath == null) {
                firstPicturePath = path;
                imageUri = null;
            } else if (secondPicturePath == null) {
                secondPicturePath = path;
                imageUri = null;
            } else if (thirdPicturePath == null) {
                thirdPicturePath = path;
                imageUri = null;
            }
            mCount = 0;
        }
    }

    protected int mCount;

    /**
     * 图库选择图片
     */
    private void showPicture() {

        //调用android自带的图库
        //Intent intent = new Intent(Intent.ACTION_PICK,
        //        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //选择图库
        //Intent intent = new Intent(Intent.ACTION_PICK, null);
        //添加所有图片
        //intent.setDataAndType(Uri.parse(MediaStore.Images.Media.DATA), IMAGE_UNSPECIFIED);

        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
        } else {
            intent = new Intent(Intent.ACTION_PICK, null);
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        //添加所有图片
        intent.setDataAndType(Uri.parse(MediaStore.Images.Media.DATA), IMAGE_UNSPECIFIED);
        startActivityForResult(intent, SHOW_MAP_DEPOT);
        // onActivityResult(SHOW_MAP_DEPOT, 1, intent);
    }

    /**
     * 打开相机照相
     */
    private void takePhoto() {

        /**
         * 最后一个参数是文件夹的名称
         */
        File file = new File(Environment.getExternalStorageDirectory(), "club");
        if (!file.exists()) {
            file.mkdir();
        }
        /**
         * 这里将时间作为不同照片的名称
         */
        output = new File(file, System.currentTimeMillis() + ".jpg");
        /**
         * 如果该文件夹已经存在，则删除它，否则创建一个
         */
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/provider_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            imageUri = FileProvider.getUriForFile(OpenPictureActivity.this,
                    Constants.PROVIDE,
                    output);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        }
        startActivityForResult(intent, OPEN_CAMERA);
    }

    public Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, Constants.PROVIDE, file);
        } else uri = Uri.fromFile(file);
        return uri;
    }


    protected void onActivityResultOld(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(getClass().getSimpleName(), "输入结果码是：" + resultCode);

        if (data != null) {
            if (requestCode == SHOW_MAP_DEPOT
                    && resultCode == Activity.RESULT_OK)
                showYourPic(data);
        }

        Log.d(getClass().getSimpleName(), "得出结果码是：" + resultCode);
        Log.d(getClass().getSimpleName(), "Intent是：" + data);

    }

    // 调用android自带图库，显示选中的图片
    protected void showYourPic(Intent data) {
        Uri selectedImage = data.getData();
        Log.d(getClass().getSimpleName(), "+++++++++=================+++++++++" + selectedImage);
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        if (picturePath.equals(""))
            return;

        String pic_path = picturePath; // 保存所添加的图片的路径

        // 缩放图片, width, height 按相同比例缩放图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        // options 设为true时，构造出的bitmap没有图片，只有一些长宽等配置信息，但比较快，设为false时，才有图片
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(picturePath, options);
        int scale = (int) (options.outWidth / (float) 300);
        if (scale <= 0)
            scale = 1;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(picturePath, options);

        //setPicture.setImageBitmap(bitmap);
        //setPicture.setMaxHeight(350);
        //setPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //setPicture.setVisibility(ImageView.VISIBLE);

    }

}
