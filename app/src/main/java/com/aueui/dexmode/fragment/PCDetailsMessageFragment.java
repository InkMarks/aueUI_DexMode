package com.aueui.dexmode.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aueui.dexmode.R;
import com.aueui.dexmode.fragment.base.BaseFragment;
import com.aueui.dexmode.utils.AppUtis;
import com.windows.explorer.utils.Util;

/*
 *xlzhen 2018/4/27
 * 系统信息
 */
public class PCDetailsMessageFragment extends BaseFragment {


    @Override
    protected void initData() {



        ((TextView) findViewById(R.id.windows_version)).setText("Windows 10 Desktop By Android " + Build.VERSION.RELEASE + " XPP");
        ((TextView) findViewById(R.id.windows_cpu)).setText("处理器：" + AppUtis.getCpuInfo());
        ((TextView) findViewById(R.id.windows_memory)).setText("已安装的内存(RAM)：" + Util.convertStorage(AppUtis.getTotalMemory(getContext())));
        ((TextView) findViewById(R.id.windows_system_type)).setText("系统类型：" + (!AppUtis.checkIfCPUx86()
                ? "64位操作系统，基于x64的处理器" : "32位操作系统，基于x86的处理器"));

        ((TextView) findViewById(R.id.windows_name)).setText("计算机名：" + Build.MODEL);


    }

    @Override
    protected void initWidget() {

    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return setFragmentView(inflater.inflate(R.layout.fragment_pc_details_message, container, false));
    }

    @Override
    protected void onClick(View view, int viewId) {

    }


    @Override
    public boolean onBackKey() {
        return true;
    }


}
