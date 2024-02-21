layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitScenicSpotCapPrice)', function(data){
        window.resource("/system/scenicSpot/addCapPrice", data.field, function (data) {}, false,"POST",'scenicSpotCappriceListTable');
        return false;
    });

})