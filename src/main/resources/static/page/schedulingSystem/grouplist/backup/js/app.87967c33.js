(function(e){function t(t){for(var r,l,n=t[0],o=t[1],d=t[2],c=0,u=[];c<n.length;c++)l=n[c],Object.prototype.hasOwnProperty.call(i,l)&&i[l]&&u.push(i[l][0]),i[l]=0;for(r in o)Object.prototype.hasOwnProperty.call(o,r)&&(e[r]=o[r]);m&&m(t);while(u.length)u.shift()();return s.push.apply(s,d||[]),a()}function a(){for(var e,t=0;t<s.length;t++){for(var a=s[t],r=!0,n=1;n<a.length;n++){var o=a[n];0!==i[o]&&(r=!1)}r&&(s.splice(t--,1),e=l(l.s=a[0]))}return e}var r={},i={app:0},s=[];function l(t){if(r[t])return r[t].exports;var a=r[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,l),a.l=!0,a.exports}l.m=e,l.c=r,l.d=function(e,t,a){l.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},l.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},l.t=function(e,t){if(1&t&&(e=l(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(l.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)l.d(a,r,function(t){return e[t]}.bind(null,r));return a},l.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return l.d(t,"a",t),t},l.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},l.p="";var n=window["webpackJsonp"]=window["webpackJsonp"]||[],o=n.push.bind(n);n.push=t,n=n.slice();for(var d=0;d<n.length;d++)t(n[d]);var m=o;s.push([1,"chunk-vendors"]),a()})({0:function(e,t){},1:function(e,t,a){e.exports=a("56d7")},2:function(e,t){},3:function(e,t){},"56d7":function(e,t,a){"use strict";a.r(t);a("0fae");var r=a("9e2f"),i=a.n(r),s=a("2b0e"),l=function(){var e=this,t=e._self._c;return t("div",{attrs:{id:"app"}},[t("grouplist",[e._v("小组列表")])],1)},n=[],o=function(){var e=this,t=e._self._c;return t("div",{staticClass:"partner-company"},[t("el-container",[t("el-header",{staticClass:"header"},[t("el-form",{ref:"searchValidateForm",staticClass:"search-validate-form",attrs:{inline:!0,model:e.searchValidateForm,"label-width":"100px",rules:e.searchValidateFormRules}},[t("el-form-item",{attrs:{label:"",prop:"groupName"}},[t("el-input",{staticClass:"input-name",attrs:{placeholder:"小组名"},model:{value:e.searchValidateForm.groupName,callback:function(t){e.$set(e.searchValidateForm,"groupName",t)},expression:"searchValidateForm.groupName"}})],1)],1),t("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.onSearch("searchValidateForm")}}},[e._v("搜索")]),t("el-button",{attrs:{type:"primary",icon:"el-icon-refresh-right"},on:{click:e.onReset}},[e._v("重置")]),t("el-button",{attrs:{type:"primary",icon:"el-icon-circle-plus-outline"},on:{click:e.onAdd}},[e._v("新增小组")])],1),t("el-main",[t("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],staticClass:"table",staticStyle:{width:"100%"},attrs:{data:this.tableData,border:""}},[t("el-table-column",{attrs:{label:"小组名","min-width":"130"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("div",[e._v(" "+e._s(a.row.groupName)+" ")])]}}])}),t("el-table-column",{attrs:{label:"组长名","min-width":"130"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("div",[e._v(" "+e._s(a.row.groupLeaderName)+" ")])]}}])}),t("el-table-column",{attrs:{label:"组员姓名","min-width":"130",fixed:"right"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("div",e._l(a.row.memberList,(function(r,i){return t("span",{key:i},[e._v(" "+e._s(r.memberName)+e._s(i<a.row.memberList.length-1?", ":"")+" ")])})),0)]}}])}),t("el-table-column",{attrs:{label:"组员所属部门","min-width":"130",fixed:"right"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("div",[e._v(" "+e._s(a.row.departmentName)+" ")])]}}])}),t("el-table-column",{attrs:{label:"操作","min-width":"130",fixed:"right"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("el-button",{attrs:{type:"text",size:"small"},nativeOn:{click:function(t){return t.preventDefault(),e.editRow(a.$index,a.row)}}},[e._v(" 编辑 ")]),t("el-button",{attrs:{type:"text",size:"small"},nativeOn:{click:function(t){return t.preventDefault(),e.deleteRow(a.$index,a.row)}}},[e._v(" 删除 ")])]}}])})],1)],1),t("el-footer",[t("div",{staticClass:"block"},[t("el-pagination",{attrs:{background:"","current-page":e.currentPage,"page-sizes":[10,20,30,40],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.onSizeChange,"current-change":e.onCurrentChange,"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t}}})],1)]),t("el-dialog",{staticClass:"dialog",attrs:{width:"50%",title:e.dialogTitle,visible:e.dialogVisible,"before-close":e.handleClose,"close-on-click-modal":!1},on:{"update:visible":function(t){e.dialogVisible=t}}},[t("el-form",{ref:"validateForm",staticClass:"demo-ruleForm",attrs:{model:e.validateForm,"label-width":"80px",rules:e.validateFormRules}},[t("el-form-item",{attrs:{label:"小组名称",prop:"groupName","label-width":"80px"}},[t("el-input",{attrs:{placeholder:"请输入小组名称"},model:{value:e.validateForm.groupName,callback:function(t){e.$set(e.validateForm,"groupName",t)},expression:"validateForm.groupName"}})],1),t("el-form-item",{attrs:{label:"组长姓名",prop:"groupLeaderName"}},[t("el-select",{attrs:{placeholder:"请选择组长"},on:{change:e.handlegroupLeaderNameChange},model:{value:e.selectedgroupLeaderName,callback:function(t){e.selectedgroupLeaderName=t},expression:"selectedgroupLeaderName"}},e._l(e.groupLeaderNamelist,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.memberName}})})),1)],1),t("el-form-item",{attrs:{label:"组员姓名",prop:"memberList"}},[t("el-select",{attrs:{multiple:"",placeholder:"请选择组员"},on:{change:e.handlememberNameChange},model:{value:e.selectedmemberName,callback:function(t){e.selectedmemberName=t},expression:"selectedmemberName"}},e._l(e.memberNameList,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.memberName}})})),1)],1),t("el-form-item",{attrs:{label:"所属部门",prop:"departmentName"}},[t("el-select",{attrs:{placeholder:"请选择部门"},on:{change:e.handleDepartmentChange},model:{value:e.selectedDepartment,callback:function(t){e.selectedDepartment=t},expression:"selectedDepartment"}},e._l(e.departmentNamelist,(function(e){return t("el-option",{key:e.value,attrs:{label:e.label,value:e.departmentName}})})),1)],1)],1),t("div",{staticClass:"submit"},[t("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.submitForm("validateForm")}}},[e._v("提交")]),t("el-button",{on:{click:e.dialogClose}},[e._v("取消")])],1)],1)],1)],1)},d=[];a("d9e2"),a("14d9"),a("907a"),a("986a"),a("1d02"),a("3c5d"),a("6ce5"),a("2834"),a("4ea1"),a("b7ef"),a("21a6"),a("1146");var m={name:"grouplist",data(){return{approveDialogVisible:!1,dialogTitle:"",appRoverData:[],showTable:!0,tableData:[],tableLoading:!1,searchCompanyOptions:[],searchScenicOptions:[],exportTableData:[],downloadLoading:!1,currentPage:1,pageSize:10,dialogVisible:!1,editDialogVisible:!1,detailDialogVisible:!1,detailTableList:[],dialogState:"",selectDateState:"1",dateType:"daterange",valueFormat:"yyyy-MM-dd",searchValidateForm:{id:"",groupName:"",departmentId:"",departmentName:"",groupLeader:"",groupLeaderName:"",memberList:[]},searchVF:{robotCode:"",spotId:"",faultName:"",faultTimeType:"",startEndDate:"",warrantyState:"",faultState:"",signState:""},validateForm:{},editValidateForm:{},searchPartnerCompanyName:"",searchScenicName:"",searchSelectDateState:"1",searchStartEndDate:"",searchPayState:"",companys:[],companyState:"",scenics:[],scenicState:"",downloadUrl:"",total:0,currentEditRowId:null,uploadDialogImageUrl:"",uploadDialogVisible:!1,uploadDisabled:!1,limitUpload:1,file:null,fileTemp:null,fileListUpload:[],modelScenicSpotName:[],searchValidateFormRules:{},validateFormRules:{groupName:[{required:!0,message:"请输入小组名称",trigger:"blur"}],groupLeaderName:[{required:!0,message:"请输入组长姓名",trigger:"blur"}],memberList:[{required:!0,message:"请输入组员姓名",trigger:"blur"}],departmentName:[{required:!0,message:"请输入部门名称",trigger:"blur"}]},editValidateFormRules:{},editDataRow:{},priorityList:[{value:"1",label:"A"},{value:"2",label:"B"},{value:"3",label:"C"}],departmentNamelist:[],memberNameList:[],groupLeaderNamelist:[],selectedDepartment:"",selectedmemberName:[],selectedgroupLeaderName:"",correspondingID:null,correspondingIDS:[],correspondingIDSS:null,getday:!1,days:"",startTime:"",endTime:"",timenode:[null,null],initTimeModle:new Date,projectstattime:[],stattimes:[]}},created(){this.initTableData()},beforeMount(){},mounted(){},methods:{async login(){const{data:e}=await this.$http.post("/loginSystem?username=houjingchen&password=houjingchen")},async initTableData(){const{data:e}=await this.$http.get("/demo2/group/listDetail",{params:{groupName:this.searchValidateForm.groupName,pageNum:this.currentPage,currentPage:this.currentPage,pageSize:this.pageSize}});this.tableData=e.data,this.total=e.totals,this.departmentNamelistData(),this.memberNameListData(),this.groupLeaderNamelistData()},async groupLeaderNamelistData(){const{data:e}=await this.$http.get("/demo2/group-member/page",{params:{pageNum:this.currentPage,currentPage:this.currentPage,pageSize:this.pageSize}});this.groupLeaderNamelist=e.data},async handlegroupLeaderNameChange(){this.validateForm.groupLeaderName=this.selectedgroupLeaderName,this.correspondingIDSS=await this.findgroupLeaderID(this.selectedgroupLeaderName),this.validateForm.groupLeader=this.correspondingIDSS},async findgroupLeaderID(e){const t=this.groupLeaderNamelist.findIndex(t=>t.memberName===e);return-1!==t?this.groupLeaderNamelist[t].id:null},async memberNameListData(){const{data:e}=await this.$http.get("/demo2/group-member/page",{params:{pageNum:this.currentPage,currentPage:this.currentPage,pageSize:this.pageSize}});this.memberNameList=e.data},async handlememberNameChange(){this.correspondingIDS=await this.findmemberNameID(this.selectedmemberName),this.validateForm.memberList=this.transformToMemberList(this.correspondingIDS,this.selectedmemberName),console.log("////////////"+JSON.stringify(this.validateForm.memberList))},async findmemberNameID(e){const t=[];for(const a of e){const e=this.memberNameList.findIndex(e=>e.memberName===a);-1!==e&&t.push(this.memberNameList[e].id)}return t.length>0?t:null},transformToMemberList(e,t){if(!e||0===e.length||!t||0===t.length)return[];const a=[];for(let r=0;r<e.length;r++)a.push({id:e[r],memberName:t[r]});return a},async departmentNamelistData(){const{data:e}=await this.$http.get(" /demo2/department/page",{params:{pageNum:this.currentPage,currentPage:this.currentPage,pageSize:this.pageSize}});this.departmentNamelist=e.data},async handleDepartmentChange(){this.validateForm.departmentName=this.selectedDepartment,this.correspondingID=await this.findDepartmentID(this.selectedDepartment),this.validateForm.departmentId=this.correspondingID},async findDepartmentID(e){const t=this.departmentNamelist.findIndex(t=>t.departmentName===e);return-1!==t?this.departmentNamelist[t].id:null},onReset(){this.searchValidateForm.groupName="",this.searchValidateForm.groupLeaderName="",this.searchValidateForm.memberNames="",this.searchValidateForm.departmentNames="",this.initTableData()},onSearch(e){this.$refs[e].validate(e=>{if(!e)return console.log("error submit!!"),!1;this.showTable=!0,this.getTableData()})},getTableData(){console.log("调用接口"),this.initTableData()},onAdd(){this.dialogVisible=!0,this.dialogState="add",this.dialogTitle="新增小组信息",this.validateForm={},this.selectedDepartment="",this.selectedmemberName="",this.selectedgroupLeaderName=""},async addTableData(){const{data:e}=await this.$http({method:"post",url:"/demo2/group/save",data:this.validateForm,dataType:"json"});"200"==e.state?(this.$message({message:e.msg,type:"success"}),this.initTableData()):this.$message({message:e.msg,type:"error"})},editRow(e,t){this.dialogVisible=!0,this.dialogState="edit";var a=Object.assign({},t);this.validateForm=a,this.selectedgroupLeaderName=this.validateForm.groupLeaderName;const r=this.validateForm.memberList.map(e=>e.memberName);this.selectedmemberName=r,this.selectedDepartment=this.validateForm.departmentName,this.dialogTitle="编辑小组信息",console.log("++++++++++++++++"+JSON.stringify(r))},async editTableData(){const{data:e}=await this.$http({method:"post",url:"/demo2/group/edit",data:this.validateForm,dataType:"json"});console.log(e),"200"==e.state?(this.$message({message:e.msg,type:"success"}),this.initTableData()):this.$message({message:e.msg,type:"error"})},deleteRow(e,t){this.$confirm("此操作将删除该行数据, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(async()=>{this.deleteTableDate(t.id)}).catch(()=>{this.$message({type:"info",message:"已取消删除",offset:100,center:!0,duration:1e3})})},async deleteTableDate(e){const{data:t}=await this.$http({method:"post",url:"/demo2/group/remove",data:{id:e}});"200"==t.state?(this.$message({type:"success",message:"删除成功",offset:100,center:!0,duration:1e3}),this.initTableData()):this.$message({type:"error",message:t.msg,offset:100,center:!0,duration:3e3})},dialogClose(){this.dialogVisible=!1,this.$refs.validateForm.resetFields()},editDialogClose(){this.editDialogVisible=!1,this.$refs.editValidateForm.resetFields(),this.editValidateForm={}},handleClose(e){e(),this.$refs.validateForm.resetFields()},editHandleClose(e){e(),this.$refs.editValidateForm.resetFields(),this.editValidateForm={}},submitForm(e){this.$refs[e].validate(e=>{if(!e)return console.log("error submit!!"),!1;"add"==this.dialogState?(console.log("新增小组信息"),this.addTableData(),this.dialogVisible=!1):"edit"==this.dialogState?(console.log("编辑小组信息"),this.editTableData(),this.dialogVisible=!1):"detail"==this.dialogState&&console.log("任务详情信息")})},onSizeChange(e){this.pageSize=e,this.initTableData()},onCurrentChange(e){this.currentPage=e,this.initTableData()},handleChange(e,t){console.log(e),this.fileTemp=e.raw,this.fileTemp?"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"==this.fileTemp.type||"application/vnd.ms-excel"==this.fileTemp.type?this.importfxx(this.fileTemp):this.$message({type:"warning",message:"附件格式错误，请删除后重新上传！"}):this.$message({type:"warning",message:"请上传附件！"})},handleExceed(e,t){this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${e.length} 个文件，共选择了 ${e.length+t.length} 个文件`)},handleRemove(e,t){this.fileTemp=null},importfxx(e){this.file=e;var t=!1,r=this.file,i=new FileReader;FileReader.prototype.readAsBinaryString=function(e){var t,r,i="",s=!1,l=new FileReader;l.onload=function(e){for(var n=new Uint8Array(l.result),o=n.byteLength,d=0;d<o;d++)i+=String.fromCharCode(n[d]);var m=a("1146");t=s?m.read(btoa(fixdata(i)),{type:"base64"}):m.read(i,{type:"binary"}),r=m.utils.sheet_to_json(t.Sheets[t.SheetNames[0]]),console.log(r),this.da=[...r];let c=[];return this.da.map(e=>{let t={robotCount:e["机器人数量"],type:e["发货进度"],applicantName:e["申请人"],factoryName:e["发货工厂"],consignorName:e["发货人"],receivingName:e["收货景区"],consigneeName:e["收货人"],consigneePhone:e["收货人电话"],receiningAddress:e["收货地址"],consignmentDate:e["发货时间"]};c.push(t)}),this.tableData=c,c},l.readAsArrayBuffer(e)},t?i.readAsArrayBuffer(r):i.readAsBinaryString(r)}},components:{},computed:{GetPriority(){return e=>(console.log(e),1==e?"A":2==e?"B":3==e?"C":void 0)},SpotName(){return e=>{var t=[];return e.appFlowPathSpotList.forEach(e=>{t.push(e.scenicSpotName)}),t+""}}}},c=m,u=(a("7163"),a("2877")),p=Object(u["a"])(c,o,d,!1,null,"1192812f",null),h=p.exports,g={name:"App",components:{grouplist:h}},f=g,b=(a("6ec8"),Object(u["a"])(f,l,n,!1,null,null,null)),v=b.exports,y=a("2f62");s["default"].use(y["a"]);var N=new y["a"].Store({state:{},mutations:{},actions:{},modules:{}}),D=a("bc3a"),S=a.n(D),w=a("c8fc"),F=a.n(w);s["default"].use(i.a),S.a.defaults.withCredentials=!0,s["default"].prototype.$http=S.a,s["default"].config.productionTip=!1,s["default"].use(F.a),new s["default"]({store:N,render:e=>e(v)}).$mount("#app")},"5f7d":function(e,t,a){},"6ec8":function(e,t,a){"use strict";a("c108")},7163:function(e,t,a){"use strict";a("5f7d")},c108:function(e,t,a){}});
//# sourceMappingURL=app.87967c33.js.map