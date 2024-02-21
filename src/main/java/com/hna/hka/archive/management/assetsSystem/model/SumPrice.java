package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @Author zhang
 * @Date 2022/3/24 10:46
 * 个别返回数据使用
 */
@Data
public class SumPrice {

    private Double operateCost;

    private Double spotMarketCost;

    private Double rent;

    private Double maintainCost;

}
