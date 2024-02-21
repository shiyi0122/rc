(function(e){function t(t){for(var r,i,s=t[0],l=t[1],c=t[2],d=0,p=[];d<s.length;d++)i=s[d],Object.prototype.hasOwnProperty.call(n,i)&&n[i]&&p.push(n[i][0]),n[i]=0;for(r in l)Object.prototype.hasOwnProperty.call(l,r)&&(e[r]=l[r]);u&&u(t);while(p.length)p.shift()();return o.push.apply(o,c||[]),a()}function a(){for(var e,t=0;t<o.length;t++){for(var a=o[t],r=!0,s=1;s<a.length;s++){var l=a[s];0!==n[l]&&(r=!1)}r&&(o.splice(t--,1),e=i(i.s=a[0]))}return e}var r={},n={app:0},o=[];function i(t){if(r[t])return r[t].exports;var a=r[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,i),a.l=!0,a.exports}i.m=e,i.c=r,i.d=function(e,t,a){i.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,t){if(1&t&&(e=i(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(i.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)i.d(a,r,function(t){return e[t]}.bind(null,r));return a},i.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(t,"a",t),t},i.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},i.p="";var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=t,s=s.slice();for(var c=0;c<s.length;c++)t(s[c]);var u=l;o.push([1,"chunk-vendors"]),a()})({0:function(e,t){},1:function(e,t,a){e.exports=a("56d7")},2:function(e,t){},3:function(e,t){},"56d7":function(e,t,a){"use strict";a.r(t);a("e260"),a("e6cf"),a("cca6"),a("a79d");var r=a("2b0e"),n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:"app"}},[a("robot-peripheral")],1)},o=[],i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"partner-company"},[a("el-container",[a("el-header",{staticClass:"header"},[a("el-form",{ref:"searchValidateForm",staticClass:"search-validate-form",attrs:{inline:!0,model:e.searchValidateForm,"label-width":"100px",rules:e.searchValidateFormRules}},[a("el-form-item",{attrs:{label:"",prop:"spotId"}},[a("el-select",{attrs:{filterable:"",placeholder:"请选择景区",clearable:""},model:{value:e.searchValidateForm.spotId,callback:function(t){e.$set(e.searchValidateForm,"spotId",t)},expression:"searchValidateForm.spotId"}},e._l(e.searchScenicOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.SCENIC_SPOT_NAME,value:e.SCENIC_SPOT_ID}})})),1)],1),a("el-form-item",{attrs:{label:"",prop:"peripheralName"}},[a("el-input",{attrs:{placeholder:"请输入外设名称"},model:{value:e.searchValidateForm.peripheralName,callback:function(t){e.$set(e.searchValidateForm,"peripheralName",t)},expression:"searchValidateForm.peripheralName"}})],1),a("el-form-item",{attrs:{label:"",prop:"searchStartEndDate"}},[a("el-date-picker",{attrs:{"unlink-panels":"",type:e.dateType,"range-separator":"至","start-placeholder":"开始时间","end-placeholder":"结束时间",clearable:!1},model:{value:e.searchStartEndDate,callback:function(t){e.searchStartEndDate=t},expression:"searchStartEndDate"}})],1)],1),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.onSearch("searchValidateForm")}}},[e._v("搜索")]),a("el-button",{attrs:{type:"primary",icon:"el-icon-refresh-right"},on:{click:e.onReset}},[e._v("重置")]),a("el-button",{attrs:{type:"primary",icon:"el-icon-circle-plus-outline"},on:{click:e.onAdd}},[e._v("新增")]),a("el-button",{attrs:{type:"primary",loading:e.downloadLoading,icon:"el-icon-download"},on:{click:e.exportExcel}},[e._v("导出数据")]),a("el-upload",{ref:"upload",attrs:{action:"/system/robotPeripheral/importExcel","show-file-list":!1,"on-change":e.handleChange,"file-list":e.fileList,"on-remove":e.handleRemove,"on-success":e.handleImportSuccess,"on-exceed":e.handleExceed}},[a("el-button",{attrs:{type:"primary",ioading:e.importLoading,icon:"el-icon-upload2"},on:{click:e.importExcel}},[e._v("导入数据")])],1)],1),a("el-main",[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],staticClass:"table",staticStyle:{width:"100%"},attrs:{data:e.tableData,border:"","header-cell-style":{background:"#f2f2f2",color:"#606266",height:"38px",padding:"0px"},"cell-style":{height:"38px",padding:"0"},"default-sort":{prop:"grossProfit",order:"descending"}},on:{"sort-change":e.onSortChange}},[e._l(e.tableFields,(function(t,r){return a("el-table-column",{key:r,attrs:{prop:t,label:r,align:"center","min-width":"130",sortable:e.tableSortable(t)},scopedSlots:e._u([{key:"default",fn:function(r){return[a("div",[e._v(" "+e._s(e.tableContentValue(r.row,t))+" ")])]}}],null,!0)})})),a("el-table-column",{attrs:{label:"操作","min-width":"130"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",size:"small"},nativeOn:{click:function(a){return a.preventDefault(),e.editRow(t.$index,t.row)}}},[e._v(" 编辑 ")]),a("el-button",{attrs:{type:"text",size:"small"},nativeOn:{click:function(a){return a.preventDefault(),e.deleteRow(t.$index,t.row)}}},[e._v(" 删除 ")])]}}])})],2),a("el-table",{staticClass:"table",staticStyle:{width:"100%",display:"none"},attrs:{id:"outTable",data:e.exportTableData,border:""}},e._l(e.tableFields,(function(e,t){return a("el-table-column",{key:t,attrs:{prop:e,label:t,"min-width":"130",align:"center"}})})),1)],1),a("el-footer",[a("div",{staticClass:"block"},[a("el-pagination",{attrs:{background:"",layout:"prev, pager, next, total, sizes, jumper",total:e.total,"current-page":e.currentPage,"page-sizes":[10,20,30,40],"page-size":e.pageSize},on:{"current-change":e.onCurrentChange,"size-change":e.onSizeChange}})],1)]),a("el-dialog",{staticClass:"dialog",attrs:{title:e.dialogTitle+"机器人外设成本",visible:e.dialogVisible,"before-close":e.handleClose,"close-on-click-modal":!1},on:{"update:visible":function(t){e.dialogVisible=t}}},[a("el-form",{ref:"validateForm",staticClass:"demo-ruleForm",attrs:{model:e.validateForm,"label-width":"100px",rules:e.validateFormRules}},[a("el-form-item",{attrs:{label:"景区名称",prop:"scenicSpotId"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",placeholder:"请选择景区",clearable:""},on:{change:e.onChangeSpot},model:{value:e.validateForm.scenicSpotId,callback:function(t){e.$set(e.validateForm,"scenicSpotId",t)},expression:"validateForm.scenicSpotId"}},e._l(e.scenicOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.SCENIC_SPOT_NAME,value:e.SCENIC_SPOT_ID}})})),1)],1),a("el-form-item",{attrs:{label:"外设单位",prop:"peripheralCompany"}},[a("el-input",{attrs:{placeholder:"请输入外设单位"},model:{value:e.validateForm.peripheralCompany,callback:function(t){e.$set(e.validateForm,"peripheralCompany",t)},expression:"validateForm.peripheralCompany"}})],1),a("el-form-item",{attrs:{label:"外设名称",prop:"peripheralName"}},[a("el-input",{attrs:{placeholder:"请输入外设名称"},model:{value:e.validateForm.peripheralName,callback:function(t){e.$set(e.validateForm,"peripheralName",t)},expression:"validateForm.peripheralName"}})],1),a("el-form-item",{attrs:{label:"外设单价/元",prop:"peripheralPrice"}},[a("el-input",{attrs:{placeholder:"请输入外设单价/元"},model:{value:e.validateForm.peripheralPrice,callback:function(t){e.$set(e.validateForm,"peripheralPrice",t)},expression:"validateForm.peripheralPrice"}})],1),a("el-form-item",{attrs:{label:"外设数量",prop:"peripheralQuantity"}},[a("el-input",{attrs:{placeholder:"请输入外设数量"},model:{value:e.validateForm.peripheralQuantity,callback:function(t){e.$set(e.validateForm,"peripheralQuantity",e._n(t))},expression:"validateForm.peripheralQuantity"}})],1),a("el-form-item",{attrs:{label:"备注",prop:"remarks"}},[a("el-input",{attrs:{placeholder:"请输入备注"},model:{value:e.validateForm.remarks,callback:function(t){e.$set(e.validateForm,"remarks",t)},expression:"validateForm.remarks"}})],1)],1),a("div",{staticClass:"submit"},[a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitForm("validateForm")}}},[e._v("提交")]),a("el-button",{on:{click:e.dialogClose}},[e._v("取消")])],1)],1)],1)],1)},s=[],l=a("2909"),c=a("1da1"),u=a("5530");a("96cf"),a("a9e3"),a("d3b7"),a("3ca3"),a("ddb0"),a("2b3d"),a("159b"),a("99af"),a("5cc6"),a("9a8c"),a("a975"),a("735e"),a("c1ac"),a("d139"),a("3a7b"),a("d5d6"),a("82f8"),a("e91f"),a("60bd"),a("5f96"),a("3280"),a("3fcc"),a("ca91"),a("25a1"),a("cd26"),a("3c5d"),a("2954"),a("649e"),a("219c"),a("170b"),a("b39a"),a("72f7"),a("d81d"),a("b680"),a("21a6"),a("1146"),a("25f0"),a("4d90");function d(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"3",a=e.getFullYear().toString(),r=(e.getMonth()+1).toString().padStart(2,"0"),n=e.getDate().toString().padStart(2,"0"),o=(e.getHours().toString().padStart(2,"0"),e.getMinutes().toString().padStart(2,"0"),e.getSeconds().toString().padStart(2,"0"),"");return"3"==t?o=a+"-"+r+"-"+n:"2"==t?o=a+"-"+r:"1"==t&&(o=a),o}var p={name:"SettlementFlow",data:function(){function e(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(t,a,r){if(!a)return r(new Error(e+"不能为空"));Number(a)?r():r(new Error(e+"必须为数字值"))}}function t(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(t,a,r){if(!a)return console.log(a),r(new Error(e+"不能为空"));r()}}return{showTable:!0,feeInput:"",taxInput:"",searchFeeInput:"",searchTaxInput:"",tableFields:{"创建时间":"createDate","景区名称":"scenicSpotName","外设名称":"peripheralName","外设单价/元":"peripheralPrice","外设数量":"peripheralQuantity","外设单位":"peripheralCompany","备注":"remarks"},tableData:[],tableLoading:!1,searchCompanyOptions:[],searchScenicOptions:[],scenicOptions:[],robotOptions:[],companyOptions:[],dateStateOptions:[{value:"1",label:"按年查询"},{value:"2",label:"按月查询"},{value:"3",label:"按日查询"}],exportTableData:[],downloadLoading:!1,importLoading:!1,fileList:[],currentPage:1,pageSize:10,total:0,dialogVisible:!1,dialogState:"",selectDateState:"3",dateType:"daterange",valueFormat:"yyyy-MM-dd HH:mm:ss",searchValidateForm:{},validateForm:{},searchStartEndDate:"",companys:[],companyState:"",scenics:[],scenicState:"",downloadUrl:"",myChart:null,myLineChart:null,markLineData:[],option:{},statisticsStateList:[],dialogTitle:"",robotId:"",statisticDialogVisible:!1,dispatchTrackList:[],dispatchTrackDialogVisible:!1,dispatchTrailCurrentPage:1,dispatchTrailTotal:0,dispatchTrailPageSize:10,currentRobotCode:"",errorRecordsList:[],sumTotal:0,nowsumTotal:0,saturationTotal:0,activeName:"table",searchValidateFormRules:{},tableSort:"1,desc",validateFormRules:{scenicSpotId:[{validator:t("景区名称"),required:!0,trigger:["blur","change"]}],peripheralCompany:[{validator:t("外设单位"),required:!0,trigger:["blur","change"]}],peripheralName:[{validator:t("外设名称"),required:!0,trigger:["blur","change"]}],peripheralPrice:[{validator:e("外设单价/元"),required:!0,trigger:["blur","change"]}],peripheralQuantity:[{validator:t("外设数量"),required:!0,trigger:["blur","change"]}]}}},created:function(){this.getSearchValidateForm(),this.getValidateForm(),this.login(),this.getCompanyList(),this.getScenicList(),this.initTableData()},beforeMount:function(){},mounted:function(){},methods:{getSearchValidateForm:function(){var e={};for(var t in this.tableFields)e[this.tableFields[t]]="";this.searchValidateForm={spotId:""}},getValidateForm:function(){var e={};for(var t in this.tableFields)e[this.tableFields[t]]="";this.validateForm=Object(u["a"])(Object(u["a"])({},e),{},{robotPeripheralId:"",scenicSpotId:""})},login:function(){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function t(){var a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/loginSystem?username=lijiazhao&password=lijiazhao");case 2:a=t.sent,a.data;case 4:case"end":return t.stop()}}),t)})))()},getCompanyList:function(e){var t=this;return Object(c["a"])(regeneratorRuntime.mark((function a(){var r,n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return e||(e=null),a.next=3,t.$http.get("/system/robot_time_statistics/companyList",{params:{spotId:e}});case 3:if(r=a.sent,n=r.data,"200"!=n.state){a.next=11;break}return t.searchCompanyOptions=n.data,t.companyOptions=n.data,a.abrupt("return",n);case 11:return a.abrupt("return");case 12:case"end":return a.stop()}}),a)})))()},getScenicList:function(e){var t=this;return Object(c["a"])(regeneratorRuntime.mark((function a(){var r,n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return e||(e=null),a.next=3,t.$http.get("/system/robot_time_statistics/spotList",{params:{companyId:e}});case 3:if(r=a.sent,n=r.data,"200"!=n.state){a.next=11;break}return t.searchScenicOptions=n.data,t.scenicOptions=n.data,a.abrupt("return",n);case 11:return a.abrupt("return");case 12:case"end":return a.stop()}}),a)})))()},initTableData:function(){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function t(){var a,r,n,o;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.tableLoading=!0,a="",r="",0!==e.searchStartEndDate.length&&(a=d(e.searchStartEndDate[0],e.selectDateState),r=d(e.searchStartEndDate[1],e.selectDateState)),t.next=6,e.$http.get("/system/robotPeripheral/getRobotPeripheral",{params:Object(u["a"])(Object(u["a"])({},e.searchValidateForm),{},{beginDate:a,endDate:r,pageNum:e.currentPage,pageSize:e.pageSize})});case 6:if(n=t.sent,o=n.data,console.log(o),200==o.state){t.next=12;break}return e.$message({type:"error",message:"请求失败！"}),t.abrupt("return");case 12:e.tableData=o.data,e.total=o.total,e.tableLoading=!1;case 15:case"end":return t.stop()}}),t)})))()},getSpotGrossProfitMarginChartList:function(){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function t(){var a,r,n,o;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return a=null,r=null,0!==e.searchStartEndDate.length&&(a=d(e.searchStartEndDate[0],e.selectDateState),r=d(e.searchStartEndDate[1],e.selectDateState)),t.next=5,e.$http.get("/system/efficiencyAnalysis/getSpotGrossProfitMarginChartList",{params:Object(u["a"])(Object(u["a"])({},e.searchValidateForm),{},{type:e.selectDateState,startTime:a,endTime:r,sort:e.tableSort,pageNum:e.currentPage,pageSize:e.pageSize})});case 5:n=t.sent,o=n.data,console.log(o),e.option.xAxis.data=o.data.dateTime,e.option.series[0].data=o.data.utilizationRate,e.option.series[1].data=o.data.yearOnYear,e.option.series[2].data=o.data.monthOnMonth,e.myChart.setOption(e.option);case 13:case"end":return t.stop()}}),t)})))()},addTableData:function(){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return console.log(e.validateForm),t.next=3,e.$http.post("/system/robotPeripheral/addRobotPeripheral",e.validateForm);case 3:a=t.sent,r=a.data,"200"==r.state?(e.$message({message:r.msg,type:"success",offset:100,center:!0,duration:1e3}),e.initTableData(),e.$refs.validateForm.resetFields()):e.$message({message:r.msg,type:"error",offset:100,center:!0,duration:3e3});case 6:case"end":return t.stop()}}),t)})))()},editTableData:function(){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/system/robotPeripheral/editRobotPeripheral",e.validateForm);case 2:a=t.sent,r=a.data,"200"==r.state?(e.$message({message:r.msg,type:"success",offset:100,center:!0,duration:1e3}),e.initTableData()):e.$message({message:r.msg,type:"error",offset:100,center:!0,duration:3e3});case 5:case"end":return t.stop()}}),t)})))()},delTableData:function(e){var t=this;return Object(c["a"])(regeneratorRuntime.mark((function a(){var r,n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return console.log(e),a.next=3,t.$http.delete("/system/robotSoftAssetInformation/delRobotSoftAssetInformation",{params:{softAssetInformationId:e}});case 3:r=a.sent,n=r.data,"200"==n.state?(t.$message({message:n.msg,type:"success",offset:100,center:!0,duration:1e3}),t.initTableData()):t.$message({message:n.msg,type:"error",offset:100,center:!0,duration:3e3});case 6:case"end":return a.stop()}}),a)})))()},getExportTableData:function(){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function t(){var a,r,n,o,i,s,l;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return a=null,r=null,0!==e.searchStartEndDate.length&&(a=d(e.searchStartEndDate[0],e.selectDateState),r=d(e.searchStartEndDate[1],e.selectDateState)),console.log(a,r),n={peripheralName:e.searchValidateForm.peripheralName,spotId:e.searchValidateForm.spotId,beginDate:a,endDate:r},t.next=7,e.$http.post("/system/robotPeripheral/uploadExcelRobotPeripheral",e.$qs.stringify(n),{responseType:"arraybuffer"});case 7:o=t.sent,i=o.data,console.log(i),s=window.URL.createObjectURL(new Blob([i],{type:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})),l=document.createElement("a"),console.log(s),l.href=s,l.download="机器人外设成本",l.click(),e.downloadLoading=!1;case 17:case"end":return t.stop()}}),t)})))()},deleteRow:function(e,t){var a=this;console.log(e,t.robotPeripheralId),this.$confirm("此操作将删除该行数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(Object(c["a"])(regeneratorRuntime.mark((function e(){var r,n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a.$http.get("/system/robotPeripheral/delRobotPeripheral",{params:{robotPeripheralId:t.robotPeripheralId}});case 2:r=e.sent,n=r.data,console.log(n),"200"==n.state?(a.$message({type:"success",message:n.msg,offset:100,center:!0,duration:1e3}),a.initTableData()):a.$message({type:"error",message:n.msg,offset:100,center:!0,duration:3e3});case 6:case"end":return e.stop()}}),e)})))).catch((function(){a.$message({type:"info",message:"已取消删除",offset:100,center:!0,duration:1e3})}))},getRobotIdList:function(){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/system/robot/getRobotIdList");case 2:if(a=t.sent,r=a.data,200==r.state){t.next=7;break}return e.$message({message:"获取机器人列表失败！",type:"error"}),t.abrupt("return");case 7:e.robotOptions=r.data;case 8:case"end":return t.stop()}}),t)})))()},getSpotGrossProfitMarginListStatistical:function(){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function t(){var a,r,n,o;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return a=null,r=null,0!==e.searchStartEndDate.length&&(a=d(e.searchStartEndDate[0],e.selectDateState),r=d(e.searchStartEndDate[1],e.selectDateState)),t.next=5,e.$http.get("/system/operateGrossProfit/getSpotGrossProfitMarginListStatistical",{params:Object(u["a"])(Object(u["a"])({},e.searchValidateForm),{},{type:e.selectDateState,startData:a,endData:r})});case 5:if(n=t.sent,o=n.data,200==o.state){t.next=10;break}return e.$message({message:"获取机器人列表失败！",type:"error"}),t.abrupt("return");case 10:e.drawEcharts();case 11:case"end":return t.stop()}}),t)})))()},onChangeCompany:function(e){var t=this;return Object(c["a"])(regeneratorRuntime.mark((function a(){var r,n,o,i;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return console.log("???"),a.next=3,t.getScenicList(e);case 3:if(r=a.sent,n=r.data,t.scenicOptions=n,0!=n.length){a.next=9;break}return t.validateForm.scenicSpotId="",a.abrupt("return");case 9:o=!1,n.forEach((function(e){e.SCENIC_SPOT_ID!=t.validateForm.scenicSpotId||(o=!0)})),i=n[0].SCENIC_SPOT_ID,e&&!o&&(t.validateForm.scenicSpotId=i);case 13:case"end":return a.stop()}}),a)})))()},onChangeSpot:function(e){var t=this;return Object(c["a"])(regeneratorRuntime.mark((function a(){var r,n,o;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.next=2,t.getCompanyList(e);case 2:if(r=a.sent,n=r.data,0!=n.length){a.next=7;break}return t.validateForm.peripheralCompanyId="",a.abrupt("return");case 7:o=!1,n.forEach((function(e){e.COMPANY_ID!=t.validateForm.peripheralCompanyId||(o=!0)})),e&&!o&&(t.validateForm.peripheralCompanyId=n[0].COMPANY_ID);case 10:case"end":return a.stop()}}),a)})))()},onReset:function(){this.getSearchValidateForm(),this.currentPage=1,this.selectDateState="3",this.dateType="daterange",this.searchStartEndDate="",this.initTableData(),this.getCompanyList(),this.getScenicList()},onExportReport:function(){var e=this;this.$nextTick((function(){var t=document.getElementById("exportReport");t.href=e.myChart.getDataURL()}))},onExportReportLine:function(){var e=this;this.$nextTick((function(){var t=document.getElementById("exportReportLine");t.href=e.myLineChart.getDataURL(),console.log(t)}))},onSearch:function(e){console.log(e);var t=this;console.log(this.$refs[e]),this.$refs[e].validate((function(e){if(console.log(e),!e)return console.log("error submit!!"),!1;t.showTable=!0,t.initTableData()}))},exportExcel:function(){var e=this;this.$confirm("确认导出Excel表格?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(Object(c["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.downloadLoading=!0,t.next=3,e.getExportTableData();case 3:e.$message({type:"success",message:"已成功导出Excel表格！",offset:100,center:!0,duration:1e3});case 4:case"end":return t.stop()}}),t)})))).catch((function(){e.downloadLoading=!1,e.$message({type:"info",message:"已取消导出Excel表格",offset:100,center:!0,duration:1e3})}))},onAdd:function(){this.dialogVisible=!0,this.dialogState="add",this.dialogTitle="新增"},editRow:function(e,t){console.log(e,t),this.dialogVisible=!0,this.dialogState="edit",this.dialogTitle="编辑",this.validateForm=Object(u["a"])({},t)},dialogClose:function(){this.dialogVisible=!1,this.$refs.validateForm.resetFields(),this.getValidateForm()},handleClose:function(e){e(),this.$refs.validateForm.resetFields(),this.getValidateForm()},handleChange:function(e,t){this.fileTemp=e.raw,this.fileTemp?"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"==this.fileTemp.type||"application/vnd.ms-excel"==this.fileTemp.type?this.importExcel(this.fileTemp):this.$message({type:"warning",message:"附件格式错误，请删除后重新上传！"}):this.$message({type:"warning",message:"请上传附件！"})},handleExceed:function(e,t){this.$message.warning("当前限制选择 1 个文件，本次选择了 ".concat(e.length," 个文件，共选择了 ").concat(e.length+t.length," 个文件"))},handleRemove:function(e,t){this.fileTemp=null},handleBeforeUpload:function(e){this.importLoading=!0},handleImportSuccess:function(){this.importLoading=!1,this.$message({type:"success",message:"已成功导入Excel表格！",offset:100,center:!0,duration:1e3}),that.initTableData()},importExcel:function(e){var t=this;return Object(c["a"])(regeneratorRuntime.mark((function r(){return regeneratorRuntime.wrap((function(r){while(1)switch(r.prev=r.next){case 0:t,t.file=e,!1,t.file,new FileReader,FileReader.prototype.readAsBinaryString=function(e){var t,r,n="",o=!1,i=new FileReader;i.onload=function(e){for(var s=new Uint8Array(i.result),c=s.byteLength,u=0;u<c;u++)n+=String.fromCharCode(s[u]);var d=a("1146");t=o?d.read(btoa(fixdata(n)),{type:"base64"}):d.read(n,{type:"binary"}),r=d.utils.sheet_to_json(t.Sheets[t.SheetNames[0]],{range:1,defval:""}),this.da=Object(l["a"])(r);var p=[];return this.da.map((function(e){console.log(e);var t={createDate:e["创建时间"],scenicSpotName:e["景区名称"],peripheralName:e["外设名称"],peripheralPrice:e["外设价格"],peripheralQuantity:e["外设数量"],peripheralCompany:e["外设单位"],remarks:e["备注"]};p.push(t)})),console.log(p),this.tableData=p,p},i.readAsArrayBuffer(e)};case 6:case"end":return r.stop()}}),r)})))()},submitForm:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;"add"==t.dialogState?(console.log("新增机器人外设成本"),t.addTableData()):"edit"==t.dialogState&&(console.log("编辑机器人外设成本"),t.editTableData()),t.dialogVisible=!1,t.$refs.validateForm.resetFields(),t.getValidateForm()}))},onCurrentChange:function(e){this.currentPage=e,this.initTableData()},onSizeChange:function(e){this.pageSize=e,this.initTableData()},onChangeSearchEquipmentStatus:function(e){},onChangeEquipmentStatus:function(e){console.log(e)},onChangeRobotId:function(e){console.log(e)},handleCloseStatistic:function(){this.statisticDialogVisible=!1},handleTabClick:function(e,t){console.log(e,t)},drawEcharts:function(){var e=this;this.myChart=this.$echarts.init(document.getElementById("myChart")),this.option={tooltip:{trigger:"axis",axisPointer:{type:"cross",crossStyle:{color:"#999"}}},legend:{data:["机器人利用率/%","同比/%","环比/%"],top:0},xAxis:{type:"category",data:["Mon","Tue","Wed","Thu","Fri","Sat","Sun"],axisPointer:{type:"shadow"}},yAxis:[{name:"机器人利用率/%",type:"value",axisLabel:{formatter:"{value} 元"}},{name:"同比环比/%",type:"value",axisLabel:{formatter:"{value} %"}}],series:[{name:"机器人利用率/%",data:[120,200,150,80,70,110,130],type:"bar"},{name:"同比/%",type:"line",yAxisIndex:1,data:[120,200,150,80,70,110,130]},{name:"环比/%",type:"line",yAxisIndex:1,data:[20,120,200,100,80,50,150]}]},this.myChart.setOption(this.option),window.addEventListener("resize",(function(){e.myChart.resize()}))},onSortChange:function(e){var t=e.column,a=e.prop,r=e.order;console.log(t,a,r),"grossProfit"==a?this.tableSort="descending"==r?"1,desc":"ascending"==r?"1,asc":"":"runningAmount"==a?this.tableSort="descending"==r?"2,desc":"ascending"==r?"2,asc":"":"comprehensiveCost"==a?this.tableSort="descending"==r?"3,desc":"ascending"==r?"3,asc":"":"robotGrossProfit"==a?this.tableSort="descending"==r?"4,desc":"ascending"==r?"4,asc":"":"netProfit"==a&&(this.tableSort="descending"==r?"5,desc":"ascending"==r?"5,asc":""),this.initTableData()},getSummaries:function(e){var t=e.columns,a=(e.data,[]);return t.forEach((function(e,t){0!==t?1==t?a[t]="1":2==t?a[t]="2":3==t&&(a[t]="3"):a[t]="合计"})),a}},components:{},computed:{tableContentValue:function(){return function(e,t){var a="";return a="cost"==t||"comprehensiveCost"==t||"netProfit"==t||"yearOnYear"==t||"monthOnMonth"==t||"grossProfitMargin"==t||"netInterestRate"==t?"".concat((100*e[t]).toFixed(2),"%"):e[t],a}},tableSortable:function(){return function(e){var t="";return t=("runningAmount"==e||"comprehensiveCost"==e||"grossProfit"==e||"robotGrossProfit"==e||"netProfit"==e)&&"custom",t}}}},m=p,h=(a("bd97"),a("2877")),f=Object(h["a"])(m,i,s,!1,null,"5412d23d",null),g=f.exports,b={name:"App",components:{RobotPeripheral:g}},v=b,y=(a("5c0b"),Object(h["a"])(v,n,o,!1,null,null,null)),S=y.exports,x=a("2f62");r["default"].use(x["a"]);var w=new x["a"].Store({state:{},mutations:{},actions:{},modules:{}}),F=(a("9e1f"),a("450d"),a("6ed5")),C=a.n(F),D=(a("0fb7"),a("f529")),O=a.n(D),k=(a("d624"),a("3e9c")),R=a.n(k),T=(a("e612"),a("dd87")),P=a.n(T),$=(a("075a"),a("72aa")),E=a.n($),I=(a("fb08"),a("21e5")),L=a.n(I),_=(a("2f02"),a("fe11")),j=a.n(_),V=(a("ed7b"),a("e1a5")),N=a.n(V),A=(a("cbb5"),a("8bbc")),z=a.n(A),M=(a("9c49"),a("6640")),B=a.n(M),q=(a("d2ac"),a("95b0")),G=a.n(q),Q=(a("f225"),a("89a9")),U=a.n(Q),Y=(a("be4f"),a("896a")),H=a.n(Y),J=(a("826b"),a("c263")),W=a.n(J),K=(a("3db2"),a("58b8")),X=a.n(K),Z=(a("eca7"),a("3787")),ee=a.n(Z),te=(a("425f"),a("4105")),ae=a.n(te),re=(a("a7cc"),a("df33")),ne=a.n(re),oe=(a("672e"),a("101e")),ie=a.n(oe),se=(a("5466"),a("ecdf")),le=a.n(se),ce=(a("38a0"),a("ad41")),ue=a.n(ce),de=(a("10cb"),a("f3ad")),pe=a.n(de),me=(a("bdc7"),a("aa2f")),he=a.n(me),fe=(a("de31"),a("c69e")),ge=a.n(fe),be=(a("a673"),a("7b31")),ve=a.n(be),ye=(a("adec"),a("3d2d")),Se=a.n(ye),xe=(a("6611"),a("e772")),we=a.n(xe),Fe=(a("1f1a"),a("4e4b")),Ce=a.n(Fe),De=(a("1951"),a("eedf")),Oe=a.n(De);r["default"].use(Oe.a),r["default"].use(Ce.a),r["default"].use(we.a),r["default"].use(Se.a),r["default"].use(ve.a),r["default"].use(ge.a),r["default"].use(he.a),r["default"].use(pe.a),r["default"].use(ue.a),r["default"].use(le.a),r["default"].use(ie.a),r["default"].use(ne.a),r["default"].use(ae.a),r["default"].use(ee.a),r["default"].use(X.a),r["default"].use(W.a),r["default"].use(H.a),r["default"].use(U.a),r["default"].use(G.a),r["default"].use(B.a),r["default"].use(z.a),r["default"].use(N.a),r["default"].use(j.a),r["default"].use(L.a),r["default"].use(E.a),r["default"].use(P.a),r["default"].use(R.a),r["default"].prototype.$message=O.a,r["default"].prototype.$confirm=C.a.confirm;var ke=a("bc3a"),Re=a.n(ke),Te=a("4328"),Pe=a.n(Te),$e=a("313e"),Ee=a("1209");Re.a.defaults.withCredentials=!0,r["default"].prototype.$http=Re.a,r["default"].prototype.$qs=Pe.a,r["default"].config.productionTip=!1,r["default"].prototype.$echarts=$e,r["default"].prototype.$anime=Ee["a"],new r["default"]({store:w,render:function(e){return e(S)}}).$mount("#app")},"5c0b":function(e,t,a){"use strict";a("9c0c")},"9c0c":function(e,t,a){},bd97:function(e,t,a){"use strict";a("dffc")},dffc:function(e,t,a){}});
//# sourceMappingURL=app.9b0f1ec3.js.map