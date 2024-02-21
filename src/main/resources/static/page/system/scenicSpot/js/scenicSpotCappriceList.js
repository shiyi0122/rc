layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotCappriceList',
        url : '/system/scenicSpot/getScenicSpotCapPriceList',
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
        id : "scenicSpotCappriceListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'normalCapOneTime', title: '一档工作日封顶时间', minWidth:100, align:"center"},
            {field: 'normalCapOnePrice', title: '一档工作日封顶价格', minWidth:100, align:"center"},
            {field: 'normalCapOneUnitPrice', title: '一档工作日封顶单价', minWidth:100, align:"center"},
            {field: 'normalCapTwoTime', title: '二档工作日封顶时间', minWidth:100, align:"center"},
            {field: 'normalCapTwoPrice', title: '二档工作日封顶价格', minWidth:100, align:"center"},
            {field: 'normalCapTwoUnitPrice', title: '二档工作日封顶单价', minWidth:100, align:"center"},
            {field: 'weekendCapOneTime', title: '一档周末封顶时间', minWidth:100, align:"center"},
            {field: 'weekendCapOnePrice', title: '一档周末封顶价格', minWidth:100, align:"center"},
            {field: 'weekendCapOneUnitPrice', title: '一档周末封顶单价', minWidth:100, align:"center"},
            {field: 'weekendCapTwoTime', title: '二档周末封顶时间', minWidth:100, align:"center"},
            {field: 'weekendCapTwoPrice', title: '二档周末封顶价格', minWidth:100, align:"center"},
            {field: 'weekendCapTwoUnitPrice', title: '二档周末封顶单价', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:100, templet:'#scenicSpotCappriceListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据景区名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotCappriceListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotName: $(".scenicSpotNameVal").val()
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
    table.on('tool(scenicSpotCappriceList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "edit"){ //编辑
            window.resources("SYSTEM_SCENIC_SPOT_UPDATE_CAPPRICE", function (e) {
                if (e.state == "200") {
                    updateScenicSpotCapprice(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "del"){
            window.resources("SYSTEM_SCENIC_SPOT_UPDATE_CAPPRICE", function (e) {
                if (e.state == "200") {
                    window.resourcedel("/system/scenicSpot/delCapPrice", {capPriceId : data.capPriceId,scenicSpotId : data.scenicSpotId}, function (data) {}, false,"GET","scenicSpotCappriceList");
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加景区封顶价格
    $('#btnAddScenicSpotCapPrice').click(function(){
        window.resources("SYSTEM_SCENIC_SPOT_ADD_CAPPRICE", function (e) {
            if (e.state == "200") {
                openAddScenicSpotCapPrice();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 添加景区封顶价格
     */
    function openAddScenicSpotCapPrice(){
        layer.open({
            type : 2,
            title: '添加景区封顶价格',
            offset: '10%',
            area: ['60%', '80%'],
            content: '/page/system/scenicSpot/html/scenicSpotCapPriceAdd.html'
        });
    }

    /**
     * 修改景区封顶价格
     * @param edit
     */
    function updateScenicSpotCapprice(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        layer.open({
            type : 2,
            title: '修改景区封顶价格',
            offset: '10%',
            area: ['60%', '75%'],
            content: '/page/system/scenicSpot/html/scenicSpotCapPriceEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    $.ajax({
                        type : 'POST',
                        url : '/system/scenicSpot/loadEditScenicSpotCapPrice',
                        data : {scenicSpotId : edit.scenicSpotId},
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                top.layer.close(dex);
                                body.find(".capPriceId").val(data.data.capPriceId);
                                body.find(".scenicSpotId").val(data.data.scenicSpotId);
                                body.find(".scenicSpotName").val(data.data.scenicSpotName);
                                body.find(".normalCapOneTime").val(data.data.normalCapOneTime);
                                body.find(".normalCapOnePrice").val(data.data.normalCapOnePrice);
                                body.find(".normalCapOneUnitPrice").val(data.data.normalCapOneUnitPrice);
                                body.find(".normalCapTwoTime").val(data.data.normalCapTwoTime);
                                body.find(".normalCapTwoPrice").val(data.data.normalCapTwoPrice);
                                body.find(".normalCapTwoUnitPrice").val(data.data.normalCapTwoUnitPrice);
                                body.find(".weekendCapOneTime").val(data.data.weekendCapOneTime);
                                body.find(".weekendCapOnePrice").val(data.data.weekendCapOnePrice);
                                body.find(".weekendCapOneUnitPrice").val(data.data.weekendCapOneUnitPrice);
                                body.find(".weekendCapTwoTime").val(data.data.weekendCapTwoTime);
                                body.find(".weekendCapTwoPrice").val(data.data.weekendCapTwoPrice);
                                body.find(".weekendCapTwoUnitPrice").val(data.data.weekendCapTwoUnitPrice);
                                form.render();
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

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYSTEM_SCENIC_SPOT_DOWNLOADEXCEL", function (e) {
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
        var scenicSpotName = $(".scenicSpotNameVal").val();
       // console.log(scenicSpotName);
        window.location.href = "/system/scenicSpot/uploadScenicSpotCapPriceExcel?scenicSpotName="+scenicSpotName;
    }


})
