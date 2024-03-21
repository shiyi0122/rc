package com.hna.hka.archive.management.assetsSystem.task;

import com.hna.hka.archive.management.appSystem.service.AppCodeScanCodePrizeCashingService;
import com.hna.hka.archive.management.assetsSystem.service.SpotProfitSharingStatisticsService;
import com.hna.hka.archive.management.assetsSystem.service.StatementOfAccessoriesService;
import com.hna.hka.archive.management.assetsSystem.service.SysRobotSoftAssetInformationService;
import com.hna.hka.archive.management.system.service.SysRobotService;
import com.hna.hka.archive.management.system.service.SysRobotUnusualLogService;
import com.hna.hka.archive.management.system.service.SysScenicSpotActivityService;
import com.hna.hka.archive.management.system.service.SysScenicSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.task
 * @ClassName: RcTask （本地执行请注释掉定时任务）
 * @Author: 郭凯
 * @Description: 定时器
 * @Date: 2021/7/4 18:15
 * @Version: 1.0
 */
@Component
public class RcTask {

    ExecutorService service = Executors.newFixedThreadPool(10);

    @Autowired
    private SysScenicSpotService sysScenicSpotService;
    @Autowired
    private SysRobotSoftAssetInformationService sysRobotSoftAssetInformationService;
    @Autowired
    private SpotProfitSharingStatisticsService spotProfitSharingStatisticsService;
    @Autowired
    private StatementOfAccessoriesService statementOfAccessoriesService;
    @Autowired
    private SysRobotUnusualLogService sysRobotUnusualLogService;
    @Autowired
    private SysScenicSpotActivityService sysScenicSpotActivityService;
    @Autowired
    private SysRobotService sysRobotService;
    @Autowired
    private AppCodeScanCodePrizeCashingService appCodeScanCodePrizeCashingService;

//    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨一点执行
    public void calculateRevenue() throws Exception {
        service.execute(() -> {
            sysScenicSpotService.addScenicSpotOperationRules();
        });
    }

    //每月执行一次，更新机器人折旧与净值
//    @Scheduled(cron = "0 59 23 28-31 * ?")
    public void robotCostUpdate() throws Exception {
        final Calendar c = Calendar.getInstance();
        if (c.get(Calendar.DATE) == c.getActualMaximum(Calendar.DATE)) {
            service.execute(() -> {
                sysRobotSoftAssetInformationService.updateRobotSoftAssetInformation();
            });
        }
    }


    //每月5号凌晨统计分润，统计页面数据
//    @Scheduled(cron = "0 0 1 5 * ?")
    public void spotProfitSharingStatisticeTimedStatistics() throws Exception {
        service.execute(() -> {

            spotProfitSharingStatisticsService.spotProfitSharingStatisticeTimedStatistics();

        });
    }

    //每每月凌晨1点，计算景区承担配件费以及就景区承担维修费的金额
//    @Scheduled(cron = "0 0 1 1 * ?")
    public void timingCalculationSpotPartsMaintenance() throws Exception {

        service.execute(() -> {
            statementOfAccessoriesService.timingCalculationSpotPartsMaintenance();
        });
    }


    //早8晚10,每5分钟，计算运营景区中的机器人的异常状态
//    @Scheduled(cron = "0 0/5 8-22 * * ?")
    public void timingRobotSpotUnusualLog() throws Exception {

        service.execute(() -> {
            sysRobotUnusualLogService.timingRobotSpotUnusualLog();
        });
    }

    //早8晚8,每1小时，计算运营景区中的超长订单状态
//    @Scheduled(cron = "0 0 8-20/1 * * ?")
//    @Scheduled(cron ="0 0/3 8-20 * * ?")
    public void timingRobotSpotOrderLog() throws Exception {
        service.execute(() -> {
            sysRobotUnusualLogService.timingRobotSpotOrderLog();
        });
    }


    //早9晚7,，计算运营景区中饱和度异常状态
//    @Scheduled(cron = "0 7,37 9-19 * * ?")
    public void timingScenicSpotSaturationLog() throws Exception {
        service.execute(() -> {
            sysRobotUnusualLogService.timingScenicSpotSaturationLog();
        });
    }

    //当日营收曲线每分钟统计订单数量和饱和度
//    @Scheduled(cron = "0 */1 5-23 * * ?")
    public void timingScenicSpotOrder() throws Exception {
        service.execute(() -> {
            sysScenicSpotService.timingScenicSpotOrder();
        });
    }

    //当日寻宝奖品的兑换是否过期
//    @Scheduled(cron = "0 0 2 * * ?")
    public void timingExchangePrize() throws Exception {
        service.execute(() -> {
            appCodeScanCodePrizeCashingService.timingExchangePrize();
        });
    }

    //PAD升级推送
//    @Scheduled(cron = "0 0/3 0-5 * * ?")
//    @Scheduled(cron = "0 15 20 * * ?")
    public void timingRobotAuto() throws Exception {
        service.execute(() -> {
            try {
                sysRobotService.timingRobotAuto();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //资产管理中机器人资产信息与后台管理中机器人档案信息同步(暂未测试使用)
    //    @Scheduled(cron="0 30 1 * * ?")//每天凌晨一点执行
//    public void  robotSynchronization() throws Exception{
//        service.execute(() -> {
//            sysRobotService.robotSynchronization();
//        });
//    }


    //晚8,计算运行中景区得每台机器人当前电量并推送管理者app(暂未使用)
//    @Scheduled(cron ="0 0 21 * * ?")
//    public void timingRobotQuantityLog() throws Exception{
//        service.execute(()->{
//            sysRobotService.timingRobotQuantityLog();
//        });
//    }


    //每天凌晨0.15分，判断优惠政策活动时间是否过期，(修改)未使用
//    @Scheduled(cron ="0 30 11 * * ?")
//    public void timingSpotActivity() throws Exception{
//        service.execute(()->{
//            sysScenicSpotActivityService.timingEditActivityFailure();
//        });
//    }


}
