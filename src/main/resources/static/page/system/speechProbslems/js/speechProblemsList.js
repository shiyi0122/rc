layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#speechProblemsList',
        url : '/system/speechProblems/getSpeechProblemsList',
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
        id : "speechProblemsListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'speechProblems', title: '语音问题名称', minWidth:100, align:"center"},
            {field: 'speechProblemsPinyin', title: '语音问题拼音', minWidth:100, align:"center"},
            {field: 'handleState', title: '处理状态', minWidth:100, align:"center",templet:function(d){
                if(d.handleState == "1"){
                    return "未处理";
                }else if(d.handleState == "2"){
                    return "已处理";
                }
            }},
            {field: 'problemStatus', title: '问题状态', minWidth:100, align:"center",templet:function(d){
                    if(d.problemStatus == "0"){
                        return "未知问题";
                    }else if(d.problemStatus == "1"){
                        return "已知问题";
                    }
                }},
            {field: 'problemTimes', title: '出现次数', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#speechProblemsListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(speechProblemsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "processed"){ //修改为已处理
            window.resources("RESOURCES_SPEECHP_ROBLEMS_PROCESSED", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为已处理吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/speechProblems/editSpeechProblemsHandleState",
                            data: {
                                speechProblemsId : data.speechProblemsId,
                                handleState : 2
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        location.reload();
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
    $('#downloadExcel').click(function(){
        window.resources("RESOURCES_SPEECHP_ROBLEMS_EXCEL", function (e) {
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
        window.location.href = "/system/speechProblems/uploadExcelSpeechProblems";
    }

})