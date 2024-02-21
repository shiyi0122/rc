layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitScenicSpotBillingRules)', function(data){
        console.log(data.field);
        window.resource("/system/scenicSpot/editScenicSpot", data.field, function (data) {}, false,"POST",'scenicSpotBillingRulesListTable');
        return false;
    });

    form.verify({
        integer: [
            /^[1-9]\d*$/
            , '只能输入正整数'
        ]
    });



})