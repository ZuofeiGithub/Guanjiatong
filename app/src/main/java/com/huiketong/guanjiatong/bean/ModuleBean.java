package com.huiketong.guanjiatong.bean;

import java.util.List;

/**
 * 模块
 */
public class ModuleBean {

    /**
     * rowcount : 16
     * total : 16
     * errorMsg :
     * success : true
     * rows : [{"frontmodulecode":"760748040ad343b79d19c63c9b7556e4","frontmodulename":"基础信息","frontmoduleicon":"information","isshow":1,"frontmoduleurl":"../projectBaseInfo/projectBaseInfo","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":160},{"frontmodulecode":"c4e67adb-183a-41b8-a0e4-6c93ef88b388","frontmodulename":"每日签到","frontmoduleicon":"qiandao1","isshow":1,"frontmoduleurl":"../sign/sign","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":150},{"frontmodulecode":"4f0006a7-5a6d-4024-93ec-8ddcb8682ae6","frontmodulename":"设计档案","frontmoduleicon":"dangan","isshow":1,"frontmoduleurl":"../design/design","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":140},{"frontmodulecode":"24e1df93-aef8-4661-bf5a-8da65e60ee3e","frontmodulename":"施工任务","frontmoduleicon":"task","isshow":1,"frontmoduleurl":"../task/task","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":130},{"frontmodulecode":"d01507f3-b7a3-4759-bac4-c000a8176bc4","frontmodulename":"工程材料","frontmoduleicon":"cailiao","isshow":1,"frontmoduleurl":"../material/material","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":120},{"frontmodulecode":"90ea86e8-802c-4144-915f-838c3cc40bb6","frontmodulename":"项目修改","frontmoduleicon":"xiangmuxg","isshow":1,"frontmoduleurl":"../projectInfo/projectInfo","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":110},{"frontmodulecode":"eeee0b7c-4ed8-4423-b889-8a6108399249","frontmodulename":"导入模板","frontmoduleicon":"daoru","isshow":1,"frontmoduleurl":"../projectMould/projectMould","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":100},{"frontmodulecode":"f9ea2f70-c950-4951-9548-5914143c6976","frontmodulename":"家装商城","frontmoduleicon":"shangcheng","isshow":1,"frontmoduleurl":"../shop/shop","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":90},{"frontmodulecode":"0363d3ee-01fe-4d72-8e3f-9b6ed059f12d","frontmodulename":"我的收藏","frontmoduleicon":"shoucang","isshow":1,"frontmoduleurl":"../collection/collection","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":80},{"frontmodulecode":"63a726a8-25d1-4fd5-87f6-5e25011c61cf","frontmodulename":"添加案例","frontmoduleicon":"anli","isshow":1,"frontmoduleurl":"../case/case","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":70},{"frontmodulecode":"872e9cb2-cbbf-4c68-9786-6167b1709a83","frontmodulename":"我的收入","frontmoduleicon":"shouru","isshow":1,"frontmoduleurl":"../money/money","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":0},{"frontmodulecode":"92b1259f-1b4c-48ea-8bd7-c822c71167dc","frontmodulename":"申请延期","frontmoduleicon":"yanqi1","isshow":1,"frontmoduleurl":"../delay/delay","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":0},{"frontmodulecode":"2cbad1a3-7ea5-408c-9321-20fac2e029d4","frontmodulename":"延期审核","frontmoduleicon":"shenhe1","isshow":1,"frontmoduleurl":"../delayHis/delayHis","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":0},{"frontmodulecode":"93b3718a-fd73-4d93-ae71-e0e6350f7523","frontmodulename":"购物车","frontmoduleicon":"cart1","isshow":1,"frontmoduleurl":"../shopcart/shopcart","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":0},{"frontmodulecode":"65582869dece4dad8f1ce280be867467","frontmodulename":"工地直播","frontmoduleicon":"live","isshow":1,"frontmoduleurl":"../live/live","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":0},{"frontmodulecode":"da41230e-b26f-4e63-907b-63f2b28a279c","frontmodulename":"处罚","frontmoduleicon":"punish-circle","isshow":1,"frontmoduleurl":"../punish/punish","isredirect":0,"redirecturl":"#","frontmoduletype":0,"frontmoduleorder":0}]
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
         * frontmodulecode : 760748040ad343b79d19c63c9b7556e4
         * frontmodulename : 基础信息
         * frontmoduleicon : information
         * isshow : 1
         * frontmoduleurl : ../projectBaseInfo/projectBaseInfo
         * isredirect : 0
         * redirecturl : #
         * frontmoduletype : 0
         * frontmoduleorder : 160
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
