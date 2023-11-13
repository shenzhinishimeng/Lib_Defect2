package com.catail.lib_commons.utils;

public class ConstantValue {
    public static int takePhotoCode = 0x1081;//拍照的requestCode和resultCode

    public static int QueryMaterialsRecordList = 0x0127;//materials 列表查询requestCode和resultCode

    public static int SelectedFiles = 0x1005;//培训/会议选择文档的requestCode和resultCode
    public static int SelectedTaskFiles = 0x1111;//图纸选择requestCode和resultCode
    public static int SelectedDocumentFiles = 0x12222;//文档选择的requestCode和resultCode

    public static int BIMAXModeSelected = 0x1080;//BIMAX模型选择的requestCode和resultCode
    public static int FilterCalendarDate = 0x1021;//筛选模块中选择日历时间的requestCode和resultCode
    public static int ImageEditorCode = 0x1010;//图片编辑的的requestCode和resultCode
    public static int choosePDFPhotoImg = 0x1031;//选择文件从本地
    public static int chooseNetPDFPhotoImg = 0x1032;//选择网络选择pdf文档转换成本地图片,选择后添加


    public static int DefectAddPersonCode = 0x2001;//Defect添加人员的requestCode和resultCode
    public static int DefectSendOkRefreshUI = 0x2002;//Defect 回复完刷新界面的requestCode和resultCode
    public static int DefectAddComponentCode = 0x2003;//defect 添加构件
    public static int DefectFilterCode = 0x2004;//defect筛选的requestCode 和resultCode


    public static int DefectApplyCloseActivityCode = 0x2021;//defect 申请流程结束后,关闭全部界面的code
    public static int DefectSelectTypeCode = 0x2047;//选择Defect类型
    public static int DefectSelectIssuseToPersonCode = 0x2050;//选择负责人
    public static int Defect2SubmitAndSaveCode = 0x2058;//保存和提交后的requestCode 和resultCode

    public static int Defect2TypeSelectCode = 0x2089; //defect2 选择类型的requestCode 和resultCode
    public static int Defect2CategorySelectCode = 0x2088;   //defect2 选择defect类型的requestCode 和resultCode

    public static int ChecklistFilterCode = 0x3001;//checklist筛选的requestCode 和resultCode
    public static int ChecklistEditCode = 0x3002;//checklist 编辑的requestCode和resultCode
    public static int ChecklistActionCode = 0x3006;//checklist action按钮的requestCode和resultCode
    public static int ChecklistSubmitChecklistFormCode = 0x3011;//checklist提交表单的requestCode和resultCode
    public static int ChecklistApprovedCode = 0x3016;// checklist审批批准的requestCode 和resultCode
    public static int ChecklistItemDefectListCode = 0x3026;//checklist item defect 列表的requestCode 和resultCode
    public static int ChecklistFinishCode = 0x3001;
    public static int ChecklistDefectFinishCode = 0x3030;//checklist item 发起defect后的requestCode 和resultCode
    public static int ChecklistCommentFinishCode = 0x3031;//checklist item 发起评论后的requestCode 和resultCode

    public static int InspectionTableNAddAttachmentName = 0x3088;//表格添加文档名称的requestCode


    public static int TaskRefreshDetails = 0x4002;//materials 刷新task 详情defect列表的requestCode和 resultCode
    public static int TaskRefreshTaskList = 0x4002;//task 刷新task 列表的requestCode和 resultCode
    public static int TaskAddChecklist = 0x4006;//task 申请添加checklist 的requestCode和resultCode
    public static int TaskApplyQrCode = 0x4010;//task  申请页面 扫码的requestCode和resultCode
    public static int TaskApplyModelSelected = 0x4011;//task 申请页面 选择模型  的requestCode和resultCode
    public static int TaskApplyDialogCreateChecklist = 0x4022;//task 申请界面 dialog 创建checklist的RequestCode 和resultCode
    public static int TaskDrawingSaveCode = 0x4023;//task drawing 界面pin图标的RequestCode 和resultCode
    public static int TaskDrawingListSaveCode = 0x4025;//task drawing 列表pin图标的RequestCode 和resultCode

    public static int InspectionTypeToDetails = 0x0364;//inspection类型页面第一次申请打开详情页面的RequestCode 和resultCode
    public static int InspectionFilterCode = 0x0369;//inspection 列表筛选的RequestCode 和resultCode
    public static int InspectionListFresh = 0x0370;//inspection 列表刷新的RequestCode 和resultCode


    public static int ProgressPlanRefreshCode = 0x0501;//progress plan 刷新 requestCode 和resultCode
    public static int ProgressSubTaskOperateCode = 0x0513;//progress sub task 申请编辑的requestCode和 resultCode
    public static int ProgressSubTaskCheckCode = 0x0517;//progress check in  check out的requestCode和 resultCode
    public static int ProgressSubTaskDefectCode = 0x0509;//progress 创建defect 的requestCode和 resultCode
    public static int ProgressSubTaskCompleteCode = 0x0519;//progress 子任务关闭--总包的requestCode和 resultCode
    public static int ProgressDefectCreateCode = 0x0520;//progress defect 创建的后刷新的requestCode和 resultCode


    public static int StorageCreateCode = 0x0603;// storage 创建的    requestCode 和resultCode
    public static int StorageEditStatusCode = 0x0608;//// storage编辑状态    requestCode 和resultCode

    public static int CopyWeChatImageSelector = 0x9999;//仿微信图片选择的request_Code和Result_Code

    public static int YZModelOptionDialogCode = 0x9999;//模型点击隐藏弹窗request_Code和Result_Code
    public static int YZModelOptionHideDialogCode = 0x9998;//模型点击隐藏弹窗request_Code和Result_Code
    public static int YZModelOptionIsolateDialogCode = 0x9997;//模型点击隔离弹窗request_Code和Result_Code

    public static int ChecklistAddPersonCode = 0x3001;//Checklist添加人员的requestCode和resultCode

    public static final String COMMON_PERSON = "common_person";//保存常用人
}
