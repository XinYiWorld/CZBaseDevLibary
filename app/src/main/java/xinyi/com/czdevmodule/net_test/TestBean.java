package xinyi.com.czdevmodule.net_test;

/**
 * Created by 陈章 on 2017/4/7 0007.
 * func:
 */

public class TestBean {
    /**
     * company_interview_id : 50
     * company_job_id : 50
     * evaluate : 职位很棒，值得面试
     * is_evaluate : 1
     * result_feedback : 职位很棒
     * result_interview : 不合适
     * star : 5
     */

    private String company_interview_id;
    private int company_job_id;
    private String evaluate;
    private int is_evaluate;
    private String result_feedback;
    private String result_interview;
    private int star;

    public String getCompany_interview_id() {
        return company_interview_id;
    }

    public void setCompany_interview_id(String company_interview_id) {
        this.company_interview_id = company_interview_id;
    }

    public int getCompany_job_id() {
        return company_job_id;
    }

    public void setCompany_job_id(int company_job_id) {
        this.company_job_id = company_job_id;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public int getIs_evaluate() {
        return is_evaluate;
    }

    public void setIs_evaluate(int is_evaluate) {
        this.is_evaluate = is_evaluate;
    }

    public String getResult_feedback() {
        return result_feedback;
    }

    public void setResult_feedback(String result_feedback) {
        this.result_feedback = result_feedback;
    }

    public String getResult_interview() {
        return result_interview;
    }

    public void setResult_interview(String result_interview) {
        this.result_interview = result_interview;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
