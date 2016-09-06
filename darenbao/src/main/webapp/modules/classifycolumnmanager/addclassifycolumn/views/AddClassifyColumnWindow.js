define(['text!modules/classifycolumnmanager/addclassifycolumn/templates/addClassifyColumn.html',
		'Portal', 'modules/upload/Uploader' ],
		function(addClassifyColumnTpl, Portal, Uploader) {
			var options = {
				height : $(window).height() * 0.6,
				width: $(window).width()*0.3,
				modal : false,
				draggable : false,
				content : addClassifyColumnTpl,
				autoResizable : true
			};
			var popup, logoUploader, max_file_count = 1;
			var image_list = [];

			return {
				openAddWin : function(listView, action, editValue) {
					image_list = [];
					var me = this;
					var checkValue = $.map(editValue, function(i) {
						return i;
					}).join(",");
					popup = club.popup($.extend({}, options, {
						modal : true
					}))
					$('.classifyColumnWindow input[field=spinner]').spinner({
						min : 0
					});
					if (action == 'edit') {
						if(editValue.imgUrl != undefined && editValue.imgUrl != ''){
							image_list.push({
								"id" : editValue.id,
								"url" : editValue.imgUrl
							});
						}
					}
					me.loadImgUrl();
					me.initTree();
					$('.classifyColumnWindow button').click(function() {
						var btnAction = $(this).attr('action');
						switch (btnAction) {
						case 'save-button':
							var imgUrl = logoUploader.get()[0];
							var imgUrlValue = '';
							if(imgUrl != undefined && imgUrl.url != undefined){
								imgUrlValue = imgUrl.url;
							}
							$('form[name=classify-column-form] input[name=imgUrl]').val(imgUrlValue);												
							var isValid = $('form[name=classify-column-form]').isValid();
							if (isValid) {
								var value = $('form[name=classify-column-form').form('value');
								var url = Portal.webRoot+ '/stock/classifyColumn/saveOrUpdate.do';
								console.log(value);
								$.post(url, {modelJson : JSON.stringify(value)}, function(result) {
									if (result.code == 1) {
										listView.callback.refresh();
										popup.close();
									} else {
										club.toast('error',result.msg);
									}

								});
							}
							;
							break;
						case 'cancel-button':
							popup.close();
							break;
						}
					});
					if (action == 'edit') {
						$('form[name=classify-column-form').form('value',editValue);
						$('.modal-title').html('编辑货品分类栏目');
					}
				},
				loadImgUrl : function() {
					logoUploader = Uploader.create({
						container : "small_img_container",
						max_file_count : max_file_count,
						image_list : image_list
					});
				},
				 initTree:function(action){
			        	/*$.ajax({  
				               type: "post",  
				               url: Portal.webRoot+'/stock/tree/queryTreeMenu.do',  
				               cache:false,  
				               async:false,  
				               dataType: "json",  
				               success: function(result){  
				                	 if(result!=null&&result!=''){
				                		  zNodes = eval("("+result+")");
				                 	}
				                }  
				        });*/
			        	var options = {
			        			view: {
			        				dblClickExpand: true,
			        				showIcon:true,
			        				showLinel:true
			        			},
			                    async: {
			                        enable: true,
			                        contentType: "application/x-www-form-urlencoded",
			                        type: "post",
			                        dataType: "text",
			                        url: Portal.webRoot+'/stock/tree/queryTreeMenu.do',
			                        autoParam: [],
			                        otherParam: [],
			                        dataFilter: function(treeId, parentNode, data){
			                        	return data;
			                        }
			                    },
			        			data: {
			        				simpleData: {
			                            enable: true,
			                            idKey: "id",
			                            pIdKey: "pId",
			                            rootPId: ""    
			        				}
			        			},
			        			callback: {
			        				beforeClick: beforeClick,
			        				onClick: onClick,
			        	            onAsyncSuccess : function(event, treeId, treeNode, msg){    
			        	            }
			        			}/*,
			        			fNodes:zNodes*/
			        		};
			        		$("#treeDemoStockCheck").tree(options);

			        		$("#tree_select_menu_html_stock_check").position({ 
			        			of: $( "#stock-chechk-classify" ),
			        			my: "left top",
			        			at: "left bottom"
			        		}).hide();

			        		$("#stock-menuBtn-check").click(function(event) {
			        			showMenu();
			        			return false;
			        		});
			        		function beforeClick(e,treeNode) {
			        			var check = true;//(treeNode && !treeNode.isParent);
			        			/*if (!check) fish.info("只能选择城市...");*/
			        			return check;
			        		}
			        		function onClick(e, treeNode) {
			        			/*var nodes = $("#treeDemoStockCheck").tree("getSelectedNodes"),
			        				v = "";
			        			nodes.sort(function compare(a,b){return a.id-b.id;});
			        			for (var i=0, l=nodes.length; i<l; i++) {
			        				v += nodes[i].name + ",";
			        			}
			        			if (v.length > 0 ) {
			        				v = v.substring(0, v.length-1);
			        			}*/
			        			var cityObj = $("#stock-chechk-classify");
			        			cityObj.attr("value", treeNode.name);
			        			$("#stock-chechk-classify_val").val(treeNode.id)
			        			$('#stock-chechk-classify').val(treeNode.name)
			        			hideMenu();
			        		}

			        		function showMenu() {
			        			$("#tree_select_menu_html_stock_check").slideDown("fast");
			        			//$("body").on("mousedown", onBodyDown);
			        		}
			        		function hideMenu() {
			        			$("#tree_select_menu_html_stock_check").fadeOut("fast");
			        			//$("body").on("mousedown", onBodyDown);
			        		}
			        		$("body").on("mousedown", function(event){
			        			if (!(event.target.id == "stock-menuBtn-check" || event.target.id == "tree_select_menu_html_stock_check" || $(event.target).parents("#tree_select_menu_html_stock_check").length>0)) {
			        				hideMenu();
			        			}
			        		});
			        }
			};
		});
