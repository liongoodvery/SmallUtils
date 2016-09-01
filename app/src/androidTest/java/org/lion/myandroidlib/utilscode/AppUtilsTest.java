package org.lion.myandroidlib.utilscode;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import com.blankj.utilcode.utils.AppUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by more on 2016-09-01 10:44:54.
 */
public class AppUtilsTest extends ApplicationTestCase<Application> {

    private final Gson mGson;
    private  static String mBrowerPackageName = "com.android.browser";

    public AppUtilsTest() {
        super(Application.class);
        mGson = new Gson();
    }

    public void testGetAllAppsInfo() throws Exception {
        StringBuilder sb = new StringBuilder();
        List<AppUtils.AppInfo> appInfos = AppUtils.getAllAppsInfo(getContext());
        for (AppUtils.AppInfo appInfo : appInfos) {
//            sb.append(mGson.toJson(appInfo)).append("\n");
            sb.append(ToStringBuilder.reflectionToString(appInfo)).append("\n");
        }
        Logger.i(sb.toString() + "");
    }

    public void testOpenAppInfo() throws Exception {
        AppUtils.openAppInfo(getContext(), mBrowerPackageName);
    }

    public void testOpenAppByPackageName(){
        AppUtils.openAppByPackageName(getContext(),mBrowerPackageName);
    }

    public void testIsInstallApp() throws Exception {
        assertTrue(AppUtils.isInstallApp(getContext(),mBrowerPackageName));
    }

}
