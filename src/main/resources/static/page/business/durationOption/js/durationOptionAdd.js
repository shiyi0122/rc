layui.use(['form','layer','jquery','upload','layedit'],function(){
    var form = layui.form;
    $ = layui.jquery;
    upload = layui.upload;

    //监听提交
    form.on('submit(btnSubmitJournalismArticle)', function(data){
        window.resource("/business/durationOption/addDurationOption", data.field, function (data) {}, false,"POST",'journalismArticleListTable');
        return false;
    });

})