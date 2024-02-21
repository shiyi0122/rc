layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#bannerList',
        url : '/business/banner/getBannerList',
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
        id : "bannerListTable",
        cols : [[
            {field: 'title', title: 'Banner图名称', minWidth:100, align:"center"},
            {field: 'appImageId', title: '手机端图片ID', minWidth:100, align:"center"},
            {field: 'webImageId', title: 'web端图片ID', minWidth:100, align:"center"},
            {field: 'url', title: 'banner图URL', minWidth:100, align:"center"},
            {field: 'state', title: 'banner图状态', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "正常";
                    }else if(d.state == "-1"){
                        return "禁用";
                    }else if(d.state == "-2"){
                        return "删除";
                    }
                }},
            {field: 'type', title: '轮播图类型', minWidth:100, align:"center",templet:function(d){
                    if(d.type == "1"){
                        return "首页轮播图";
                    }else if(d.type == "2"){
                        return "关于我们轮播图";
                    }else if(d.type == "3"){
                        return "启动页轮播图"
                    }
                }},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#bannerListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(bannerList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("BUSINESS_BANNER_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditBanner(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_BANNER_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/banner/delBanner", {id : data.id}, function (data) {}, false,"GET","bannerListTable");
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
        table.reload("bannerListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                type: $(".typesOf").val()
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
    $('#btnAddBanner').click(function(){
        window.resources("BUSINESS_BANNER_INSERT", function (e) {
            if (e.state == "200") {
                openAddBanner();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddBanner() {
        layer.open({
            type : 2,
            title: '添加Banner图',
            offset: '10%',
            area: ['800px', '500px'],
            content: '/page/business/banner/html/bannerAdd.html'
        });
    };

    function openEditBanner(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改Banner图',
                offset: '10%',
                area: ['800px', '460px'],
                content: '/page/business/banner/html/bannerEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".title").val(edit.title);
                        body.find(".url").val(edit.url);
                        body.find(".webImageId").val(edit.webImageId);
                        body.find(".appImageId").val(edit.appImageId);
                        body.find(".type").val(edit.type);
                        form.render();
                    }
                }
            });
        },500);
    }

})