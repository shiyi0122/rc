layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    table.render({
        elem: '#scenicSpotVoicePromptList',
        url : '/system/scenicSpot/getScenicSpotList',
        cellMinWidth : 150,
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
        id : "scenicSpotVoicePromptListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'scenicSpotOpenword', title: '开机提示语', minWidth:100, align:"center"},
            {field: 'scenicSpotCloseword', title: '关机提示语', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#scenicSpotVoicePromptListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据景区名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotVoicePromptListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotName: $(".scenicSpotNameVal").val(), //搜索的关键字
                robotWakeupWords: $(".robotWakeupWords").val() //搜索的关键字
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
    table.on('tool(scenicSpotVoicePromptList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SCENIC_SPOT_VOICE_PROMPT_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditScenicSpot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditScenicSpot(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改语音提示',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/system/scenicSpot/html/scenicSpotVoicePromptEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".scenicSpotId").val(edit.scenicSpotId);
                        body.find(".scenicSpotName").val(edit.scenicSpotName);
                        body.find(".scenicSpotOpenword").val(edit.scenicSpotOpenword);
                        body.find(".scenicSpotCloseword").val(edit.scenicSpotCloseword);
                        form.render();
                    }
                }
            });
        },500);
    }

})
