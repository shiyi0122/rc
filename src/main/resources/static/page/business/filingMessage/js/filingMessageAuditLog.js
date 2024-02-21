layui.use(['form','layer','table','laytpl'],function(){
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var load = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
    setTimeout(function(){
        top.layer.close(load);
        table.render({
            elem: '#authorityDetailsList',
            url : '/business/filingMessage/getFilingMessageLogList',
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
                dataName: 'data' //数据列表的字段名称，默认：data
            },
            where: {
                id : $(".id").val()
            },
            id : "authorityDetailsListTable",
            cols : [[
                {field: 'filingName', title: '报备人名称', minWidth:100, align:"center"},
                {field: 'filingTime', title: '报备日期', minWidth:100, align:"center"},
                {field: 'examineName', title: '审核人名称', minWidth:100, align:"center"},
                {field: 'auditDate', title: '审核日期', minWidth:100, align:"center"},
                {field: 'findingsOfAudit', title: '审核结果', align:'center',templet:function(d){
                        if(d.findingsOfAudit == "2"){
                            return "通过";
                        }else if(d.findingsOfAudit == "3"){
                            return "驳回";
                        }
                    }},
                {field: 'reson', title: '原因', minWidth:100, align:"center"},
                {field: 'createTime', title: '创建时间', minWidth:100, align:"center"},
                // {title: '操作', minWidth:175, templet:'#authorityDetailsListBar',fixed:"right",align:"center"}
            ]]
        });
    },500);

    /**
     * 模糊查询
     */
    // $("#btnSearch").on("click",function(){
    //     if($(".scenicSpotNameVal").val() != ''){
    //         table.reload("authorityDetailsListTable",{
    //             page: {
    //                 curr: 1 //重新从第 1 页开始
    //             },
    //             where: {
    //                 scenicSpotName: $(".scenicSpotNameVal").val()  //搜索的关键字
    //             }
    //         })
    //     }else{
    //         layer.msg("请输入搜索的景区名称");
    //     }
    // });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    // table.on('tool(authorityDetailsList)', function(obj){
    //     var layEvent = obj.event,
    //         data = obj.data;
    //
    //     if(layEvent === 'del'){ //删除
    //         window.resources("SYSTEM_USER_DELETE", function (e) {
    //             if (e.state == "200"){
    //                 window.resourcedel("/system/user/delUserRoleSpot", {id : data.id}, function (data) {}, false,"GET","authorityDetailsListTable");
    //             }else if (e.state == "400"){
    //                 layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
    //             }
    //         }, false,"GET");
    //     }
    // });

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("BUSINESS_FILING_MESSAGE", function (e) {
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
        // var scenicSpotName = $(".scenicSpotNameVal").val();
        var id = $(".id").val();

        window.location.href = "/business/filingMessage/uploadExcelFilingMessageAuditLog?id="+id;
    }


})
