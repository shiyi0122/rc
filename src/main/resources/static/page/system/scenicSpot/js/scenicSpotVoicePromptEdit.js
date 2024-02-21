layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitScenicSpotVoicePrompt)', function(data){
        window.resource("/system/scenicSpot/editScenicSpot", data.field, function (data) {}, false,"POST",'scenicSpotVoicePromptListTable');
        return false;
    });



    //检查输入的账号长度
    form.verify({
        account: function(value, item){
            var max = item.getAttribute('lay-max');
            if(value.length > max){
                return '内容不能超过'+max+'个字';
            }
        }
    });

})