(function(e){function t(t){for(var r,i,s=t[0],l=t[1],c=t[2],d=0,p=[];d<s.length;d++)i=s[d],Object.prototype.hasOwnProperty.call(n,i)&&n[i]&&p.push(n[i][0]),n[i]=0;for(r in l)Object.prototype.hasOwnProperty.call(l,r)&&(e[r]=l[r]);u&&u(t);while(p.length)p.shift()();return o.push.apply(o,c||[]),a()}function a(){for(var e,t=0;t<o.length;t++){for(var a=o[t],r=!0,s=1;s<a.length;s++){var l=a[s];0!==n[l]&&(r=!1)}r&&(o.splice(t--,1),e=i(i.s=a[0]))}return e}var r={},n={app:0},o=[];function i(t){if(r[t])return r[t].exports;var a=r[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,i),a.l=!0,a.exports}i.m=e,i.c=r,i.d=function(e,t,a){i.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,t){if(1&t&&(e=i(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(i.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)i.d(a,r,function(t){return e[t]}.bind(null,r));return a},i.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(t,"a",t),t},i.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},i.p="";var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=t,s=s.slice();for(var c=0;c<s.length;c++)t(s[c]);var u=l;o.push([1,"chunk-vendors"]),a()})({0:function(e,t){},1:function(e,t,a){e.exports=a("56d7")},2:function(e,t){},3:function(e,t){},"56d7":function(e,t,a){"use strict";a.r(t);a("e260"),a("e6cf"),a("cca6"),a("a79d");var r=a("2b0e"),n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:"app"}},[a("people-count")],1)},o=[],i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"partner-company"},[a("el-container",[a("el-header",{staticClass:"header"},[a("el-form",{ref:"searchValidateForm",staticClass:"search-validate-form",attrs:{inline:!0,model:e.searchValidateForm,"label-width":"100px",rules:e.searchValidateFormRules}},[[a("el-form-item",{attrs:{label:"",prop:"spotId"}}),a("el-form-item",{attrs:{label:"",prop:"startEndDate"}},[a("el-date-picker",{attrs:{"unlink-panels":"",type:e.dateType,"range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期"},model:{value:e.searchValidateForm.startEndDate,callback:function(t){e.$set(e.searchValidateForm,"startEndDate",t)},expression:"searchValidateForm.startEndDate"}})],1)]],2),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.onSearch("searchValidateForm")}}},[e._v("搜索")]),a("el-button",{attrs:{type:"primary",icon:"el-icon-refresh"},on:{click:function(t){return e.onRefresh("searchValidateForm")}}},[e._v("刷新")])],1),a("el-main",[a("div",{staticClass:"my-echarts"},[a("div",{attrs:{id:"myChart"}})])]),a("el-footer")],1)],1)},s=[],l=a("1da1"),c=(a("96cf"),a("a9e3"),a("d81d"),a("b680"),a("159b"),a("21a6")),u=a.n(c),d=a("1146"),p=a.n(d);a("d3b7"),a("25f0"),a("4d90");function m(e){var t=e.getFullYear().toString(),a=(e.getMonth()+1).toString().padStart(2,"0"),r=(e=e.getDate().toString().padStart(2,"0"),t+"-"+a+"-"+e);return r}function f(){var e=new Date,t=new Date(e.getTime()-6048e5);return new Date(t)}var h={name:"PartnerCompany",data:function(){function e(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(t,a,r){if(!a)return r(new Error(e+"不能为空"));r()}}return{searchCompanyName:"",tableFields:{"合作公司名称":"companyName","收款账户":"collectionAccount","收款账号":"collectionAccountNumber","开户行":"bank","创建时间":"createTime","备注":"notes"},tableData:[],exportTableData:[],downloadLoading:!1,loadingTable:!1,currentPage:1,pageSize:10,dialogVisible:!1,dialogState:"",dateType:"daterange",valueFormat:"yyyy-MM-dd",updateSelectSpotIdList:!0,searchModeOptions:[{label:"固定时间",value:1},{label:"固定景区",value:2}],searchScenicOptions:[],searchValidateForm:{mode:2,spotIdList:[],spotId:"",date:new Date,startEndDate:[f(),new Date]},searchVF:{mode:2,spotIdList:[],spotId:"",date:"",startEndDate:[]},validateForm:{},total:0,myChart:null,option:{},realTimeList:[],drysList:[],jxzList:[],validateFormRules:{companyName:[{validator:e("合作公司名称"),required:!0,trigger:["blur","change"]}],collectionAccount:[{validator:e("收款账户"),required:!0,trigger:["blur","change"]}],collectionAccountNumber:[{validator:e("收款账号"),required:!0,trigger:["blur","change"]}],bank:[{validator:e("开户行"),required:!0,trigger:["blur","change"]}]},searchValidateFormRules:{}}},created:function(){this.searchValidateForm.spotId=JSON.parse(window.sessionStorage.getItem("realTimeLineSpotId")),window.sessionStorage.removeItem("realTimeLineSpotId"),this.getValidateForm()},mounted:function(){this.getPeopleCount()},methods:{getValidateForm:function(){var e={};for(var t in this.tableFields)e[this.tableFields[t]]="";this.validateForm=e},login:function(){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function t(){var a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/loginSystem?username=lijiazhao&password=lijiazhao");case 2:a=t.sent,a.data;case 4:case"end":return t.stop()}}),t)})))()},initTableData:function(){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function t(){var a,r,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return a=e.searchVF.companyName,""!=a&&a||(a=null),e.loadingTable=!0,t.next=5,e.$http.get("/system/cooperative_company/list",{params:{name:a,pageNum:(e.currentPage-1)*e.pageSize,pageSize:e.pageSize}});case 5:if(r=t.sent,n=r.data,"200"!=n.state){t.next=14;break}e.tableData=n.data,e.total=n.total,e.$message({message:n.msg,type:"success",offset:100,center:!0,duration:1e3}),e.loadingTable=!1,t.next=16;break;case 14:return e.$message({message:n.msg,type:"error",offset:100,center:!0,duration:3e3}),t.abrupt("return");case 16:case"end":return t.stop()}}),t)})))()},addTableData:function(){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/system/cooperative_company/add",e.validateForm);case 2:a=t.sent,r=a.data,"200"==r.state?(e.$message({message:r.msg,type:"success",offset:100,center:!0,duration:1e3}),e.initTableData(),e.$refs.validateForm.resetFields()):e.$message({message:r.msg,type:"error",offset:100,center:!0,duration:3e3});case 5:case"end":return t.stop()}}),t)})))()},editTableData:function(){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/system/cooperative_company/edit",e.validateForm);case 2:a=t.sent,r=a.data,"200"==r.state?(e.$message({message:r.msg,type:"success",offset:100,center:!0,duration:1e3}),e.initTableData()):e.$message({message:r.msg,type:"error",offset:100,center:!0,duration:3e3});case 5:case"end":return t.stop()}}),t)})))()},getExportTableData:function(){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/system/cooperative_company/list");case 2:if(a=t.sent,r=a.data,"200"!=r.state){t.next=8;break}e.exportTableData=r.data,t.next=9;break;case 8:return t.abrupt("return");case 9:case"end":return t.stop()}}),t)})))()},getAllSpotList:function(){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/system/robot_time_statistics/spotList");case 2:a=t.sent,r=a.data,e.searchScenicOptions=r.data;case 5:case"end":return t.stop()}}),t)})))()},getPeopleCount:function(){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function t(){var a,r,n,o,i,s,l,c,u,d,p,f,h,g,b,v,y;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.drawEcharts(),e.searchValidateForm.spotIdList,a=e.searchValidateForm.spotId,"",e.searchValidateForm.date,r=e.searchValidateForm.startEndDate,e.searchValidateForm.mode,""!=a&&a?a:null,0!=r.length&&r?(n=m(r[0]),o=m(r[1])):(n=null,o=null),e.myChart.showLoading({text:"loading",color:"#c23531",textColor:"#000",maskColor:"rgba(255, 255, 255, 0.2)",zlevel:0}),t.next=12,e.$http.get("/system/peopleCounting/getPeopleCountingList",{params:{beginDate:n,endDate:o}});case 12:i=t.sent,s=i.data,"200"==s.state?(e.myChart.hideLoading(),l=s.data.dateList||[],c=s.data.peopleList||[],u=s.data.orderNumberList.map((function(e){return(100*e).toFixed(4)}))||[],d=s.data.moneyList.map((function(e){return e.toFixed(2)}))||[],p=0,f=0,h=0,g=6,b=null,v=null,y=null,c.forEach((function(e){var t=Number(e);p<t&&(p=t)})),u.forEach((function(e){var t=Number(e);f<t&&(f=t)})),d.forEach((function(e){var t=Number(e);h<t&&(h=t)})),p<=g&&(p=g),f<=g&&(f=g),h<=g&&(h=g),b=Math.ceil(p/g),v=Math.ceil(f/g),y=Math.ceil(h/g),p=b*g,f=v*g,h=y*g,e.option.xAxis[0].data=l,e.option.yAxis[0].interval=b,e.option.yAxis[1].interval=v,e.option.yAxis[2].interval=y,e.option.yAxis[0].max=p,e.option.yAxis[1].max=f,e.option.yAxis[2].max=h,e.option.series[0].data=c,e.option.series[1].data=u,e.option.series[2].data=d,e.myChart.setOption(e.option)):(e.$message({type:"error",message:"无数据",offset:100,center:!0,duration:3e3}),e.myChart.hideLoading());case 15:case"end":return t.stop()}}),t)})))()},getTableData:function(){this.searchValidateForm.spotId!=this.searchVF.spotId&&(this.searchVF.spotId=this.searchValidateForm.spotId,console.log("调用接口"),this.initTableData())},onChange:function(){},onSearch:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;t.getPeopleCount()}))},onRefresh:function(){var e=this;this.searchValidateForm.spotIdList=[],this.searchValidateForm.spotId="",this.searchValidateForm.mode=1,this.searchValidateForm.date=new Date,this.searchValidateForm.startEndDate=[f(),new Date],this.updateSelectSpotIdList=!1,this.$nextTick((function(){e.updateSelectSpotIdList=!0})),this.getPeopleCount()},exportExcel:function(){var e=this;this.$confirm("确认导出Excel表格?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.downloadLoading=!0,t.next=3,e.getExportTableData();case 3:e.$nextTick((function(){var t={raw:!0},a=p.a.utils.table_to_book(document.querySelector("#outTable"),t),r=p.a.write(a,{bookType:"xlsx",bookSST:!0,type:"array"});try{u.a.saveAs(new Blob([r],{type:"application/octet-stream"}),"合作公司.xlsx")}catch(n){"undefined"!==typeof console&&console.log(n,r)}return e.downloadLoading=!1,r})),e.$message({type:"success",message:"已成功导出Excel表格！",offset:100,center:!0,duration:1e3});case 5:case"end":return t.stop()}}),t)})))).catch((function(){e.$message({type:"info",message:"已取消导出Excel表格",offset:100,center:!0,duration:1e3})}))},onAdd:function(){this.dialogVisible=!0,this.dialogState="add"},editRow:function(e,t){this.dialogVisible=!0;var a=Object.assign({},t);this.validateForm=a,this.dialogState="edit"},dialogClose:function(){this.dialogVisible=!1,this.$refs.validateForm.resetFields(),this.getValidateForm()},handleClose:function(e){e(),this.$refs.validateForm.resetFields(),this.getValidateForm()},submitForm:function(e){var t=this;this.$refs[e].validate(function(){var e=Object(l["a"])(regeneratorRuntime.mark((function e(a){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(!a){e.next=7;break}console.log(t.validateForm),"add"==t.dialogState?(console.log("新增公司信息"),t.addTableData()):"edit"==t.dialogState&&(console.log("编辑公司信息"),t.editTableData()),t.dialogVisible=!1,t.$refs.validateForm.resetFields(),e.next=9;break;case 7:return console.log("error submit!!"),e.abrupt("return",!1);case 9:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}())},drawEcharts:function(){var e=this;this.myChart=this.$echarts.init(document.getElementById("myChart")),this.option={tooltip:{trigger:"axis",axisPointer:{type:"cross",crossStyle:{color:"#999"}},textStyle:{align:"left"}},toolbox:{feature:{dataView:{show:!0,readOnly:!1},magicType:{show:!0,type:["line","bar"]},saveAsImage:{show:!0}}},legend:{data:["人数","总订单数除以人数","总金额除以人数"]},grid:{right:"15%"},xAxis:[{type:"category",data:[],axisPointer:{type:"shadow"}}],yAxis:[{type:"value",name:"人数",axisLabel:{formatter:"{value}"},max:null,splitNumber:6,interval:null,position:"left"},{type:"value",name:"总订单数除以人数",axisLabel:{formatter:"{value} %"},max:null,splitNumber:6,interval:null,position:"right"},{type:"value",name:"总金额除以人数",axisLabel:{formatter:"{value} 元"},max:null,splitNumber:6,interval:null,position:"right",offset:100,boundaryGap:[0,"50%"],axisLine:{lineStyle:{color:"#F81945"}},axisTick:{inside:"false",length:10}}],series:[{name:"人数",type:"bar",data:[],itemStyle:{normal:{label:{show:!0,position:"top",textStyle:{color:"black",fontSize:16},formatter:""}}}},{name:"总订单数除以人数",type:"line",yAxisIndex:1,tooltip:{valueFormatter:function(e){return e+" %"}},data:[]},{name:"总金额除以人数",type:"line",yAxisIndex:2,tooltip:{valueFormatter:function(e){return e+" 元"}},data:[]}]},this.myChart.setOption(this.option,!0),window.addEventListener("resize",(function(){e.myChart.resize()}))},onCurrentChange:function(e){this.currentPage=e,this.initTableData()},onSizeChange:function(e){this.pageSize=e,this.initTableData()},onChangeMode:function(e){var t=this;1==e&&(this.updateSelectSpotIdList=!1,this.$nextTick((function(){t.updateSelectSpotIdList=!0})))}},components:{}},g=h,b=(a("7cef"),a("2877")),v=Object(b["a"])(g,i,s,!1,null,"7f25d0ea",null),y=v.exports,x={name:"App",components:{peopleCount:y}},w=x,F=(a("5c0b"),Object(b["a"])(w,n,o,!1,null,null,null)),S=F.exports,T=a("2f62");r["default"].use(T["a"]);var V=new T["a"].Store({state:{},mutations:{},actions:{},modules:{}}),D=(a("9e1f"),a("450d"),a("6ed5")),$=a.n(D),k=(a("0fb7"),a("f529")),C=a.n(k),L=(a("826b"),a("c263")),O=a.n(L),I=(a("be4f"),a("896a")),j=a.n(I),E=(a("eca7"),a("3787")),R=a.n(E),A=(a("425f"),a("4105")),_=a.n(A),P=(a("a7cc"),a("df33")),N=a.n(P),z=(a("672e"),a("101e")),M=a.n(z),q=(a("5466"),a("ecdf")),B=a.n(q),J=(a("38a0"),a("ad41")),G=a.n(J),Y=(a("10cb"),a("f3ad")),H=a.n(Y),K=(a("bdc7"),a("aa2f")),Q=a.n(K),U=(a("de31"),a("c69e")),W=a.n(U),X=(a("a673"),a("7b31")),Z=a.n(X),ee=(a("adec"),a("3d2d")),te=a.n(ee),ae=(a("6611"),a("e772")),re=a.n(ae),ne=(a("1f1a"),a("4e4b")),oe=a.n(ne),ie=(a("1951"),a("eedf")),se=a.n(ie);r["default"].use(se.a),r["default"].use(oe.a),r["default"].use(re.a),r["default"].use(te.a),r["default"].use(Z.a),r["default"].use(W.a),r["default"].use(Q.a),r["default"].use(H.a),r["default"].use(G.a),r["default"].use(B.a),r["default"].use(M.a),r["default"].use(N.a),r["default"].use(_.a),r["default"].use(R.a),r["default"].use(j.a),r["default"].use(O.a),r["default"].prototype.$message=C.a,r["default"].prototype.$confirm=$.a.confirm;var le=a("bc3a"),ce=a.n(le),ue=a("313e");ce.a.defaults.withCredentials=!0,r["default"].prototype.$http=ce.a,r["default"].config.productionTip=!1,r["default"].prototype.$echarts=ue,new r["default"]({store:V,render:function(e){return e(S)}}).$mount("#app")},"5c0b":function(e,t,a){"use strict";a("9c0c")},"7cef":function(e,t,a){"use strict";a("8a49")},"8a49":function(e,t,a){},"9c0c":function(e,t,a){}});
//# sourceMappingURL=app.155d50ef.js.map