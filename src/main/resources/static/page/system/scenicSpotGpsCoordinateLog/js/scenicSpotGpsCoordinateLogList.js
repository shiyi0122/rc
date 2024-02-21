layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotGpsCoordinateLogList',
        url : '/system/storedValueAmountLog/getStoredValueAmountLogList',
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
            logType : "2"
        },
        id : "scenicSpotGpsCoordinateLogListTable",
        cols : [[
            {field: 'userName', title: '操作人名称', minWidth:100, align:"center"},
            {field: 'logScenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'logCoordinateOuterringBaiDu', title: '百度坐标', minWidth:100, align:"center"},
            {field: 'logCoordinateOuterring', title: 'WGS84坐标', minWidth:100, align:"center"},
            {field: 'createDate', title: '操作时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotGpsCoordinateLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotName: $(".scenicSpotNameVal").val()
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