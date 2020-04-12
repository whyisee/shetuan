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


}
