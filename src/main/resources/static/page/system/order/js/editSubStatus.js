layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    $("#deductibleAmount").hide();
    $("#orderDiscount").hide();
    //监听提交
    form.on('submit(btnSubmitSubStatus)', function(data){
        window.resource("/system/order/updateSubMethod", data.field, function (data) {}, false,"POST",'orderListTable');
        return false;
    });

    form.on('select(group)',function(data){
        var subMethod = data.value
        var discount = $(".orderDiscount").val();
        var deductibleAmount = $(".deductibleAmount").val();
        var dispatchingFee = $(".dispatchingFee").val();//调度费
        if(subMethod == 1){
            if (parseFloat(deductibleAmount) == 0 || deductibleAmount == "" || deductibleAmount == null) {
                parent.layer.msg("请微信支付", {icon: 5,time: 1000,shift: 6});
                $("#subMethod option[value='0']").prop("selected",true);
                form.render('select','group');
            }else{
                $("#coupon").hide();
                $("#orderDiscount").show();
                $("#deductibleAmount").hide();
                var discount1 = "0" + "." + discount;
                $(".orderAmounts").val((parseFloat($(".orderAmount").val()) * parseFloat(discount1) + parseFloat($(".dispatchingFee").val())).toFixed(2));
                var a = $(".orderAmounts").val();
                if (parseFloat(a) > parseFloat(deductibleAmount)) {
                    parent.layer.msg("请储值抵扣支付", {icon: 5,time: 1000,shift: 6});
                    $("#coupon").hide();
                    $("#orderDiscount").show();
                    $("#deductibleAmount").show();
                    $(".orderAmounts").val((parseFloat(a) - parseFloat(deductibleAmount)).toFixed(2))
                    $("#subMethod").find("option[value='2']").prop("selected",true);
                    form.render('select','group');
                }
            }
        }
        if(subMethod == 0){
            $("#coupon").show();
            $("#orderDiscount").hide();
            $("#deductibleAmount").hide();
            $(".orderAmounts").val((parseFloat($(".orderAmount").val()) + parseFloat($(".dispatchingFee").val())).toFixed(2));
        }
        if(subMethod == 2){
            var discount1 = "0" + "." + discount;
            var aa = (parseFloat($(".orderAmount").val()) * parseFloat(discount1) + parseFloat($(".dispatchingFee").val())).toFixed(2);
            if (parseFloat(deductibleAmount) == 0 || deductibleAmount == "" || deductibleAmount == null) {
                parent.layer.msg("请微信支付", {icon: 5,time: 1000,shift: 6});
                $("#subMethod option[value='0']").prop("selected",true);
                form.render('select','group');
            }else if(parseFloat(aa) > parseFloat(deductibleAmount)){
                $("#coupon").hide();
                $("#orderDiscount").show();
                $("#deductibleAmount").show();
                $(".orderAmounts").val((parseFloat(aa) - parseFloat(deductibleAmount)).toFixed(2));
            }else if (parseFloat(aa) <= parseFloat(deductibleAmount)) {
                parent.layer.msg("请储值支付", {icon: 5,time: 1000,shift: 6});
                $("#subMethod").find("option[value='1']").prop("selected",true);
                form.render('select','group');
                $("#coupon").hide();
                $("#orderDiscount").show();
                $("#deductibleAmount").hide();
                var discount1 = "0" + "." + discount;
                $(".orderAmounts").val((parseFloat($(".orderAmount").val()) * parseFloat(discount1) + parseFloat($(".dispatchingFee").val())).toFixed(2));
            }
        }
    })
})