package xinyi.com.czdevmodule.net_test.retrofit;

/**
 * Created by 陈章 on 2017/3/24 0024.
 * func:
 * 用户实体类
 */

public class UserBean  {
    private int user_id;

    private int position_id;

    private int category_id;

    private int status;

    private String name;

    private String img;

    private String sex;

    private String email;

    private String telephone;

    private String address;

    private long reg_time;

    private String company;

    private int cat;

    private int degree_id;

    private int experience;

    private String name_true;

    private long time_born;

    private long time_work;

    private String technical;

    private String position_id_follow;

    private int remind_invite;

    private String head_img;

    private float score;

    //my define
    private String password;

    private boolean isUserLogin;

    public UserBean() {

    }

    public UserBean(String telephone, int cat, String password) {
        this.telephone = telephone;
        this.cat = cat;
        this.password = password;
    }

    public boolean isUserLogin() {
        return isUserLogin;
    }

    public void setUserLogin(boolean userLogin) {
        isUserLogin = userLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getDegree_id() {
        return degree_id;
    }

    public void setDegree_id(int degree_id) {
        this.degree_id = degree_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_true() {
        return name_true;
    }

    public void setName_true(String name_true) {
        this.name_true = name_true;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public String getPosition_id_follow() {
        return position_id_follow;
    }

    public void setPosition_id_follow(String position_id_follow) {
        this.position_id_follow = position_id_follow;
    }

    public long getReg_time() {
        return reg_time;
    }

    public void setReg_time(long reg_time) {
        this.reg_time = reg_time;
    }

    public int getRemind_invite() {
        return remind_invite;
    }

    public void setRemind_invite(int remind_invite) {
        this.remind_invite = remind_invite;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTechnical() {
        return technical;
    }

    public void setTechnical(String technical) {
        this.technical = technical;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public long getTime_born() {
        return time_born;
    }

    public void setTime_born(long time_born) {
        this.time_born = time_born;
    }

    public long getTime_work() {
        return time_work;
    }

    public void setTime_work(long time_work) {
        this.time_work = time_work;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
