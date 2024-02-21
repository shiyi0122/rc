layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitCorpusMediaExtend)', function(data){
        window.resource("/system/corpusAudioAndVideo/editCorpusMediaExtend", data.field, function (data) {}, false,"POST",'viewDetailsListTable');
        return false;
    });

})