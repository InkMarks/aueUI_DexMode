package com.aueui.dexmode.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.aueui.dexmode.BaseApplication;
import com.aueui.dexmode.R;
import com.aueui.dexmode.activity.MainActivity;
import com.aueui.dexmode.adapter.AppGridAdapter;
import com.aueui.dexmode.entity.DesktopEntity;
import com.aueui.dexmode.fragment.base.BaseFragment;
import com.aueui.dexmode.utils.AppUtis;
import com.aueui.dexmode.widget.MenuPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xlzhen on 9/14 0014.
 * 回收站
 */

public class RecycleFragment extends BaseFragment {
    private AppGridAdapter appGridAdapter;
    @Override
    protected void initData() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //setActionBarTitle("回收站");

        appGridAdapter = new AppGridAdapter(getActivity(), AppUtis.isPad(getContext())?8:4, BaseApplication.recycleBinEntities, Color.parseColor("#000000"), null, new AppGridAdapter.OnLongClickListener() {
            @Override
            public void onClick(View view, final int position, final DesktopEntity desktopEntity) {
                final MenuPopupWindow menuPopupWindow=new MenuPopupWindow(getActivity(), MenuPopupWindow.Style.white);
                List<String> menuData=new ArrayList<>();
                menuData.add("彻底删除");
                menuData.add("还原");
                menuPopupWindow.setMenuData(menuData, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){
                            case 0:
                                BaseApplication.recycleBinEntities.remove(position);
                                AppUtis.saveRecycleData(getActivity());
                                appGridAdapter.notifyDataSetChanged();
                                break;
                            case 1:
                                BaseApplication.desktopAppEntities.add(desktopEntity);
                                ((MainActivity)getActivity()).getAppGridAdapter().notifyDataSetChanged();
                                AppUtis.saveDesktopData(getActivity());
                                BaseApplication.recycleBinEntities.remove(position);
                                AppUtis.saveRecycleData(getActivity());
                                appGridAdapter.notifyDataSetChanged();
                                break;
                        }
                        if(BaseApplication.recycleBinEntities.size()==0) {
                            ((MainActivity)getActivity()).getAppGridAdapter().getRecycleBin().setAppIcon("空");
                            ((MainActivity)getActivity()).getAppGridAdapter().notifyDataSetChanged();
                            AppUtis.saveDesktopData(getActivity());
                        }
                        menuPopupWindow.dismiss();
                    }
                });
                menuPopupWindow.showAsDropDown(view);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), AppUtis.isPad(getContext())?8:4));
        recyclerView.setAdapter(appGridAdapter);
    }

    @Override
    protected void initWidget() {

    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return setFragmentView(inflater.inflate(R.layout.fragment_recycle, container, false));
    }

    @Override
    protected void onClick(View view, int viewId) {

    }

    @Override
    public boolean onBackKey() {
        return true;
    }
}
