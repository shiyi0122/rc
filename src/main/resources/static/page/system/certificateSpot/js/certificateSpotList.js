layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#certificateSpotList',
        url : '/system/certificateSpot/getCertificateSpotList',
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
        id : "certificateSpotListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'merchantName', title: '主体名称', minWidth:100, align:"center"},
            {field: 'createDate', title: '分配时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#certificateSpotListBar',fixed:"right",align:"center"}
        ]]
    });

	/**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("certificateSpotListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotId").val()
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
    table.on('tool(certificateSpotList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEM_CERTIFICATE_SPOT_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditCertificateSpot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_CERTIFICATE_SPOT_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/certificateSpot/delCertificateSpot", {certificateSpotId : data.certificateSpotId}, function (data) {}, false,"GET","certificateSpotListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddCertificateSpot').click(function(){
        window.resources("SYSTEM_CERTIFICATE_SPOT_INSERT", function (e) {
            if (e.state == "200") {
                openAddCertificateSpot();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddCertificateSpot() {
        layer.open({
            type : 2,
            title: '添加景区证书',
            offset: '10%',
            area: ['800px', '330px'],
            content: '/page/system/certificateSpot/html/certificateSpotAdd.html',
            tableId: '#certificateSpotList'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditCertificateSpot(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改景区证书',
                offset: '10%',
                area: ['800px', '330px'],
                content: '/page/system/certificateSpot/html/certificateSpotEdit.html',
                tableId: '#certificateSpotList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".certificateSpotId").val(edit.certificateSpotId);
                        body.find(".scenicSpotIds").val(edit.scenicSpotId);
                        body.find(".certificateIds").val(edit.certificateId);
                        form.render();
                    }
                }
            });
        },500);
    }

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYSTEM_CERTIFICATE_SPOT_DOWNLOADEXCEL", function (e) {
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
        var scenicSpotId = $(".scenicSpotId").val();
        window.location.href = "/system/certificateSpot/uploadExcelCertificateSpot?scenicSpotId="+scenicSpotId;
    }

})
