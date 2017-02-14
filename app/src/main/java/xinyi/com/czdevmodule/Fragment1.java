package xinyi.com.czdevmodule;

import android.view.View;

import com.xinyi.czbasedevtool.base.bean.UploadFileWrapper;
import com.xinyi.czbasedevtool.base.main.DefaultBaseFragment;

import java.io.IOException;
import java.util.List;

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
    public void doOnCreateInit() {

    }

    @Override
    public String getLeftTextString() {
        return "232543543";
    }
}
