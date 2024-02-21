layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;


    var tableIns = table.render({
        elem: '#winningList',
        url : '/system/Winning/getWinningInformation',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize',//每页数据量的参数名，默认：pageSize
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "WinningListTable",
        cols : [[
            {field: 'currentUserPhone', title: '手机号', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'exchangeState', title: '状态', align:'center',templet:function(d){
                    if(d.exchangeState == "0"){
                        return "未兑换";
                    }else if(d.exchangeState == "1"){
                        return "已兑换";
                    }else if (d.exchangeState == "2"){
                        return "已过期";
                    }
                }},
            {field: 'exchangeType', title: '宝箱类型', align:'center',templet:function(d){
                    if(d.exchangeType == "4"){
                        return "寻宝宝箱";
                    }else if(d.exchangeType == "5"){
                        return "随机宝箱";
                    }
                }},
            {field: 'exchangeName', title: '奖品名称', minWidth:100, align:"center"},
            {field: 'startValiDity', title: '开始兑换时间', minWidth:100, align:"center"},
            {field: 'endValiDity', title: '结束兑换时间', minWidth:100, align:"center"},
        ]]
    });

    $("#btnSearch").on("click",function(){
        table.reload("WinningListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                currentUserPhone: $(".currentUserPhone").val(),  //搜索的关键字
                exchangeType: $(".exchangeType").val(),  //搜索的关键字
            }
        })
    });

    $("#reset").click(function () {
        location.reload();
    })
})