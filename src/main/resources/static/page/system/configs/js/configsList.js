layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#configsList',
        url : '/system/configs/getConfigsList',
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
        id : "configsListTable",
        cols : [[
            {field: 'configsId',title: 'id',minWidth: 100,align: "center"},
            {field: 'title', title: '标题', minWidth:100, align:"center"},
            {field: 'createTime', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#configsListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(configsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_CONFIGS_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditConfigs(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_CONFIGS_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/configs/delConfigs", {configsId : data.configsId}, function (data) {}, false,"GET","configsListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加按钮
    $('#btnAddConfigs').click(function(){
        window.resources("SYS_CONFIGS_INSERT", function (e) {
            if (e.state == "200") {
                openAddConfigs();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加内容框
     */
    function openAddConfigs() {
        layer.open({
            type : 2,
            title: '添加用户协议',
            offset: '10%',
            area: ['800px', '580px'],
            content: '/page/system/configs/html/configsAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditConfigs(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改用户协议',
                offset: '10%',
                area: ['800px', '580px'],
                content: '/page/system/configs/html/configsEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
	                    top.layer.close(dex);
	                    body.find(".configsId").val(edit.configsId);
	                    body.find(".title").val(edit.title);
						body.find("#configsValues").html(edit.configsValues);
	                    form.render();
	                }
                }
            });
        },500);
    }

})
