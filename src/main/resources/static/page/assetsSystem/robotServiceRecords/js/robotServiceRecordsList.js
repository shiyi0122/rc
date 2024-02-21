layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;
    var robotCode = new URL(location.href).searchParams.get("robotCode")
    document.querySelector(".serviceRecordsCode").value = robotCode

    table.render({
        elem: '#robotServiceRecordsList',
        url : '/system/robotServiceRecords/getRobotServiceRecordsList',
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
        id : "robotServiceRecordsListTable",
        where: {
            serviceRecordsCode: robotCode
        },
        cols : [[
            {field: 'serviceRecordsCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'serviceRecordsModel', title: '机器人型号', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'errorRecordsModel', title: '故障单号', minWidth:100, align:"center"},
            {field: 'errorRecordsName', title: '故障名称', minWidth:100, align:"center"},
            {field: 'serviceRecordsResult', title: '维修结果', minWidth:100, align:"center",templet:function(d){
                    if(d.serviceRecordsResult == "1"){
                        return "未维修已派单";
                    }else if(d.serviceRecordsResult == "2"){
                        return "已修好，远超协助";
                    }else if(d.serviceRecordsResult == "3"){
                        return "已判断问题，需要配件";
                    }else if(d.serviceRecordsResult == "4"){
                        return "未修好，需要售后现场解决";
                    }else if(d.serviceRecordsResult == "5"){
                        return "已修好，售后现场解决";
                    }else if(d.serviceRecordsResult == "6"){
                        return "已确认修好";
                    }else if(d.serviceRecordsResult == "7"){
                        return "已关闭";
                    }else{
                        return "空"
                    }
                }},
            {field: 'serviceRecordsDetails', title: '维修详情', minWidth:100, align:"center"},
            {field: 'serviceRecordsPersonnelName', title: '维修人员', minWidth:100, align:"center"},
            {field: 'serviceRecordsTel', title: '维修人员电话', minWidth:100, align:"center"},
            {field: 'serviceRecordsLevel', title: '维修评价级别', minWidth:100, align:"center"},
            {field: 'serviceRecordsServiceDate', title: '维修日期', minWidth:100, align:"center"},
            {field: 'serviceRecordsSendDate', title: '派单日期', minWidth:100, align:"center"},
            {field: 'serviceRecordsRemark', title: '备注', minWidth:100, align:"center"},
            // {title: '操作', minWidth:175, templet:'#robotServiceRecordsListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotServiceRecordsListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                serviceRecordsCode: $(".serviceRecordsCode").val(),
                scenicSpotId: $(".scenicSpotId").val(),
                errorRecordsName: $(".errorRecordsName").val(),
                errorRecordsModel: $(".errorRecordsModel").val(),
                serviceRecordsPersonnel: $(".serviceRecordsPersonnel").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val()
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
    table.on('tool(invoiceList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "billing"){ //修改为已开票
            window.resources("RESOURCES_PARKING_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为已开票吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/invoice/editBilling",
                            data: {
                                invoiceId : data.invoiceId,
                                processingProgress : 1
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("invoiceListTable");
                                    },500);
                                }else if (data.state == "400"){
                                    setTimeout(function(){
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                                    },500);
                                }
                            }
                        });
                    });
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
        var phone = $(".phone").val();
        var invoiceType = $(".invoiceType").val();
        var processingProgress = $(".processingProgress").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val()
        window.location.href = "/system/invoice/uploadExcelInvoice?scenicSpotId=" + scenicSpotId +"&phone=" + phone +"&invoiceType=" + invoiceType +"&processingProgress=" + processingProgress + "&startTime=" + startTime + "&endTime=" + endTime;
    }


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
            title: '添加机器人维修记录',
            offset: '10%',
            area: ['800px', '550px'], //宽高
            content: '/page/assetsSystem/robotServiceRecords/html/robotServiceRecordsAdd.html'
        });
    };

})
