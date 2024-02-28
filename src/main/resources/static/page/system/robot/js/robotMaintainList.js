layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotMaintainList',
        url: '/system/robot/getRobotList',
        cellMinWidth: 100,
        page: true,
        height: "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            type: "1,3",
        },
        response: {
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id: "robotMaintainListTable",
        cols: [[
            {field: 'robotCode', title: '机器人编号', minWidth: 100, align: "center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth: 100, align: "center"},
            {
                field: 'robotRunState', title: '运行状态', minWidth: 100, align: "center", templet: function (d) {
                    if (d.robotRunState == "10") {
                        return "闲置";
                    } else if (d.robotRunState == "20") {
                        return "用户解锁";
                    } else if (d.robotRunState == "30") {
                        return "用户临时锁定";
                    } else if (d.robotRunState == "40") {
                        return "管理员解锁";
                    } else if (d.robotRunState == "50") {
                        return "管理员锁定";
                    } else if (d.robotRunState == "60") {
                        return "自检故障报警";
                    } else if (d.robotRunState == "70") {
                        return "扫码解锁中";
                    } else if (d.robotRunState == "80") {
                        return "运营人员钥匙解锁";
                    } else if (d.robotRunState == "90") {
                        return "运营人员维护";
                    } else if (d.robotRunState == "100") {
                        return "禁区锁定";
                    }
                }
            },
            {field: 'robotVersionNumber', title: '机器人版本号', minWidth: 100, align: "center"},
            {field: 'clientVersion', title: '机器人PAD版本号', minWidth: 100, align: "center"},
            {
                field: 'robotPowerState', title: '电量状态', minWidth: 100, align: "center", templet: function (d) {
                    return d.robotPowerState + '%'
                }
            },
            {
                field: 'robotFaultState', title: '故障状态', minWidth: 100, align: "center", templet: function (d) {
                    if (d.robotFaultState == "10") {
                        return "正常";
                    } else if (d.robotFaultState == "20") {
                        return "报修";
                    } else if (d.robotFaultState == "30") {
                        return "故障";
                    }
                }
            },
            {field: 'robotBatchNumber', title: '机器人批次号', minWidth: 100, align: "center"},
            {field: 'autoUpdateState', title: '升级状态', minWidth: 100, align: "center", templet: function (d) {
                    if (d.autoUpdateState == "0") {
                        return "下载中";
                    } else if (d.autoUpdateState == "1") {
                        return "下载完成";
                    } else if (d.autoUpdateState == "2") {
                        return "下载失败";
                    } else if (d.autoUpdateState == "3") {
                        return "安装中";
                    } else if (d.autoUpdateState == "4") {
                        return "安装完成";
                    } else if (d.autoUpdateState == "5") {
                        return "安装失败";
                    }
                }
            },
            {field: 'createDate', title: '添加时间', minWidth: 100, align: "center"},
            {title: '操作', minWidth: 175, templet: '#robotMaintainListBar', fixed: "right", align: "center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click", function () {
        table.reload("robotMaintainListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                type: "1,3",
                robotCode: $(".robotCodeVal").val(),  //搜索的关键字
                scenicSpotId: $(".scenicSpotId").val(),  //搜索的关键字
                robotCodeSim: $(".robotCodeSimVal").val(),
                robotRunState: $(".robotRunState").val(),
                clientVersion: $(".clientVersionVal").val()
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
    table.on('tool(robotMaintainList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "updateRobotRunState") { //修改状态
            window.resources("SYSTEM_ROBOT_UPDATE_RUN_STATE", function (e) {
                if (e.state == "200") {
                    updateRobotRunState(data);
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else if (layEvent === "qrcode") { //下载二维码
            window.resources("SYSTEM_ROBOT_QRCODE", function (e) {
                if (e.state == "200") {
                    window.location.href = "/system/robot/qrcode?fileName=" + data.robotCode + ".jpg"
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        } else if (layEvent === "onQrCode") { //生成二维码
            window.resources("SYSTEM_ROBOT_ONQRCODE", function (e) {
                if (e.state == "200") {
                    onQrCode(data);
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        }
    });

    //点击导出EXCEL表
    $('#downloadExcel').click(function () {
        window.resources("ROBOT_MAINTAIN_DOWNLOADEXCEL", function (e) {
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
        var robotCodeVal = $(".robotCodeVal").val();
        var scenicSpotId = $(".scenicSpotId").val();
        var robotRunState = $(".robotRunState").val();
        var clientVersion = $(".clientVersionVal").val();
        window.location.href = "/system/robot/uploadExcelRobot?robotCode=" + robotCodeVal + "&scenicSpotId=" + scenicSpotId + "&robotRunState=" + robotRunState + "&clientVersion=" + clientVersion;
    }

    /**
     * 修改状态
     * @param edit
     */
    function updateRobotRunState(edit) {
        layer.open({
            type: 2,
            title: '状态修改',
            area: ['300px', '400px'],
            content: '/page/system/robot/html/updateRobotRunState.html',
            tableId: '#robotList',
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    body.find(".robotId").val(edit.robotId);
                    form.render();
                }
            }
        });
    }

    function onQrCode(edit) {
        if (edit.robotCode != '') {
            layer.confirm('确定生成此二维码吗？', {icon: 3, title: '提示信息'}, function (index) {
                var dex = top.layer.msg('二维码生成中，请稍候', {icon: 16, time: false, shade: 0.8});
                $.ajax({
                    url: "/system/robot/onQrCode",
                    data: {
                        robotCode: edit.robotCode
                    },
                    type: "POST",
                    cache: false,
                    success: function (data) {
                        if (data.state == "200") {
                            setTimeout(function () {
                                top.layer.msg(data.msg, {icon: 6});
                                top.layer.close(dex);
                                layer.close(index);
                                layui.table.reload("robotMaintainListTable");
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
            top.layer.msg("机器人编号为空", {icon: 5, time: 1000, shift: 6});
        }
    }

    //生成所有二维码
    $('#generateQrCode').click(function () {
        window.resources("ROBOT_MAINTAIN_DOWNLOADEXCEL", function (e) {
            if (e.state == "200") {
                layer.confirm('确定生成所有二维码吗？', {icon: 3, title: '提示信息'}, function (index) {
                    var dex = top.layer.msg('二维码生成中，请稍候', {icon: 16, time: false, shade: 0.8});
                    $.ajax({
                        url: "/system/robot/JINGHUAgenerateQrCode",
                        data: {
                            scenicSpotId: $(".scenicSpotId").val()
                        },
                        type: "POST",
                        cache: false,
                        success: function (data) {
                            if (data.state == "200") {
                                setTimeout(function () {
                                    layer.alert(data.msg, function () {
                                        layer.closeAll();//关闭所有弹框
                                    });
                                    top.layer.close(dex);
                                    layer.close(index);
                                    layui.table.reload("robotMaintainListTable");
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
    })

})
