layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotBindingList',
        url : '/system/scenicSpotBinding/getScenicSpotBindingList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where:{
            scenicSpotType: 1
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "scenicSpotBindingListTable",
        cols : [[
            {field: 'scenicSpotFname', title: '景区归属地名称', minWidth:100, align:"center"},
            {field: 'cityLabel', title: '城市标签', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#scenicSpotBindingListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".scenicSpotBindingNameVal").val() != ''){
            table.reload("scenicSpotBindingListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    scenicSpotFname: $(".scenicSpotBindingNameVal").val(),  //搜索的关键字
                    scenicSpotType: 1

                }
            })
        }else{
            layer.msg("请输入搜索的归属地名称");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(scenicSpotBindingList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEM_SCENICSPOTBINDING_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditScenicSpotBinding(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_SCENICSPOTBINDING_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/scenicSpotBinding/delScenicSpotBinding", {scenicSpotFid : data.scenicSpotFid}, function (data) {}, false,"GET","scenicSpotBindingListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddScenicSpotBinding').click(function(){
        window.resources("SYSTEM_SCENICSPOTBINDING_INSERT", function (e) {
            if (e.state == "200") {
                openAddScenicSpotBinding();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddScenicSpotBinding(edit) {
        layer.open({
            type : 2,
            title: '添加归属地',
            offset: '10%',
            area: ['800px', '350px'],
            content: '/page/system/scenicSpotBinding/html/scenicSpotBindingAdd.html',
            tableId: '#scenicSpotBindingList'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditScenicSpotBinding(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改归属地',
                offset: '10%',
                area: ['800px', '350px'],
                content: '/page/system/scenicSpotBinding/html/scenicSpotBindingEdit.html',
                tableId: '#scenicSpotBindingList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".scenicSpotFid").val(edit.scenicSpotFid);
                        body.find(".scenicSpotFname").val(edit.scenicSpotFname);
                        body.find(".scenicSpotType").val(edit.scenicSpotType);
                        body.find(".cityLabel").val(edit.cityLabel);
                        form.render();
                    }
                }
            });
        },500);
    }

})
