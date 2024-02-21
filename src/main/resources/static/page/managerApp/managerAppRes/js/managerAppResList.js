layui.config({
    base: '/'
}).extend({
    treetable: 'module/treetable'
}).use(['form','table', 'treetable','layer'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var treetable = layui.treetable;

    // 渲染表格
    layer.load(2);
    tableIns = treetable.render({
        treeColIndex: 1,
        treeSpid: -1,
        treeIdName: 'resCode',
        treePidName: 'resPcode',
        elem: '#managerAppResList',
        url: '/system/managerAppRes/getManagerAppResList',
        id:'managerAppResListId',
        page: false,
        cols: [[
            {type: 'numbers'},
            {field: 'resName', minWidth: 200, title: '菜单名称'},
            {field: 'resCode', align: 'center', title: '菜单权限标识'},
            {field: 'resSort', align: 'center', title: '资源排序号'},
            {
                field: 'resMenuFlag', align: 'center', templet: function (d) {
                    if (d.resMenuFlag == 'N') {
                        return '<span class="layui-badge layui-bg-gray">按钮</span>';
                    }else{
                        return '<span class="layui-badge-rim">菜单</span>';
                    }
                }, title: '类型'
            },
            {templet: '#auth-state', align: 'center',fixed:"right", title: '操作'}
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });

    $('#expandAll').click(function () {
        treetable.expandAll('#managerAppResList');
    });

    $('#foldAll').click(function () {
        treetable.foldAll('#managerAppResList');
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        $.get("/system/permissionId/getPermissionId",{
            permissionId : "SYSTEM_RES_INSERT"
        },function(e){
            if (e.state == "200"){
                openAddManagerAppRes();
            }else if (e.state == "400"){
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        })
    });

    //列表操作
    table.on('tool(managerAppResList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            $.get("/system/permissionId/getPermissionId",{
                permissionId : "SYSTEM_RES_UPDATE"
            },function(e){
                if (e.state == "200"){
                    openEditMenu(data);
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            })
        }else if(layEvent === 'del'){ //删除
            $.get("/system/permissionId/getPermissionId",{
                permissionId : "SYSTEM_RES_DELETE"
            },function(e){
                if (e.state == "200"){
                    layer.confirm('确定删除菜单栏？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.get("/system/managerAppRes/delManagerAppRes",{
                            resId : data.resId
                        },function(data){
                            if (data.state == "200"){
                                setTimeout(function(){
                                    top.layer.msg(data.msg, {icon: 6});
                                    top.layer.close(dex);
                                    layer.close(index);
                                    window.location.reload();//刷新当前页
                                },1000);
                            }else if (data.state == "400"){
                                top.layer.close(dex);
                                layer.close(index);
                                layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                            }
                        })
                    });
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            })
        }
    });
    /**
     * 弹出添加菜单
     */
    function openAddManagerAppRes() {
        layer.open({
            type : 2,
            title: '添加APP菜单',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/managerApp/managerAppRes/html/managerAppResAdd.html'
        });
    };

    function openEditMenu(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        layer.open({
            type : 2,
            title: '修改菜单',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/system/resource/html/resourceEdit.html',
            tableId: '#resList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    $.ajax({
                        type : 'POST',
                        url : '/system/res/LoadEditResource',
                        data : {resId : edit.resId},
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                var valueD = data.data.resMenuFlag
                                top.layer.close(dex);
                                body.find(".resId").val(data.data.resId);//菜单ID
                                body.find(".resName").val(data.data.resName);//菜单名称
                                body.find(".resCode").val(data.data.resCode);//菜单编号
                                body.find(".resPcode").val(data.data.resPcode);//父级编号
                                body.find(".resPcodeName").val(data.data.resPcodeName);//父级编号名称
                                if(valueD == "Y"){
                                    body.find("#radioD input:radio[name='resMenuFlag'][value='Y']").attr("checked",valueD == "Y" ? true : false);//是否是菜单
                                    form.render('radio');
                                }else if (valueD == "N"){
                                    body.find("#radioD input:radio[name='resMenuFlag'][value='N']").attr("checked",valueD == "N" ? true : false);//是否是菜单
                                    form.render('radio');
                                }
                                body.find(".resUrl").val(data.data.resUrl);//请求地址
                                body.find(".resIcon").val(data.data.resIcon);//菜单图标
                                body.find(".resSort").val(data.data.resSort);//菜单排序
                                body.find(".resDescription").val(data.data.resDescription);//菜单备注
                                form.render('radio');
                            }else if(data.state == "400"){
                                top.layer.close(dex);
                                layer.msg(data.msg);
                            }
                        }
                    })
                }
            }
        });
    }

    $('#btnSearch').on('click', function () {
        if($("#resName").val() != ''){
            table.reload("managerAppResListId",{
                url:'/system/managerAppRes/getManagerAppResList',
                where: {
                    resName: $("#resName").val()
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

});