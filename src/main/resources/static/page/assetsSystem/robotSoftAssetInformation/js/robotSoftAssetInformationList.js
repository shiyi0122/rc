layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#robotSoftAssetInformationList',
        url : '/system/robotSoftAssetInformation/getRobotSoftAssetInformationList',
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
        id : "robotSoftAssetInformationListTable",
        cols : [[
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'clientVersion', title: '机器人PAD版本号', minWidth:100, align:"center"},
            {field: 'robotModel', title: '机器人型号', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'robotBatchNumber', title: '生产批次', minWidth:100, align:"center"},
            {field: 'dateProduction', title: '出厂日期', minWidth:100, align:"center"},
            {field: 'batteryWarrantyDeadline', title: '电池质保截止日期', minWidth:100, align:"center"},
            {field: 'robotWarrantyDeadline', title: '机器人质保截止日期', minWidth:100, align:"center"},
            {field: 'updateDate', title: '更新时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:225, templet:'#robotSoftAssetInformationListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotSoftAssetInformationListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                robotCode : $(".robotCodeVal").val(),
                robotModel : $(".robotModelVal").val(),
                scenicSpotId : $(".scenicSpotId").val()
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
    table.on('tool(robotSoftAssetInformationList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_SOFT_ASSET_INFORMATION_EDIT", function (e) {
                if (e.state == "200") {
                    openEditRobotSoftAssetInformation(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_SOFT_ASSET_INFORMATION_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotSoftAssetInformation/delRobotSoftAssetInformation", {softAssetInformationId : data.softAssetInformationId}, function (data) {}, false,"GET","robotSoftAssetInformationListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        } else if (layEvent == 'showTrail'){
            layer.open({
                type : 2,
                title: '调运轨迹',
                offset: '10%',
                area: ['40%', '60%'], //宽高
                content: '/page/assetsSystem/robotSoftAssetInformation/trail/index.html?robotCode=' + data.robotCode
            });
        }
    });
    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYS_ROBOT_SOFT_ASSET_INFORMATION_DOWNLOADEXCEL", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        var robotCode = $(".robotCodeVal").val();
        var robotModel = $(".robotModelVal").val();
        var scenicSpotId = $(".scenicSpotId").val();
        window.location.href = "/system/robotSoftAssetInformation/uploadExcelRobotSoftAssetInformation?robotCode=" + robotCode + "&robotModel=" + robotModel + "&scenicSpotId=" + scenicSpotId;
    }

    /**
     * 弹出编辑框
     */
    function openEditRobotSoftAssetInformation(edit) {
        layer.open({
            type : 2,
            title: '编辑机器人软资产信息',
            offset: '10%',
            area: ['800px', '450px'], //宽高
            content: '/page/assetsSystem/robotSoftAssetInformation/html/robotSoftAssetInformationEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".robotCode").val(edit.robotCode);
                    body.find(".robotId").val(edit.robotId);
                    body.find(".softAssetInformationId").val(edit.softAssetInformationId);
                    body.find(".dateProduction").val(edit.dateProduction);
                    body.find(".batteryWarrantyDeadline").val(edit.batteryWarrantyDeadline);
                    body.find(".robotWarrantyDeadline").val(edit.robotWarrantyDeadline);
                    form.render();
                }
            }
        });
    };

    //导入Excel表
    upload.render({
        elem: '#importExcel',
         // url: '/system/robotSoftAssetInformation/importExcelSoft'
         url: '/system/robotPeripheral/importExcel'
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
                    layui.table.reload("robotSoftAssetInformationListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
                    layer.closeAll();
                },500);
            }
        }
    });

    //点击添加按钮
    $('#btnAdd').click(function(){
        window.resources("SYSTEM_ROBOT_INSERT", function (e) {
            if (e.state == "200") {
                openAddRobotObstacleAvoidanceModule();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobotObstacleAvoidanceModule() {
        layer.open({
            type : 2,
            title: '添加机器人避障模块信息',
            offset: '10%',
            area: ['800px', '550px'], //宽高
            content: '/page/assetsSystem/robotObstacleAvoidanceModule/html/robotSoftAssetInformationAdd.html'
        });
    };

})
