layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#ascriptionCompanyList',
        url : '/system/ascriptionCompany/getAscriptionCompanyList',
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
        id : "ascriptionCompanyListTable",
        cols : [[
            {field: 'companyName', title: '景区归属公司名称', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#ascriptionCompanyListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(ascriptionCompanyList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_SCENIC_SPOT_ASCRIPTION_COMPANY_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditAscriptionCompany(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_SCENIC_SPOT_ASCRIPTION_COMPANY_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/ascriptionCompany/delAscriptionCompany", {companyId : data.companyId}, function (data) {}, false,"GET","ascriptionCompanyListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });


    //点击添加按钮
    $('#btnAddAscriptionCompany').click(function(){
        window.resources("SYS_SCENIC_SPOT_ASCRIPTION_COMPANY_INSERT", function (e) {
            if (e.state == "200") {
                openAddAscriptionCompany();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })


    /**
     * 弹出添加内容框
     */
    function openAddAscriptionCompany() {
        layer.open({
            type : 2,
            title: '添加景区归属公司',
            offset: '10%',
            area: ['800px', '270px'],
            content: '/page/system/ascriptionCompany/html/ascriptionCompanyAdd.html',
            tableId: '#ascriptionCompanyList'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditAscriptionCompany(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改景区归属公司',
                offset: '10%',
                area: ['800px', '270px'],
                content: '/page/system/ascriptionCompany/html/ascriptionCompanyEdit.html',
                tableId: '#broadcastList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".companyId").val(edit.companyId);
                        body.find(".companyName").val(edit.companyName);
                        form.render();
                    }
                }
            });
        },500);
    }

})
