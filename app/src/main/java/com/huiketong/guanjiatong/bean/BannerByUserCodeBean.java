package com.huiketong.guanjiatong.bean;

import java.util.List;

/**
 * GetBannerByUserCodeBean
 */
public class BannerByUserCodeBean {

    /**
     * rowcount : 2
     * total : 2
     * errorMsg :
     * success : true
     * rows : [{"bannercode":"dc95d78ecfcb497b8ce507b61ae22b83","bannertitle":"2","bannerimg":"/upload/20181129031521.jpg","redirecturl":"1","isshow":1,"createdatetime":"2018-11-27 15:25:46","createusercode":"d386c2db-048f-4cd7-b832-6d1f6e139b77","companycode":"4f91fada828b4a4baff87a9dcf65547c"},{"bannercode":"deaa40fdf5fa43dabf1a6f510b41460a","bannertitle":"1","bannerimg":"/upload/20181129031510.jpg","redirecturl":"1","isshow":1,"createdatetime":"2018-11-26 16:53:13","createusercode":"d386c2db-048f-4cd7-b832-6d1f6e139b77","companycode":"4f91fada828b4a4baff87a9dcf65547c"}]
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
         * bannercode : dc95d78ecfcb497b8ce507b61ae22b83
         * bannertitle : 2
         * bannerimg : /upload/20181129031521.jpg
         * redirecturl : 1
         * isshow : 1
         * createdatetime : 2018-11-27 15:25:46
         * createusercode : d386c2db-048f-4cd7-b832-6d1f6e139b77
         * companycode : 4f91fada828b4a4baff87a9dcf65547c
         */

        private String bannercode;
        private String bannertitle;
        private String bannerimg;
        private String redirecturl;
        private int isshow;
        private String createdatetime;
        private String createusercode;
        private String companycode;

        public String getBannercode() {
            return bannercode;
        }

        public void setBannercode(String bannercode) {
            this.bannercode = bannercode;
        }

        public String getBannertitle() {
            return bannertitle;
        }

        public void setBannertitle(String bannertitle) {
            this.bannertitle = bannertitle;
        }

        public String getBannerimg() {
            return bannerimg;
        }

        public void setBannerimg(String bannerimg) {
            this.bannerimg = bannerimg;
        }

        public String getRedirecturl() {
            return redirecturl;
        }

        public void setRedirecturl(String redirecturl) {
            this.redirecturl = redirecturl;
        }

        public int getIsshow() {
            return isshow;
        }

        public void setIsshow(int isshow) {
            this.isshow = isshow;
        }

        public String getCreatedatetime() {
            return createdatetime;
        }

        public void setCreatedatetime(String createdatetime) {
            this.createdatetime = createdatetime;
        }

        public String getCreateusercode() {
            return createusercode;
        }

        public void setCreateusercode(String createusercode) {
            this.createusercode = createusercode;
        }

        public String getCompanycode() {
            return companycode;
        }

        public void setCompanycode(String companycode) {
            this.companycode = companycode;
        }
    }
}
