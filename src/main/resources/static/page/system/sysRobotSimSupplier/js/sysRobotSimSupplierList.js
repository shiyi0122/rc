layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
	var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#sysRobotSimSupplierList',
        url : '/system/sysRobotSimSupplier/getSysRobotSimSupplierList',
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
        id : "sysRobotSimSupplierListTable",
        cols : [[
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'simCard', title: 'ICCID卡号', minWidth:100, align:"center"},
            {field: 'supplierName', title: '供应商', minWidth:100, align:"center"},
			{field: 'remarks', title: '备注', minWidth:100, align:"center"},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#sysRobotSimSupplierListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("sysRobotSimSupplierListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                robotCode: $(".robotCodeVal").val(),  //搜索的关键字
                supplierName: $(".supplierNameVal").val(),  //搜索的关键字
                simCard : $(".robotCodeSimVal").val()
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
    table.on('tool(sysRobotSimSupplierList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_SIM_SUPPLIER", function (e) {
                if (e.state == "200") {
                    openEditSim(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_SIM_SUPPLIER", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/sysRobotSimSupplier/delSysRobotSimSupplier", {id : data.id}, function (data) {}, false,"GET","sysRobotSimSupplierListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddRole').click(function(){
        window.resources("SYS_ROBOT_SIM_SUPPLIER", function (e) {
            if (e.state == "200") {
                openAddSim();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })
    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYS_ROBOT_SIM_SUPPLIER", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddSim(edit) {
        layer.open({
            type : 2,
            title: '添加ICCID卡号',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/system/sysRobotSimSupplier/html/sysRobotSimSupplierAdd.html',
            tableId: '#sysRobotSimSupplierList'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditSim(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改sim卡信息',
                offset: '10%',
                area: ['800px', '460px'], //宽高
                content: '/page/system/sysRobotSimSupplier/html/sysRobotSimSupplierEdit.html',
                tableId: '#sysRobotSimSupplierList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".robotCode").val(edit.robotCode);
                        body.find(".simCard").val(edit.simCard);
                        body.find(".supplierName").val(edit.supplierName);
                        body.find(".remarks").val(edit.remarks);
                        form.render();
                    }
                }
            });
        },500);
    }

    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        var robotCode = $(".robotCodeVal").val();
        var simCard = $(".robotCodeSimVal").val();
        var supplierName = $(".supplierNameVal").val();
        window.location.href = "/system/sysRobotSimSupplier/uploadExcelSim?robotCode=" + robotCode + "&simCard="+simCard + "&supplierName=" + supplierName ;
    }

	//导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/system/sysRobotSimSupplier/upload'
        ,accept: 'file' //普通文件
        ,exts: 'xls|xlsx' //只允许上传Excel文件
		,before:function(obj){
			layer.load(); //上传loading
		}
        ,done: function(res){
            if (res.state == 200){
                setTimeout(function(){
					layer.alert(res.msg,function(){
                    	layer.closeAll();//关闭所有弹框
                    });
                    layui.table.reload("sysRobotSimSupplierListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
					layer.closeAll();
                },500);
            }
        }
    });
})
