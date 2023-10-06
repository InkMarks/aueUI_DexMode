/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * This file is part of FileExplorer.
 *
 * FileExplorer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FileExplorer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SwiFTP.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.windows.explorer.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;


import com.aueui.dexmode.R;
import com.windows.explorer.entity.FileInfo;
import com.windows.explorer.helper.FileIconHelper;
import com.windows.explorer.utils.Util;

import java.io.File;

public class InformationDialog extends AlertDialog {
    protected static final int ID_USER = 100;
    private FileInfo mFileInfo;
    private FileIconHelper mFileIconHelper;
    private Context mContext;
    private View mView;

    public InformationDialog(Context context, FileInfo f, FileIconHelper iconHelper) {
        super(context);
        mFileInfo = f;
        mFileIconHelper = iconHelper;
        mContext = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        mView = getLayoutInflater().inflate(R.layout.information_dialog, null);

        if (mFileInfo.isDir()) {
            setIcon(R.mipmap.folder);
            asyncGetSize();
        } else {
            setIcon(R.mipmap.blank);
        }
        setTitle(mFileInfo.getFileName());

        ((TextView) mView.findViewById(R.id.information_size))
                .setText(formatFileSizeString(mFileInfo.getFileSize()));
        ((TextView) mView.findViewById(R.id.information_location))
                .setText(mFileInfo.getFilePath());
        ((TextView) mView.findViewById(R.id.information_modified)).setText(Util
                .formatDateString(mContext, mFileInfo.getModifiedDate()));
        ((TextView) mView.findViewById(R.id.information_canread))
                .setText(mFileInfo.isCanRead() ? R.string.yes : R.string.no);
        ((TextView) mView.findViewById(R.id.information_canwrite))
                .setText(mFileInfo.isCanWrite() ? R.string.yes : R.string.no);
        ((TextView) mView.findViewById(R.id.information_ishidden))
                .setText(mFileInfo.isHidden() ? R.string.yes : R.string.no);

        setView(mView);
        setButton(BUTTON_NEGATIVE, "知道了", (OnClickListener) null);

        super.onCreate(savedInstanceState);
    }

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ID_USER:
                    Bundle data = msg.getData();
                    long size = data.getLong("SIZE");
                    ((TextView) mView.findViewById(R.id.information_size)).setText(formatFileSizeString(size));
            }
        };
    };

    private AsyncTask task;

    @SuppressWarnings("unchecked")
    private void asyncGetSize() {
        task = new AsyncTask() {
            private long size;

            @Override
            protected Object doInBackground(Object... params) {
                String path = (String) params[0];
                size = 0;
                getSize(path);
                task = null;
                return null;
            }

            private void getSize(String path) {
                if (isCancelled())
                    return;
                File file = new File(path);
                if (file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    if (listFiles == null)
                        return;

                    for (File f : listFiles) {
                        if (isCancelled())
                            return;

                        getSize(f.getPath());
                    }
                } else {
                    size += file.length();
                    onSize(size);
                }
            }

        }.execute(mFileInfo.getFilePath());
    }

    private void onSize(final long size) {
        Message msg = new Message();
        msg.what = ID_USER;
        Bundle bd = new Bundle();
        bd.putLong("SIZE", size);
        msg.setData(bd);
        mHandler.sendMessage(msg); // 向Handler发送消息,更新UI
    }

    private String formatFileSizeString(long size) {
        String ret = "";
        if (size >= 1024) {
            ret = Util.convertStorage(size);
            ret += (" (" + mContext.getResources().getString(R.string.file_size, size) + ")");
        } else {
            ret = mContext.getResources().getString(R.string.file_size, size);
        }

        return ret;
    }
}
