package xinyi.com.czdevmodule.net_test.retrofit;

/**
 * Created by 陈章 on 2017/4/15 0015.
 * func:
 * 学历-实体类
 */
public class DegreeBean  {
    private int degree_id;
    private String degree_name;
    private int order_degree;
    private int is_deleted;

    public int getDegree_id() {
        return degree_id;
    }

    public void setDegree_id(int degree_id) {
        this.degree_id = degree_id;
    }

    public String getDegree_name() {
        return degree_name;
    }

    public void setDegree_name(String degree_name) {
        this.degree_name = degree_name;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getOrder_degree() {
        return order_degree;
    }

    public void setOrder_degree(int order_degree) {
        this.order_degree = order_degree;
    }
}
