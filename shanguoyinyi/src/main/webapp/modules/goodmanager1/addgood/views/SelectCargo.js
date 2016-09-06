define(
		[ 'text!modules/goodmanager1/addgood/templates/selectCargo.html',
		  'modules/goodmanager1/addgood/views/AddGoodWindow',
				'css!styles/css/good.css', 'Portal' ],
		function(addViewTpl,AddgoodWindow, addCss,Portal) {
			var options = {
				height : $(window).height() * 0.9,
				modal : false,
				draggable : false,
				content : addViewTpl,
				autoResizable : true,
				dialogClass : addCss
			};
			
			var GRID_DOM = "#selectgood-info-content-div";
			var search=function(){
	            pageData(1);
	        };
			$("#selectgood-info-content-div .cbox").hide();
		    var pageData =function (page, rowNum, sortname, sortorder) {
	            var conditions = $('input[name=good-select-conditions]').val();
	            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
	           // var classifyId = classifySelect.get();
	            var data = {
	            	'name':'%'+conditions+'%',
	                'classifyId': "0", 
	                'brandId':  "0"
	            };
	            
	            $.post(Portal.webRoot+'/cargo/getList.do',{
	                conditionStr:JSON.stringify(data),
	                start:(page-1)*rowNum,limit:page*rowNum,name:'client'},function(result){
	                result = Portal.convertPage(result);
	                result.page = page;
	                $(GRID_DOM).grid("reloadData", result);
	            });
	            return false;
	        }
	        var _initListView=function(){
	            var me = this;
	            //参数配置
	            var opt = {
	                height:$(window).height()-125,
	                width:$(window).width(),
	                colModel: [
	                    {name: 'id', hidden:true, key:true}, 
	                    {name: 'cargoNo', label: '货品编号', width:60, align: "center", sortable: false},  
	                    {name: 'smallImage', label: '缩略图', width: 60, align: "center", headertitle : '名称', sortable: false,  //列头的提示
	                    	formatter: function(cellval, opts, rwdat, _act){
		                        var imagUrl =  "./image/user5-128x128.jpg";
		                        if (rwdat.smallImage) {
		                            imagUrl = rwdat.smallImage;
		                        };
		                        return '<img src = "'+imagUrl+'" class="client-face-img"/>';
	                    	}
	                    }, 
	                    {name: 'name', label: '名称', width: 80, align: "center", title: false, sortable: false},  //内容没有提示
	                    {name: 'classify', label: '分类', width: 80, align: "center", sortable: false}, 
	                    {name: 'brand', label: '品牌', width: 80, align: "center", sortable: false}              
	                ], 
	                pager: true, 
	                rowNum: 20, 
	                rowList: [10,20,50,100,200], 
	                datatype: "json", 
	                recordtext:"{0} 到 {1} 总共: {2} ", 
	                pgtext : " {0} / {1}", 
	                rowtext:"{0}", 
	                gotext:'跳转至第{0}页', 
	                multiselect:false, 
	                pageData:pageData
	            };
	            pageData(1);
	            //加载grid
	            $grid = $(GRID_DOM).grid(opt);
	            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
	            //classifySelect.init(".cargo-manager-classify-group", "info");
	            //brandSelect.init(".btn-group-brand", "info");
	        }
			var popup;
            
			return {
				openAddwin : function(listView, action, editValue) {

					popup = club.popup($.extend({}, options, {
						modal : true
					}))
					_initListView();
				
//					$("#selectgood-info-content-div.ch")
//					
					$('.selectCargoWindow button').click(function(){
		                var btnAction = $(this).attr('action');
		                switch(btnAction){
		                   case 'save-button':
		                  var record=$(GRID_DOM).grid('getSelection');
		                  console.log(record);
		                  if(record==null||record.id==null||record.id==''){
		                	  club.toast('warn', "请选择货品,否则无法添加商品");
		                  }else{
		                	  AddgoodWindow.openAddWin(listView,action,record);
			                    popup.close();
		                  }break;  
		                    case 'cancel-button': popup.close();break;
		                    case 'search' : search(); break;
		                }
		            });

				}

			};
		});