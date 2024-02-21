layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#robotAccessoriesApplicationList',
        url : '/system/robotAccessoriesApplication/getRobotAccessoriesApplicationList',
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
        id : "robotAccessoriesApplicationListTable",
        cols : [[
            {field: 'scenicSpotName', title: '申请景区', minWidth:100, align:"center"},
            // {field: 'accessoriesName', title: '配件名称', minWidth:100, align:"center"},
            // {field: 'accessoryPrice', title: '备件价格/元', minWidth:100, align:"center"},
            // {field: 'accessoryModel', title: '配件型号', minWidth:100, align:"center"},
            // {field: 'accessoriesCode', title: '配件编码', minWidth:100, align:"center"},
            // {field: 'accessoryNumber', title: '配件数量', minWidth:100, align:"center"},
            {field: 'applicant', title: '申请人', minWidth:100, align:"center"},
            {field: 'processingProgress', title: '处理进度', minWidth:100, align:"center",templet:function(d){
                    if(d.processingProgress == "1"){
                        return "已申请";
                    }else if(d.processingProgress == "2"){
                        return "已付费";
                    }else if(d.processingProgress == "3"){
                        return "已付费未寄出";
                    }else if(d.processingProgress == "4"){
                        return "未付费已寄出";
                    }else if(d.processingProgress == "5"){
                        return "已下单";
                    }else if(d.processingProgress == "6"){
                        return "已发货";
                    }else if(d.processingProgress == "7"){
                        return "已签收";
                    }
                }},
            {field: 'createDate', title: '申请时间', minWidth:100, align:"center"},
            {field: 'applicationReason', title: '申请原因', minWidth:100, align:"center"},
            {field: 'approvalRecord', title: '审批记录', minWidth:100, align:"center"},
            // {field: 'accessoriesReceivedType', title: '配件是否已收到', minWidth:100, align:"center",templet:function(d){
            //         if(d.accessoriesReceivedType == "1"){
            //             return "收到";
            //         }else if(d.accessoriesReceivedType == "2"){
            //             return "未收到";
            //         }else{
            //             return "未收到"
            //         }
            //     }},
            {field: 'remarks', title: '备注', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotAccessoriesApplicationListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotAccessoriesApplicationListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotId").val(),  //搜索的关键字
                accessoriesName: $(".accessoriesName").val(),
                applicant: $(".applicant").val(),
                processingProgress: $(".processingProgress").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val(),
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
    table.on('tool(robotAccessoriesApplicationList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_BELARC_ADVISOR_EDIT", function (e) {
                if (e.state == "200") {
                    openEditRobotAccessoriesApplication(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent ==='accessories'){
            window.resources("SYS_ROBOT_BELARC_ADVISOR_EDIT", function (e) {
                if (e.state == "200") {
                    openRobotAccessories(data);
                } else {
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
        var scenicSpotId = $(".scenicSpotId").val();
        var accessoriesName = $(".accessoriesName").val();
        var applicant = $(".applicant").val();
        var processingProgress = $(".processingProgress").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        window.location.href = "/system/robotAccessoriesApplication/uploadExcelRobotAccessoriesApplication?scenicSpotId=" + scenicSpotId +"&accessoriesName=" + accessoriesName +"&applicant=" + applicant +"&processingProgress=" + processingProgress  +"&startTime=" + startTime  +"&endTime=" + endTime;
    }

    /**
     * 弹出编辑框
     */
    function openEditRobotAccessoriesApplication(edit) {
        layer.open({
            type : 2,
            title: '编辑处理进度',
            offset: '10%',
            area: ['800px', '260px'], //宽高
            content: '/page/assetsSystem/robotAccessoriesApplication/html/robotAccessoriesApplicationEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".accessoriesApplicationId").val(edit.accessoriesApplicationId);
                    body.find(".processingProgress select").val(edit.processingProgress);
                    form.render();
                }
            }
        });
    };

    /**
     * 弹出配件详情
     */
    function openRobotAccessories(edit) {
        layer.open({
            type : 2,
            title: '配件详情',
            offset: '10%',
            area: ['800px', '800px'], //宽高
            content: '/page/assetsSystem/robotAccessoriesApplication/html/robotAccessoriesManagement.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    console.log(edit)
                    body.find(".accessoriesApplicationId").val(edit.accessoriesApplicationId);
                }
            }
        });
    };


    //点击添加按钮
    $('#btnAdd').click(function(){
        window.resources("SYS_ROBOT_BELARC_ADVISOR_EDIT", function (e) {
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
            title: '添加配件申请',
            offset: '10%',
            area: ['800px', '550px'], //宽高
            content: '/page/assetsSystem/robotAccessoriesApplication/html/robotAccessoriesApplicationAdd.html'
        });
    };

})
