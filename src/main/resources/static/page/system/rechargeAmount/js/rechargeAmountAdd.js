layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitRecharge)', function(data){
        window.resource("/system/rechargeAmount/addRechargeAmount", data.field, function (data) {}, false,"POST",'rechargeAmountListTable');
        return false;
    });

})