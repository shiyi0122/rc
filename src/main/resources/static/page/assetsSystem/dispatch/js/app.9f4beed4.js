(function(e){function t(t){for(var r,o,s=t[0],l=t[1],c=t[2],d=0,p=[];d<s.length;d++)o=s[d],Object.prototype.hasOwnProperty.call(n,o)&&n[o]&&p.push(n[o][0]),n[o]=0;for(r in l)Object.prototype.hasOwnProperty.call(l,r)&&(e[r]=l[r]);u&&u(t);while(p.length)p.shift()();return i.push.apply(i,c||[]),a()}function a(){for(var e,t=0;t<i.length;t++){for(var a=i[t],r=!0,s=1;s<a.length;s++){var l=a[s];0!==n[l]&&(r=!1)}r&&(i.splice(t--,1),e=o(o.s=a[0]))}return e}var r={},n={app:0},i=[];function o(t){if(r[t])return r[t].exports;var a=r[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,o),a.l=!0,a.exports}o.m=e,o.c=r,o.d=function(e,t,a){o.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},o.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},o.t=function(e,t){if(1&t&&(e=o(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(o.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)o.d(a,r,function(t){return e[t]}.bind(null,r));return a},o.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return o.d(t,"a",t),t},o.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},o.p="";var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=t,s=s.slice();for(var c=0;c<s.length;c++)t(s[c]);var u=l;i.push([1,"chunk-vendors"]),a()})({0:function(e,t){},1:function(e,t,a){e.exports=a("56d7")},2:function(e,t){},3:function(e,t){},"56d7":function(e,t,a){"use strict";a.r(t);a("0fae");var r=a("9e2f"),n=a.n(r),i=(a("e260"),a("e6cf"),a("cca6"),a("a79d"),a("2b0e")),o=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:"app"}},[a("spot-dispatch")],1)},s=[],l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"partner-company"},[a("el-container",[a("el-header",{staticClass:"header"},[a("el-form",{ref:"searchValidateForm",staticClass:"search-validate-form",attrs:{inline:!0,model:e.searchValidateForm,"label-width":"100px",rules:e.searchValidateFormRules}},[a("el-form-item",{attrs:{label:"",prop:"spotId"}},[a("el-select",{attrs:{filterable:"",placeholder:"请选择景区",clearable:""},model:{value:e.searchValidateForm.spotId,callback:function(t){e.$set(e.searchValidateForm,"spotId",t)},expression:"searchValidateForm.spotId"}},e._l(e.searchScenicOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.spotName,value:e.spotId}})})),1)],1),a("el-form-item",{attrs:{label:"",prop:"startEndDate"}},[a("el-date-picker",{attrs:{"unlink-panels":"",type:"daterange","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期","value-format":e.valueFormat},model:{value:e.searchValidateForm.startEndDate,callback:function(t){e.$set(e.searchValidateForm,"startEndDate",t)},expression:"searchValidateForm.startEndDate"}})],1),a("el-form-item",{attrs:{label:"",prop:"type"}},[a("el-select",{attrs:{filterable:"",placeholder:"请选择调运进度"},model:{value:e.searchValidateForm.type,callback:function(t){e.$set(e.searchValidateForm,"type",t)},expression:"searchValidateForm.type"}},e._l(e.typeOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.label,value:e.value}})})),1)],1)],1),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.onSearch("searchValidateForm")}}},[e._v("搜索")]),a("el-button",{attrs:{type:"primary",icon:"el-icon-refresh-right"},on:{click:e.onReset}},[e._v("重置")]),a("el-button",{attrs:{type:"primary",icon:"el-icon-circle-plus-outline"},on:{click:e.onAdd}},[e._v("新增")]),a("el-button",{attrs:{type:"primary",loading:e.downloadLoading,icon:"el-icon-s-data"},on:{click:e.exportExcel}},[e._v("导出Excel")])],1),a("el-main",[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],staticClass:"table",staticStyle:{width:"100%"},attrs:{data:e.tableData,border:"","header-cell-style":{background:"#f2f2f2",color:"#606266",height:"38px",padding:"0px"},"cell-style":{height:"38px",padding:"0"}}},[e._l(e.tableFields,(function(t,r){return a("el-table-column",{key:r,attrs:{prop:t,label:r,"min-width":"130"},scopedSlots:e._u([{key:"default",fn:function(r){return[a("div",[e._v(" "+e._s(e.tableContentValue(r.row,t))+" ")])]}}],null,!0)})})),a("el-table-column",{attrs:{label:"操作","min-width":"130"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"text",size:"small"},nativeOn:{click:function(a){return a.preventDefault(),e.editRow(t.$index,t.row)}}},[e._v(" 编辑 ")]),a("el-button",{attrs:{type:"text",size:"small"},nativeOn:{click:function(a){return a.preventDefault(),e.detailRow(t.$index,t.row)}}},[e._v(" 详情 ")])]}}])})],2),a("el-table",{staticClass:"table",staticStyle:{width:"100%",display:"none"},attrs:{id:"outTable",data:e.exportTableData,border:""}},e._l(e.tableFields,(function(t,r){return a("el-table-column",{key:r,attrs:{prop:t,label:r,"min-width":"130",align:"center"},scopedSlots:e._u([{key:"default",fn:function(r){return[a("div",[e._v(" "+e._s(e.tableContentValue(r.row,t))+" ")])]}}],null,!0)})})),1)],1),a("el-footer",[a("div",{staticClass:"block"},[a("el-pagination",{attrs:{background:"",layout:"prev, pager, next, total, sizes, jumper",total:e.total,"current-page":e.currentPage,"page-sizes":[10,20,30,40,50,60,70,80,90],"page-size":e.pageSize},on:{"current-change":e.onCurrentChange,"size-change":e.onSizeChange}})],1)]),a("el-dialog",{staticClass:"dialog",attrs:{title:"新增景区调运信息",visible:e.dialogVisible,"before-close":e.handleClose,"close-on-click-modal":!1},on:{"update:visible":function(t){e.dialogVisible=t}}},[a("el-form",{ref:"validateForm",staticClass:"demo-ruleForm",attrs:{model:e.validateForm,"label-width":"100px",rules:e.validateFormRules}},[a("el-form-item",{attrs:{label:"申请人",prop:"applicantId"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",placeholder:"请选择申请人"},on:{change:e.onChangeApplicant},model:{value:e.validateForm.applicantId,callback:function(t){e.$set(e.validateForm,"applicantId",t)},expression:"validateForm.applicantId"}},e._l(e.applicanteOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.userName,value:e.userId}})})),1)],1),a("el-form-item",{attrs:{label:"申请时间",prop:"applicantTime"}},[a("el-date-picker",{staticStyle:{width:"100%"},attrs:{align:"right",type:"datetime",placeholder:"选择申请时间","value-format":"yyyy-MM-dd hh:mm:ss","picker-options":e.pickerOptions},model:{value:e.validateForm.applicantTime,callback:function(t){e.$set(e.validateForm,"applicantTime",t)},expression:"validateForm.applicantTime"}})],1),a("el-form-item",{attrs:{label:"收货人",prop:"consigneeId"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",placeholder:"请选择发货人"},on:{change:e.onChangeConsignee},model:{value:e.validateForm.consigneeId,callback:function(t){e.$set(e.validateForm,"consigneeId",t)},expression:"validateForm.consigneeId"}},e._l(e.consigneeOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.userName,value:e.userId}})})),1)],1),a("el-form-item",{attrs:{label:"收货人电话",prop:"consigneePhone"}},[a("el-input",{attrs:{type:"text",autocomplete:"off",placeholder:"请输入收货人电话"},model:{value:e.validateForm.consigneePhone,callback:function(t){e.$set(e.validateForm,"consigneePhone",t)},expression:"validateForm.consigneePhone"}})],1),a("el-form-item",{attrs:{label:"发货件数",prop:"consignmentCount"}},[a("el-input",{attrs:{type:"text",autocomplete:"off",placeholder:"请输入发货件数"},model:{value:e.validateForm.consignmentCount,callback:function(t){e.$set(e.validateForm,"consignmentCount",t)},expression:"validateForm.consignmentCount"}})],1),a("el-form-item",{attrs:{label:"发货人",prop:"consignorId"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",placeholder:"请选择发货人"},on:{change:e.onChangeConsignor},model:{value:e.validateForm.consignorId,callback:function(t){e.$set(e.validateForm,"consignorId",t)},expression:"validateForm.consignorId"}},e._l(e.consignorOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.userName,value:e.userId}})})),1)],1),a("el-form-item",{attrs:{label:"发货工厂",prop:"factoryId"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",placeholder:"请选择发货工厂"},on:{change:e.onChangeFactory},model:{value:e.validateForm.factoryId,callback:function(t){e.$set(e.validateForm,"factoryId",t)},expression:"validateForm.factoryId"}},e._l(e.factoryOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.spotName,value:e.spotId}})})),1)],1),a("el-form-item",{attrs:{label:"收货地址",prop:"receiningAddress"}},[a("el-input",{attrs:{type:"text",autocomplete:"off",placeholder:"请输入收货地址"},model:{value:e.validateForm.receiningAddress,callback:function(t){e.$set(e.validateForm,"receiningAddress",t)},expression:"validateForm.receiningAddress"}})],1),a("el-form-item",{attrs:{label:"收货数量",prop:"receivingCount"}},[a("el-input",{attrs:{type:"text",autocomplete:"off",placeholder:"请输入收货数量"},model:{value:e.validateForm.receivingCount,callback:function(t){e.$set(e.validateForm,"receivingCount",t)},expression:"validateForm.receivingCount"}})],1),a("el-form-item",{attrs:{label:"收货景区",prop:"receivingId"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",placeholder:"请选择收货景区"},on:{change:e.onChangeReceiving},model:{value:e.validateForm.receivingId,callback:function(t){e.$set(e.validateForm,"receivingId",t)},expression:"validateForm.receivingId"}},e._l(e.receivingOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.spotName,value:e.spotId}})})),1)],1),a("el-form-item",{attrs:{label:"机器人数量",prop:"robotCount"}},[a("el-input",{attrs:{type:"text",autocomplete:"off",placeholder:"请输入机器人数量"},model:{value:e.validateForm.robotCount,callback:function(t){e.$set(e.validateForm,"robotCount",t)},expression:"validateForm.robotCount"}})],1)],1),a("div",{staticClass:"submit"},[a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitForm("validateForm")}}},[e._v("提交")]),a("el-button",{on:{click:e.dialogClose}},[e._v("取消")])],1)],1),a("el-dialog",{staticClass:"dialog",attrs:{title:"编辑景区调运信息",visible:e.editDialogVisible,"before-close":e.editHandleClose,"close-on-click-modal":!1},on:{"update:visible":function(t){e.editDialogVisible=t}}},[a("el-form",{ref:"editValidateForm",staticClass:"demo-ruleForm",attrs:{model:e.editValidateForm,"label-width":"100px",rules:e.editValidateFormRules}},[a("el-form-item",{attrs:{label:"调运进度",prop:"type"}},[a("el-select",{staticStyle:{width:"100%"},attrs:{filterable:"",placeholder:"请选择调运进度"},model:{value:e.editValidateForm.type,callback:function(t){e.$set(e.editValidateForm,"type",t)},expression:"editValidateForm.type"}},e._l(e.editTypeOptions,(function(e,t){return a("el-option",{key:t,attrs:{label:e.label,value:e.value}})})),1)],1)],1),a("div",{staticClass:"submit"},[a("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitForm("editValidateForm")}}},[e._v("提交")]),a("el-button",{on:{click:e.editDialogClose}},[e._v("取消")])],1)],1),a("el-dialog",{staticClass:"dialog",attrs:{title:"",visible:e.detailDialogVisible,"before-close":e.detailHandleClose,"close-on-click-modal":!1},on:{"update:visible":function(t){e.detailDialogVisible=t}}},[a("el-descriptions",{attrs:{title:"景区调运详情信息",border:""}},e._l(e.detailTableList,(function(t,r){return a("el-descriptions-item",{key:r,attrs:{label:r}},["发货图片"==r||"收货图片"==r?[a("el-image",{staticStyle:{width:"100px"},attrs:{src:t,"preview-src-list":[t]}})]:[e._v(" "+e._s(t)+" ")]],2)})),1)],1)],1)],1)},c=[],u=a("2909"),d=a("5530"),p=a("1da1"),m=(a("96cf"),a("a9e3"),a("d3b7"),a("ddb0"),a("159b"),a("99af"),a("5cc6"),a("9a8c"),a("a975"),a("735e"),a("c1ac"),a("d139"),a("3a7b"),a("d5d6"),a("82f8"),a("e91f"),a("60bd"),a("5f96"),a("3280"),a("3fcc"),a("ca91"),a("25a1"),a("cd26"),a("3c5d"),a("2954"),a("649e"),a("219c"),a("170b"),a("b39a"),a("72f7"),a("d81d"),a("21a6")),f=a.n(m),h=a("1146"),g=a.n(h);a("25f0"),a("4d90");var b={name:"SettlementFlow",data:function(){function e(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(t,a,r){a||r(),isNaN(Number(a))?r(new Error(e+"必须为数字值")):r()}}function t(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(t,a,r){if(!a)return r(new Error(e+"不能为空"));isNaN(Number(a))?r(new Error(e+"必须为数字值")):r()}}function a(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(t,a,r){if(!a)return r(new Error(e+"不能为空"));r()}}function r(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(t,a,r){if(!a)return r(new Error(e+"不能为空"));var n=/^(?:(?:\+|00)86)?1[3-9]\d{9}$/;if(!n.test(a))return r(new Error(e+"填写有误"));r()}}return{showTable:!0,feeInput:"",taxInput:"",tableFields:{"机器人数量":"robotCount","发货进度":"type","申请人":"applicantName","发货工厂":"factoryName","发货人":"consignorName","收货景区":"receivingName","收货人":"consigneeName","收货人电话":"consigneePhone","收货地址":"receiningAddress","发货时间":"consignmentDate"},tableData:[],tableLoading:!1,searchCompanyOptions:[],searchScenicOptions:[],receivingOptions:[],dateStateOptions:[{value:"1",label:"按月查询"},{value:"2",label:"按日查询"},{value:"3",label:"按年查询"}],typeOptions:[{value:1,label:"待发货"},{value:2,label:"已发货"},{value:3,label:"已收货"},{value:4,label:"已关闭"}],editTypeOptions:[{value:1,label:"待发货"},{value:4,label:"已关闭"}],loadingTypeOptions:[{value:1,label:"人工装车"},{value:2,label:"叉车装车"}],pickerOptions:{disabledDate:function(e){return e.getTime()>Date.now()},shortcuts:[{text:"今天",onClick:function(e){e.$emit("pick",new Date)}},{text:"昨天",onClick:function(e){var t=new Date;t.setTime(t.getTime()-864e5),e.$emit("pick",t)}},{text:"一周前",onClick:function(e){var t=new Date;t.setTime(t.getTime()-6048e5),e.$emit("pick",t)}}]},applicanteOptions:[],factoryOptions:[],consignorOptions:[],consigneeOptions:[],consignmentDateOptions:[],exportTableData:[],downloadLoading:!1,currentPage:1,pageSize:10,dialogVisible:!1,editDialogVisible:!1,detailDialogVisible:!1,detailTableList:[],dialogState:"",selectDateState:"1",dateType:"daterange",valueFormat:"yyyy-MM-dd",searchValidateForm:{spotId:"",startEndDate:"",type:""},searchVF:{spotId:"",startEndDate:"",type:""},validateForm:{},editValidateForm:{},searchPartnerCompanyName:"",searchScenicName:"",searchSelectDateState:"1",searchStartEndDate:"",searchPayState:"",companys:[],companyState:"",scenics:[],scenicState:"",downloadUrl:"",total:0,currentEditRowId:null,uploadDialogImageUrl:"",uploadDialogVisible:!1,uploadDisabled:!1,limitUpload:3,file:null,fileTemp:null,fileListUpload:[],updateUpload:!0,searchValidateFormRules:{},validateFormRules:{robotCount:[{validator:e("机器人数量"),trigger:["blur","change"]}],applicantId:[{validator:a("申请人"),required:!0,trigger:["blur","change"]}],factoryId:[{validator:a("发货工厂"),required:!0,trigger:["blur","change"]}],consignorId:[{validator:a("发货人"),required:!0,trigger:["blur","change"]}],receivingId:[{validator:a("收货景区"),required:!0,trigger:["blur","change"]}],consigneeId:[{validator:a("收货人"),required:!0,trigger:["blur","change"]}],consigneePhone:[{validator:r("收货人电话"),required:!0,trigger:["blur","change"]}],receiningAddress:[{validator:a("收货地址"),required:!0,trigger:["blur","change"]}],receivingCount:[{validator:t("收货数量"),required:!0,trigger:["blur","change"]}],consignmentCount:[{validator:t("发货件数"),required:!0,trigger:["blur","change"]}],loadingCost:[{validator:e("运费"),trigger:["blur","change"]}]},editValidateFormRules:{}}},created:function(){this.getValidateForm(),this.getScenicList(),this.initTableData(),this.getFactoryList(),this.getUserList()},beforeMount:function(){},mounted:function(){},methods:{getValidateForm:function(){var e={};for(var t in this.tableFields)e[this.tableFields[t]]="";this.validateForm=e},login:function(){var e=this;return Object(p["a"])(regeneratorRuntime.mark((function t(){var a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/loginSystem?username=lijiazhao&password=lijiazhao");case 2:a=t.sent,a.data;case 4:case"end":return t.stop()}}),t)})))()},getCompanyList:function(e){var t=this;return Object(p["a"])(regeneratorRuntime.mark((function a(){var r,n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return e||(e=null),a.next=3,t.$http.get("/system/subscriptionInformation/companyList",{params:{spotId:e}});case 3:if(r=a.sent,n=r.data,"200"!=n.state){a.next=10;break}return t.searchCompanyOptions=n.data,a.abrupt("return",n);case 10:return a.abrupt("return");case 11:case"end":return a.stop()}}),a)})))()},getScenicList:function(e){var t=this;return Object(p["a"])(regeneratorRuntime.mark((function a(){var r,n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return e||(e=null),a.next=3,t.$http.get("/system/factory_send/spot_list",{params:{companyId:e}});case 3:return r=a.sent,n=r.data,t.searchScenicOptions=n,t.receivingOptions=n,a.abrupt("return",n);case 8:case"end":return a.stop()}}),a)})))()},initTableData:function(){var e=this;return Object(p["a"])(regeneratorRuntime.mark((function t(){var a,r,n,i,o,s,l;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return a=e.searchVF.spotId,""!=a&&a||(a=null),r=e.searchVF.startEndDate,""!=r&&r?(n=e.searchVF.startEndDate[0],i=e.searchVF.startEndDate[1]):(n=null,i=null),o=e.searchVF.type,""!=o&&o||(o=null),e.tableLoading=!0,t.next=9,e.$http.get("/system/factory_send/dispatchList",{params:{spotId:a,beginDate:n,endDate:i,type:o,pageNum:(e.currentPage-1)*e.pageSize,pageSize:e.pageSize}});case 9:s=t.sent,l=s.data,e.tableData=l.data,e.total=l.total,e.tableLoading=!1;case 14:case"end":return t.stop()}}),t)})))()},addTableData:function(){var e=this;return Object(p["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/system/factory_send/add",Object(d["a"])(Object(d["a"])({},e.validateForm),{},{type:1,form:2}));case 2:a=t.sent,r=a.data,"200"==r.state?(e.$message({message:r.msg,type:"success",offset:100,center:!0,duration:1e3}),e.initTableData(),e.$nextTick((function(){e.$refs.validateForm.resetFields()}))):e.$message({message:r.msg,type:"error",offset:100,center:!0,duration:3e3});case 5:case"end":return t.stop()}}),t)})))()},editTableData:function(){var e=this;return Object(p["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/system/factory_send/edit",Object(d["a"])(Object(d["a"])({},e.editValidateForm),{},{form:2,id:e.currentEditRowId}));case 2:a=t.sent,r=a.data,"200"==r.state?(e.$message({message:r.msg,type:"success",offset:100,center:!0,duration:1e3}),e.initTableData()):e.$message({message:r.msg,type:"error",offset:100,center:!0,duration:3e3});case 5:case"end":return t.stop()}}),t)})))()},detailTableDate:function(e){var t=this;return Object(p["a"])(regeneratorRuntime.mark((function a(){var r,n,i,o,s,l;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return o=function(e){return function(t){"装车方式"==t?1==n.data[e]?i[t]="人工装车":2==n.data[e]?i[t]="叉车装车":i[t]="":"发货进度"==t?1==n.data[e]?i[t]="待发货":2==n.data[e]?i[t]="已发货":3==n.data[e]?i[t]="已收货":4==n.data[e]?i[t]="已关闭":i[t]="":i[t]=n.data[e]}},a.next=3,t.$http.get("/system/factory_send/detail",{params:{id:e}});case 3:r=a.sent,n=r.data,i={},a.t0=regeneratorRuntime.keys(n.data);case 7:if((a.t1=a.t0()).done){a.next=68;break}s=a.t1.value,l=o(s),a.t2=s,a.next="applicantName"===a.t2?13:"applicantTime"===a.t2?15:"carType"===a.t2?17:"comsignmentNotes"===a.t2?19:"consigneeName"===a.t2?21:"consigneePhone"===a.t2?23:"consignmentCount"===a.t2?25:"consignmentDate"===a.t2?27:"consignmentPicture"===a.t2?29:"consignorName"===a.t2?31:"driverName"===a.t2?33:"driverPhone"===a.t2?35:"factoryName"===a.t2?37:"license"===a.t2?39:"loadingCost"===a.t2?41:"loadingType"===a.t2?43:"receiningAddress"===a.t2?45:"receivingCount"===a.t2?47:"receivingDate"===a.t2?49:"receivingName"===a.t2?51:"receivingNotes"===a.t2?53:"receivingPicture"===a.t2?55:"receivingRobotCount"===a.t2?57:"robotCodes"===a.t2?59:"robotCount"===a.t2?61:"type"===a.t2?63:65;break;case 13:return l("申请人"),a.abrupt("break",66);case 15:return l("申请时间"),a.abrupt("break",66);case 17:return l("货车类型"),a.abrupt("break",66);case 19:return l("发货备注"),a.abrupt("break",66);case 21:return l("收货人"),a.abrupt("break",66);case 23:return l("收货人电话"),a.abrupt("break",66);case 25:return l("发货件数"),a.abrupt("break",66);case 27:return l("发货时间"),a.abrupt("break",66);case 29:return l("发货图片"),a.abrupt("break",66);case 31:return l("发货人"),a.abrupt("break",66);case 33:return l("驾驶员姓名"),a.abrupt("break",66);case 35:return l("驾驶员电话"),a.abrupt("break",66);case 37:return l("发货工厂"),a.abrupt("break",66);case 39:return l("车牌号"),a.abrupt("break",66);case 41:return l("运费"),a.abrupt("break",66);case 43:return l("装车方式"),a.abrupt("break",66);case 45:return l("收货地址"),a.abrupt("break",66);case 47:return l("收货数量"),a.abrupt("break",66);case 49:return l("收货时间"),a.abrupt("break",66);case 51:return l("收货景区"),a.abrupt("break",66);case 53:return l("收货备注"),a.abrupt("break",66);case 55:return l("收货图片"),a.abrupt("break",66);case 57:return l("收货机器人数量"),a.abrupt("break",66);case 59:return l("设备编码"),a.abrupt("break",66);case 61:return l("机器人数量"),a.abrupt("break",66);case 63:return l("发货进度"),a.abrupt("break",66);case 65:return a.abrupt("break",66);case 66:a.next=7;break;case 68:t.detailTableList=i;case 69:case"end":return a.stop()}}),a)})))()},getFactoryList:function(){var e=this;return Object(p["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/system/factory_send/factory_list");case 2:a=t.sent,r=a.data,e.factoryOptions=r;case 5:case"end":return t.stop()}}),t)})))()},getUserList:function(){var e=this;return Object(p["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/system/factory_send/user_list");case 2:a=t.sent,r=a.data,e.applicanteOptions=r,e.consignorOptions=r,e.consigneeOptions=r;case 7:case"end":return t.stop()}}),t)})))()},getExportTableData:function(){var e=this;return Object(p["a"])(regeneratorRuntime.mark((function t(){var a,r,n,i,o,s,l;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return a=e.searchVF.spotId,""!=a&&a||(a=null),r=e.searchVF.startEndDate,""!=r&&r?(n=e.searchVF.startEndDate[0],i=e.searchVF.startEndDate[1]):(n=null,i=null),o=e.searchVF.type,""!=o&&o||(o=null),t.next=8,e.$http.get("/system/factory_send/sendList",{params:{spotId:a,beginDate:n,endDate:i,type:o}});case 8:if(s=t.sent,l=s.data,"200"!=l.state){t.next=14;break}e.exportTableData=l.data,t.next=15;break;case 14:return t.abrupt("return");case 15:case"end":return t.stop()}}),t)})))()},getTableData:function(){this.searchValidateForm.spotId==this.searchVF.spotId&&this.selectDateState==this.searchSelectDateState&&this.searchValidateForm.startEndDate==this.searchVF.startEndDate&&this.searchValidateForm.type==this.searchVF.type||(this.searchVF.spotId=this.searchValidateForm.spotId,this.searchSelectDateState=this.selectDateState,this.searchVF.startEndDate=this.searchValidateForm.startEndDate,this.searchVF.type=this.searchValidateForm.type,console.log("调用接口"),this.initTableData())},onChangeApplicant:function(e){var t=this;return Object(p["a"])(regeneratorRuntime.mark((function a(){var r;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",t.applicanteOptions.forEach((function(t){t.userId==e&&(r=t.userName)})),t.validateForm.applicantName=r;case 3:case"end":return a.stop()}}),a)})))()},onChangeFactory:function(e){var t=this;return Object(p["a"])(regeneratorRuntime.mark((function a(){var r;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",t.factoryOptions.forEach((function(t){t.spotId==e&&(r=t.spotName)})),t.validateForm.factoryName=r;case 3:case"end":return a.stop()}}),a)})))()},onChangeConsignor:function(e){var t=this;return Object(p["a"])(regeneratorRuntime.mark((function a(){var r;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",t.consignorOptions.forEach((function(t){t.userId==e&&(r=t.userName)})),t.validateForm.consignorName=r;case 3:case"end":return a.stop()}}),a)})))()},onChangeReceiving:function(e){var t=this;return Object(p["a"])(regeneratorRuntime.mark((function a(){var r,n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",n="",t.receivingOptions.forEach((function(t){t.spotId==e&&(r=t.spotName,n=t.address)})),t.validateForm.receivingName=r,t.validateForm.receiningAddress=n;case 5:case"end":return a.stop()}}),a)})))()},onChangeConsignee:function(e){var t=this;return Object(p["a"])(regeneratorRuntime.mark((function a(){var r;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",t.consigneeOptions.forEach((function(t){t.userId==e&&(r=t.userName)})),t.validateForm.consigneeName=r;case 3:case"end":return a.stop()}}),a)})))()},onDateChange:function(){console.log(this.selectDateState),"1"==this.selectDateState?(this.dateType="monthrange",this.valueFormat="yyyy-MM"):"2"==this.selectDateState?(this.dateType="daterange",this.valueFormat="yyyy-MM-dd"):"3"==this.selectDateState&&(this.dateType="yearrange",this.valueFormat="yyyy")},onReset:function(){this.searchValidateForm.spotId="",this.searchValidateForm.startEndDate="",this.searchValidateForm.type="",this.selectDateState="1",this.dateType="monthrange",this.getTableData()},onSearch:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;t.showTable=!0,t.getTableData()}))},exportExcel:function(){var e=this;this.$confirm("确认导出Excel表格?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(Object(p["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.downloadLoading=!0,t.next=3,e.getExportTableData();case 3:e.$nextTick((function(){var t={raw:!0},a=g.a.utils.table_to_book(document.querySelector("#outTable"),t),r=g.a.write(a,{bookType:"xlsx",bookSST:!0,type:"array"});try{f.a.saveAs(new Blob([r],{type:"application/octet-stream"}),"景区设备调运.xlsx")}catch(n){"undefined"!==typeof console&&console.log(n,r)}return e.downloadLoading=!1,r})),e.$message({type:"success",message:"已成功导出Excel表格！",offset:100,center:!0,duration:1e3});case 5:case"end":return t.stop()}}),t)})))).catch((function(){e.$message({type:"info",message:"已取消导出Excel表格",offset:100,center:!0,duration:1e3})}))},onAdd:function(){this.dialogVisible=!0,this.dialogState="add"},editRow:function(e,t){if(console.log(e,t),1==t.type){this.currentEditRowId=t.id,this.editDialogVisible=!0,this.dialogState="edit";var a="";a={type:t.type},this.editValidateForm=a}else this.$message({message:"该行信息不能被编辑",type:"warning",offset:100,center:!0,duration:3e3})},detailRow:function(e,t){this.detailDialogVisible=!0,this.dialogState="detail",this.detailTableDate(t.id)},dialogClose:function(){this.dialogVisible=!1,this.$refs.validateForm.resetFields(),this.getValidateForm()},editDialogClose:function(){this.editDialogVisible=!1,this.$refs.editValidateForm.resetFields(),this.editValidateForm={}},detailDialogClose:function(){this.detailDialogVisible=!1},handleClose:function(e){e(),this.$refs.validateForm.resetFields(),this.getValidateForm()},editHandleClose:function(e){e(),this.$refs.editValidateForm.resetFields(),this.editValidateForm={}},detailHandleClose:function(e){e()},submitForm:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;"add"==t.dialogState?(console.log("新增公司信息"),t.addTableData(),t.dialogVisible=!1):"edit"==t.dialogState?(console.log("编辑公司信息"),t.editTableData(),t.editDialogVisible=!1):"detail"==t.dialogState&&console.log("工厂发货详情信息")}))},onCurrentChange:function(e){this.currentPage=e,this.initTableData()},onSizeChange:function(e){this.pageSize=e,this.initTableData()},reloadUpload:function(){var e=this;this.updateUpload=!1,this.$nextTick((function(){e.updateUpload=!0}))},handleChange:function(e,t){this.fileListUpload[0]=e,this.fileTemp=e.raw,this.reloadUpload(),this.fileTemp?"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"==this.fileTemp.type||"application/vnd.ms-excel"==this.fileTemp.type?this.importfxx(this.fileTemp):this.$message({type:"warning",message:"附件格式错误，请删除后重新上传！",offset:100,center:!0,duration:3e3}):this.$message({type:"warning",message:"请上传附件！",offset:100,center:!0,duration:3e3})},handleExceed:function(e,t){this.$message.warning("当前限制选择 1 个文件，本次选择了 ".concat(e.length," 个文件，共选择了 ").concat(e.length+t.length," 个文件"))},handleRemove:function(e,t){this.fileTemp=null,this.fileListUpload=[]},importfxx:function(e){this.file=e;var t=!1,r=this.file,n=new FileReader;FileReader.prototype.readAsBinaryString=function(e){var t,r,n="",i=!1,o=new FileReader;o.onload=function(e){for(var s=new Uint8Array(o.result),l=s.byteLength,c=0;c<l;c++)n+=String.fromCharCode(s[c]);var d=a("1146");t=i?d.read(btoa(fixdata(n)),{type:"base64"}):d.read(n,{type:"binary"}),r=d.utils.sheet_to_json(t.Sheets[t.SheetNames[0]]),console.log(r),this.da=Object(u["a"])(r);var p=[];return this.da.map((function(e){var t={robotCount:e["机器人数量"],type:e["发货进度"],applicantName:e["申请人"],factoryName:e["发货工厂"],consignorName:e["发货人"],receivingName:e["收货景区"],consigneeName:e["收货人"],consigneePhone:e["收货人电话"],receiningAddress:e["收货地址"],consignmentDate:e["发货时间"]};p.push(t)})),p},o.readAsArrayBuffer(e)},t?n.readAsArrayBuffer(r):n.readAsBinaryString(r)}},components:{},computed:{tableContentValue:function(){return function(e,t){var a="";return"type"==t?1==e[t]?a="待发货":2==e[t]?a="已发货":3==e[t]?a="已收货":4==e[t]&&(a="已关闭"):a=e[t],a}}}},v=b,y=(a("8ad6"),a("2877")),F=Object(y["a"])(v,l,c,!1,null,"3c4a2422",null),w=F.exports,k={name:"App",components:{SpotDispatch:w}},x=k,D=(a("5c0b"),Object(y["a"])(x,o,s,!1,null,null,null)),C=D.exports,V=a("2f62");i["default"].use(V["a"]);var S=new V["a"].Store({state:{},mutations:{},actions:{},modules:{}}),O=a("bc3a"),T=a.n(O),I=a("4328"),$=a.n(I),R=a("313e");i["default"].use(n.a),T.a.defaults.withCredentials=!0,i["default"].prototype.$http=T.a,i["default"].prototype.$qs=$.a,i["default"].config.productionTip=!1,i["default"].prototype.$echarts=R,new i["default"]({store:S,render:function(e){return e(C)}}).$mount("#app")},"5c0b":function(e,t,a){"use strict";a("9c0c")},"8ad6":function(e,t,a){"use strict";a("b56e")},"9c0c":function(e,t,a){},b56e:function(e,t,a){}});
//# sourceMappingURL=app.9f4beed4.js.map