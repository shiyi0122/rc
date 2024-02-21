layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#attendanceRulesList',
        url : '/system/orderExceptionManagement/getOrderExceptionManagementList',
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
        id : "attendanceRulesListTable",
        cols : [[
            {field: 'causesName', title: '上报原因大类', minWidth:100, align:"center"},
            {field: 'reason', title: '上报原因', minWidth:100, align:"center"},
            {field: 'remarks', title: '备注', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#orderExceptionManagementListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("attendanceRulesListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotId").val(),  //搜索的关键字
                attendanceRulesName: $(".attendanceRulesName").val()
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
    table.on('tool(attendanceRulesList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_BELARC_ADVISOR_EDIT", function (e) {
                if (e.state == "200") {
                    openEditAttendanceRules(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_BELARC_ADVISOR_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/orderExceptionManagement/delOrderExceptionManagement", {orderExceptionManagementId : data.orderExceptionManagementId}, function (data) {}, false,"GET","orderExceptionManagementListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYS_ROBOT_BELARC_ADVISOR_DOWNLOADEXCEL", function (e) {
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
        var causes = $(".causes").val();
        var reason = $(".reason").val();
        window.location.href = "/system/orderExceptionManagement/uploadExcelOderExceptionManagement?causes=" + causes +"&reason=" + reason;
    }

    /**
     * 弹出编辑框
     */
    function openEditAttendanceRules(edit) {
        layer.open({
            type : 2,
            title: '编辑考勤规则',
            offset: '10%',
            area: ['800px', '460px'], //宽高
            content: '/page/assetsSystem/orderExceptionManagement/html/orderExceptionManagementEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".orderExceptionManagementId").val(edit.orderExceptionManagementId);
                    body.find(".causes select").val(edit.causes);
                    body.find(".reason").val(edit.reason);
                    body.find(".remarks").val(edit.remarks);
                    form.render();
                }
            }
        });
    };
})
