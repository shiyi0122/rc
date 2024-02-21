layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;
    layuiTable = layui.table;
    var tabledata;

    var tableIns = table.render({
        elem: '#currentUserCouponsList',
        url : '/system/currentUserCoupons/getCurrentUserCouponsList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where:{
            paymentMethod : "1"
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "currentUserCouponsListTable",
        cols : [[
            {field: 'phone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'userCouponsName', title: '活动名称', minWidth:100, align:"center"},
            {field: 'couponsScenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'couponsStandard', title: '活动满减标准', minWidth:100, align:"center"},
            {field: 'couponsAmount', title: '满减金额', minWidth:100, align:"center"},
            {field: 'termOfValidity', title: '活动有效期', minWidth:100, align:"center"},
            {field: 'couponsType', title: '活动类型', minWidth:100, align:"center",templet:function(d){
                    if(d.couponsType == "1"){
                        return "景区活动用户直接领取";
                    }else if(d.couponsType == "2"){
                        return "后台或管理人员发放";
                    }else if(d.couponsType == "3"){
                        return "满多少钱可以领取";
                    }else if(d.couponsType == "4"){
                        return "寻宝活动领取"
                    }else if(d.couponsType == "5"){
                        return "随机寻宝活动领取"
                    }
                }},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
			{title: '操作', minWidth:175, templet:'#currentUserCouponsListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("currentUserCouponsListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                phone: $(".userPhoneVal").val()
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

	//列表操作
    table.on('tool(currentUserCouponsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'del'){ //删除
            window.resources("SYS_CURRENT_USER_COUPONS_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/currentUserCoupons/delCurrentUserCoupons", {userCouponsId : data.userCouponsId}, function (data) {}, false,"GET","currentUserCouponsListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加按钮
    $('#btnAddCurrentUserCoupons').click(function(){
        window.resources("SYS_CURRENT_USER_COUPONS_INSERT", function (e) {
            if (e.state == "200") {
                openAddCurrentUserCoupons();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddCurrentUserCoupons() {
        layer.open({
            type : 2,
            title: '用户分配优惠',
            offset: '10%',
            area: ['800px', '330px'],
            content: '/page/system/currentUserCoupons/html/currentUserCouponsAdd.html'
        });
    };


})