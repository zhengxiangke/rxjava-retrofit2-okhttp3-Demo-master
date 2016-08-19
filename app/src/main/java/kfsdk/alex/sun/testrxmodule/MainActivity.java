package kfsdk.alex.sun.testrxmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kfsdk.alex.sun.testrxmodule.http.HttpManager;
import kfsdk.alex.sun.testrxmodule.module.Subject;
import kfsdk.alex.sun.testrxmodule.subscribers.ProgressSubscriber;
import kfsdk.alex.sun.testrxmodule.subscribers.SubscriberOnnextListener;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.click_me_BN)
    Button clickMeBN;
    @Bind(R.id.result_TV)
    TextView resultTV;
    private SubscriberOnnextListener subscriberOnnextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
         subscriberOnnextListener= new SubscriberOnnextListener() {
             @Override
             public void onNext(Object o) {
                 resultTV.setText(o.toString());
             }
         };
    }
    @OnClick(R.id.click_me_BN)
    public void click(){
        HttpManager.getInstance().getTopMovies(new ProgressSubscriber<List<Subject>>(subscriberOnnextListener,this),0,10);
    }
}
