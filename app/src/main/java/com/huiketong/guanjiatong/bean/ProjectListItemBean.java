package com.huiketong.guanjiatong.bean;

import java.util.List;

/**
 * 项目列表项
 */
public class ProjectListItemBean {

    /**
     * rowcount : 1
     * total : 1
     * errorMsg :
     * success : true
     * rows : [{"projectcode":"6645365bdc164e269b00049bc9513dfc","projectname":"滨湖新居","companycode":"4f91fada828b4a4baff87a9dcf65547c","createusercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","createdatetime":"2018-12-07 11:45:56","projectstatus":1,"gps":null,"projectaddress":"黄海路","projectprocess":0,"startdate":"2018-10-26 00:00:00","enddate":"2019-01-23 00:00:00","projectdays":90,"updatedatetime":"2019-05-15 15:37:49","projectcatename":"进行中","templatecode":"7efa19d3766745cbb2d3dc6346198104","firstletter":null,"areacode":"","areaname":"江苏省,南通市,港闸区","housenumber":"3#102","projectimg":"","createusername":null,"companyname":"南通九鼎装饰","username":"马梓潇,曹俊,缪佳丽,王加耀,庄新华,曹俊","tipcount":0,"pinyin_index":"B"}]
     */

    private int rowcount;
    private int total;
    private String errorMsg;
    private boolean success;
    private List<RowsBean> rows;

    public int getRowcount() {
        return rowcount;
    }

    public void setRowcount(int rowcount) {
        this.rowcount = rowcount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * projectcode : 6645365bdc164e269b00049bc9513dfc
         * projectname : 滨湖新居
         * companycode : 4f91fada828b4a4baff87a9dcf65547c
         * createusercode : ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6
         * createdatetime : 2018-12-07 11:45:56
         * projectstatus : 1
         * gps : null
         * projectaddress : 黄海路
         * projectprocess : 0
         * startdate : 2018-10-26 00:00:00
         * enddate : 2019-01-23 00:00:00
         * projectdays : 90
         * updatedatetime : 2019-05-15 15:37:49
         * projectcatename : 进行中
         * templatecode : 7efa19d3766745cbb2d3dc6346198104
         * firstletter : null
         * areacode :
         * areaname : 江苏省,南通市,港闸区
         * housenumber : 3#102
         * projectimg :
         * createusername : null
         * companyname : 南通九鼎装饰
         * username : 马梓潇,曹俊,缪佳丽,王加耀,庄新华,曹俊
         * tipcount : 0
         * pinyin_index : B
         */

        private String projectcode;
        private String projectname;
        private String companycode;
        private String createusercode;
        private String createdatetime;
        private int projectstatus;
        private Object gps;
        private String projectaddress;
        private int projectprocess;
        private String startdate;
        private String enddate;
        private int projectdays;
        private String updatedatetime;
        private String projectcatename;
        private String templatecode;
        private Object firstletter;
        private String areacode;
        private String areaname;
        private String housenumber;
        private String projectimg;
        private Object createusername;
        private String companyname;
        private String username;
        private int tipcount;
        private String pinyin_index;

        public String getProjectcode() {
            return projectcode;
        }

        public void setProjectcode(String projectcode) {
            this.projectcode = projectcode;
        }

        public String getProjectname() {
            return projectname;
        }

        public void setProjectname(String projectname) {
            this.projectname = projectname;
        }

        public String getCompanycode() {
            return companycode;
        }

        public void setCompanycode(String companycode) {
            this.companycode = companycode;
        }

        public String getCreateusercode() {
            return createusercode;
        }

        public void setCreateusercode(String createusercode) {
            this.createusercode = createusercode;
        }

        public String getCreatedatetime() {
            return createdatetime;
        }

        public void setCreatedatetime(String createdatetime) {
            this.createdatetime = createdatetime;
        }

        public int getProjectstatus() {
            return projectstatus;
        }

        public void setProjectstatus(int projectstatus) {
            this.projectstatus = projectstatus;
        }

        public Object getGps() {
            return gps;
        }

        public void setGps(Object gps) {
            this.gps = gps;
        }

        public String getProjectaddress() {
            return projectaddress;
        }

        public void setProjectaddress(String projectaddress) {
            this.projectaddress = projectaddress;
        }

        public int getProjectprocess() {
            return projectprocess;
        }

        public void setProjectprocess(int projectprocess) {
            this.projectprocess = projectprocess;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public int getProjectdays() {
            return projectdays;
        }

        public void setProjectdays(int projectdays) {
            this.projectdays = projectdays;
        }

        public String getUpdatedatetime() {
            return updatedatetime;
        }

        public void setUpdatedatetime(String updatedatetime) {
            this.updatedatetime = updatedatetime;
        }

        public String getProjectcatename() {
            return projectcatename;
        }

        public void setProjectcatename(String projectcatename) {
            this.projectcatename = projectcatename;
        }

        public String getTemplatecode() {
            return templatecode;
        }

        public void setTemplatecode(String templatecode) {
            this.templatecode = templatecode;
        }

        public Object getFirstletter() {
            return firstletter;
        }

        public void setFirstletter(Object firstletter) {
            this.firstletter = firstletter;
        }

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }

        public String getHousenumber() {
            return housenumber;
        }

        public void setHousenumber(String housenumber) {
            this.housenumber = housenumber;
        }

        public String getProjectimg() {
            return projectimg;
        }

        public void setProjectimg(String projectimg) {
            this.projectimg = projectimg;
        }

        public Object getCreateusername() {
            return createusername;
        }

        public void setCreateusername(Object createusername) {
            this.createusername = createusername;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getTipcount() {
            return tipcount;
        }

        public void setTipcount(int tipcount) {
            this.tipcount = tipcount;
        }

        public String getPinyin_index() {
            return pinyin_index;
        }

        public void setPinyin_index(String pinyin_index) {
            this.pinyin_index = pinyin_index;
        }
    }
}
