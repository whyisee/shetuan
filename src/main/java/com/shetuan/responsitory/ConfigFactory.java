package com.shetuan.responsitory;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/27 12:43
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class ConfigFactory {
    //序列
    public static String SEQ_LOGIN_ID="SELECT NEXTVAL('seq_user_id');";
    public static String SEQ_COMM_ID="SELECT NEXTVAL('seq_comm_id');";

    //活动id序号同审核id
    public static String SEQ_APPRO_ID="SELECT NEXTVAL('seq_appro_id');";

    //角色编码
    public static String ROLE_CODE_ADMIN="100";
    public static String ROLE_CODE_COMM_ADMIN="200";

    public static String ROLE_CODE_PERSION="500";


    //社团成员职位--最低展示
    public static String COMM_WORKER_ID="100";

    //审核状态
    public static String APPRO_STATUS="100";

    //社团申请创建状态
    public static String COMM_APPRO_STATUS="10";


    //审核类型
    //社团创建
    public static String APPRO_TYPE_COMM_ADD="11";
    //加入社团
    public static String APPRO_TYPE_COMM_SIGN="12";
    //删除社团
    public static String APPRO_TYPE_COMM_DEL="13";
    //退出社团
    public static String APPRO_TYPE_COMM_QUIT="14";

    //创建活动
    public static String APPRO_TYPE_ACT_ADD="21";
    //加入活动
    public static String APPRO_TYPE_ACT_SIGN="22";
    //删除活动
    public static String APPRO_TYPE_ACT_DEL="23";
    //退出活动
    public static String APPRO_TYPE_ACT_QUIT="24";


}
