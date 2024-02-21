layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#robotPartsManagementList',
        url : '/system/partsManagementType/getPartsManagementTypeList',
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
        id : "robotPartsManagementListTable",
        cols : [[
            {field: 'partsManagementType', title: '配件类型', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotPartsManagementBar',fixed:"right",align:"center"}

        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotPartsManagementListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                partsManagementType : $(".faultNameVal").val()
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
    table.on('tool(robotPartsManagementList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("ROBOT_PARTS_MANAGEMENT_TYPE", function (e) {
                if (e.state == "200") {
                    openEditRobotPartsManagement(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("ROBOT_PARTS_MANAGEMENT_TYPE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/partsManagementType/delPartsManagementType", {typeId : data.id}, function (data) {}, false,"GET","robotPartsManagementListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击导出EXCEL表
    // $('#downloadExcel').click(function(){
    //     window.resources("SYSTEM_ROBOT_DOWNLOADEXCEL", function (e) {
    //         if (e.state == "200") {
    //             downloadExcel();
    //         } else {
    //             layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
    //         }
    //     }, false,"GET");
    // })

    /**
     * 导出EXCEL表
     */
    // function downloadExcel(){
    //     var accessoriesType = $(".accessoriesType").val();
    //     var faultName = $(".faultNameVal").val();
    //     var accessoryModel = $(".accessoryModelVal").val();
    //     window.location.href = "/system/robotPartsManagement/uploadExcelRobotPartsManagement?accessoriesType=" + accessoriesType + "&faultName=" + faultName + "&accessoryModel=" + accessoryModel;
    // }

    /**
     * 弹出编辑框
     */
    function openEditRobotPartsManagement(edit) {
        layer.open({
            type : 2,
            title: '编辑机器人配件管理',
            offset: '10%',
            area: ['800px', '450px'], //宽高
            content: '/page/assetsSystem/robotPartsManagementType/html/robotPartsManagementTypeEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".id").val(edit.id);
                    body.find(".partsManagementType").val(edit.partsManagementType);
                    form.render();
                }
            }
        });
    };

    //导入Excel表
    // upload.render({
    //     elem: '#importExcel'
    //     ,url: '/system/robotPartsManagement/importExcel'
    //     ,accept: 'file' //普通文件
    //     ,exts: 'xls|xlsx' //只允许上传Excel文件
    //     ,before:function(obj){
    //         layer.load(); //上传loading
    //     }
    //     ,done: function(res){
    //         if (res.state == 200){
    //             setTimeout(function(){
    //                 layer.alert(res.msg,function(){
    //                     layer.closeAll();//关闭所有弹框
    //                 });
    //                 layui.table.reload("robotPartsManagementListTable");
    //             },500);
    //         }else{
    //             setTimeout(function(){
    //                 top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
    //                 layer.closeAll();
    //             },500);
    //         }
    //     }
    // });

    //点击添加按钮
    $('#btnAdd').click(function(){
        window.resources("ROBOT_PARTS_MANAGEMENT_TYPE", function (e) {
            if (e.state == "200") {
                openAddRobotPartsManagement();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobotPartsManagement() {
        layer.open({
            type : 2,
            title: '添加机器人配件管理',
            offset: '10%',
            area: ['800px', '550px'], //宽高
            content: '/page/assetsSystem/robotPartsManagementType/html/robotPartsManagementTypeAdd.html'
        });
    };


})
