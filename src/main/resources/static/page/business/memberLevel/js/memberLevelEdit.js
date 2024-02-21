layui.use(['form','layer','jquery','upload','layedit'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var laydate = layui.laydate;
    var layedit = layui.layedit;
    $ = layui.jquery;
    upload = layui.upload;

    //监听提交
    form.on('submit(btnSubmitMemberLevel)', function(data){
        window.resource("/business/memberLevel/editMemberLevel", data.field, function (data) {}, false,"POST",'memberLevelListTable');
        return false;
    });

    upload.render({
        elem: '#file',
        url: 'https://oursjxzy.topsroboteer.ac.cn/common/upload_file',
        accept: 'file',//普通文件
        exts: 'jpg|png',//只允许上传图片
        done: function(res){
            if (res.status == "200") {
                $(".attachId").attr("value",res.data.attachId);
            }else{
                layer.msg('上传失败');
            }
        }
    });

})