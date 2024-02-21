layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#advertisingList',
        url : '/system/advertising/getAdvertisingList',
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
        id : "advertisingListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'advertisingUrl', title: '轮播图预览', minWidth:100, align:"center", templet: function(d) {
                     var url ="https://topsroboteer.com.cn/" + d.advertisingUrl;
                    //  var url ="https://jxzy-robot.oss-cn-beijing.aliyuncs.com/pro/" + d.advertisingUrl;
                    return '<img src="' + url + '" class="layui-upload-img clickImg" style="height:20px;width=20px"  />';
                }},
            {field: 'advertisingName', title: '轮播图名称', minWidth:100, align:"center"},
            {field: 'advertisingOrder', title: '显示顺序', minWidth:100, align:"center"},
            {field: 'advertisingValid', title: '是否启用', align:'center',templet:function(d){
                if(d.advertisingValid == "1"){
                    return "启用";
                }else if(d.advertisingValid == "0"){
                    return "禁用";
                }
            }},
            {field: 'createTime', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#advertisingListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(advertisingList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_ADVERTISING_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditCertificateSpot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_ADVERTISING_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/advertising/delAdvertising", {advertisingId : data.advertisingId}, function (data) {}, false,"GET","advertisingListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "download"){ //下载
            window.resources("RESOURCES_ADVERTISING_DOWNLOAD", function (e) {
                if (e.state == "200") {
                    window.location.href = "/system/advertising/download?fileName=" + data.advertisingUrl
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "forbidden"){ //修改为启用
            window.resources("RESOURCES_ADVERTISING_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为启用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/advertising/editValid",
                            data: {
                                advertisingId : data.advertisingId,
                                advertisingValid : 1
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("advertisingListTable");
                                    },500);
                                }else if (data.state == "400"){
                                    setTimeout(function(){
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                                    },500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "open"){ //修改为禁用
            window.resources("RESOURCES_ADVERTISING_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为禁用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/advertising/editValid",
                            data: {
                                advertisingId : data.advertisingId,
                                advertisingValid : 0
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("advertisingListTable");
                                    },500);
                                }else if (data.state == "400"){
                                    setTimeout(function(){
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                                    },500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddAdvertising').click(function(){
        window.resources("RESOURCES_ADVERTISING_INSERT", function (e) {
            if (e.state == "200") {
                openAddAdvertising();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    $(document).on('click','img.clickImg',function(obj){
        var imgHtml = "<img src='" + obj.toElement.currentSrc + "'width='100%' height='100%'/>";
        //弹出层
        layer.open({
            type: 1,
            offset: 'auto',
            // area: ['auto','auto'],
            area: [80 + '%',100+'%'] , //原图显示
            shadeClose:true,
            scrollbar: false,
            title: "图片预览", //不显示标题
            content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function () {
                //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
            }
        });

    });

    /**
     * 弹出添加框
     */
    function openAddAdvertising() {
        layer.open({
            type : 2,
            title: '添加轮播图',
            offset: '10%',
            area: ['800px', '460px'],
            content: '/page/system/advertising/html/advertisingAdd.html',
            tableId: '#advertisingList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type : 'POST',
                    url : '/system/scenicSpot/getCurrentScenicSpot',
                    dataType : 'json',
                    success:function (data) {
                        if (data.state == "200"){
                            body.find(".advertisingScenicSpotId").val(data.data.scenicSpotId);//景区ID
                            body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                            form.render();
                        }else if(data.state == "400"){
                            layer.msg(data.msg);
                        }
                    }
                })
            }
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
                title: '修改轮播图',
                offset: '10%',
                area: ['800px', '460px'],
                content: '/page/system/advertising/html/advertisingEdit.html',
                tableId: '#advertisingList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    $.ajax({
                        type : 'POST',
                        url : '/system/scenicSpot/getCurrentScenicSpot',
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                if(edit){
                                    top.layer.close(dex);
                                    body.find(".advertisingScenicSpotId").val(data.data.scenicSpotId);//景区ID
                                    body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                                    body.find(".advertisingName").val(edit.advertisingName);
                                    body.find(".advertisingOrder").val(edit.advertisingOrder);
                                    body.find(".advertisingId").val(edit.advertisingId);
                                    form.render();
                                }
                            }else if(data.state == "400"){
                                layer.msg(data.msg);
                            }
                        }
                    })
                }
            });
        },500);
    }

})
