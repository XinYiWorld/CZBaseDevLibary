package xinyi.com.czdevmodule;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.xinyi.czbasedevtool.base.bean.BaseHttpResponseBean;
import com.xinyi.czbasedevtool.base.main.DefaultBaseFragment;
import com.xinyi.czbasedevtool.base.manager.DataCleanManager;
import com.xinyi.czbasedevtool.base.manager.net_about.RetrofitClient;
import com.xinyi.czbasedevtool.base.view.ContentViewHolder;
import com.xinyi.czbasedevtool.base.view.round.RoundTextView;

import java.io.IOException;

import xinyi.com.czdevmodule.net_test.SimpleRequestService;
import xinyi.com.czdevmodule.net_test.retrofit.DegreeBean;
import xinyi.com.czdevmodule.net_test.retrofit.UserBean;
import xinyi.com.czdevmodule.net_test.retrofit.callback.IUserRegistCallBack;
import xinyi.com.czdevmodule.net_test.retrofit.service.UserService;

/**
 * Created by 陈章 on 2017/2/14 0014.
 * func:
 */

public class Fragment1 extends DefaultBaseFragment implements IUserRegistCallBack {
    @Override
    public boolean useDataBinding() {
        return false;
    }



    @Override
    public int getLayoutID() {
        return R.layout.fragment1;
    }

    @Override
    public <BindingObj> void bindView(BindingObj binding) throws IOException {

    }


    @Override
    public void bindView(ContentViewHolder contentViewHolder) throws IOException {
        super.bindView(contentViewHolder);

        final DataCleanManager dataCleanManager = DataCleanManager.getInstance(mContext);

        final RoundTextView roundTextView = contentViewHolder.findViewById(R.id.test);
        roundTextView.setGravity(Gravity.CENTER);
        roundTextView.setTextColor(Color.WHITE);//设置白色字体
        roundTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        roundTextView.setEnabled(true);


        final UserService userService = new UserService(mContext, this);
        userService.setRegistCallBack(this);



        roundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BASE_URL = "http://app.bestbeijing.top/";
                RetrofitClient.setHasToken(true);
                RetrofitClient.setBaseUrl(BASE_URL);
                requestData(v,100, SimpleRequestService.class,"getDegreeList",DegreeBean.class,new Object[]{"fdsaf"},true);
            }
        });



        contentViewHolder.findViewById(R.id.test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void doOnCreateInit() {

    }

    @Override
    public String getLeftTextString() {
        return "232543543";
    }


    @Override
    public <T> void onSuccess(int requestCode, BaseHttpResponseBean codeInfoBean, T successBean) {

    }


    @Override
    public void onRegistSucess(UserBean userBean) {

    }

    @Override
    public void onRegistFailure() {

    }
}
