package com.melonltd.naberc.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.melonltd.naberc.model.constant.NaberConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PhotoTools {
    private static final String TAG = PhotoTools.class.getSimpleName();


    private static final  int MULTIPLE = 4;
    public static Bitmap sampleBitmap(Bitmap bitmap , int minLenDP) {
        int dp = minLenDP * MULTIPLE;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        Log.w(TAG, "原圖 ---> size: " + (bitmap.getByteCount() / 1024)  + " width: " + bitmap.getWidth() + " heigth:" + bitmap.getHeight());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        byte[] bytes = out.toByteArray();
        BitmapFactory.decodeByteArray(bytes, 0 , bytes.length, options);
        int height = options.outHeight;
        int width= options.outWidth;
        int inSampleSize = 1; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.max(height, width); // 原图的最小边长
        if(minLen > dp) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
            float ratio = (float)minLen /dp; // 计算像素压缩比例
            Log.w(TAG, "壓縮比 ---> ratio: " + ratio);
            inSampleSize = (int)ratio;
            Log.w(TAG, "壓縮比 ---> inSampleSize: " + inSampleSize);
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bbm = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length, options); // 解码文件
        Log.w(TAG, "壓縮 ---> size: " + (bbm.getByteCount() / 1024) + "KB" + " width: " + bbm.getWidth() + " heigth:" + bbm.getHeight());
        return bbm;
    }


    public static byte[] sampleToByteArray(Bitmap bitmap , int minLenDP) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        byte[] bytes = out.toByteArray();
//        BitmapFactory.decodeByteArray(bytes, 0 , bytes.length, options);
        int height = options.outHeight;
        int width= options.outWidth;
        int inSampleSize = 2; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.min(height, width); // 原图的最小边长
        if(minLen > minLenDP) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
            float ratio = (float)minLen / 100.0f; // 计算像素压缩比例
            inSampleSize = (int)ratio;
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bbm = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length, options); // 解码文件

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bbm.compress(Bitmap.CompressFormat.JPEG, 100, out);
        return outputStream.toByteArray();
    }

    public static StorageReference getReference(String path, String child, String fileName){
        return FirebaseStorage
                .getInstance(path)
                .getReference()
                .child(child + fileName);
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static String bitmap2Base64String(Bitmap bm) {
        byte[] b64file = Base64.encode(bitmap2Bytes(bm), Base64.DEFAULT);
        return new String(b64file, StandardCharsets.UTF_8);
    }

    public static byte[] bitmap2Base64(Bitmap bm) {
        byte[] b64file = Base64.encode(bitmap2Bytes(bm), Base64.DEFAULT);
        return b64file;
    }

    public static File bitmap2File(Bitmap bm, File file, String filename) throws IOException {
        File f = new File(file, filename);
        f.createNewFile();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100/*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }

}
