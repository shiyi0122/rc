(function(e){function t(t){for(var i,r,l=t[0],n=t[1],d=t[2],p=0,h=[];p<l.length;p++)r=l[p],Object.prototype.hasOwnProperty.call(s,r)&&s[r]&&h.push(s[r][0]),s[r]=0;for(i in n)Object.prototype.hasOwnProperty.call(n,i)&&(e[i]=n[i]);c&&c(t);while(h.length)h.shift()();return o.push.apply(o,d||[]),a()}function a(){for(var e,t=0;t<o.length;t++){for(var a=o[t],i=!0,l=1;l<a.length;l++){var n=a[l];0!==s[n]&&(i=!1)}i&&(o.splice(t--,1),e=r(r.s=a[0]))}return e}var i={},s={index:0},o=[];function r(t){if(i[t])return i[t].exports;var a=i[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,r),a.l=!0,a.exports}r.m=e,r.c=i,r.d=function(e,t,a){r.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},r.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},r.t=function(e,t){if(1&t&&(e=r(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(r.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var i in e)r.d(a,i,function(t){return e[t]}.bind(null,i));return a},r.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return r.d(t,"a",t),t},r.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},r.p="";var l=window["webpackJsonp"]=window["webpackJsonp"]||[],n=l.push.bind(l);l.push=t,l=l.slice();for(var d=0;d<l.length;d++)t(l[d]);var c=n;o.push([0,"chunk-vendors"]),a()})({0:function(e,t,a){e.exports=a("56d7")},"164e":function(e,t){e.exports=echarts},"17dc":function(e,t,a){},"56d7":function(e,t,a){"use strict";a.r(t);var i=a("8bbf"),s=a.n(i),o=function(){var e=this,t=e._self._c;return t("div",{attrs:{id:"app"}},[t("revenue-line")],1)},r=[],l=function(){var e=this,t=e._self._c;return t("div",{staticClass:"partner-company"},[t("el-container",[t("el-header",{staticClass:"header"},[t("el-form",{ref:"searchValidateForm",staticClass:"search-validate-form",attrs:{inline:!0,model:e.searchValidateForm,"label-width":"100px",rules:e.searchValidateFormRules}},[0==e.searchValidateForm.mode?[t("el-form-item",{attrs:{label:"",prop:"startEndDate"}},[t("el-date-picker",{attrs:{"unlink-panels":"",type:e.dateType,"range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期"},model:{value:e.searchValidateForm.startEndDate,callback:function(t){e.$set(e.searchValidateForm,"startEndDate",t)},expression:"searchValidateForm.startEndDate"}})],1),t("el-form-item",{attrs:{label:"",prop:"mode"}},[t("el-select",{attrs:{filterable:"",placeholder:"请选择模式",clearable:""},on:{change:e.onChangeMode},model:{value:e.searchValidateForm.mode,callback:function(t){e.$set(e.searchValidateForm,"mode",t)},expression:"searchValidateForm.mode"}},e._l(e.searchModeOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.label,value:e.value}})})),1)],1),t("el-form-item",{attrs:{label:"",prop:"timeSpotId"}},[t("el-select",{attrs:{filterable:"",placeholder:"请选择景区",clearable:""},model:{value:e.searchValidateForm.timeSpotId,callback:function(t){e.$set(e.searchValidateForm,"timeSpotId",t)},expression:"searchValidateForm.timeSpotId"}},e._l(e.searchScenicOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.SCENIC_SPOT_NAME,value:e.SCENIC_SPOT_ID}})})),1)],1)]:e._e(),1==e.searchValidateForm.mode?[t("el-form-item",{attrs:{label:"",prop:"spotIdList"}},[e.updateSelectSpotIdList?t("el-select",{attrs:{filterable:"",placeholder:"请选择景区",clearable:"",multiple:"","collapse-tags":""},model:{value:e.searchValidateForm.spotIdList,callback:function(t){e.$set(e.searchValidateForm,"spotIdList",t)},expression:"searchValidateForm.spotIdList"}},e._l(e.searchScenicOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.SCENIC_SPOT_NAME,value:e.SCENIC_SPOT_ID}})})),1):e._e()],1),t("el-form-item",{attrs:{label:"",prop:"mode"}},[t("el-select",{attrs:{filterable:"",placeholder:"请选择模式",clearable:""},on:{change:e.onChangeMode},model:{value:e.searchValidateForm.mode,callback:function(t){e.$set(e.searchValidateForm,"mode",t)},expression:"searchValidateForm.mode"}},e._l(e.searchModeOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.label,value:e.value}})})),1)],1),t("el-form-item",{attrs:{label:"",prop:"date"}},[e.updateSelectDate?t("el-date-picker",{attrs:{type:"date",placeholder:"请选择日期"},model:{value:e.searchValidateForm.date,callback:function(t){e.$set(e.searchValidateForm,"date",t)},expression:"searchValidateForm.date"}}):e._e()],1)]:e._e(),2==e.searchValidateForm.mode?[t("el-form-item",{attrs:{label:"",prop:"multipeDate"}},[t("div",{on:{click:function(e){e.stopPropagation()}}},[t("el-date-picker",{ref:"mydates",attrs:{id:"mydates","unlink-panels":"",type:"dates",editable:!1,"range-separator":"至",placeholder:"请选择日期","default-value":new Date},on:{focus:e.onFocusDate},model:{value:e.searchValidateForm.multipeDate,callback:function(t){e.$set(e.searchValidateForm,"multipeDate",t)},expression:"searchValidateForm.multipeDate"}})],1)]),t("el-form-item",{attrs:{label:"",prop:"mode"}},[t("el-select",{attrs:{filterable:"",placeholder:"请选择模式",clearable:""},on:{change:e.onChangeMode},model:{value:e.searchValidateForm.mode,callback:function(t){e.$set(e.searchValidateForm,"mode",t)},expression:"searchValidateForm.mode"}},e._l(e.searchModeOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.label,value:e.value}})})),1)],1),t("el-form-item",{attrs:{label:"",prop:"spotId"}},[e.updateSelectSpotId?t("el-select",{attrs:{filterable:"",placeholder:"请选择景区",clearable:""},model:{value:e.searchValidateForm.spotId,callback:function(t){e.$set(e.searchValidateForm,"spotId",t)},expression:"searchValidateForm.spotId"}},e._l(e.searchScenicOptions,(function(e,a){return t("el-option",{key:a,attrs:{label:e.SCENIC_SPOT_NAME,value:e.SCENIC_SPOT_ID}})})),1):e._e()],1)]:e._e()],2),t("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.onSearch("searchValidateForm")}}},[e._v("搜索")]),t("el-button",{attrs:{type:"primary",icon:"el-icon-refresh"},on:{click:function(t){return e.onRefresh("searchValidateForm")}}},[e._v("刷新")])],1),t("el-main",[t("div",{staticClass:"my-echarts"},[t("div",{attrs:{id:"myChart"}})])]),t("el-footer")],1)],1)},n=[],d=(a("d9e2"),a("14d9"),a("13d5"),a("9588")),c=a.n(d),p=a("5f5c"),h=a.n(p);function u(e){var t=e.getFullYear().toString(),a=(e.getMonth()+1).toString().padStart(2,"0"),i=(e=e.getDate().toString().padStart(2,"0"),t+"-"+a+"-"+e);return i}var m={name:"PartnerCompany",data(){function e(e=""){return(t,a,i)=>{if(!a)return i(new Error(e+"不能为空"));i()}}return{searchCompanyName:"",tableFields:{"合作公司名称":"companyName","收款账户":"collectionAccount","收款账号":"collectionAccountNumber","开户行":"bank","创建时间":"createTime","备注":"notes"},tableData:[],exportTableData:[],downloadLoading:!1,loadingTable:!1,currentPage:1,pageSize:10,dialogVisible:!1,dialogState:"",dateType:"daterange",valueFormat:"yyyy-MM-dd",updateSelectSpotIdList:!0,updateSelectDate:!0,updateSelectSpotId:!0,searchModeOptions:[{label:"单选景区多选时间范围",value:0},{label:"单选日期多选景区",value:1},{label:"单选景区多选日期",value:2}],searchScenicOptions:[],totle:0,allTotle:0,searchValidateForm:{mode:0,spotIdList:[],spotId:"",timeSpotId:"",date:new Date,startEndDate:[new Date,new Date],multipeDate:[]},searchVF:{mode:2,spotIdList:[],spotId:"",timeSpotId:"",date:"",startEndDate:[],multipeDate:[]},validateForm:{},total:0,myChart:null,option:{},realTimeList:[],drysList:[],jxzList:[],validateFormRules:{companyName:[{validator:e("合作公司名称"),required:!0,trigger:["blur","change"]}],collectionAccount:[{validator:e("收款账户"),required:!0,trigger:["blur","change"]}],collectionAccountNumber:[{validator:e("收款账号"),required:!0,trigger:["blur","change"]}],bank:[{validator:e("开户行"),required:!0,trigger:["blur","change"]}]},searchValidateFormRules:{},timer:null}},created(){this.searchValidateForm.timeSpotId=JSON.parse(window.sessionStorage.getItem("realTimeLineSpotIdCopy")),this.getValidateForm(),this.getAllSpotList()},mounted(){const e=()=>{this.timer=setInterval(()=>{console.log("监听");var e=JSON.parse(window.sessionStorage.getItem("realTimeLineSpotId"));e&&(window.sessionStorage.setItem("realTimeLineSpotIdCopy",e),window.sessionStorage.removeItem("realTimeLineSpotId"),window.location.reload())},1e3)};e(),document.addEventListener("visibilitychange",()=>{const t=document.visibilityState;"visible"==t?e():clearInterval(this.timer)}),this.getRevenueLine()},beforeDestroy(){clearInterval(this.timer)},methods:{getValidateForm(){var e={};for(var t in this.tableFields)e[this.tableFields[t]]="";this.validateForm=e},async login(){const{data:e}=await this.$http.post("/loginSystem?username=houjingchen&password=houjingchen")},async initTableData(){var e=this.searchVF.companyName;""!=e&&e||(e=null),this.loadingTable=!0;const{data:t}=await this.$http.get("/system/cooperative_company/list",{params:{name:e,pageNum:(this.currentPage-1)*this.pageSize,pageSize:this.pageSize}});"200"==t.state?(this.tableData=t.data,this.total=t.total,this.$message({message:t.msg,type:"success",offset:100,center:!0,duration:1e3}),this.loadingTable=!1):this.$message({message:t.msg,type:"error",offset:100,center:!0,duration:3e3})},async addTableData(){const{data:e}=await this.$http.post("/system/cooperative_company/add",this.validateForm);"200"==e.state?(this.$message({message:e.msg,type:"success",offset:100,center:!0,duration:1e3}),this.initTableData(),this.$refs.validateForm.resetFields()):this.$message({message:e.msg,type:"error",offset:100,center:!0,duration:3e3})},async editTableData(){const{data:e}=await this.$http.post("/system/cooperative_company/edit",this.validateForm);"200"==e.state?(this.$message({message:e.msg,type:"success",offset:100,center:!0,duration:1e3}),this.initTableData()):this.$message({message:e.msg,type:"error",offset:100,center:!0,duration:3e3})},async getExportTableData(){const{data:e}=await this.$http.get("/system/cooperative_company/list");"200"==e.state&&(this.exportTableData=e.data)},async getAllSpotList(){const{data:e}=await this.$http.get("/system/robot_time_statistics/spotList");this.searchScenicOptions=e.data},async getRevenueLine(){this.drawEcharts();var e,t,a,i,s=this.searchValidateForm.spotIdList,o=this.searchValidateForm.spotId,r=this.searchValidateForm.timeSpotId,l="",n=this.searchValidateForm.date,d=this.searchValidateForm.startEndDate,c=this.searchValidateForm.multipeDate,p=this.searchValidateForm.mode;0==p?(l=""!=r&&r?r:null,0!=d.length&&d?(t=u(d[0]),a=u(d[1])):(t=null,a=null)):1==p?(l=0!=s.length&&s?s.join(","):null,e=""!=n&&n?u(n):null):2==p&&(l=""!=o&&o?o:null,i=0!=d.length&&d?c.reduce((e,t)=>e+","+u(t),"").slice(1):null),this.myChart.showLoading({text:"loading",color:"#c23531",textColor:"#000",maskColor:"rgba(255, 255, 255, 0.2)",zlevel:0});const{data:h}=await this.$http.get("/system/realTime/lineN",{params:{spotId:l,time:e,startTime:t,endTime:a,multipeDate:i,type:p}});if("200"==h.state){this.myChart.hideLoading();var m=h.data.drysMax,f=h.data.jxzMax,g=6,b=null,y=null;m<=g&&(m=g),f<=g&&(f=g),b=Math.ceil(m/g),y=Math.ceil(f/g),m=b*g,f=y*g,this.option.legend.data=h.data.nameList,this.option.xAxis[0].data=h.data.dateList,this.option.yAxis[0].interval=b,this.option.yAxis[1].interval=y,this.option.yAxis[0].max=m,this.option.yAxis[1].max=f,this.totle=h.data.totle,this.allTotle=h.data.allTotle,this.option.series=h.data.lineList,this.myChart.setOption(this.option)}else this.$message({type:"error",message:"无数据",offset:100,center:!0,duration:3e3}),this.myChart.hideLoading()},getTableData(){this.searchValidateForm.spotId!=this.searchVF.spotId&&(this.searchVF.spotId=this.searchValidateForm.spotId,console.log("调用接口"),this.initTableData())},onChange(){},onSearch(e){this.$refs[e].validate(e=>{if(!e)return console.log("error submit!!"),!1;this.getRevenueLine()})},onRefresh(){this.searchValidateForm.spotIdList=[],this.searchValidateForm.spotId="",this.searchValidateForm.mode=0,this.searchValidateForm.date=new Date,this.searchValidateForm.startEndDate=[new Date,new Date],this.searchValidateForm.multipeDate=[new Date],this.updateSelectSpotIdList=!1,this.$nextTick(()=>{this.updateSelectSpotIdList=!0}),this.updateSelectDate=!1,this.$nextTick(()=>{this.updateSelectDate=!0}),this.updateSelectSpotId=!1,this.$nextTick(()=>{this.updateSelectSpotId=!0}),this.getRevenueLine()},exportExcel(){this.$confirm("确认导出Excel表格?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(async()=>{this.downloadLoading=!0,await this.getExportTableData(),this.$nextTick(()=>{var e={raw:!0},t=h.a.utils.table_to_book(document.querySelector("#outTable"),e),a=h.a.write(t,{bookType:"xlsx",bookSST:!0,type:"array"});try{c.a.saveAs(new Blob([a],{type:"application/octet-stream"}),"合作公司.xlsx")}catch(i){"undefined"!==typeof console&&console.log(i,a)}return this.downloadLoading=!1,a}),this.$message({type:"success",message:"已成功导出Excel表格！",offset:100,center:!0,duration:1e3})}).catch(()=>{this.$message({type:"info",message:"已取消导出Excel表格",offset:100,center:!0,duration:1e3})})},onAdd(){this.dialogVisible=!0,this.dialogState="add"},editRow(e,t){this.dialogVisible=!0;var a=Object.assign({},t);this.validateForm=a,this.dialogState="edit"},dialogClose(){this.dialogVisible=!1,this.$refs.validateForm.resetFields(),this.getValidateForm()},handleClose(e){e(),this.$refs.validateForm.resetFields(),this.getValidateForm()},submitForm(e){this.$refs[e].validate(async e=>{if(!e)return console.log("error submit!!"),!1;console.log(this.validateForm),"add"==this.dialogState?(console.log("新增公司信息"),this.addTableData()):"edit"==this.dialogState&&(console.log("编辑公司信息"),this.editTableData()),this.dialogVisible=!1,this.$refs.validateForm.resetFields()})},drawEcharts(){var e=this;this.myChart=this.$echarts.init(document.getElementById("myChart")),this.option={tooltip:{trigger:"axis",formatter:function(t){for(var a=[],i="",s=0;s<t.length;s++)a.push('<span style="display: inline-block;padding: 3px 0;" ><i style="display: inline-block;width: 10px;height: 10px;margin-right:7px;background: '+t[s].color+';border-radius: 50%;}"></i><span style="display:inline-block;">'+t[s].seriesName+":&nbsp&nbsp</span><span style='font-weight: bold;font-size:14.5px;'>"+t[s].value+"</span></span>");return i=a.join("<br/>"),`<div style="display: flex;justify-content: space-between;align-items: center;">\n\t\t\t\t\t\t<div>${t[0].name}</div>\n\t\t\t\t\t\t<div style="font-weight: bold;font-size:14.5px;">\n\t\t\t\t\t\t\t<div>已结算订单数：${e.totle}</div>\n\t\t\t\t\t\t\t<div>累计订单总数：${e.allTotle}</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t\t<div>${i} </div>`},axisPointer:{type:"cross",crossStyle:{color:"#999"}},textStyle:{align:"left"}},toolbox:{feature:{dataView:{show:!0,readOnly:!1},magicType:{show:!0,type:["line","bar"]},saveAsImage:{show:!0}}},legend:{bottom:0,data:["当日营收/元","当日在线订单/条"]},xAxis:[{type:"category",data:[],axisPointer:{type:"shadow"}}],yAxis:[{type:"value",name:"营收",max:null,splitNumber:6,interval:null},{type:"value",name:"在线订单",max:null,splitNumber:6,interval:null}],series:[]};for(let t=0;t<999;t++)this.option.series[t]=t%2==0?{type:"line",yAxisIndex:0}:{type:"line",yAxisIndex:1};this.myChart.setOption(this.option,!0),window.addEventListener("resize",()=>{this.myChart.resize()})},onCurrentChange(e){this.currentPage=e,this.initTableData()},onSizeChange(e){this.pageSize=e,this.initTableData()},onChangeMode(e){0==e||(1==e?(this.updateSelectSpotIdList=!1,this.$nextTick(()=>{this.updateSelectSpotIdList=!0}),this.updateSelectDate=!1,this.$nextTick(()=>{this.updateSelectDate=!0})):2==e&&(this.$nextTick(()=>{this.$refs.mydates.showPicker(),this.$nextTick(()=>{var e=document.querySelector(".available.today");e.click(),this.$refs.mydates.hidePicker()})}),this.updateSelectSpotId=!1,this.$nextTick(()=>{this.updateSelectSpotId=!0})))},onFocusDate(){this.$refs.mydates.handleClose=function(){this.pickerVisible&&(this.pickerVisible=!1)}}},components:{}},f=m,g=(a("9195"),a("2877")),b=Object(g["a"])(f,l,n,!1,null,"2c81660c",null),y=b.exports,v={name:"App",components:{RevenueLine:y}},S=v,F=(a("d34b"),Object(g["a"])(S,o,r,!1,null,null,null)),x=F.exports,V=a("5880"),w=a.n(V);s.a.use(w.a);var I=new w.a.Store({state:{},mutations:{},actions:{},modules:{}}),T=(a("9e1f"),a("450d"),a("6ed5")),D=a.n(T),$=(a("0fb7"),a("f529")),k=a.n($),C=(a("826b"),a("c263")),_=a.n(C),L=(a("be4f"),a("896a")),O=a.n(L),E=(a("eca7"),a("3787")),A=a.n(E),M=(a("425f"),a("4105")),N=a.n(M),P=(a("a7cc"),a("df33")),j=a.n(P),z=(a("672e"),a("101e")),R=a.n(z),q=(a("5466"),a("ecdf")),B=a.n(q),J=(a("38a0"),a("ad41")),X=a.n(J),Y=(a("10cb"),a("f3ad")),G=a.n(Y),H=(a("bdc7"),a("aa2f")),K=a.n(H),Q=(a("de31"),a("c69e")),U=a.n(Q),W=(a("a673"),a("7b31")),Z=a.n(W),ee=(a("adec"),a("3d2d")),te=a.n(ee),ae=(a("6611"),a("e772")),ie=a.n(ae),se=(a("1f1a"),a("4e4b")),oe=a.n(se),re=(a("1951"),a("eedf")),le=a.n(re);s.a.use(le.a),s.a.use(oe.a),s.a.use(ie.a),s.a.use(te.a),s.a.use(Z.a),s.a.use(U.a),s.a.use(K.a),s.a.use(G.a),s.a.use(X.a),s.a.use(B.a),s.a.use(R.a),s.a.use(j.a),s.a.use(N.a),s.a.use(A.a),s.a.use(O.a),s.a.use(_.a),s.a.prototype.$message=k.a,s.a.prototype.$confirm=D.a.confirm;var ne=a("bc3a"),de=a.n(ne),ce=a("164e");de.a.defaults.withCredentials=!0,s.a.prototype.$http=de.a,s.a.config.productionTip=!1,s.a.prototype.$echarts=ce,new s.a({store:I,render:e=>e(x)}).$mount("#app")},5880:function(e,t){e.exports=Vuex},"5dea":function(e,t,a){},"5f5c":function(e,t){e.exports=XLSX},"8bbf":function(e,t){e.exports=Vue},9195:function(e,t,a){"use strict";a("17dc")},9588:function(e,t){e.exports=FileSaver},d34b:function(e,t,a){"use strict";a("5dea")}});
//# sourceMappingURL=index.e3628c51.js.map