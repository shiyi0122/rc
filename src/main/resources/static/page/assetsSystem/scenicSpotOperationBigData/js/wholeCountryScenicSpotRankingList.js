layui.use(['form','layer','table','laytpl','upload'],function(){
    var layer = layui.layer;
    var table = layui.table;
    var $ = layui.jquery;

    table.render({
        elem: '#wholeCountryScenicSpotRankingList',
        url : '/system/operationBigData/getWholeCountryScenicSpotRankingList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        initSort: {field: "orderAmount", type: "desc"},
        autoSort: false, //禁用前端自动排序
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
        where: {
            id : $(".id").val()
        },
        id : "wholeCountryScenicSpotRankingListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'operationTime', title: '运营时长/小时', minWidth:100, align:"center",sort : true},
            {field: 'orderNumber', title: '已付款订单数量', minWidth:100, align:"center",sort : true},
            {field: 'orderAmount', title: '已付款订单金额/元', minWidth:100, align:"center",sort : true},
            {field: 'completionRatio', title: '目标完成比例/%', minWidth:100, align:"center",sort : true},
            {field: 'robotReceivingOrder', title: '单台机器人接单量', minWidth:100, align:"center",sort : true},
            {field: 'robotOutputValue', title: '单台机器人产值/元', minWidth:100, align:"center",sort : true},
            {field: 'robotOperationTime', title: '单台机器人运营时长/小时', minWidth:100, align:"center",sort : true},
            {field: 'operatorsReceivingOrder', title: '单运营人员接单量', minWidth:100, align:"center",sort : true},
            {field: 'robotUtilization', title: '机器人利用率/%', minWidth:100, align:"center",sort : true},
            {field: 'unitPricePerCustomer', title: '客单价/元', minWidth:100, align:"center",sort : true},
            {field: 'failureRate', title: '订单故障率/%', minWidth:100, align:"center",sort : true},
            {field: 'robotLaunchQuantity', title: '机器人投放数量', minWidth:100, align:"center",sort : true},
            {
                field: "jxz",
                title: "在线订单数量",
                minWidth: 100,
                align: "center",
                sort: true,
            },
            {
                field: "wzf",
                title: "未付款订单数量",
                minWidth: 100,
                align: "center",
                sort: true,
            },
            {
                field: "wzfje",
                title: "未付款金额/元",
                minWidth: 100,
                align: "center",
                sort: true,
            },
        ]]
    });

    /**
     * 排序操作
     */
    //注：sort 是工具条事件名，operate_table 是 table 原始容器的属性 lay-filter="对应的值"
    table.on("sort(wholeCountryScenicSpotRankingList)", function (obj) {
        // console.log(obj.field); //当前排序的字段名
        // console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
        // console.log(this); //当前排序的 th 对象
        table.reload('wholeCountryScenicSpotRankingListTable', {
            initSort: obj,
            where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                field: obj.field,
                type: obj.type //排序方式
            }

        })
    });

    /**
     * 根据用户名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("wholeCountryScenicSpotRankingListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotId").val(),  //搜索的关键字
                companyId: $(".companyId").val(),  //搜索的关键字
                dataType: $(".dataType").val(),  //搜索的关键字
                startTime: $(".startTime").val(),  //搜索的关键字
                endTime: $(".endTime").val()  //搜索的关键字
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYSTEM_USER_DELETE", function (e) {
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
        var companyId = $(".companyId").val();
        var dataType = $(".dataType").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        var id = $(".id").val();
        window.location.href = "/system/operationBigData/uploadScenicSpotRankingExcel?scenicSpotId=" + scenicSpotId + "&companyId=" + companyId + "&dataType=" + dataType + "&startTime=" + startTime + "&endTime=" + endTime + "&id=" + id;
    }

})
