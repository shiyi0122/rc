layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotBasicArchivesLogList',
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
            type : "1"
        },
        id : "scenicSpotBasicArchivesLogListTable",
        cols : [[
            {field: 'priceModificationSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'priceModificationUserName', title: '修改人名称', minWidth:100, align:"center"},
            {field: 'scenicSpotContact', title: '景区联系人', minWidth:100, align:"center"},
            {field: 'scenicSpotPhone', title: '景区联系人电话', minWidth:100, align:"center"},
            {field: 'scenicSpotProvince', title: '景区归属地', minWidth:100, align:"center"},
            {field: 'testStartTime', title: '测试开始时间', minWidth:100, align:"center"},
            {field: 'trialOperationsTime', title: '试运营开始时间', minWidth:100, align:"center"},
            {field: 'formalOperationTime', title: '正式运营时间', minWidth:100, align:"center"},
            {field: 'stopOperationTime', title: '停止运营时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotBasicArchivesLogListTable",{
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
        window.location.href = "/system/priceModificationLog/uploadExcelBasicArchivesLog?priceModificationSpotName=" + priceModificationSpotName + "&startTime=" + startTime + "&endTime=" +endTime;
    }


})