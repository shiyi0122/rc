package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.appSystem.model.BigPadSpot;
import com.hna.hka.archive.management.assetsSystem.model.ScenicSpot;
import com.hna.hka.archive.management.business.model.BusinessScenicSpotArea;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotService
 * @Author: 郭凯
 * @Description: 景区管理业务层（接口）
 * @Date: 2020/5/21 11:30
 * @Version: 1.0
 */
public interface SysScenicSpotService {
    /**
     * 根据ID查询景区
     *
     * @param scenicSpotId
     * @return
     */
    SysScenicSpot getSysScenicSpotById(Long scenicSpotId);

    /**
     * 景区列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getScenicSpotList(Integer pageNum, Integer pageSize, Map<String, String> search);

    /**
     * 查询景区归属地
     *
     * @return
     */
    List<SysScenicSpotBinding> getScenicSpotBindingList();

    /**
     * 新增景区
     *
     * @param sysScenicSpot
     * @param modificationLog
     * @return
     */
    int addScenicSpot(SysScenicSpot sysScenicSpot, SysScenicSpotPriceModificationLog modificationLog);

    /**
     * 新增景区(改)
     *
     * @param sysScenicSpotExpand
     * @param modificationLog
     * @return
     */
    int addScenicSpotNew(SysScenicSpotExpand sysScenicSpotExpand, SysScenicSpotPriceModificationLog modificationLog, BusinessScenicSpotArea businessScenicSpotArea);

    /**
     * 修改景区信息
     *
     * @param sysScenicSpot
     * @return
     */
    int editScenicSpot(SysScenicSpot sysScenicSpot, SysScenicSpotPriceModificationLog modificationLog);

    /**
     * 修改景区信息（改）
     *
     * @param sysScenicSpotExpand
     * @return
     */
    int editScenicSpotNew(BusinessScenicSpotArea businessScenicSpotArea, SysScenicSpotExpand sysScenicSpotExpand, SysScenicSpotPriceModificationLog modificationLog);

    /**
     * 删除景区
     *
     * @param scenicSpotId
     * @return
     */
    int delScenicSpot(Long scenicSpotId);

    /**
     * 修改景区状态
     *
     * @param sysScenicSpot
     * @return
     */
    int updateScenicSpotState(SysScenicSpot sysScenicSpot);

    /**
     * 获取当前景区
     *
     * @param scenicSpotId
     * @return
     */
    SysScenicSpot getCurrentScenicSpot(long scenicSpotId);

    /**
     * 查询当前景区是否有封顶价格
     *
     * @param scenicSpotId
     * @return
     */
    SysScenicSpotCapPrice getCapPriceByScenicSpotId(Long scenicSpotId);

    /**
     * 新增景区封顶价格
     *
     * @param capPrice
     * @return
     */
    int addCapPrice(SysScenicSpotCapPrice capPrice, SysUsers user);

    /**
     * 查询景区封顶价格
     *
     * @param scenicSpotId
     * @return
     */
    SysScenicSpotCapPrice getScenicSpotCapPriceByScenicSpotId(Long scenicSpotId);

    /**
     * 编辑景区封顶价格
     *
     * @param capPrice
     * @return
     */
    int editCapPrice(SysScenicSpotCapPrice capPrice, SysUsers user);

    int addScenicSpotPicture(MultipartFile file, SysScenicSpotImg sysScenicSpotImg);

    List<SysScenicSpot> getScenicSpotBillingRulesList(Map<String, Object> search);

    PageDataResult getScenicSpotCapPriceList(Integer pageNum, Integer pageSize, Map<String, String> search);

    PageDataResult getScenicSpotPadList(Integer pageNum, Integer pageSize, Map<String, String> search);

    SysRobotAppVersion getRobotAppVersionById(Long scenicSpotId);

    int addScenicSpotPad(Long scenicSpotId, Long padId,String autoUpdateMonitor);

    int editScenicSpotPad(Long versionId, Long scenicSpotId, Long padId, String autoUpdateMonitor);

    PageDataResult getSemanticResourcesList(Integer pageNum, Integer pageSize, Map<String, String> search);

    List<SysScenicSpot> getScenicSpotExcel(Map<String, String> search);

    void addScenicSpotOperationRules();

    List<SysScenicSpotBinding> getScenicSpotBindingListA();


    List<SysScenicSpotCapPrice> getScenicSpotCapPriceExcel(Map<String, String> search);

    int updateScenicSpotSwitchs(Long sysScenicSpotid, String switchs);

    List<SysScenicSpot> getScenicSpotNameList();

    SysScenicSpotAndCap getSysScenicSpotAndCap(Long scenicSpotId);

    int getScenicSpotSwitchs(long scenicSpotId);

    int delCapPrice(SysScenicSpotCapPrice capPrice);


    List<BigPadSpot> getSpotIdList();


    Map getSpotIdPrice(String spotId);

    SysScenicSpot getSpotNameById(String scenicSpotName);


    List<SysScenicSpot> getSysScenicSpotAll();


    List<SysScenicSpotBinding> getScenicSpotBindingAllList();


    void timingScenicSpotOrder();


    int oneTouchUpdateScenicSpotSwitchs(String switchs);


    List<SysScenicSpot> getScenicSpotById(ScenicSpot scenicSpot);

}
