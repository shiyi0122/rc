layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#serviceResList',
        url : '/system/serviceRes/getServiceResList',
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
        id : "serviceResListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'serviceName', title: '服务项名称', minWidth:100, align:"center"},
            {field: 'serviceGps', title: 'WGS84坐标', minWidth:100, align:"center"},
            {field: 'serviceGpsBaiDu', title: '百度坐标', minWidth:100, align:"center"},
            {field: 'serviceIntroduce', title: '服务站介绍', minWidth:100, align:"center"},
            {field: 'servicePic', title: '服务站图片', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#serviceResListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(serviceResList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_SERVICE_RES_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditServiceRes(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_SERVICE_RES_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/serviceRes/delServiceRes", {serviceId : data.serviceId}, function (data) {}, false,"GET","serviceResListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddServiceRes').click(function(){
        window.resources("RESOURCES_SERVICE_RES_INSERT", function (e) {
            if (e.state == "200") {
                openAddServiceRes();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddServiceRes(edit) {
        layer.open({
            type : 2,
            title: '添加服务项',
            offset: '10%',
            area: ['800px', '520px'], //宽高
            content: '/page/system/serviceRes/html/serviceResAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditServiceRes(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改服务项',
                offset: '10%',
                area: ['800px', '520px'], //宽高
                content: '/page/system/serviceRes/html/serviceResEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".serviceId").val(edit.serviceId);
                        body.find(".serviceTypes").val(edit.serviceType);
                        body.find(".serviceGps").val(edit.serviceGps);
                        body.find(".serviceGpsBaiDu").val(edit.serviceGpsBaiDu);
                        body.find(".serviceIntroduce").val(edit.serviceIntroduce);
                        form.render();
                    }
                }
            });
        },500);
    }

})