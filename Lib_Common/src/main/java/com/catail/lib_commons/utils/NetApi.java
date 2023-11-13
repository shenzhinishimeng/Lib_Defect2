package com.catail.lib_commons.utils;


public class NetApi {
    //修改的正式地址
//    public static final String BaseUrl = "https://server.globalbes.sg/cms_qa/dbapi?cmd=";
//    public static String UPLOAD_IMG = "https://server.globalbes.sg/appupload";// 图片上传地址
//    public static String IMG_SHOW_URL = "https://server.globalbes.sg/getfile";// 图片显示地址
//    public static String PDF_CATALOG_PATH_2 = "https://server.globalbes.sg/getfile";// pdf路径正式
//    public static String webBaseUrl = "https://server.globalbes.sg/"; //web页面//正式库

    //   测试库地址
    public static String BaseUrl = "https://t.cmstech.aoepos.cn/cms_qa/dbapi?cmd=";
    public static String UPLOAD_IMG = "https://t.cmstech.aoepos.cn/appupload";// 图片上传地址
    public static String IMG_SHOW_URL = "https://t.cmstech.aoepos.cn";// 图片显示地址
    public static String PDF_CATALOG_PATH_2 = "https://t.cmstech.aoepos.cn";// pdf路径测试
    public static String webBaseUrl = "https://service.cmstech.sg/"; //webs页面//正式库

    //测试库
//    public static String BaseUrl = "http://47.100.64.238/cms_qa/dbapi?cmd=";
//    public static String UPLOAD_IMG = "http://47.100.64.238/appupload";// 图片上传地址
//    public static String IMG_SHOW_URL = "http://47.100.64.238";// 图片显示地址
//    public static String PDF_CATALOG_PATH_2 = "http://47.100.64.238";// pdf路径测试
//    public static String webBaseUrl = "http://47.100.64.238/"; //webs页面//正式库

//    public static String webBaseUrl = "https://t.cmstech.aoepos.cn/";// web页面  测试库

    //bimax 相关
    public static String BIMAXModelDownLoadUrl = "https://bim.globalbes.sg/api/v1/models/";
    public static String BIMAXModelGetToken = "https://bim.globalbes.sg/api/v1/token";
    public static String BIMAXModelGetsTheEntityAttributeBasedOnTheUuid
            = "https://bim.globalbes.sg/api/v1/entity/";
    public static String GetComponentAttributes
            = "https://bim.globalbes.sg/api/v1/models/";

    public static String TaskStatistics = webBaseUrl + "webchart/Cms.html";//统计图
//    public static String TaskStatistics = webBaseUrl + "webchart/chart-bar.html";//统计图

    //roboxz 文档管理系统
    public static String RoboxzBaseUrl = "https://dms.globalbes.sg/dmsapi";
//    public static String RoboxzProjectSelectedDocURL = RoboxzBaseUrl + "/filecache/mobile?";//项目选择文档(第三版,前两版优化都删掉了)
    public static String RoboxzProjectSelectedDocURL = RoboxzBaseUrl + "/v2/public-files?";//项目选择文档(第三版,前两版优化都删掉了)
    public static String RoboxzFilePreview = RoboxzBaseUrl + "/preview?id=";//文件预览

    //Progress
    public static String ProgressPlanListUrl = "https://service.globalbes.sg/test/bimax/treetable-progress/demo/index.html";

    //00    系统模块
    public static String Login = BaseUrl + "CMSC0001";//登录接口
    public static String changePassword = BaseUrl + "CMSC0002";//我的界面,修改密码
    public static String QueryUserInfo = BaseUrl + "CMSC0003";//查询个人信息
    public static String electronicSignature = BaseUrl + "CMSC0004";//上传签名信息
    public static String ResetPassword = BaseUrl + "CMSC0006";//重置密码

    public static String QueryMainPowerType = BaseUrl + "CMSC0011";//查询manpower类型
    public static String QueryEquipmentType = BaseUrl + "CMSC0012";//查询设备类型
    public static String QueryMaterialType = BaseUrl + "CMSC0013";//查询材料类型

    //01    项目模块
    public static String QueryProjectList = BaseUrl + "CMSC0101";//查询用户参与的项目
    public static String QueryWorkLocation = BaseUrl + "CMSC0102";//查询工作区域
    public static String QueryProjectPerson = BaseUrl + "CMSC0103";//查询项目成员
    public static String QueryBlock = BaseUrl + "CMSC0104";//查询一级区域
    public static String QueryLevel = BaseUrl + "CMSC0105";//查询二级区域
    public static String QueryMembersPerson = BaseUrl + "CMSC0106";//查询项目成员
    public static String QueryUnit = BaseUrl + "CMSC0107";//查询Location/Unit
    public static String QueryProjectDrawing = BaseUrl + "CMSC0108";//查询项目图纸

    public static String SetLocationPermission = BaseUrl + "CMSC0111V2";//根据某一项目的设置参数
    public static String QueryMessageToRemind = BaseUrl + "CMSC0112V3";//查询消息提醒接口
    public static String QueryUserRoleInProject = BaseUrl + "CMSC0113";//登陆者在项目中的身份角色
    public static String queryBIMAXModel = BaseUrl + "CMSC0121";//查询bimax 模型
    public static String QueryTaskBlock = BaseUrl + "CMSC0122";//查询block
    public static String QueryTaskLevel = BaseUrl + "CMSC0123";//查询level
    public static String QueryTaskUnit = BaseUrl + "CMSC0124";//查询unit
    public static String QueryTaskPbu = BaseUrl + "CMSC0125";//查询Pbu
    public static String QueryPubInfo = BaseUrl + "CMSC0126";//查询pub 详细信息
    public static String MaterialsComponentRecordList = BaseUrl + "CMSC0127";//查询构件状态列表.
    public static String MaterialsQueryComponentDetails = BaseUrl + "CMSC0128";//查询单一构件的详情、任务列表、defect列表.
    public static String MaterialsQueryPartList = BaseUrl + "CMSC0129";//查询构件part(pbu表）
    public static String MaterialsComponentRecordList1 = BaseUrl + "CMSC0130";//查询构件状态列表.

    public static String QueryDefectBlockLists=BaseUrl + "CMSC0131";//查询defect2.0 Block列表.
    public static String QueryDefectLevelLists=BaseUrl + "CMSC0132";//查询defect2.0 Level列表.
    public static String QueryDefectZoneLists=BaseUrl + "CMSC0133";//查询defect2.0 Zone列表.
    public static String QuerySubConLists = BaseUrl + "CMSC0134";//查询分包列表


    //02    defect 模块
    public static String SendData = BaseUrl + "CMSC0201";//新建defect 检查单
    public static String DedectList = BaseUrl + "CMSC0202V2";//查询defect list列表
    public static String DefectDetails = BaseUrl + "CMSC0203";//查询defect 详情
    public static String DefectReplyForm = BaseUrl + "CMSC0204";//回复检查单
    public static String QueryDefectType = BaseUrl + "CMSC0205";//查询defect 类型
    public static String QueryDefectType1 = BaseUrl + "CMSC0206";//查询defect 类型

    public static String DMDefectSubmitDefect1 = BaseUrl + "CMSC0211";//submit 提交
    public static String QueryDMDLPDefectListDetails1 = BaseUrl + "CMSC0212"; // Defect列表-Unit  带筛选条件的
    public static String QueryDefectDetails1 = BaseUrl + "CMSC0213";// 查询defect详情列表
    public static String DefectReplyForm1 = BaseUrl + "CMSC0214";// 回复检查单
    public static String QueryDLPDefectType1 = BaseUrl + "CMSC0215";//查询defect type 类型
    public static String QueryDLPDefectTypeV2 = BaseUrl + "CMSC0215V2";//查询defect type 类型
    public static String QueryDefectBlockList1 = BaseUrl + "CMSC0216"; // Defect列表-Block0241
    public static String QueryDefectLevelList1 = BaseUrl + "CMSC0217"; // Defect列表-Level  带筛选条件的
    public static String QueryDefectUnitList1 = BaseUrl + "CMSC0218"; // Defect列表-Level  带筛选条件的
    public static String QueryDefectLevelUnitList = BaseUrl + "CMSC0219"; // Defect列表-Level unit 列表.

    public static String DMDefectSubmitDefect = BaseUrl + "CMSC0221";//submit 提交
    public static String DMDefectSendReply = BaseUrl + "CMSC0223";//消息回复
    public static String DefectSetStatus = BaseUrl + "CMSC0224";//申请人改变Defect状态
    public static String DefectSetPicStatus = BaseUrl + "CMSC0225";//申请人设置图片的状态
    public static String SubmitPanneList = BaseUrl + "CMSC0227";//讨论组添加人员
    public static String DeletePanneList = BaseUrl + "CMSC0229";//讨论组删除人员

    public static String QueryDefectBlockList = BaseUrl + "CMSC0241"; // Defect列表-Block0241
    public static String QueryDefectBlockListFILTER = BaseUrl + "CMSC0241FILTER"; // Defect列表-Block0241  带筛选条件的
    public static String QueryDefectLevelList = BaseUrl + "CMSC0242"; // Defect列表-Level  带筛选条件的
    public static String QueryDefectUnitList = BaseUrl + "CMSC0243"; // Defect列表-Unit  带筛选条件的
    public static String QueryDefectTypeStatistics = BaseUrl + "CMSC0244"; // 项目下Defect类型统计

    public static String QueryDMDLPDefectListDetails = BaseUrl + "CMSC0245";//图纸的Defect列表
    public static String QueryDMDLPActivityLog = BaseUrl + "CMSC0246";//查询房屋的ActivityLog
    public static String QueryDLPDefectType = BaseUrl + "CMSC0247";//查询defect type 类型
    public static String QueryDefectDetails = BaseUrl + "CMSC0248";// 查询defect详情列表
    public static String QueryDLPDefectPicsList = BaseUrl + "CMSC0249"; // 房屋已有Defect的pic列表
    public static String QueryDLPDefectIssueToPersonList = BaseUrl + "CMSC0250";//查询负责人Users列表
    public static String QueryDefectContractorStatistics = BaseUrl + "CMSC0251";//查询defect数量(企业)
    public static String QueryExistingPerson = BaseUrl + "CMSC0252";//查询defect现有人员

    public static String QueryDisciplineLists = BaseUrl + "CMSC0253";//查询Discipline列表
    public static String QueryTradeLists = BaseUrl + "CMSC0254";//获取Trade,承包商对应关系
    public static String QueryCategoryLists = BaseUrl + "CMSC0255";//查询 Category列表
    public static String CreateNewCategory = BaseUrl + "CMSC0256";//创建新的Category
    public static String SaveDefect2Draft = BaseUrl + "CMSC0257";//保存为草稿
    public static String SubmitDefect2 = BaseUrl + "CMSC0258";//提交检查单
    public static String QueryDefect2Details = BaseUrl + "CMSC0259";//查询检查单详情.
    public static String QueryDefect2PinLists = BaseUrl + "CMSC0260";//查询图纸上的点
    public static String QueryDefect2Lists = BaseUrl + "CMSC0261";//查询defect2列表
    public static String Defect2DetailsSubmit = BaseUrl + "CMSC0262";//defect详情提交操作.
    public static String QueryDefect2StatisticsLists = BaseUrl + "CMSC0264";//统计列表.
    public static String QueryDefect2ConstructionPower = BaseUrl + "CMSC0265";//查询Construction权限.

    //03   QA-Checklist 模块
    public static String ChecklistSaveData = BaseUrl + "CMSC0301";//新建Checklist流程单，如送流程单编号(check_id)则进行编辑
    public static String ChecklistListList = BaseUrl + "CMSC0302";//Checklist流程单列表
    public static String ChecklistDetailsData = BaseUrl + "CMSC0303V3";// Checklist流程单详情
    public static String ChecklistFormClosed = BaseUrl + "CMSC0304";//关闭检查单
    public static String ChecklistDelItem = BaseUrl + "CMSC0305";//checklist  删除item
    public static String ChecklistSend = BaseUrl + "CMSC0306";//checklist 新建或者审批
    public static String ChecklistFormInListList = BaseUrl + "CMSC0307";//查询含有某个检查单的流程单
    public static String QueryChecklistProjectPerson = BaseUrl + "CMSC0308";//查询项目成员
    public static String QueryActionList = BaseUrl + "CMSC0309V2";//查询action列表

    public static String ChecklistSubmitChecklistForm = BaseUrl + "CMSC0311";//新建检查单
    public static String ChecklistFormDetailsData = BaseUrl + "CMSC0312V2";//检查单详情
    public static String ChecklistDetailsSubmitData = BaseUrl + "CMSC0313";//检查单提交 申请人 submit
    public static String ChecklistDetailsDelChecklistItem = BaseUrl + "CMSC0314";//未提交的检查单删除：申请人可删除
    public static String ChecklistDetailsModificationData = BaseUrl + "CMSC0315";//检查单提交 申请人 submit
    public static String ChecklistDetailsInspectData = BaseUrl + "CMSC0316";//检查单审批 inspect
    public static String ChecklistDetailsApproveData = BaseUrl + "CMSC0317";//检查单批准 approve
    public static String QueryChecklistStatistics = BaseUrl + "CMSC0318";//checklist小单子状态数量统计--上传参数

    public static String ChecklistChecklistConditionList = BaseUrl + "CMSC0321V2";//获取表单项目及参数 (条件)
    public static String ChecklistTypeList = BaseUrl + "CMSC0322";//Checklist获取检查单类型及名称 二级
    public static String SubmitItemComment = BaseUrl + "CMSC0324";//form  item  添加评论
    public static String QueryFormItemComment = BaseUrl + "CMSC0325";//查询item 的评论
    public static String QueryFormItemDefectList = BaseUrl + "CMSC0326V2";//查询item 的defect
    public static String QueryProjectFormType = BaseUrl + "CMSC0327";//获取项目可用检查单一二三级如无项目自定义，则返回系统数据
    public static String QueryFormTypeLists = BaseUrl + "CMSC0328";//表单类型
    public static String QueryCreatorLists = BaseUrl + "CMSC0329";//获取创建者
    public static String QueryLastRecifyLists = BaseUrl + "CMSC0330";//获取最后一步回复人

    public static String ChecklistAddAttachment = BaseUrl + "CMSC0331";//流程单添加附件
    public static String ChecklistDelAttachment = BaseUrl + "CMSC0332";//流程单删除附件
    public static String ChecklistAddAttachment1 = BaseUrl + "CMSC0333";//流程单添加附件
    public static String QueryPDFImg = BaseUrl + "CMSC0334";//查询图纸
    public static String QueryDocument = BaseUrl + "CMSC0335";//查询checklist文档
    public static String QueryGroupTypeLists = BaseUrl + "CMSC0336";//查询Group Type人员集合

    public static String QueryZoneDrawingPosition = BaseUrl + "CMSC0357";//查询zone图纸上面的点.
    public static String QuerySendPersonGroup = BaseUrl + "CMSC0358";//自定义发送菜单.

    public static String QueryInspectionLists = BaseUrl + "CMSC0360";//查询inspection 列表
    public static String downloadInspectionProgress = BaseUrl + "CMSC0363";//查询Progress数据
    public static String InspectionUploadAndUpdateData = BaseUrl + "CMSC0364V2";//inspection上传更新数据
    public static String downloadInspectionData = BaseUrl + "CMSC0365V2";//inspection下载数据
    public static String downloadChecklistData = BaseUrl + "CMSC0366";//checklist下载数据
    public static String downloadCommentData = BaseUrl + "CMSC0367";//comment下载数据
    public static String downloadAttachmentData = BaseUrl + "CMSC0368";//Attachment下载数据
    public static String QueryChecklistVersion = BaseUrl + "CMSC0369";//查询Inspection记录服务器版本
    public static String RequestToModify = BaseUrl + "CMSC0370V2";//申请加锁/解锁推送
    public static String MainConBesManagerUnLock = BaseUrl + "CMSC0371";//表单记录解锁(推送给申请解锁人)
    public static String QueryDWPList = BaseUrl + "CMSC0372";//查询DWP 列表
    public static String QueryDWPDetails = BaseUrl + "CMSC0373V2";//查询DWP 详情
    public static String SubmitDWPConfim = BaseUrl + "CMSC0374V2";//DWP确认
    public static String SubmitDWPRemarks = BaseUrl + "CMSC0375";//DWP保存备注信息
    public static String QueryInspectionStatistics = BaseUrl + "CMSC0376";//查询inspection的统计.
    public static String DeleteAttachment = BaseUrl + "CMSC0378";//删除attachment 文档.

    //04 Task 模块
    public static String TaskBIMModelProgress = BaseUrl + "CMSC0401";//查询某一模型的进度（构件组，颜色）
    public static String TaskModelTaskList = BaseUrl + "CMSC0402";//查询某一构件的任务列表
    public static String QueryTaskDetails = BaseUrl + "CMSC0403";//查询某一构件某一任务详情
    public static String TaskStageRecordSubmit = BaseUrl + "CMSC0404";//新建task记录
    public static String TaskStageRecordEdit = BaseUrl + "CMSC0405";//编辑/完成task记录
    public static String TaskAddChecklist = BaseUrl + "CMSC0406";//新建构件的TASK-NA记录
    public static String TaskDelChecklist = BaseUrl + "CMSC0407";//删除构件的TASK-NA记录
    public static String QueryTaskDetailsChecklist = BaseUrl + "CMSC0408";//查询某一构件某一任务的checklist列表
    public static String TaskList = BaseUrl + "CMSC0409";//task记录列表
    public static String QueryTaskStatistics = BaseUrl + "CMSC0410";//项目task关联的checklist小单子状态数量统计
    public static String TaskBIMModelTemplate = BaseUrl + "CMSC0411";//查询任务模板
    public static String TaskphaseList = BaseUrl + "CMSC0412";//根据任务模板查询阶段和任务列表
    public static String TaskphaseList1 = BaseUrl + "CMSC0414";//根据任务模板查询阶段和任务列表

    public static String QueryTaskDetails1 = BaseUrl + "CMSC0421";//task记录详情
    public static String TaskDelTaskItem = BaseUrl + "CMSC0422";//删除未完成的task记录
    public static String TaskAddBigChecklist = BaseUrl + "CMSC0423";//关联checklist大单子
    public static String QueryTaskDetailsBigChecklist = BaseUrl + "CMSC0424";//查询task记录关联的checklist列表
    public static String TaskFinsh = BaseUrl + "CMSC0425";//task finish 检查单
    public static String DraftIsExistTask = BaseUrl + "CMSC0426";//判断草稿箱中某一构件是否存在某一task
    public static String TaskBIMModelProgress1 = BaseUrl + "CMSC0427";//查询模型构件的状态
    public static String QueryLegend = BaseUrl + "CMSC0428";//根据任务模板查询阶段图例
    public static String QueryTaskHistoryLists = BaseUrl + "CMSC0429";//0429 查询某一构件的某一任务的历史列表

    //05 Progress 模块.
    public static String QueryProgessTaskList = BaseUrl + "CMSC0501";//查询计划版本列表
    public static String QueryProgressTasksLists = BaseUrl + "CMSC0503";//查询任务列表
    public static String QueryProgressPlanDetailsBasic = BaseUrl + "CMSC0504";//查询任务详情--基本信息
    public static String QueryProgressSubTaskList = BaseUrl + "CMSC0505";//查询任务详情--子任务
    public static String QueryProgressSubTaskListDetailsRecord = BaseUrl + "CMSC0506";//查询任务详情--record动作列表
    public static String QueryProgressPlanAttachmentLists = BaseUrl + "CMSC0507";//查询任务详情--文档列表
    public static String QueryProgressPlanDefectLists = BaseUrl + "CMSC0508";//查询任务详情--defect列表
    public static String QueryProgressSubTaskListDetails = BaseUrl + "CMSC0509";//查询子任务详情
    public static String QueryProgressPlanDetails = BaseUrl + "CMSC0510";//查询计划详情
    public static String ProgressCreatePlan = BaseUrl + "CMSC0511";//创建计划.
    public static String ProgressFinishPlan = BaseUrl + "CMSC0512";//完成任务	--总包 .
    public static String CreateSubTask = BaseUrl + "CMSC0513";//任务新建子任务	--总包.
    public static String ProgressDeletePlan = BaseUrl + "CMSC0514";//任务删除未开始的子任务	--总包
    public static String ProgressRelatedAttachment = BaseUrl + "CMSC0515";//任务关联附件	--总包
    public static String ProgressDeleteRelatedAttachment = BaseUrl + "CMSC0516";//任务删除关联的附件	--总包
    public static String ProgressSubTaskCheckIn = BaseUrl + "CMSC0517";//子任务check-in	--分包
    public static String ProgressSubTaskCheckOut = BaseUrl + "CMSC0518";//子任务check-out	--分包
    public static String ProgressSubTaskComplete = BaseUrl + "CMSC0519";//子任务关闭	--总包

    //06 Storage 模块.
    public static String QueryStorageList = BaseUrl + "CMSC0601";//查询存储位置.
    public static String QueryStorageTrackRecordList = BaseUrl + "CMSC0602";//查询存储的状态变更记录.
    public static String StorageCreate = BaseUrl + "CMSC0603";//新建存储及位置.
    public static String StorageEditStorage = BaseUrl + "CMSC0604";//编辑存储.
    public static String StorageDelStorage = BaseUrl + "CMSC0605";//删除storage及位置.
    public static String StorageAddSlot = BaseUrl + "CMSC0606";//添加新的slot.
    public static String StorageDelSlot = BaseUrl + "CMSC0607";//删除slot.
    public static String StorageEditStatus = BaseUrl + "CMSC0608";//slot状态变更.



    public static String TaskChecklistDetails = BaseUrl + "CMSC9901";//查询显示每个阶段的checklist数量
    public static String TaskStatisticsStageList = BaseUrl + "CMSC9902";//task 统计 stage 统计
    public static String TaskStatisticsStageList1 = BaseUrl + "CMSC9903";//task 统计 stage 统计
}

