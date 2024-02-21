layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#businessUsersList',
        url : '/business/businessUsers/getBusinessUsersList',
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
        id : "businessUsersListTable",
        cols : [[
            {field: 'userName', title: '用户名称', minWidth:100, align:"center"},
            {field: 'phone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'identityCard', title: '身份证号码', minWidth:100, align:"center"},
            {field: 'accountBalance', title: '账户余额', minWidth:100, align:"center"},
            {field: 'userType', title: '用户类型', minWidth:100, align:"center",templet:function(d){
                if(d.userType == "0"){
                    return "非合伙人";
                }else if(d.userType == "1"){
                    return "合伙人";
                }
            }},
            {field: 'userState', title: '用户状态', minWidth:100, align:"center",templet:function(d){
                if(d.userState == "0"){
                    return "正常";
                }else if(d.userState == "-1"){
                    return "禁用";
                }else if(d.userState == "-2"){
                    return "删除";
                }else if(d.userState == "1"){
                    return "核验中";
                }
            }},
            {field: 'scenicType', title: '景区状态', minWidth:100, align:"center",templet:function(d){
                if(d.scenicType == "0"){
                    return "无景区";
                }else if(d.scenicType == "1"){
                    return "有景区";
                }
            }},
            {field: 'examineType', title: '审核报备权限', minWidth:100, align:"center",templet:function(d){
                    if(d.examineType == "0"){
                        return "报备权限";
                    }else if(d.examineType == "1"){
                        return "审核权限";
                    }else if(d.examineType == "2"){
                        return "无权限";
                    }else if(d.examineType == "3"){
                        return "审核报备权限";
                    }
                }},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#businessUsersListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("businessUsersListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                phone: $(".phoneVal").val(),  //搜索的关键字
                userName: $(".userNameVal").val(),   //搜索的关键字
                userType: $(".userType").val(),   //搜索的关键字
                priceMin: $(".priceMin").val(),   //搜索的关键字
                priceMax: $(".priceMax").val()   //搜索的关键字
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(businessUsersList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'allocateScenicSpot'){ //分配景区
            window.resources("BUSINESS_SYSTEM_USERS_ALLOCATE_SCENICSPOT", function (e) {
                if (e.state == "200") {
                    allocateScenicSpot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'allocateRole'){ //分配角色
            window.resources("BUSINESS_SYSTEM_USERS_ALLOCATE_ROLE", function (e) {
                if (e.state == "200") {
                    allocateRole(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent == 'details'){
            viewDetails(data);
        }else if(layEvent === 'filing'){ //分配报备权限
            window.resources("BUSINESS_SYSTEM_USERS_ALLOCATE_ROLE", function (e) {
                if (e.state == "200") {
                    filing(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    /**
     * 分配景区
     * @param edit
     */
    function allocateScenicSpot(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '分配景区',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/business/businessUsers/html/allocateScenicSpot.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".userId").val(edit.id);
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


    /**
     * 分配报备角色
     * @param edit
     */
    function filing(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '分配报备角色',
                offset: '10%',
                area: ['800px', '270px'],
                content: '/page/business/businessUsers/html/fillingRole.html',
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

    /**
     * 跳转详情页面
     * @param edit
     */
    function viewDetails(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '绑定景区',
                offset: '10%',
                area: ['1000px', '580px'], //宽高
                content: '/page/business/businessUsers/html/boundScenicSpots.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".userId").val(edit.id);//景区ID
                        form.render();
                    }
                }
            });
        },500);
    }
})