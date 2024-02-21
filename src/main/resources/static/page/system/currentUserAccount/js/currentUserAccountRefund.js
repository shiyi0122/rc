layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitPayBack)', function(data){
        // var arr_box = [];
        // $('input[type=checkbox]:checked').each(function() {
        //     arr_box.push($(this).val());
        // });
        var index = top.layer.msg('储值余额退款中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "/system/currentUserAccount/userAccountOrderRefund",
            data: {
                currentUserId : data.field.accountUserId,
                //orderNumber : data.field.orderNumber,
                // currentUserPhone : data.field.currentUserPhone,
                // orderRobotCode : data.field.orderRobotCode,
                // orderScenicSpotName : data.field.orderScenicSpotName,
                // orderStartTime : data.field.orderStartTime,
                // totalTime : data.field.totalTime,
                // orderAmount : data.field.orderAmount,
                // orderRefundAmount : data.field.orderRefundAmount,
                // payMoney : data.field.payMoney,
                //reason : arr_box.toString(),
                reasonsRefunds : data.field.reasonsRefunds
            },
            type: "POST",
            cache:false,
            success: function (e) {
                if (e.state == "200") {
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg(e.msg, {icon: 6});
                        parent.layer.closeAll("iframe");
                        parent.layui.table.reload('currentUserAccountListTable');
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