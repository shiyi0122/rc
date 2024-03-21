package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysCurrentUserIntegralHuntLog {

    private Long treasureLogId;

    //关联用户ID
    private Long userId;

    private String userPhone;

    //景区id
    private Long scenicSpotId;

    //景区名称
    private String scenicSpotName;

    //奖品名称
    private String treasureLogName;

    //奖品类型  1代金券 2 免单  3实物
    private String treasureLogType;

    //消耗前积分额度
    private Long frontIntegralNum;

    //消耗后积分额度
    private Long afterIntegralNum;

    //当前消费积分
    private Long useIntegralNum;

    //0普通奖品 1大奖
    private String prizeSize;

    private String createDate;

    private String updateDate;

    private Integer pageNum;
    private Integer pageSize;
    private String userPhone1;
    private String picUrl;
    private String jackpotId;
    private String jackpotName;
}
