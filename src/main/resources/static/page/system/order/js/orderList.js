layui.use(['form', 'layer', 'table', 'jquery'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;
    layuiTable = layui.table;
    var tabledata;

    var tableIns = table.render({
        elem: '#orderList',
        url: '/system/order/getOrderList',
        cellMinWidth: 100,
        page: true,
        height: "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            paymentMethod: "1,4",
            subMethod: "0",
            paymentPort: "0,1,2"
        },
        response: {
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id: "orderListTable",
        cols: [[
            {field: 'orderNumber', title: '订单编号', minWidth: 100, align: "center"},
            {field: 'currentUserPhone', title: '用户手机号', minWidth: 100, align: "center"},
            {field: 'orderRobotCode', title: '机器人编号', minWidth: 100, align: "center"},
            {field: 'orderScenicSpotName', title: '景区名称', minWidth: 100, align: "center"},
            {field: 'orderStartTime', title: '开始时间', minWidth: 100, align: "center"},
            {field: 'orderEndTime', title: '结束时间', minWidth: 100, align: "center"},
            {field: 'totalTime', title: '使用分钟', minWidth: 100, align: "center"},
            // {field: 'coupon', title: '优惠券', minWidth:50, align:"center"},
            {field: 'actualAmount', title: '实际计费金额', minWidth: 100, align: "center"},
            {field: 'dispatchingFee', title: '调度费', minWidth: 100, align: "center"},
            {field: 'orderAndDeductible', title: '实际支付金额', minWidth: 100, align: "center", sort: true, edit: 'text'},
            {field: 'orderRefundAmount', title: '退款', minWidth: 100, align: "center"},
            {field: 'realIncome', title: '实际收入', minWidth: 100, align: "center"},
            {field: 'orderStatus', title: '支付状态', align: 'center', templet: '#selectOrderStatus'},
            {
                field: 'huntsState', title: '是否参加寻宝活动', align: 'center', templet: function (d) {
                    if (d.huntsState == "0") {
                        return "未参加";
                    } else if (d.huntsState == "1") {
                        return "已参加";
                    } else if (d.huntsState == "3") {
                        return "已抽奖";
                    }
                }
            },
            {field: 'startParkingName', title: '扫码时停靠点名称', minWidth: 100, align: "center"},
            {field: 'parkingName', title: '还车点', minWidth: 100, align: "center"},
            {title: '操作', minWidth: 175, templet: '#orderListBar', fixed: "right", align: "center"}
        ]], done: function (res, curr, count) {
            tabledata = res.data;
            $.ajax({
                //拼接下拉选项
                type: "GET",
                url: "/system/order/getOrderStateName",
                dataType: "json",
                async: false,
                success: function (data) {
                    var tdata = data.data;
                    for (var k in tdata) {
                        $("select[name='orderStatus']").append('<option value="' + tdata[k].orderStatus + '">' + tdata[k].orderStatusName + '</option>');
                    }
                }
            });
            // 渲染之前组装select的option选项值
            layui.each($("select[name='orderStatus']"), function (index, item) {

                var elem = $(item);
                elem.val(elem.data('value'));
            });
            form.render('select');
            $(".realIncome").val(res.realIncome);
        }
    });

    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click", function () {
        table.reload("orderListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                currentUserPhone: $(".currentUserPhoneVal").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val(),
                startParkingName: $(".startParkingName").val(),
                parkingName: $(".parkingName").val(),
                orderScenicSpotId: $(".scenicSpotId").val(),
                orderStatus: $(".orderStatus").val(),
                huntsState: $(".huntsState").val(),
                orderRobotCode: $(".orderRobotCodeVal").val(),
                orderParkingId: $(".orderParkingId").val(),
                parkingType: $(".typee").val()
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //监听单元格编辑
    table.on('edit(orderList)', function (obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段
        window.resources("FINANCE_ORDER_UPDATEORDERSTATE", function (e) {
            if (e.state == "200") {
                window.resource("/system/order/editOrderAmount", data, function (data) {
                }, false, "POST", 'orderListTable');
            } else {
                layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
            }
        }, false, "GET");
    });

    form.on('select(orderStatus)', function (data) {
        //获取当前行tr对象
        var elem = data.othis.parents('tr');
        //第一列的值是Guid，取guid来判断
        var orderNumber = elem.first().find('td').eq(0).text();
        //选择的select对象值；
        var selectValue = data.value;
        if (selectValue == 30 || selectValue == 40) {
            window.resources("FINANCE_ORDER_UPDATEORDERSTATE", function (e) {
                if (e.state == "200") {
                    updateOrderState(orderNumber, selectValue);
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else {
            top.layer.msg("不能修改为此状态！", {icon: 5, time: 1000, shift: 6});
        }
    })

    //列表操作
    table.on('tool(orderList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "settlement") {//结算订单
            window.resources("SYSTEM_ORDER_SETTLEMENT", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定结算此订单吗？', {icon: 3, title: '提示信息'}, function (index) {
                        var dex = top.layer.msg('订单结算中，请稍候', {icon: 16, time: false, shade: 0.8});
                        $.ajax({
                            url: "/system/order/settlementOrder",
                            data: {
                                orderId: data.orderId
                            },
                            type: "POST",
                            cache: false,
                            success: function (data) {
                                if (data.state == "200") {
                                    setTimeout(function () {
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("orderListTable");
                                    }, 500);
                                } else if (data.state == "400") {
                                    setTimeout(function () {
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5, time: 1000, shift: 6});
                                    }, 500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else if (layEvent === "payBackBtn") {//订单退款
            window.resources("SYSTEM_ORDER_REFUND", function (e) {
                if (e.state == "200") {
                    if (data.paymentPort === "1") {
                        payBackBtn(data);
                    } else if (data.paymentPort === "2") {
                        alipayRefundBtn(data);
                    } else {
                        layer.msg("此用户未确认支付方式，无法退款！", {icon: 5, time: 1000, shift: 6});
                    }
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else if (layEvent === "depositPay") {//押金扣款
            window.resources("SYSTEM_ORDER_DEDUCTION", function (e) {
                if (e.state == "200") {
                    depositPay(data);
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else if (layEvent === "onEditSubStatus") {//提交
            window.resources("SYSTEM_ORDER_DEDUCTION", function (e) {
                if (e.state == "200") {
                    onEditSubStatus(data);
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else if (layEvent === "transactionClosure") {//查看数据
            window.resources("SYSTEM_ORDER_DEDUCTION", function (e) {
                if (e.state == "200") {
                    viewOrder(data);
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else if (layEvent === "blacklist") {//黑名单
            window.resources("FINANCE_ORDER_BLACK_LIST", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定要将此用户设置成黑名单吗？', {icon: 3, title: '提示信息'}, function (index) {
                        var dex = top.layer.msg('执行中，请稍候', {icon: 16, time: false, shade: 0.8});
                        $.ajax({
                            url: "/system/order/setBlacklist",
                            data: {
                                userId: data.userId
                            },
                            type: "POST",
                            cache: false,
                            success: function (data) {
                                if (data.state == "200") {
                                    setTimeout(function () {
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("orderListTable");
                                    }, 500);
                                } else if (data.state == "400") {
                                    setTimeout(function () {
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5, time: 1000, shift: 6});
                                    }, 500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else if (layEvent === "showTrail") {
            layer.open({
                type: 2,
                title: '调运轨迹',
                offset: '10%',
                area: ['50%', '70%'], //宽高
                content: '/page/system/order/trail/index.html?orderId=' + data.orderId
            });

        } else if (layEvent === "deductionAmount") {
            window.resources("SYSTEM_ORDER_DEDUCTION", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定要减免此单的调度费用吗？', {icon: 3, title: '提示信息'}, function (index) {
                        var dex = top.layer.msg('执行中，请稍候', {icon: 16, time: false, shade: 0.8});
                        $.ajax({
                            url: "/system/order/unpaidLessOrderFee",
                            data: {
                                userId: data.userId,
                                orderNumber: data.orderNumber,

                            },
                            type: "POST",
                            cache: false,
                            success: function (data) {
                                if (data.state == "200") {
                                    setTimeout(function () {
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("orderListTable");
                                    }, 500);
                                } else if (data.state == "400") {
                                    setTimeout(function () {
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5, time: 1000, shift: 6});
                                    }, 500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else if (layEvent === "storedValuePhone") {//普通订单修改储值手机号订单
            window.resources("SYSTEM_ORDER_DEDUCTION", function (e) {
                if (e.state == "200") {
                    storedValuePhone(data);
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");

        }
    });

    //点击导出EXCEL表
    $('#downloadExcel').click(function () {
        window.resources("FINANCE_ORDER_DOWNLOADEXCEL", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
            }
        }, false, "GET");
    })

    /**
     * 导出EXCEL表
     */
    function downloadExcel() {
        var currentUserPhone = $(".currentUserPhoneVal").val();//客户手机号
        var startTime = $(".startTime").val();//开始时间
        var endTime = $(".endTime").val();//结束时间
        var orderScenicSpotId = $(".scenicSpotId").val();
        var orderStatus = $(".orderStatus").val();
        var orderRobotCode = $(".orderRobotCodeVal").val();
        var orderParkingId = $(".orderParkingId").val();
        var parkingType = $(".typee").val();
        var paymentMethod = 1;
        var subMethod = 0;
        var huntsState = $(".huntsState").val();
        window.location.href = "/system/order/uploadExcelOrder?currentUserPhone=" + currentUserPhone + "&startTime=" + startTime + "&endTime=" + endTime + "&orderScenicSpotId=" + orderScenicSpotId + "&orderStatus=" + orderStatus + "&orderRobotCode=" + orderRobotCode + "&paymentMethod=" + paymentMethod + "&subMethod=" + subMethod + "&orderParkingId=" + orderParkingId + "&parkingType=" + parkingType + "&huntsState=" + huntsState;
    }

    /**
     * 修改状态
     * @param edit
     */
    function updateOrderState(orderNumber, selectValue) {
        layer.open({
            type: 2,
            title: '修改订单状态',
            area: ['800px', '400px'],
            content: '/page/system/order/html/updateOrderState.html',
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                body.find(".orderNumber").val(orderNumber);
                body.find(".orderStatus").val(selectValue);
                form.render();
            }
        });
    }

    /**
     * 微信订单退款
     * @param edit
     */
    function payBackBtn(edit) {
        layer.open({
            type: 2,
            title: '微信订单退款',
            area: ['800px', '80%'],
            content: '/page/system/order/html/refund.html',
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    body.find(".orderId").val(edit.orderId);
                    body.find(".orderNumber").val(edit.orderNumber);
                    body.find(".currentUserPhone").val(edit.currentUserPhone);
                    body.find(".orderRobotCode").val(edit.orderRobotCode);
                    body.find(".orderScenicSpotName").val(edit.orderScenicSpotName);
                    body.find(".orderStartTime").val(edit.orderStartTime);
                    body.find(".orderGpsCoordinate").val(edit.orderGpsCoordinate);
                    body.find(".totalTime").val(edit.totalTime);
                    body.find(".orderAmount").val(edit.orderAmount);
                    body.find(".orderRefundAmount").val(edit.orderRefundAmount);
                    body.find(".payMoney").val((parseFloat(edit.orderAmount) - parseFloat(edit.orderRefundAmount)).toFixed(2));
                    body.find(".reasonsRefunds").val(edit.reasonsRefunds);
                    form.render();
                }
            }
        });
    }

    /**
     * 支付宝订单退款
     * @param edit
     */
    function alipayRefundBtn(edit) {
        layer.open({
            type: 2,
            title: '支付宝订单退款',
            area: ['800px', '80%'],
            content: '/page/system/order/html/alipayRefund.html',
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    body.find(".orderId").val(edit.orderId);
                    body.find(".orderNumber").val(edit.orderNumber);
                    body.find(".currentUserPhone").val(edit.currentUserPhone);
                    body.find(".orderRobotCode").val(edit.orderRobotCode);
                    body.find(".orderScenicSpotName").val(edit.orderScenicSpotName);
                    body.find(".orderStartTime").val(edit.orderStartTime);
                    body.find(".orderGpsCoordinate").val(edit.orderGpsCoordinate);
                    body.find(".totalTime").val(edit.totalTime);
                    body.find(".orderAmount").val(edit.orderAmount);
                    body.find(".orderRefundAmount").val(edit.orderRefundAmount);
                    body.find(".payMoney").val((parseFloat(edit.orderAmount) - parseFloat(edit.orderRefundAmount)).toFixed(2));
                    body.find(".reasonsRefunds").val(edit.reasonsRefunds);
                    form.render();
                }
            }
        });
    }

    /**
     * 押金扣款
     * @param edit
     */
    function depositPay(edit) {
        layer.open({
            type: 2,
            title: '押金扣款',
            area: ['800px', '450px'],
            content: '/page/system/order/html/depositDeduction.html',
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type: 'POST',
                    url: '/system/order/getDepositPayAmountByUserId',
                    data: {
                        orderId: edit.orderId
                    },
                    dataType: 'json',
                    success: function (data) {
                        if (data.state == "200") {
                            body.find(".orderId").val(edit.orderId);
                            body.find(".depositPayAmount").val(data.data.depositPayAmount);
                            body.find(".orderAmount").val(data.data.orderAmount);
                            body.find(".reasonsRefunds").val(data.data.reasonsRefunds);
                            form.render();
                        } else if (data.state == "400") {
                            layer.msg(data.msg);
                        }
                    }
                })
            }
        });
    }

    /**
     * 普通订单修改为储值订单
     * @param edit
     */
    function storedValuePhone(edit) {
        layer.open({
            type: 2,
            title: '普通订单修改为储值订单',
            area: ['800px', '50%'],
            content: '/page/system/order/html/editStoredValuePhone.html',
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    body.find(".orderId").val(edit.orderId);
                    form.render();
                }
            }
        });
    }


    /**
     * 提交
     * @param edit
     */
    function onEditSubStatus(edit) {
        layer.open({
            type: 2,
            title: '提交',
            area: ['800px', '80%'],
            content: '/page/system/order/html/editSubStatus.html',
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type: 'POST',
                    url: '/system/currentUserAccount/getUserAccountByUserId',
                    data: {
                        accountUserId: edit.userId
                    },
                    dataType: 'json',
                    success: function (data) {
                        body.find(".orderId").val(edit.orderId);
                        body.find(".orderNumber").val(edit.orderNumber);
                        body.find(".currentUserPhone").val(edit.currentUserPhone);
                        body.find(".orderRobotCode").val(edit.orderRobotCode);
                        body.find(".orderScenicSpotName").val(edit.orderScenicSpotName);
                        body.find(".orderStartTime").val(edit.orderStartTime);
                        body.find(".totalTime").val(edit.totalTime);
                        body.find(".orderAmount").val(edit.orderAmount);
                        body.find(".dispatchingFee").val(edit.dispatchingFee);
                        body.find(".orderDiscount").val(data.data.discount);
                        body.find(".deductibleAmount").val(data.data.accountAmount);
                        body.find(".orderAmounts").val((parseFloat(edit.orderAmount) + parseFloat(edit.dispatchingFee)).toFixed(2));
                        form.render();
                    }
                })
            }
        });
    }

    /**
     * 查看数据
     * @param edit
     */
    function viewOrder(edit) {
        layer.open({
            type: 2,
            title: '查看数据',
            area: ['800px', '80%'],
            content: '/page/system/order/html/viewOrder.html',
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    body.find(".orderId").val(edit.orderId);
                    body.find(".orderNumber").val(edit.orderNumber);
                    body.find(".currentUserPhone").val(edit.currentUserPhone);
                    body.find(".orderRobotCode").val(edit.orderRobotCode);
                    body.find(".orderScenicSpotName").val(edit.orderScenicSpotName);
                    body.find(".orderGpsCoordinate").val(edit.orderGpsCoordinate);
                    body.find(".orderStartTime").val(edit.orderStartTime);
                    body.find(".totalTime").val(edit.totalTime);
                    body.find(".orderAmount").val(edit.orderAmount);
                    body.find(".orderRefundAmount").val(edit.orderRefundAmount);
                    body.find(".reasonsRefunds").val(edit.reasonsRefunds);
                    form.render();
                }
            }
        });
    }


})
