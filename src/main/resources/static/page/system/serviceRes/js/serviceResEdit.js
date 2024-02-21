layui.use(['form','layer','upload'],function(){
    var form = layui.form,
        upload = layui.upload;

    //监听提交
    form.on('submit(btnSubmitServiceRes)', function(data){
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var formData = new FormData($("#Form")[0]);
        $.ajax({
            url: '/system/serviceRes/editServiceRes',
            type: 'POST',
            data: formData,
            async: true,
            cache: false,
            contentType: false,
            mimeType: "multipart/form-data",
            processData: false,
            dataType: "json",
            success: function (e) {
                if (e.state == "200") {
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg(e.msg, {icon: 6});
                        parent.layer.closeAll("iframe");
                        parent.layui.table.reload("serviceResListTable");
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
        auto: false
    });

})