layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;
    var upload = layui.upload;

    table.render({
        elem: '#robotOperatingInformationList',
        url : '/system/assetsRobot/getRobotOperatingInformationList',
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
        id : "robotOperatingInformationListTable",
        cols : [[
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center",fixed:"left"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'robotRunState', title: '运行状态', minWidth:100, align:"center",templet:function(d){
                    if(d.robotRunState == "10"){
                        return "闲置";
                    }else if(d.robotRunState == "20"){
                        return "用户解锁";
                    }else if(d.robotRunState == "30"){
                        return "用户临时锁定";
                    }else if(d.robotRunState == "40"){
                        return "管理员启动";
                    }else if(d.robotRunState == "50"){
                        return "管理员停止";
                    }else if(d.robotRunState == "60"){
                        return "自检故障报警";
                    }else if(d.robotRunState == "70"){
                        return "扫码解锁中";
                    }else if(d.robotRunState == "80"){
                        return "运营人员钥匙解锁";
                    }else if(d.robotRunState == "90"){
                        return "运营人员维护";
                    }else if (d.robotRunState == "100"){
                        return "禁区锁定";
                    }
                }},
            {field: 'robotFaultState', title: '故障状态', minWidth:100, align:"center",templet:function(d){
                    if(d.robotFaultState == "10"){
                        return "正常";
                    }else if(d.robotFaultState == "20"){
                        return "报修";
                    }else if(d.robotFaultState == "30"){
                        return "故障";
                    }
                }},
            {field: 'totalTime', title: '当天运营时长', minWidth:100, align:"center"},
            {field: 'clientVersion', title: '机器人PAD版本号', minWidth:100, align:"center"},
            // {field: 'robotModel', title: '机器人型号', minWidth:100, align:"center"},
            {field: 'robotVersionNumber', title: '机器人版本号', minWidth:100, align:"center"},
            {field: 'robotPowerState', title: '电量状态', minWidth:100, align:"center",templet:function(d){return d.robotPowerState+'%'}},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotOperatingInformationListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotOperatingInformationListTable",{
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
    table.on('tool(robotOperatingInformationList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "updateRobotRunState"){ //修改状态
            window.resources("ROBOT_OPERATING_INFORMATION_MODIFYSTATE", function (e) {
                if (e.state == "200") {
                    updateRobotRunState(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "qrcode"){ //下载二维码
            window.resources("ROBOT_OPERATING_INFORMATION_DOWNLOADQRCODE", function (e) {
                if (e.state == "200") {
                    window.location.href = "/system/robot/qrcode?fileName=" + data.robotCode + ".jpg"
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("ROBOT_OPERATING_INFORMATION_DOWNLOADEXCEL", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 修改状态
     * @param edit
     */
    function updateRobotRunState(edit){
        layer.open({
            type: 2,
            title: '状态修改',
            area: ['300px', '400px'],
            content: '/page/system/robot/html/updateRobotRunState.html',
            tableId: '#robotList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".robotId").val(edit.robotId);
                    form.render();
                }
            }
        });
    }

    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        var robotCodeVal = $(".robotCodeVal").val();
        var scenicSpotId = $(".scenicSpotId").val();
        var robotCodeSim = $(".robotCodeSimVal").val();
        window.location.href = "/system/assetsRobot/uploadExcelAssetsRobot?robotCode=" + robotCodeVal +"&scenicSpotId="+scenicSpotId +"&robotCodeSim="+robotCodeSim;
    }

    //导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/system/assetsRobot/upload'
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
                    layui.table.reload("robotOperatingInformationListTable");
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
