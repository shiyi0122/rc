layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotCapPriceList',
        url : '/system/scenicSpotWifiData/getScenicSpotWifiDataList',
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
        id : "scenicSpotRobotOperateListTable",
        cols : [[
            {field: 'mac', title: 'MAC', minWidth:100, align:"center"},
            {field: 'rssi', title: 'RSSI', minWidth:100, align:"center"},
            {field: 'rssiOne', title: 'RSSI_ONE', minWidth:100, align:"center"},
            {field: 'rssiTwo', title: 'RSSI_TWO', minWidth:100, align:"center"},
            {field: 'rssiThree', title: 'RSSI_THREE', minWidth:100, align:"center"},
            {field: 'tmc', title: 'TMC', minWidth:100, align:"center"},
            {field: 'router', title: 'ROUTER', minWidth:100, align:"center"},
            {field: 'ranges', title: 'RANGES', minWidth:100, align:"center"},
            {field: 'createDate', title: 'CREATE_DATE', minWidth:100, align:"center"},
            {field: 'updateDate', title: 'UPDATE_DATE', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotRobotOperateListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                router: $(".router").val()
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })



})
