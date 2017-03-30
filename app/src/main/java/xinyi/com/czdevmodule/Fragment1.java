package xinyi.com.czdevmodule;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;

import com.xinyi.czbasedevtool.base.main.DefaultBaseFragment;
import com.xinyi.czbasedevtool.base.manager.PermissionManager;
import com.xinyi.czbasedevtool.base.view.ContentViewHolder;
import com.xinyi.czbasedevtool.base.view.round.RoundCheckBox;

import java.io.IOException;

/**
 * Created by 陈章 on 2017/2/14 0014.
 * func:
 */

public class Fragment1 extends DefaultBaseFragment {
    @Override
    public boolean useDataBinding() {
        return false;
    }

    @Override
    public <T> void onSuccess(int requestCode, T successBean) {

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
        ViewGroup test = contentViewHolder.findViewById(R.id.test);
        RoundCheckBox roundTextView = new RoundCheckBox(mContext);
        roundTextView.setText("adfafaf");
        roundTextView.setBgColor(R.color.aqua);
        roundTextView.setBgPressColor(R.color.honeydew);
        roundTextView.setGravity(Gravity.CENTER);
        roundTextView.setTextColor(Color.WHITE);//设置白色字体
        roundTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        test.addView(roundTextView);


    }

    @Override
    public void doOnCreateInit() {

    }

    @Override
    public String getLeftTextString() {
        return "232543543";
    }




}
