layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotExpandList',
        url : '/business/scenicSpotExpand/getScenicSpotExpandList',
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
        id : "scenicSpotExpandListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'imageId', title: '景区照片ID', minWidth:100, align:"center"},
            {field: 'scenicSpotArea', title: '景区面积', minWidth:100, align:"center"},
            {field: 'revenueYear', title: '年收入', minWidth:100, align:"center"},
            {field: 'rewardRate', title: '回报率', minWidth:100, align:"center"},
            {field: 'realData', title: '数据状态', minWidth:100, align:"center",templet:function(d){
                    if(d.realData == "1"){
                        return "真实获取";
                    }else if(d.realData == "0"){
                        return "后台输入";
                    }
                }},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#scenicSpotExpandListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(scenicSpotExpandList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("BUSINESS_SCENIC_SPOT_EXPAND_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditScenicSpotExpand(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_SCENIC_SPOT_EXPAND_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/scenicSpotExpand/delScenicSpotExpand", {id : data.id}, function (data) {}, false,"GET","scenicSpotExpandListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotExpandListTable",{
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

    //点击添加按钮
    $('#btnAddScenicSpotExpand').click(function(){
        window.resources("BUSINESS_SCENIC_SPOT_EXPAND_INSERT", function (e) {
            if (e.state == "200") {
                openAddScenicSpotExpand();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddScenicSpotExpand() {
        layer.open({
            type : 2,
            title: '添加景区拓展',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/business/scenicSpotExpand/html/scenicSpotExpandAdd.html'
        });
    };

	/**
	 * 弹出修改框
	 */
    function openEditScenicSpotExpand(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改景区拓展',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/business/scenicSpotExpand/html/scenicSpotExpandEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".spotId").val(edit.scenicSpotId);
                        body.find(".imageId").val(edit.imageId);
                        body.find(".scenicSpotArea").val(edit.scenicSpotArea);
                        body.find(".revenueYear").val(edit.revenueYear);
                        body.find(".rewardRate").val(edit.rewardRate);
                        body.find(".provinceId").val(edit.provinceId);
                        body.find(".cityId").val(edit.cityId);
                        body.find(".regionId").val(edit.regionId);
                        body.find("#scenicSpotIntroduce").html(edit.scenicSpotIntroduce);
                        form.render();
                    }
                }
            });
        },500);
    }

})