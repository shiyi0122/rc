(function(e){function t(t){for(var r,i,s=t[0],l=t[1],c=t[2],u=0,p=[];u<s.length;u++)i=s[u],Object.prototype.hasOwnProperty.call(n,i)&&n[i]&&p.push(n[i][0]),n[i]=0;for(r in l)Object.prototype.hasOwnProperty.call(l,r)&&(e[r]=l[r]);d&&d(t);while(p.length)p.shift()();return o.push.apply(o,c||[]),a()}function a(){for(var e,t=0;t<o.length;t++){for(var a=o[t],r=!0,s=1;s<a.length;s++){var l=a[s];0!==n[l]&&(r=!1)}r&&(o.splice(t--,1),e=i(i.s=a[0]))}return e}var r={},n={app:0},o=[];function i(t){if(r[t])return r[t].exports;var a=r[t]={i:t,l:!1,exports:{}};return e[t].call(a.exports,a,a.exports,i),a.l=!0,a.exports}i.m=e,i.c=r,i.d=function(e,t,a){i.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:a})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,t){if(1&t&&(e=i(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var a=Object.create(null);if(i.r(a),Object.defineProperty(a,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)i.d(a,r,function(t){return e[t]}.bind(null,r));return a},i.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(t,"a",t),t},i.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},i.p="";var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=t,s=s.slice();for(var c=0;c<s.length;c++)t(s[c]);var d=l;o.push([1,"chunk-vendors"]),a()})({0:function(e,t){},1:function(e,t,a){e.exports=a("56d7")},1501:function(e,t,a){},2:function(e,t){},"22ab":function(e,t,a){"use strict";a("38f8")},3:function(e,t){},"38f8":function(e,t,a){},"56d7":function(e,t,a){"use strict";a.r(t);a("0fae");var r=a("9e2f"),n=a.n(r),o=(a("e260"),a("e6cf"),a("cca6"),a("a79d"),a("ac1f"),a("5319"),a("4d63"),a("25f0"),a("2b0e")),i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{attrs:{id:"app"}},[a("broadcast-hunt-log")],1)},s=[],l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"partner-company"},[a("el-container",[a("el-header",{staticClass:"header"},[a("el-form",{ref:"searchValidateForm",staticClass:"search-validate-form",attrs:{inline:!0,model:e.searchValidateForm,"label-width":"100px",rules:e.searchValidateFormRules}},[a("el-form-item",{attrs:{label:"",prop:"userPhone1"}},[a("el-input",{attrs:{placeholder:"请输入用户手机号"},model:{value:e.searchValidateForm.userPhone1,callback:function(t){e.$set(e.searchValidateForm,"userPhone1",t)},expression:"searchValidateForm.userPhone1"}})],1)],1),a("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:function(t){return e.onSearch("searchValidateForm")}}},[e._v("搜索")]),a("el-button",{attrs:{type:"primary",icon:"el-icon-refresh-right"},on:{click:e.onReset}},[e._v("重置")])],1),a("el-main",[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],staticClass:"table",staticStyle:{width:"100%"},attrs:{data:e.tableData,border:"","header-cell-style":{background:"#f2f2f2",color:"#606266",height:"38px",padding:"0px"},"cell-style":{height:"38px",padding:"0"}}},e._l(e.tableFields,(function(t,r){return a("el-table-column",{key:r,attrs:{prop:t,label:r,"min-width":"130"},scopedSlots:e._u([{key:"default",fn:function(r){return[a("div",[e._v(" "+e._s(e.tableContentValue(r.row,t))+" ")])]}}],null,!0)})})),1),a("el-table",{staticClass:"table",staticStyle:{width:"100%",display:"none"},attrs:{id:"outTable",data:e.exportTableData,border:""}},e._l(e.tableFields,(function(e,t){return a("el-table-column",{key:t,attrs:{prop:e,label:t,"min-width":"130",align:"center"}})})),1)],1),a("el-footer",[a("div",{staticClass:"block"},[a("el-pagination",{attrs:{background:"",layout:"prev, pager, next, total, sizes, jumper",total:e.total,"current-page":e.currentPage,"page-sizes":[10,20,30,40],"page-size":e.pageSize},on:{"current-change":e.onCurrentChange,"size-change":e.onSizeChange}})],1)]),a("el-dialog",{attrs:{title:"入库单","close-on-click-modal":!1,fullscreen:!0,visible:e.dialogVisible,"before-close":e.handleClose},on:{"update:visible":function(t){e.dialogVisible=t}}},[a("stock-list",{ref:"stockList",attrs:{goodsList:e.searchGoodsNameOptions,spotList:e.spotIdOptions,pickerOptions:e.pickerOptions,dialogState:e.dialogState,currentRowOrderNumber:e.currentRowOrderNumber,inStockTypeList:e.searchInStockTypeOptions},on:{save:e.onSave}})],1)],1)],1)},c=[],d=a("2909"),u=a("1da1"),p=a("5530");a("96cf"),a("a9e3"),a("d3b7"),a("3ca3"),a("ddb0"),a("2b3d"),a("159b"),a("99af"),a("5cc6"),a("9a8c"),a("a975"),a("735e"),a("c1ac"),a("d139"),a("3a7b"),a("d5d6"),a("82f8"),a("e91f"),a("60bd"),a("5f96"),a("3280"),a("3fcc"),a("ca91"),a("25a1"),a("cd26"),a("3c5d"),a("2954"),a("649e"),a("219c"),a("170b"),a("b39a"),a("72f7"),a("d81d"),a("21a6"),a("1146"),a("4d90"),a("1276");function m(e){try{var t=1,a=new Array("","万","亿"),r=new Array("拾","佰","仟"),n=new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖");function o(e){var t=new Array("","");f=e.split(".");for(var a=0;a<f.length;a++)t[a]=f[a];return t}var i=o(e),s=(i[0],i[1]),l=0,c=0,d=0,u="",p=i[0].length;for(t=1;t<=p;t++){var m=i[0].charAt(p-t),h=0;if(p-t-1>=0&&(h=i[0].charAt(p-t-1)),d+=Number(m),0!=d&&(u=n[Number(m)].concat(u),"0"==m&&(d=0)),p-t-1>=0)if(3!=l)0!=h&&(u=r[l].concat(u)),l++;else{l=0;var f=u.charAt(0);"万"!=f&&"亿"!=f||(u=u.substr(1,u.length-1)),u=a[c].concat(u),d=0}3==l&&c++}var g="";if(""!=s){m=s.charAt(0);0!=m&&(g+=n[Number(m)]+"角");m=s.charAt(1);0!=m&&(g+=n[Number(m)]+"分")}u+="元"+g}catch(v){return"0元"}return u}var h=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"stock-list"},[a("el-container",[a("el-header",[a("h1",[a("span",[e._v("No. ")]),a("span",[e._v(e._s(e.validateForm.orderNumber))])])]),a("el-main",[a("el-form",{ref:"validateForm",staticClass:"demo-ruleForm",attrs:{model:e.validateForm,"label-width":"100px",rules:e.validateFormRules}},[a("div",{staticClass:"one-row"},[a("el-form-item",{attrs:{label:"收货单位",prop:"selectReceivingIdList"}},[a("el-cascader",{attrs:{options:e.spotList,placeholder:"选择收货单位",clearable:"",props:{value:"id",label:"label",children:"addressList"}},on:{change:e.onReceivingIdChange},model:{value:e.validateForm.selectReceivingIdList,callback:function(t){e.$set(e.validateForm,"selectReceivingIdList",t)},expression:"validateForm.selectReceivingIdList"}})],1),a("el-form-item",{attrs:{label:"日期",prop:"orderTime"}},[a("el-date-picker",{staticClass:"color-606266",attrs:{type:"datetime",placeholder:"请选择日期","picker-options":e.pickerOptions,disabled:""},model:{value:e.validateForm.orderTime,callback:function(t){e.$set(e.validateForm,"orderTime",t)},expression:"validateForm.orderTime"}})],1)],1),a("div",{staticClass:"one-row"},[a("el-form-item",{attrs:{label:"供货单位",prop:"selectSpotIdList"}},[a("el-cascader",{attrs:{options:e.spotList,placeholder:"选择收货单位",clearable:"",props:{value:"id",label:"label",children:"addressList"}},on:{change:e.onSpotIdChange},model:{value:e.validateForm.selectSpotIdList,callback:function(t){e.$set(e.validateForm,"selectSpotIdList",t)},expression:"validateForm.selectSpotIdList"}})],1)],1),a("el-card",{staticClass:"box-card"},[a("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[a("span",{staticClass:"fs-18"},[e._v("新增入库单列表")]),"preview"!==e.dialogState?a("el-tooltip",{staticClass:"add-item",attrs:{effect:"dark",content:"新增",placement:"top"}},[a("el-button",{staticClass:"icon-size",staticStyle:{float:"right",padding:"3px 0"},attrs:{type:"text",icon:"el-icon-circle-plus-outline"},on:{click:e.onAddTable}})],1):e._e()],1),a("el-form-item",{staticClass:"table-form-item",attrs:{label:"",prop:"table"}},[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.tableLoading,expression:"tableLoading"}],staticClass:"table",staticStyle:{width:"100%"},attrs:{data:e.tableData,border:"","show-summary":"","summary-method":e.getSummaries,"cell-style":e.summariesStyle}},[a("el-table-column",{attrs:{type:"expand"},scopedSlots:e._u([{key:"default",fn:function(t){return["preview"===e.dialogState?[a("el-form",{staticClass:"demo-table-expand",attrs:{"label-position":"left"}},e._l(e.tableFields,(function(r,n){return a("el-form-item",{key:n,attrs:{label:n}},[a("span",[e._v(e._s(e.tableContentValue(t.row,r)))])])})),1)]:e._e(),"preview"!==e.dialogState?[a("el-form",{staticClass:"demo-table-expand",attrs:{"label-position":"left"}},e._l(e.tableFields,(function(r,n){return a("el-form-item",{key:n,staticClass:"table-expand-form-item",attrs:{label:n}},["inStockType"==r?[a("el-select",{attrs:{filterable:"",placeholder:""},on:{change:function(a){return e.onChangeReason(t.row)}},model:{value:t.row[r],callback:function(a){e.$set(t.row,r,a)},expression:"scope.row[value]"}},e._l(e.inStockTypeList,(function(e,t){return a("el-option",{key:t,attrs:{label:e.label,value:e.value}})})),1)]:"goodsCode"==r?[a("el-select",{attrs:{filterable:"",placeholder:""},on:{change:function(a){return e.onChangeGoods(t.row)}},model:{value:t.row[r],callback:function(a){e.$set(t.row,r,a)},expression:"scope.row[value]"}},e._l(e.goodsList,(function(e,t){return a("el-option",{key:t,attrs:{label:e.code,value:e.code}})})),1)]:"goodsName"==r||"model"==r||"unit"==r||"totalAmount"==r?[a("el-input",{staticClass:"color-606266",attrs:{disabled:""},model:{value:t.row[r],callback:function(a){e.$set(t.row,r,a)},expression:"scope.row[value]"}})]:"goodsAmount"==r||"unitPirce"==r?[a("el-input",{on:{input:function(a){return e.onInput(t.row)}},model:{value:t.row[r],callback:function(a){e.$set(t.row,r,a)},expression:"scope.row[value]"}})]:[a("el-input",{ref:"tableInputRef",refInFor:!0,model:{value:t.row[r],callback:function(a){e.$set(t.row,r,a)},expression:"scope.row[value]"}})]],2)})),1)]:e._e()]}}])}),a("el-table-column",{attrs:{label:"序号",type:"index",width:"100"}}),e._l(e.tableFields,(function(t,r){return a("el-table-column",{key:r,attrs:{prop:t,label:r,"min-width":"130"},scopedSlots:e._u([{key:"default",fn:function(r){return["preview"!==e.dialogState?["inStockType"==t?[a("el-select",{attrs:{filterable:"",placeholder:""},on:{change:function(t){return e.onChangeReason(r.row)}},model:{value:r.row[t],callback:function(a){e.$set(r.row,t,a)},expression:"scope.row[value]"}},e._l(e.inStockTypeList,(function(e,t){return a("el-option",{key:t,attrs:{label:e.label,value:e.value}})})),1)]:"goodsCode"==t?[a("el-select",{attrs:{filterable:"",placeholder:""},on:{change:function(t){return e.onChangeGoods(r.row)}},model:{value:r.row[t],callback:function(a){e.$set(r.row,t,a)},expression:"scope.row[value]"}},e._l(e.goodsList,(function(t,r){return a("el-option",{key:r,attrs:{label:t.code,value:t.code}},[a("span",{staticStyle:{float:"left"}},[e._v(e._s(t.code))]),a("span",{staticStyle:{float:"right",color:"#a3a6ad","font-size":"13px"}},[e._v(e._s(t.name))])])})),1)]:"goodsName"==t?[a("el-select",{attrs:{filterable:"",placeholder:""},on:{change:function(t){return e.onChangeNames(r.row)}},model:{value:r.row[t],callback:function(a){e.$set(r.row,t,a)},expression:"scope.row[value]"}},e._l(e.goodsList,(function(t,r){return a("el-option",{key:r,attrs:{label:t.name,value:t.code}},[a("span",[e._v(e._s(t.name))])])})),1)]:"model"==t||"unit"==t||"totalAmount"==t?[a("el-input",{staticClass:"color-606266",attrs:{disabled:""},model:{value:r.row[t],callback:function(a){e.$set(r.row,t,a)},expression:"scope.row[value]"}})]:"goodsAmount"==t||"unitPirce"==t?[a("el-input",{attrs:{type:"number"},on:{input:function(t){return e.onInput(r.row)}},model:{value:r.row[t],callback:function(a){e.$set(r.row,t,a)},expression:"scope.row[value]"}})]:"expressFee"==t?[a("el-input",{ref:"tableInputRef",refInFor:!0,attrs:{type:"number"},model:{value:r.row[t],callback:function(a){e.$set(r.row,t,a)},expression:"scope.row[value]"}})]:[a("el-input",{ref:"tableInputRef",refInFor:!0,model:{value:r.row[t],callback:function(a){e.$set(r.row,t,a)},expression:"scope.row[value]"}})]]:e._e(),"preview"===e.dialogState?[e._v(" "+e._s(e.tableContentValue(r.row,t))+" ")]:e._e()]}}],null,!0)})})),"preview"!==e.dialogState?a("el-table-column",{attrs:{label:"操作","min-width":"130"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tooltip",{staticClass:"add-item",attrs:{effect:"dark",content:"删除",placement:"top"}},[a("el-button",{staticClass:"icon-size",attrs:{type:"text",icon:"el-icon-remove-outline"},nativeOn:{click:function(a){return a.preventDefault(),e.delectRow(t.$index,t.row)}}})],1)]}}],null,!1,2987493361)}):e._e()],2)],1)],1),a("div",{staticClass:"one-row mt-10"},[a("el-form-item",{attrs:{label:"送货人",prop:"deliveryMan"}},[a("el-input",{attrs:{placeholder:"请输入送货人"},model:{value:e.validateForm.deliveryMan,callback:function(t){e.$set(e.validateForm,"deliveryMan",t)},expression:"validateForm.deliveryMan"}})],1),a("el-form-item",{attrs:{label:"送货人电话",prop:"phone"}},[a("el-input",{staticStyle:{width:"220px"},attrs:{placeholder:"请输入送货人电话",type:"number"},model:{value:e.validateForm.phone,callback:function(t){e.$set(e.validateForm,"phone",t)},expression:"validateForm.phone"}})],1),a("el-form-item",{attrs:{label:"收货人",prop:"reveiver"}},[a("el-input",{attrs:{placeholder:"请输入收货人"},model:{value:e.validateForm.reveiver,callback:function(t){e.$set(e.validateForm,"reveiver",t)},expression:"validateForm.reveiver"}})],1),a("el-form-item",{attrs:{label:"收货人电话",prop:"receivingPhone"}},[a("el-input",{staticStyle:{width:"220px"},attrs:{placeholder:"请输入收货人电话",type:"number"},model:{value:e.validateForm.receivingPhone,callback:function(t){e.$set(e.validateForm,"receivingPhone",t)},expression:"validateForm.receivingPhone"}})],1)],1)],1),"preview"!==e.dialogState?a("el-tooltip",{staticClass:"mt-10",attrs:{effect:"dark",content:"保存",placement:"top"}},[a("el-button",{staticStyle:{width:"100%"},attrs:{type:"primary",icon:"el-icon-check"},on:{click:function(t){return e.onSaveData("validateForm")}}},[e._v("保存")])],1):e._e(),"preview"===e.dialogState?a("el-tooltip",{staticClass:"mt-10",attrs:{effect:"dark",content:"下载",placement:"top"}},[a("el-button",{staticStyle:{width:"100%"},attrs:{type:"primary",icon:"el-icon-download",loading:e.downloadLoading},on:{click:e.onDownloadOrder}},[e._v("下载")])],1):e._e(),a("a",{staticClass:"exportOrder",attrs:{id:"exportOrder",href:e.downloadUrl,download:""}})],1)],1)],1)},f=[],g=(a("b0c0"),a("a434"),{name:"",props:{goodsList:{type:Array,required:!0,default:[]},spotList:{type:Array,required:!0,default:[]},pickerOptions:{type:Object,required:!0,default:{}},dialogState:{type:String,required:!0,default:""},currentRowOrderNumber:{type:String,required:!0,default:""},inStockTypeList:{type:Array,required:!0,default:[]}},data:function(){function e(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(t,a,r){if(!a)return r(new Error(e+"不能为空"));var n=/^1[3456789]\d{9}$/;n.test(a)?r():r(new Error(e+"必须是正确的手机号"))}}return{tableFields:{"入库类型":"inStockType","商品编码":"goodsCode","商品名称":"goodsName","规格型号":"model","单位":"unit","数量":"goodsAmount","进货单价(元)":"unitPirce","金额(元)":"totalAmount","快递单号":"courierNumber","快递费/元":"expressFee","备注":"notes"},tableData:[],tableLoading:!1,downloadLoading:!1,downloadUrl:"",validateForm:{orderNumber:"",deliveryMan:"",phone:"",orderTime:"",receivingId:"",receivingName:"",reveiver:"",receivingPhone:"",spotId:"",spotName:"",type:1,selectReceivingIdList:null,selectSpotIdList:null},validateFormRules:{orderNumber:[{validator:this.checkRequireMethod("单号"),required:!0,trigger:["blur","change"]}],selectReceivingIdList:[{validator:this.checkRequireMethod("收货单位"),required:!0,trigger:["blur","change"]}],orderTime:[{validator:this.checkRequireMethod("日期"),required:!0,trigger:["blur","change"]}],selectSpotIdList:[{validator:this.checkRequireMethod("供货单位"),required:!0,trigger:["blur","change"]}],table:[{validator:this.checkRequireMethod("表格"),required:!0,trigger:["blur","change"]}],deliveryMan:[{validator:this.checkRequireMethod("送货人"),required:!0,trigger:["blur","change"]}],phone:[{validator:e("送货人电话"),required:!0,trigger:["blur","change"]}],reveiver:[{validator:this.checkRequireMethod("收货人"),required:!0,trigger:["blur","change"]}],receivingPhone:[{validator:e("收货人电话"),required:!0,trigger:["blur","change"]}]}}},created:function(){},mounted:function(){},updated:function(){},methods:{checkRequireMethod:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(a,r,n){var o=!1;if(e.tableData.forEach((function(e){if("表格"==t&&e){if(!e.inStockType)return o=!1,n(new Error("入库类型不能为空"));if(!e.goodsCode)return o=!1,n(new Error("商品编码不能为空"));if(!e.goodsAmount)return o=!1,n(new Error("数量不能为空"));if(!e.unitPirce)return o=!1,n(new Error("单价不能为空"));if(!e.totalAmount)return o=!1,n(new Error("金额不能为空"));o=!0}})),"表格"==t&&o&&(r="这里随便给一个值就行"),!r)return n(new Error(t+"不能为空"));n()}},onChangeReceiving:function(e){var t="",a="";this.spotList.forEach((function(r){r.id==e&&(t=r.name,a=r.contacts)})),this.validateForm.receivingName=t,this.validateForm.reveiver=a},onChangeSpot:function(e){var t="",a="",r="";this.spotList.forEach((function(n){n.id==e&&(t=n.name,r=n.contacts,a=n.phone)})),this.validateForm.spotName=t,this.validateForm.phone=a,this.validateForm.deliveryMan=r},onChangeGoods:function(e){var t=e.goodsCode;this.goodsList.forEach((function(a){a.code==t&&(console.log(a),e.goodsId=a.id,e.goodsName=a.name,e.model=a.model,e.unit=a.unit,e.unitPirce=a.inPrice,e.totalAmount=e.goodsAmount*e.unitPirce)})),this.validateForm.details=this.tableData},onChangeNames:function(e){console.log(e);var t=e.goodsName;this.goodsList.forEach((function(a){a.code==t&&(console.log(a),e.goodsId=a.id,e.goodsName=a.name,e.model=a.model,e.unit=a.unit,e.unitPirce=a.inPrice,e.goodsCode=a.code,e.totalAmount=e.goodsAmount*e.unitPirce)})),this.validateForm.details=this.tableData},onChangeReason:function(e){},getOrderNumber:function(){var e=this;return Object(u["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/system/common/getOrderNumber",{params:{type:1}});case 2:a=t.sent,r=a.data,e.validateForm.orderNumber=r;case 5:case"end":return t.stop()}}),t)})))()},getByOrderNumber:function(){var e=this;return Object(u["a"])(regeneratorRuntime.mark((function t(){var a,r,n,o;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.validateForm.orderNumber||(e.validateForm.orderNumber=null),t.next=3,e.$http.get("/system/inventory_detail/getByOrderNumber",{params:{orderNumber:e.validateForm.orderNumber,type:1}});case 3:a=t.sent,r=a.data,console.log(r),n={orderNumber:r.data[0].orderNumber,deliveryMan:r.data[0].deliveryMan,orderTime:r.data[0].orderTime,phone:r.data[0].phone,receivingId:r.data[0].receivingId,receivingName:r.data[0].receivingName,reveiver:r.data[0].receiver,spotId:r.data[0].spotId,spotName:r.data[0].spotName,type:r.data[0].type,selectReceivingIdList:data[0].receivingLevelId&&data[0].receivingLevelId.split(",")&&data[0].receivingLevelId.split(",").map((function(e){return Number(e)})),selectSpotIdList:data[0].spotLevelId&&data[0].spotLevelId.split(",")&&data[0].spotLevelId.split(",").map((function(e){return Number(e)}))},o=r.data.map((function(e){return{id:e.id,inStockType:e.inStockType,goodsAmount:e.goodsAmount,goodsCode:e.goodsCode,goodsId:e.goodsId,goodsName:e.goodsName,model:e.model,notes:e.notes,totalAmount:e.totalAmount,unit:e.unit,unitPirce:e.unitPirce,courierNumber:e.courierNumber,expressFee:e.expressFee}})),e.tableData=o,n.details=o,e.validateForm=n;case 11:case"end":return t.stop()}}),t)})))()},addTableData:function(){var e=this;return Object(u["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return console.log(e.validateForm),t.next=3,e.$http.post("/system/inventory_detail/add",Object(p["a"])({},e.validateForm));case 3:a=t.sent,r=a.data,"200"==r.state?(e.$message({type:"success",message:"保存成功！"}),e.resetFields(),e.$emit("save")):e.$message({type:"error",message:"保存失败！"});case 6:case"end":return t.stop()}}),t)})))()},editTableData:function(){var e=this;return Object(u["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/system/inventory_detail/edit",e.validateForm);case 2:a=t.sent,r=a.data,"200"==r.state?(e.$message({type:"success",message:"编辑成功！"}),e.resetFields(),e.$emit("save")):e.$message({type:"success",message:"编辑失败！"});case 5:case"end":return t.stop()}}),t)})))()},getExportOrderData:function(){var e=this;return Object(u["a"])(regeneratorRuntime.mark((function t(){var a,r,n,o;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.get("/system/inventory_detail/downloadOrder",{params:{orderNumber:e.validateForm.orderNumber,type:1},responseType:"arraybuffer"});case 2:a=t.sent,r=a.data,r?(n=window.URL.createObjectURL(new Blob([r],{type:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})),o=document.createElement("a"),console.log(n),o.href=n,o.download="入库单",o.click(),e.$message({message:"导出成功",type:"success"})):e.$message({message:"导入失败",type:"error"});case 5:case"end":return t.stop()}}),t)})))()},onAddClick:function(e){this.validateForm.orderTime=(new Date).format("yyyy-MM-dd hh:mm:ss"),this.getOrderNumber()},onEditClick:function(e){console.log(e),this.validateForm.orderNumber=this.currentRowOrderNumber},onPreviewClick:function(e){console.log(e);var t={orderNumber:e[0].orderNumber,deliveryMan:e[0].deliveryMan,orderTime:e[0].orderTime,phone:e[0].phone,receivingId:e[0].receivingId,receivingName:e[0].receivingName,reveiver:e[0].receiver,receivingPhone:e[0].receivingPhone,spotId:e[0].spotId,spotName:e[0].spotName,type:e[0].type,selectReceivingIdList:e[0].receivingLevelId&&e[0].receivingLevelId.split(",")&&e[0].receivingLevelId.split(",").map((function(e){return Number(e)})),selectSpotIdList:e[0].spotLevelId&&e[0].spotLevelId.split(",")&&e[0].spotLevelId.split(",").map((function(e){return Number(e)}))},a=e.map((function(e){return{id:e.id,inStockType:e.inStockType,goodsAmount:e.goodsAmount,goodsCode:e.goodsCode,goodsId:e.goodsId,goodsName:e.goodsName,model:e.model,notes:e.notes,totalAmount:e.totalAmount,unit:e.unit,unitPirce:e.unitPirce,courierNumber:e.courierNumber,expressFee:e.expressFee}}));this.tableData=a,t.details=a,this.validateForm=t},getSummaries:function(e){var t=e.columns,a=e.data,r=[];return t.forEach((function(e,n){if(1!==n){var o=a.map((function(t){return Number(t[e.property])}));o.every((function(e){return isNaN(e)}))||(r[n]=o.reduce((function(e,t){var a=Number(t);return isNaN(a)?e:e+t}),0)),"inStockType"!=e.property&&"goodsCode"!=e.property&&"unit"!=e.property&&"goodsAmount"!=e.property&&"unitPirce"!=e.property&&"notes"!=e.property&&"courierNumber"!=e.property&&"expressFee"!=e.property||(r[n]=""),"goodsName"==e.property&&(r[n]="人民币(大写)零元"),"totalAmount"==e.property&&(t.forEach((function(e,t){"model"==e.property&&(r[t]=m(String(r[n])))})),r[n]+=" 元")}else r[n]="合计(大写)"})),r},resetFields:function(){this.tableData=[{inStockType:"",goodsAmount:"",goodsCode:"",goodsId:null,goodsName:"",model:"",notes:"",totalAmount:"",unit:"",unitPirce:"",courierNumber:"",expressFee:""}],this.dialogVisible=!1,this.$refs.validateForm.resetFields()},onInput:function(e){e.totalAmount=e.goodsAmount*e.unitPirce},delectRow:function(e,t){this.tableData.splice(e,1),this.$refs.tableInputRef[0].focus(),this.$refs.tableInputRef[0].blur()},onAddTable:function(){this.tableData.push({inStockType:"",goodsAmount:"",goodsCode:"",goodsId:null,goodsName:"",model:"",notes:"",totalAmount:"",unit:"",unitPirce:"",courierNumber:"",expressFee:""})},onSaveData:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;"add"==t.dialogState?t.addTableData():"edit"==t.dialogState&&t.editTableData()}))},onDownloadOrder:function(){var e=this;return Object(u["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:e.$confirm("确认导出Excel表格?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){e.downloadLoading=!0,e.getExportOrderData(),e.downloadLoading=!1})).catch((function(){e.$message({type:"info",message:"已取消导出Excel表格"})}));case 1:case"end":return t.stop()}}),t)})))()},summariesStyle:function(e){e.row,e.column,e.rowIndex,e.columnIndex},onReceivingIdChange:function(e){console.log(this.spotList),console.log(e);var t=this;this.spotList.forEach((function(a){a.id==e[0]&&a.addressList.forEach((function(a){a.id==e[1]&&a.addressList.forEach((function(a){a.id==e[2]&&a.addressList.forEach((function(e){t.validateForm.reveiver=e.name,t.validateForm.receivingPhone=e.phone}))}))}))})),this.validateForm.receivingId=e&&e[e.length-1]},onSpotIdChange:function(e){console.log(e);var t=this;this.spotList.forEach((function(a){a.id==e[0]&&a.addressList.forEach((function(a){a.id==e[1]&&a.addressList.forEach((function(a){a.id==e[2]&&a.addressList.forEach((function(e){t.validateForm.deliveryMan=e.name,t.validateForm.phone=e.phone}))}))}))})),this.validateForm.spotId=e&&e[e.length-1]}},computed:{dialogModel:{get:function(){return this.dialogState},set:function(e){}},tableContentValue:function(){return function(e,t){var a="";return"inStockType"==t?1==e[t]?a="采购":2==e[t]&&(a="回收"):a=e[t],a}}},watch:{}}),v=g,b=(a("22ab"),a("2877")),y=Object(b["a"])(v,h,f,!1,null,"6a006e0c",null),w=y.exports,F={name:"SettlementFlow",data:function(){function e(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"";return function(t,a,r){if(!a)return r(new Error(e+"不能为空"));r()}}return{showTable:!0,feeInput:"",taxInput:"",tableFields:{"景区名称":"scenicSpotName","用户手机号":"userPhone1","景点名称":"broadcastName","触发宝箱金币额度":"integralNum","创建时间":"createDate","更新时间":"updateDate"},tableData:[],tableLoading:!1,searchCompanyOptions:[],searchScenicOptions:[],receivingOptions:[],searchInStockTypeOptions:[{value:"1",label:"采购"},{value:"2",label:"回收"}],dateStateOptions:[{value:"1",label:"按月查询"},{value:"2",label:"按日查询"},{value:"3",label:"按年查询"}],searchSpotNameOptions:[],searchGoodsNameOptions:[],spotIdOptions:[],pickerOptions:{disabledDate:function(e){return e.getTime()>Date.now()},shortcuts:[{text:"今天",onClick:function(e){e.$emit("pick",new Date)}},{text:"昨天",onClick:function(e){var t=new Date;t.setTime(t.getTime()-864e5),e.$emit("pick",t)}},{text:"一周前",onClick:function(e){var t=new Date;t.setTime(t.getTime()-6048e5),e.$emit("pick",t)}}]},exportTableData:[],downloadLoading:!1,importLoading:!1,currentPage:1,pageSize:10,selectDateState:"1",dateType:"daterange",valueFormat:"yyyy-MM-dd",searchValidateForm:{},validateForm:{},editValidateForm:{},previewValidateForm:{previewOrderNumber:""},searchPartnerCompanyName:"",searchScenicName:"",searchSelectDateState:"1",searchStartEndDate:"",searchPayState:"",companys:[],companyState:"",scenics:[],scenicState:"",oddNumberList:[],downloadUrl:"",total:0,operateStatus:0,dialogVisible:!1,dialogState:"",currentRowOrderNumber:"",searchValidateFormRules:{},previewValidateFormRules:{previewOrderNumber:[{validator:e("单号"),required:!0,trigger:["blur","change"]}]},validateFormRules:{},editValidateFormRules:{}}},created:function(){this.getSearchValidateForm(),this.getValidateForm(),this.initTableData()},beforeMount:function(){},mounted:function(){},methods:{getSearchValidateForm:function(){var e={};for(var t in this.tableFields)e[this.tableFields[t]]="";this.searchValidateForm=Object(p["a"])(Object(p["a"])({},e),{},{goodId:null,spotId:null,inStockReason:""})},getValidateForm:function(){var e={};for(var t in this.tableFields)e[this.tableFields[t]]="";this.validateForm=e},login:function(){var e=this;return Object(u["a"])(regeneratorRuntime.mark((function t(){var a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$http.post("/loginSystem?username=houjingchen&password=houjingchen");case 2:a=t.sent,a.data;case 4:case"end":return t.stop()}}),t)})))()},initTableData:function(){var e=arguments,t=this;return Object(u["a"])(regeneratorRuntime.mark((function a(){var r,n,o,i,s;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return r=e.length>0&&void 0!==e[0]&&e[0],n=t.searchStartEndDate[0],o=t.searchStartEndDate[1],{},r?Object(p["a"])(Object(p["a"])({},t.searchValidateForm),{},{startDate:n,endDate:o,type:1,pageNum:(t.currentPage-1)*t.pageSize,pageSize:t.pageSize}):Object(p["a"])(Object(p["a"])({},t.searchValidateForm),{},{startDate:n,endDate:o,type:1,pageNum:0,pageSize:t.pageSize}),t.tableLoading=!0,a.next=8,t.$http.post("/system/broadcastHunt/getBroadcastHuntLog",Object(p["a"])(Object(p["a"])({},t.searchValidateForm),{},{pageNum:t.currentPage,pageSize:t.pageSize}));case 8:i=a.sent,s=i.data,console.log(s.list),"200"==s.code&&(t.tableData=s.list,t.total=s.totals,t.tableLoading=!1);case 12:case"end":return a.stop()}}),a)})))()},updateStatus:function(e,t){var a=this;return Object(u["a"])(regeneratorRuntime.mark((function r(){var n;return regeneratorRuntime.wrap((function(r){while(1)switch(r.prev=r.next){case 0:return r.next=2,a.$http.post("/system/inventory_detail/updateStatus",a.$qs.stringify({id:e,status:t}));case 2:n=r.sent,n.data,a.initTableData(!0);case 5:case"end":return r.stop()}}),r)})))()},getByOrderNumber:function(){var e=this;return Object(u["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.previewValidateForm.previewOrderNumber||(e.previewValidateForm.previewOrderNumber=null),t.next=3,e.$http.get("/system/inventory_detail/getByOrderNumber",{params:{orderNumber:e.previewValidateForm.previewOrderNumber,type:1}});case 3:return a=t.sent,r=a.data,t.abrupt("return",r);case 6:case"end":return t.stop()}}),t)})))()},getExportTableData:function(){var e=this;return Object(u["a"])(regeneratorRuntime.mark((function t(){var a,r,n,o,i,s;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return a=e.searchStartEndDate&&e.searchStartEndDate[0]||null,r=e.searchStartEndDate&&e.searchStartEndDate[1]||null,t.next=4,e.$http.get("/system/inventory_detail/downloadExcel",{params:Object(p["a"])(Object(p["a"])({},e.searchValidateForm),{},{startDate:a,endDate:r,type:1}),responseType:"arraybuffer"});case 4:n=t.sent,o=n.data,o?(i=window.URL.createObjectURL(new Blob([o],{type:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})),s=document.createElement("a"),console.log(i),s.href=i,s.download="入库信息",s.click(),e.$message({message:"导出成功",type:"success"})):e.$message({message:"导入失败",type:"error"});case 7:case"end":return t.stop()}}),t)})))()},importE:function(e){var t=this;return Object(u["a"])(regeneratorRuntime.mark((function a(){var r,n,o;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return t.importLoading=!0,r=new FormData,r.append("file",e),a.next=5,t.$http.post("/system/inventory_detail/importExcel",r);case 5:n=a.sent,o=n.data,t.importLoading=!1,"200"==o.state?(t.$message({message:"导入成功！",type:"success"}),t.initTableData()):t.$message({message:o.msg,type:"error"});case 9:case"end":return a.stop()}}),a)})))()},confirmRow:function(e,t){var a=this;this.$confirm("确认已入库?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){a.updateStatus(t.id,1),a.$message({type:"success",message:"确认成功!"})})).catch((function(){a.$message({type:"info",message:"已取消确认操作"})}))},revokeRow:function(e,t){var a=this;this.$confirm("您确定要撤销吗?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then((function(){a.updateStatus(t.id,2),a.$message({type:"success",message:"撤销成功!"})})).catch((function(){a.$message({type:"info",message:"已取消撤销操作"})}))},onChangeApplicant:function(e){var t=this;return Object(u["a"])(regeneratorRuntime.mark((function a(){var r;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",t.applicanteOptions.forEach((function(t){t.userId==e&&(r=t.userName)})),t.validateForm.applicantName=r;case 3:case"end":return a.stop()}}),a)})))()},onChangeFactory:function(e){var t=this;return Object(u["a"])(regeneratorRuntime.mark((function a(){var r;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",t.factoryOptions.forEach((function(t){t.spotId==e&&(r=t.spotName)})),t.validateForm.factoryName=r;case 3:case"end":return a.stop()}}),a)})))()},onChangeConsignor:function(e){var t=this;return Object(u["a"])(regeneratorRuntime.mark((function a(){var r;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",t.consignorOptions.forEach((function(t){t.userId==e&&(r=t.userName)})),t.validateForm.consignorName=r;case 3:case"end":return a.stop()}}),a)})))()},onChangeReceiving:function(e){var t=this;return Object(u["a"])(regeneratorRuntime.mark((function a(){var r,n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",n="",t.receivingOptions.forEach((function(t){t.spotId==e&&(r=t.spotName,n=t.address)})),t.validateForm.receivingName=r,t.validateForm.receiningAddress=n;case 5:case"end":return a.stop()}}),a)})))()},onChangeConsignee:function(e){var t=this;return Object(u["a"])(regeneratorRuntime.mark((function a(){var r;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:r="",t.consigneeOptions.forEach((function(t){t.userId==e&&(r=t.userName)})),t.validateForm.consigneeName=r;case 3:case"end":return a.stop()}}),a)})))()},onDateChange:function(){console.log(this.selectDateState),"1"==this.selectDateState?(this.dateType="monthrange",this.valueFormat="yyyy-MM"):"2"==this.selectDateState?(this.dateType="daterange",this.valueFormat="yyyy-MM-dd"):"3"==this.selectDateState&&(this.dateType="yearrange",this.valueFormat="yyyy")},onReset:function(){this.getSearchValidateForm(),this.searchStartEndDate=[],this.selectDateState="1",this.dateType="monthrange",this.initTableData()},onSearch:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;t.showTable=!0,t.initTableData()}))},exportExcel:function(){var e=this;this.$confirm("确认导出Excel表格?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(Object(u["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.downloadLoading=!0,t.next=3,e.getExportTableData();case 3:e.downloadLoading=!1;case 4:case"end":return t.stop()}}),t)})))).catch((function(){e.$message({type:"info",message:"已取消导出Excel表格"})}))},onAdd:function(){var e=this;this.dialogVisible=!0,this.dialogState="add",this.$nextTick((function(){e.$refs.stockList.onAddClick("add")}))},onPreview:function(e){var t=this;this.$refs[e].validate(function(){var e=Object(u["a"])(regeneratorRuntime.mark((function e(a){var r,n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(!a){e.next=8;break}return e.next=3,t.getByOrderNumber();case 3:r=e.sent,n=r.data,n&&n.length>0?(t.dialogVisible=!0,t.dialogState="preview",t.$nextTick((function(){t.$refs.stockList.onPreviewClick(n)}))):t.$message({message:"请输入有效单号",type:"warning"}),e.next=10;break;case 8:return console.log("error submit!!"),e.abrupt("return",!1);case 10:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}())},editRow:function(e,t){var a=this;this.currentRowOrderNumber=t.orderNumber,this.dialogVisible=!0,this.dialogState="edit",this.$nextTick((function(){a.$refs.stockList.onEditClick("edit")}))},detailRow:function(e,t){this.detailDialogVisible=!0,this.dialogState="detail",this.detailTableDate(t.id)},dialogClose:function(){this.dialogVisible=!1,this.$refs.validateForm.resetFields(),this.getValidateForm()},editDialogClose:function(){this.editDialogVisible=!1,this.$refs.editValidateForm.resetFields(),this.editValidateForm={}},detailDialogClose:function(){this.detailDialogVisible=!1},handleClose:function(e){e(),this.$refs.stockList.resetFields()},editHandleClose:function(e){e(),this.$refs.editValidateForm.resetFields(),this.editValidateForm={}},detailHandleClose:function(e){e()},submitForm:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return console.log("error submit!!"),!1;"add"==t.dialogState?(console.log("新增公司信息"),t.addTableData(),t.dialogVisible=!1):"edit"==t.dialogState?(console.log("编辑公司信息"),t.editTableData(),t.editDialogVisible=!1):"detail"==t.dialogState&&console.log("工厂发货详情信息"),t.$refs.validateForm.resetFields(),t.$refs.editValidateForm.resetFields()}))},onSave:function(){this.dialogVisible=!1,this.initTableData()},onDownloadOrder:function(){},onCurrentChange:function(e){this.currentPage=e,this.initTableData(!0)},onSizeChange:function(e){this.pageSize=e,this.initTableData(!0)},handleChange:function(e,t){console.log(e),this.fileTemp=e.raw,this.fileTemp?"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"==this.fileTemp.type||"application/vnd.ms-excel"==this.fileTemp.type?this.importfxx(this.fileTemp):this.$message({type:"warning",message:"附件格式错误，请删除后重新上传！"}):this.$message({type:"warning",message:"请上传附件！"})},handleExceed:function(e,t){this.$message.warning("当前限制选择 1 个文件，本次选择了 ".concat(e.length," 个文件，共选择了 ").concat(e.length+t.length," 个文件"))},handleRemove:function(e,t){this.fileTemp=null},importfxx:function(e){this.file=e;var t=!1,r=this.file,n=new FileReader;FileReader.prototype.readAsBinaryString=function(e){var t,r,n="",o=!1,i=new FileReader;i.onload=function(e){for(var s=new Uint8Array(i.result),l=s.byteLength,c=0;c<l;c++)n+=String.fromCharCode(s[c]);var u=a("1146");t=o?u.read(btoa(fixdata(n)),{type:"base64"}):u.read(n,{type:"binary"}),r=u.utils.sheet_to_json(t.Sheets[t.SheetNames[0]]),console.log(r),this.da=Object(d["a"])(r);var p=[];return this.da.map((function(e){var t={robotCount:e["机器人数量"],type:e["发货进度"],applicantName:e["申请人"],factoryName:e["发货工厂"],consignorName:e["发货人"],receivingName:e["收货景区"],consigneeName:e["收货人"],consigneePhone:e["收货人电话"],receiningAddress:e["收货地址"],consignmentDate:e["发货时间"]};p.push(t)})),this.tableData=p,p},i.readAsArrayBuffer(e)},t?n.readAsArrayBuffer(r):n.readAsBinaryString(r)},handleImportExceed:function(e){this.$refs.uploadImport.clearFiles(),this.$refs.uploadImport.submit(e)},handleImportChange:function(e,t){console.log(e),this.importE(e.raw)}},components:{StockList:w},computed:{tableContentValue:function(){return function(e,t){var a="";return"type"==t?1==e[t]?a="待发货":2==e[t]?a="已发货":3==e[t]?a="已收货":4==e[t]&&(a="已关闭"):"inStockReason"==t?1==e[t]?a="生产":2==e[t]&&(a="销售"):"inStockType"==t?1==e[t]?a="采购":2==e[t]&&(a="回收"):a=e[t]||"无",a}}}},x=F,S=(a("90d2"),Object(b["a"])(x,l,c,!1,null,"033ea52c",null)),k=S.exports,N={name:"App",components:{broadcastHuntLog:k}},R=N,C=(a("5c0b"),Object(b["a"])(R,i,s,!1,null,null,null)),O=C.exports,$=a("2f62");o["default"].use($["a"]);var L=new $["a"].Store({state:{},mutations:{},actions:{},modules:{}}),T=a("bc3a"),D=a.n(T),I=a("4328"),E=a.n(I),_=a("313e");o["default"].use(n.a),D.a.defaults.withCredentials=!0,o["default"].prototype.$http=D.a,o["default"].prototype.$qs=E.a,o["default"].config.productionTip=!1,o["default"].prototype.$echarts=_,Date.prototype.format=function(e){var t={"M+":this.getMonth()+1,"d+":this.getDate(),"h+":this.getHours(),"m+":this.getMinutes(),"s+":this.getSeconds(),"q+":Math.floor((this.getMonth()+3)/3),S:this.getMilliseconds()};for(var a in/(y+)/.test(e)&&(e=e.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length))),t){var r=t[a];new RegExp("("+a+")").test(e)&&(e=e.replace(RegExp.$1,1==RegExp.$1.length?r:("00"+r).substr((""+r).length)))}return e},new o["default"]({store:L,render:function(e){return e(O)}}).$mount("#app")},"5c0b":function(e,t,a){"use strict";a("9c0c")},"90d2":function(e,t,a){"use strict";a("1501")},"9c0c":function(e,t,a){}});
//# sourceMappingURL=app.52704cdc.js.map