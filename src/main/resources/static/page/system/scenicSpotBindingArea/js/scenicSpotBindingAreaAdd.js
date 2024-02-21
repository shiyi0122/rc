layui.use(['form','layer','jquery','upload','layedit'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var layedit = layui.layedit;
    $ = layui.jquery;
    upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitScenicSpotBinding)', function(data){
        var formData = new FormData($("#userForm")[0]);
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        // window.resource("/system/scenicSpotBinding/addScenicSpotBinding", data.field, function (data) {}, false,"POST",'scenicSpotBindingListTable');

        $.ajax({
            url: "/system/scenicSpotBinding/addScenicSpotBinding",
            data: formData,
            type: "POST",
            async: true,
            cache: false,
            contentType: false,   //jax 中 contentType 设置为 false 是为了避免 JQuery 对其操作，从而失去分界符，而使服务器不能正常解析文件
            processData: false,   //当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
            success: function (e) {
                if (e.state == "200") {
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg(e.msg, {icon: 6});
                        parent.layer.closeAll("iframe");
                        parent.layui.table.reload('scenicSpotBindingListTable');
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


    /*城市菜单*/
    form.on('select(scenicSpotPid)',function(data){
        $('#scenicSpotSid').empty();//选择省的时候清空城市地区的值
        $('#scenicSpotSid').html('<option value="">市</option>');
        // $('#county').empty();
        // $('#county').html('<option value="">区/县</option>');
        if(data.value){
            $.ajax({
                type:"POST",
                url:"/system/scenicSpotBinding/getSpotBindingCity",
                data:{"pid":data.value},
                async : false,
                success:function(data){
                    var item = data.data;
                    var htt = '<option value="">选择市</option>';
                    $.each(item, function (k, v) {
                        htt += '<option value="'+ item[k].scenicSpotFid +'">'+ item[k].scenicSpotFname +'</option>'
                    });
                    $("#scenicSpotSid").html(htt);
                    form.render('select');
                }
            });
        }
    })


})