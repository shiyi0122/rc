package com.hna.hka.archive.management.system.util;

/**
 * @ClassName Constant
 * @Description 设置返回状态
 * @Author 郭凯
 * @Date 2020/4/26 14:58
 * @Version 1.0
 **/
public class Constant {
    //============================================返回状态(状态不可重复)===============================================//

    //成功状态
    public static final String STATE_SUCCESS = "200";

    //失败状态
    public static final String STATE_FAILURE = "400";

    //失败状态
    public static final String STATE_NORMAL = "201";

    //==================================================个推推送TYPE========================================================//
    //结束服务
    public static final String END_THE_JOURNEY = "503";
    //闲置状态
    public static final String ROBOT_IDLE_STATE = "504";
    //管理员解锁机器人
    public static final String ADMINISTRATOR_UNLOCKING_ROBOT = "505";
    //管理员锁定机器人
    public static final String ADMINISTRATOR_LOCKS_ROBOT = "506";
    //解除锁定
    public static final String ROBOT_UNLOCKING = "507";
    //VIP解锁
    public static final String VIP_UNLOCKING = "508";
    //运营人员维护
    public static final String OPERATOR_MAINTENANCE = "509";
    //PAD升级
    public static final String ROBOT_UPDATE = "700";

}
