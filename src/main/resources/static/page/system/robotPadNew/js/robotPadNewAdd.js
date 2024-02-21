layui.use(['form','layer','jquery','upload','laytpl','laydate'],function(){
    var form = layui.form;
    $ = layui.jquery;
    upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitRobotPad)', function(data){
        var formData = new FormData($("#robotPadForm")[0]);

        var type = "scenicSpotIds";
        var typeOne = "completeIds";
        //获取当前多选选中的值
        var selectArr = xmRender.getValue('valueStr');
        console.log("selectArr: ", selectArr)
        formData.append(type, selectArr)

        var selectArrOne = xmRenderOne.getValue("valueStr");
        formData.append(typeOne, selectArrOne)

        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "/system/robotPadNew/addRobotPadNew",
            data: formData,
            type: "POST",
            async: true,
            cache:false,
            contentType: false,   //jax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
            processData: false,   //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
            success: function (e) {
                if (e.state == "200") {
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg(e.msg, {icon: 6});
                        parent.layer.closeAll("iframe");
                        parent.layui.table.reload('robotPadListTable');
                    },500);
                } else {
                    setTimeout(function(){
                        top.layer.close(index);
                        parent.layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                    },500);

                }
            }
        });
        return false;
    });

    upload.render({
        elem: '#file',
        auto: false,
        acceptMime: '.apk',
        exts: 'apk'
    });

})