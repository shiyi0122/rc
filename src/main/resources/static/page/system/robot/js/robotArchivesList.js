layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
	var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotArchivesList',
        url : '/system/robot/getRobotList',
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
        id : "robotArchivesListTable",
        cols : [[
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '目前所在地', minWidth:100, align:"center"},
            {field: 'robotCodeSim', title: '机器人ICCID', minWidth:100, align:"center"},
            {field: 'robotRunState', title: '运行状态', minWidth:100, align:"center",templet:function(d){
                    if(d.robotRunState == "10"){
                        return "闲置";
                    }else if(d.robotRunState == "20"){
                        return "用户解锁";
                    }else if(d.robotRunState == "30"){
                        return "用户临时锁定";
                    }else if(d.robotRunState == "40"){
                        return "管理员解锁";
                    }else if(d.robotRunState == "50"){
                        return "管理员锁定";
                    }else if(d.robotRunState == "60"){
                        return "自检故障报警";
                    }else if(d.robotRunState == "70"){
                        return "扫码解锁中";
                    }else if(d.robotRunState == "80"){
                        return "运营人员钥匙解锁";
                    }else if(d.robotRunState == "90"){
	                    return "运营人员维护";
	                }else if(d.robotRunState == "100"){
                        return  "禁区锁定";
                    }
                }},
            {field: 'robotVersionNumber', title: '机器人型号', minWidth:100, align:"center"},
            {field: 'robotPowerState', title: '电量状态', minWidth:100, align:"center",templet:function(d){return d.robotPowerState+'%'}},
            {field: 'robotFaultState', title: '故障状态', minWidth:100, align:"center",templet:function(d){
                    if(d.robotFaultState == "10"){
                        return "正常";
                    }else if(d.robotFaultState == "20"){
                        return "报修";
                    }else if(d.robotFaultState == "30"){
                        return "故障";
                    }
                }},
            // {field: 'robotRemarks', title: '机器人备注', minWidth:100, align:"center"},
			{field: 'robotBatchNumber', title: '机器人批次号', minWidth:100, align:"center"},
			{field: 'autoUpdateState', title: '升级状态', minWidth:100, align:"center",templet:function (d) {
                    if (d.autoUpdateState == "0"){
                        return "下载中";
                    }else if (d.autoUpdateState == "1"){
                        return "下载完成";
                    }else if (d.autoUpdateState == "2"){
                        return "下载失败";
                    }else if (d.autoUpdateState == "3"){
                        return "安装中";
                    }else if (d.autoUpdateState == "4"){
                        return "安装完成";
                    }else if (d.autoUpdateState == "5"){
                        return "安装失败";
                    }
                }},
            // {field: 'robotBluetooth', title: '机器人蓝牙编号', minWidth:100, align:"center"},
            // {field: 'clientVersion', title: '机器人PAD版本号', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotArchivesListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotArchivesListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                robotCode: $(".robotCodeVal").val(),  //搜索的关键字
                scenicSpotId: $(".scenicSpotId").val(),  //搜索的关键字
                robotCodeSim : $(".robotCodeSimVal").val()
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
    table.on('tool(robotArchivesList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEM_ROBOT_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRobot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_ROBOT_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robot/delRobot", {robotId : data.robotId}, function (data) {}, false,"GET","robotArchivesListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddRole').click(function(){
        window.resources("SYSTEM_ROBOT_INSERT", function (e) {
            if (e.state == "200") {
                openAddRobot();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })
    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYSTEM_ROBOT_DOWNLOADEXCEL", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobot(edit) {
        layer.open({
            type : 2,
            title: '添加机器人',
            offset: '10%',
            area: ['800px', '460px'], //宽高
            content: '/page/system/robot/html/robotAdd.html',
            tableId: '#robotList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type : 'POST',
                    url : '/system/robot/getRobotId',
                    dataType : 'json',
                    success:function (data) {
                        if (data.state == "200"){
                            body.find(".robotCode").val(data.data.robotCode);//机器人编号
                            form.render();
                        }else if(data.state == "400"){
                            top.layer.close(dex);
                            layer.msg(data.msg);
                        }
                    }
                })
            }
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditRobot(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改机器人',
                offset: '10%',
                area: ['800px', '460px'], //宽高
                content: '/page/system/robot/html/robotEdit.html',
                tableId: '#robotList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".robotId").val(edit.robotId);
                        body.find(".robotCode").val(edit.robotCode);
                        body.find(".robotCodeSim").val(edit.robotCodeSim);
                        body.find(".robotVersionNumber").val(edit.robotVersionNumber);
                        body.find(".robotRemarks").val(edit.robotRemarks);
                        body.find(".scenicspotNames").val(edit.scenicSpotId);
                        body.find(".robotBatchNumber").val(edit.robotBatchNumber);
                        body.find(".robotBluetooth").val(edit.robotBluetooth);
                        form.render();
                    }
                }
            });
        },500);
    }


    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        var robotCodeVal = $(".robotCodeVal").val();
        var scenicSpotId = $(".scenicSpotId").val();
        window.location.href = "/system/robot/uploadExcelArchivesRobot?robotCode=" + robotCodeVal +"&scenicSpotId="+scenicSpotId;
    }

	//导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/system/robot/upload'
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
                    layui.table.reload("robotArchivesListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
					layer.closeAll();
                },500);
            }
        }
    });
	//导入批次号
    upload.render({
        elem: '#importBatchNumber'
        ,url: '/system/robot/importBatchNumber'
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
                    layui.table.reload("robotArchivesListTable");
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
