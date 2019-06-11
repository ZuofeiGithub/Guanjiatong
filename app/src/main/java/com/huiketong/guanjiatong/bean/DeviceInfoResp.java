package com.huiketong.guanjiatong.bean;

public class DeviceInfoResp {

    /**
     * code : 0
     * msg : 获取地址成功
     * data : {"liveaddress":"http://hls01open.ys7.com/openlive/ad13099ec2f04419bdbf7101dcda6943.m3u8","deviceId":"12","deviceInfo":{"deviceSerial":"C99792309","deviceName":"C6C(C99792309)","deviceType":null,"model":"CS-C6C-3B2WFR","addTime":0,"supportExtShort":null,"deviceVersion":null,"deviceCover":null,"cameraNum":0,"detectorNum":0,"supportChannelNums":0,"status":1,"defence":0,"isEncrypt":0,"alarmSoundMode":0,"offlineNotify":0,"category":"C6C"}}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * liveaddress : http://hls01open.ys7.com/openlive/ad13099ec2f04419bdbf7101dcda6943.m3u8
         * deviceId : 12
         * deviceInfo : {"deviceSerial":"C99792309","deviceName":"C6C(C99792309)","deviceType":null,"model":"CS-C6C-3B2WFR","addTime":0,"supportExtShort":null,"deviceVersion":null,"deviceCover":null,"cameraNum":0,"detectorNum":0,"supportChannelNums":0,"status":1,"defence":0,"isEncrypt":0,"alarmSoundMode":0,"offlineNotify":0,"category":"C6C"}
         */

        private String liveaddress;
        private String deviceId;
        private DeviceInfoBean deviceInfo;

        public String getLiveaddress() {
            return liveaddress;
        }

        public void setLiveaddress(String liveaddress) {
            this.liveaddress = liveaddress;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public DeviceInfoBean getDeviceInfo() {
            return deviceInfo;
        }

        public void setDeviceInfo(DeviceInfoBean deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        public static class DeviceInfoBean {
            /**
             * deviceSerial : C99792309
             * deviceName : C6C(C99792309)
             * deviceType : null
             * model : CS-C6C-3B2WFR
             * addTime : 0
             * supportExtShort : null
             * deviceVersion : null
             * deviceCover : null
             * cameraNum : 0
             * detectorNum : 0
             * supportChannelNums : 0
             * status : 1
             * defence : 0
             * isEncrypt : 0
             * alarmSoundMode : 0
             * offlineNotify : 0
             * category : C6C
             */

            private String deviceSerial;
            private String deviceName;
            private String deviceType;
            private String model;
            private int addTime;
            private Object supportExtShort;
            private Object deviceVersion;
            private String deviceCover;
            private int cameraNum;
            private int detectorNum;
            private int supportChannelNums;
            private int status;
            private int defence;
            private int isEncrypt;
            private int alarmSoundMode;
            private int offlineNotify;
            private String category;

            public String getDeviceSerial() {
                return deviceSerial;
            }

            public void setDeviceSerial(String deviceSerial) {
                this.deviceSerial = deviceSerial;
            }

            public String getDeviceName() {
                return deviceName;
            }

            public void setDeviceName(String deviceName) {
                this.deviceName = deviceName;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public int getAddTime() {
                return addTime;
            }

            public void setAddTime(int addTime) {
                this.addTime = addTime;
            }

            public Object getSupportExtShort() {
                return supportExtShort;
            }

            public void setSupportExtShort(Object supportExtShort) {
                this.supportExtShort = supportExtShort;
            }

            public Object getDeviceVersion() {
                return deviceVersion;
            }

            public void setDeviceVersion(Object deviceVersion) {
                this.deviceVersion = deviceVersion;
            }

            public String getDeviceCover() {
                return deviceCover;
            }

            public void setDeviceCover(String deviceCover) {
                this.deviceCover = deviceCover;
            }

            public int getCameraNum() {
                return cameraNum;
            }

            public void setCameraNum(int cameraNum) {
                this.cameraNum = cameraNum;
            }

            public int getDetectorNum() {
                return detectorNum;
            }

            public void setDetectorNum(int detectorNum) {
                this.detectorNum = detectorNum;
            }

            public int getSupportChannelNums() {
                return supportChannelNums;
            }

            public void setSupportChannelNums(int supportChannelNums) {
                this.supportChannelNums = supportChannelNums;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getDefence() {
                return defence;
            }

            public void setDefence(int defence) {
                this.defence = defence;
            }

            public int getIsEncrypt() {
                return isEncrypt;
            }

            public void setIsEncrypt(int isEncrypt) {
                this.isEncrypt = isEncrypt;
            }

            public int getAlarmSoundMode() {
                return alarmSoundMode;
            }

            public void setAlarmSoundMode(int alarmSoundMode) {
                this.alarmSoundMode = alarmSoundMode;
            }

            public int getOfflineNotify() {
                return offlineNotify;
            }

            public void setOfflineNotify(int offlineNotify) {
                this.offlineNotify = offlineNotify;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }
        }
    }
}
