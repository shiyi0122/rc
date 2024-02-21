package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

public interface WinningInformationService {
    PageDataResult getWinningInformation(Integer pageNum, Integer pageSize, String currentUserPhone,String exchangeType);
}
