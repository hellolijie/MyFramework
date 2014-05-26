package cn.lijie.net;

public class NetStatics {
	//服务器地址
	public static String GLOBALURL="http://112.65.246.206/";
	//数据格式
	public static String GETFORM="?get=json";
	
	//登录
	public static String LOGIN="AccountService.asmx/Login";		
	//预交单身份验证
	public static String CHECKIDCODE="AdvOrderService.asmx/CheckIdCode";
	//提交预交单申请
	public static String SUBMITPREORDER="AdvOrderService.asmx/SubmitPreOrder";
	//上传文件
	public static String UPLOADFILE="OtherService.asmx/UploadFile";
//	public static String UPLOADFILELIST="OtherService.asmx/UploadFileList";
	//提交文件列表
	public static String SUBMITFILELIST="OtherService.asmx/SubmitFileList";
	//下载文件
	public static String GETFILE="OtherService.asmx/GetFile";
	
	//暂存预交单
	public static String TEMPSTORAGEPREORDER="AdvOrderService.asmx/TempStoragePreOrder";
	//获取预交单列表
	public static String GETPREORDERLIST="AdvOrderService.asmx/GetPreOrderList";
	//获取预交单信息
	public static String GETPREORDERINFO="AdvOrderService.asmx/GetPreOrderInfo";
	//获取暂存的预交单列表
	public static String GETTEMPPREORDERLIST="AdvOrderService.asmx/GetTempPreOrderList";
	//获取暂存的预交单信息
	public static String GETTEMPPREORDERINFO="AdvOrderService.asmx/GetTempPreOrderInfo";
	//删除暂存的预交单
	public static String DELETETEMPPREORDER="AdvOrderService.asmx/DeleteTempPreOrder";
	//删除预交单
	public static String DELETEPREORDER="AdvOrderService.asmx/DeletePreOrder";
	//获取文件列表
	public static String GETFILELIST="OtherService.asmx/GetFileList";
	//抢单提醒
	public static String SUBMITWARN="AdvOrderService.asmx/SubmitWarn";
	
	
	//查询订单
	public static String QUERYORDERLIST="OrderService.asmx/QueryOrderList";
	//获取订单基本资料
	public static String GETORDERBASICINFO="OrderService.asmx/GetOrderBasicInfo";
	//获取订单审核信息
	public static String GETORDERVERIFYINFO="OrderService.asmx/GetOrderVerifyInfo";
	//获取订单签约备注信息
	public static String GETORDERCONTRACTINFO="OrderService.asmx/GetOrderContractInfo";
	
	//获得订单最新状态
	public static String GETORDERLASTSTATUES="OrderService.asmx/GetOrderLastStatus";
	//获取订单申请资料
	public static String GETORDERAPPLYINFO="Orderservice.asmx/GetOrderApplyInfo";
	//获取订单审核信息
//	public static String GETORDERVERIFYINFO="Orderservice.asmx/GetOrderVerifyInfo";
	//获取订单签约备注
//	public static String GETORDERCONTRACTINFO="Orderservice.asmx/GetOrderContractInfo";
	//获得订单放款信息
	public static String GETORDERLENDINGINFO="Orderservice.asmx/GetOrderLendingInfo";
	
	
	//获取配置文件
	public static String GETCONFIGINFO="OtherService.asmx/GetConfigInfo";
	
	//查询消息
	public static String QUERYMESSAGELIST="MessageService.asmx/QueryMessageList";
	//设置消息状态
	public static String UPDATEMESSAGESTATUS="MessageService.asmx/UpdateMessageStatus";
	
	//获取内容列表
	public static String GETCONTENTLIST="InformationService.asmx/GetContentList";
	//获取内容详情
	public static String GETCONTENTINFO="InformationService.asmx/GetContentInfo";
	
	//获取业务信息
	public static String GETPRODUCTLIST="InformationService.asmx/GetProductList";
	//获取业务详情
	public static String GETPRODUCTCONTENT="InformationService.asmx/GetProductContent";
	
	//修改密码
	public static String UPDATEPASSWORD="AccountService.asmx/UpdatePassword";
	
	//消息推送
	public static String PUSHMESSAGES="MessageService.asmx/PushMessages";
	
	//检查内容是否已被更新
	public static String ISCONTENTUPDATE="InformationService.asmx/IsContentUpdate";
	
	//获得报表信息
	public static String GETREPORTINFO="ReportService.asmx/GetReportInfo";
	
	//获取获取登录者未读的消息数量
	public static String GETMESSAGECOUNT="MessageService.asmx/GetMessageCount";
	
	//意见与反馈
	public static String SUBMITUSERFEEDBACK="AccountService.asmx/SubmitUserFeedback";
	
	//版本更新
	public static String ISVERSIONSUPDATE="OtherService.asmx/IsVersionsUpdate";
	
	//更新首頁數據
	public static String REFRESHHOMEPAGE="AccountService.asmx/RefreshHomepage";
}
