package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("地址管理")
public class Address {

  @ApiModelProperty("主键")
  private long id;
  @Excel(name = "地址类型" , width = 20 , replace = {"景区地址_1" , "工厂地址_2" , "库房地址_3" , "配件供应商地址_4"})
  @ApiModelProperty("地址类型：1、景区地址；2、工厂地址；3、库房地址；4、配件供应商地址")
  private long type;
  @ApiModelProperty("景区ID")
  private long spotId;
  @ApiModelProperty("景区名称")
  @Excel(name = "景区名称" , width = 20 , orderNum = "1")
  private String spotName;
  @ApiModelProperty("登录ID")
  private long userId;
  @ApiModelProperty("登录名")
  private String userName;
  @ApiModelProperty("角色ID")
  private long roleId;
  @ApiModelProperty("角色名")
  private String roleName;
  @ApiModelProperty("地址")
  @Excel(name = "地址" , width = 20 , orderNum = "2")
  private String address;
  @ApiModelProperty("联系人姓名")
  @Excel(name = "联系人姓名" , width = 20 , orderNum = "3")
  private String name;
  @ApiModelProperty("联系人电话")
  @Excel(name = "联系人电话" , width = 20 , orderNum = "4")
  private String phone;
  @ApiModelProperty("大区经理")
  @Excel(name = "大区经理" , width = 20 , orderNum = "5")
  private String managerName;
  @ApiModelProperty("大区经理电话")
  @Excel(name = "大区经理电话" , width = 20 , orderNum = "6")
  private String managerPhone;
  @ApiModelProperty("合作公司ID")
  private long companyId;
  @ApiModelProperty("合作公司名称")
  @Excel(name = "合作公司名称" , width = 20 , orderNum = "7")
  private String companyName;

  @ApiModelProperty("级别")
  @Excel(name = "级别" , width = 20 , orderNum = "8")
  private Integer level;

  @ApiModelProperty("默认邮寄地址")
  @Excel(name = "默认邮寄地址" , width = 20 , orderNum = "9")
  private String mailAddress;

  @ApiModelProperty("四级联动所需名称")
  private String label;

  private List<Address> addressList;






}
