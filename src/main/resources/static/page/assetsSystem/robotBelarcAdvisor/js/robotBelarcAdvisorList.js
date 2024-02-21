layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#robotBelarcAdvisorList',
        url : '/system/robotBelarcAdvisor/getRobotBelarcAdvisorList',
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
        id : "robotBelarcAdvisorListTable",
        cols : [[
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center",fixed:"left"},
            {field: 'scenicSpotName', title: '目前所在地', minWidth:100, align:"center"},
            {field: 'robotModel', title: '机器人型号', minWidth:100, align:"center"},
            {field: 'clientVersion', title: 'PAD版本号', minWidth:100, align:"center"},
            {field: 'robotCodeSim', title: '机器人ICCID', minWidth:100, align:"center"},
            // {field: 'masterControlModel', title: '主控型号', minWidth:100, align:"center"},
            {field: 'masterControlFirmwareVersion', title: '主控版本号', minWidth:100, align:"center"},
            // {field: 'masterControlId', title: '主控ID', minWidth:100, align:"center"},
            // {field: 'padVersion', title: 'PAD型号', minWidth:100, align:"center"},
            // {field: 'padFirmwareVersion', title: 'PAD固件版本号', minWidth:100, align:"center"},
            // {field: 'padManufactor', title: 'PAD厂家', minWidth:100, align:"center"},
            // {field: 'meid', title: 'MEID', minWidth:100, align:"center"},
            // {field: 'driveModel', title: '驱动器型号', minWidth:100, align:"center"},
            {field: 'driveFirmwareVersion', title: '驱动器版本号', minWidth:100, align:"center"},
            // {field: 'driverManufacturer', title: '驱动器厂家', minWidth:100, align:"center"},
            // {field: 'driveId', title: '驱动器ID', minWidth:100, align:"center"},
            {field: 'rangingModularVersion', title: '避障模块版本号', minWidth:100, align:"center"},
            // {field: 'rangingModularModel', title: '避障模块型号', minWidth:100, align:"center"},
            // {field: 'rangingModularManufacturer', title: '避障模块厂家', minWidth:100, align:"center"},
            // {field: 'rangingModularId', title: '避障模块ID', minWidth:100, align:"center"},
            {field: 'motorModel', title: '电机型号', minWidth:100, align:"center"},
            // {field: 'motorManufacturer', title: '电机厂家', minWidth:100, align:"center"},
            // {field: 'motorId', title: '电机ID', minWidth:100, align:"center"},
            {field: 'batteryModel', title: '电池型号', minWidth:100, align:"center"},
            // {field: 'batteryManufacturer', title: '电池厂家', minWidth:100, align:"center"},
            // {field: 'batteryId', title: '电池ID', minWidth:100, align:"center"},
            {field: 'robotPower', title: '机器人功率', minWidth:100, align:"center"},
            {field: 'chargerModel', title: '充电器型号', minWidth:100, align:"center"},
            // {field: 'chargerManufacturer', title: '充电器厂家', minWidth:100, align:"center"},
            // {field: 'chargerId', title: '充电器ID', minWidth:100, align:"center"},

            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotBelarcAdvisorListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotBelarcAdvisorListTable",{
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
    table.on('tool(robotBelarcAdvisorList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_BELARC_ADVISOR_EDIT", function (e) {
                if (e.state == "200") {
                    openEditRobotBelarcAdvisor(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_BELARC_ADVISOR_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotBelarcAdvisor/delRobotBelarcAdvisor", {robotBelarcAdvisorId : data.robotBelarcAdvisorId}, function (data) {}, false,"GET","robotBelarcAdvisorListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYS_ROBOT_BELARC_ADVISOR_DOWNLOADEXCEL", function (e) {
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
        var robotCodeVal = $(".robotCodeVal").val();
        var scenicSpotId = $(".scenicSpotId").val();
        var robotCodeSim = $(".robotCodeSimVal").val();
        window.location.href = "/system/robotBelarcAdvisor/uploadExcelRobotBelarcAdvisor?robotCode=" + robotCodeVal +"&scenicSpotId=" + scenicSpotId + "&robotCodeSim=" + robotCodeSim;
    }

    /**
     * 弹出编辑框
     */
    function openEditRobotBelarcAdvisor(edit) {
        layer.open({
            type : 2,
            title: '编辑机器人软硬件信息',
            offset: '10%',
            area: ['800px', '80%'], //宽高
            content: '/page/assetsSystem/robotBelarcAdvisor/html/robotBelarcAdvisorEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".robotId").val(edit.robotId);
                    body.find(".robotCode").val(edit.robotCode);
                    body.find(".masterControlModel").val(edit.masterControlModel);
                    body.find(".masterControlFirmwareVersion").val(edit.masterControlFirmwareVersion);
                    body.find(".masterControlId").val(edit.masterControlId);
                    body.find(".padVersion").val(edit.padVersion);
                    body.find(".padFirmwareVersion").val(edit.padFirmwareVersion);
                    body.find(".padManufactor").val(edit.padManufactor);
                    body.find(".meid").val(edit.meid);
                    body.find(".driveModel").val(edit.driveModel);
                    body.find(".driveFirmwareVersion").val(edit.driveFirmwareVersion);
                    body.find(".driverManufacturer").val(edit.driverManufacturer);
                    body.find(".driveId").val(edit.driveId);
                    body.find(".motorModel").val(edit.motorModel);
                    body.find(".motorManufacturer").val(edit.motorManufacturer);
                    body.find(".motorId").val(edit.motorId);
                    body.find(".batteryModel").val(edit.batteryModel);
                    body.find(".batteryManufacturer").val(edit.batteryManufacturer);
                    body.find(".batteryId").val(edit.batteryId);
                    body.find(".robotPower").val(edit.robotPower);
                    body.find(".chargerModel").val(edit.chargerModel);
                    body.find(".chargerManufacturer").val(edit.chargerManufacturer);
                    body.find(".chargerId").val(edit.chargerId);
                    body.find(".rangingModularModel").val(edit.rangingModularModel);
                    body.find(".rangingModularVersion").val(edit.rangingModularVersion);
                    body.find(".rangingModularManufacturer").val(edit.rangingModularManufacturer);
                    body.find(".rangingModularId").val(edit.rangingModularId);
                    form.render();
                }
            }
        });
    };

    //导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/system/robotBelarcAdvisor/upload'
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
