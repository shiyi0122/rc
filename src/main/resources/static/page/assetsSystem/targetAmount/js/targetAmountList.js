layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#targetAmountList',
        url : '/system/targetAmount/getTargetAmountList',
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
        id : "targetAmountListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'targetState', title: '目标金额类型', align:'center',templet:function(d){
                    if(d.targetState == "1"){
                        return "日目标";
                    }else if(d.targetState == "2"){
                        return "月目标";
                    }else if(d.targetState == "3"){
                        return "年目标";
                    }
                }},
            {field: 'targetAmount', title: '景区目标金额', minWidth:100, align:"center"},
            {field: 'robotTargetAmount', title: '机器人目标金额', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#targetAmountListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(targetAmountList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_ADVERTISING_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditTargetAmount(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_ADVERTISING_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/targetAmount/delTargetAmount", {targetAmountId : data.targetAmountId}, function (data) {}, false,"GET","targetAmountListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddTargetAmount').click(function(){
        window.resources("RESOURCES_ADVERTISING_INSERT", function (e) {
            if (e.state == "200") {
                openAddTargetAmount();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddTargetAmount() {
        layer.open({
            type : 2,
            title: '添加目标金额',
            offset: '10%',
            area: ['800px', '460px'],
            content: '/page/assetsSystem/targetAmount/html/targetAmountAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditTargetAmount(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改目标金额',
                offset: '10%',
                area: ['800px', '460px'],
                content: '/page/assetsSystem/targetAmount/html/targetAmountEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".targetAmountId").val(edit.targetAmountId);
                        body.find(".targetAmount").val(edit.targetAmount);
                        body.find(".robotTargetAmount").val(edit.robotTargetAmount);
                        form.render();
                    }
                }
            });
        },500);
    }

})
