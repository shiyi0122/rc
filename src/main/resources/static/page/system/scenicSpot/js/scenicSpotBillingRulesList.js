layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotBillingRulesList',
        url : '/system/scenicSpot/getScenicSpotList',
        cellMinWidth : 150,
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
        id : "scenicSpotBillingRulesListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'scenicSpotDeposit', title: '押金', minWidth:100, align:"center"},
            {field: 'scenicSpotWeekendPrice', title: '周六日单价', minWidth:100, align:"center"},
            {field: 'scenicSpotNormalPrice', title: '工作日单价', minWidth:100, align:"center"},
            {field: 'scenicSpotWeekendRentPrice', title: '周六日起租价格', minWidth:100, align:"center"},
            {field: 'scenicSpotNormalRentPrice', title: '工作日起租价格', minWidth:100, align:"center"},
            {field: 'scenicSpotWeekendTime', title: '周六日计费时间', minWidth:100, align:"center"},
            {field: 'scenicSpotNormalTime', title: '工作日计费时间', minWidth:100, align:"center"},
            {field: 'scenicSpotRentTime', title: '起租时间', minWidth:100, align:"center"},
            {field: 'scenicSpotBeyondPrice', title: '调度费', minWidth:100, align:"center"},
            {field: 'randomBroadcastTime', title: '随机播报时间', minWidth:100, align:"center"},
            {field: 'scenicSpotFrequency', title: '锁定次数', minWidth:100, align:"center"},
            {field: 'scenicSpotFenceTime', title: '电子围栏锁定时间(秒)', minWidth:100, align:"center"},
            {field: 'scenicSpotForbiddenZoneTime', title: '禁区锁定时间(秒)', minWidth:100, align:"center"},
            // {field: 'holidayString',title: '节假日日期', minWidth: 100, align:"center"},
            {field: 'settlementMethod', title: '结算方式', minWidth:100, align:"center",templet:function(d){
                    if(d.settlementMethod == "0"){
                        return "默认结算方式";
                    }else if(d.settlementMethod == "1"){
                        return "唐山结算方式";
                    }
                }},
            {title: '操作', minWidth:175, templet:'#scenicSpotBillingRulesListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据景区名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotBillingRulesListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotName: $(".scenicSpotNameVal").val(), //搜索的关键字
                robotWakeupWords: $(".robotWakeupWords").val() //搜索的关键字
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
    table.on('tool(scenicSpotBillingRulesList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SCENIC_SPOT_BILLING_RULES_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditScenicSpot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditScenicSpot(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改计费规则',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/system/scenicSpot/html/scenicSpotBillingRulesEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        // console.log(edit);
                        top.layer.close(dex);
                        body.find(".scenicSpotId").val(edit.scenicSpotId);
                        body.find(".scenicSpotName").val(edit.scenicSpotName);
                        body.find(".scenicSpotDeposit").val(edit.scenicSpotDeposit);
                        body.find(".scenicSpotWeekendPrice").val(edit.scenicSpotWeekendPrice);
                        body.find(".scenicSpotNormalPrice").val(edit.scenicSpotNormalPrice);
                        body.find(".scenicSpotWeekendRentPrice").val(edit.scenicSpotWeekendRentPrice);
                        body.find(".scenicSpotNormalRentPrice").val(edit.scenicSpotNormalRentPrice);
                        body.find(".scenicSpotWeekendTime").val(edit.scenicSpotWeekendTime);
                        body.find(".scenicSpotNormalTime").val(edit.scenicSpotNormalTime);
						body.find(".scenicSpotFrequency").val(edit.scenicSpotFrequency);
                        body.find(".scenicSpotFenceTime").val(edit.scenicSpotFenceTime);
                        body.find(".scenicSpotForbiddenZoneTime").val(edit.scenicSpotForbiddenZoneTime);
                        body.find(".scenicSpotRentTime").val(edit.scenicSpotRentTime);
                        body.find(".scenicSpotBeyondPrice").val(edit.scenicSpotBeyondPrice);
                        body.find(".randomBroadcastTime").val(edit.randomBroadcastTime);
                        // body.find(".workTime").val(edit.workTime);
                        // body.find(".closingTime").val(edit.closingTime);
                        // body.find(".workBroadcast").val(edit.workBroadcast);
                        // body.find(".closingBroadcast").val(edit.closingBroadcast);
                        // body.find(".hornSwitch").val(edit.hornSwitch);
                        // body.find(".lampClosingTime").val(edit.lampClosingTime);
                        // body.find(".lampOpeningTime").val(edit.lampOpeningTime);
                        // body.find(".repeatStatus").val(edit.repeatStatus);
                        // body.find(".huntQuantity").val(edit.huntQuantity);
                         body.find(".freeTimeSetting").val(edit.freeTimeSetting);
                         body.find(".settlementMethod").val(edit.settlementMethod);
                         body.find(".holidayString").val(edit.holidayString);
                        form.render();
                    }
                }
            });
        },500);
    }
    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYSTEM_SCENIC_SPOT_DOWNLOADEXCEL", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        var scenicSpotName = $(".scenicSpotNameVal").val();//客户手机号
        var robotWakeupWords = $(".robotWakeupWords").val();//开始时间
        window.location.href = "/system/scenicSpot/uploadScenicSpotBillingRulesExcelOrder?scenicSpotName="+scenicSpotName+"&robotWakeupWords="+robotWakeupWords;
    }

})
