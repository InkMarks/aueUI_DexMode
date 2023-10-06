package com.aueui.dexmode.utils;


import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.aueui.dexmode.R;
import com.aueui.dexmode.activity.MainActivity;
import com.aueui.dexmode.entity.TaskBarEntity;
import com.aueui.dexmode.fragment.CatHouseFragment;
import com.aueui.dexmode.fragment.CatPhotoFragment;
import com.aueui.dexmode.fragment.EdgeFragment;
import com.aueui.dexmode.fragment.FileCategoryFragment;
import com.aueui.dexmode.fragment.FileViewFragment;
import com.aueui.dexmode.fragment.FlashlightFragment;
import com.aueui.dexmode.fragment.NetWorkDeviceFragment;
import com.aueui.dexmode.fragment.PCDetailsMessageFragment;
import com.aueui.dexmode.fragment.RecycleFragment;
import com.aueui.dexmode.fragment.SettingsFragment;
import com.aueui.dexmode.fragment.ThanksAboutFragment;
import com.aueui.dexmode.fragment.TranslateFragment;
import com.aueui.dexmode.fragment.base.BaseFragment;
import com.aueui.dexmode.touchlistener.FragmentTouchListener;

/**
 * Created by xlzhen on 9/7 0007.
 * fragment 工具
 */

public class FragmentUtils {
    private static FragmentManager fm;
    //fragment的窗口化在竖屏状态时，顶部的间距为160DP
    private static int windowTopPortraitMargin = DensityUtils.dp2px(160);
    //fragment的窗口化在横屏状态时，右边的间距为200DP
    private static int windowRightLandscapeMargin = DensityUtils.dp2px(1000);
    //fragment的窗口化在竖屏状态时，右边的间距为80DP
    private static int windowRightPortraitMargin = DensityUtils.dp2px(80);
    
    public static void initFragmentList(MainActivity mainActivity){
        windowTopPortraitMargin = DensityUtils.dp2px(AppUtis.isPad(mainActivity) ? 500 : 200);
        windowRightLandscapeMargin = DensityUtils.dp2px(AppUtis.isPad(mainActivity) ? 600 : 500);
        windowRightPortraitMargin = DensityUtils.dp2px(AppUtis.isPad(mainActivity) ? 260 : 40);
        
        mainActivity.edgeFragment = (EdgeFragment) initFragment(mainActivity,new EdgeFragment(), "Microsoft Edge"
                , R.mipmap.edge_white, R.id.fragment1, Color.parseColor("#0078d7")
                , BaseFragment.Theme.white, "");

        mainActivity.recycleFragment = (RecycleFragment) initFragment(mainActivity,new RecycleFragment(), "回收站"
                , R.mipmap.recyclestationnull, R.id.fragment2, Color.parseColor("#cccccc")
                , BaseFragment.Theme.black, "");

        mainActivity.fileViewFragment = (FileViewFragment) initFragment(mainActivity,new FileViewFragment(), "我的电脑"
                , R.mipmap.ic_launcher, R.id.fragment3, Color.parseColor("#cccccc")
                , BaseFragment.Theme.black, "");

        mainActivity.fileCategoryFragment = (FileCategoryFragment) initFragment(mainActivity,new FileCategoryFragment()
                , "文件资源管理器", R.mipmap.user_folder, R.id.fragment4, Color.parseColor("#cccccc")
                , BaseFragment.Theme.black, "");

        mainActivity.netWorkDeviceFragment = (NetWorkDeviceFragment) initFragment(mainActivity,new NetWorkDeviceFragment(), "网络"
                , R.mipmap.internet, R.id.fragment5, Color.parseColor("#cccccc")
                , BaseFragment.Theme.black, "");

        mainActivity.translateFragment = (TranslateFragment) initFragment(mainActivity,new TranslateFragment(), "翻译"
                , R.mipmap.translate, R.id.fragment6, Color.parseColor("#7463ff")
                , BaseFragment.Theme.white, "");

        mainActivity.flashlightFragment = (FlashlightFragment) initFragment(mainActivity,new FlashlightFragment(), "手电筒"
                , R.mipmap.flashlight, R.id.fragment7, Color.parseColor("#0078d7")
                , BaseFragment.Theme.white, "");

        mainActivity.pcDetailsMessageFragment = (PCDetailsMessageFragment) initFragment(mainActivity,new PCDetailsMessageFragment(), "系统信息"
                , R.mipmap.pc_details_icon, R.id.fragment8, Color.parseColor("#cccccc")
                , BaseFragment.Theme.black, "");

        mainActivity.catHouseFragment = (CatHouseFragment) initFragment(mainActivity,new CatHouseFragment(), "美猫"
                , R.mipmap.cat_logo, R.id.fragment9, Color.parseColor("#111111")
                , BaseFragment.Theme.white, "");

        mainActivity.catPhotoFragment = (CatPhotoFragment) initFragment(mainActivity,new CatPhotoFragment(), "美猫-看图"
                , R.mipmap.cat_logo, R.id.fragment10, Color.parseColor("#111111")
                , BaseFragment.Theme.white, "");

        mainActivity.thanksAboutFragment = (ThanksAboutFragment) initFragment(mainActivity,new ThanksAboutFragment(), "鸣谢"
                , R.mipmap.help, R.id.fragment11, Color.parseColor("#cccccc")
                , BaseFragment.Theme.black, "");
        mainActivity.settingsFragment = (SettingsFragment)  initFragment(mainActivity,new SettingsFragment(), "DEX设置"
                , R.mipmap.settings, R.id.fragment12, Color.parseColor("#cccccc")
                , BaseFragment.Theme.black, "");







    }

    private static BaseFragment initFragment(final MainActivity activity, BaseFragment baseFragment, String name, int icon, int layoutRes, int themeColor, BaseFragment.Theme theme, String url) {


        //设置fragment的name，方便后续从fragment取出，每个fragment指定唯一的name
        baseFragment.setName(name);
        //设置fragment的icon，方便后续从fragment取出，每个fragment指定唯一的icon
        baseFragment.setIcon(icon);
        //设置layoutId，方便后续从fragment取出，每个fragment指定唯一的layoutId
        baseFragment.setLayoutId(layoutRes);
        //设置fragment的窗口色
        baseFragment.setThemeColor(themeColor);
        //设置窗口的文字，图标颜色
        baseFragment.setTheme(theme);
        //设置触摸事件，让每个fragment都能来回拖动
        final FragmentTouchListener fragmentTouchListener = new FragmentTouchListener(activity, baseFragment.getLayoutId());
        activity.findViewById(baseFragment.getLayoutId()).setOnTouchListener(fragmentTouchListener);
        //设置最小化，最大化/窗口化，关闭 按钮事件
        baseFragment.setWindowMenuClickListener(new BaseFragment.WindowMenuClickListener() {
            @Override
            public void onMin(View view, BaseFragment baseFragment) {
                TaskBarEntity bean = activity.taskBarAdapter.getFragmentId(baseFragment.getLayoutId());
                if (!bean.isShow()) //如果不在显示状态，则显示fragment
                    FragmentUtils.showFragment(baseFragment);
                else //如果在显示状态，则关闭fragment
                    FragmentUtils.dismissFragment(baseFragment);


                bean.setShow(!bean.isShow());

                activity.taskBarAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onMax(boolean isWindowsMax, BaseFragment baseFragment) {
                if (!baseFragment.isVisible()) {
                    return !isWindowsMax;
                }
                Configuration configuration = baseFragment.getResources().getConfiguration();//判断当前的横竖屏状态

                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) baseFragment.getActivity().findViewById(baseFragment.getLayoutId())
                        .getLayoutParams();
                layoutParams.leftMargin = isWindowsMax ? 0 : 10;
                layoutParams.topMargin = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? (isWindowsMax ? 0 : AppUtis.isPad(activity) ? DensityUtils.dp2px(220) : 10) : (isWindowsMax ? 0 : windowTopPortraitMargin);

                layoutParams.rightMargin = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                        ? (isWindowsMax ? 0 : windowRightLandscapeMargin) : (isWindowsMax ? 0 : windowRightPortraitMargin);
                layoutParams.bottomMargin = isWindowsMax ? 0 : 10;

                baseFragment.getActivity().findViewById(baseFragment.getLayoutId()).setLayoutParams(layoutParams);


                fragmentTouchListener.setFragmentIsTouch(!isWindowsMax);
                isWindowsMax = !isWindowsMax;
                return isWindowsMax;
            }

            @Override
            public void onClose(View view, BaseFragment baseFragment) {
                FragmentUtils.closeFragment(baseFragment);
                activity.taskBarAdapter.remove(activity.taskBarAdapter.getFragmentId(baseFragment.getLayoutId()));
            }
        });

        return baseFragment;
    }

    public static void openFragment(MainActivity activity, Fragment fragment) {
        AppUtis.changeFragmentFocus(activity,((BaseFragment)fragment).getLayoutId());//更改fragment焦点
        fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(((BaseFragment)fragment).getLayoutId(), fragment);
        try {
            ft.commit();
        }catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public static void dismissFragment(Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fragment);
        try {
            transaction.commit();
        }catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public static void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.show(fragment);
        try {
            transaction.commit();
        }catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeFragment(Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.remove(fragment);
        try {
            transaction.commit();
        }catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeFragment(Fragment... fragments) {
        FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment fragment : fragments)
            transaction.remove(fragment);
        try {
            transaction.commit();
        }catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }
}
