layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitScenicSpotGpsCoordinate)', function(data){
        window.resource("/system/scenicSpot/editBtnSubmitScenicSpotGpsCoordinate", data.field, function (data) {}, false,"POST",'scenicSpotGpsCoordinateListTable');
        return false;
    });

})