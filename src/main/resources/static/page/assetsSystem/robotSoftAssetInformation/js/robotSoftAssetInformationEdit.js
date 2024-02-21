layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmitSoftAssetInformation)', function(data){
        window.resource("/system/robotSoftAssetInformation/editRobotSoftAssetInformation", data.field, function (data) {}, false,"POST",'robotSoftAssetInformationListTable');
        return false;
    });

})