package xinyi.com.czdevmodule.net_test;

import java.io.Serializable;

/**
 * Created by 陈章 on 2017/3/28 0028.
 * func:
 * 收货地址实体类
 */

public class AddressBean implements Serializable{
    public int a_id;
    public String u_name;
    public String u_phone;
    public String u_area;
    public String u_address;
    public int u_default;   //默认收货地址

    public AddressBean() {
        u_default = 0;
    }

    public AddressBean(String u_name, String u_phone, String u_area, String u_address, int u_default) {
        this.u_name = u_name;
        this.u_phone = u_phone;
        this.u_area = u_area;
        this.u_address = u_address;
        this.u_default = u_default;
    }

    public AddressBean(int a_id , String u_name, String u_phone, String u_area, String u_address, int u_default) {
        this(u_name, u_phone, u_area, u_address, u_default);
        this.a_id = a_id;
    }
}
