layui.use(['layer','table','laytpl','laydate'],function(){
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    /**
     * 开始时间
     */
    laydate.render({
        elem: '#startTime'
    });

    /**
     * 结束时间
     */
    laydate.render({
        elem: '#endTime'
    });

    //封装的ajax请求。
    window.resource = function (path, data, fnSuccess, async, type,tableId) {
        if (!type || type.toLowerCase() != "POST") {
            type = "GET";
        }
        var _data = data;

        if (async == undefined) {
            async = true;
        }
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: path,
            data: data,
            type: type,
            cache:false,
            success: function (e) {
                if (e.state == "200") {
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg(e.msg, {icon: 6});
                        parent.layer.closeAll("iframe");
                        parent.layui.table.reload(tableId);
                    },500);
                } else {
                    setTimeout(function(){
                        top.layer.close(index);
                        parent.layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                    },500);

                }
            },
            async: async
        });
    }


    //封装的ajax请求。
    window.resourceN = function (path, data, fnSuccess, async, type,tableId) {
        if (!type || type.toLowerCase() != "POST") {
            type = "GET";
        }
        var _data = data;

        if (async == undefined) {
            async = true;
        }
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: path,
            data: data,
            type: type,
            cache:false,
            success: function (e) {
                if (e.state == "200") {
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg(e.msg, {icon: 6});
                        layer.close(index);
                        layui.table.reload(tableId);
                    },500);
                } else {
                    setTimeout(function(){
                        top.layer.close(index);
                        parent.layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                    },500);

                }
            },
            async: async
        });
    }



    //封装查询权限的ajax请求。
    window.resources = function (data, fnSuccess, async,type) {
        if (!type || type.toLowerCase() != "POST") {
            type = "GET";
        }
        var _data = data;
        if (async == undefined) {
            async = true;
        }
        $.ajax({
            url: "/system/permissionId/getPermissionId",
            data: {
                permissionId : data
            },
            type: type,
            cache:false,
            success: fnSuccess,
            async: async
        });
    }
    //封装删除的ajax请求。
    window.resourcedel = function (path, data, fnSuccess, async, type,tableId) {
        if (!type || type.toLowerCase() != "POST") {
            type = "GET";
        }

        var _data = data;

        if (async == undefined) {
            async = true;
        }
        layer.confirm('确定删除吗？',{icon:3, title:'提示信息'},function(index){
        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: path,
            data: data,
            type: type,
            cache:false,
            success: function (data) {
                if (data.state == "200"){
                    setTimeout(function(){
                        top.layer.msg(data.msg, {icon: 6});
                        top.layer.close(dex);
                        layer.close(index);
                        layui.table.reload(tableId);
                    },500);
                }else if (data.state == "400"){
                    setTimeout(function(){
                        top.layer.close(dex);
                        layer.close(index);
                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                    },500);
                }
            },
            async: async
        });
        });

    }


    // 缓存当前操作的是哪个表格的哪个tr的哪个td
    $(document).off('mousedown','.layui-table-grid-down').on('mousedown','.layui-table-grid-down',function(event){
        table._tableTrCurr = $(this).closest('td');
    });

    $(document).off('click','.layui-table-tips-main [lay-event]') .on('click','.layui-table-tips-main [lay-event]',function(event){
        var elem = $(this);
        var tableTrCurr = table._tableTrCurr;
        if(!tableTrCurr){
            return;
        }
        var layerIndex = elem.closest('.layui-table-tips').attr('times');
        // 关闭当前这个显示更多的tip
        layer.close(layerIndex);
        table._tableTrCurr.find('[lay-event="' + elem.attr('lay-event') + '"]').first().click();
    });
})