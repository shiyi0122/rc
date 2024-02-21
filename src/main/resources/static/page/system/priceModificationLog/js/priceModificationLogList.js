layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#priceModificationLogList',
        url : '/system/priceModificationLog/getPriceModificationLogList',
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
        where:{
            type : "2"
        },
        id : "priceModificationLogListTable",
        cols : [[
            {field: 'priceModificationSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'priceModificationUserName', title: '修改人名称', minWidth:100, align:"center"},
            {field: 'scenicSpotDeposit', title: '押金', minWidth:100, align:"center"},
            {field: 'scenicSpotWeekendPrice', title: '周末单价', minWidth:100, align:"center"},
            {field: 'scenicSpotNormalPrice', title: '工作日单价', minWidth:100, align:"center"},
            {field: 'scenicSpotWeekendRentPrice', title: '周末起租价格', minWidth:100, align:"center"},
            {field: 'scenicSpotNormalRentPrice', title: '工作日起租价格', minWidth:100, align:"center"},
            {field: 'scenicSpotRentTime', title: '起租时间', minWidth:100, align:"center"},
            {field: 'scenicSpotBeyondPrice', title: '调度费', minWidth:100, align:"center"},
            {field: 'scenicSpotNormalTime', title: '工作日计费时间', minWidth:100, align:"center"},
            {field: 'scenicSpotWeekendTime', title: '周末计费时间', minWidth:100, align:"center"},
            {field: 'randomBroadcastTime', title: '随机播报时间', minWidth:100, align:"center"},
            {field: 'scenicSpotFrequency', title: '锁定次数', minWidth:100, align:"center"},
            {field: 'scenicSpotFenceTime', title: '电子围栏锁定时间(秒)', minWidth:100, align:"center"},
            {field: 'scenicSpotForbiddenZoneTime', title: '禁区锁定时间(秒)', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("priceModificationLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                priceModificationSpotName: $(".priceModificationSpotNameVal").val(),
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

    /**
     * 点击导出EXCEL表
     */
    $('#downloadExcel').click(function(){
        window.resources("PRICE_MODIFICATION_LOG_DOWNLOADEXCEL", function (e) {
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
        var priceModificationSpotName = $(".priceModificationSpotNameVal").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        window.location.href = "/system/priceModificationLog/uploadExcelPriceModificationLog?priceModificationSpotName=" + priceModificationSpotName + "&startTime=" + startTime + "&endTime=" +endTime;
    }

})