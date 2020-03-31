package com.shetuan.web;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/3/27 12:43
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
public class SeqFactory {
    public static String SEQ_LOGIN_ID="SELECT NEXTVAL('seq_user_id');";
    public static String SEQ_COMM_ID="SELECT NEXTVAL('seq_comm_id');";

}
