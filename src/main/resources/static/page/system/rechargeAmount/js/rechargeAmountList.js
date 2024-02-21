layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#rechargeAmountList',
        url : '/system/rechargeAmount/getRechargeAmountList',
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
        id : "rechargeAmountListTable",
        cols : [[
            {field: 'rechargeScenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'rechargeAmount', title: '充值金额', minWidth:100, align:"center"},
            {field: 'rechargeDiscount', title: '充值消费折扣', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#rechargeAmountListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".randomProblemVal").val() != ''){
            table.reload("randomProblemListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    randomProblem: $(".randomProblemVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的播报标题");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(rechargeAmountList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("FINANCE_RECHARGE_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRecharge(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("FINANCE_RECHARGE_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/rechargeAmount/delRechargeAmount", {rechargeId : data.rechargeId}, function (data) {}, false,"GET","randomProblemListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加按钮
    $('#btnAddrechargeAmount').click(function(){
        window.resources("FINANCE_RECHARGE_INSERT", function (e) {
            if (e.state == "200") {
                openAddRecharge();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRecharge() {
        layer.open({
            type : 2,
            title: '添加储值充值',
            offset: '10%',
            area: ['800px', '400px'], //宽高
            content: '/page/system/rechargeAmount/html/rechargeAmountAdd.html',
            tableId: '#rechargeAmountList'
        });
    };

    /**
     * 弹出修改框
     */
    function openEditRecharge(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改储值充值',
                offset: '10%',
                area: ['800px', '400px'], //宽高
                content: '/page/system/rechargeAmount/html/rechargeAmountEdit.html',
                tableId: '#rechargeAmountList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".scenicSpotId").val(edit.rechargeScenicSpotId);
                        body.find(".rechargeId").val(edit.rechargeId);
                        body.find(".rechargeAmount").val(edit.rechargeAmount);
                        body.find(".rechargeDiscount").val(edit.rechargeDiscount);
                        form.render();
                    }
                }
            });
        },500);
    }

})