(function(e){function t(t){for(var i,s,l=t[0],o=t[1],d=t[2],m=0,u=[];m<l.length;m++)s=l[m],Object.prototype.hasOwnProperty.call(r,s)&&r[s]&&u.push(r[s][0]),r[s]=0;for(i in o)Object.prototype.hasOwnProperty.call(o,i)&&(e[i]=o[i]);c&&c(t);while(u.length)u.shift()();return n.push.apply(n,d||[]),a()}function a(){for(var e,t=0;t<n.length;t++){for(var a=n[t],i=!0,l=1;l<a.length;l++){var o=a[l];0!==r[o]&&(i=!1)}i&&(n.splice(t--,1),e=s(s.s=a[0]))}return e}var i={},r={app:0},n=[];function s(t){if(i[t])return i[t].exports;var a=i[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,s),a.l=!0,a.exports}s.m=e,s.c=i,s.d=function(e,t,a){s.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},s.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},s.t=function(e,t){if(1&t&&(e=s(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(s.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var i in e)s.d(a,i,function(t){return e[t]}.bind(null,i));return a},s.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return s.d(t,"a",t),t},s.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},s.p="";var l=window["webpackJsonp"]=window["webpackJsonp"]||[],o=l.push.bind(l);l.push=t,l=l.slice();for(var d=0;d<l.length;d++)t(l[d]);var c=o;n.push([1,"chunk-vendors"]),a()})({0:function(e,t){},"0ab2":function(e,t,a){"use strict";a("2c01")},1:function(e,t,a){e.exports=a("56d7")},2:function(e,t){},"2c01":function(e,t,a){},3:function(e,t){},"56d7":function(e,t,a){"use strict";a.r(t);a("0fae");var i=a("9e2f"),r=a.n(i),n=a("2b0e"),s=function(){var e=this,t=e._self._c;return t("div",{attrs:{id:"app"}},[t("departmentNamelist",[e._v("部门列表")])],1)},l=[],o=function(){var e=this,t=e._self._c;return t("div",{staticClass:"partner-company"},[t("el-container",[t("el-header",{staticClass:"header"},[t("el-form",{ref:"searchValidateForm",staticClass:"search-validate-form",attrs:{inline:!0,model:e.searchValidateForm,"label-width":"100px",rules:e.searchValidateFormRules}},[t("el-form-item",{attrs:{label:"",prop:"departmentName"}},[t("el-input",{staticClass:"input-name",attrs:{placeholder:"部门名称"},model:{value:e.searchValidateForm.departmentName,callback:function(t){e.$set(e.searchValidateForm,"departmentName",t)},expression:"searchValidateForm.departmentName"}})],1)],1),t("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.onSearch("searchValidateForm")}}},[e._v("搜索")]),t("el-button",{attrs:{type:"primary",icon:"el-icon-refresh-right"},on:{click:e.onReset}},[e._v("重置")]),t("el-button",{attrs:{type:"primary",icon:"el-icon-circle-plus-outline"},on:{click:e.onAdd}},[e._v("新增部门")])],1),t("el-main",[t("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],staticClass:"table",staticStyle:{width:"100%"},attrs:{data:this.tableData,border:""}},[t("el-table-column",{attrs:{label:"部门名称","min-width":"130",fixed:"right"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("div",[e._v(" "+e._s(a.row.departmentName)+" ")])]}}])}),t("el-table-column",{attrs:{label:"操作","min-width":"130",fixed:"right"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("el-button",{attrs:{type:"text",size:"small"},nativeOn:{click:function(t){return t.preventDefault(),e.editRow(a.$index,a.row)}}},[e._v(" 编辑 ")]),t("el-button",{attrs:{type:"text",size:"small"},nativeOn:{click:function(t){return t.preventDefault(),e.deleteRow(a.$index,a.row)}}},[e._v(" 删除 ")])]}}])})],1)],1),t("el-footer",[t("div",{staticClass:"block"},[t("el-pagination",{attrs:{background:"","current-page":e.currentPage,"page-sizes":[10,20,30,40],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.onSizeChange,"current-change":e.onCurrentChange,"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t}}})],1)]),t("el-dialog",{staticClass:"dialog",attrs:{width:"50%",title:e.dialogTitle,visible:e.dialogVisible,"before-close":e.handleClose,"close-on-click-modal":!1},on:{"update:visible":function(t){e.dialogVisible=t}}},[t("el-form",{ref:"validateForm",staticClass:"demo-ruleForm",attrs:{model:e.validateForm,"label-width":"80px",rules:e.validateFormRules}},[t("el-form-item",{attrs:{label:"部门名称",prop:"departmentName"}},[t("el-input",{attrs:{placeholder:"请输入部门名称"},model:{value:e.validateForm.departmentName,callback:function(t){e.$set(e.validateForm,"departmentName",t)},expression:"validateForm.departmentName"}})],1)],1),t("div",{staticClass:"submit"},[t("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitForm("validateForm")}}},[e._v("提交")]),t("el-button",{on:{click:e.dialogClose}},[e._v("取消")])],1)],1)],1)],1)},d=[];a("d9e2"),a("907a"),a("986a"),a("1d02"),a("3c5d"),a("6ce5"),a("2834"),a("4ea1"),a("b7ef"),a("14d9"),a("21a6"),a("1146");var c={name:"departmentNamelist",data(){return{approveDialogVisible:!1,dialogTitle:"",appRoverData:[],showTable:!0,tableData:[],tableLoading:!1,searchCompanyOptions:[],searchScenicOptions:[],exportTableData:[],downloadLoading:!1,currentPage:1,pageSize:10,dialogVisible:!1,editDialogVisible:!1,detailDialogVisible:!1,detailTableList:[],dialogState:"",selectDateState:"1",dateType:"daterange",valueFormat:"yyyy-MM-dd",searchValidateForm:{departmentId:"",departmentName:""},searchVF:{robotCode:"",spotId:"",faultName:"",faultTimeType:"",startEndDate:"",warrantyState:"",faultState:"",signState:""},validateForm:{departmentId:"",departmentName:""},editValidateForm:{},searchPartnerCompanyName:"",searchScenicName:"",searchSelectDateState:"1",searchStartEndDate:"",searchPayState:"",companys:[],companyState:"",scenics:[],scenicState:"",downloadUrl:"",total:0,currentEditRowId:null,uploadDialogImageUrl:"",uploadDialogVisible:!1,uploadDisabled:!1,limitUpload:1,file:null,fileTemp:null,fileListUpload:[],modelScenicSpotName:[],searchValidateFormRules:{},validateFormRules:{departmentName:[{required:!0,message:"请输入部门名称",trigger:"blur"}]},editValidateFormRules:{},editDataRow:{},priorityList:[{value:"1",label:"A"},{value:"2",label:"B"},{value:"3",label:"C"}],getday:!1,days:"",startTime:"",endTime:"",timenode:[null,null],initTimeModle:new Date,projectstattime:[],stattimes:[]}},created(){this.initTableData()},beforeMount(){},mounted(){},methods:{async login(){const{data:e}=await this.$http.post("/loginSystem?username=houjingchen&password=houjingchen")},async initTableData(){const{data:e}=await this.$http.get("/projectManagement/userManage/department/page",{params:{departmentName:this.searchValidateForm.departmentName,pageNum:this.currentPage,pageSize:this.pageSize}});this.tableData=e.data},onReset(){this.searchValidateForm.departmentName="",this.initTableData()},onSearch(e){this.$refs[e].validate(e=>{if(!e)return console.log("error submit!!"),!1;this.showTable=!0,this.getTableData()})},getTableData(){console.log("调用接口"),this.initTableData()},onAdd(){this.dialogVisible=!0,this.dialogState="add",this.dialogTitle="新增部门信息",this.validateForm={}},async addTableData(){const{data:e}=await this.$http({method:"post",url:"/projectManagement/userManage/department/add",data:this.validateForm,dataType:"json"});"200"==e.state?(this.$message({message:e.msg,type:"success"}),this.dialogVisible=!1,this.initTableData(),this.validateForm={}):this.$message({message:e.msg,type:"error"})},editRow(e,t){this.dialogVisible=!0,this.dialogState="edit";var a=Object.assign({},t);this.validateForm=a,this.dialogTitle="编辑小组信息"},async editTableData(){const{data:e}=await this.$http({method:"post",url:"/projectManagement/userManage/department/edit",data:this.validateForm,dataType:"json"});console.log(e),"200"==e.state?(this.$message({message:e.msg,type:"success"}),this.initTableData()):this.$message({message:e.msg,type:"error"})},deleteRow(e,t){this.$confirm("此操作将删除该行数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(async()=>{this.deleteTableDate(t.id)}).catch(()=>{this.$message({type:"info",message:"已取消删除",offset:100,center:!0,duration:1e3})})},async deleteTableDate(e){const{data:t}=await this.$http({method:"post",url:"/projectManagement/userManage/department/remove",data:{id:e}});"200"==t.state?(this.$message({type:"success",message:"删除成功",offset:100,center:!0,duration:1e3}),this.initTableData()):this.$message({type:"error",message:t.msg,offset:100,center:!0,duration:3e3})},dialogClose(){this.dialogVisible=!1,this.$refs.validateForm.resetFields()},editDialogClose(){this.editDialogVisible=!1,this.$refs.editValidateForm.resetFields(),this.editValidateForm={}},handleClose(e){e(),this.$refs.validateForm.resetFields()},editHandleClose(e){e(),this.$refs.editValidateForm.resetFields(),this.editValidateForm={}},submitForm(e){this.$refs[e].validate(e=>{if(!e)return console.log("error submit!!"),!1;"add"==this.dialogState?(console.log("新增小组信息"),this.addTableData()):"edit"==this.dialogState?(console.log("编辑小组信息"),this.editTableData(),this.dialogVisible=!1):"detail"==this.dialogState&&console.log("任务详情信息")})},onSizeChange(e){this.pageSize=e,this.initTableData()},onCurrentChange(e){this.currentPage=e,this.initTableData()},handleChange(e,t){console.log(e),this.fileTemp=e.raw,this.fileTemp?"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"==this.fileTemp.type||"application/vnd.ms-excel"==this.fileTemp.type?this.importfxx(this.fileTemp):this.$message({type:"warning",message:"附件格式错误，请删除后重新上传！"}):this.$message({type:"warning",message:"请上传附件！"})},handleExceed(e,t){this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${e.length} 个文件，共选择了 ${e.length+t.length} 个文件`)},handleRemove(e,t){this.fileTemp=null},importfxx(e){this.file=e;var t=!1,i=this.file,r=new FileReader;FileReader.prototype.readAsBinaryString=function(e){var t,i,r="",n=!1,s=new FileReader;s.onload=function(e){for(var l=new Uint8Array(s.result),o=l.byteLength,d=0;d<o;d++)r+=String.fromCharCode(l[d]);var c=a("1146");t=n?c.read(btoa(fixdata(r)),{type:"base64"}):c.read(r,{type:"binary"}),i=c.utils.sheet_to_json(t.Sheets[t.SheetNames[0]]),console.log(i),this.da=[...i];let m=[];return this.da.map(e=>{let t={robotCount:e["机器人数量"],type:e["发货进度"],applicantName:e["申请人"],factoryName:e["发货工厂"],consignorName:e["发货人"],receivingName:e["收货景区"],consigneeName:e["收货人"],consigneePhone:e["收货人电话"],receiningAddress:e["收货地址"],consignmentDate:e["发货时间"]};m.push(t)}),this.tableData=m,m},s.readAsArrayBuffer(e)},t?r.readAsArrayBuffer(i):r.readAsBinaryString(i)}},components:{},computed:{GetPriority(){return e=>(console.log(e),1==e?"A":2==e?"B":3==e?"C":void 0)},SpotName(){return e=>{var t=[];return e.appFlowPathSpotList.forEach(e=>{t.push(e.scenicSpotName)}),t+""}}}},m=c,u=(a("0ab2"),a("2877")),p=Object(u["a"])(m,o,d,!1,null,"cac7be02",null),h=p.exports,g={name:"App",components:{departmentNamelist:h}},f=g,b=(a("7216"),Object(u["a"])(f,s,l,!1,null,null,null)),v=b.exports,y=a("2f62");n["default"].use(y["a"]);var w=new y["a"].Store({state:{},mutations:{},actions:{},modules:{}}),S=a("bc3a"),F=a.n(S),T=a("c8fc"),D=a.n(T);n["default"].use(r.a),F.a.defaults.withCredentials=!0,n["default"].prototype.$http=F.a,n["default"].config.productionTip=!1,n["default"].use(D.a),new n["default"]({store:w,render:e=>e(v)}).$mount("#app")},7216:function(e,t,a){"use strict";a("7952")},7952:function(e,t,a){}});
//# sourceMappingURL=app.efa6cdee.js.map