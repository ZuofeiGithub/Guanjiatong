package com.huiketong.guanjiatong.bean;

import java.util.List;

public class CaseListBean {

    /**
     * rowcount : 1
     * total : 1
     * errorMsg :
     * success : true
     * rows : [{"projectcode":"f6ed59c7d5ea4cd18dbafd8065fa5de9","projectname":"中粮壹号","companycode":"4f91fada828b4a4baff87a9dcf65547c","createusercode":"ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6","createdatetime":"2018-12-31 15:23:21","projectstatus":1,"gps":null,"projectaddress":"合","projectprocess":0,"startdate":"2018-01-01 00:00:00","enddate":"2018-04-30 00:00:00","projectdays":120,"updatedatetime":null,"createusername":null,"companyname":"南通九鼎装饰","casecode":"46C761DA656926BB","caseorder":0,"caseprojectcode":"dcd2d04656684cd2bb364fa3c9a40c5d"}]
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
         * projectcode : f6ed59c7d5ea4cd18dbafd8065fa5de9
         * projectname : 中粮壹号
         * companycode : 4f91fada828b4a4baff87a9dcf65547c
         * createusercode : ae2a11f5-8ef1-4c73-ae6e-5bf5fbdd1aa6
         * createdatetime : 2018-12-31 15:23:21
         * projectstatus : 1
         * gps : null
         * projectaddress : 合
         * projectprocess : 0
         * startdate : 2018-01-01 00:00:00
         * enddate : 2018-04-30 00:00:00
         * projectdays : 120
         * updatedatetime : null
         * createusername : null
         * companyname : 南通九鼎装饰
         * casecode : 46C761DA656926BB
         * caseorder : 0
         * caseprojectcode : dcd2d04656684cd2bb364fa3c9a40c5d
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
        private Object updatedatetime;
        private Object createusername;
        private String companyname;
        private String casecode;
        private int caseorder;
        private String caseprojectcode;

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

        public Object getUpdatedatetime() {
            return updatedatetime;
        }

        public void setUpdatedatetime(Object updatedatetime) {
            this.updatedatetime = updatedatetime;
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

        public String getCasecode() {
            return casecode;
        }

        public void setCasecode(String casecode) {
            this.casecode = casecode;
        }

        public int getCaseorder() {
            return caseorder;
        }

        public void setCaseorder(int caseorder) {
            this.caseorder = caseorder;
        }

        public String getCaseprojectcode() {
            return caseprojectcode;
        }

        public void setCaseprojectcode(String caseprojectcode) {
            this.caseprojectcode = caseprojectcode;
        }
    }
}
