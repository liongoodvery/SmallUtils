package org.lion.utilslib;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by more on 2016-08-27 20:51:25.
 */
public class AndroidUtils {
    private AndroidUtils() {
    }

    private static final String TAG = "====_AndroidUtils";

    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            Log.i(TAG, "MobUtils.getVersionName() " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            Log.i(TAG, "MobUtils.getVersionName() " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public static void showKeyboard(EditText edittext) {
        if (edittext != null) {
            Object showing = edittext.getTag();
            if (showing != null) {
                return;
            }
        }
        try {
            InputMethodManager imm = (InputMethodManager) edittext.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edittext, 0);
            edittext.setTag(new Object());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    public static void hideKeyboard(EditText edittext) {
        if (edittext == null || edittext.getTag() == null) {
            return;
        }
        try {
            InputMethodManager imm = ((InputMethodManager) edittext
                    .getContext().getSystemService(
                            Activity.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(edittext.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            edittext.setTag(null);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 获取某个字符传的md5
     *
     * @param password
     * @return
     */
    public static String getMd5(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());

            StringBuffer sb = parseMd5(result);

            // System.out.println(sb.toString());//打印得到的md5
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // 如果算法不存在的话,就会进入该方法中
            e.printStackTrace();
        }

        return "";
    }

    private static StringBuffer parseMd5(byte[] result) {
        StringBuffer sb = new StringBuffer();
        for (byte b : result) {
            int i = b & 0xff;// 将字节转为整数
            String hexString = Integer.toHexString(i);// 将整数转为16进制
            if (hexString.length() == 1) {
                hexString = "0" + hexString;// 如果长度等于1, 加0补位
            }
            sb.append(hexString);
        }
        return sb;
    }

    /**
     * 获取某个文件的md5
     *
     * @param path
     * @return
     */
    public static String getFileMd5(String path) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(path);

            int len = 0;
            byte[] buffer = new byte[1024];

            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }

            byte[] result = digest.digest();

            StringBuffer sb = parseMd5(result);

            // System.out.println(sb.toString());// 打印得到的md5
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static boolean isServiceRunning(Context context, Class<? extends Service> service) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = manager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : services) {
            if (TextUtils.equals(info.service.getClassName(), service.getName())) {
                return true;
            }
        }
        return false;
    }

    public static long getFileSize(String path) {
        File file = new File(path);
        return file.length();
    }

    public static void close(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    public static void close(SQLiteDatabase db) {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }


    /**
     * Copy an InputStream to an OutputStream
     *
     * @param in  a InputStream
     * @param out a OutputStream
     */
    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[1 << 12];
        int len;

        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }

        closeStream(out);
        closeStream(in);
    }

    public static void putSharePreference(Context context, String sp_name, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }




    public static void putBoolean(Context context, String sp_name, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();

    }

    public static void putString(Context context, String key, String value) {
        context.getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE).edit().putString(key, value).apply();

    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE).getString(key, "");
    }

    public static void putBoolean(Context context, String key, boolean value) {
        putBoolean(context, Consts.SP_NAME, key, value);

    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();

    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static boolean getBoolean(Context context, String sp_name, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, value);

    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);

    }

    public static boolean getBooleanFalse(Context context, String sp_name, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);

    }

    public static String getSimNumber(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getSimSerialNumber();
    }

    public static void sendMsm(String safeNumber, String s) {
        SmsManager.getDefault().sendTextMessage(safeNumber, null, s, null, null);

    }

    public static boolean killCall(Context context) {
        try {
            // Get the boring old TelephonyManager
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            // Get the getITelephony() method
            Class classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");

            // Ignore that the method is supposed to be private
            methodGetITelephony.setAccessible(true);

            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

            // Get the endCall method from ITelephony
            Class telephonyInterfaceClass =
                    Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);

        } catch (Exception ex) { // Many things can go wrong with reflection calls
            ex.printStackTrace();
            Log.d(TAG, "PhoneStateReceiver **" + ex.toString());
            return false;
        }
        return true;
    }

    public static void zip(File sourceFile, File targetFile) throws IOException {
        FileInputStream fin = new FileInputStream(sourceFile);
        GZIPOutputStream fout = new GZIPOutputStream(new FileOutputStream(targetFile));
        copy(fin, fout);
    }

    public static void unZip(File sourceFile, File targetFile) throws IOException {
        GZIPInputStream fin = new GZIPInputStream(new FileInputStream(sourceFile));
        FileOutputStream fout = new FileOutputStream(targetFile);
        copy(fin, fout);
    }

    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static TextView createTextView(Context context, String text) {
        TextView tv = new TextView(context);
        tv.setPadding(10, 10, 10, 10);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(24);
        tv.setTextColor(Color.RED);
        return tv;
    }

    public static String createRandomText(List<String> strings, int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(strings.get(random.nextInt(length)));
        }
        return sb.toString();
    }

    public static String createRandomText(int length) {
        final int alphaCount = 26;
        List<String> strings = new ArrayList<>(alphaCount);
        for (int i = 0; i < alphaCount; i++) {
            strings.add(String.valueOf((char)('a' + i)));
        }
        return createRandomText(strings, length);
    }

    public static String createRandomText() {
        return createRandomText(10);
    }

    public static int getRandomColor(int min , int max) {
        if (min>max){
            throw new IllegalArgumentException("min can not be greater than max");
        }
        int diff = max - min;
        Random random = new Random();
        int r = min + random.nextInt(diff);
        int g = min + random.nextInt(diff);
        int b = min + random.nextInt(diff);
        int a = min + random.nextInt(diff);
        return Color.argb(a,r,g,b);
    }


    public static int getRandomColor() {
        return getRandomColor(50,200);
    }


    /**
     * 获取屏幕尺寸
     *
     * @param activity
     *            Activity
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    public static int[] getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[] { metrics.widthPixels, metrics.heightPixels };
    }



}
