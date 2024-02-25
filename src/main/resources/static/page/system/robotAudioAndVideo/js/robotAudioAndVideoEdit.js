layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;

    var mediaResourcesType = $(".mediaResourcesType").val();
    if (mediaResourcesType == 1) {
        $("#videoForm").css("display","none");
        $("#audioForm").css("display","block");
        $("input:radio[value='"+mediaResourcesType+"']").attr('checked','true');
        form.render();
    }else if (mediaResourcesType == 2) {
        $("#videoForm").css("display","block");
        $("#audioForm").css("display","none");
        $("input:radio[value='"+mediaResourcesType+"']").attr('checked','true');
        form.render();
    }
    //监听提交音频
    form.on('submit(btnAddAudioForm)', function(data){
        var formData = new FormData($("#audioForm")[0]);
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "/system/robotAudioAndVideo/editRobotAudio",
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
                        parent.layui.table.reload('robotAudioAndVideoListTable');
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

    //监听提交视频
    form.on('submit(btnAddVideoForm)', function(data){
        var formData = new FormData($("#videoForm")[0]);
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "/system/robotAudioAndVideo/editRobotVideo",
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
                        parent.layui.table.reload('robotAudioAndVideoListTable');
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

    form.on('radio', function(data){
        if(data.value == "1") {
            //data.elem.style = "bgcolor:red"这种方式改变当前radio的颜色无效，只能在当前radio结点加一个父节点div，改变div中的颜色
            $("#videoForm").css("display","none");
            $("#audioForm").css("display","block");
        }
        if(data.value == "2") {
            $("#videoForm").css("display","block");
            $("#audioForm").css("display","none");
        }
    });

    upload.render({
        elem: '#fileAudio',
        auto: false,
        accept: 'audio' //音频
    });
    upload.render({
        elem: '#fileVideo',
        auto: false,
        accept: 'video' //视频
    });

})