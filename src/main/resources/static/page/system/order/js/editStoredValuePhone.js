layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    // $(".inp-box").hide();
    // //监听复选框
    // form.on('checkbox(switchTest)', function(data){
    //     var vals = data.value
    //     var other=$("#other")
    //     if($("#other").prop("checked") == true){
    //         $(".inp-box").show()
    //     }else{
    //         $(".inp-box").hide()
    //     }
    //
    // });
    //监听提交
    form.on('submit(btnSubmitPayBack)', function(data){
        var arr_box = [];
        $('input[type=checkbox]:checked').each(function() {
            arr_box.push($(this).val());
        });
        var index = top.layer.msg('订单修改中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "/system/order/orderTransformation",
            data: {
                orderId : data.field.orderId,
                storedPhone : data.field.storedPhone

            },
            type: "POST",
            cache:false,
            success: function (e) {
                if (e.state == "200") {
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg(e.msg, {icon: 6});
                        parent.layer.closeAll("iframe");
                        parent.layui.table.reload('orderListTable');
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
})