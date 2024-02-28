layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotBasicArchivesList',
        url : '/system/scenicSpot/getScenicSpotList',
        cellMinWidth : 150,
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
        id : "scenicSpotBasicArchivesListTable",
        cols : [[
            {field: 'scenicSpotId', title: '景区ID', minWidth:100, align:"center"},
            //{field: 'scenicSpotAscriptionName', title: '景区归属', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'scenicSpotContact', title: '联系人', minWidth:100, align:"center"},
            {field: 'scenicSpotPhone', title: '联系人电话', minWidth:100, align:"center"},
            {field: 'scenicSpotEmail', title: '景区邮箱', minWidth:100, align:"center"},
            {field: 'scenicSpotAddres', title: '景区地址', minWidth:100, align:"center"},
            {field: 'scenicSpotPostalCode', title: '景区邮编', minWidth:100, align:"center"},
            {field: 'scenicSpotRegion', title: '景区归属地', minWidth:100, align:"center"},
            {field: 'companyName', title: '景区归属公司', minWidth:100, align:"center"},
            {field: 'robotWakeupWords', title: '景区状态', minWidth:100, align:"center",templet:function(d){
                    if(d.robotWakeupWords == "1"){
                        return "已运营景区";
                    }else if(d.robotWakeupWords == "2"){
                        return "测试景区";
                    }else if(d.robotWakeupWords == "3"){
                        return "预运营景区";
                    }else if(d.robotWakeupWords == "4"){
                        return "停运景区"
                    }
                }},
            {field: 'businessStatus', title: '营业状态', minWidth:100, align:"center",templet:function(d){
                    if(d.businessStatus == "1"){
                        return "营业中";
                    }else if(d.businessStatus == "2"){
                        return "休息中";
                    }else{
                        return "该景区暂未设置上下班时间"
                    }
                }},
            // {field: 'scenicSpotUrlName', title: '景区图片', minWidth:100, align:"center"},
            {field: 'smallNetwork', title: '网络状态', minWidth:100, align:"center",templet:function(d){
                    if(d.smallNetwork == "1"){
                        return "只走网络,不走蓝牙和验证码";
                    }else if(d.smallNetwork == "2"){
                        return "先走网络,不通走蓝牙，再不通走验证码";
                    }else if(d.smallNetwork == "3"){
                        return "先走网络,不通直接走验证码";
                    }else if(d.smallNetwork == "4"){
                        return "先走网络,不通蓝牙和验证码一起走";
                    }else if(d.smallNetwork == "5"){
                        return "网络、蓝牙、验证码同时走,那个可以走通就走哪个,不走网络";
                    }else if(d.smallNetwork == "6"){
                        return "直接走蓝牙,不走网络和验证码";
                    }else if(d.smallNetwork == "7"){
                        return "直接走验证码,不走网络和蓝牙";
                    }else if(d.smallNetwork == "8"){
                        return "不走网络,直接走蓝牙,不通再走验证码";
                    }else if(d.smallNetwork == "9"){
                        return "不走网络,蓝牙、验证码同时走";
                    }
                }},
            {field: 'heat', title: '景区热度', minWidth:100, align:"center"},
            {field: 'treasureCooldownTime', title: '宝箱冷却时间(分)', minWidth:100, align:"center"},
            {field: 'cooldownTime', title: '扫码启动后所有宝箱的冷却时间(秒)', minWidth:100, align:"center"},
            {field: 'autoUpdateMonitor', title: '监听状态', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#scenicSpotBasicArchivesListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据景区名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotBasicArchivesListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotName: $(".scenicSpotNameVal").val(), //搜索的关键字
                robotWakeupWords: $(".robotWakeupWords").val() //搜索的关键字
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
    table.on('tool(scenicSpotBasicArchivesList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SCENIC_SPOT_BASIC_ARCHIVES_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditScenicSpot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SCENIC_SPOT_BASIC_ARCHIVES_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/scenicSpot/delScenicSpot", {scenicSpotId : data.scenicSpotId}, function (data) {}, false,"GET","scenicSpotBasicArchivesListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "updateScenicSpotState"){ //修改状态
            window.resources("SCENIC_SPOT_BASIC_ARCHIVES_UPDATE_STATE", function (e) {
                if (e.state == "200") {
                    updateScenicSpotState(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "scenicSpotPicture"){
            window.resources("SCENIC_SPOT_BASIC_ARCHIVES_CAPPRICE", function (e) {
                if (e.state == "200") {
                    scenicSpotPicture(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddScenicSpot').click(function(){
        window.resources("SCENIC_SPOT_BASIC_ARCHIVES_INSERT", function (e) {
            if (e.state == "200") {
                openAddScenicSpot();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框 
     */
    function openAddScenicSpot() {
        layer.open({
            type : 2,
            title: '添加景区基本档案',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/system/scenicSpot/html/scenicSpotBasicArchivesAdd.html',
            tableId: '#scenicSpotList'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditScenicSpot(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        // console.log(edit.scenicSpotAscriptionId);
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改景区基本档案',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/system/scenicSpot/html/scenicSpotBasicArchivesEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        console.log(edit);
                        body.find(".scenicSpotId").val(edit.scenicSpotId);
                        //body.find(".SpotAscriptionId").val(edit.scenicSpotAscriptionId);
                        body.find(".scenicSpotName").val(edit.scenicSpotName);
                        body.find(".scenicSpotContact").val(edit.scenicSpotContact);
                        body.find(".scenicSpotPhone").val(edit.scenicSpotPhone);
                        body.find(".scenicSpotEmail").val(edit.scenicSpotEmail);
                        body.find(".scenicSpotAddres").val(edit.scenicSpotAddres);
                        body.find(".scenicSpotPostalCode").val(edit.scenicSpotPostalCode);
                        body.find(".spotFId").val(edit.scenicSpotFid);
                        body.find(".testStartTime").val(edit.testStartTime);
                        body.find(".trialOperationsTime").val(edit.trialOperationsTime);
                        body.find(".formalOperationTime").val(edit.formalOperationTime);
                        body.find(".stopOperationTime").val(edit.stopOperationTime);
                        body.find(".companyIds").val(edit.companyId);
                        body.find(".huntQuantity").val(edit.huntQuantity);
                        body.find(".heat").val(edit.heat);
                        body.find(".treasureCooldownTime").val(edit.treasureCooldownTime);
                        body.find(".cooldownTime").val(edit.cooldownTime);
                        body.find(".repeatStatus").val(edit.repeatStatus);
                        body.find(".imageId").val(edit.imageId);
                        body.find(".workTime").val(edit.workTime);
                        body.find(".closingTime").val(edit.closingTime);
                        body.find(".workBroadcast").val(edit.workBroadcast);
                        body.find(".closingBroadcast").val(edit.closingBroadcast);
                        body.find(".hornSwitch").val(edit.hornSwitch);
                        body.find(".lampClosingTime").val(edit.lampClosingTime);
                        body.find(".lampOpeningTime").val(edit.lampOpeningTime);
                        body.find(".freeTimeSetting").val(edit.freeTimeSetting);
                        body.find(".scenicSpotArea").val(edit.scenicSpotArea);
                        body.find(".revenueYear").val(edit.revenueYear);
                        body.find(".rewardRate").val(edit.rewardRate);
                        body.find(".scenicSpotIntroduce").val(edit.scenicSpotIntroduce);
                        body.find(".provinceId").val(edit.provinceId);
                        body.find(".cityId").val(edit.cityId);
                        body.find(".regionId").val(edit.regionId);
                        body.find(".coordinateRange").val(edit.coordinateRange);
                        body.find(".spotSId").val(edit.scenicSpotSid);
                        body.find(".spotQId").val(edit.scenicSpotQid);
                        body.find(".smallNetwork").val(edit.smallNetwork);
                        form.render();
                    }
                }
            });
        },500);
    }

    /**
     * 修改状态
     * @param edit
     */
    function updateScenicSpotState(edit){
        layer.open({
            type: 2,
            title: '修改状态',
            area: ['300px', '400px'],
            content: '/page/system/scenicSpot/html/updateScenicSpotState.html',
            tableId: '#scenicSpotList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".scenicSpotId").val(edit.scenicSpotId);
                    form.render();
                }
            }
        });
    }


    /**
     * 上传景区照片
     * @param edit
     */
    function scenicSpotPicture(edit){
        layer.open({
            type: 2,
            title: '上传景区照片',
            area: ['800px', '330px'],
            content: '/page/system/scenicSpot/html/scenicSpotPicture.html',
            tableId: '#scenicSpotList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".scenicSpotId").val(edit.scenicSpotId);
                    body.find(".scenicSpotName").val(edit.scenicSpotName);
                    form.render();
                }
            }
        });
    }

	//点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYSTEM_SCENIC_SPOT_DOWNLOADEXCEL", function (e) {
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
        var scenicSpotName = $(".scenicSpotNameVal").val();
        var robotWakeupWords = $(".robotWakeupWords").val();
        window.location.href = "/system/scenicSpot/uploadScenicSpotExcel?scenicSpotName="+scenicSpotName+"&robotWakeupWords="+robotWakeupWords;
    }

})
