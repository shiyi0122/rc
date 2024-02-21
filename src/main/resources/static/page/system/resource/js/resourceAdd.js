/**
 * 详情对话框
 */
var MenuInfoDlg = {
    data: {
        pid: "",
        pcodeName: ""
    }
};
layui.use(['layer', 'form'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var layer = layui.layer;

    // 点击父级菜单
    $('#resPcodeName').click(function () {
         layer.open({
            type: 2,
            title: '父级菜单',
            area: ['300px', '400px'],
            content: '/page/system/resource/html/tree_dlg.html',
            end: function () {
                $("#resPcode").val(MenuInfoDlg.data.pid);
                $("#resPcodeName").val(MenuInfoDlg.data.pcodeName);
            }
        });
    });

    //监听提交
    form.on('submit(btnSubmit)', function(data){
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type: 'POST',
            url: '/system/res/addResource',
            data: data.field,
            success: function (e) {
                if (e.state == "200") {
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg(e.msg, {icon: 6});
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    },500);
                } else {
                    top.layer.close(index);
                    parent.layer.msg(e.msg, {icon: 5,time: 8*1000,shift: 6});
                }
            },
            dataType: 'json'
        });
        return false;
    });

});