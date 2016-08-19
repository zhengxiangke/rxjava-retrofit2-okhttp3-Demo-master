package kfsdk.alex.sun.testrxmodule.httpdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by kf on 2016/8/19.
 * 提示框类
 */
public class HttpDialogHandle extends Handler{
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    private ProgressDialog dialog;
    private Context context;
    private  boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;
    public HttpDialogHandle(Context context, ProgressCancelListener mProgressCancelListener,
                            boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what==SHOW_PROGRESS_DIALOG){
            showProgressDialog();
        }else if(msg.what==DISMISS_PROGRESS_DIALOG){
            dismissProgressDialog();
        }
    }

    private void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void showProgressDialog() {
        if(dialog==null){
            dialog=new ProgressDialog(context);

            dialog.setCancelable(cancelable);

            if (cancelable) {
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
        }
        if(!dialog.isShowing())
        {
            dialog.show();
        }
    }
}
