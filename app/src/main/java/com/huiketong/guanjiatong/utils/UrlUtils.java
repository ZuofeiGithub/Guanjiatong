package com.huiketong.guanjiatong.utils;

/**
 * 请求接口地址
 */
public class UrlUtils {

    // 基础url
    public static String baseUrl = "https://www.guanjiatong.com.cn";
    // api url
    public static String apiUrl = baseUrl + "/api/service/";

    //萤石云后台接口地址
    public static String cameraUrl = "https://gjt.jkt365.com/api/";
    //获取直播地址
    public static String LiveAddress = cameraUrl + "get_device_live_address";
    /**
     * 登录模块
     */
    public static String CheckLogin = apiUrl + "CheckLogin";   //登录验证-企业用户,uid,userpassword
    public static String RemoveTeam = apiUrl + "RemoveTeam";   //移除企业团队,uid,userpassword
    public static String GetCompanyUserInfo = apiUrl + "GetCompanyUserInfo";   //根据帐号获得对应的企业用户的信息-企业信息包括企业基础资料,uid
    public static String GetCompanyUserRole = apiUrl + "GetCompanyUserRole";   //根据帐号获得对应的企业用户的信息-企业信息包括企业基础资料,usercode
    /**
     * 轮播图模块
     */
    public static String GetBanner = apiUrl + "GetBanner";   //根据帐号获得对应的企业用户的信息-企业信息包括企业基础资料,companyCode
    public static String GetBannerByUserCode = apiUrl + "GetBannerByUserCode";   //根据帐号获得对应的企业用户所属的所有角色信息获取企业轮播图信息,userCode
    /**
     * 项目资料
     */
    public static String GetProjectCate = apiUrl + "GetProjectCate";   //获得项目状态,usercode
    public static String GetProject = apiUrl + "GetProject";   //获得项目列表-根据状态获得和用户编号,companyCode,userCode,projectstatus,p,ps
    public static String GetReport = apiUrl + "GetReport";   //获取项目统计,companyCode,userCode
    public static String GetProjectInfo = apiUrl + "GetProjectInfo";   //获得项目-详情,projectCode
    public static String UpdateProjectInfo = apiUrl + "UpdateProjectInfo";   //修改一个项目,根据项目编号,projectcode,projectname,projectstatus,startdate,enddate,projectaddress,areaname,areacode,housenumber
    public static String UpdateProject = apiUrl + "UpdateProject";   //跟新一个项目,projectcode,projectname,projectaddress,areaname,areacode,housenumber
    public static String GetTaskTemplate = apiUrl + "GetTaskTemplate";   //任务模版,companyCode
    public static String AddProjectInfo = apiUrl + "AddProjectInfo";   //添加一个项目,projectname,usercode,projectaddress,companycode,areaname,areacode,housenumber
    public static String ImportTaskTemplate = apiUrl + "ImportTaskTemplate";   //导入模版,projectcode,templatecode,startdate,enddate
    /**
     * 设计档案
     */
    public static String GetDocInfo = apiUrl + "GetDocInfo";   //获得项目-设计档案,projectCode,doctype
    public static String UpdateDocInfo = apiUrl + "UpdateDocInfo";   //修改-设计档案,doccode,doccontent,docimages
    public static String RemoveDocInfo = apiUrl + "RemoveDocInfo";   //删除 设计档案,doccode
    public static String AddDocInfo = apiUrl + "AddDocInfo";   //新增-设计档案,doctype,projectcode,doccontent,docimages,createusercode
    /**
     * 任务分类
     */
    public static String GetTaskCate = apiUrl + "GetTaskCate";   //获得项目-施工任务分类,projectCode
    public static String GetTask = apiUrl + "GetTask";   //获得项目-分类施工任务,projectCode,cateCode
    public static String GetAllTask = apiUrl + "GetAllTask";   //获得项目-所有施工任务,projectCode
    public static String GetTaskInfo = apiUrl + "GetTaskInfo";   //获得项目-施工任务明细,taskCode
    public static String GetTaskExecute = apiUrl + "GetTaskExecute";   // 获得项目-施工任务-执行记录,taskCode
    public static String CompleteTask = apiUrl + "CompleteTask";   //获得项目-施工任务-设置任务完成,taskcode
    public static String AddTaskExecuteInfo = apiUrl + "AddTaskExecuteInfo";   //项目-施工任务-添加执行任务,taskcode,usercode,executecontent,executeimgs
    /**
     * 工程材料
     */
    public static String GetMateriaApplay = apiUrl + "GetMateriaApplay";   //获得项目-工程材料,projectCode
    public static String GetMateriaApplayCate = apiUrl + "GetMateriaApplayCate";   //获得项目-工程材料分类,
    public static String AddMateriaApplay = apiUrl + "AddMateriaApplay";   //获得项目-工程材料添加,projectcode,materialtype,receivetime,receiver,receiverphone,applayremark
    public static String IsReturned = apiUrl + "IsReturned";   //申请退货-工程材料退货,申请退货-工程材料退货
    /**
     * 项目成员
     */
    public static String GetProjectTeamUser = apiUrl + "GetProjectTeamUser";   //获得项目成员-服务团队,projectCode,p,ps
    public static String GetCompanyUser = apiUrl + "GetCompanyUser";   //获得企业用户,companyCode,p,ps
    public static String GetCompanyRole = apiUrl + "GetCompanyRole";   //获得企业角色,companyCode,p,ps
    public static String AddCompanyUser = apiUrl + "AddCompanyUser";   //添加企业用户,companycode,username,cellphone,remark,projectcode,userrole,usercode
    /**
     * 家装商城
     */
    public static String GetProductCate = apiUrl + "GetProductCate";   //获得商城分类,companyCode,catecode
    public static String GetProduct = apiUrl + "GetProduct";   //获得商城分类-商品信息,companyCode,cateCode,p,ps
    public static String GetProductDetail = apiUrl + "GetProductDetail";   //产品详情,productcode
    /**
     * 精选案例
     */
    public static String GetCase = apiUrl + "GetCase";   //获得精选案例列表,projectcode,userCode,p,ps
    public static String GetSelectCase = apiUrl + "GetSelectCase";   //获取未选择的数据,projectcode,companycode
    public static String AddCase = apiUrl + "AddCase";   //添加精选案例,projectcode,usercode,caseprojectcode,rolecodes,
    /**
     * 我的
     */
    public static String GetActivity = apiUrl + "GetActivity";   //获得活动推送-列表,companyCode,p,ps
    public static String GetActivityEntity = apiUrl + "GetActivityEntity";   //获得活动推送-详情,activityCode
    public static String GetCompany = apiUrl + "GetCompany";   //关于,companyCode
    public static String AddTip = apiUrl + "AddTip";   //项目消息提醒-添加,rolecode,tiptitle,tipcontent,createusercode,projectcode,taskcode
    public static String AddNewTip = apiUrl + "AddNewTip";   //消息提醒-添加,projectcatecode,tipcontent,reminddatetime,projectcode,taskcode,tipimg,createusercode,rolecode
    public static String GetTip = apiUrl + "GetTip";   //项目消息提醒,userCode,p,ps
    public static String TipCount = apiUrl + "TipCount";   //获取提醒总数,usercode,projectcode
    public static String GetSysMsg = apiUrl + "GetSysMsg";   //系统公告,companyCode,p,ps
    public static String UpDateHeadImgByUserCode = apiUrl + "UpDateHeadImgByUserCode";   //修改头像,usercode,headimg
    /**
     * 签到
     */
    public static String SignIn = apiUrl + "SignIn";   //签到,usercode,gps,signinaddress,projectcode
    public static String GetSignIn = apiUrl + "GetSignIn";   //获得签到记录,usercode,projectCode,p,ps
    /**
     * 微信二维码专用
     */
    public static String SetCode = apiUrl + "SetCode";   //设置code,taskcode
    public static String GetCode = apiUrl + "GetCode";   // 获取 任务编码 以MD5加密Code获取,usercode,projectCode,p,ps
    public static String GetSignInWithProjectCode = apiUrl + "GetSignInWithProjectCode";   //获得项目下所有签到记录,projectCode,p,ps
    /**
     * 报备客户
     * companycode，potentialusercode，potentialname，potentialphone，potentialaddress，
     * potentialstyle，potentialsln，potentialarea，potentialbudget，potentialremark，createusercode
     */
    public static String AddPotential = apiUrl + "AddPotential";
    /**
     * 分享留言
     */
    public static String AddTaskMessage = apiUrl + "AddTaskMessage";   //增加留言,cellphone，membername，address，taskcode，content，companycode，createdate，messsagecode
    public static String GetTaskMessage = apiUrl + "GetTaskMessage";   //获取分享留言列表,companycode，taskcode
    /**
     * 收藏
     */
    public static String AddProductCollection = apiUrl + "AddProductCollection";   //添加收藏,productcode，createusercode
    public static String GetProductCollection = apiUrl + "GetProductCollection";   //获得签到记录,usercode，p，ps
    public static String RemoveProductCollection = apiUrl + "RemoveProductCollection";   //删除收藏,collectioncode
    /**
     * 购物车
     */
    public static String AddShopCart = apiUrl + "AddShopCart";   //添加到购物车,createusercode，productcode，productnum
    public static String GetShopCart = apiUrl + "GetShopCart";   //获得购物车列表,usercode，p，ps
    public static String RemoveShopCart = apiUrl + "RemoveShopCart";   //删除购物车,shopCartCode
    /**
     * 我的收入
     * userCode，p，ps
     */
    public static String GetPay = apiUrl + "GetPay";
    /**
     * 添加处罚
     * companycode，penalizemoney，penalizeimg，createusercode，taskcode，penalizeremark
     */
    public static String AddPenalize = apiUrl + "AddPenalize";
    /**
     * 其他
     */
    public static String GetModule = apiUrl + "GetModule";   //获取模块，usercode
    public static String GetTipRole = apiUrl + "GetTipRole";   //获取可提醒角色，usercode
    public static String AddDelay = apiUrl + "AddDelay";   //申请延期,projectcode，delayreason，delaydays，submitusercode，delaycode
    public static String CheckDelay = apiUrl + "CheckDelay";   //工程经理审核,delaycode，checkusercode1，ischeck1
    public static String CheckDelayForCustomer = apiUrl + "CheckDelayForCustomer";   //业主审核,delaycode，checkusercode2，ischeck2
    public static String GetDelayList = apiUrl + "GetDelayList";   //待审核列表,projectCode，delaystatus，p，ps
    /**
     * 项目任务记录
     */
    public static String UpdateExecute = apiUrl + "UpdateExecute";   //修改一个任务记录,executecode，executecontent，executeimgs
    public static String RemoveByExecuteCode = apiUrl + "RemoveByExecuteCode";   //删除任务记录,executecode
    /**
     * 系统配置
     */
    public static String GetSystemConfiguration = apiUrl + "GetSystemConfiguration";   //获取系统配置,companyCode，parentCode
    public static String GetSytemConfigByTaskCode = apiUrl + "GetSytemConfigByTaskCode";   //通过任务代码获取系统配置,taskcode，parentcode

    /**
     * 处理url地址
     * @param url
     * @return
     */
    public static String buildUrl(String url){
        if(url.contains("https://") || url.contains("http://")){
            return url;
        }else if(url.indexOf("/") == 0){
            return baseUrl + url;
        }
        return baseUrl + "/" + url;
    }

}
