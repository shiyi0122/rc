(function(e){function t(t){for(var r,l,n=t[0],i=t[1],c=t[2],h=0,u=[];h<n.length;h++)l=n[h],Object.prototype.hasOwnProperty.call(s,l)&&s[l]&&u.push(s[l][0]),s[l]=0;for(r in i)Object.prototype.hasOwnProperty.call(i,r)&&(e[r]=i[r]);d&&d(t);while(u.length)u.shift()();return o.push.apply(o,c||[]),a()}function a(){for(var e,t=0;t<o.length;t++){for(var a=o[t],r=!0,n=1;n<a.length;n++){var i=a[n];0!==s[i]&&(r=!1)}r&&(o.splice(t--,1),e=l(l.s=a[0]))}return e}var r={},s={app:0},o=[];function l(t){if(r[t])return r[t].exports;var a=r[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,l),a.l=!0,a.exports}l.m=e,l.c=r,l.d=function(e,t,a){l.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},l.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.t=function(e,t){if(1&t&&(e=l(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(l.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)l.d(a,r,function(t){return e[t]}.bind(null,r));return a},l.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return l.d(t,"a",t),t},l.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},l.p="";var n=window["webpackJsonp"]=window["webpackJsonp"]||[],i=n.push.bind(n);n.push=t,n=n.slice();for(var c=0;c<n.length;c++)t(n[c]);var d=i;o.push([1,"chunk-vendors"]),a()})({0:function(e,t){},"0c64":function(e,t,a){"use strict";a("433a")},1:function(e,t,a){e.exports=a("56d7")},2:function(e,t){},3:function(e,t){},"433a":function(e,t,a){},"56d7":function(e,t,a){"use strict";a.r(t);var r=a("2b0e"),s=function(){var e=this,t=e._self._c;return t("div",{attrs:{id:"app"}},[t("broadcast-hunt-details-list")],1)},o=[],l=function(){var e=this,t=e._self._c;return t("div",{staticClass:"partner-company"},[t("el-container",[t("el-header",{staticClass:"header"},[t("el-form",{ref:"searchValidateForm",staticClass:"search-validate-form",attrs:{inline:!0,model:e.searchValidateForm,"label-width":"100px",rules:e.searchValidateFormRules}},[t("el-form-item",{attrs:{label:"",prop:"companyId"}},[t("el-select",{attrs:{filterable:"",placeholder:"请选择公司",clearable:""},on:{change:e.onChangeSearchCompany},model:{value:e.searchValidateForm.companyId,callback:function(t){e.$set(e.searchValidateForm,"companyId",t)},expression:"searchValidateForm.companyId"}},e._l(e.searchCompanyOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.COMPANY_NAME,value:e.COMPANY_ID}})})),1)],1),t("el-form-item",{attrs:{label:"",prop:"spotId"}},[t("el-select",{attrs:{filterable:"",placeholder:"请选择景区",clearable:""},on:{change:e.onChangeSearchSpot},model:{value:e.searchValidateForm.spotId,callback:function(t){e.$set(e.searchValidateForm,"spotId",t)},expression:"searchValidateForm.spotId"}},e._l(e.searchScenicOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.SCENIC_SPOT_NAME,value:e.SCENIC_SPOT_ID}})})),1)],1),t("el-form-item",{attrs:{label:"",prop:"huntSwitch"}},[t("el-select",{attrs:{filterable:"",placeholder:"请选择寻宝活动状态",clearable:""},on:{change:e.onChangeSearchhuntSwitch},model:{value:e.searchValidateForm.huntSwitch,callback:function(t){e.$set(e.searchValidateForm,"huntSwitch",t)},expression:"searchValidateForm.huntSwitch"}},e._l(e.huntSwitchOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.label,value:e.value}})})),1)],1),t("el-form-item",{attrs:{label:"",prop:"selectDateState"}},[t("el-select",{attrs:{filterable:"",placeholder:"请选择日期统计方式"},on:{change:e.onDateChange},model:{value:e.searchValidateForm.selectDateState,callback:function(t){e.$set(e.searchValidateForm,"selectDateState",t)},expression:"searchValidateForm.selectDateState"}},e._l(e.dateStateOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.label,value:e.value}})})),1)],1),t("el-form-item",{staticStyle:{width:"350px"},attrs:{label:"",prop:"startEndDate"}},[t("el-date-picker",{attrs:{type:e.dateType,"range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期","append-to-body":!1},model:{value:e.searchValidateForm.startEndDate,callback:function(t){e.$set(e.searchValidateForm,"startEndDate",t)},expression:"searchValidateForm.startEndDate"}})],1)],1),t("div",[t("el-button",{attrs:{type:"primary",icon:"el-icon-refresh-right"},on:{click:e.onReset}},[e._v("重置")]),t("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.onSearch("searchValidateForm")}}},[e._v("查询数据")]),t("el-button",{attrs:{type:"primary",loading:e.downloadLoading,icon:"el-icon-top-right"},on:{click:e.exportExcel}},[e._v("导出数据")])],1)],1),t("div",{directives:[{name:"show",rawName:"v-show",value:e.showTable,expression:"showTable"}]},[t("el-main",[t("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],staticClass:"table",staticStyle:{width:"100%"},attrs:{data:e.tableData,border:"","default-sort":{prop:"totalTime",order:"descending"},"header-cell-style":{background:"#f2f2f2",color:"#606266",height:"38px",padding:"0px"},"cell-style":{height:"38px",padding:"0"}},on:{"sort-change":e.onSortChange}},[e._l(e.tableFields,(function(a,r){return[t("el-table-column",{key:r,attrs:{prop:a,label:r,"min-width":"130",fixed:e.activeFixed(a)},scopedSlots:e._u([{key:"default",fn:function(r){return[t("div",[e._v(" "+e._s(e.tableContentValue(r.row,a))+" ")])]}}],null,!0)})]}))],2),t("el-table",{staticClass:"table",staticStyle:{width:"100%",display:"none"},attrs:{id:"outTable",data:e.exportTableData,border:""}},e._l(e.tableFields,(function(a,r){return t("el-table-column",{key:r,attrs:{prop:a,label:r,"min-width":"130",align:"center"},scopedSlots:e._u([{key:"default",fn:function(r){return[t("div",[e._v(" "+e._s(e.tableContentValue(r.row,a))+" ")])]}}],null,!0)})})),1)],1),t("el-footer",[t("div",{staticClass:"block"},[t("el-pagination",{attrs:{background:"",layout:"prev, pager, next, total, sizes, jumper",total:e.total,"current-page":e.currentPage,"page-sizes":[10,20,30,40,50,60,70,80,90],"page-size":e.pageSize},on:{"current-change":e.onCurrentChange,"size-change":e.onSizeChange}})],1)])],1),t("div",{directives:[{name:"show",rawName:"v-show",value:!e.showTable,expression:"!showTable"}],staticClass:"my-echarts"},[t("div",{attrs:{id:"myChart"}})]),t("div")],1)],1)},n=[];a("d9e2"),a("14d9"),a("88a7"),a("271a"),a("5494"),a("21a6"),a("1146");function i(e,t="1"){var a=e.getFullYear().toString(),r=(e.getMonth()+1).toString().padStart(2,"0"),s=e.getDate().toString().padStart(2,"0"),o="";return"0"==t?o=a+"-"+r+"-"+s:"1"==t?o=a+"-"+r:"2"==t&&(o=a),o}function c(){var e=(new Date).getTime(),t=new Date(e);return t}var d={name:"ScenicProfitStatistic",data(){function e(e=""){return(t,a,r)=>{a||r(),isNaN(a)||a<0?r(new Error(e+"必须为正整数")):r()}}return{showTable:!0,feeInput:"",taxInput:"",searchFeeInput:"",searchTaxInput:"",tableFields:{"景区名称":"scenicSpotName","总订单数":"allOrder","寻宝订单数":"huntOrder","抽奖订单数":"lotteryOrder","寻宝订单占比":"huntProportion","抽奖订单占比":"lotteryProportion","客单价":"perCustomerTransaction","寻宝客单价":"huntPerCustomerTransaction","抽奖客单价":"lotteryPerCustomerTransaction","未抽奖客单价":"noLotteryPerCustomerTransaction","抽奖订单的效益评估":"lotteryOrderBenefitEvaluation","客单价提升":"perCustomerTransactionIncrease"},tableData:[],searchTableFields:{"景区名称":"scenicSpotName","总订单数":"allOrder","寻宝订单数":"huntOrder","抽奖订单数":"lotteryOrder","寻宝订单占比":"huntProportion","抽奖订单占比":"lotteryProportion","客单价":"perCustomerTransaction","寻宝客单价":"huntPerCustomerTransaction","抽奖客单价":"lotteryPerCustomerTransaction","未抽奖客单价":"noLotteryPerCustomerTransaction","抽奖订单的效益评估":"lotteryOrderBenefitEvaluation","客单价提升":"perCustomerTransactionIncrease"},tableLoading:!1,searchCompanyOptions:[],searchScenicOptions:[],huntSwitchOptions:[{value:"0",label:"未开启寻宝活动"},{value:"1",label:"开启寻宝活动"}],dateStateOptions:[{value:"0",label:"按日查询"},{value:"1",label:"按月查询"},{value:"2",label:"按年查询"}],typeOptions:[{value:"1",label:"按景区统计"},{value:"2",label:"按机器人统计"}],statusOptions:[{value:"1",label:"已运营景区"},{value:"2",label:"测试景区"},{value:"3",label:"预运营景区"}],timeTypeOptions:[{value:"1",label:"运营时长"},{value:"2",label:"平均单台运营时长"}],exportTableData:[],downloadLoading:!1,currentPage:1,pageSize:10,dialogVisible:!1,dialogState:"",dateType:"daterange",valueFormat:"yyyy-MM-dd",searchValidateForm:{companyId:"",spotId:"",huntSwitch:"",selectDateState:"0",startEndDate:[c(),c()]},searchVF:{companyId:"",spotId:"",huntSwitch:"",selectDateState:"0",startEndDate:[c(),c()]},searchVFEcharts:{companyId:"",spotId:"",huntSwitch:"",selectDateState:"0",startEndDate:[c(),c()]},validateForm:{},companys:[],companyState:"",scenics:[],scenicState:"",timeChartData:[],total:0,typeTotalTime:0,order:"totalTime desc",echartsOrder:null,myChart:null,option:{},disabledTimeType:!1,disabledRobotCode:!0,searchValidateFormRules:{totalTime:[{validator:e("运营时长"),required:!0,trigger:["blur","change"]}]},validateFormRules:{}}},created(){this.getValidateForm(),this.initTableData(),this.getCompanyList(),this.getScenicList()},mounted(){},methods:{getValidateForm(){var e={};for(var t in this.tableFields)e[this.tableFields[t]]="";this.validateForm=e,console.log(this.validateForm)},async login(){const{data:e}=await this.$http.post("/loginSystem?username=houjingchen&password=houjingchen")},async initTableData(e=!1){var t=this.searchVF.companyId;""!=t&&t||(t="");var a=this.searchVF.spotId;""!=a&&a||(a="");var r=this.searchVF.huntSwitch;""!=r&&r||(r="");var s=this.searchVF.selectDateState;""!=s&&s||(s=null);var o,l,n=this.searchVF.startEndDate;""!=n&&n?(o=i(this.searchVF.startEndDate[0],this.searchVF.selectDateState),l=i(this.searchVF.startEndDate[1],this.searchVF.selectDateState)):(o=null,l=null),console.log(o,l),this.tableLoading=!0;const{data:c}=await this.$http({method:"post",url:"/system/broadcastHunt/getTreasureHuntDetail",data:{companyId:t,orderScenicSpotId:a,huntSwitch:r,dateType:s,orderStartTime:o,orderEndTime:l,pageNum:this.currentPage,pageSize:this.pageSize},headers:{"Content-Type":"application/json;charset=UTF-8"}});return"200"==c.state&&(this.tableData=c.data.list,this.total=c.data.total,this.tableLoading=!1),c},async getCompanyList(e){e||(e=null);const{data:t}=await this.$http.get("/system/robot_time_statistics/companyList",{params:{spotId:e}});return"200"==t.state?(this.searchCompanyOptions=t.data,t):void 0},async getScenicList(e){e||(e=null);const{data:t}=await this.$http.get("/system/robot_time_statistics/spotList",{params:{companyId:e}});return"200"==t.state?(this.searchScenicOptions=t.data,t):void 0},async getExportTableData(){var e=this.searchValidateForm.companyId;""!=e&&e||(e="");var t=this.searchValidateForm.spotId;""!=t&&t||(t="");var a=this.searchValidateForm.huntSwitch;""!=a&&a||(a="");var r=this.searchValidateForm.selectDateState;""!=r&&r||(r="");var s,o,l=this.searchValidateForm.startEndDate;""!=l&&l?(s=i(this.searchValidateForm.startEndDate[0],this.searchValidateForm.selectDateState),o=i(this.searchValidateForm.startEndDate[1],this.searchValidateForm.selectDateState)):(s="",o="");let n=new FormData;n.append("companyId",e),n.append("orderScenicSpotId",t),n.append("huntSwitch",a),n.append("dateType",r),n.append("orderStartTime",s),n.append("orderEndTime",o);const c=new URLSearchParams(n),{data:d}=await this.$http.get("/system/broadcastHunt/exportTreasureHuntDetail?"+c.toString(),{responseType:"arraybuffer"});if(d){const e=window.URL.createObjectURL(new Blob([d],{type:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})),t=document.createElement("a");t.href=e,t.download="寻宝详情列表",t.click(),this.$message({message:"导出成功",type:"success"})}else this.$message({message:"导入失败",type:"error"});"200"==d.state&&(this.exportTableData=d.data)},onDateChange(){var e=[];for(let a in this.searchTableFields){let t={};t[a]=this.searchTableFields[a],e.push(t)}"0"==this.searchValidateForm.selectDateState?this.dateType="daterange":"1"==this.searchValidateForm.selectDateState?this.dateType="monthrange":"2"==this.searchValidateForm.selectDateState&&(this.dateType="yearrange");var t={};e.forEach(e=>{t[Object.keys(e)[0]]=Object.values(e)[0]}),console.log(t),this.searchTableFields=t},onSortChange({column:e,prop:t,order:a}){console.log(e,t,a),"ascending"==a?a="asc":"descending"==a&&(a="desc"),this.order=a?`${t} ${a}`:null,this.initTableData()},async getTableData(){console.log("调用接口1"),i(this.searchValidateForm.startEndDate[0],this.searchValidateForm.selectDateState),i(this.searchValidateForm.startEndDate[1],this.searchValidateForm.selectDateState),console.log("调用接口2"),i(this.searchVF.startEndDate[0],this.searchVF.selectDateState),i(this.searchVF.startEndDate[1],this.searchVF.selectDateState),console.log("调用接口3"),console.log("调用接口4"),this.searchVF.companyId=this.searchValidateForm.companyId,this.searchVF.spotId=this.searchValidateForm.spotId,this.searchVF.huntSwitch=this.searchValidateForm.huntSwitch,this.searchVF.selectDateState=this.searchValidateForm.selectDateState,this.searchVF.startEndDate=this.searchValidateForm.startEndDate,console.log("调用接口5");var e=await this.initTableData();"200"==e.state&&(this.tableFields=this.searchTableFields)},async onChangeSearchCompany(e){},async onChangeSearchhuntSwitch(e){console.log(e)},async onChangeSearchSpot(e){},onReset(){this.searchValidateForm.companyId="",this.searchValidateForm.spotId="",this.searchValidateForm.huntSwitch="",this.searchValidateForm.startEndDate=[c(),c()],this.searchValidateForm.selectDateState="0",this.dateType="daterange",this.getTableData()},onSearch(e){this.$refs[e].validate(e=>{if(!e)return console.log("error submit!!"),!1;i(this.searchValidateForm.startEndDate[0],this.searchValidateForm.selectDateState),i(this.searchValidateForm.startEndDate[1],this.searchValidateForm.selectDateState),i(new Date,this.searchValidateForm.selectDateState);switch(this.searchValidateForm.selectDateState){case"0":"日";break;case"1":"月";break;case"2":"年";break;default:break}this.getTableData()})},exportExcel(){this.$confirm("确认导出Excel表格?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{this.downloadLoading=!0,this.$nextTick(async()=>{await this.getExportTableData(),this.downloadLoading=!1})}).catch(()=>{this.$message({type:"info",message:"已取消导出Excel表格"})})},onAdd(){this.dialogVisible=!0,this.dialogState="add"},editRow(e,t){console.log(e,t),this.dialogVisible=!0;var a=Object.assign({},t);this.validateForm=a,this.dialogState="edit"},dialogClose(){this.dialogVisible=!1,this.$refs.validateForm.resetFields(),this.getValidateForm()},handleClose(e){e(),this.$refs.validateForm.resetFields(),this.getValidateForm()},submitForm(e){this.$refs[e].validate(e=>{if(!e)return console.log("error submit!!"),!1;console.log(this.validateForm),"add"==this.dialogState?console.log("新增公司信息"):"edit"==this.dialogState&&console.log("编辑公司信息"),this.dialogVisible=!1,this.$refs.validateForm.resetFields()})},onCurrentChange(e){this.currentPage=e,this.initTableData(!0)},onSizeChange(e){this.pageSize=e,this.initTableData(!0)}},components:{},computed:{activeFixed(){return e=>{}},tableContentValue(){return(e,t)=>{var a="";return a="lotteryPerCustomerTransaction"==t?null==e[t]?0:e[t]:"huntPerCustomerTransaction"==t&&null==e[t]?0:e[t],a}}}},h=d,u=(a("b99f"),a("2877")),p=Object(u["a"])(h,l,n,!1,null,null,null),m=p.exports,b={name:"App",components:{broadcastHuntDetailsList:m}},f=b,g=(a("0c64"),Object(u["a"])(f,s,o,!1,null,null,null)),y=g.exports,v=a("2f62");r["default"].use(v["a"]);var S=new v["a"].Store({state:{},mutations:{},actions:{},modules:{}}),F=(a("9e1f"),a("450d"),a("6ed5")),V=a.n(F),D=(a("0fb7"),a("f529")),w=a.n(D),T=(a("be4f"),a("896a")),C=a.n(T),x=(a("826b"),a("c263")),O=a.n(x),E=(a("3db2"),a("58b8")),I=a.n(E),_=(a("eca7"),a("3787")),k=a.n(_),P=(a("425f"),a("4105")),$=a.n(P),L=(a("a7cc"),a("df33")),j=a.n(L),N=(a("672e"),a("101e")),z=a.n(N),M=(a("5466"),a("ecdf")),R=a.n(M),H=(a("38a0"),a("ad41")),A=a.n(H),B=(a("10cb"),a("f3ad")),U=a.n(B),Y=(a("bdc7"),a("aa2f")),q=a.n(Y),J=(a("de31"),a("c69e")),G=a.n(J),K=(a("a673"),a("7b31")),Q=a.n(K),W=(a("adec"),a("3d2d")),X=a.n(W),Z=(a("6611"),a("e772")),ee=a.n(Z),te=(a("1f1a"),a("4e4b")),ae=a.n(te),re=(a("1951"),a("eedf")),se=a.n(re);r["default"].use(se.a),r["default"].use(ae.a),r["default"].use(ee.a),r["default"].use(X.a),r["default"].use(Q.a),r["default"].use(G.a),r["default"].use(q.a),r["default"].use(U.a),r["default"].use(A.a),r["default"].use(R.a),r["default"].use(z.a),r["default"].use(j.a),r["default"].use($.a),r["default"].use(k.a),r["default"].use(I.a),r["default"].use(O.a),r["default"].use(C.a),r["default"].prototype.$message=w.a,r["default"].prototype.$confirm=V.a.confirm;var oe=a("521e"),le=a("bc3a"),ne=a.n(le),ie=a("313e");r["default"].use(oe["a"]),r["default"].directive("loadmore",{bind(e,t){const a=e.querySelector(".el-table__body-wrapper");a.addEventListener("scroll",(function(){let e=0;const a=this.scrollHeight-this.scrollTop-this.clientHeight;a<=e&&(console.log(t.value),t.value())}))}}),ne.a.defaults.withCredentials=!0,r["default"].prototype.$http=ne.a,r["default"].config.productionTip=!1,r["default"].prototype.$echarts=ie,new r["default"]({store:S,render:e=>e(y)}).$mount("#app")},b99f:function(e,t,a){"use strict";a("d108")},d108:function(e,t,a){}});
//# sourceMappingURL=app.fa2671ba.js.map