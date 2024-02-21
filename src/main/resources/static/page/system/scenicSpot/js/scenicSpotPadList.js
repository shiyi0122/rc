layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotPadList',
        url : '/system/scenicSpot/getScenicSpotPadList',
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
        id : "scenicSpotPadListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'versionUrl', title: 'PAD路径', minWidth:100, align:"center"},
            {field: 'versionNumber', title: 'PAD版本号', minWidth:100, align:"center"},
            {title: '操作', minWidth:100, templet:'#scenicSpotPadListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据景区名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotPadListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                versionNumber: $(".versionNumberVal").val(),
                scenicSpotName: $(".scenicSpotNameVal").val() //搜索的关键字
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
    table.on('tool(scenicSpotPadList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_APP_VERSION_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditScenicSpotPad(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddScenicSpotPad').click(function(){
        window.resources("SYS_ROBOT_APP_VERSION_INSERT", function (e) {
            if (e.state == "200") {
                openAddScenicSpotPad();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddScenicSpotPad() {
        layer.open({
            type : 2,
            title: '添加景区PAD',
            offset: '10%',
            area: ['800px', '350px'],
            content: '/page/system/scenicSpot/html/scenicSpotPadAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditScenicSpotPad(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改景区PAD',
                offset: '10%',
                area: ['800px', '350px'],
                content: '/page/system/scenicSpot/html/scenicSpotPadEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".versionId").val(edit.versionId);
                        body.find(".scenicSpotId").val(edit.scenicSpotId);
                        body.find(".scenicSpotName").val(edit.scenicSpotName);
                        body.find(".versionNumber").val(edit.versionNumber);
                        form.render();
                    }
                }
            });
        },500);
    }

})
