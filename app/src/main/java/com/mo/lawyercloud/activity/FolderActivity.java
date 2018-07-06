package com.mo.lawyercloud.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mo.lawyercloud.R;
import com.mo.lawyercloud.adapter.FolderAdapter;
import com.mo.lawyercloud.base.BaseActivity;
import com.mo.lawyercloud.beans.BaseEntity;
import com.mo.lawyercloud.beans.FileInfo;
import com.mo.lawyercloud.beans.apiBeans.UploadFileBean;
import com.mo.lawyercloud.network.BaseObserver;
import com.mo.lawyercloud.network.RetrofitFactory;
import com.mo.lawyercloud.utils.FileUtil;
import com.mo.lawyercloud.utils.NToast;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Mohaifeng on 18/7/2.
 */
public class FolderActivity extends BaseActivity {

    @BindView(R.id.bar_title)
    TextView barTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.bar_tv_right)
    TextView barTvRight;

    private ProgressDialog progressDialog;
    private ArrayList<FileInfo> wordData = new ArrayList<>();
    private FolderAdapter mFolderAdapter;
    private UploadFileBean mUploadFileBean;
    private FileInfo mFileInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_folder;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        barTitle.setText("选择上传的文档");
        barTvRight.setText("上传");
        barTvRight.setVisibility(View.VISIBLE);
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


    @OnClick({R.id.bar_iv_back, R.id.bar_tv_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_iv_back:
                finish();
                break;
            case R.id.bar_tv_right:
                if (mFileInfo== null){
                    NToast.shortToast(mContext,"请选择需要上传的文件");
                    return;
                }
                uploadFile(new File(mFileInfo.getFilePath()));
                break;
        }

    }

    private void uploadFile(File file) {
        final ProgressDialog progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setMessage("正在上传中...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", file.getName(), body);//"files" 后台接收图片流的参数名
        List<MultipartBody.Part> parts = builder.build().parts();
        Observable<BaseEntity<UploadFileBean>> observable = RetrofitFactory.getInstance().uploadFile(parts);
        observable.compose(this.<BaseEntity<UploadFileBean>>rxSchedulers()).subscribe(new BaseObserver<UploadFileBean>() {
            @Override
            protected void onHandleSuccess(UploadFileBean uploadFileBean, String msg) {
                progressDialog.dismiss();
                NToast.shortToast(mContext,msg);
                if (uploadFileBean != null) {
                    Intent intent = new Intent();
                    intent.putExtra("file", uploadFileBean);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }

            @Override
            protected void onHandleError(int statusCode, String msg) {
                super.onHandleError(statusCode, msg);
                progressDialog.dismiss();
                NToast.shortToast(mContext,msg);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                progressDialog.dismiss();
                NToast.shortToast(mContext,"网络出错");
            }
        });
    }



    /**
     * 遍历文件夹中资源
     */
    public void getFolderData() {

        scanDirNoRecursion(Environment.getExternalStorageDirectory().toString());
        handler.sendEmptyMessage(1);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //设置RecyclerView 布局
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                mFolderAdapter = new FolderAdapter(wordData);
                recyclerView.setAdapter(mFolderAdapter);
                mFolderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        mFileInfo = mFolderAdapter.getData().get(position);
                        mFolderAdapter.setSelectItem(position);
                    }
                });
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
                        if (file[i].getName().endsWith(".doc") || file[i].getName().endsWith("" +
                                ".docx")) {
                            FileInfo document = FileUtil.getFileInfoFromFile(new File(file[i]
                                    .getAbsolutePath()));
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
