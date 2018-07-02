package com.mo.lawyercloud.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.FolderAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.FileInfo;
import com.mo.lawyercloud.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mohaifeng on 18/7/2.
 */
public class FolderActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ProgressDialog progressDialog;
    private ArrayList<FileInfo> wordData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_folder;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setMessage("正在加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                getFolderData();
            }
        }.start();

    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }


    @OnClick(R.id.bar_iv_back)
    public void onViewClicked() {
        finish();
    }

    /**
     * 遍历文件夹中资源
     */
    public void getFolderData() {

        scanDirNoRecursion(Environment.getExternalStorageDirectory().toString());
        handler.sendEmptyMessage(1);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //设置RecyclerView 布局
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                FolderAdapter pptListAdapter = new FolderAdapter(wordData);
                recyclerView.setAdapter(pptListAdapter);
                progressDialog.dismiss();
            }
        }
    };
    /**
     * 非递归
     *
     * @param path
     */
    public void scanDirNoRecursion(String path) {
        LinkedList list = new LinkedList();
        File dir = new File(path);
        File file[] = dir.listFiles();
        if (file == null) return;
        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory())
                list.add(file[i]);
            else {
                System.out.println(file[i].getAbsolutePath());
            }
        }
        File tmp;
        while (!list.isEmpty()) {
            tmp = (File) list.removeFirst();//首个目录
            if (tmp.isDirectory()) {
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++) {
                    if (file[i].isDirectory())
                        list.add(file[i]);//目录则加入目录列表，关键
                    else {
//                        System.out.println(file[i]);
                        if (file[i].getName().endsWith(".doc") || file[i].getName().endsWith(".docx")) {
                            FileInfo document = FileUtil.getFileInfoFromFile(new File(file[i].getAbsolutePath()));
                            wordData.add(document);
                        }
                    }
                }
            } else {
                System.out.println(tmp);
            }
        }
    }

}
