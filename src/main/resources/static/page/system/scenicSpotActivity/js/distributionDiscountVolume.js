layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    $ = layui.jquery;
    //监听提交
    form.on('submit(btnDistributionDiscountVolume)', function(data){
        window.resource("/system/currentUserCoupons/addCurrentUserCoupons", data.field, function (data) {}, false,"POST",'scenicSpotActivityListTable');
        return false;
    });

})