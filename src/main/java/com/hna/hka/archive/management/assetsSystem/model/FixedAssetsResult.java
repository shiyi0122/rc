package com.hna.hka.archive.management.assetsSystem.model;

import com.hna.hka.archive.management.system.util.PageDataResult;
import lombok.Data;

@Data
public class FixedAssetsResult extends PageDataResult {
    private int totalCycle;
    private double totalNetProfit;;
    private double totalRepay;
}
