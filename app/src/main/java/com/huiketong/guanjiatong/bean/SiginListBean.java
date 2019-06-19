package com.huiketong.guanjiatong.bean;

import java.util.List;

public class SiginListBean {

    /**
     * rowcount : 10
     * total : 33
     * errorMsg :
     * success : true
     * rows : [{"signincode":"b87f0671-f69a-439c-8bcc-4fcb60468adf","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:59:56","gps":"32.01161322699653,120.90246799045138","signinaddress":"江苏省南通市崇川区通京大道","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"},{"signincode":"1a4ae528-67e2-4975-ae30-4e9d5e54c4da","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:39:41","gps":"32.01161322699653,120.90246799045138","signinaddress":"江苏省南通市崇川区通京大道","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"},{"signincode":"c0327e34-353a-4e83-9193-86618882c52e","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:36:02","gps":"32.011613,120.902468","signinaddress":"江苏省南通市崇川区通甲路6号","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"},{"signincode":"09b8fdb6-041a-4f84-bb52-9d11d318276a","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:35:21","gps":"32.011613,120.902468","signinaddress":"江苏省南通市崇川区通甲路6号","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"},{"signincode":"b2b2119b-00d7-4b29-97a5-50968452accf","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:34:23","gps":"32.011613,120.902468","signinaddress":"江苏省南通市崇川区通甲路6号","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"},{"signincode":"7ee69d31-cb57-4e5e-a541-69f80c41493d","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:30:23","gps":"32.011613,120.902468","signinaddress":"江苏省南通市崇川区通甲路6号","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"},{"signincode":"838743ae-1307-4def-aeb4-02c9a6de25ed","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:05:14","gps":"32.003969,120.923601","signinaddress":"江苏省南通市崇川区青年东路153号","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"},{"signincode":"e98b5eca-e5ec-412e-87a8-28cfda145751","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:03:12","gps":"32.01161322699653,120.90246799045138","signinaddress":"江苏省南通市崇川区通京大道","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"},{"signincode":"18d889bb-ac4c-4bd4-8abf-ba478ad338b4","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:03:09","gps":"32.01161322699653,120.90246799045138","signinaddress":"江苏省南通市崇川区通京大道","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"},{"signincode":"8a717439-b981-4949-9229-e276e275530b","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","signintime":"2019-06-19 09:03:03","gps":"32.01161322699653,120.90246799045138","signinaddress":"江苏省南通市崇川区通京大道","projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","username":"曹俊"}]
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
         * signincode : b87f0671-f69a-439c-8bcc-4fcb60468adf
         * usercode : ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6
         * signintime : 2019-06-19 09:59:56
         * gps : 32.01161322699653,120.90246799045138
         * signinaddress : 江苏省南通市崇川区通京大道
         * projectcode : f6ed59c7d5ea4cd18dbafd8065fa5de9
         * username : 曹俊
         */

        private String signincode;
        private String usercode;
        private String signintime;
        private String gps;
        private String signinaddress;
        private String projectcode;
        private String username;

        public String getSignincode() {
            return signincode;
        }

        public void setSignincode(String signincode) {
            this.signincode = signincode;
        }

        public String getUsercode() {
            return usercode;
        }

        public void setUsercode(String usercode) {
            this.usercode = usercode;
        }

        public String getSignintime() {
            return signintime;
        }

        public void setSignintime(String signintime) {
            this.signintime = signintime;
        }

        public String getGps() {
            return gps;
        }

        public void setGps(String gps) {
            this.gps = gps;
        }

        public String getSigninaddress() {
            return signinaddress;
        }

        public void setSigninaddress(String signinaddress) {
            this.signinaddress = signinaddress;
        }

        public String getProjectcode() {
            return projectcode;
        }

        public void setProjectcode(String projectcode) {
            this.projectcode = projectcode;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
