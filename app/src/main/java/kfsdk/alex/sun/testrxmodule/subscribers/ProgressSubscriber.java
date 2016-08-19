package kfsdk.alex.sun.testrxmodule.subscribers;

import android.content.Context;
import android.widget.Toast;

import java.net.SocketException;

import kfsdk.alex.sun.testrxmodule.httpdialog.HttpDialogHandle;
import kfsdk.alex.sun.testrxmodule.httpdialog.ProgressCancelListener;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by kf on 2016/8/19.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private SubscriberOnnextListener subscriberOnnextListener;
    private Context context;
    private HttpDialogHandle httpDialogHandle;

    public ProgressSubscriber(SubscriberOnnextListener subscriberOnnextListener, Context context) {
        this.subscriberOnnextListener = subscriberOnnextListener;
        this.context = context;
        httpDialogHandle = new HttpDialogHandle(context, this, true);
    }


    @Override
    public void onStart() {
        Toast.makeText(context,Thread.currentThread().getName(),Toast.LENGTH_LONG).show();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
        Toast.makeText(context, "数据获取成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketException) {
            Toast.makeText(context, "网络有误 请检查你的网络", Toast.LENGTH_LONG).show();
        } else if (e instanceof HttpException) {
            Toast.makeText(context, "网络有误 请检查你的网络", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        dismissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        if (subscriberOnnextListener != null) {
            subscriberOnnextListener.onNext(t);
        }
    }

    private void dismissProgressDialog() {
        if (httpDialogHandle != null)
            httpDialogHandle.obtainMessage(HttpDialogHandle.DISMISS_PROGRESS_DIALOG).sendToTarget();

    }

    private void showProgressDialog() {
        if (httpDialogHandle != null)
            httpDialogHandle.obtainMessage(HttpDialogHandle.SHOW_PROGRESS_DIALOG).sendToTarget();

    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed())
            this.unsubscribe();
    }
}
