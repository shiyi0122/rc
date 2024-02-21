layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#invoiceList',
        url : '/system/invoice/getInvoiceList',
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
        id : "invoiceListTable",
        cols : [[
            {field: 'scenicSpotName', title: '申请景区', minWidth:100, align:"center"},
            {field: 'phone', title: '手机号', minWidth:100, align:"center"},
            {field: 'invoiceAmount', title: '发票金额', minWidth:100, align:"center"},
            {field: 'invoiceType', title: '发票类型', minWidth:100, align:"center",templet:function(d){
                    if(d.invoiceType == "0"){
                        return "增值税专用发票";
                    }else if(d.invoiceType == "1"){
                        return "增值税电子普通发票";
                    }
                }},
            {field: 'dutyParagraph', title: '税号', minWidth:100, align:"center"},
            {field: 'riseType', title: '抬头类型', minWidth:100, align:"center",templet:function(d){
                    if(d.riseType == "0"){
                        return "企业";
                    }else if(d.riseType == "1"){
                        return "个人";
                    }
                }},
            {field: 'invoiceRise', title: '发票抬头', minWidth:100, align:"center"},
            {field: 'invoiceContent', title: '发票内容', minWidth:100, align:"center"},
            {field: 'processingProgress', title: '处理进度', minWidth:100, align:"center",templet:function(d){
                    if(d.processingProgress == "0"){
                        return "已申请发票";
                    }else if(d.processingProgress == "1"){
                        return "已开具发票";
                    }
                }},
            {field: 'createTime', title: '申请时间', minWidth:100, align:"center"},
            {field: 'receivingInformation', title: '收件地址', minWidth:100, align:"center"},
            {field: 'emailAddress', title: '邮箱地址', minWidth:100, align:"center"},
            {field: 'remarks', title: '备注', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#invoiceListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("invoiceListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotId").val(),
                phone: $(".phone").val(),
                invoiceType: $(".invoiceType").val(),
                processingProgress: $(".processingProgress").val(),
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

})
