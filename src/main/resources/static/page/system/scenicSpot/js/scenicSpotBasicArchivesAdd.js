layui.use(['form','layer','jquery','upload','layedit'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var laydate = layui.laydate;
    var layedit = layui.layedit;
    $ = layui.jquery;
    upload = layui.upload;

    layedit.set({
        uploadImage: {
            url: '/business/article/uploadApiFile' //接口url
            ,type: 'POST' //默认post
        }
    });

    //监听提交
    form.on('submit(btnSubmitScenicSpotBasicArchives)', function(data){
        window.resource("/system/scenicSpot/addScenicSpotNew", data.field, function (data) {}, false,"POST",'scenicSpotBasicArchivesListTable');
        return false;
    });

    var index =  layedit.build('scenicSpotIntroduce'); //建立编辑器
    form.verify({
        content: function(value) {
            return layedit.sync(index);
        }
    });


    upload.render({
        elem: '#file',
        url: 'https://oursjxzy.topsroboteer.ac.cn/common/upload_file',
        accept: 'file',//普通文件
        exts: 'jpg|png',//只允许上传图片
        done: function(res){
            if (res.status == "200") {
                console.log(res.data.attachId)
                $(".imageId").attr("value",res.data.attachId);
                // var ssss = $("#Form")
                // var formData = new FormData(ssss)
                //
                // $.ajax({
                //     type: 'POST',
                //     url: '/system/scenicSpot/addScenicSpotPicture',
                //     data: formData,
                //     contentType: false,
                //     processData: false,
                //     success: function(data) {
                //         console.log(data)
                //     }
                // });
            }else{
                console.log("失败")
                layer.msg('上传失败');
            }
        }
    });

    // let sss=document.getElementById("Form");
    // let formData=new FormData(sss);


    //景区所属省
    form.on('select(scenicSpotFid)',function(data){
        $('#scenicSpotSid').empty();//选择省的时候清空城市地区的值
        $('#scenicSpotSid').html('<option value="">市</option>');
        $('#scenicSpotQid').empty();
        $('#scenicSpotQid').html('<option value="">区/县</option>');
        if(data.value){
            $.ajax({
                type:"POST",
                url:"/system/scenicSpotBinding/getSpotBindingCity",
                data:{"pid":data.value},
                async : false,
                success:function(data){
                    var item = data.data;
                    var htt = '<option value="">选择市</option>';
                    $.each(item, function (k, v) {
                        htt += '<option value="'+ item[k].scenicSpotFid +'">'+ item[k].scenicSpotFname +'</option>'
                    });
                    $("#scenicSpotSid").html(htt);
                    form.render('select');
                }
            });
        }
    })

    //景区所属市
    form.on('select(scenicSpotSid)',function(data){
        $('#scenicSpotQid').empty();
        $('#scenicSpotQid').html('<option value="">区/县</option>');
        if(data.value){
            $.ajax({
                type:"POST",
                url:"/system/scenicSpotBinding/getSpotBindingArea",
                data:{"pid":data.value},
                async : false,
                success:function(data){
                    var item = data.data;
                    var htt = '<option value="">选择区/县</option>';
                    $.each(item, function (k, v) {
                        htt += '<option value="'+ item[k].scenicSpotFid +'">'+ item[k].scenicSpotFname +'</option>'
                    });
                    $("#scenicSpotQid").html(htt);
                    form.render('select');
                }
            });
        }
    })





    /*城市菜单*/
    form.on('select(province)',function(data){
        $('#city').empty();//选择省的时候清空城市地区的值
        $('#city').html('<option value="">市</option>');
        $('#county').empty();
        $('#county').html('<option value="">区/县</option>');
        if(data.value){
            $.ajax({
                type:"POST",
                url:"/business/scenicSpotExpand/getProvince",
                data:{"pid":data.value},
                dataType:"json",
                success:function(data){
                    var item = data.data;
                    var htt = '<option value="">选择市</option>';
                    $.each(item, function (k, v) {
                        htt += '<option value="'+ item[k].id +'">'+ item[k].name +'</option>'
                    });
                    $("#city").html(htt);
                    form.render('select');
                }
            });
        }
    })


    /*区县菜单*/
    form.on('select(city)',function(data){
        $('#county').empty();
        $('#county').html('<option value="">区/县</option>');
        if(data.value){
            $.ajax({
                type:"POST",
                url:"/business/scenicSpotExpand/getProvince",
                data:{"pid":data.value},
                dataType:"json",
                success:function(data){
                    var item = data.data;
                    var htt = '<option value="">选择区/县</option>';
                    $.each(item, function (k, v) {
                        htt += '<option value="'+ item[k].id +'">'+ item[k].name +'</option>'
                    });
                    $("#county").html(htt);
                    form.render('select');
                }
            });
        }
    })







})