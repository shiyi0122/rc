var tabFilter,menu=[],liIndex,curNav,delMenu,
    changeRefreshStr = window.sessionStorage.getItem("changeRefresh");
layui.define(["element","jquery"],function(exports){
	var element = layui.element,
		$ = layui.$,
		layId,
		Tab = function(){
			this.tabConfig = {
				openTabNum : undefined,  //最大可打开窗口数量
				tabFilter : "bodyTab",  //添加窗口的filter
				url : undefined  //获取菜单json地址
			}
		};
    //生成左侧菜单
    Tab.prototype.navBar = function(strData){
        var data;
        if(typeof(strData) == "string"){
            var data = JSON.parse(strData); //部分用户解析出来的是字符串，转换一下
        }else{
            data = strData;
        }
        var ulHtml = '';
        for(var i=0;i<data.length;i++){
            /*if(data[i].spread || data[i].spread == undefined){
                ulHtml += '<li class="layui-nav-item layui-nav-itemed">';
            }else{
                ulHtml += '<li class="layui-nav-item">';
            }*/
			ulHtml += '<li class="layui-nav-item">';
            if(data[i].child != undefined && data[i].child.length > 0){
                ulHtml += '<a>';
                if(data[i].resIcon != undefined && data[i].resIcon != ''){
                    if(data[i].resIcon.indexOf("icon-") != -1){
                        ulHtml += '<i class="iconfont '+data[i].resIcon+'"></i>';
                    }else{
                        ulHtml += '<i class="iconfont '+data[i].resIcon+'"></i>';
                    }
                }
                ulHtml += '<cite>'+data[i].resName+'</cite>';
                ulHtml += '<span class="layui-nav-more"></span>';
                ulHtml += '</a>';
                ulHtml += '<dl class="layui-nav-child">';
                for(var j=0;j<data[i].child.length;j++){
                	if(data[i].resName == "操作日志管理" && data[i].child[j].resName == "机器人异常监控日志") {
						ulHtml += '<dd id="operateLogManage">'
					} else {
						ulHtml += '<dd>'
					}
                	if(data[i].resName == "操作日志管理" && data[i].child[j].resName == "机器人异常监控日志") {
						ulHtml += '<a id="badgeA" data-url="'+data[i].child[j].resUrl+'">';
					}else if(data[i].child[j].target == "_blank"){
                        ulHtml += '<a data-url="'+data[i].child[j].resUrl+'" target="'+data[i].child[j].target+'">';
                    }else{
                        ulHtml += '<a data-url="'+data[i].child[j].resUrl+'">';
                    }

                    if(data[i].child[j].resIcon != undefined && data[i].child[j].resIcon != ''){
                        if(data[i].child[j].resIcon.indexOf("icon-") != -1){
                            ulHtml += '<i class="iconfont '+data[i].child[j].resIcon+'"></i>';
                        }else{
                            ulHtml += '<i class="iconfont '+data[i].child[j].resIcon+'"></i>';
                        }
                    }
                    ulHtml += '<cite>'+data[i].child[j].resName+'</cite>';

					ulHtml += '</a>'

					if(data[i].child[j].child != undefined && data[i].child[j].child.length > 0){
						ulHtml += '<dl class="layui-nav-child">';
						for(var k=0;k < data[i].child[j].child.length; k++){
							if(data[i].child[j].child[k].target == "_blank"){
								ulHtml += '<dd><a data-url="'+data[i].child[j].child[k].resUrl+'" target="'+data[i].child[j].child[k].target+'">';
							}else{
								ulHtml += '<dd><a data-url="'+data[i].child[j].child[k].resUrl+'">';
							}
							if(data[i].child[j].child[k].resIcon != undefined && data[i].child[j].child[k].resIcon != ''){
								if(data[i].child[j].child[k].resIcon.indexOf("icon-") != -1){
									ulHtml += '<i class="iconfont '+data[i].child[j].child[k].resIcon+'"></i>';
								}else{
									ulHtml += '<i class="iconfont '+data[i].child[j].child[k].resIcon+'"></i>';
								}
							}
							ulHtml += '<cite>'+data[i].child[j].child[k].resName+'</cite></a></dd>';

						}
						ulHtml += "</dl>";
					}
					ulHtml += "</dd>";
                }
                ulHtml += "</dl>";
            }else{
                if(data[i].target == "_blank"){
                    ulHtml += '<a data-url="'+data[i].resUrl+'" target="'+data[i].target+'">';
                }else{
                    ulHtml += '<a data-url="'+data[i].resUrl+'">';
                }
                if(data[i].resIcon != undefined && data[i].resIcon != ''){
                    if(data[i].resIcon.indexOf("icon-") != -1){
                        ulHtml += '<i class="iconfont '+data[i].resIcon+'"></i>';
                    }else{
                        ulHtml += '<i class="iconfont '+data[i].resIcon+'"></i>';
                    }
                }
                ulHtml += '<cite>'+data[i].resName+'</cite></a>';
            }
            ulHtml += '</li>';
        }
        return ulHtml;
    }
	//获取二级菜单数据
	Tab.prototype.render = function() {
		//显示左侧菜单
		var _this = this;
		$(".navBar ul").html('<li class="layui-nav-item layui-this"><a data-url="page/main.html"><i class="layui-icon" data-icon=""></i><cite>后台首页</cite></a></li>').append(_this.navBar(dataStr)).height($(window).height()-210);
		element.init();  //初始化页面元素
		$(window).resize(function(){
			$(".navBar").height($(window).height()-210);
		})

		// 监听机器人异常监控日志点击事件
		const operateLogManageEl = document.querySelector("#operateLogManage")
		operateLogManageEl && operateLogManageEl.addEventListener("click", () => {
			// 删除徽章
			const badgeEl = document.querySelector("#badge")
			badgeEl && badgeEl.parentNode.removeChild(badgeEl)
		})

		queryNewMessage() // 查询是否有新消息提醒
	}


	// 查询是否有新消息提醒
	function queryNewMessage() {
		$.ajax({
			url: "/system/sysRobotUnusualLog/ifSysRobotBadge",
			type: "get",
			cache:false,
			success: function (e) {
				console.log(e)

				if(e) {
					// 创建徽章
					const spanEl = document.createElement("span")
					spanEl.setAttribute("id", "badge")
					spanEl.setAttribute("class", "layui-badge-dot layui-bg-orange")
					const badgeAEl = document.querySelector("#badgeA")
					badgeAEl && badgeAEl.appendChild(spanEl)
				}
			},
			async: false
		});
	}
	setInterval(() => {
		queryNewMessage()
	}, 1000 * 30)

	//是否点击窗口切换刷新页面
	Tab.prototype.changeRegresh = function(index){
        if(changeRefreshStr == "true"){
            $(".clildFrame .layui-tab-item").eq(index).find("iframe")[0].contentWindow.location.reload();
        }
	}

	//参数设置
	Tab.prototype.set = function(option) {
		var _this = this;
		$.extend(true, _this.tabConfig, option);
		return _this;
	};

	//通过title获取lay-id
	Tab.prototype.getLayId = function(title){
		$(".layui-tab-title.top_tab li").each(function(){
			if($(this).find("cite").text() == title){
				layId = $(this).attr("lay-id");
			}
		})
		return layId;
	}
	//通过title判断tab是否存在
	Tab.prototype.hasTab = function(title){
		var tabIndex = -1;
		$(".layui-tab-title.top_tab li").each(function(){
			if($(this).find("cite").text() == title){
				tabIndex = 1;
			}
		})
		return tabIndex;
	}

	//右侧内容tab操作
	var tabIdIndex = 0;
	Tab.prototype.tabAdd = function(_this){
		if(window.sessionStorage.getItem("menu")){
			menu = JSON.parse(window.sessionStorage.getItem("menu"));
		}
		var that = this;
		var openTabNum = that.tabConfig.openTabNum;
			tabFilter = that.tabConfig.tabFilter;
		if(_this.attr("target") == "_blank"){
			window.open(_this.attr("data-url"));
		}else if(_this.attr("data-url") != undefined){
			var title = '';
			if(_this.find("i.seraph,i.layui-icon").attr("data-icon") != undefined){
				if(_this.find("i.seraph").attr("data-icon") != undefined){
					title += '<i class="seraph '+_this.find("i.seraph").attr("data-icon")+'"></i>';
				}else{
					title += '<i class="layui-icon">'+_this.find("i.layui-icon").attr("data-icon")+'</i>';
				}
			}
			//已打开的窗口中不存在
			if(that.hasTab(_this.find("cite").text()) == -1 && _this.siblings("dl.layui-nav-child").length == 0){
				if($(".layui-tab-title.top_tab li").length == openTabNum){
					layer.msg('只能同时打开'+openTabNum+'个选项卡哦。不然系统会卡的！');
					return;
				}
				tabIdIndex++;
				title += '<cite>'+_this.find("cite").text()+'</cite>';
				title += '<i class="layui-icon layui-unselect layui-tab-close" data-id="'+tabIdIndex+'">&#x1006;</i>';
				element.tabAdd(tabFilter, {
			        title : title,
			        content :"<iframe src='"+_this.attr("data-url")+"' data-id='"+tabIdIndex+"'></frame>",
			        id : new Date().getTime()
			    })
				//当前窗口内容
				var curmenu = {
					"icon" : _this.find("i.seraph").attr("data-icon")!=undefined ? _this.find("i.seraph").attr("data-icon") : _this.find("i.layui-icon").attr("data-icon"),
					"title" : _this.find("cite").text(),
					"href" : _this.attr("data-url"),
					"layId" : new Date().getTime()
				}
				menu.push(curmenu);
				window.sessionStorage.setItem("menu",JSON.stringify(menu)); //打开的窗口
				window.sessionStorage.setItem("curmenu",JSON.stringify(curmenu));  //当前的窗口
				element.tabChange(tabFilter, that.getLayId(_this.find("cite").text()));
				that.tabMove(); //顶部窗口是否可滚动
			}else{
				//当前窗口内容
				var curmenu = {
					"icon" : _this.find("i.seraph").attr("data-icon")!=undefined ? _this.find("i.seraph").attr("data-icon") : _this.find("i.layui-icon").attr("data-icon"),
					"title" : _this.find("cite").text(),
					"href" : _this.attr("data-url")
				}
                that.changeRegresh(_this.parent('.layui-nav-item').index());
				window.sessionStorage.setItem("curmenu", JSON.stringify(curmenu));  //当前的窗口
				element.tabChange(tabFilter, that.getLayId(_this.find("cite").text()));
				that.tabMove(); //顶部窗口是否可滚动
			}
		}
	}

	//顶部窗口移动
	Tab.prototype.tabMove = function(){
		$(window).on("resize",function(event){
			var topTabsBox = $("#top_tabs_box"),
				topTabsBoxWidth = $("#top_tabs_box").width(),
				topTabs = $("#top_tabs"),
				topTabsWidth = $("#top_tabs").width(),
				tabLi = topTabs.find("li.layui-this"),
				top_tabs = document.getElementById("top_tabs"),
				event = event || window.event;

			if(topTabsWidth > topTabsBoxWidth){
				if(tabLi.position().left > topTabsBoxWidth || tabLi.position().left+topTabsBoxWidth > topTabsWidth){
					topTabs.css("left",topTabsBoxWidth-topTabsWidth);
				}else{
					topTabs.css("left",-tabLi.position().left);
				}
				//拖动效果
				var flag = false;
				var cur = {
				    x:0,
				    y:0
				}
				var nx,dx,x ;
				function down(event){
				    flag = true;
				    var touch ;
				    if(event.touches){
				        touch = event.touches[0];
				    }else {
				        touch = event;
				    }
				    cur.x = touch.clientX;
				    dx = top_tabs.offsetLeft;
				}
				function move(event){
					var self = this;
                    if(flag){
						window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
				        var touch ;
				        if(event.touches){
				            touch = event.touches[0];
				        }else {
				            touch = event;
				        }
				        nx = touch.clientX - cur.x;
				        x = dx+nx;
				        if(x > 0){
				        	x = 0;
				        }else{
				        	 if(x < topTabsBoxWidth-topTabsWidth){
				        	 	x = topTabsBoxWidth-topTabsWidth;
				        	 }else{
				        	 	x = dx+nx;
				        	 }
				        }
				        top_tabs.style.left = x +"px";
				        //阻止页面的滑动默认事件
				        document.addEventListener("touchmove",function(){
				            event.preventDefault();
				        },false);
				    }
				}
				//鼠标释放时候的函数
				function end(){
				    flag = false;
				}
				//pc端拖动效果
				topTabs.on("mousedown",down);
				topTabs.on("mousemove",move);
				$(document).on("mouseup",end);
				//移动端拖动效果
				topTabs.on("touchstart",down);
				topTabs.on("touchmove",move);
				topTabs.on("touchend",end);
			}else{
				//移除pc端拖动效果
				topTabs.off("mousedown",down);
				topTabs.off("mousemove",move);
				topTabs.off("mouseup",end);
				//移除移动端拖动效果
				topTabs.off("touchstart",down);
				topTabs.off("touchmove",move);
				topTabs.off("touchend",end);
				topTabs.removeAttr("style");
				return false;
			}
		}).resize();
	}

    //切换后获取当前窗口的内容
	$("body").on("click",".top_tab li",function(){
		var curmenu = '';
		var menu = JSON.parse(window.sessionStorage.getItem("menu"));
        if(window.sessionStorage.getItem("menu")) {
            curmenu = menu[$(this).index() - 1];
        }
		if($(this).index() == 0){
			window.sessionStorage.setItem("curmenu",'');
		}else{
			window.sessionStorage.setItem("curmenu",JSON.stringify(curmenu));
			if(window.sessionStorage.getItem("curmenu") == "undefined"){
				//如果删除的不是当前选中的tab,则将curmenu设置成当前选中的tab
				if(curNav != JSON.stringify(delMenu)){
					window.sessionStorage.setItem("curmenu",curNav);
				}else{
					window.sessionStorage.setItem("curmenu",JSON.stringify(menu[liIndex-1]));
				}
			}
		}
		element.tabChange(tabFilter,$(this).attr("lay-id")).init();
        bodyTab.changeRegresh($(this).index());
		setTimeout(function(){
			bodyTab.tabMove();
		},100);
	})

	//删除tab
	$("body").on("click",".top_tab li i.layui-tab-close",function(){
		//删除tab后重置session中的menu和curmenu
		liIndex = $(this).parent("li").index();
		var menu = JSON.parse(window.sessionStorage.getItem("menu"));
		if(menu != null) {
            //获取被删除元素
            delMenu = menu[liIndex - 1];
            var curmenu = window.sessionStorage.getItem("curmenu") == "undefined" ? undefined : window.sessionStorage.getItem("curmenu") == "" ? '' : JSON.parse(window.sessionStorage.getItem("curmenu"));
            if (JSON.stringify(curmenu) != JSON.stringify(menu[liIndex - 1])) {  //如果删除的不是当前选中的tab
                // window.sessionStorage.setItem("curmenu",JSON.stringify(curmenu));
                curNav = JSON.stringify(curmenu);
            } else {
                if ($(this).parent("li").length > liIndex) {
                    window.sessionStorage.setItem("curmenu", curmenu);
                    curNav = curmenu;
                } else {
                    window.sessionStorage.setItem("curmenu", JSON.stringify(menu[liIndex - 1]));
                    curNav = JSON.stringify(menu[liIndex - 1]);
                }
            }
            menu.splice((liIndex - 1), 1);
            window.sessionStorage.setItem("menu", JSON.stringify(menu));
        }
		element.tabDelete("bodyTab",$(this).parent("li").attr("lay-id")).init();
		bodyTab.tabMove();
	})

	//刷新当前
	$(".refresh").on("click",function(){  //此处添加禁止连续点击刷新一是为了降低服务器压力，另外一个就是为了防止超快点击造成chrome本身的一些js文件的报错(不过貌似这个问题还是存在，不过概率小了很多)
		if($(this).hasClass("refreshThis")){
			$(this).removeClass("refreshThis");
			$(".clildFrame .layui-tab-item.layui-show").find("iframe")[0].contentWindow.location.reload();
			setTimeout(function(){
				$(".refresh").addClass("refreshThis");
			},2000)
		}else{
			layer.msg("您点击的速度超过了服务器的响应速度，还是等两秒再刷新吧！");
		}
	})

	//关闭其他
	$(".closePageOther").on("click",function(){
		if($("#top_tabs li").length>2 && $("#top_tabs li.layui-this cite").text()!="后台首页"){
			var menu = JSON.parse(window.sessionStorage.getItem("menu"));
			$("#top_tabs li").each(function(){
				if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
					element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
					//此处将当前窗口重新获取放入session，避免一个个删除来回循环造成的不必要工作量
					for(var i=0;i<menu.length;i++){
						if($("#top_tabs li.layui-this cite").text() == menu[i].title){
							menu.splice(0,menu.length,menu[i]);
							window.sessionStorage.setItem("menu",JSON.stringify(menu));
						}
					}
				}
			})
		}else if($("#top_tabs li.layui-this cite").text()=="后台首页" && $("#top_tabs li").length>1){
			$("#top_tabs li").each(function(){
				if($(this).attr("lay-id") != '' && !$(this).hasClass("layui-this")){
					element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
					window.sessionStorage.removeItem("menu");
					menu = [];
					window.sessionStorage.removeItem("curmenu");
				}
			})
		}else{
			layer.msg("没有可以关闭的窗口了@_@");
		}
		//渲染顶部窗口
		tab.tabMove();
	})
	//关闭全部
	$(".closePageAll").on("click",function(){
		if($("#top_tabs li").length > 1){
			$("#top_tabs li").each(function(){
				if($(this).attr("lay-id") != ''){
					element.tabDelete("bodyTab",$(this).attr("lay-id")).init();
					window.sessionStorage.removeItem("menu");
					menu = [];
					window.sessionStorage.removeItem("curmenu");
				}
			})
		}else{
			layer.msg("没有可以关闭的窗口了@_@");
		}
		//渲染顶部窗口
		tab.tabMove();
	})

	var bodyTab = new Tab();
	exports("bodyTab",function(option){
		return bodyTab.set(option);
	});
})
