layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotActivityList',
        url : '/system/scenicSpotActivity/getScenicSpotActivityList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "scenicSpotActivityListTable",
        cols : [[
            {field: 'activityName', title: '活动名称', minWidth:100, align:"center"},
            {field: 'activityScenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'activityStandard', title: '活动满减标准', minWidth:100, align:"center"},
            {field: 'activityAmount', title: '满减金额', minWidth:100, align:"center"},
            {field: 'numberCoupons', title: '优惠券数量', minWidth:100, align:"center"},
            {field: 'claimConditions', title: '优惠基础', minWidth:100, align:"center"},
            {field: 'activityStartTime', title: '开始时间', minWidth:100, align:"center"},
            {field: 'activityEndTime', title: '结束时间', minWidth:100, align:"center"},
            {field: 'termOfValidity', title: '活动有效期', minWidth:100, align:"center"},
            {field: 'activityType', title: '活动类型', minWidth:100, align:"center",templet:function(d){
                    if(d.activityType == "1"){
                        return "景区活动用户直接领取";
                    }else if(d.activityType == "2"){
                        return "后台或管理人员发放";
                    }else if(d.activityType == "3"){
                        return "满多少钱可以领取";
                    }
                }},
            {field: 'activityFailure', title: '开启状态', minWidth:100, align:"center",templet:function(d){
                    if(d.activityFailure == "1"){
                        return "启用";
                    }else if(d.activityFailure == "0"){
                        return "禁用";
                    }
                }},
            {field: 'activityUseType', title: '活动开启类型', minWidth:100, align:"center",templet:function(d){
                    if(d.activityUseType == "1"){
                        return "工作日";
                    }else if(d.activityUseType == "2"){
                        return "周六日";
                    }else if(d.activityUseType == "3"){
                        return "每天";
                    }else{
                        return "暂未设置"
                    }
                }},
            {title: '操作', minWidth:175, templet:'#scenicSpotActivityListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".scenicSpotId").val() != ''){
            table.reload("scenicSpotActivityListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    activityScenicSpotId: $(".scenicSpotId").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请选择搜索的景区名称");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(scenicSpotActivityList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'distributionDiscountVolume'){ //用户分配优惠卷 
            window.resources("DISTRIBUTION_DISCOUNT_VOLUME", function (e) {
                if (e.state == "200") {
                    openDistributionDiscountVolume(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_SCENIC_SPOT_ACTIVITY_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/scenicSpotActivity/delScenicSpotActivity", {activityId : data.activityId}, function (data) {}, false,"GET","scenicSpotActivityListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent ==='activityFailure'){
            window.resources("DISTRIBUTION_DISCOUNT_VOLUME", function (e) {
                if (e.state == "200"){
                    var activityFailure = 0;
                    if (data.activityFailure == 1){
                        activityFailure = 0;
                    }else{
                        activityFailure = 1;
                    }
                    console.log(activityFailure)
                    window.resourceN("/system/scenicSpotActivity/editActivityFailure", {activityId : data.activityId,activityFailure : activityFailure}, function (data) {}, false,"GET","scenicSpotActivityListTable");

                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent ==='edit'){
            window.resources("DISTRIBUTION_DISCOUNT_VOLUME", function (e) {
                if (e.state == "200") {
                    edit(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");

        }


    });
    //点击添加按钮
    $('#btnAddScenicSpotActivity').click(function(){
        window.resources("SYS_SCENIC_SPOT_ACTIVITY_INSERT", function (e) {
            if (e.state == "200") {
                openAddScenicSpotActivity();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddScenicSpotActivity() {
        layer.open({
            type : 2,
            title: '添加优惠',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/system/scenicSpotActivity/html/scenicSpotActivityAdd.html'
        });
    };

    /**
     * 弹出修改分配优惠框
     * @param edit
     */
    function openDistributionDiscountVolume(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改优惠',
                offset: '10%',
                area: ['800px', '270px'],
                content: '/page/system/scenicSpotActivity/html/distributionDiscountVolume.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".activityId").val(edit.activityId);
                        form.render();
                    }
                }
            });
        },500);
    }

    /**
     * 弹出编辑框
     * @param edit
     */
    function edit(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '编辑',
                offset: '10%',
                area: ['800px', '600px'],
                content: '/page/system/scenicSpotActivity/html/scenicSpotActivityEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".activityId").val(edit.activityId);
                        body.find(".activityScenicSpotId").val(edit.activityScenicSpotId);
                        body.find(".activityName").val(edit.activityName);
                        body.find(".activityStandard").val(edit.activityStandard);
                        body.find(".activityAmount").val(edit.activityAmount);
                        body.find(".numberCoupons").val(edit.numberCoupons);
                        body.find(".activityStartTime").val(edit.activityStartTime);
                        body.find(".activityEndTime").val(edit.activityEndTime);
                        body.find(".activityType").val(edit.activityType);
                        body.find(".claimConditions").val(edit.claimConditions);
                        body.find(".activityUseType").val(edit.activityUseType);
                        form.render();
                    }
                }
            });
        },500);
    }




})
