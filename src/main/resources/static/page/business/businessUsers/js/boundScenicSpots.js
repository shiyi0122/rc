layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#boundScenicSpotsList',
        url : '/business/businessUsers/getBusinessUsersScenicSpotsList',
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
        where: {
            userId : $(".userId").val()
        },
        id : "boundScenicSpotsListTable",
        cols : [[
            {field: 'userName', title: '用户名称', minWidth:100, align:"center",templet: function (d) {
                    if (d.timeType==1){
                        return    '<div class="layui-table-cell laytable-cell-1-0-0" style="color: red;" >'+d.userName+'</div>';
                    } else{
                        return    '<div class="layui-table-cell laytable-cell-1-0-0" >'+d.userName+'</div>';
                    }}
             },
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center",templet:function (d) {
                    if (d.timeType==1){
                        return    '<div class="layui-table-cell laytable-cell-1-0-0" style="color: red;" >'+d.scenicSpotName+'</div>';
                    } else{
                        return    '<div class="layui-table-cell laytable-cell-1-0-0" >'+d.scenicSpotName+'</div>';
                    }}
                },
            {field: 'createTime', title: '分配时间', minWidth:100, align:"center"},
            {field: 'totalNum', title: '分配台数', minWidth:100, align:"center"},
            {field: 'totalAmount', title: '收入总金额', minWidth:100, align:"center"},
            {field: 'amount', title: '昨天收入金额', minWidth:100, align:"center"},
            {field: 'createDate', title: '入账时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#boundScenicSpotsListBar',fixed:"right",align:"center"}
        ]]
    });


    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("boundScenicSpotsListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userId : $(".userId").val(),
                scenicSpotName: $(".spotNameVal").val()   //搜索的关键字
            }
        })
    });

    $("#reset").click(function () {
        location.reload();
    })


    //列表操作
    table.on('tool(boundScenicSpotsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("BUSINESS_SYSTEM_USERS_ALLOCATE_SCENICSPOT", function (e) {
                if (e.state == "200") {
                    editAllocateScenicSpot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //解绑
            window.resources("BUSINESS_SYSTEM_USERS_ALLOCATE_ROLE", function (e) {
                if (e.state == "200") {
                    window.resourcedel("/business/businessUsers/delBusinessUsersScenicSpot", {id : data.id}, function (data) {}, false,"GET","boundScenicSpotsListTable");
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    /**
     * 编辑
     * @param edit
     */
    function editAllocateScenicSpot(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '编辑景区',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/business/businessUsers/html/editAllocateScenicSpot.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".scenicSpotIds").val(edit.scenicSpotId);
                        body.find(".contractId").val(edit.contractId);
                        body.find(".totalNum").val(edit.totalNum);
                        body.find(".dividendRatio").val(edit.dividendRatio);
                        body.find(".cooperationType select").val(edit.cooperationType);
                        body.find(".realType select").val(edit.realType);
                        body.find(".switchType select").val(edit.switchType);
                        body.find(".contractStartTime").val(edit.contractStartTime);
                        body.find(".contractEndTime").val(edit.contractEndTime);
                        form.render();
                    }
                }
            });
        },500);
    }

    /**
     * 分配角色
     * @param edit
     */
    function allocateRole(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '分配角色',
                offset: '10%',
                area: ['800px', '270px'],
                content: '/page/business/businessUsers/html/allocateRole.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    $.ajax({
                        type : 'POST',
                        url : '/business/businessUsers/getBusinessUsersByUserId',
                        data : {
                            userId : edit.id
                        },
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                top.layer.close(dex);
                                body.find(".userId").val(edit.id);
                                body.find(".roleIds").val(data.data.roleId);
                                form.render();
                            }
                        }
                    })
                }
            });
        },500);
    }

})