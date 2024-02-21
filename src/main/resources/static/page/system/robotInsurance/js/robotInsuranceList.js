layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
	var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotInsuranceList',
        url : '/system/robotInsurance/getRobotInsuranceList',
		toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "robotInsuranceListTable",
        cols : [[
			{type:'checkbox'},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'insuranceNumber', title: '保险单号', minWidth:100, align:"center"},
            {field: 'emergencyCall', title: '出险电话', minWidth:100, align:"center"},
            {field: 'insuranceType', title: '参险种类', minWidth:100, align:"center",templet:function(d){
                    if(d.insuranceType == "1"){
                        return "三者保险";
                    }else{
						return "暂无";
					}
                }},
			{field: 'insuranceCompany', title: '参险单位', minWidth:100, align:"center",templet:function(d){
                    if(d.insuranceCompany == "1"){
                        return "北京九星智元科技有限公司";
                    }else if(d.insuranceCompany == "2"){
                        return "常州九星人工智能科技有限公司";
                    }else if(d.insuranceCompany == "3"){
                        return "平遥九星科技有限公司";
                    }else if(d.insuranceCompany == "4"){
                        return "上海游伴科技有限责任公司";
                    }else{
						return "暂无";
					}
                }},
			{field: 'insuranceUnit', title: '保险公司', minWidth:100, align:"center",templet:function(d){
                    if(d.insuranceUnit == "1"){
                        return "平安保险";
                    }else if(d.insuranceUnit == "2"){
                        return "中国人民保险";
                    }else if(d.insuranceUnit == "3"){
                        return "中国人寿保险";
                    }else{
						return "平安保险";
					}
                }},
            {field: 'insureStartTime', title: '投保开始日期', minWidth:100, align:"center"},
            {field: 'insureEndTime', title: '投保结束日期', minWidth:100, align:"center"},
            {field: 'remainingTime', title: '剩余天数', minWidth:100, align:"center"},
            {field: 'insureUrl', title: '保单地址', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotInsuranceListBar',fixed:"right",align:"center"}
        ]]
    });

	//头工具栏事件
	var ida = [];
	  table.on('toolbar(robotInsuranceList)', function(obj){
		  var checkStatus = table.checkStatus(obj.config.id);
		  switch(obj.event){
			  case 'getCheckData':
			  var data = checkStatus.data;
			  var ids = [];
			  for(var i=0;i<data.length;i++){
			  	ids.push(data[i].robotCode)
			  }
			  ids = ids.join(',');
			  ida = ids;
			  $("#importPdf").click();
			  break;
		  };
	  });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotInsuranceListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                robotCode: $(".robotCodeVal").val(),
                scenicSpotId: $(".scenicSpotId").val()
            }

        })
    });

    /**
     * 模糊查询
     */
    $("#btnExpire").on("click",function(){
        table.reload("robotInsuranceListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                insureState: "1"
            }

        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(robotInsuranceList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_INSURANCE_EDIT", function (e) {
                if (e.state == "200") {
                    openEditRobotInsurance(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'download'){ //下载PDF
            window.resources("SYS_ROBOT_INSURANCE_DOWNLOAD", function (e) {
                if (e.state == "200") {
                    window.location.href = "/system/robotInsurance/download?fileName=" + data.insureUrl
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditRobotInsurance(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改机器人保险',
                offset: '10%',
                area: ['800px', '80%'], //宽高
                content: '/page/system/robotInsurance/html/robotInsuranceAdd.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".robotId").val(edit.robotId);
                        body.find(".insuranceId").val(edit.insuranceId);
                        body.find(".robotCode").val(edit.robotCode);
                        body.find(".insuranceNumber").val(edit.insuranceNumber);
                        body.find(".emergencyCall").val(edit.emergencyCall);
                        body.find(".insuranceType select").val(edit.insuranceType);
                        body.find(".insuranceCompany select").val(edit.insuranceCompany);
                        body.find(".insuranceUnit select").val(edit.insuranceUnit);
                        body.find(".insureStartTime").val(edit.insureStartTime);
                        body.find(".insureEndTime").val(edit.insureEndTime);
                        body.find(".accidentProcess").val(edit.accidentProcess);
                        form.render();
                    }
                }
            });
        },500);
    }

 	//导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/system/robotInsurance/upload'
        ,accept: 'file' //普通文件
        ,exts: 'xls|xlsx' //只允许上传Excel文件
		,before:function(obj){
			layer.load(); //上传loading
		}
        ,done: function(res){
            if (res.state == 200){
                setTimeout(function(){
					layer.alert(res.msg,function(){
                    	layer.closeAll();//关闭所有弹框
                    });
                    layui.table.reload("robotInsuranceListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
					layer.closeAll();
                },500);
            }
        }
    });

    //点击导出EXCEL按钮
    $('#exportExcel').click(function(){
        window.resources("SYS_ROBOT_INSURANCE_DOWNLOAD", function (e) {
            if (e.state == "200") {
                exportExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    });

    /**
     * 导出EXCEL表
     */
    function exportExcel(){
        var scenicSpotId = $(".scenicSpotId").val();
        var robotCodeVal = $(".robotCodeVal").val();

        window.location.href = "/system/robotInsurance/uploadRobotInsuranceExcel?scenicSpotId="+scenicSpotId+"&robotCode="+robotCodeVal;
    }

	upload.render({
		elem: '#importPdf'
        ,url: '/system/robotInsurance/uploadPdf'
        ,acceptMime: '.pdf'
        ,exts: 'pdf'
		,before:function(obj){
			layer.load(); //上传loading
			this.data={"ids":ida};
		}
        ,done: function(res){
            if (res.state == 200){
                setTimeout(function(){
					layer.alert(res.msg,function(){
                    	layer.closeAll();//关闭所有弹框
                    });
                    layui.table.reload("robotInsuranceListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
					layer.closeAll();
                },500);
            }
        }
    });

})
