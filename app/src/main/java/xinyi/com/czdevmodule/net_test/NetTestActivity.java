package xinyi.com.czdevmodule.net_test;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.xinyi.czbasedevtool.base.bean.BaseHttpResponseBean;
import com.xinyi.czbasedevtool.base.main.DefaultBaseAppCompatActivity;
import com.xinyi.czbasedevtool.base.manager.net_about.RetrofitClient;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import xinyi.com.czdevmodule.R;

/**
 * Created by 陈章 on 2017/4/7 0007.
 * func:
 */

public class NetTestActivity extends DefaultBaseAppCompatActivity {
    @Override
    public void annotationBind(@NonNull Activity target, View view) {
        super.annotationBind(target, view);
        ButterKnife.bind(this);
    }

    @Override
    public boolean useDataBinding() {
        return false;
    }


    @Override
    public int getLayoutID() {
        return R.layout.activity_net_test;
    }

    @Override
    public <BindingObj> void bindView(BindingObj binding) throws IOException {

    }

    @Override
    public void doOnCreateInit() {

    }

    @OnClick(R.id.btn_regsiter)
    public void onClick(View view) {
        Log.i(TAG, "onClick: ");
        RetrofitClient.setHasToken(true);
        RetrofitClient.setBaseUrl("http://app.bestbeijing.top/");
        requestData(view,200,SimpleRequestService.class,"regist",TestBean.class,new Object[]{"13121116226",1,"5613659"},false);
    }

    @Override
    public <T> void onSuccess(int requestCode, BaseHttpResponseBean codeInfoBean, T successBean) {

    }
}
