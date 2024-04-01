layui.use(['form', 'layedit', 'laydate'], function(){
    var form = layui.form,
        layer = layui.layer,
        layedit = layui.layedit,
        laydate = layui.laydate;
    $(".inp-box").hide()
    var test1 = ""
    // 监听提交
    form.on('radio(switchTest)', function(data){
        var vals = data.value;
        console.log(JSON.stringify(data));
        test1 = vals;
        var other = $("#other");
        if ($("#other").prop("checked") == true) {
            $(".inp-box").show();
        } else {
            $(".inp-box").hide();
        }
    });

    form.on('submit(btnSubmit)', function(data){
        console.log(data)
        var arr_box = [];
        $('input[type=radio]:checked').each(function() {
            arr_box = [$(this).val()];
        });
        console.log(arr_box)
        var dex = top.layer.msg('状态修改中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "/system/order/updateOrderState",
            data: {
                orderNumber : $(".orderNumber").val(),
                orderStatus : $(".orderStatus").val(),
                reason : arr_box.toString(),
                reasonsRefunds : $(".reasonsRefunds").val(),
                reasonsRefundsTrue : $(".reasonsRefundsTrue").val()
            },
            type: "POST",
            cache:false,
            success: function (data) {
                if (data.state == "200"){
                    setTimeout(function(){
                        top.layer.msg(data.msg, {icon: 6});
                        top.layer.close(dex);
                        parent.layer.closeAll("iframe");
                        parent.layui.table.reload("orderListTable");
                    },500);
                }else if (data.state == "400"){
                    setTimeout(function(){
                        top.layer.close(dex);
                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                    },500);
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});