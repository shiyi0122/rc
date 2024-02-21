layui.use(['form','layer','table','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#filingMessageList',
        url : '/business/filingMessage/getFilingMessageList',
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
            dataName: 'data' //数据列表的字段名称，默认：data
        },
        id : "filingMessageList",
        cols : [[
            {field: 'filingName', title: '主体报备人姓名', minWidth:100, align:"center"},
            {field: 'filingPhone', title: '手机号', minWidth:100, align:"center"},
            {field: 'filingRegion', title: '报备区域', minWidth:100, align:"center"},
            {field: 'filingCycle', title: '报备周期', minWidth:100, align:"center"},
            {field: 'filingSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'filingSpotContacts', title: '报备景区联系人', minWidth:100, align:"center"},
            {field: 'filingSpotContactsPhone', title: '报备景区联系人手机号', minWidth:100, align:"center"},
            {field: 'filingTime', title: '报备日期', minWidth:100, align:"center"},
            {field: 'filingSpotContactsPosition', title: '职位', minWidth:100, align:"center"},
            {field: 'filingRobotCount', title: '预期投放机器人台数', minWidth:100, align:"center"},
            {field: 'filingSpotCity', title: '景区属地', minWidth:100, align:"center"},
            {field: 'filingSpotStarLevel', title: '景区类型', minWidth:100, align:"center",templet:function(d){
                    if(d.filingSpotStarLevel == "0"){
                        return "市民公园";
                    }else if(d.filingSpotStarLevel == "1"){
                        return "A";
                    }else if(d.filingSpotStarLevel == "2"){
                        return "AA";
                    }else if(d.filingSpotStarLevel == "3"){
                        return "AAA"
                    }else if (d.filingSpotStarLevel == "4"){
                        return "AAAA"
                    }else if (d.filingSpotStarLevel == "5"){
                        return "AAAAA"
                    }else if (d.filingSpotStarLevel == ""){
                        return "无"
                    }
                }},
            {field: 'filingSpotMeasure', title: '报备景区面积(亩)', minWidth:100, align:"center"},
            {field: 'filingSpotVisitorsFlowrate', title: '景区年度有效客流(万人)', minWidth:100, align:"center"},
            {field: 'remarks', title: '备注', minWidth:100, align:"center"},
            {field: 'approvalName', title: '审核人名称', minWidth:100, align:"center"},
            {field: 'signingTime', title: '报备失效期', minWidth:100, align:"center"},
            {field: 'findingsOfAudit', title: '审核结果', minWidth:100, align:"center",templet:function(d){
                    if(d.findingsOfAudit == "2"){
                        return "通过";
                    }else if(d.findingsOfAudit == "3"){
                        return "驳回";
                    }else if(d.findingsOfAudit == "4"){
                        return "待审核";
                    }else if(d.findingsOfAudit == "5"){
                        return "已失效"
                    }else if (d.findingsOfAudit == "6"){
                        return "已签约"
                    }else if (d.findingsOfAudit == "7"){
                        return "排队中"
                    }
                }},
            {field: 'auditDate', title: '审核日期', minWidth:100, align:"center"},
            {field: 'reson', title: '原因', minWidth:100, align:"center"},
            {field: 'createTime', title: '创建时间', minWidth:100, align:"center"},
            {field: 'updateTime', title: '修改时间', minWidth:100, align:"center"},
            {field: 'isSigning', title: '是否签约', minWidth:100, align:"center",templet:function(d){
                    if(d.isSigning == "6"){
                        return "未签约";
                    }else if(d.isSigning == "7"){
                        return "已签约";
                    }else if(d.isSigning == ""){
                        return "未签约"
                    }else if(d.isSigning == "0"){
                        return "未签约"
                    }
                }},
            {field: 'signingTimeDetermine', title: '签约日期', minWidth:100, align:"center"},
            {field: 'signingTimeDetermineInvalid', title: '签约失效日期', minWidth:100, align:"center"},
            {field: 'executiveDirectorName', title: '主管报备人姓名', minWidth:100, align:"center"},
            {field: 'executiveDirectorPhone', title: '主管报备人手机号', minWidth:100, align:"center"},
            {field: 'traffic', title: '路况', minWidth:100, align:"center",templet:function(d){
                    if(d.traffic == "1"){
                        return "封闭式仅行人";
                    }else if(d.traffic == "2"){
                        return "开放式人车混行";
                    }else if(d.traffic == "0"){
                        return "无"
                    }else if(d.traffic == ""){
                        return "无"
                    }
                }},
            {field: 'state', title: '是否失效', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "未失效";
                    }else if(d.state == "1"){
                        return "已失效";
                    }
                }},
            {field: 'pavement', title: '路面情况', minWidth:100, align:"center",templet:function(d){
                    if(d.pavement == "0" || d.pavement == ""){
                        return "无";
                    }else if(d.pavement == "1"){
                        return "沥青路";
                    }else if(d.pavement == "2"){
                        return "混凝土路";
                    }else if(d.pavement == "3"){
                        return "石板路";
                    }else if(d.pavement == "4"){
                        return "碎石路";
                    }else if(d.pavement == "5"){
                        return "透水路";
                    }else if(d.pavement == "1,2"){
                        return "沥青路,混凝土路";
                    }else if(d.pavement == "1,3"){
                        return "沥青路,石板路";
                    }else if(d.pavement == "1,4"){
                        return "沥青路,碎石路";
                    }else if(d.pavement == "1,5"){
                        return "沥青路,透水路";
                    }else if(d.pavement == "2,3"){
                        return "混凝土路,石板路";
                    }else if(d.pavement == "2,4"){
                        return "混凝土路,碎石路";
                    }else if(d.pavement == "2,5"){
                        return "混凝土路,透水路";
                    }else if(d.pavement == "3,4"){
                        return "石板路,碎石路";
                    }else if(d.pavement == "3,5"){
                        return "石板路,透水路";
                    }else if(d.pavement == "4,5"){
                        return "碎石路,透水路";
                    }else if(d.pavement == "1,2,3"){
                        return "沥青路,混凝土路,石板路";
                    }else if(d.pavement == "1,2,4"){
                        return "沥青路,混凝土路,碎石路";
                    }else if(d.pavement == "1,2,5"){
                        return "沥青路,混凝土路,透水路";
                    }else if(d.pavement == "1,3,4"){
                        return "沥青路,石板路,碎石路";
                    }else if(d.pavement == "1,3,5"){
                        return "沥青路,石板路,透水路";
                    }else if(d.pavement == "1,4,5"){
                        return "沥青路,碎石路,透水路";
                    } else if(d.pavement == "2,3,4"){
                        return "混凝土路,石板路,碎石路";
                    }else if(d.pavement == "2,3,5"){
                        return "混凝土路,石板路,透水路";
                    }else if(d.pavement == "2,4,5"){
                        return "混凝土路,碎石路,透水路";
                    }else if(d.pavement == "3,4,5"){
                        return "石板路,碎石路,透水路";
                    }else if(d.pavement == ""){
                        return "无";
                    }

                }},
            {field: 'roadWidth', title: '路宽', minWidth:100, align:"center",templet:function(d){
                    if(d.roadWidth == "0"){
                        return "无";
                    }else if(d.roadWidth == "1"){
                        return "2米以下";
                    }else if(d.roadWidth == "2"){
                        return "2-3米";
                    }else if(d.roadWidth == "3"){
                        return "3-4米";
                    }else if(d.roadWidth == "4"){
                        return "4-5米";
                    }else if(d.roadWidth == "5"){
                        return "5米以上";
                    }
                }},
            {field: 'robotLinesInOperation', title: '机器人可运行线路(公里)', minWidth:100, align:"center"},
            {field: 'vehicle', title: '景区交通工具', minWidth:100, align:"center",templet:function(d){
                    if(d.vehicle == "0" || d.vehicle == ""){
                        return "无";
                    }else if(d.vehicle == "1"){
                        return "脚踏车";
                    }else if(d.vehicle == "2"){
                        return "小型电动车";
                    }else if(d.vehicle == "3"){
                        return "游览摆渡车";
                    }else if(d.vehicle == "1,2"){
                        return "脚踏车,小型电动车";
                    }else if(d.vehicle == "1,3"){
                        return "脚踏车,游览摆渡车";
                    }else if(d.vehicle == "2,3"){
                        return "小型电动车,游览摆渡车";
                    }else if(d.vehicle == "1,2,3"){
                        return "脚踏车,小型电动车,游览摆渡车";
                    }
                }},
            {field: 'proportionOfPassengerFlow', title: '客流组成占比', minWidth:100, align:"center"},
            {field: 'dockingUnitName', title: '对接单位名称', minWidth:100, align:"center"},
            {field: 'signatoryName', title: '签约主体名称', minWidth:100, align:"center"},
            {field: 'dockingDate', title: '对接时间', minWidth:100, align:"center"},
            {field: 'dockingName', title: '对接人', minWidth:100, align:"center"},
            {field: 'dockingPosition', title: '对接人职位', minWidth:100, align:"center"},
            {field: 'dockingResult', title: '对接结果', minWidth:100, align:"center"},
            {field: 'dockingPlace', title: '对接地点', minWidth:100, align:"center"},
            {field: 'reason', title: '延期理由', minWidth:100, align:"center"},
            {field: 'days', title: '延期天数', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#filingMessageListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(filingMessageList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'adopt'){ //审核通过
            window.resources("BUSINESS_FILING_MESSAGE", function (e) {
                if (e.state == "200") {
                    openEditAdopt(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'reject'){ //审核驳回
            window.resources("BUSINESS_FILING_MESSAGE", function (e) {
                if (e.state == "200") {
                    openEditReject(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent == 'sign'){ //签约

            window.resources("BUSINESS_FILING_MESSAGE", function (e) {
                if (e.state == "200"){
                    console.log(data.id);
                    window.resource("/business/filingMessage/editFilingMessageResult", {id : data.id, findingsOfAudit: "6"}, function (data) {}, false,"GET","filingMessageList");
                    t.where = data.field;
                    table.reload('test', t);
                    // window.resourcedel("/business/filingMessage/editFilingMessageResult", {id : data.id}, function (data) {}, false,"GET","filingMessageList");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent == 'auditLog'){
            window.resources("BUSINESS_FILING_MESSAGE", function (e) {
                if (e.state == "200") {
                    getAuditLog(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");


        }
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("filingMessageList",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                filingName: $(".filingNameVal").val(),
                findingsOfAudit: $(".findingsOfAudit").val(),
                filingPhone: $(".filingPhoneVal").val()
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    /**
     * 驳回页面
     * @param edit
     */
    function openEditReject(edit){
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '原因',
                offset: '10%',
                area: ['800px', '280px'],
                content: '/page/business/filingMessage/html/filingMessageAdopt.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        body.find(".id").val(edit.id);
                        body.find(".findingsOfAudit").val("3");
                        form.render();
                    }
                }
            });
        },500);
    }
    /**
     * 同意页面
     * @param edit
     */
    function openEditAdopt(edit){
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '原因',
                offset: '10%',
                area: ['800px', '280px'],
                content: '/page/business/filingMessage/html/filingMessageAdopt.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        body.find(".id").val(edit.id);
                        body.find(".findingsOfAudit").val("2");
                        form.render();
                    }
                }
            });
        },500);
    }

    //签约
    function signMethod(edit){
        $.ajax({
            type: "GET",
            url: "/business/filingMessage/editFilingMessageResult",
            data: {id: data.id ,findingsOfAudit: "6" },
            // dataType: "json",
            success: function(result){
                成功后的操作
            },
            faliure: function(result){
                失败后的操作
            },
            complete: function(result){
                无论成功或失败都会显示
            }
        });
    }

    /**
     * 审核日志查看页面
     * @param edit
     */
    function getAuditLog(edit){
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '查看审核日志',
                offset: '10%',
                area: ['1000px', '500px'],
                content: '/page/business/filingMessage/html/filingMessageAuditLog.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        body.find(".id").val(edit.id);
                        // body.find(".findingsOfAudit").val("3");
                        form.render();
                    }
                }
            });
        },500);
    }


    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("BUSINESS_FILING_MESSAGE", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    //点击添加按钮
    $('#filingMessageAdd').click(function(){
        window.resources("BUSINESS_FILING_MESSAGE", function (e) {
            if (e.state == "200") {
                openAddMemberLevel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 导入excel
     */

    //导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/business/filingMessage/importExcel'
        ,accept: 'file' //普通文件
        ,exts: 'xls|xlsx' //只允许上传Excel文件
        ,before:function(obj){
            layer.load(); //上传loading
        }
        ,done: function(res){
            if (res.state == 200){
                setTimeout(function(){
                    layer.alert(res.msg,function(){
                        layer.closeAll();//关闭所有弹框
                    });
                    layui.table.reload("filingMessageList");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
                    layer.closeAll();
                },500);
            }
        }
    });




    /**
     * 弹出添加框
     */
    function openAddMemberLevel() {
        layer.open({
            type : 2,
            title: '添加报备景区',
            offset: '10%',
            area: ['800px', '455px'],
            content: '/page/business/filingMessage/html/filingMessageAdd.html'
        });
    };





    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
          var  userName = $(".filingNameVal").val();
           var state = $(".findingsOfAudit").val();
          var  filingPhone = $(".filingPhoneVal").val();
        console.log(userName);
        console.log(filingPhone);
        console.log(state);

        window.location.href = "/business/filingMessage/uploadExcelFilingMessage?filingName="+userName+"&findingsOfAudit="+state+"&filingPhone="+filingPhone;
    }

})