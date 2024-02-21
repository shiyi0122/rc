layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotCapPriceList',
        url : '/system/scenicSpotCapPrice/getScenicSpotCapPriceList',
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
        id : "scenicSpotCapPriceListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'loginName', title: '操作人', minWidth:100, align:"center"},
            {field: 'normalCapOneTime', title: '一档工作日封顶时间', minWidth:100, align:"center"},
            {field: 'normalCapOnePrice', title: '一档工作日封顶价格', minWidth:100, align:"center"},
            {field: 'normalCapOneUnitPrice', title: '一档工作日封顶单价', minWidth:100, align:"center"},
            {field: 'normalCapTwoTime', title: '二档工作日封顶时间', minWidth:100, align:"center"},
            {field: 'normalCapTwoPrice', title: '二档工作日封顶价格', minWidth:100, align:"center"},
            {field: 'normalCapTwoUnitPrice', title: '二档工作日封顶单价', minWidth:100, align:"center"},
            {field: 'weekendCapOneTime', title: '一档周末封顶时间', minWidth:100, align:"center"},
            {field: 'weekendCapOnePrice', title: '一档周末封顶价格', minWidth:100, align:"center"},
            {field: 'weekendCapOneUnitPrice', title: '一档周末封顶单价', minWidth:100, align:"center"},
            {field: 'weekendCapTwoTime', title: '二档周末封顶时间', minWidth:100, align:"center"},
            {field: 'weekendCapTwoPrice', title: '二档周末封顶价格', minWidth:100, align:"center"},
            {field: 'weekendCapTwoUnitPrice', title: '二档周末封顶单价', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotCapPriceListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotId").val()
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
