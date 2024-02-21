layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    $ = layui.jquery;
    //监听提交
    form.on('submit(btnSubmitScenicSpotPad)', function(data){
        window.resource("/system/scenicSpot/addScenicSpotPad", data.field, function (data) {}, false,"POST",'scenicSpotPadListTable');
        return false;
    });

})