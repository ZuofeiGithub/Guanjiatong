package com.huiketong.guanjiatong.bean;

import java.util.List;

public class ProjectCateBean {

    /**
     * rowcount : 4
     * total : 4
     * errorMsg :
     * success : true
     * rows : [{"frontmodulecode":"47c1f404-7eb4-4ed1-be65-d1550f679abb","frontmodulename":"意向","frontmoduleicon":"1","isshow":1,"frontmoduleurl":"22","isredirect":0,"redirecturl":"33","frontmoduletype":1,"frontmoduleorder":990},{"frontmodulecode":"db6bea87-e7f6-4ced-8f65-ece1f629afe2","frontmodulename":"进行中","frontmoduleicon":"2","isshow":1,"frontmoduleurl":"111","isredirect":0,"redirecturl":"11","frontmoduletype":1,"frontmoduleorder":980},{"frontmodulecode":"c274a371-d9c8-4f87-8d2e-90fe1d1446db","frontmodulename":"延期","frontmoduleicon":"3","isshow":1,"frontmoduleurl":"2","isredirect":0,"redirecturl":"2","frontmoduletype":1,"frontmoduleorder":970},{"frontmodulecode":"23b831fe-4006-4362-b171-b5322d655268","frontmodulename":"已完成","frontmoduleicon":"4","isshow":1,"frontmoduleurl":"2","isredirect":0,"redirecturl":"2","frontmoduletype":1,"frontmoduleorder":960}]
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
         * frontmodulecode : 47c1f404-7eb4-4ed1-be65-d1550f679abb
         * frontmodulename : 意向
         * frontmoduleicon : 1
         * isshow : 1
         * frontmoduleurl : 22
         * isredirect : 0
         * redirecturl : 33
         * frontmoduletype : 1
         * frontmoduleorder : 990
         */

        private String frontmodulecode;
        private String frontmodulename;
        private String frontmoduleicon;
        private int isshow;
        private String frontmoduleurl;
        private int isredirect;
        private String redirecturl;
        private int frontmoduletype;
        private int frontmoduleorder;

        public String getFrontmodulecode() {
            return frontmodulecode;
        }

        public void setFrontmodulecode(String frontmodulecode) {
            this.frontmodulecode = frontmodulecode;
        }

        public String getFrontmodulename() {
            return frontmodulename;
        }

        public void setFrontmodulename(String frontmodulename) {
            this.frontmodulename = frontmodulename;
        }

        public String getFrontmoduleicon() {
            return frontmoduleicon;
        }

        public void setFrontmoduleicon(String frontmoduleicon) {
            this.frontmoduleicon = frontmoduleicon;
        }

        public int getIsshow() {
            return isshow;
        }

        public void setIsshow(int isshow) {
            this.isshow = isshow;
        }

        public String getFrontmoduleurl() {
            return frontmoduleurl;
        }

        public void setFrontmoduleurl(String frontmoduleurl) {
            this.frontmoduleurl = frontmoduleurl;
        }

        public int getIsredirect() {
            return isredirect;
        }

        public void setIsredirect(int isredirect) {
            this.isredirect = isredirect;
        }

        public String getRedirecturl() {
            return redirecturl;
        }

        public void setRedirecturl(String redirecturl) {
            this.redirecturl = redirecturl;
        }

        public int getFrontmoduletype() {
            return frontmoduletype;
        }

        public void setFrontmoduletype(int frontmoduletype) {
            this.frontmoduletype = frontmoduletype;
        }

        public int getFrontmoduleorder() {
            return frontmoduleorder;
        }

        public void setFrontmoduleorder(int frontmoduleorder) {
            this.frontmoduleorder = frontmoduleorder;
        }
    }
}
