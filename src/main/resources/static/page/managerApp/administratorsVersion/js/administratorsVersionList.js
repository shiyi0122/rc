layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#administratorsVersionList',
        url : '/system/administratorsVersion/getAdministratorsVersionList',
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
        id : "administratorsVersionListTable",
        cols : [[
            {field: 'versionUrl', title: 'URL', minWidth:100, align:"center"},
            {field: 'versionNumber', title: '版本号', minWidth:100, align:"center"},
            {field: 'versionDescription', title: '版本描述', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#administratorsVersionListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(administratorsVersionList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_ADMINISTRATORS_VERSION_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditAdministratorsVersion(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_ADMINISTRATORS_VERSION_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/administratorsVersion/delAdministratorsVersion", {versionId : data.versionId}, function (data) {}, false,"GET","administratorsVersionListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "download"){ //下载
            window.resources("SYS_ROBOT_ADMINISTRATORS_VERSION_DOWNLOAD", function (e) {
                if (e.state == "200") {
                    window.location.href = "/system/administratorsVersion/download?fileName=" + data.versionUrl
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddAdministratorsVersion').click(function(){
        window.resources("SYS_ROBOT_ADMINISTRATORS_VERSION_INSERT", function (e) {
            if (e.state == "200") {
                openAddAdministratorsVersion();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddAdministratorsVersion() {
        layer.open({
            type : 2,
            title: '上传APP',
            offset: '10%',
            area: ['800px', '400px'], //宽高
            content: '/page/managerApp/administratorsVersion/html/administratorsVersionAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditAdministratorsVersion(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改APP',
                offset: '10%',
                area: ['800px', '270px'],
                content: '/page/managerApp/administratorsVersion/html/administratorsVersionEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".versionId").val(edit.versionId);
                        body.find(".versionNumber").val(edit.versionNumber);
                        body.find(".versionDescription").val(edit.versionDescription);
                        form.render();
                    }
                }
            });
        },500);
    }

})
