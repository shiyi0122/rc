package com.hna.hka.archive.management.assetsSystem.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@ApiModel("常见故障")
public class CommonFault {

  @ApiModelProperty(name = "id" , value = "id")
  private long id;
  @ApiModelProperty(name = "title" , value = "问题标题")
  private String title;
  @ApiModelProperty(name = "phenomenon" , value = "现象")
  private String phenomenon;
  @ApiModelProperty(name = "resolvent" , value = "解决方法")
  private String resolvent;
  @ApiModelProperty(name = "video" , value = "视频操作")
  private String video;
  @ApiModelProperty(name = "applicableModel" , value = "适用机型")
  private String applicableModel;
  @ApiModelProperty(name = "createTime" , value = "创建时间")
  private String createTime;
  @ApiModelProperty(name = "notes" , value = "备注")
  private String notes;

}
