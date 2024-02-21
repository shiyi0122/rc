package com.hna.hka.archive.management.system.dao;

import com.hna.hka.archive.management.system.model.WinningInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WinningInformationMapper {
    List<WinningInformation> getWinningInformation(@Param("currentUserPhone") String currentUserPhone,@Param("exchangeType") String exchangeType);
}
