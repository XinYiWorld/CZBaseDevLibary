package xinyi.com.czdevmodule.net_test.retrofit;

import java.util.List;

/**
 * Created by 陈章 on 2017/4/26 0026.
 * func:
 * 求职-首页实体类
 */

public class FJHomeBean  {
    private int p;
    private int count;
    private int total_pages;

    private List<JobPositionBeanXX> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<JobPositionBeanXX> getList() {
        return list;
    }

    public void setList(List<JobPositionBeanXX> list) {
        this.list = list;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
