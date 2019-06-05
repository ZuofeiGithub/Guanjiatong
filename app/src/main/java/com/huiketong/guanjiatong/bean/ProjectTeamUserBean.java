package com.huiketong.guanjiatong.bean;

import java.util.List;

/**
 * 项目团队用户
 */
public class ProjectTeamUserBean {

    /**
     * rowcount : 1
     * total : 1
     * errorMsg :
     * success : true
     * rows : [{"teamcode":"4d96c0c85bc14b7bbf244fd9bb5b353c","projectcode":"5a8d6a1009d2414e8ef796988ce74eaf","usercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","uid":"1","companycode":"4f91fada828b4a4baff87a9dcf65547c","username":"曹俊","rolename":"监理","cellphone":"18051661999","headimg":"https://wx.qlogo.cn/mmopen/vi_32/aicMI90eyFn1GOBeNTI3oo1EichrE8gyn1jmP115PAAsibZccnmIVt5aibhS8pJabegJCibjxibuyrhG6WUkFyxulYFg/132","rolenames":"监理,,总经理,"}]
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
         * teamcode : 4d96c0c85bc14b7bbf244fd9bb5b353c
         * projectcode : 5a8d6a1009d2414e8ef796988ce74eaf
         * usercode : ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6
         * uid : 1
         * companycode : 4f91fada828b4a4baff87a9dcf65547c
         * username : 曹俊
         * rolename : 监理
         * cellphone : 18051661999
         * headimg : https://wx.qlogo.cn/mmopen/vi_32/aicMI90eyFn1GOBeNTI3oo1EichrE8gyn1jmP115PAAsibZccnmIVt5aibhS8pJabegJCibjxibuyrhG6WUkFyxulYFg/132
         * rolenames : 监理,,总经理,
         */

        private String teamcode;
        private String projectcode;
        private String usercode;
        private String uid;
        private String companycode;
        private String username;
        private String rolename;
        private String cellphone;
        private String headimg;
        private String rolenames;

        public String getTeamcode() {
            return teamcode;
        }

        public void setTeamcode(String teamcode) {
            this.teamcode = teamcode;
        }

        public String getProjectcode() {
            return projectcode;
        }

        public void setProjectcode(String projectcode) {
            this.projectcode = projectcode;
        }

        public String getUsercode() {
            return usercode;
        }

        public void setUsercode(String usercode) {
            this.usercode = usercode;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCompanycode() {
            return companycode;
        }

        public void setCompanycode(String companycode) {
            this.companycode = companycode;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getRolenames() {
            return rolenames;
        }

        public void setRolenames(String rolenames) {
            this.rolenames = rolenames;
        }
    }
}
