layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;
    var robotCode = new URL(location.href).searchParams.get("robotCode")
    document.querySelector(".robotCodeVal").value = robotCode

    table.render({
        elem: '#robotErrorRecordsList',
        url : '/system/robotErrorRecords/getRobotErrorRecordsList',
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
        id : "robotErrorRecordsListTable",
        where: {
            robotCode
        },
        cols : [[
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center",fixed:"left"},
            {field: 'errorRecordsModel', title: '机器人型号', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'errorRecordsNo', title: '故障单号', minWidth:100, align:"center"},
            {field: 'errorRecordsAffect', title: '是否影响使用', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsAffect == "0"){
                        return "不影响使用";
                    }else if(d.errorRecordsAffect == "1"){
                        return "影响使用";
                    }else{
                        return "空"
                    }
                }},
            {field: 'errorRecordsType', title: '故障类型', minWidth:100, align:"center"},
            {field: 'errorRecordsName', title: '故障名称', minWidth:100, align:"center"},
            {field: 'errorRecordsDescription', title: '故障描述', minWidth:100, align:"center"},
            {field: 'errorRecordsPic', title: '故障图片', minWidth:100, align:"center"},
            {field: 'errorRecordsPart', title: '是否需要发配件', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsPart == "0"){
                        return "不需要";
                    }else if(d.errorRecordsPart == "1"){
                        return "需要";
                    }else{
                        return "空"
                    }
                }},
            {field: 'errorRecordsOldId', title: '配件旧id', minWidth:100, align:"center"},
            {field: 'errorRecordsNewId', title: '配件新id', minWidth:100, align:"center"},
            {field: 'errorRecordsAddress', title: '收件地址', minWidth:100, align:"center"},
            {field: 'errorRecordsSend', title: '配件是否已发送', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsSend == "0"){
                        return "未发送";
                    }else if(d.errorRecordsSend == "1"){
                        return "已发送";
                    }else{
                        return "空"
                    }
                }},
            {field: 'errorRecordsReceive', title: '配件是否收到', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsPart == "0"){
                        return "未收到";
                    }else if(d.errorRecordsPart == "1"){
                        return "已收到";
                    }else{
                        return "空"
                    }
                }},
            {field: 'errorRecordsReportSource', title: '上报来源', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsReportSource == "1"){
                        return "设备自检上报故障";
                    }else if(d.errorRecordsReportSource == "2"){
                        return "管理员APP订单页上报损坏";
                    }else if(d.errorRecordsReportSource == "3"){
                        return "管理员APP上报故障";
                    }else if(d.errorRecordsReportSource == "4"){
                        return "4用户在小程序页上报故障";
                    }else{
                        return "空"
                    }
                }},
            {field: 'errorRecordsDate', title: '故障上报日期', minWidth:100, align:"center"},
            {field: 'errorRecordsPersonnel', title: '上报人员', minWidth:100, align:"center"},
            {field: 'errorRecordsTel', title: '上报电话', minWidth:100, align:"center"},
            {field: 'errorRecordsOrderNo', title: '关联订单', minWidth:100, align:"center"},
            {field: 'errorRecordsQuality', title: '是否质保', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsQuality == "0"){
                        return "不质保或已过质保期";
                    }else if(d.errorRecordsQuality == "1"){
                        return "在质保期内";
                    }else{
                        return "空"
                    }
                }},
            {field: 'improperOperation', title: '是否运营不当造成', minWidth:100, align:"center",templet:function(d){
                    if(d.improperOperation == "0"){
                        return "是";
                    }else if(d.improperOperation == "1"){
                        return "否";
                    }else{
                        return "否"
                    }
                }},
            {field: 'inventoryNumber', title: '出库单号', minWidth:100, align:"center"},
            {field: 'errorRecordsApprove', title: '审核结果', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsApprove == "0"){
                        return "不通过";
                    }else if(d.errorRecordsApprove == "2" || d.errorRecordsApprove == "1"){
                        return "通过";
                    }else{
                        return "审批中"
                    }
                }},
            // {field: 'errorRecordsAppRover', title: '审核记录', minWidth:100, align:"center"},
            {field: 'errorRecordsReplace', title: '已更换配件', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsReplace == "0"){
                        return "未更换配件";
                    }else if(d.errorRecordsReplace == "1"){
                        return "已更换配件";
                    }else{
                        return "空"
                    }
                }},
            {field: 'errorRecordsSource', title: '配件来源', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsSource == "1"){
                        return "新收到的配件";
                    }else if(d.errorRecordsSource == "2"){
                        return "园区库房配件";
                    }else{
                        return "空"
                    }
                }},
            {field: 'errorRecordsStatus', title: '维修结果', minWidth:100, align:"center",templet:function(d){
                    if(d.errorRecordsStatus == "1"){
                        return "未维修已派单";
                    }else if(d.errorRecordsStatus == "2"){
                        return "已修好，远超协助";
                    }else if(d.errorRecordsStatus == "3"){
                        return "已判断问题，需要配件";
                    }else if(d.errorRecordsStatus == "4"){
                        return "未修好，需要售后现场解决";
                    }else if(d.errorRecordsStatus == "5"){
                        return "已修好，售后现场解决";
                    }else if(d.errorRecordsStatus == "6"){
                        return "已确认修好";
                    }else if(d.errorRecordsStatus == "7"){
                        return "已关闭";
                    }else{
                        return "空"
                    }
                }},

            {field: 'errorRecordsPartPrice', title: '配件费用/元', minWidth:100, align:"center"},
            {field: 'errorRecordsPartBearer', title: '配件费用承担方', minWidth:100, align:"center"},
            {field: 'errorRecordsUpkeepCost', title: '维修费用/元', minWidth:100, align:"center"},
            {field: 'errorRecordsUpkeepBearer', title: '维修费用承担方', minWidth:100, align:"center"},
            {field: 'errorRecordsRemark', title: '备注', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotErrorRecordsListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
            table.reload("robotErrorRecordsListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                robotCode : $(".robotCodeVal").val(),
                errorRecordsName : $(".errorRecordsName").val(),
                scenicSpotId : $(".scenicSpotId").val(),
                startTime : $(".startTime").val(),
                endTime : $(".endTime").val(),
                errorRecordsStatus : $(".errorRecordsStatus").val()

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
    table.on('tool(robotErrorRecordsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'distributeLeaflets'){ //派单
            window.resources("SYSTEM_ROBOT_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRobotErrorRecords(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === 'edit'){//编辑
            window.resources("SYSTEM_ROBOT_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditErrorRecords(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent ==='accessories'){//配件详情
            window.resources("SYSTEM_ROBOT_UPDATE",function (e) {
                if (e.state =="200"){
                    openAccessoriesErrorRecords(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            },false,"GET")
        }else if (layEvent ==='delete'){//删除
            window.resources("SYSTEM_ROBOT_UPDATE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotErrorRecords/delErrorRecords", {errorRecordsId : data.errorRecordsId}, function (data) {}, false,"GET","robotErrorRecordsListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent ==='approvalResults'){//审批
            window.resources("SYSTEM_ROBOT_UPDATE",function (e) {
                if (e.state =="200"){
                    approvalResults(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            },false,"GET")
        }
    });
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
     * 导出EXCEL表
     */
    function downloadExcel(){
        var obstacleAvoidanceId = $(".obstacleAvoidanceId").val();
        window.location.href = "/system/robotObstacleAvoidanceModule/uploadExcelRobotObstacleAvoidanceModule?obstacleAvoidanceId=" + obstacleAvoidanceId;
    }

    /**
     * 弹出派单框
     */
    function openEditRobotErrorRecords(edit) {
        layer.open({
            type : 2,
            title: '派单',
            offset: '10%',
            area: ['800px', '250px'], //宽高
            content: '/page/assetsSystem/robotErrorRecords/html/robotErrorRecordsEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".userIds").val(edit.userId);
                    body.find(".errorRecordsId").val(edit.errorRecordsId);
                    body.find(".robotCode").val(edit.robotCode);
                    body.find(".scenicSpotName").val(edit.scenicSpotName);
                    body.find(".errorRecordsName").val(edit.errorRecordsName);
                    body.find(".errorRecordsModel").val(edit.errorRecordsModel);
                    form.render();
                }
            }
        });
    };


    /**
     * 弹出审批框
     */
    function approvalResults(edit) {
        layer.open({
            type : 2,
            title: '审批',
            offset: '10%',
            area: ['800px', '250px'], //宽高
            content: '/page/assetsSystem/robotErrorRecords/html/robotErrorApprovalResults.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".userIds").val(edit.userId);
                    body.find(".errorRecordsId").val(edit.errorRecordsId);
                    body.find(".robotCode").val(edit.robotCode);
                    body.find(".scenicSpotName").val(edit.scenicSpotName);
                    body.find(".errorRecordsName").val(edit.errorRecordsName);
                    body.find(".errorRecordsModel").val(edit.errorRecordsModel);
                    form.render();
                }
            }
        });
    };

    /**
     * 弹出编辑框
     */
    function openEditErrorRecords(edit) {
        layer.open({
            type : 2,
            title: '编辑',
            offset: '10%',
            area: ['800px', '550px'], //宽高
            content: '/page/assetsSystem/robotErrorRecords/html/errorRecordsEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".errorRecordsId").val(edit.errorRecordsId);
                    body.find(".errorRecordsSend select").val(edit.errorRecordsSend);
                    body.find(".errorRecordsReceive select").val(edit.errorRecordsReceive);
                    body.find(".errorRecordsPartBearer").val(edit.errorRecordsPartBearer);
                    body.find(".errorRecordsUpkeepBearer").val(edit.errorRecordsUpkeepBearer);
                    body.find(".errorRecordsRemark").val(edit.errorRecordsRemark);
                    form.render();
                }
            }
        });
    };

    function openAccessoriesErrorRecords(edit) {
        layer.open({
            type : 2,
            title: '配件详情',
            offset: '10%',
            area: ['800px', '550px'], //宽高
            content: '/page/assetsSystem/robotErrorRecords/html/robotPartsManagement.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    //console.log("222222")
                    console.log(edit.errorRecordsId)
                    body.find(".errorRecordsId").val(edit.errorRecordsId);
                }
            }
        });
    };


    //点击添加按钮
    $('#btnAdd').click(function(){
        window.resources("SYSTEM_ROBOT_INSERT", function (e) {
            if (e.state == "200") {
                openAddRobotPartsManagement();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobotPartsManagement() {
        layer.open({
            type : 2,
            title: '添加机器人故障记录',
            offset: '10%',
            area: ['800px', '550px'], //宽高
            content: '/page/assetsSystem/robotErrorRecords/html/errorRecordsAdd.html'
        });
    };



})
