define([
    'text!modules/common/templates/MainView.html',
    'modules/common/views/HeaderView', 
    'Portal'
], function (MainViewTpl, HeaderView, Portal) {
	
	var firstView;
	var menus = {};

    return club.View.extend({
        template: club.compile(MainViewTpl),
        TABITEM_TAG: '-tabItem',
        events: {
            "click .content-wrapper": "doClickWrapper",
            "click .sidebar li>a": "doMenuLV1Click",
            "click .sidebar li>ul>a": "doMenuLv2Click"
        },
        serialize: function () {
            return club.extend({}, this.i18nData);
        },

        views: {
            '.main-header': new HeaderView()
        },

        initialize: function () {
            club.on('replaceTabView', $.proxy(this.replaceTabView, this));
        },
    	
        //这里用来初始化页面上要用到的club组件
        afterRender: function () {
    		var me = this;
    		var noticeFlag = false;
        	// 加载菜单
    		$.post(Portal.webRoot+'/security/function/getUserTree.do', {}, function(data){
    			if(data && data.data && data.data.length>0) {
    				data = data.data;
    				var menuBar = $(".sidebar-menu");
    				for(var i=0; i<data.length; i++) {
    					var menu = data[i];
    					if(menu && menu.children && menu.children.length>0) {
    						var li = $("<li class='treeview'></li>");
    						var a = $("<a href='javascript:void(0);' id='"+menu.name+"'></a>");
    						li.append(a);
    						var icon = $("<i class='fa fa-sitemap'></i>");
    						a.append(icon);
    						var name = $("<span>"+menu.note+"</span>");
    						a.append(name);
    						a.append("<i class='fa fa-angle-left pull-right'></i>");
    						menuBar.append(li);
    						var ul = $("<ul class='treeview-menu' style='display: none;'></ul>");
    						li.append(ul);
    						for(var j=0; j<menu.children.length; j++) {
    							var child = menu.children[j];
    							if(child) {
    								if(!firstView)
    									firstView = child;
    								menus[child.name] = child;
    								if(child.name == 'FUNC_NOTICE_LIST'){
    									noticeFlag = true;
        								ul.append("<li  style='display: none'><a href='javascript:void(0);' id='"+child.name+"'><i class='fa fa-user'></i>"+child.note+"</a></li>");
    								}else{
        								ul.append("<li><a href='javascript:void(0);' id='"+child.name+"'><i class='fa fa-user'></i>"+child.note+"</a></li>");
    								}
    							}
    						}
    					}
    				}
    			}

                // tab初始化
    			me._initTabs();

                // 初始化打开信息接报
    			if(firstView) {
    				$("#"+firstView.name).click();
    			}
            	if(!noticeFlag){
            		$("#noticeClick").hide();
            	}
    		});
			
            $(".sidebar-menu").css({
            	"max-height": ($(window).height()-50)+"px"
            });
        },

        _initTabs: function () {
            $('.main-content-tabs').tabs({
                paging: true,
                autoResizable: true,
                canClose: true
            });
        },

        doClickWrapper: function () {
            var screenSizes = {
                xs: 480,
                sm: 768,
                md: 992,
                lg: 1200
            };
            if ($(window).width() <= (screenSizes.sm - 1) && $("body").hasClass("sidebar-open")) {
                $("body").removeClass('sidebar-open');
            }
        },

        // 一级菜单导航点击事件处理
        doMenuLV1Click: function (event) {
            // 首先处理导航菜单的响应
            this.doExpandSidebar(event);
            // 打开相应的模块视图
            var moduleId = $(event.currentTarget).attr("id");
            this.doOpenModuleView(moduleId);
        },

        // 二级菜单导航点击事件处理
        doMenuLv2Click: function (event) {
            event.stopPropagation();
            var moduleId = $(event.currentTarget).attr("id");
            // 打开相应的模块视图
            this.doOpenModuleView(moduleId);

        },

        _switchButtonClick: function (moduleURL) {
            var me = this;
            require([moduleURL], function (View) {
                var view = new View();
                me.replaceView(".unionhandle-right-view", view);
            });
        },

        doOpenModuleView: function (moduleId) {
            var menu = menus[moduleId];
            if(!menu)
            	return;
            var allTabs = $(".main-content-tabs").tabs("getAllTabs", true, false);
            if (allTabs.length > 0) {
                var id_liItem;
                for (var index = 0; index < allTabs.length; index++) {
                    id_liItem = allTabs[index].children().attr("href");
                    // 去除#符号
                    id_liItem = id_liItem.substring(1, id_liItem.length);
                    if (id_liItem == (moduleId + this.TABITEM_TAG)) {
                        // 激活tabItem
                        this.doActiveTabItem(id_liItem);
                        return;
                    }
                }
            }
            this.doAddTabItem(moduleId, menu.note, menu.url);
        },

        // 打开新的标签页
        doAddTabItem: function (moduleId, moduleName, moduleURL) {
            if (moduleURL) {
                var viewId = moduleId + this.TABITEM_TAG + "";
                this.$('.main-content-tabs').tabs('add', {id: viewId, label: moduleName, active: true});
                this.requireView({selector: '#' + viewId, url: moduleURL});
            }
        },

        /**
         * 替换TabView
         * @param options
         */
        replaceTabView: function (options) {
            this.requireView(options);
        },

        // 定位到指定的标签页
        doActiveTabItem: function (moduleId) {
            $(".main-content-tabs").tabs("showTab", moduleId, true);
        },

        doExpandSidebar: function (e) {
            var thisView = this;
            var $this = $(e.currentTarget);
            var checkElement = $this.next();
            var animationSpeed = 500;
            // 无子菜单导航处理
            if (checkElement.length == 0) {
                var parent = $this.parents('ul').first();
                var ul = parent.find('ul:visible').slideUp(animationSpeed);
                //Remove the menu-open class from the parent
                ul.removeClass('menu-open');
                var parent_li = $this.parent("li");
                parent.find('li.active').removeClass('active');
                parent_li.addClass('active');
                //Fix the layout in case the sidebar stretches over the height of the window
                thisView.fix();
            }
            //Check if the next element is a menu and is visible
            else if ((checkElement.is('.treeview-menu')) && (checkElement.is(':visible'))) {
                //Close the menu
                checkElement.slideUp(animationSpeed, function () {
                    checkElement.removeClass('menu-open');
                });
                checkElement.parent("li").removeClass("active");
            }
            //If the menu is not visible
            else if ((checkElement.is('.treeview-menu')) && (!checkElement.is(':visible'))) {
                //Get the parent menu
                var parent = $this.parents('ul').first();
                //Close all open menus within the parent
                var ul = parent.find('ul:visible').slideUp(animationSpeed);
                //Remove the menu-open class from the parent
                ul.removeClass('menu-open');
                //Get the parent li
                var parent_li = $this.parent("li");

                //Open the target menu and add the menu-open class
                checkElement.slideDown(animationSpeed, function () {
                    //Add the class active to the parent li
                    checkElement.addClass('menu-open');
                    parent.find('li.active').removeClass('active');
                    parent_li.addClass('active');
                    //Fix the layout in case the sidebar stretches over the height of the window
                    thisView.fix();
                });
            }
            //if this isn't a link, prevent the page from being redirected
            if (checkElement.is('.treeview-menu')) {
                e.preventDefault();
            }
        },

        fix: function () {
            //Get window height and the wrapper height
            var neg = $('.main-header').outerHeight() + $('.main-footer').outerHeight();
            var window_height = $(window).height();
            var sidebar_height = $(".sidebar").height();
            //Set the min-height of the content and sidebar based on the
            //the height of the document.
            if ($("body").hasClass("fixed")) {
                $(".content-wrapper, .right-side").css('min-height', window_height - $('.main-footer').outerHeight());
            } else {
                var postSetWidth;
                if (window_height >= sidebar_height) {
                    $(".content-wrapper, .right-side").css('min-height', window_height - neg);
                    postSetWidth = window_height - neg;
                } else {
                    $(".content-wrapper, .right-side").css('min-height', sidebar_height);
                    postSetWidth = sidebar_height;
                }

                //Fix for the control sidebar height
                var controlSidebar = $('.control-sidebar');
                if (typeof controlSidebar !== "undefined") {
                    if (controlSidebar.height() > postSetWidth)
                        $(".content-wrapper, .right-side").css('min-height', controlSidebar.height());
                }

            }
        },

        switchFirstMenu: function (event) {
            var moduleId = $(event.target).attr("id");
            var moduleURL = menus[moduleId].url;
//            var moduleURL = viewMapping[moduleId];
            var me = this;
            require([moduleURL], function (View) {
                var view = new View();
                me.replaceView(".main-view-main", view);
            });
        }
    });
});