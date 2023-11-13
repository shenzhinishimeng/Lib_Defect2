package com.catail.lib_commons.base;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.catail.lib_commons.interfaces.UiOpration;
import com.catail.lib_commons.utils.Utils;


public abstract class BaseFragment extends Fragment implements UiOpration {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewLayoutId(), null);
        initView(view);
        return view;
    }

    /**
     * 显示吐司消息
     *
     * @param msg 吐司消息
     */
    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示吐司消息
     */
    public void showNoDataToast() {
        Toast.makeText(getActivity(), getResources().getString(com.catail.lib_commons.R.string.no_data),
                Toast.LENGTH_SHORT).show();
    }

    private Dialog loadingDialog;

    /**
     * 显示ProgressDialog
     */
    protected void showProgressDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = Utils.createLoadingDialog(getActivity(), msg);
        }
        loadingDialog.show();
    }

    /**
     * 隐藏progressDialog
     */
    protected void dismissProgressDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

}
