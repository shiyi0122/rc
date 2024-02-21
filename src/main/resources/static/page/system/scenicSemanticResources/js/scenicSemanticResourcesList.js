layui.use(['form', 'layer', 'table', 'laytpl'], function() {
	var form = layui.form;
	var layer = layui.layer;
	var laytpl = layui.laytpl;
	var table = layui.table;
	$ = layui.jquery;

	var tableIns = table.render({
		elem: '#scenicSemanticResourcesList',
		url: '/system/semanticResources/getSemanticResourcesList',
		cellMinWidth: 100,
		page: true,
		height: "full-125",
		request: {
			pageName: 'pageNum', //页码的参数名称，默认：pageNum
			limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
		},
		response: {
			statusName: 'code', //数据状态的字段名称，默认：code
			statusCode: 200, //成功的状态码，默认：0
			countName: 'totals', //数据总数的字段名称，默认：count
			dataName: 'list' //数据列表的字段名称，默认：data
		},
		id: "scenicSemanticResourcesListTable",
		cols: [[
			{ field: 'scenicSpotName', title: '景区名称', minWidth: 100, align: "center" },
			{ title: '操作', minWidth: 175, templet: '#scenicSemanticResourcesListBar', fixed: "right", align: "center" }
		]]
	});

    /**
     * 根据角色名称模糊查询
     */
	$("#btnSearch").on("click", function() {
		table.reload("scenicSemanticResourcesListTable", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				scenicSpotName: $(".scenicSpotNameVal").val()  //搜索的关键字
			}
		})
	});

    /**
     * 重置
     */
	$("#reset").click(function() {
		$(".roleNameVal").val("");
		location.reload();
	})

	//列表操作
	table.on('tool(scenicSemanticResourcesList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;

		if (layEvent === "resourceAllocation") { //资源分配
			window.resources("SYSTEM_ROLE_PERMISSION_SETTINGS", function(e) {
				if (e.state == "200") {
					resourceAllocation(data);
				} else {
					layer.msg(e.msg, { icon: 5, time: 1000, shift: 6 });
				}
			}, false, "GET");
		}
	});


	/**
	 * 资源分配
	 */
	function resourceAllocation(edit) {
		layer.open({
			type: 2,
			title: '资源分配',
			area: ['300px', '400px'],
			content: '/page/system/scenicSemanticResources/html/resourceAllocation.html',
			success: function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				if (edit) {
					body.find(".scenicSpotId").val(edit.scenicSpotId);
					form.render();
				}
			}
		});
	}

})
