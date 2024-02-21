layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#randomProblemList',
        url : '/system/randomProblem/getRandomProblemList',
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
        id : "randomProblemListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'randomProblem', title: '播报标题', minWidth:100, align:"center"},
            {field: 'randomAnswers', title: '播报内容', minWidth:100, align:"center"},
            {field: 'randomType', title: '播报类型', align:'center',templet:function(d){
                    if(d.randomType == "1"){
                        return "文字类型";
                    }else if(d.randomType == "2"){
                        return "视频类型";
                    }else if(d.randomType == "3"){
                        return "音频类型";
                    }
                }},
            {field: 'randomResRul', title: '音视频资源地址', minWidth:100, align:"center"},
            {field: 'randomResRulPic', title: '图片资源地址', minWidth:100, align:"center"},
            // {field: 'weight', title: '权重', minWidth:100, align:"center"},
            {field: 'randomState', title: '播报状态', align:'center',templet:function(d){
                    if(d.randomState == "1"){
                        return "启用";
                    }else if(d.randomState == "0"){
                        return "禁用";
                    }
                }},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#randomProblemListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据用户手机号模糊查询
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
    table.on('tool(randomProblemList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_RANDOM_PROBLEM_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRandomProblem(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_RANDOM_PROBLEM_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/randomProblem/delRandomProblem", {problemId : data.problemId}, function (data) {}, false,"GET","randomProblemListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "open"){ //修改为禁用
            window.resources("RESOURCES_RANDOM_PROBLEM_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为禁用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/randomProblem/editRandomProblemValid",
                            data: {
                                problemId : data.problemId,
                                randomState : 0
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("randomProblemListTable");
                                    },500);
                                }else if (data.state == "400"){
                                    setTimeout(function(){
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                                    },500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "forbidden"){ //修改为启用
            window.resources("RESOURCES_RANDOM_PROBLEM_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为启用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/randomProblem/editRandomProblemValid",
                            data: {
                                problemId : data.problemId,
                                randomState : 1
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("randomProblemListTable");
                                    },500);
                                }else if (data.state == "400"){
                                    setTimeout(function(){
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                                    },500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加按钮
    $('#btnAddRandomProblem').click(function(){
        window.resources("RESOURCES_RANDOM_PROBLEM_INSERT", function (e) {
            if (e.state == "200") {
                openAddRandomProblem();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRandomProblem() {
        layer.open({
            type : 2,
            title: '添加随机播报',
            offset: '10%',
            area: ['800px', '500px'], //宽高
            content: '/page/system/randomProblem/html/randomProblemAdd.html'
        });
    };

    /**
     * 弹出修改框
     */
    function openEditRandomProblem(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改随机播报',
                offset: '10%',
                area: ['800px', '430px'], //宽高
                content: '/page/system/randomProblem/html/randomProblemEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".problemId").val(edit.problemId);
                        body.find(".randomProblem").val(edit.randomProblem);
                        body.find(".randomAnswers").val(edit.randomAnswers);
                        body.find(".randomType").val(edit.randomType);
                        body.find(".scenicSpotId").val(edit.scenicSpotId);
                        // body.find(".weight").val(edit.weight);
                        form.render();
                    }
                }
            });
        },500);
    }

})