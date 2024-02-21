layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitScenicSpot)', function(data){
        window.resource("/system/scenicSpot/addScenicSpot", data.field, function (data) {}, false,"POST",'scenicSpotListTable');
        return false;
    });

})