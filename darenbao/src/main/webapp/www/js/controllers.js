angular.module('starter.controllers', [])
    .controller('homeCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams',
        function($scope,$state,doService,$window,$rootScope,$stateParams) {
        // var shopId='248474721296064512';
        var shopId=$stateParams.shopId;
        $scope.totalOrder={
            historyNumTotal:0,
            hostoryAmtTotal:0.00,
            todayNumTotal:0,
            tomorowAmtTotal:0.00
        };
        $scope.shopInfo={};
        $scope.$on("$ionicView.beforeEnter", function(event, data){
            doService.getTotalOrderData({shopId:shopId}).then(
                function(suc){
                    $scope.totalOrder=suc;
                },function(err){
                    console.log(err);
                });
            doService.getShopInfo({shopId:shopId}).then(
                function(suc){
                    $scope.shopInfo=suc;
                    console.log(suc);
                },function(err){
                    console.log(err);
                });
        });
        //个人中心首页
        $scope.personal = function(){
            $state.go('personCenter',{shopId: shopId})
        };

        //活动部落
         $scope.goActivity=function(){
             $state.go("activityList",{shopId:shopId});
         };

        $scope.goShareList=function(){
            $state.go("shareList",{shopId:shopId});
            //alert(shopId);
        };
        //店铺管理-分类2016.6.29修改
        $scope.goShopClassify=function () {
            $state.go("shopGoodClassify",{shopId:shopId})
        };
        /*$scope.goShopManage =function(){
          $state.go("shopGoodList",{shopId:shopId});
        };*/
        $scope.goStudyList=function(){
            $state.go("studyList",{shopId:shopId});
            //alert(shopId);
        };
        //更多
        $scope.goCompanyShow=function(){
        	$state.go("companyShow");
        	//alert(shopId);
        };


    }])
    .controller('pcCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams',
        function($scope,$state,doService,$window,$rootScope,$stateParams) {
    		var shopId = $stateParams.shopId;
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                doService.getShopInfo({shopId:$stateParams.shopId}).then(
                    function(suc){
                        $scope.shopInfo = suc;
                        console.log(suc);
                    },function(err){
                        console.log(err);
                    });
            });

            $scope.personalName='个人中心';
            //个人中心-粉丝管理
            $scope.goFansList = function(){
            	$state.go('fansList',{shopId: shopId})
            };
            //个人中心-兑换券列表
            $scope.goVouchList = function(){
            	$state.go('vouchList',{shopId: shopId})
            };
            //个人中心-创建的活动
            $scope.goActivityPersonList = function(){
            	$state.go('activityPersonList',{shopId: shopId,statu:1})
            };
            //个人中心-我的账户
            $scope.goAccountMsg = function(){
            	$state.go('accountMsg',{shopId: shopId})
            };
            //个人信息
            $scope.personalInfo = function(){
                $state.go('personInfo',{shopId: $stateParams.shopId})
            };
            //设置
            $scope.setting = function(){
                $state.go('setting',{shopId: $stateParams.shopId})
            };
            //我的订单
            $scope.orderList = function(){
                $state.go('orderList',{shopId: $stateParams.shopId})
            };
        }])
    //个人信息首页
    .controller('pcInfoCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicLoading',
        function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicLoading) {
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                doService.getShopInfo({shopId:$stateParams.shopId}).then(
                    function(suc){
                        $scope.shopInfo = suc;
                        console.log(suc);
                    },function(err){
                        console.log(err);
                    });
            });
           var tempUrl=$rootScope.qiNiu;
           var saveShopImage = function(url){
                var param = {
                    shopId:$stateParams.shopId,
                    headUrl:url
                };
                doService.saveShopName({"param":param}).then(
                    function(suc){
                        if(suc.code == 0){
                            $scope.shopInfo.headImgUrl=tempUrl;
                        }
                    },function(err){
                        console.log(err);
                    }).finally(function(){
                        $scope.hide();
                        tempUrl=$rootScope.qiNiu;
                });
            };
            window.setHeadImg=function(type){
                tempUrl+=type;
                $scope.show();
                saveShopImage(tempUrl);
            };
            $scope.show = function() {
                $ionicLoading.show({
                    template: '头像上传中'
                });
            };
            $scope.hide = function(){
                $ionicLoading.hide();
            };
            $scope.choosePic=function(){
                if(ionic.Platform.isAndroid()){
                    webapp.imageChoose("1");
                }else if(ionic.Platform.isIOS()){
                    if(native)
                        native.send({action:'imageChoose', type: 1},function() {});
                }
            };
            //编辑店铺名称
            $scope.editShopName = function(){
                $state.go('editShopName',{shopId:$stateParams.shopId,columnVal: $scope.shopInfo.name,columnName:'name'})
            };

            //编辑昵称
            $scope.editUserName = function(){
                $state.go('editShopName',{shopId:$stateParams.shopId,columnVal: $scope.shopInfo.userName,columnName:'userName'})
            };

            //编辑手机号
            $scope.editMobile = function(){
                $state.go('editShopName',{shopId:$stateParams.shopId,columnVal: $scope.shopInfo.phone,columnName:'phone'})
            };

            //编辑微信号
            $scope.editWeixin = function(){
                $state.go('editShopName',{shopId:$stateParams.shopId,columnVal: $scope.shopInfo.weixin,columnName:'weixin'})
            };

            //编辑二维码
            $scope.editQrcode = function(){
                $state.go('shopQrcode',{shopId:$stateParams.shopId})
            };

            //编辑店铺地址
            $scope.editAddress = function(){
                $state.go('editShopAddress',{shopId:$stateParams.shopId,province:$scope.shopInfo.province,city:$scope.shopInfo.city,
                    country:$scope.shopInfo.country,address:$scope.shopInfo.address});
            };

            //编辑店铺介绍
            $scope.editDescription = function(){
                $state.go('editShopName',{shopId:$stateParams.shopId,columnVal: $scope.shopInfo.description,columnName:'description'})
            };
        }])

    .controller('editShopNameCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','ionicToast',
        function($scope,$state,doService,$window,$rootScope,$stateParams,ionicToast) {
            $scope.data = {
                columnVal: $stateParams.columnVal
            }
            //保存店铺名称
            $scope.saveShopName = function(){
                var columnName = $stateParams.columnName;
                if(columnName == 'name'){
                    if($scope.data.columnVal == null || $scope.data.columnVal == ''){
                        return ionicToast.show('店铺名称不能为空', 'middle', false, 2000);
                    } else if($scope.data.columnVal.length > 10){
                        return ionicToast.show('很抱歉，店铺名称不能超过10个字', 'middle', false, 2000);
                    }
                }if(columnName == 'phone'){
                    if($scope.data.columnVal == null || $scope.data.columnVal == ''){
                        return ionicToast.show('门店电话不能为空', 'middle', false, 2000);
                    }
                    var re = /^[0-9]*]*$/;
                    if(!re.test($scope.data.columnVal)){
                        return ionicToast.show('请输入正确的门店电话', 'middle', false, 2000);
                    }
                }
                var param = {
                    shopId:$stateParams.shopId
                }
                param[columnName] = $scope.data.columnVal;
                doService.saveShopName({"param":param}).then(
                    function(suc){
                        if(suc.code == 0){
                            ionicToast.show('保存成功！', 'middle', false, 2000);
                            $state.go('personInfo',{shopId: $stateParams.shopId})
                        } else{
                            return ionicToast.show(suc.msg, 'middle', false, 2000);
                        }
                    },function(err){
                        console.log(err);
                    });
            };
        }])

    //修改门店地址
    .controller('editShopAddressController', ['$scope','$state','doService','$window','$rootScope','$stateParams','ionicToast',
        function($scope,$state,doService,$window,$rootScope,$stateParams,ionicToast) {
            console.log($stateParams.province);
            var cityNum={
                province:'',
                city:'',
                country:''
            };
            var address='';
            if($stateParams.province){
                cityNum.province=$stateParams.province;
            }
            if($stateParams.city){
                cityNum.city=$stateParams.city;
            }
            if($stateParams.country){
                cityNum.country=$stateParams.country;
            }
            if($stateParams.address){
                address=$stateParams.address;
            }
            $scope.city={
                citydata:'',
                cityNum:cityNum,
                address:address
            };
            $scope.saveShopAddress = function(){
                var cityData = $scope.city.citydata.split("-");
                var countryName = '';
                if(cityData.length > 2){
                    countryName = cityData[2];
                }
                var param = {
                    shopId : $stateParams.shopId,
                    province : $scope.city.cityNum.province,
                    city : $scope.city.cityNum.city,
                    //country : $scope.city.cityNum.country,
                    addres : $scope.city.address,
                    provinceName : cityData[0],
                    cityName : cityData[1]
                };
                if(countryName){
                    param['countryName'] = countryName;
                    param['country'] = $scope.city.cityNum.country;
                }
                doService.saveShopName({"param":param}).then(
                    function(suc){
                        if(suc.code == 0){
                            ionicToast.show('保存成功！', 'middle', false, 2000);
                            $state.go('personInfo',{shopId: $stateParams.shopId})
                        } else{
                            return ionicToast.show(suc.msg, 'middle', false, 2000);
                        }
                    },function(err){
                        console.log(err);
                    });
            };
        }])

    //获取活动部落列表
    .controller('activitylistCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicScrollDelegate',
        function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicScrollDelegate) {
            var shopId=$stateParams.shopId;
            $scope.isShow=true;
            $scope.shopId=shopId;

            $scope.$on("$ionicView.beforeEnter", function(event, data){
                $scope.loadMore();
            });

            //分页初始化
            $scope.start=0;
            $scope.limit=6;
            $scope.showScroll=false;
            $scope.activitys=[];
            $scope.doRefresh=function(){
                console.log("doRefresh");
                $scope.start=0;
                $scope.showScroll=true;
                doService.geActivityListData({"start":$scope.start,"limit":$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        $scope.activitys=date;
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                         $scope.$broadcast('scroll.refreshComplete');
                     });
            };
            $scope.loadMore = function() {
                if($scope.activitys.length>0){
                    $scope.start=$scope.start+ $scope.limit;
                }
                doService.geActivityListData({"start":$scope.start,"limit":$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.activitys, suc.resultList);
                        $ionicScrollDelegate.$getByHandle('activity-list').resize();
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                        setTimeout(function(){
                            $scope.$broadcast('scroll.infiniteScrollComplete');
                        },3000);
                    });
            };
        }])
    //获取活动报名名单
    .controller('activitySignUpCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicScrollDelegate',
        function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicScrollDelegate) {
            var activityId=$stateParams.activityId;
            var shopId=$stateParams.shopId;
            var userId=$stateParams.userId;
            $scope.shopId=shopId;
            $scope.activityId=activityId;
            $scope.userId=userId;

            $scope.isShow=true;

            //分页初始化
            $scope.start=0;
            $scope.limit=6;
            $scope.showScroll=false;
            $scope.users=[];
            $scope.doRefresh=function(){
                console.log("doRefresh");
                $scope.start=0;
                $scope.showScroll=true;
                doService.geActivitySignUpListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        $scope.users=date;
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                       $scope.$broadcast('scroll.refreshComplete');
                     });
            };
            $scope.loadMore = function() {
                if($scope.users.length>0){
                    $scope.start=$scope.start+ $scope.limit;
                }
                doService.geActivitySignUpListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.users, suc.resultList);
                        $ionicScrollDelegate.$getByHandle('activity-SignUp').resize();
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                        setTimeout(function(){
                            $scope.$broadcast('scroll.infiniteScrollComplete');
                        },3000);
                   });
            };
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                $scope.loadMore();
            });
        }])
    //获取活动评论列表
    .controller('activityCommentCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicScrollDelegate',
        function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicScrollDelegate) {
            var activityId=$stateParams.activityId;
            var shopId=$stateParams.shopId;
            $scope.shopId=shopId;
            $scope.activityId=activityId;

            $scope.isShow=true;

            //分页初始化
            $scope.start=0;
            $scope.limit=6;
            $scope.showScroll=false;
            $scope.comments=[];
            $scope.doRefresh=function(){
                console.log("doRefresh");
                $scope.start=0;
                $scope.showScroll=true;
                doService.geActivityCommentListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        $scope.comments=date;
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                       $scope.$broadcast('scroll.refreshComplete');
                     });
            };
            $scope.loadMore = function() {
                if($scope.comments.length>0){
                    $scope.start=$scope.start+ $scope.limit;
                }
                doService.geActivityCommentListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.comments, suc.resultList);
                        $ionicScrollDelegate.$getByHandle('activity-Comment').resize();
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                        setTimeout(function(){
                            $scope.$broadcast('scroll.infiniteScrollComplete');
                        },3000);
                     });
            };
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                $scope.loadMore();
            });

        }])
     //创建活动
    .controller('addActicityCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','ionicToast','$filter',
        function($scope,$state,doService,$window,$rootScope,$stateParams,ionicToast,$filter) {

            var shopId=$stateParams.shopId;
            $scope.shopId=shopId;

            var mymobile =/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
            var zhengzhengshu =/^[0-9]*[1-9][0-9]*$/;

            //活动信息
            $scope.activityInfo={};
            $scope.activitytypes={};

            //$scope.activityInfo.beginTime= new Date();
            //$scope.activityInfo.endTime= new Date();
            //$scope.activityInfo.regEndTime=new Date();

            //获取活动分类
            doService.getActivityTypeListData().then(
                function(suc){
                    $scope.activitytypes=suc;
                    angular.forEach($scope.activitytypes, function(data){
                        data.isActive=false;
                    });
                },function(err){
                    console.log(err);
                });

            //活动分类选择
            $scope.selectType=function(typeId){
                angular.forEach($scope.activitytypes, function(data){
                    if(data.id==typeId){
                        data.isActive=true;
                    }else{
                        data.isActive=false;
                    }
                });
            }

            var imageTemp="";
            $scope.activityInfo.activityPic=[];
            window.setHeadImg=function(type){
                imageTemp=type;
                $scope.$apply(function(){
                    var imageObj={
                        id:0,
                        url:$rootScope.qiNiu+'/'+imageTemp
                    }
                    $scope.activityInfo.activityPic.push(imageObj);
                });
            };
            $scope.choosePic=function(){
                if(ionic.Platform.isAndroid()){
                    webapp.imageChoose("1");
                }else if(ionic.Platform.isIOS()){
                    if(native)
                        native.send({action:'imageChoose', type: 1},function() {});
                }
            };
            //图片删除
            $scope.closeImage=function(index){
                $scope.activityInfo.activityPic.splice(index,1);
            }

            $scope.clickSubmit=1;
            //活动保存
            $scope.saveActivity=function(){
                angular.forEach($scope.activitytypes, function (data) {
                    if (data.isActive == true) {
                        $scope.activityInfo.activityTypeId = data.id;
                    }
                });

                if ($scope.activityInfo.title == null || $scope.activityInfo.title == '') {
                    ionicToast.show('请填写活动标题！', 'bottom', false, 2000);
                } else if ($scope.activityInfo.beginTime == null || $scope.activityInfo.beginTime == '') {
                    ionicToast.show('请选择活动开始时间！', 'bottom', false, 2000);
                } else if ($scope.activityInfo.endTime == null || $scope.activityInfo.endTime == '') {
                    ionicToast.show('请选择活动结束时间！', 'bottom', false, 2000);
                } else if ($scope.activityInfo.beginTime > $scope.activityInfo.endTime) {
                    ionicToast.show('开始时间要在结束时间之前！', 'bottom', false, 2000);
                } else if ($scope.activityInfo.sponsorTel == null || $scope.activityInfo.sponsorTel == '') {
                    ionicToast.show('请填写活动联系人联系方式！', 'bottom', false, 2000);
                } else if (!mymobile.test($scope.activityInfo.sponsorTel)) {
                    ionicToast.show('请填写正确的联系方式！', 'bottom', false, 2000);
                } else if ($scope.activityInfo.activityAddress == null || $scope.activityInfo.activityAddress == '') {
                    ionicToast.show('请填写活动地址！', 'bottom', false, 2000);
                } else if ($scope.activityInfo.numberLimit == null || $scope.activityInfo.numberLimit == '') {
                    ionicToast.show('请填写活动人数限制！', 'bottom', false, 2000);
                } else if (!zhengzhengshu.test($scope.activityInfo.numberLimit)) {
                    ionicToast.show('请填写正确活动人数限制！', 'bottom', false, 2000);
                } else if ($scope.activityInfo.regEndTime == null || $scope.activityInfo.regEndTime == '') {
                    ionicToast.show('请选择活动报名截止时间！', 'bottom', false, 2000);
                } else if ($scope.activityInfo.beginTime > $scope.activityInfo.regEndTime) {
                    ionicToast.show('开始时间要在报名截止时间之前！', 'bottom', false, 2000);
                } else if ($scope.activityInfo.activityTypeId == null || $scope.activityInfo.activityTypeId == '') {
                    ionicToast.show('请选择活动分类！', 'bottom', false, 2000);
                } else {
                    $scope.activityInfo.subbranchId = $scope.shopId;
                    doService.getShopInfo({shopId: shopId}).then(
                        function (suc) {
                            $scope.activityInfo.sponsorName = suc.name;
                            $scope.activityInfo.beginTime = $filter('date')($scope.activityInfo.beginTime, 'yyyy-MM-dd HH:mm:ss');
                            $scope.activityInfo.endTime = $filter('date')($scope.activityInfo.endTime, 'yyyy-MM-dd HH:mm:ss');
                            $scope.activityInfo.regStartTime = $filter('date')($scope.activityInfo.beginTime, 'yyyy-MM-dd HH:mm:ss');
                            $scope.activityInfo.regEndTime = $filter('date')($scope.activityInfo.regEndTime, 'yyyy-MM-dd HH:mm:ss');
                            if($scope.clickSubmit==0){
                                ionicToast.show('操作太频繁，不要点击太快哦！', 'bottom', false, 3000);
                            }else {
                                $scope.clickSubmit = 0;
                                //保存活动信息
                                doService.saveActivityData({modelJson: $scope.activityInfo}).then(
                                    function (suc) {
                                        console.log(suc);
                                        if (suc.success) {
                                            $state.go("activityList", {shopId: shopId});
                                            $scope.clickSubmit = 1;
                                        } else {
                                            ionicToast.show(suc.msg, 'bottom', false, 2000);
                                            $scope.clickSubmit = 1;
                                        }
                                    }, function (err) {
                                        console.log(err);
                                    })
                            }
                        }, function (err) {
                            $scope.clickSubmit = 1;
                            console.log(err);
                        });
                }
            }
        }])

    //活动编辑
    .controller('editActicityCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','ionicToast','$filter','$ionicSlideBoxDelegate',
        function($scope,$state,doService,$window,$rootScope,$stateParams,ionicToast,$filter,$ionicSlideBoxDelegate) {

            var shopId=$stateParams.shopId;
            var activityId=$stateParams.activityId;
            $scope.shopId=shopId;
            $scope.activityId=activityId;

            var mymobile =/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
            var zhengzhengshu =/^[0-9]*[1-9][0-9]*$/;

            //活动信息
            $scope.activityInfo={};
            $scope.activityInfonew={};
            $scope.activitytypes={};
            $scope.activityInfonew.activityPic=[];

            var imageTemp="";
            window.setHeadImg=function(type){
                imageTemp=type;
                $scope.$apply(function(){
                    var imagePic={
                        id:0,
                        picUrl:$rootScope.qiNiu+'/'+imageTemp
                    }
                    $scope.activityInfo.image.push(imagePic);
                });
            };
            $scope.choosePic=function(){
                if(ionic.Platform.isAndroid()){
                    webapp.imageChoose("1");
                }else if(ionic.Platform.isIOS()){
                    if(native)
                        native.send({action:'imageChoose', type: 1},function() {});
                }
            };

            //图片删除
            $scope.closeImage=function(index){
                $scope.activityInfo.image.splice(index,1);
            }

            //根据ID获取活动详情
            doService.getActivityDetailData({id:activityId}).then(
                function(suc){
                    $scope.activityInfo=suc;
                    $scope.activityInfo.beginTime=new Date($scope.activityInfo.beginTime);
                    $scope.activityInfo.endTime=new Date($scope.activityInfo.endTime);
                    $scope.activityInfo.regStartTime=new Date($scope.activityInfo.regStartTime);
                    $scope.activityInfo.regEndTime=new Date($scope.activityInfo.regEndTime);
                    //console.log($scope.activityInfo);

                    //获取活动分类
                    doService.getActivityTypeListData().then(
                        function(suc){
                            $scope.activitytypes=suc;
                            angular.forEach($scope.activitytypes, function(data){
                                //console.log(data);
                                //console.log( $scope.activityInfo.activityTypeId);
                                if(data.id== $scope.activityInfo.activityTypeId){
                                    data.isActive=true;
                                }else{
                                    data.isActive=false;
                                }
                            });
                        },function(err){
                            console.log(err);
                        });
                    $ionicSlideBoxDelegate.$getByHandle('activeSildeHandler').update();

                },function(err){
                    console.log(err);
                })

            //活动分类选择
            $scope.selectType=function(typeId){
                angular.forEach($scope.activitytypes, function(data){
                    if(data.id==typeId){
                        data.isActive=true;
                    }else{
                        data.isActive=false;
                    }
                });
            }

            //活动保存
            $scope.saveActivity=function(){
                angular.forEach($scope.activitytypes, function(data){
                    if(data.isActive==true){
                        $scope.activityInfo.activityTypeId=data.id;
                    }
                });
                if($scope.activityInfo.title==null||$scope.activityInfo.title==''){
                    ionicToast.show('请填写活动标题！', 'bottom', false, 2000);
                }else if($scope.activityInfo.beginTime==null||$scope.activityInfo.beginTime==''){
                    ionicToast.show('请选择活动开始时间！', 'bottom', false, 2000);
                }else if($scope.activityInfo.endTime==null||$scope.activityInfo.endTime==''){
                    ionicToast.show('请选择活动结束时间！', 'bottom', false, 2000);
                }else if($scope.activityInfo.beginTime>$scope.activityInfo.endTime){
                    ionicToast.show('开始时间要在结束时间之前！', 'bottom', false, 2000);
                }else  if($scope.activityInfo.sponsorTel==null||$scope.activityInfo.sponsorTel==''){
                    ionicToast.show('请填写活动联系人联系方式！', 'bottom', false, 2000);
                }else  if(!mymobile.test($scope.activityInfo.sponsorTel)){
                    ionicToast.show('请填写正确的联系方式！', 'bottom', false, 2000);
                }else  if($scope.activityInfo.activityAddress==null||$scope.activityInfo.activityAddress==''){
                    ionicToast.show('请填写活动地址！', 'bottom', false, 2000);
                }else  if($scope.activityInfo.numberLimit==null||$scope.activityInfo.numberLimit==''){
                    ionicToast.show('请填写活动人数限制！', 'bottom', false, 2000);
                }else  if(!zhengzhengshu.test($scope.activityInfo.numberLimit)){
                    ionicToast.show('请填写正确活动人数限制！', 'bottom', false, 2000);
                }else if($scope.activityInfo.regEndTime==null||$scope.activityInfo.regEndTime==''){
                    ionicToast.show('请选择活动报名截止时间！', 'bottom', false, 2000);
                }else if($scope.activityInfo.beginTime>$scope.activityInfo.regEndTime){
                    ionicToast.show('开始时间要在报名截止时间之前！', 'bottom', false, 2000);
                }else if($scope.activityInfo.activityTypeId==null||$scope.activityInfo.activityTypeId==''){
                    ionicToast.show('请选择活动分类！', 'bottom', false, 2000);
                }else{
                    $scope.activityInfonew.id= $scope.activityId;
                    $scope.activityInfonew.activityTypeId=$scope.activityInfo.activityTypeId;
                    $scope.activityInfonew.subbranchId=$scope.shopId;
                    angular.forEach($scope.activityInfo.image, function (data) {
                        console.log(data);
                        var imageObj={
                            id:0,
                            url:data.picUrl
                        }
                        $scope.activityInfonew.activityPic.push(imageObj);
                    });
                    doService.getShopInfo({shopId:shopId}).then(
                        function(suc){

                            $scope.activityInfonew.title=$scope.activityInfo.title;
                            $scope.activityInfonew.sponsorName=suc.name;
                            $scope.activityInfonew.sponsorTel=$scope.activityInfo.sponsorTel;
                            $scope.activityInfonew.activityAddress=$scope.activityInfo.activityAddress;
                            $scope.activityInfonew.numberLimit=$scope.activityInfo.numberLimit;
                            $scope.activityInfonew.content=$scope.activityInfo.content;
                            $scope.activityInfonew.beginTime= $filter('date')($scope.activityInfo.beginTime,'yyyy-MM-dd HH:mm:ss');
                            $scope.activityInfonew.endTime= $filter('date')($scope.activityInfo.endTime,'yyyy-MM-dd HH:mm:ss');
                            $scope.activityInfonew.regStartTime= $filter('date')($scope.activityInfo.beginTime,'yyyy-MM-dd HH:mm:ss');
                            $scope.activityInfonew.regEndTime= $filter('date')($scope.activityInfo.regEndTime,'yyyy-MM-dd HH:mm:ss');

                            //保存活动信息
                            doService.saveActivityData({modelJson: $scope.activityInfonew}).then(
                                function(suc){
                                    console.log(suc);
                                    if(suc.success){
                                        $rootScope.myGoBack();
                                    }else{
                                        ionicToast.show(suc.msg,'bottom', false, 2000);
                                    }
                                },function(err){
                                    console.log(err);
                                })
                        },function(err){
                            console.log(err);
                        });
                }
            }
        }])
    //活动详情
    .controller('detailActicityCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicSlideBoxDelegate','ModalShare','$ionicScrollDelegate',
        function($scope,$state,doService,$window,$rootScope,$stateParams, $ionicSlideBoxDelegate,ModalShare,$ionicScrollDelegate) {

            //图片放大预览
            $scope.images = [];
            var myPhotoBrowserStandalone ;
            $scope.photoBrowser = photoBrowser;
            function photoBrowser(index){
                photoBrowserStandalone(index, $scope.images)
            }
            function photoBrowserStandalone(index, images){
                var myApp = new Framework7({
                    init: false, //IMPORTANT - just do it, will write about why it needs to false later
                });
                var photoBrowsers = document.getElementsByClassName("photo-browser")
                if (photoBrowsers.length <= 0) {
                    myPhotoBrowserStandalone = myApp.photoBrowser({
                        type: 'standalone',
                        theme: 'light',
                        photos : images,
                        initialSlide: index,
                        onClose: function(){
                            myApp = undefined;
                        }
                    });
                }
                myPhotoBrowserStandalone.open();
            }
            $scope.$on('$ionicView.beforeLeave',function(){
                myPhotoBrowserStandalone.close();
            })

            //分享
            new ModalShare.initModal($scope);

            var activityId=$stateParams.activityId;
            var shopId=$stateParams.shopId;
            $scope.shopId=shopId;
            $scope.activityId=activityId;

            //活动介绍展开收起
            $scope.activityJscStyle=1;
            $scope.activityJscMoreText="展开";
            $scope.activityJscMore=function(){
                if($scope.activityJscStyle==1){
                    $scope.activityJscStyle=0;
                    $scope.activityJscMoreText="收起";
                }else{
                    $scope.activityJscStyle=1;
                    $scope.activityJscMoreText="展开";
                }
                //$ionicScrollDelegate.$getByHandle('detailActivity-content').scrollTop();
            }

            //分享地址
            $scope.shareUrl=doService.getHostUrl()+"/weixin/weixinClient/weixinEventIndex.do?activityId="+activityId+"&storeId="+shopId;
            $scope.shareWeixin=function(){
                $scope.openShareModal($scope.activityInfo.title,$scope.shareUrl,'',$scope.activityInfo.image.length>0?$scope.activityInfo.image[0].picUrl:'');
            }

            $scope.$on("$ionicView.beforeEnter", function(event, data){
                //根据ID获取活动详情
                doService.getActivityDetailData({id:activityId}).then(
                    function(suc){
                        $scope.activityInfo=suc;
                        angular.forEach($scope.activityInfo.image, function(data,index,array){
                            $scope.images[index]=data.picUrl;
                        });
                        $ionicSlideBoxDelegate.$getByHandle('activeSildeHandler').update();
                        console.log($scope.activityInfo);
                    },function(err){
                        console.log(err);
                    })
                $scope.loadMore();
            });

            $scope.myActiveSlide = 0;
            $scope.activityInfo={};

            //分页初始化
            $scope.start=0;
            $scope.limit=6;
            $scope.comments=[];
            $scope.isShow=true;
            $scope.showScroll=false;
            $scope.doRefresh=function(){
                console.log("doRefresh");
                $scope.start=0;
                $scope.showScroll=true;
                doService.geActivityCommentListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        $scope.comments=date;
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                    $scope.$broadcast('scroll.refreshComplete');
                });
            };

            $scope.loadMore = function() {
                if($scope.comments.length>0){
                    $scope.start=$scope.start+ $scope.limit;
                }
                doService.geActivityCommentListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.comments, suc.resultList);
                        $ionicScrollDelegate.$getByHandle('detailActivity-content').resize();
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                    },3000);
                });
            };

        }])
    //活动详情-微信
    .controller('weixinActivityDetailCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicSlideBoxDelegate','ionicToast','$location','$ionicScrollDelegate',
        function($scope,$state,doService,$window,$rootScope,$stateParams, $ionicSlideBoxDelegate,ionicToast,$location,$ionicScrollDelegate) {
            var activityId=$stateParams.activityId;
            var shopId=$stateParams.shopId;
            var userId=$stateParams.userId;
            $scope.shopId=shopId;
            $scope.activityId=activityId;
            $scope.userId=userId;

            var ismobile =/(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;

            $scope.myActiveSlide = 0;
            $scope.activityInfo={};

            //活动介绍展开收起
            $scope.activityJscStyle=1;
            $scope.activityJscMoreText="展开";
            $scope.activityJscMore=function(){
                if($scope.activityJscStyle==1){
                    $scope.activityJscStyle=0;
                    $scope.activityJscMoreText="收起";
                }else{
                    $scope.activityJscStyle=1;
                    $scope.activityJscMoreText="展开";
                }
                //$ionicScrollDelegate.$getByHandle('weixinactivityDeail-content').scrollTop();
            }

            //图片放大预览
            $scope.images = [];
            var myPhotoBrowserStandalone ;
            $scope.photoBrowser = photoBrowser;
            function photoBrowser(index){
                photoBrowserStandalone(index, $scope.images)
            }
            function photoBrowserStandalone(index, images){
                var myApp = new Framework7({
                    init: false, //IMPORTANT - just do it, will write about why it needs to false later
                });
                var photoBrowsers = document.getElementsByClassName("photo-browser")
                if (photoBrowsers.length <= 0) {
                    myPhotoBrowserStandalone = myApp.photoBrowser({
                        type: 'standalone',
                        theme: 'light',
                        photos : images,
                        initialSlide: index,
                        onClose: function(){
                            myApp = undefined;
                        }
                    });
                }
                myPhotoBrowserStandalone.open();
            }
            $scope.$on('$ionicView.beforeLeave',function(){
                myPhotoBrowserStandalone.close();
            })

            $scope.$on("$ionicView.beforeEnter", function(event, data){
                //根据ID获取活动详情
                doService.getActivityDetailData({id:activityId,userId:userId}).then(
                    function(suc){

                        //返回数据
                        $scope.activityInfo=suc;

                        angular.forEach($scope.activityInfo.image, function(data,index,array){
                            $scope.images[index]=data.picUrl;
                        });

                        $ionicSlideBoxDelegate.$getByHandle('activeSildeHandler').update();

                        //地址验证
                        var url='www/index.html#/weixinActivityDetail/'+activityId+'/'+shopId+'/'+userId;
                        doService.weixinVerification({url:url}).then(
                            function(suc){
                                var verificationData=suc.msg;
                                console.log(verificationData);
                                var shareTitle= $scope.activityInfo.title;
                                var shareUrl="activityId="+activityId+"&storeId="+shopId;
                                var shareImage=$scope.activityInfo.image.length>0?$scope.activityInfo.image[0].picUrl:'';
                                doService.weixinShare(verificationData,shareTitle,shareUrl,shareImage);
                            },function(err){
                                console.log(err);
                            })

                    },function(err){
                        console.log(err);
                    })
                $scope.loadMore();
            });

            //分页初始化
            $scope.start=0;
            $scope.limit=6;
            $scope.comments=[];
            $scope.isShow=true;
            $scope.showScroll=false;
            $scope.doRefresh=function(){
                console.log("doRefresh");
                $scope.start=0;
                $scope.showScroll=true;
                doService.geActivityCommentListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        $scope.comments=date;
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                    $scope.$broadcast('scroll.refreshComplete');
                });
            };

            $scope.loadMore = function() {
                if($scope.comments.length>0){
                    $scope.start=$scope.start+ $scope.limit;
                }
                doService.geActivityCommentListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.comments, suc.resultList);
                        $ionicScrollDelegate.$getByHandle('weixinactivityDeail-content').resize();
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                    },3000);
                });
            };

            //活动报名提交
            $scope.clickSignUp=1;
            $scope.activitysignUp={signUptel:''};
            $scope.submitActivitySignUp=function(){
                if(!ismobile.test($scope.activitysignUp.signUptel)){
                    ionicToast.show('请填写正确的联系方式！', 'bottom', false, 2000);
                }else{
                    if($scope.clickSignUp==0){
                        ionicToast.show('操作太频繁，不要点击太快哦！', 'bottom', false, 3000);
                    }else {
                        $scope.clickSignUp = 0;
                        doService.activitySignUp({modelJson: {eventActivityId: activityId,eventActivityUserinfo: userId, tel:$scope.activitysignUp.signUptel}}).then(
                            function (suc) {
                                if (suc.success) {
                                    ionicToast.show('报名成功！', 'bottom', false, 1000);
                                    $scope.activityInfo.participation++;
                                    $scope.activitysignUp.signUptel='';
                                } else {
                                    ionicToast.show(suc.msg, 'bottom', false, 1000);
                                }
                                $scope.clickSignUp = 1;
                            }, function (err) {
                                ionicToast.show('报名失败！', 'bottom', false, 1000);
                                $scope.clickSignUp = 1;
                                console.log(err);
                            })
                    }
                }
            }

            //活动评论
            $scope.clickComment=1;
            $scope.activitycomment={content:''};
            $scope.submitActivityComment=function(){
                if($scope.activitycomment.content==null||$scope.activitycomment.content==''){
                    ionicToast.show('请填写评论内容！', 'bottom', false, 2000);
                }else{
                    if($scope.clickComment==0){
                        ionicToast.show('操作太频繁，不要点击太快哦！', 'bottom', false, 3000);
                    }else {
                        $scope.clickComment = 0;
                        doService.activityComment({modelJson: {eventActivityId: activityId,eventActivityUserinfo: userId, content:$scope.activitycomment.content}}).then(
                            function (suc) {
                                if (suc.success) {
                                    ionicToast.show('评论成功！', 'bottom', false, 1000);
                                    $scope.activitycomment.content='';
                                    $scope.doRefresh();
                                } else {
                                    ionicToast.show(suc.msg, 'bottom', false, 1000);
                                }
                                $scope.clickComment = 1;
                            }, function (err) {
                                ionicToast.show('评论失败！', 'bottom', false, 1000);
                                $scope.clickComment = 1;
                                console.log(err);
                            })
                    }
                }
            }

            //活动点赞
            $scope.clickPoint=1;
            $scope.getParticipation=function(){
                if($scope.clickPoint==0){
                    ionicToast.show('操作太频繁，不要点击太快哦！', 'bottom', false, 3000);
                }else{
                    $scope.clickPoint=0;
                    if($scope.activityInfo.ispoint>0){
                        doService.activityCanclePoint({modelJson:{eventActivityId:activityId,eventActivityUserinfo:userId}}).then(
                            function(suc){
                               if(suc.success){
                                   ionicToast.show('取消点赞成功！', 'bottom', false, 1000);
                                   $scope.activityInfo.ispoint=0;
                                   $scope.activityInfo.pointNum--;
                               }else{
                                   ionicToast.show(suc.msg, 'bottom', false, 1000);
                               }
                                $scope.clickPoint=1;
                            },function(err){
                                ionicToast.show('取消点赞失败！', 'bottom', false, 1000);
                                $scope.clickPoint=1;
                                console.log(err);
                            })
                    }else{
                        doService.activityPoint({modelJson:{eventActivityId:activityId,eventActivityUserinfo:userId}}).then(
                            function(suc){
                                if(suc.success){
                                    ionicToast.show('点赞成功！', 'bottom', false, 1000);
                                    $scope.clickPoint=1;
                                    $scope.activityInfo.ispoint=1;
                                    $scope.activityInfo.pointNum++;
                                }else{
                                    ionicToast.show(suc.msg, 'bottom', false, 1000);
                                }
                            },function(err){
                                ionicToast.show('点赞失败！', 'bottom', false, 1000);
                                $scope.clickPoint=1;
                                console.log(err);
                            })
                    }
                }
            }
        }])
    //获取活动报名名单-微信
    .controller('weixinActivitySignUpCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicScrollDelegate',
        function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicScrollDelegate) {
            var activityId=$stateParams.activityId;
            var shopId=$stateParams.shopId;
            $scope.shopId=shopId;
            $scope.activityId=activityId;

            //地址验证--屏蔽菜单
            var url='www/index.html#/weixinActivitySignUp/'+shopId+'/'+activityId;
            doService.weixinVerification({url:url}).then(
                function(suc){
                    var data=suc.msg;
                    console.log(data);
                    wx.config({
                        debug: false,
                        appId: data.appId,
                        timestamp: data.timestamp,
                        nonceStr:data.nonceStr,
                        signature: data.signature,
                        jsApiList: [
                            "onMenuShareTimeline",
                            "onMenuShareAppMessage"
                        ]
                    });
                    wx.ready(function(){
                        wx.hideOptionMenu();
                    });
                    wx.error(function(res){

                    });
                },function(err){
                    console.log(err);
                })

            $scope.isShow=true;

            //分页初始化
            $scope.start=0;
            $scope.limit=6;
            $scope.showScroll=false;
            $scope.users=[];
            $scope.doRefresh=function(){
                console.log("doRefresh");
                $scope.start=0;
                $scope.showScroll=true;
                doService.geActivitySignUpListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        $scope.users=date;
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                    $scope.$broadcast('scroll.refreshComplete');
                });
            };
            $scope.loadMore = function() {
                if($scope.users.length>0){
                    $scope.start=$scope.start+ $scope.limit;
                }
                doService.geActivitySignUpListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.users, suc.resultList);
                        $ionicScrollDelegate.$getByHandle('weixinActivity-SignUp').resize();
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                    },3000);
                });
            };
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                $scope.loadMore();
            });
        }])
    //获取活动评论列表-微信
    .controller('weixinActivityCommentCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicScrollDelegate',
        function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicScrollDelegate) {
            var activityId=$stateParams.activityId;
            var shopId=$stateParams.shopId;
            $scope.shopId=shopId;
            $scope.activityId=activityId;

            //地址验证--屏蔽菜单
            var url='www/index.html#/weixinActivityComment/'+shopId+'/'+activityId;
            doService.weixinVerification({url:url}).then(
                function(suc){
                    var data=suc.msg;
                    console.log(data);
                    wx.config({
                        debug: false,
                        appId: data.appId,
                        timestamp: data.timestamp,
                        nonceStr:data.nonceStr,
                        signature: data.signature,
                        jsApiList: [
                            "onMenuShareTimeline",
                            "onMenuShareAppMessage"
                        ]
                    });
                    wx.ready(function(){
                        wx.hideOptionMenu();
                    });
                    wx.error(function(res){

                    });
                },function(err){
                    console.log(err);
                })

            $scope.isShow=true;

            //分页初始化
            $scope.start=0;
            $scope.limit=6;
            $scope.showScroll=false;
            $scope.comments=[];
            $scope.doRefresh=function(){
                console.log("doRefresh");
                $scope.start=0;
                $scope.showScroll=true;
                doService.geActivityCommentListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        $scope.comments=date;
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                    $scope.$broadcast('scroll.refreshComplete');
                });
            };
            $scope.loadMore = function() {
                if($scope.comments.length>0){
                    $scope.start=$scope.start+ $scope.limit;
                }
                doService.geActivityCommentListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                            $scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.comments, suc.resultList);
                        $ionicScrollDelegate.$getByHandle('weixinActivity-Comment').resize();
                    },function(err){
                        $scope.isShow=true;
                        $scope.showScroll=false;
                        console.log(err);
                    }).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                    },3000);
                });
            };
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                $scope.loadMore();
            });

        }])

//推广列表
   .controller('shareListCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicModal', 'ModalTabMenu',
    function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicModal, ModalTabMenu) {

        // var shopId='248474721296064512';
        var shopId=$stateParams.shopId;
        $scope.spreadClassifyList=[];
        $scope.spreadList=[];
        $scope.conditionStr={
            name:'',
            updateTime:'',
            readNum:'',
            classifyId:''
        };
        $scope.name="";
        //分页初始化
        $scope.start=0;
        $scope.limit=6;
        $scope.showScroll=true;
        $scope.doRefresh=function(){
            console.log("doRefresh");
            $scope.start=0;
            $scope.showScroll=true;
            doService.getSpreadList({conditionStr:$scope.conditionStr,start:$scope.start,limit:$scope.limit}).then(
                function(suc){
                    console.log(suc);
                    var data=suc;
                    if(data.length<$scope.limit){
                        $scope.showScroll=false;
                    }else{
                        $scope.showScroll=true;
                    }
                    $scope.spreadList=data;
                },function(err){
                    console.log(err);
                }).finally(function () {
                $scope.$broadcast('scroll.refreshComplete');
            });
        };
        $scope.loadMore = function() {
            if($scope.spreadList.length>0){
                $scope.start=$scope.start+ $scope.limit;
            }
            doService.getSpreadList({conditionStr:$scope.conditionStr,start:$scope.start,limit:$scope.limit}).then(
                function(suc){
                    console.log(suc);
                    var date=suc;
                    if(date.length<$scope.limit){
                        $scope.showScroll=false;
                    }else{
                        $scope.showScroll=true;
                    }
                    Array.prototype.push.apply($scope.spreadList, suc);
                },function(err){
                    console.log(err);
                }).finally(function () {
                setTimeout(function(){
                    $scope.$broadcast('scroll.infiniteScrollComplete');
                },3000);
            });
        };
        $scope.$on('$stateChangeSuccess', function() {
            $scope.loadMore();
        });


        $scope.sortByTime=function($event,params){
            $scope.start=0;
            $scope.limit=6;
            $scope.conditionStr.updateTime=params;
            $scope.conditionStr.readNum="";
            $scope.doRefresh();
        }


        $scope.sortByReadNums=function($event,params){
            $scope.conditionStr.readNum=params;
            $scope.conditionStr.updateTime="";
            $scope.start=0;
            $scope.limit=6;
            $scope.doRefresh();
        }

        /**
         * 定义：
         *  TabMenu在$scope 中的变量名称，
         *  打开TabMenu 的方法名称，
         *  关闭TabMenu 的方法名称
         */
        var tabMenuName = "popoverMenu",
            openTabFuncName = "openPopoverMenu",
            closeTabFuncName = "closePopoverMenu";

        var data1=[];
        onselect="select"
        doService.getSpreadClassify({}).then(
            function(suc){
                console.log(suc);
                data1.push(
                    {
                        id:0,
                        name:"全部分类"
                    }
                )
                for(var i=0;i<suc.length;i++){
                    data1.push({
                        id:suc[i].id,
                        name:suc[i].name
                    });
                }
                tabMenu.setSelectItem(0);
                //$scope.tabMenuData[0].name = tabMenu.selected().name;
                //$scope.conditionStr.classifyId=tabMenu.selected().id;
                console.log("data1-----"+data1);
            },function(err){
                console.log(err);
            });
        var select = function(item){
            console.log(item.id);
            $scope.conditionStr.name="",
            $scope.conditionStr.classifyId=item.id;
            $scope.doRefresh();
        }

        /**
         * 初始化，为了能够同时存在多个TabMenu, 需要 new
         */
        var tabMenu = new ModalTabMenu.initModal($scope, {
            name: tabMenuName,
            open: openTabFuncName,
            close: closeTabFuncName,
            data:data1
            //data: [{
            //    id: 0,
            //    name: '全部分类',
            //    children: [
            //        { id: -1, name: '全部' }
            //    ]
            //}, { id: 1, name: '学习类型',
            //    children: [
            //        { id: -1, name: '全部' }
            //    ]
            //}, { id: 2, name: '知识类型',
            //    children: [
            //        { id: -1, name: '全部' },
            //        { id: 21, name: '案例分析' },
            //        { id: 22, name: '激励机制' },
            //        { id: 23, name: '时政法政' }
            //    ]
            //}, { id: 3, name: '咨询类型',
            //    children: [
            //        { id: -1, name: '全部' }
            //    ]
            //}]
        });

        //var selectMenuName = "selectMenu",
        //    openSelectFuncName = "openSelectMenu",
        //    closeSelectFuncName = "closeSelectMenu";
        //
        //var selectMenu = new ModalTabMenu.initModal($scope, {
        //    name: selectMenuName,
        //    open: openSelectFuncName,
        //    close: closeSelectFuncName,
        //    data: [
        //        { id: 0, name: '全部' },
        //        { id: 1, name: '文章' },
        //        { id: 2, name: '视频' },
        //        { id: 3, name: 'A' },
        //        { id: 4, name: 'B' },
        //        { id: 5, name: 'C' },
        //        { id: 6, name: 'D' },
        //        { id: 7, name: 'E' },
        //        { id: 8, name: 'F' },
        //        { id: 9, name: 'G' }
        //    ]
        //});
        //
        //
        //var timeClickCallback = function($event, params){
        //    console.log(params.data);
        //    console.log($scope.tabMenuData);
        //}

        /**
         * TabMenu 的初始化信息
         * 用法： <ym-tab-menu config='tabMenuData'></ym-tab-menu>
         */
        $scope.tabMenuData = [{ name: '', scope: $scope, tabMenu: tabMenuName,onselect:select,
            toggles: [
                {style: {color: "#000"}, icon: './img/yxtg_qbfl_md.png', callback: $scope[closeTabFuncName]},
                {style: {color: "#f55"}, icon: './img/yxtg_qbfl_dl.png', callback: $scope[openTabFuncName]}
            ]
        }, { name: '时间',
            toggles: [
                {icon: './img/yxtg_jx.png', callback: $scope.sortByTime, params:"0"},
                {icon: './img/yxtg_sx.png', callback: $scope.sortByTime, params:"1"}

            ]
        }, { name: '阅读量',
            toggles: [
                {icon: './img/yxtg_jx.png',callback: $scope.sortByReadNums, params:"0"},
                {icon: './img/yxtg_sx.png',callback: $scope.sortByReadNums, params:"1"}


            ]
        }];


        $scope.goSpreadDetail=function(i){
            $state.go('spreadDetail',{spreadId: $scope.spreadList[i].id,shopId:shopId,url:$scope.spreadList[i].url});
        };

        var search=function(value){
            $scope.start=0;
            $scope.limit=6;
            $scope.conditionStr.name=value;
            $scope.doRefresh();
        }

        var closeSearch=function(value){
            $scope.start=0;
            $scope.limit=6;
            $scope.conditionStr.name=value;
            $scope.doRefresh();
        }

        $scope.searchData = {
            icon: "img/search.png",
            iconOpen: "img/dpgl_ssl_ss.png",
            value: $scope.conditionStr.name,
            placeholder: "根据关键字搜索",
            autoClear: true,
            callback: search,
            onclose:closeSearch
        }

    }])
    //推廣詳情
    .controller('spreadDetailCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','ModalShare',
        function($scope,$state,doService,$window,$rootScope,$stateParams,ModalShare) {
            var spreadId=$stateParams.spreadId;
            var shopId = $stateParams.shopId;
            var url=$stateParams.url;
            console.log(url);
            $scope.spread={ };
            $scope.qrcodeInfo="";
            doService.getSpreadDetail({id:spreadId}).then(
                function(suc){
                    $scope.spread=suc;
                    console.log(suc);
                    if(suc.goodId==null||suc.goodId==""||suc.goodId==undefined){
                        //店铺
                        $scope.qrcodeInfo=doService.getHostUrl()+"/www/index.html#/shareDetail/"+spreadId+"/"+shopId+"/";
                        console.log($scope.qrcodeInfo);
                    }else{
                        //商品
                        $scope.qrcodeInfo=doService.getHostUrl()+"/www/index.html#/shareDetail/"+spreadId+"/"+shopId+"/"+suc.goodId;
                        console.log($scope.qrcodeInfo);
                    }

                    console.log($scope.qrcodeInfo);
                    console.log("---spread"+$scope.spread.name);
                    $scope.shopTitle = $scope.spread.name;
                },function(err){
                    console.log(err);
                });
            ModalShare.initModal($scope);
            $scope.openShare=function(){
                console.log($scope.shopTitle);
                console.log($scope.qrcodeInfo);
                console.log(url);
                $scope.openShareModal($scope.shopTitle,$scope.qrcodeInfo,"",url);
            }

        }])
    //推廣的分享页面
    .controller('shareDetailCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','ModalShare',
        function($scope,$state,doService,$window,$rootScope,$stateParams,ModalShare) {
            var spreadId=$stateParams.spreadId;
            var shopId = $stateParams.shopId;
            var goodId=$stateParams.goodId;
            $scope.spread={
            };
            $scope.shopInfo={ };
            $scope.qrcodeInfo={ };

            if(goodId==null||goodId==""||goodId==undefined){
                //推广店铺
                $scope.qrcodeInfo=doService.getHostUrl()+"/weixin/weixinClient/index.do?" +
                    "fromshanguo=weixinfront/html/customer/index.html&storeId="+shopId+"&type=weixinIndex";
                console.log($scope.qrcodeInfo);
            }else{
                //推广商品
                $scope.qrcodeInfo=doService.getHostUrl()+"/weixin/weixinClient/index.do?" +
                    "fromshanguo=weixinfront/html/customer/details/details.html?id="+goodId+"&storeId="+shopId+"&type=weixinIndex";
                console.log($scope.qrcodeInfo);
            }

            $scope.$on("$ionicView.beforeEnter", function(event, data){
                doService.getSpreadDetail({id:spreadId}).then(
                    function(suc){
                        $scope.spread=suc;

                        console.log(goodId);
                    },function(err){
                        console.log(err);
                    });

                doService.getShareShopInfo({id:shopId}).then(
                    function(suc){
                        $scope.shopInfo=suc;
                        console.log(suc);
                    },function(err){
                        console.log(err);
                    });
            });

        }])
//学习乐园列表
    .controller('studyListCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicModal','ModalTabMenu',
        function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicModal,ModalTabMenu) {
            //        // var shopId='248474721296064512';
            var shopId = $stateParams.shopId;
            //$scope.spreadClassifyList=[];
            $scope.studyList = [];
            $scope.conditionStr = {
                "conditions": "%%",
                "studyType": "0",
                "studyChildType": "0",
                "createTimeOrder": "desc",
                "readNumOrder": null,
                "type": "0"
            };
            $scope.temp={
                name:''
            };

            //分页初始化
            $scope.start=0;
            $scope.limit=6;
            $scope.isShow=false;

            $scope.doRefresh=function(){
                console.log("doRefresh");
                $scope.start=0;
                $scope.showScroll=true;
                doService.getStudyList({conditionStr: $scope.conditionStr,start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        $scope.studyList=date;
                    },function(err){
                        console.log(err);
                    }).finally(function () {
                    $scope.$broadcast('scroll.refreshComplete');
                });
            };
            $scope.loadMore = function() {
                if($scope.studyList.length>0){
                    $scope.start=$scope.start+ $scope.limit;
                }
                doService.getStudyList({conditionStr: $scope.conditionStr,start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.studyList, suc.resultList);
                    },function(err){
                        console.log(err);
                    }).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                    },3000);
                });
            };
            $scope.$on('$stateChangeSuccess', function() {
                $scope.loadMore();
            });


            $scope.sortByTime=function($event,params){
                $scope.start=0;
                $scope.limit=6;
                $scope.conditionStr.createTimeOrder=params;
                $scope.conditionStr.readNumOrder=null;
                $scope.doRefresh();
            };


            $scope.sortByReadNums=function($event,params){
                $scope.conditionStr.createTimeOrder=null;
                $scope.conditionStr.readNumOrder=params;
                $scope.start=0;
                $scope.limit=6;
                $scope.doRefresh();
            };
            $scope.search=function(){
                $scope.closeModal();
                $scope.conditionStr.conditions="%"+$scope.temp.name+"%";
                $scope.start=0;
                $scope.limit=6;
                $scope.doRefresh();
            };

            /**
             * 定义：
             *  TabMenu在$scope 中的变量名称，
             *  打开TabMenu 的方法名称，
             *  关闭TabMenu 的方法名称
             */
            var tabMenuName = "popoverMenu",
                openTabFuncName = "openPopoverMenu",
                closeTabFuncName = "closePopoverMenu";
            var datac1=[]
            var data1=[];
            //获取一级分类
            doService.getStudyFirstClassify({}).then(
                function(suc){
                    console.log(suc);
                    datac1=suc;
                    data1.push(
                        {
                            id:0,
                            name:"全部分类",
                            children:[{
                                id: -1,
                                name: "全部"
                            }]
                        }
                    )
                    for(var i=0;i<suc.length;i++){
                        data1.push({
                            id:suc[i].id,
                            name:suc[i].name,
                            children:[]
                        });
                    }
                    //$scope.tabMenuData[0].name = tabMenu.selected().name;
                    //$scope.conditionStr.classifyId=tabMenu.selected().id;
                    console.log("data1-----"+data1);



                    //根据一级分类获取2级分类
                    for(var k=1;k<data1.length;k++){
                        getClassify(k);
                    }
                    tabMenu.setSelectItem(0);

                },function(err){
                    console.log(err);
                });

            var getClassify = function(k){
                doService.getStudySecondClassify({parentId:data1[k].id}).then(
                    function(suc){
                        console.log(suc);
                        data1[k].children.push(
                            {
                                id:0,
                                name:"全部分类"
                            }
                        )
                        for(var j=0;j<suc.length;j++){
                            data1[k].children.push({
                                id:suc[j].id,
                                name:suc[j].name,
                            });
                        }
                        //tabMenu.setSelectItem(0);
                        //$scope.tabMenuData[0].name = tabMenu.selected().name;
                        //$scope.conditionStr.classifyId=tabMenu.selected().id;
                        console.log("data1-----"+data1);
                    },function(err){
                        console.log(err);
                    });
            }

            var select = function(item){
                console.log(item.id);
                $scope.conditionStr.conditions="%%",
                $scope.conditionStr.studyChildType=tabMenu.selected().id;
                if(tabMenu.selected().parent !=null && tabMenu.selected().parent.id!=0){
                    $scope.conditionStr.studyType=tabMenu.selected().parent.id;
                }
                $scope.doRefresh();
            }

            var selectType = function(item){
                console.log(item.id);
                $scope.conditionStr.conditions="%%";
                $scope.conditionStr.type=item.id;
                $scope.doRefresh();
            }
            /**
             * 初始化，为了能够同时存在多个TabMenu, 需要 new
             */
            var tabMenu = new ModalTabMenu.initModal($scope, {
                name: tabMenuName,
                open: openTabFuncName,
                close: closeTabFuncName,
                deep: 2,
                data:data1
            });

            var selectMenuName = "selectMenu",
                openSelectFuncName = "openSelectMenu",
                closeSelectFuncName = "closeSelectMenu";
                onselect="selectType";
            var selectMenu = new ModalTabMenu.initModal($scope, {
                name: selectMenuName,
                open: openSelectFuncName,
                close: closeSelectFuncName,
                data: [
                    { id: 0, name: '全部' },
                    { id: 1, name: '文章' },
                    { id: 2, name: '视频' }
                ]
            });

            //
            //var timeClickCallback = function($event, params){
            //    console.log(params.data);
            //    console.log($scope.tabMenuData);
            //}

            /**
             * TabMenu 的初始化信息
             * 用法： <ym-tab-menu config='tabMenuData'></ym-tab-menu>
             */
            $scope.tabMenuData = [{ name: '全部分类', scope: $scope, tabMenu: tabMenuName, onselect:select,
                toggles: [
                    {style: {color: "#000"}, icon: './img/yxtg_qbfl_md.png', callback: $scope[closeTabFuncName]},
                    {style: {color: "#f55"}, icon: './img/yxtg_qbfl_dl.png', callback: $scope[openTabFuncName]}
                ]
            }, { name: '时间',
                toggles: [
                    {icon: './img/yxtg_jx.png', callback: $scope.sortByTime, params:"desc"},
                    {icon: './img/yxtg_sx.png', callback: $scope.sortByTime, params:"asc"}


                ]
            }, { name: '阅读量',
                toggles: [
                    {icon: './img/yxtg_jx.png',callback: $scope.sortByReadNums, params:"desc"},
                    {icon: './img/yxtg_sx.png',callback: $scope.sortByReadNums, params:"asc"}


                ]
                }, { name: '类型', scope: $scope, tabMenu: selectMenuName, onselect:selectType,
                    toggles: [
                        {style: {color: "#000"},icon: './img/yxtg_qbfl_md.png', callback: $scope[closeSelectFuncName]},
                        {style: {color: "#f55"},icon: './img/yxtg_qbfl_dl.png', callback: $scope[openSelectFuncName]}
                    ]
            }];



            $scope.goStudyDetail=function(i){
                $state.go('studyDetail',{studyId: $scope.studyList[i].id,shopId:shopId});
            }
            var search=function(value){
                $scope.conditionStr.conditions="%"+value+"%";
                $scope.start=0;
                $scope.limit=6;
                $scope.doRefresh();
            };
            var closeSearch=function(value){
                $scope.start=0;
                $scope.limit=6;
                $scope.conditionStr.conditions="%"+value+"%";
                $scope.doRefresh();
            }
            $scope.searchData = {
                icon: "img/search.png",
                iconOpen: "img/dpgl_ssl_ss.png",
                value: $scope.temp.name,
                placeholder:"根据关键字搜索",
                autoClear: true,
                callback: search,
                onclose:closeSearch
            }

        }])
    //学习乐园详情
    .controller('studyDetailCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','ModalShare', "$sce",
        function($scope,$state,doService,$window,$rootScope,$stateParams,ModalShare, $sce) {
            var studyId=$stateParams.studyId;
            $scope.ifVideoShow=false;
            doService.getStudyDetail({id:studyId}).then(
                function(suc){
                    if(suc.success=true){
                        $scope.study=suc.msg;

                        $scope.study.videoUrl = $sce.trustAsResourceUrl($scope.study.videoUrl);
                        if(suc.msg.type==2){
                            $scope.ifVideoShow=true;
                        }

                        var filepath=$scope.study.file;
                        var fileName=filepath.substring(filepath.lastIndexOf("/")+1);
                        ////判断文件是否下载完成
                        isDownFile(fileName);

                        console.log(suc.msg);
                    }else{
                        console.log(suc.msg);
                    }

                },function(err){
                    console.log(err);
                });

            $scope.fileClickContent='点击可下载文件';
            $scope.filePath="";
            $scope.fileStatus=1;
            //判断文件是否存在
            var isDownFile=function(fileName){
                if(ionic.Platform.isAndroid()){
                    webapp.isDownFile(fileName);
                }else if(ionic.Platform.isIOS()){
                    if(native)
                        native.send({action:'isDownFile', fileName:fileName},function() {});
                }
            };
            //下载文件
            $scope.fileClick=function(){
                //文件打开
                if($scope.fileStatus==2){
                    if(ionic.Platform.isAndroid()){
                        webapp.readFile($scope.filePath);
                    }else if(ionic.Platform.isIOS()){
                        if(native)
                            native.send({action:'readFile', filePath:$scope.filePath},function() {});
                    }
                }else if($scope.fileStatus==1||$scope.fileStatus==3) {//文件下载
                    if(ionic.Platform.isAndroid()){
                        webapp.downFile($scope.study.file);
                    }else if(ionic.Platform.isIOS()){
                        if(native)
                            native.send({action:'downFile', fileUrl:$scope.study.file},function() {});
                    }
                    $scope.fileClickContent='文件下载中';
                    $scope.fileStatus=4;
                }
            };
            //设置文件地址
            window.setFilePath=function(filePath,fileStatus){
                $scope.fileStatus=fileStatus;
                $scope.filePath=filePath;
                $scope.$apply(function(){
                    if($scope.fileStatus==1){
                        $scope.fileClickContent='点击可下载文件';
                    }else if($scope.fileStatus==2){
                        $scope.fileClickContent='下载完成,点击打开本文';
                    }else if($scope.fileStatus==3){
                        $scope.fileClickContent='下载失败,点击重新下载';
                    }
                });
            };
        }])
    //粉丝管理
    .controller('fansListCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicScrollDelegate',
    function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicScrollDelegate) {
        var shopId=$stateParams.shopId;
        $scope.pageNum=1;
        $scope.pageSize=9;
        $scope.showScroll=false;
        $scope.fans=[];
        $scope.doRefresh=function(){
        	$scope.pageNum=1;
        	$scope.showScroll=false;
        	doService.getFansList({shopId:shopId,pageNum:$scope.pageNum,pageSize:$scope.pageSize}).then(
        			function(suc){
        				if(suc==null||suc.length==0||suc==''){
        					$scope.vouchDataF=true;
        				}else{
        					$scope.fans=suc;
        					$scope.vouchDataF=false;
        				}
                        if(suc==null||suc.length<$scope.pageSize){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
        				console.log(suc);
        			},function(err){
        				$scope.vouchDataF=true;
        				console.log(err);
        				$scope.showScroll=false;
        			}).finally(function () {
                        $scope.$broadcast('scroll.refreshComplete');
                    });
        }
        $scope.loadMore = function() {
            if($scope.fans.length>0){
                $scope.pageNum=$scope.pageNum+1;
            }
            doService.getFansList({shopId:shopId,pageNum:$scope.pageNum,pageSize:$scope.pageSize}).then(
        			function(suc){
                        if(suc.length<$scope.pageSize){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.fans, suc);
                        if($scope.fans==null||$scope.fans.length==0||$scope.fans==''){
                        	$scope.vouchDataF=true;
                        }else{
                        	$scope.vouchDataF=false;
                        }
                        $ionicScrollDelegate.$getByHandle('fans-content-con').resize();
        				console.log(suc);
        			},function(err){
        				$scope.vouchDataF=true;
        				console.log(err);
        				$scope.showScroll=false;
        			}).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                    },3000);
                });
        }
        $scope.$on('$ionicView.beforeEnter', function() {
            $scope.loadMore();
        });
    }])
    //店铺二维码
    .controller('shopQrcodeCtrl', ['$scope', '$state', 'doService', '$window', '$rootScope', '$stateParams','ModalShare',
        function ($scope, $state, doService, $window, $rootScope, $stateParams,ModalShare) {
            var shopId = $stateParams.shopId;
            doService.getShareShopInfo({id:shopId}).then(
                function(suc){
                    $scope.shopInfo=suc;
                    console.log(suc);
                },function(err){
                    console.log(err);
                });
            ModalShare.initModal($scope);
            $scope.qrcodeInfo=doService.getHostUrl()+"/weixin/weixinClient/index.do?" +
                "fromshanguo=weixinfront/html/customer/index.html&storeId="+shopId+"&type=weixinIndex";
            $scope.shopContent = '这么多好茶汇聚的店铺，不分享给你看看，臣妾做不到啊！';
        }])
    //店铺管理-分类2016.6.29修改
    .controller('shopGoodClassifyCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicSlideBoxDelegate','$ionicScrollDelegate','$interval',
        function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicSlideBoxDelegate,$ionicScrollDelegate,$interval) {
            var shopId=$stateParams.shopId;
            $scope.shopClassifySlide = 1;
            var imgWidth=0;
            var maxWidth=0;
            var sucnum = 0;
            $interval(function () {
                var brandList=$scope.brandList;
                if(brandList.length>4){
                    var first=brandList.shift();
                    brandList.push(first);
                }
            }, 3000);
            doService.getLunbo({}).then(
                function(suc){
                    $scope.lunboList=suc;
                    $ionicSlideBoxDelegate.update();
                    $ionicSlideBoxDelegate.$getByHandle('shopClassifySlider').loop(true);
                    console.log(suc);
                },function(err){
                    console.log(err);
                });
            doService.getClassifyColumnList({}).then(
                function(suc){
                    $scope.classifyColumnList=suc;
                    console.log(suc);
                },function(err){
                    console.log(err);
                });
            doService.getBrandList({}).then(
                function(suc){
                    $scope.brandList=suc;
                    console.log(suc);
                    console.log(suc.length);
                    var screenWidth= $window.screen.width;
                    imgWidth=screenWidth/4;
                    sucnum = suc.length;
                    maxWidth =imgWidth*sucnum;
                    $scope.imgStyle={
                        "width":""+imgWidth+"px"
                    }
                    $scope.imgScroll={
                        "width":+imgWidth*sucnum+"px"
                    };
                },function(err){
                    console.log(err);
                });
            $scope.shopGoodList =function(brandIndex,classifyIndex){
                var brandId = '';
                var brandName = '';
                var classifyId = '';
                var classifyName = '';
                if(brandIndex != undefined){
                    brandId = $scope.brandList[brandIndex].id;
                    brandName = $scope.brandList[brandIndex].name;
                }if(classifyIndex != undefined){
                    classifyId = $scope.classifyColumnList[classifyIndex].classifyId;
                    classifyName = $scope.classifyColumnList[classifyIndex].classifyName;
                }
                $state.go("shopGoodList",{shopId:shopId,brandId:brandId,brandName:brandName,classifyId:classifyId,classifyName:classifyName});
            };
            $scope.goShopQrCode=function(){
                $state.go('shopQrcode',{shopId: shopId})
            };
        }])
    //店铺商品列表
    .controller('shopGoodListCtrl', ['$scope', '$state', 'doService', '$window', '$rootScope', '$stateParams','$ionicScrollDelegate','$ionicSlideBoxDelegate','$interval','$ionicLoading',
        function ($scope, $state, doService, $window, $rootScope, $stateParams,$ionicScrollDelegate,$ionicSlideBoxDelegate,$interval,$ionicLoading) {
            var shopId = $stateParams.shopId;
            var classifyId = $stateParams.classifyId;
            var classifyName = $stateParams.classifyName;
            var brandId = $stateParams.brandId;
            var brandName = $stateParams.brandName;
            $scope.goodListTitle = "全部商品";
            if(classifyName){
                $scope.goodListTitle = classifyName;
            }if(brandName){
                $scope.goodListTitle = brandName;
            }
            var pageNum = 1;
            var pageSize = 6;
            $scope.sortType = 0;
            $scope.showScroll = false;
            $scope.isInLoad = true;
            $scope.goodName = '';
            $scope.listFlag = true;
            $scope.firstFlag = true;
            var timer ;
            $scope.goTopPos=function(){
                $ionicScrollDelegate.$getByHandle('goodListScroll').scrollTop(true);
            };
            $scope.goShopGoodManage=function(){
                $state.go('shopGoodManage',{shopId: shopId,sortType:$scope.sortType,brandId:brandId,classifyId:classifyId,goodName:$scope.searchData.value})
            };
            $scope.goShopGoodInfo=function(goodId){
                $state.go('shopGoodInfo',{shopId:shopId,goodId: goodId})
            };
            $scope.searchData = {
                //text: "搜索",
                icon: "img/search.png",
                iconOpen: "img/dpgl_ssl_ss.png",
                value: $scope.goodName,
                placeholder: '输入出售中商品名称',
                autoClear: true,
                callback: function(value){
                    $scope.goodName = value;
                    $scope.doRefresh();
                },onclose: function(value){
                    $scope.goodName = '';
                    $scope.doRefresh();
                }
            }
            var getParam = function(){
                var param = {};
                param["shopId"]=shopId;
                param["classifyId"]=classifyId;
                param["brandId"]=brandId;
                param["pageSize"]=pageSize;
                param["pageNum"]=pageNum;
                param["sortType"]=$scope.sortType;
                param["goodName"]=$scope.goodName;
                return param;
            }
            //上拉或刷新开始时参数设置 flag true:刷新 false:上拉
            var setStartProperty = function(flag){
                if(flag){
                    if($scope.firstFlag){
                        $ionicLoading.show({
                            content: 'Loading',
                            animation: 'fade-in',
                            showBackdrop: true,
                            maxWidth: 200,
                            showDelay: 0
                        });
                    }
                    $scope.listFlag = true;
                    $scope.goTopPos();
                    pageNum = 1;
                    $scope.showScroll = false;
                }else{
                    pageNum++;
                    $scope.showScroll = true;
                    $scope.isInLoad=false;
                }
            }
            //上拉或刷新结束时参数设置 flag true:刷新 false:上拉
            var setEndProperty = function(flag,data){
                if(flag && (data == undefined || data.length == 0)){
                    $scope.listFlag = false;
                }
                if(flag){
                    $scope.goodList=data;
                }else{
                    Array.prototype.push.apply($scope.goodList, data);
                }
                if(data && data.length == pageSize){
                    if(flag){
                        $scope.isInLoad = true;
                    }
                    $scope.showScroll = true;
                }else{
                    $scope.showScroll = false;
                }
                if($scope.firstFlag){
                    $ionicLoading.hide();
                    $scope.firstFlag = false;
                }
                $ionicScrollDelegate.$getByHandle('goodListScroll').resize();
            }

            $scope.doRefresh = function(){
                setStartProperty(true);
                setTimeout(function(){
                    doService.getShopGoodList(getParam()).then(
                        function(suc){
                            setEndProperty(true,suc)
                            console.log(suc);
                        },function(err){
                            console.log(err);
                        }
                    ).finally(function () {
                        $scope.$broadcast('scroll.refreshComplete');
                    });
                },50);
            }
            $scope.loadMore = function(){
                setStartProperty(false);
                doService.getShopGoodList(getParam()).then(
                    function(suc){
                        setEndProperty(false,suc);
                        console.log(suc);
                    },function(err){
                        console.log(err);
                    }
                ).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                        $scope.isInLoad=true;
                    },3000);
                });
            }
            $scope.sortSell = function(){
                $scope.sortType = 0;
                $scope.searchData.placeholder='输入出售中商品名称';
                $scope.firstFlag = true;
                $scope.doRefresh();
            }
            $scope.sortSoldOut = function(){
                $scope.sortType = 1;
                $scope.searchData.placeholder='输入已下架商品名称';
                $scope.firstFlag = true;
                $scope.doRefresh();
            }
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                $ionicSlideBoxDelegate.$getByHandle('fistPicSlider').start();
                $scope.topDistance=false;
                timer = $interval(function(){
                    var distance=$ionicScrollDelegate.$getByHandle('goodListScroll').getScrollPosition();
                    if(distance.top>420){
                        $scope.topDistance=true;
                    }else{
                        $scope.topDistance=false;
                    }
                },100);
                $scope.doRefresh();
            });
            $scope.$on("$ionicView.beforeLeave", function(event, data){
                $interval.cancel(timer);
            });
            $scope.changestatus=1;
            $scope.changelisticon="zk";
            $scope.changelistactive="";
            $scope.shopgoodchange=function(){
                if($scope.changestatus==1){
                    $scope.changestatus=0;
                    $scope.changelisticon="lb";
                    $scope.changelistactive="changelistactive";
                }else{
                    $scope.changestatus=1;
                    $scope.changelisticon="zk";
                    $scope.changelistactive=""
                }
            };
        }])
    //批量管理
    .controller('shopGoodManageCtrl', ['$scope', '$state', 'doService', '$window', '$rootScope', '$stateParams','$ionicScrollDelegate','$ionicSlideBoxDelegate','$interval','PopupCustom','$ionicLoading',
        function ($scope, $state, doService, $window, $rootScope, $stateParams,$ionicScrollDelegate,$ionicSlideBoxDelegate,$interval,PopupCustom,$ionicLoading) {
            var shopId = $stateParams.shopId;
            var pageNum = 1;
            var pageSize = 6;
            $scope.sortType = $stateParams.sortType;
            $scope.operate = $scope.sortType == 0?"下架":"上架";
            var brandId = $stateParams.brandId;
            var classifyId = $stateParams.classifyId;
            var goodName = $stateParams.goodName;
            $scope.showScroll = false;
            $scope.isInLoad = true;
            $scope.isSelect=false;
            $scope.goodNum = 0;
            $scope.listFlag = true;
            $scope.firstFlag = true;
            $scope.goTopPos=function(){
                $ionicScrollDelegate.$getByHandle('goodManageScroll').scrollTop(true);
            };
            $scope.goShopQrCode=function(){
                $state.go('shopQrcode',{shopId: shopId})
            };

            var getParam = function(){
                var param = {};
                param["shopId"] = shopId;
                param["brandId"]=brandId;
                param["classifyId"]=classifyId;
                param["goodName"]=goodName;
                param["sortType"]=$scope.sortType;
                param["pageSize"] = pageSize;
                param["pageNum"] = pageNum;
                return param;
            }
            //上拉或刷新开始时参数设置 flag true:刷新 false:上拉
            var setStartProperty = function(flag){
                if(flag){
                    if($scope.firstFlag){
                        $ionicLoading.show({
                            content: 'Loading',
                            animation: 'fade-in',
                            showBackdrop: true,
                            maxWidth: 200,
                            showDelay: 0
                        });
                    }
                    $scope.listFlag = true;
                    $scope.goTopPos();
                    pageNum = 1;
                    $scope.showScroll = false;
                }else{
                    pageNum++;
                    $scope.showScroll = true;
                    $scope.isInLoad=false;
                }
            }
            //上拉或刷新结束时参数设置 flag true:刷新 false:上拉
            var setEndProperty = function(flag,data){
                if(flag && (data == undefined || data.length == 0)){
                    $scope.listFlag = false;
                }
                if(data && data.length == pageSize){
                    if(flag){
                        $scope.isInLoad = true;
                    }
                    $scope.showScroll = true;
                }else{
                    $scope.showScroll = false;
                }
                if(flag){
                    $scope.goodList=data;
                }else{
                    Array.prototype.push.apply($scope.goodList, data);
                }
                if($scope.firstFlag){
                    $ionicLoading.hide();
                    $scope.firstFlag = false;
                }
                $ionicScrollDelegate.$getByHandle('goodManageScroll').resize();
            }

            $scope.doRefresh = function(){
                setStartProperty(true);
                doService.getShopGoodList(getParam()).then(
                    function(suc){
                        setEndProperty(true,suc)
                        console.log(suc);
                    },function(err){
                        console.log(err);
                    }
                ).finally(function () {
                    $scope.$broadcast('scroll.refreshComplete');
                });
            }
            $scope.loadMore = function(){
                setStartProperty(false);
                doService.getShopGoodList(getParam()).then(
                    function(suc){
                        setEndProperty(false,suc)
                        console.log(suc);
                    },function(err){
                        console.log(err);
                    }
                ).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                        $scope.isInLoad=true;
                    },3000);
                });
            }
            $scope.checkAll = function() {
                for(var i=0;i<$scope.goodList.length;i++){
                    $scope.goodList[i].isSelect=$scope.isSelect;
                }
                if($scope.isSelect){
                    $scope.goodNum = $scope.goodList.length;
                }else{
                    $scope.goodNum = 0;
                }
            }
            $scope.$watch('goodList', function(newValue, oldValue) {
                var goodNum=0;
                angular.forEach(newValue, function(item, key) {
                    if(item.isSelect){
                        goodNum=goodNum+1;
                    }
                });
                $scope.goodNum=goodNum;
                if($scope.goodList && $scope.goodNum != 0 && $scope.goodNum == $scope.goodList.length){
                    $scope.isSelect = true;
                }else{
                    $scope.isSelect = false;
                }
            }, true);
            $scope.operateGood = function(){
                var param = {};
                param["shopId"]= shopId;
                var goodIds = "";
                for(var i = 0; i < $scope.goodList.length; i++){
                    if($scope.goodList[i].isSelect){
                        goodIds += $scope.goodList[i].id+",";
                    }
                }
                if(goodIds.length == 0){
                    return PopupCustom.toastPopup({
                        template: '<div class="alert-text">请勾选商品再'+($scope.sortType == 0?'下架':'上架')+'！</div>',
                        subTitle: '<div class="alert-img"><img src="img/resp-error.png"></div>'
                    },1500);
                }
                param["goodIds"]=goodIds;
                doService.operateGood($scope.sortType,param).then(
                    function(result){
                        if(result.code == 0){
                            if($scope.sortType == 0){
                                PopupCustom.toastPopup({
                                    template: '<div class="alert-text">下架成功！</div>',
                                    subTitle: '<div class="alert-img"><img src="img/resp-success.png"></div>'
                                },1500);
                            }else{
                                PopupCustom.toastPopup({
                                    template: '<div class="alert-text">上架成功！</div>',
                                    subTitle: '<div class="alert-img"><img src="img/resp-success.png"></div>'
                                },1500);
                            }
                            var newArray = [],j=0;
                            for(var i = 0; i < $scope.goodList.length; i++){
                                if(!$scope.goodList[i].isSelect){
                                    newArray[j++] = $scope.goodList[i];
                                }
                            }
                            $scope.goodList = newArray;
                            $scope.firstFlag=true;
                            $scope.goTopPos();
                            if(newArray.length == 0){
                                $scope.doRefresh();
                            }
                        }else{
                            PopupCustom.toastPopup({
                                template: '<div class="alert-text">'+result.msg+'</div>',
                                subTitle: '<div class="alert-img"><img src="img/resp-error.png"></div>'
                            },1500);
                        }
                    },function(err){
                        console.log(err);
                    }
                );
            }
            var timer ;
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                $scope.doRefresh();
                $ionicSlideBoxDelegate.$getByHandle('fistPicSlider').start();
                $scope.topDistance=false;
                timer = $interval(function(){
                    var distance=$ionicScrollDelegate.$getByHandle('goodManageScroll').getScrollPosition();
                    if(distance && distance.top>420){
                        $scope.topDistance=true;
                    }else{
                        $scope.topDistance=false;
                    }
                },100);
            });
            $scope.$on("$ionicView.beforeLeave", function(event, data){
                $interval.cancel(timer);
            });
        }])
    //商品详情
    .controller('shopGoodInfoCtrl', ['$scope', '$state', 'doService', '$window', '$rootScope', '$stateParams','$ionicScrollDelegate','$ionicSlideBoxDelegate','$interval','ModalShare','$ionicModal','PopupCustom',
        function ($scope, $state, doService, $window, $rootScope, $stateParams,$ionicScrollDelegate,$ionicSlideBoxDelegate,$interval,ModalShare,$ionicModal,PopupCustom) {
            ModalShare.initModal($scope);
            var shopId = $stateParams.shopId;
            var goodId = $stateParams.goodId;
            $scope.shareUrl=doService.getHostUrl()+"/weixin/weixinClient/index.do?" +
                "fromshanguo=weixinfront/html/customer/details/details.html?id="+goodId+"&storeId="+shopId+"&type=weixinIndex";
            $scope.goodContent = "这款被大家热捧的宝贝，据说快要卖光啦~你还不看看？";
            $scope.sortType = 0;
            $scope.operate = $scope.sortType == 0?"下架":"上架";
            $scope.good = undefined;
            var skuTypeIncluds={};
            doService.getGoodById({goodId:goodId,shopId:shopId}).then(
                function(data){
                    if(data !=null){
                        console.log(data);
                        var tempType = {};
                        var tempSku = {};
                        var skuItems = {};
                        var choseType=data.skuTypeList;
                        var skuList=data.goodSkuList;
                        angular.forEach(choseType,function(data,index){
                            var skuValues=[];
                            var typeName=data.skuName;
                            if(!tempType[typeName])
                                tempType[typeName] = {};
                            angular.forEach(skuList,function(item){
                                if(!item.skuLong){
                                    item.skuLong='';
                                }
                                var itemSkuList=item.skuLong.split('!!');
                                var tempStr = [];
                                for(var i=0;i<itemSkuList.length;i++){
                                    var itemSkuDetail=itemSkuList[i].split('~');
                                    tempStr.push(itemSkuDetail[0]+"!!" + itemSkuDetail[1]);
                                    if(typeName==itemSkuDetail[0]){
                                        var skuValue={
                                            name:itemSkuDetail[1],
                                            value:itemSkuDetail[2],
                                            isDisable:false
                                        };
                                        var isExit=false;
                                        for(var j=0;j<skuValues.length;j++){
                                            if(skuValues[j].name==skuValue.name&&skuValues[j].value==skuValue.value){
                                                isExit=true;
                                                break;
                                            }
                                        }
                                        if(!isExit){
                                            skuValues.push(skuValue);
                                            tempType[typeName][itemSkuDetail[1]] = null;
                                            skuTypeIncluds[typeName+"!!"+itemSkuDetail[1]]={};
                                        }
                                    }
                                }
                                tempSku[tempStr.join(";")]=null;
                            });
                            data.skuValues=skuValues;
                            data.selectValue='';
                        });
                        for(var key in tempSku) {
                            var items = key.split(";");
                            for(var i in items){
                                var temp = skuTypeIncluds[items[i]];
                                if(!temp){
                                    temp={};
                                }
                                for(var j in items)
                                    temp[items[j]] = null;
                            }
                        }
                        for(var type in tempType) {
                            var templist = {};
                            for(var item2 in tempType[type])
                                templist[type+"!!"+item2] = null;
                            for(var key in templist){
                                var temp = skuTypeIncluds[key];
                                for(var key2 in templist)
                                    temp[key2] = null;
                            }
                        }
                        $scope.skuTypeList=choseType;
                        for(var i = 0; i < data.goodEvaluationList.length; i++){
                            var evaluation = data.goodEvaluationList[i];
                            var timeArray = evaluation.evaluateTime.split(" ");
                            if(timeArray && timeArray.length > 0){
                                evaluation.newEvaluateTime = timeArray[0];
                            }
                        }
                    }
                    $scope.good = data;
                    $scope.sortType = $scope.good.status == 0?1:0;
                    $scope.operate = $scope.sortType == 0?"下架":"上架";
                    $ionicSlideBoxDelegate.$getByHandle('goodInfoSlider').update();
                    console.log($scope.skuTypeList);
                },function(err){
                    console.log(err);
                }
            );
            $scope.operateGood = function(){
                var param = {};
                param["shopId"]=shopId;
                param["goodIds"]=$scope.good.tradeGoodId;
                doService.operateGood($scope.sortType,param).then(
                    function(result){
                        if(result.code == 0){
                            PopupCustom.toastPopup({
                                template: '<div class="alert-text">'+$scope.operate+'成功！</div>',
                                subTitle: '<div class="alert-img"><img src="img/resp-success.png"></div>'
                            },1500);
                            var temp = $scope.good.status;
                            $scope.good.status=$scope.sortType;
                            $scope.sortType = temp;
                            $scope.operate = $scope.sortType == 0?"下架":"上架";
                        }else{
                            PopupCustom.toastPopup({
                                template: '<div class="alert-text">'+result.msg+'</div>',
                                subTitle: '<div class="alert-img"><img src="img/resp-error.png"></div>'
                            },1500);
                        }
                    },function(err){
                        console.log(err);
                    }
                );
            }
            $scope.choseSku = function(obj,index){
                for(var i = 0; i < obj.skuValues.length; i++){
                    if(index == i){
                        obj.skuValues[i].isDisable = !obj.skuValues[i].isDisable;
                    }else{
                        obj.skuValues[i].isDisable = false;
                    }
                }
            }
            $ionicModal.fromTemplateUrl('templates/shopManage/good-info-model.html', {
                scope: $scope,
                animation: 'slide-in-up'
            }).then(function(modal) {
                $scope.typeModel = modal;
            });
            $ionicModal.fromTemplateUrl('templates/shopManage/good-evaluation-model.html', {
                scope: $scope,
                animation: 'slide-in-left'
            }).then(function(modal) {
                $scope.evaluationModel = modal;
            });
            $scope.$on("$ionicView.beforeLeave", function(event, data){
                $scope.typeModel.hide();
                $scope.evaluationModel.hide();
            });
            $scope.$on('$destroy', function() {
                $scope.typeModel.remove();
                $scope.evaluationModel.remove();
            });


        }])
    //个人中心-卡券兑换
	.controller('vouchListCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicScrollDelegate',
	function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicScrollDelegate) {
		var shopId=$stateParams.shopId;
		$scope.shopId=shopId;
        $scope.pageNum=1;
        $scope.pageSize=7;
        $scope.showScroll=false;
        $scope.vouchs=[];
        $scope.doRefresh=function(){
        	$scope.pageNum=1;
        	$scope.showScroll=false;
        	doService.getVouchList({shopId:shopId,pageNum:$scope.pageNum,pageSize:$scope.pageSize}).then(
        			function(suc){
        				if(suc==null||suc.length==0||suc==''){
        					$scope.vouchDataF=true;
        					$scope.vouchDataT=false;
        				}else{
        					$scope.vouchs=suc;
        					$scope.vouchDataT=true;
        					$scope.vouchDataF=false;
        				}
                        if(suc==null||suc.length<$scope.pageSize){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
        				console.log(suc);
        			},function(err){
        				$scope.showScroll=false;
        				$scope.vouchDataF=true;
        				$scope.vouchDataT=false;
        				console.log(err);
        			}).finally(function () {
                        $scope.$broadcast('scroll.refreshComplete');
                    });
        }
        $scope.loadMore = function() {
            if($scope.vouchs.length>0){
                $scope.pageNum=$scope.pageNum+1;
            }
        	doService.getVouchList({shopId:shopId,pageNum:$scope.pageNum,pageSize:$scope.pageSize}).then(
        			function(suc){
                        if(suc==null||suc.length<$scope.pageSize){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.vouchs, suc);
                        if($scope.vouchs==null||$scope.vouchs.length==0||$scope.vouchs==''){
                        	$scope.vouchDataF=true;
                        	$scope.vouchDataT=false;
                        }else{
                        	$scope.vouchDataT=true;
                        	$scope.vouchDataF=false;
                        }
                        $ionicScrollDelegate.$getByHandle('vouch-list-content-con').resize();
        				console.log(suc);
        			},function(err){
        				$scope.showScroll=false;
        				$scope.vouchDataF=true;
        				$scope.vouchDataT=false;
        				console.log(err);
        			}).finally(function () {
                        setTimeout(function(){
                            $scope.$broadcast('scroll.infiniteScrollComplete');
                        },3000);
                    });
        }
        $scope.$on('$ionicView.beforeEnter', function() {
            $scope.loadMore();
        });
	}])
	//个人中心-卡券明细兑换
	.controller('vouchDetailListCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','ionicToast','PopupCustom','$ionicScrollDelegate',
	function($scope,$state,doService,$window,$rootScope,$stateParams,ionicToast,PopupCustom,$ionicScrollDelegate) {
		$scope.vocherId=$stateParams.vocherId;
		var vocherId=$stateParams.vocherId;
		$scope.shopId=$stateParams.shopId;
		var vouchStatus=$stateParams.statu
        $scope.pageNum=1;
        $scope.pageSize=7;
        $scope.showScroll=false;
        $scope.vouchDetails=[];
        $scope.doRefresh=function(){
        	$scope.pageNum=1;
        	$scope.showScroll=false;
			doService.getVoucherDetailList({vocherId:vocherId,status:vouchStatus,pageNum:$scope.pageNum,pageSize:$scope.pageSize}).then(
					function(suc){
						$scope.vouchDetails=suc;
						if(suc==null||suc.length==0||suc==''){
							 $scope.vouchDetails=[];
							$scope.vouchDataF=true;
							$scope.vouchDataT=false;
						}else{
							$scope.vouchDetails=suc;
							$scope.vouchDataT=true;
							$scope.vouchDataF=false;
						}
                       if(suc==null||suc.length<$scope.pageSize){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
						console.log(suc);
					},function(err){
						$scope.showScroll=false;
						$scope.vouchDataF=true;
						$scope.vouchDataT=false;
						console.log(err);
					}).finally(function () {
                        $scope.$broadcast('scroll.refreshComplete');
				        if(vouchStatus==1){
							$scope.vouchDetailT=true;
							$scope.vouchDetailF=false;
				        }else{
							$scope.vouchDetailT=false;
							$scope.vouchDetailF=true;
				        }
                    });
        }
        $scope.loadMore = function() {
            if($scope.vouchDetails.length>0){
                $scope.pageNum=$scope.pageNum+1;
            }
        	doService.getVoucherDetailList({vocherId:vocherId,status:vouchStatus,pageNum:$scope.pageNum,pageSize:$scope.pageSize}).then(
					function(suc){
						if(suc==null||suc.length<$scope.pageSize){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
						console.log(suc);
				        Array.prototype.push.apply($scope.vouchDetails, suc);
				        if($scope.vouchDetails==null||$scope.vouchDetails.length==0||$scope.vouchDetails==''){
				        	$scope.vouchDataF=true;
				        	$scope.vouchDataT=false;
				        }else{
				        	$scope.vouchDataT=true;
				        	$scope.vouchDataF=false;
				        }
				        $ionicScrollDelegate.$getByHandle('mainScroll-vouch').resize();
					},function(err){
						$scope.showScroll=false;
						$scope.vouchDataF=true;
						$scope.vouchDataT=false;
						console.log(err);
					}).finally(function () {
				        if(vouchStatus==1){
							$scope.vouchDetailT=true;
							$scope.vouchDetailF=false;
				        }else{
							$scope.vouchDetailT=false;
							$scope.vouchDetailF=true;
				        }
                        setTimeout(function(){
                            $scope.$broadcast('scroll.infiniteScrollComplete');
                        },3000);
                    });
        }
        $scope.$on('$ionicView.beforeEnter', function() {
            $scope.loadMore();
        });
        $scope.getTabVouchDetailList = function(status){
        	$ionicScrollDelegate.$getByHandle('mainScroll-vouch').scrollTop();
        	vouchStatus=status;
            $scope.pageNum=1;
            $scope.showScroll=true;
            $scope.vouchDetails=[];
        };
        $scope.setVoucherStatus = function(id){
        	var alertOptions={
        			title:"<div class='popup-div'>您确定要禁用该卡券？</div>",
        			scope:$scope,
        			buttons:[
        			         {
        			        	 text:"取消",
        			        	 type:'button-cancel'
        			         },
        			         {
        			        	 text:"确定",
        			        	 type: 'button-save',
        			        	 onTap:function(e){
        			        		 doService.setVoucherStatus({vouchId:id,status:3}).then(
        			        				 function(suc){
        			        					 if(suc.code=='0'){
        			        						 $scope.doRefresh();
        			        						 ionicToast.show('禁用该卡券成功', 'bottom', false, 2000);
        			        					 }else{
        			        						 ionicToast.show('禁用该卡券失败', 'bottom', false, 2000);
        			        					 }
        			        				 },function(err){
        			        					 ionicToast.show('禁用该卡券失败', 'bottom', false, 2000);
        			        				 });
        			        	 }
        			         }
        			         ]
        	};
            var alsert=PopupCustom.alertPopup($scope, alertOptions);
        };
	}])
	//个人中心-卡券搜索兑换
	.controller('searchVouchListCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','PopupCustom','ionicToast','$ionicScrollDelegate',
	 function($scope,$state,doService,$window,$rootScope,$stateParams,PopupCustom,ionicToast,$ionicScrollDelegate) {
		$scope.shopId=$stateParams.shopId;
		$scope.data = {
				codeName: ''
		};
        $scope.pageNum=1;
        $scope.pageSize=7;
        $scope.showScroll=false;
        $scope.vouchDetails=[];
        $scope.doRefresh=function(){
        	$scope.pageNum=1;
        	$scope.showScroll=false;
			doService.getSearchVouchList({code:$scope.data.codeName,pageNum:$scope.pageNum,pageSize:$scope.pageSize}).then(
					function(suc){
						if(suc==null||suc==''||suc.length==0){
							$scope.vouchDataF=true;
							$scope.vouchDetails=[];
						}else{
							$scope.vouchDetails=suc;
							$scope.vouchDataF=false;
						}
						if(suc==null||suc.length<$scope.pageSize){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
						console.log(suc);
					},function(err){
						$scope.showScroll=false;
						$scope.vouchDataF=false;
						console.log(err);
					}).finally(function () {
                        $scope.$broadcast('scroll.refreshComplete');
                    });
        }
        $scope.loadMore = function() {
            if($scope.vouchDetails.length>0){
                $scope.pageNum=$scope.pageNum+1;
            }
			doService.getSearchVouchList({code:$scope.data.codeName,pageNum:$scope.pageNum,pageSize:$scope.pageSize}).then(
					function(suc){
						if(suc==null||suc.length<$scope.pageSize){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
						console.log(suc);
						Array.prototype.push.apply($scope.vouchDetails, suc);
						if($scope.vouchDetails==null||$scope.vouchDetails==''||$scope.vouchDetails.length==0){
							$scope.vouchDataF=true;
						}else{
							$scope.vouchDataF=false;
						}
						$ionicScrollDelegate.$getByHandle('mainScroll-vouch-serach').resize();
					},function(err){
						$scope.showScroll=false;
						$scope.vouchDataF=false;
						console.log(err);
					}).finally(function () {
                        setTimeout(function(){
                            $scope.$broadcast('scroll.infiniteScrollComplete');
                        },3000);
                    });
        }
		$scope.getSearchVouchList = function(){
			$ionicScrollDelegate.$getByHandle('mainScroll-vouch-serach').scrollTop();
	        $scope.pageNum=1;
	        $scope.showScroll=true;
	        $scope.vouchDetails=[];
		};
        $scope.setVoucherStatus = function(id){
        	var alertOptions={
        			title:"<div class='popup-div'>您确定要禁用该卡券？</div>",
        			scope:$scope,
        			buttons:[
        			         {
        			        	 text:"取消",
        			        	 type:'button-cancel'
        			         },
        			         {
        			        	 text:"确定",
        			        	 type: 'button-save',
        			        	 onTap:function(e){
        			        		 doService.setVoucherStatus({vouchId:id,status:3}).then(
        			        				 function(suc){
        			        					 if(suc.code=='0'){
        			        						 $scope.doRefresh();
        			        						 ionicToast.show('禁用该卡券成功', 'bottom', false, 2000);
        			        					 }else{
        			        						 ionicToast.show('禁用该卡券失败', 'bottom', false, 2000);
        			        					 }
        			        				 },function(err){
        			        					 ionicToast.show('禁用该卡券失败', 'bottom', false, 2000);
        			        				 });
        			        	 }
        			         }
        			         ]
        	};
            var alsert=PopupCustom.alertPopup($scope, alertOptions);
        };
	}])
	//个人中心-我的账户
	.controller('accountMsgCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicPopup','ionicToast',
	function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicPopup,ionicToast) {
		$scope.shopId=$stateParams.shopId;
        $scope.account={
        		canSettled:"0.00",
        		noSettled:"0.00",
        		settled:"0.00"
            };
		$scope.bankShow=true;
		$scope.$on("$ionicView.beforeEnter", function(event, data){
			doService.getAccountBankMsg({shopId:$stateParams.shopId}).then(
					function(suc){
						if(suc==null||suc==''||suc.length==0){
							$scope.bankShow=true;
						}else{
							$scope.bank=suc[0];
							$scope.bankShow=false;
						}
						console.log(suc);
					},function(err){
						console.log(err);
					});

            doService.getAccountMsg({shopId:$stateParams.shopId}).then(
                function(suc){
                    console.log(suc);
                    $scope.account=suc;
                },function(err){
                    console.log(err);
                });
		});
	}])
	//个人中心-我的账户-修改新增银行卡
	.controller('buildBankMsgCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicPopup','ionicToast',
	     function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicPopup,ionicToast) {
		$scope.shopId=$stateParams.shopId;
		$scope.bank = {
				id: '',
				name:'',
				card:'',
				bankCard:'',
				bankName:'',
				mobile:''
		};
		doService.getBankCardObj({id:$stateParams.bankId}).then(
				function(suc){
					if(suc!=null&&suc!=''){
						$scope.bank=suc;
					}
					console.log(suc);
				},function(err){
					console.log(err);
		});
		 $scope.saveOrUpdateBank=function(){
			var bankMsg=$scope.bank;
			if(bankMsg.name==''||bankMsg.name==null){
				ionicToast.show('开户人姓名不能为空', 'bottom', false, 2000);
			}else if(bankMsg.card==''||bankMsg.card==null){
				ionicToast.show('身份证号码不能为空', 'bottom', false, 2000);
			}else if(bankMsg.bankCard==''||bankMsg.bankCard==null){
				ionicToast.show('银行卡号不能为空', 'bottom', false, 2000);
			}else if(bankMsg.bankAddress==''||bankMsg.bankAddress==null){
				ionicToast.show('开户行不能为空', 'bottom', false, 2000);
			} else if(!bankMsg.bankName){
                ionicToast.show('所属银行不能为空', 'bottom', false, 2000);
            } else if(bankMsg.mobile==''||bankMsg.mobile==null){
				ionicToast.show('手机号码不能为空', 'bottom', false, 2000);
			}else{
				doService.saveOrUpdateBankMsg({param:{shopId:$stateParams.shopId,id:bankMsg.id,name:bankMsg.name,card:bankMsg.card,bankCard:bankMsg.bankCard,
					bankName:bankMsg.bankName,mobile:bankMsg.mobile,bankAddress:bankMsg.bankAddress}}).then(
						function(suc){
		                	if(suc.code=='0'){
		                		 ionicToast.show('操作成功', 'bottom', false, 2000);
		                         $state.go('accountMsg',{shopId: $stateParams.shopId})
		                	}else{
		                		ionicToast.show(suc.msg, 'bottom', false, 2000);
		                	}
							console.log(suc);
						},function(err){
							ionicToast.show('操作失败', 'bottom', false, 2000);
							console.log(err);
				});
			}
		}
		
	}])
	//个人中心-我的账户-店铺协议
	.controller('getShopProtolCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicPopup','ionicToast',
	 function($scope,$state,doService,$window,$rootScope,$stateParams,$ionicPopup,ionicToast) {
		doService.getShopProtolText({shopId:$stateParams.shopId}).then(
				function(suc){
					$scope.stroPro=suc;
					console.log("rgeger:"+suc);
				},function(err){
					console.log(err);
		});
	}])
	//个人中心-创建的活动
	.controller('activityPersonListCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','PopupCustom','ionicToast','$ionicScrollDelegate',
	  function($scope,$state,doService,$window,$rootScope,$stateParams,PopupCustom,ionicToast,$ionicScrollDelegate) {
		var shopId=$stateParams.shopId;
		var actStatus=$stateParams.statu;
        $scope.start=0;
        $scope.limit=7;
        $scope.showScroll=false;
        $scope.activitys=[];
        $scope.doRefresh=function(){
        	$scope.start=0;
        	$scope.showScroll=false;
	        doService.geActivityListData({conditionStr:{subbranchId:shopId,activityStatus:actStatus,personal:1},limit:$scope.limit,start:$scope.start}).then(
	                function(suc){
	                    console.log(suc);
	                    if(suc==null||suc==''||suc.resultList==null||suc.resultList.length<1){
	                    	$scope.actPersonShow=true;
	                    	$scope.activitys=[];
	                    }else{
	                    	$scope.activitys=suc.resultList;
	                    	$scope.actPersonShow=false;
	                    }
                        if(suc==null||suc.resultList==null||suc.resultList.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
	                },function(err){
	                	$scope.showScroll=false;
	                	$scope.actPersonShow=true;
	                    console.log(err);
	             }).finally(function () {
	                    if(actStatus==1){
	                    	$scope.actPersonT=true;
	                    	$scope.actPersonDelete=false;
	                    }else{
	                    	$scope.actPersonT=false;
	                    	$scope.actPersonDelete=true;
	                    }
                     $scope.$broadcast('scroll.refreshComplete');
                 });
        }
        $scope.loadMore = function() {
            if($scope.activitys.length>0){
                $scope.start=$scope.start+$scope.limit;
            }
	        doService.geActivityListData({conditionStr:{subbranchId:shopId,activityStatus:actStatus,personal:1},limit:$scope.limit,start:$scope.start}).then(
	                function(suc){
                        if(suc==null||suc.resultList==null||suc.resultList.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
	                    Array.prototype.push.apply($scope.activitys, suc.resultList);
	                    if($scope.activitys==null||$scope.activitys==''||$scope.activitys.length<1){
	                    	$scope.actPersonShow=true;
	                    }else{
	                    	$scope.actPersonShow=false;
	                    }
	                    $ionicScrollDelegate.$getByHandle('mainScroll-act-person').resize();
	                },function(err){
	                	$scope.actPersonShow=true;
	                    console.log(err);
	                    $scope.showScroll=false;
	             }).finally(function () {
	                    if(actStatus==1){
	                    	$scope.actPersonT=true;
	                    	$scope.actPersonDelete=false;
	                    }else{
	                    	$scope.actPersonT=false;
	                    	$scope.actPersonDelete=true;
	                    }
                     setTimeout(function(){
                         $scope.$broadcast('scroll.infiniteScrollComplete');
                     },3000);
                 });
        }
          $scope.$on("$ionicView.beforeEnter", function(event, data){
              $scope.loadMore();
          });
		$scope.getTabActPersonList=function(statu){
			$ionicScrollDelegate.$getByHandle('mainScroll-act-person').scrollTop();
			actStatus=statu;
	        $scope.start=0;
	        $scope.showScroll=true;
	        $scope.activitys=[];
		}
		$scope.deleteActById=function(id){
        	var alertOptions={
        			title:"<img src='img/delete.png' class='popup-img'><div class='popup-div'>确定要删除此活动吗？</div>",
        			scope:$scope,
        			buttons:[
        			         {
        			        	 text:"取消",
        			        	 type:'button-cancel'
        			         },
        			         {
        			        	 text:"确定",
        			        	 type: 'button-save',
        			        	 onTap:function(e){
        					    	   doService.deleteActById({Ids:id}).then(
        						                function(suc){
        						                	if(suc.success){
        						                		 $scope.doRefresh();
        						                		 ionicToast.show('删除成功', 'bottom', false, 2000);
        						                	}else{
        						                		ionicToast.show('删除失败', 'bottom', false, 2000);
        						                	}
        						                },function(err){
        						                	ionicToast.show('删除失败', 'bottom', false, 2000);
        						    });
        			        	 }
        			         }
        			  ]
        	};
        	var alsert=PopupCustom.alertPopup($scope, alertOptions);
		}
		$scope.changeActStatus=function(id){
        	var alertOptions={
        			title:"<img src='img/forbiden.png' class='popup-img'><div class='popup-div'>确定要禁用该活动（禁用后不可启用）？</div>",
        			scope:$scope,
        			buttons:[
        			         {
        			        	 text:"取消",
        			        	 type:'button-cancel'
        			         },
        			         {
        			        	 text:"确定",
        			        	 type: 'button-save',
        			        	 onTap:function(e){
        								doService.changeActStatus({Ids:id}).then(
        										function(suc){
        						                	if(suc.success){
        						                		 $scope.doRefresh();
        						                		 ionicToast.show('禁用成功', 'bottom', false, 2000);
        						                	}else{
        						                		ionicToast.show('禁用失败', 'bottom', false, 2000);
        						                	}
        										},function(err){
        											ionicToast.show('禁用失败', 'bottom', false, 2000);
        							});
        			        	 }
        			         }
        			  ]
        	};
        	var alsert=PopupCustom.alertPopup($scope, alertOptions);
		}
		$scope.getActicityDetail=function(id){
			 $state.go('activityPersonDetail',{shopId:shopId,activityId: id})
		}
	}])

    //个人中心-活动详情
    .controller('activityPersonDetailCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams','$ionicSlideBoxDelegate','ModalShare','$ionicScrollDelegate',
        function($scope,$state,doService,$window,$rootScope,$stateParams, $ionicSlideBoxDelegate,ModalShare,$ionicScrollDelegate) {
            //分享
            new ModalShare.initModal($scope);

            var activityId=$stateParams.activityId;
            var shopId=$stateParams.shopId;
            $scope.shopId=shopId;
            $scope.activityId=activityId;

            //活动介绍展开收起
            $scope.activityJscStyle=1;
            $scope.activityJscMoreText="展开";
            $scope.activityJscMore=function(){
                if($scope.activityJscStyle==1){
                    $scope.activityJscStyle=0;
                    $scope.activityJscMoreText="收起";
                }else{
                    $scope.activityJscStyle=1;
                    $scope.activityJscMoreText="展开";
                }
                //$ionicScrollDelegate.$getByHandle('act-person-detail-content-ti').scrollTop();
            }

            //图片放大预览
            $scope.images = [];
            var myPhotoBrowserStandalone ;
            $scope.photoBrowser = photoBrowser;
            function photoBrowser(index){
                photoBrowserStandalone(index, $scope.images)
            }
            function photoBrowserStandalone(index, images){
                var myApp = new Framework7({
                    init: false, //IMPORTANT - just do it, will write about why it needs to false later
                });
                var photoBrowsers = document.getElementsByClassName("photo-browser")
                if (photoBrowsers.length <= 0) {
                    myPhotoBrowserStandalone = myApp.photoBrowser({
                        type: 'standalone',
                        theme: 'light',
                        photos : images,
                        initialSlide: index,
                        onClose: function(){
                            myApp = undefined;
                        }
                    });
                }
                myPhotoBrowserStandalone.open();
            }
            $scope.$on('$ionicView.beforeLeave',function(){
                myPhotoBrowserStandalone.close();
            })

            //分享地址
            $scope.shareUrl=doService.getHostUrl()+"/weixin/weixinClient/weixinEventIndex.do?activityId="+activityId+"&storeId="+shopId;
            $scope.shareWeixin=function(){
                $scope.openShareModal($scope.activityInfo.title,$scope.shareUrl,'',$scope.activityInfo.image.length>0?$scope.activityInfo.image[0].picUrl:'');
            }

            $scope.myActiveSlide = 0;
            $scope.activityInfo={};

            $scope.$on("$ionicView.beforeEnter", function(event, data){
                //根据ID获取活动详情
                doService.getActivityDetailData({id:activityId}).then(
                    function(suc){
                        $scope.activityInfo=suc;
                        angular.forEach($scope.activityInfo.image, function(data,index,array){
                            $scope.images[index]=data.picUrl;
                        });
                        $ionicSlideBoxDelegate.$getByHandle('activeSildeHandler').update();
                        console.log($scope.activityInfo);
                    },function(err){
                        console.log(err);
                 })
                $scope.loadMore();
            });

            //分页初始化
            $scope.start=0;
            $scope.limit=6;
            $scope.isShow=true;
            $scope.showScroll=false;
            $scope.comments=[];
            $scope.loadMore = function() {
                if($scope.comments.length>0){
                    $scope.start=$scope.start+ $scope.limit;
                }
                doService.geActivityCommentListData({conditionStr:{"eventActivityId":activityId},start:$scope.start,limit:$scope.limit}).then(
                    function(suc){
                        console.log(suc);
                        var date=suc.resultList;
                        if($scope.start==0&&date.length<1){
                            $scope.isShow=true;
                        }else{
                        	$scope.isShow=false;
                        }
                        if(date.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
                        Array.prototype.push.apply($scope.comments, suc.resultList);
                        $ionicScrollDelegate.$getByHandle('act-person-detail-content-ti').resize();
                    },function(err){
                        console.log(err);
                    }).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                    },3000);
                });
            };
        }])

    .controller('ModalTestController', function($scope, ModalShare, ModalTabMenu,$ionicPopup,$timeout) {
        new ModalShare.initModal($scope);
        $scope.showAlert = function() {
            var alertPopup = $ionicPopup.alert({
                title: 'Don\'t eat that!',
                template: 'It might taste good'
            });
            alertPopup.then(function(res) {
                console.log('Thank you for not eating my delicious ice cream cone');
            });
        };
        $scope.showPopup = function() {
            $scope.data = {};

            // An elaborate, custom popup
            var myPopup = $ionicPopup.show({
                template: '<div style="text-align: center;width:100%;color:white;">商品已下架</div>',
                subTitle: '<img src="img/dl_ps.png">'
                // subTitle: 'Please use normal things',
                // scope: $scope,
                // buttons: [
                //     { text: 'Cancel' },
                //     {
                //         text: '<b>Save</b>',
                //         type: 'button-positive',
                //         onTap: function(e) {
                //             if (!$scope.data.wifi) {
                //                 //don't allow the user to close unless he enters wifi password
                //                 e.preventDefault();
                //             } else {
                //                 return $scope.data.wifi;
                //             }
                //         }
                //     }
                // ]
            });
            myPopup.then(function(res) {
                console.log('Tapped!', res);
            });
            // $timeout(function() {
            //     myPopup.close(); //close the popup after 3 seconds for some reason
            // }, 3000);
        };
        /**
         * 定义：
         *  TabMenu在$scope 中的变量名称，
         *  打开TabMenu 的方法名称，
         *  关闭TabMenu 的方法名称
         */
        var tabMenuName = "classifyMenu",
            openTabFuncName = "openClassifyMenu",
            closeTabFuncName = "closeClassifyMenu";

        var select = function(item){
            console.log(item.id);
        }

        /**
         * 初始化，为了能够同时存在多个TabMenu, 需要 new
         */
        var tabMenu = new ModalTabMenu.initModal($scope, {
            name: tabMenuName,
            open: openTabFuncName,
            close: closeTabFuncName,
            //onselect: $scope.select,
            //selected: 21,
            data: [{ id: 0, name: '全部分类',
                children: [
                    { id: -1, name: '全部' }
                ]
            }, { id: 1, name: '学习类型',
                children: [
                    { id: -1, name: '全部' }
                ]
            }, { id: 2, name: '知识类型',
                children: [
                    { id: -1, name: '全部' },
                    { id: 21, name: '案例分析' },
                    { id: 22, name: '激励机制' },
                    { id: 23, name: '时政法政' }
                ]
            }, { id: 3, name: '咨询类型',
                children: [
                    { id: -1, name: '全部' }
                ]
            }]
        });

        var selectMenuName = "selectMenu",
            openSelectFuncName = "openSelectMenu",
            closeSelectFuncName = "closeSelectMenu";

        var selectMenu = new ModalTabMenu.initModal($scope, {
            name: selectMenuName,
            open: openSelectFuncName,
            close: closeSelectFuncName,
            data: [
                { id: 0, name: '全部' },
                { id: 1, name: '文章' },
                { id: 2, name: '视频' }
            ]
        });


        var timeClickCallback = function($event, params){
            console.log(params.data);
            console.log($scope.tabMenuData);
        }

        /**
         * TabMenu 的初始化信息
         * 用法： <ym-tab-menu config='tabMenuData'></ym-tab-menu>
         */
        $scope.tabMenuData = [{ name: '', scope: $scope, tabMenu: tabMenuName, onselect: select,
            toggles: [
                {style: {color: "#000"}, icon: './img/yxtg_qbfl_md.png', callback: $scope[closeTabFuncName]},
                {style: {color: "#f55"}, icon: './img/yxtg_qbfl_dl.png', callback: $scope[openTabFuncName]}
            ],
        }, { name: '时间',
            toggles: [
                {icon: './img/yxtg_jx.png', callback: timeClickCallback, params:{data: 1}},
                {icon: './img/yxtg_sx.png', callback: timeClickCallback, params:{data: 2}}
            ],
        }, { name: '阅读量',
            toggles: [
                {icon: './img/yxtg_jx.png'},
                {icon: './img/yxtg_sx.png'}
            ],
        }, { name: '', scope: $scope, tabMenu: selectMenuName, onselect: select,
            toggles: [
                {icon: './img/yxtg_sx_2.png', callback: $scope[closeSelectFuncName], params: {setData: false}},
                {icon: './img/yxtg_sx_2.png', callback: $scope[openSelectFuncName]}
            ],
        }];

        tabMenu.setSelectItem(23);

        $scope.searchData = {
            text: "搜索",
            icon: "img/search.png",
            iconOpen: "img/dpgl_ssl_ss.png",
            value: "",
            placeholder: "擦，感觉好~有意思",
            autoClear: true,
            callback: function(value){
                console.log("Search: "+$scope.searchData.value);
            },
            onclose: function(value) {
                console.log("Close: "+$scope.searchData.value);
            }
        }
    })
    .controller('settingCtrl', ['$scope','$state','doService','$stateParams','$rootScope',
        function($scope, $state, doService, $stateParams,$rootScope){
        doService.getShopInfo({shopId:$stateParams.shopId}).then(
            function(suc){
                $scope.shopInfo = suc;
                console.log(suc);
            },function(err){
                console.log(err);
            });
        //$scope.versionCode = 'V1.0.0';
        $scope.checkApkUpdate = function(){
            if(ionic.Platform.isAndroid()){
                webapp.checkUpdate();
            }else if(ionic.Platform.isIOS()){
                if(native)
                    native.send({action:'checkApkUpdate'},function(){});
            }
        };
        //$scope.romSize = '1.2MB';
        $scope.cleanCache=function(){
            if(ionic.Platform.isAndroid()){
                webapp.cleanCache();
                $ionicHistory.clearCache();
            }else if(ionic.Platform.isIOS()){
                if(native)
                    native.send({action:'cleanCache'},function(){});
            }
        };

        $scope.loginOut=function(){
            if(ionic.Platform.isAndroid()){
                webapp.loginOut();
                $rootScope.clearCache();
                $state.go("login");
            }else if(ionic.Platform.isIOS()){
                if(native)
                    native.send({action:'loginOut'},function(){});
            }
        };
        window.reSetLogin=function(val){
            $rootScope.clearCache();
            $state.go("login");
        };
        var getVersionCode = function(){
            if(ionic.Platform.isAndroid()){
                var versionStr=webapp.getVersionName();
                var version=angular.fromJson(versionStr);
                $scope.versionCode = version.versionCode;
                $scope.versionName = version.versionName;
                return;
            }
            if(ionic.Platform.isIOS()){
                if(native)
                    native.send({action:'getVersionName'},  function(response) {});
            }
        };
        window.setVersionName=function(val){
            $scope.versionName=val;
        };
        $scope.$on("$ionicView.beforeEnter", function(event, data){
            getVersionCode();
        });
        //修改密码
        $scope.updatePassword = function(){
            $state.go('updatePassword',{mobile: $scope.shopInfo.mobile, shopId:$stateParams.shopId})
        };
        //意见反馈
        $scope.opinion = function(){
            $state.go('opinion',{bizId: $scope.shopInfo.name, shopId:$stateParams.shopId, mobile: $scope.shopInfo.mobile})
        };
        //关于我们
        $scope.aboutUs = function(){
            $state.go('aboutUs')
        };
        //服务协议
        $scope.serviceAgreement = function(){
            $state.go('serviceAgreement')
        };
    }])

    //设置-修改密码
    .controller('updatePasswordCtrl', ['$scope','$state','doService','$stateParams','ionicToast',function($scope,$state, doService, $stateParams, ionicToast){
        $scope.data = {
            phoneCode : $stateParams.mobile,
            verifyCode : ''
        };

        $scope.isShow = (!$stateParams.mobile);
        $scope.isFirstClick = true;

        //获取验证码
        $scope.getVerifyCode = function(){
            if($scope.data.phoneCode == ''){
                return ionicToast.show('手机号码不能为空', 'middle', false, 2000);
            } else{
                if($scope.isFirstClick){
                    $scope.isFirstClick = false;
                    $scope.clickTime = (new Date()).getTime();
                    doService.getVerifyPhone({phoneCode : $scope.data.phoneCode}).then(
                        function(suc){
                            ionicToast.show(suc.msg, 'middle', false, 2000);
                        },function(err){
                            console.log(err);
                        });
                } else{
                    var timeNow = (new Date()).getTime();
                    if(timeNow - $scope.clickTime < 60000){
                        return ionicToast.show('60秒内不能再次获取验证码', 'middle', false, 2000);
                    } else{
                        $scope.clickTime = timeNow;
                        doService.getVerifyPhone({phoneCode : $scope.data.phoneCode}).then(
                            function(suc){
                                ionicToast.show(suc.msg, 'middle', false, 2000);
                            },function(err){
                                console.log(err);
                            });
                    }
                }

            }
        };

        //下一步
        $scope.getNext = function(){
            if($scope.data.phoneCode == ''){
                return ionicToast.show('手机号码不能为空', 'middle', false, 2000);
            } else if($scope.data.verifyCode == ''){
                return ionicToast.show('验证码不能为空', 'middle', false, 2000);
            } else{
                $state.go('updatePasswordNext',{mobile: $scope.data.phoneCode, verifyCode:$scope.data.verifyCode, shopId:$stateParams.shopId})
            }
        };
    }])

    //设置-修改密码-下一步
    .controller('updatePasswordNextCtrl', ['$scope','$state','doService','$stateParams','md5','ionicToast',
        function($scope, $state, doService, $stateParams, md5, ionicToast){
        $scope.data = {
            password : '',
            repassword : ''
        };

        //完成
        $scope.finish = function(){
            if($scope.data.password == ''){
                return ionicToast.show('新密码不能为空', 'middle', false, 2000);
            } else if($scope.data.repassword == ''){
                return ionicToast.show('重复新密码不能为空', 'middle', false, 2000);
            } else{
                var passwordMd5 = md5.createHash($scope.data.password);
                var repasswordMd5 = md5.createHash($scope.data.repassword);

                doService.getResetPassword({verifyCode:$stateParams.verifyCode, mobile:$stateParams.mobile,
                    password:passwordMd5, repassword:repasswordMd5}).then(
                    function(suc){
                        if(suc.success){
                            ionicToast.show('密码修改成功', 'middle', false, 2000);
                            if($stateParams.shopId){
                                $state.go('personCenter',{shopId:$stateParams.shopId});
                            } else{
                                $state.go('login');
                            }
                        } else{
                            return ionicToast.show(suc.msg, 'middle', false, 2000);
                        }
                    },function(err){
                        console.log(err);
                    });
            }
        };
    }])

    //设置-意见反馈
    .controller('opinionCtrl',['$rootScope','$scope','$state','doService','$ionicPopup','$stateParams','ionicToast',
        function($rootScope,$scope,$state,doService,$ionicPopup,$stateParams,ionicToast){
            //var bizId = $stateParams.bizId;
            $scope.opinion = {
                type : 2,
                clientName : $stateParams.bizId,
                versionName : '',
                clientPhone : $stateParams.mobile
            };
            $scope.changeType = function(type){
                $scope.opinion.type=type;
            };
            var getVersionCode = function(){
                if(ionic.Platform.isAndroid()){
                    var versionStr=webapp.getVersionName();
                    var version=angular.fromJson(versionStr);
                    $scope.versionCode = version.versionCode;
                    $scope.versionName = version.versionName;
                    return;
                }else if(ionic.Platform.isIOS()){
                    if(native)
                        native.send({action:'getVersionName'},  function(response) {});
                }
            };
            window.setVersionName=function(val){
                $scope.versionName=val;
            };
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                getVersionCode();
            });
            $scope.addOpinion = function(opinion){
                if($scope.opinion.description == undefined || $scope.opinion.description == ''){
                    return ionicToast.show('请填写内容后在发送', 'middle', false, 2000);
                }
                //$scope.opinion.clientId = bizId;
                $scope.opinion.versionName = $scope.versionName;
                if(ionic.Platform.isAndroid()){
                    $scope.opinion.platform = 0;
                }if(ionic.Platform.isIOS()){
                    $scope.opinion.platform = 1;
                }
                console.log(opinion);
                doService.addOpinionDo({modelJson:JSON.stringify($scope.opinion)}).then(function(data){
                    if(data.code == '1'){
                        ionicToast.show('提交成功', 'middle', false, 2000);
                        $state.go('setting',{shopId: $stateParams.shopId})
                    } else{
                        return ionicToast.show(data.msg, 'middle', false, 2000);
                    }
                },function(err){
                    console.log(err);
                });
            }
        }])

    //设置-关于我们
    .controller('aboutUsCtrl', ['$scope','$state','storage','doService','ModalShare', function($scope,$state,storage,doService,ModalShare){
        //$scope.versionCode =  'V1.0.0';

        //分享
        new ModalShare.initModal($scope);

        //获取分享内容
        $scope.qrcodeInfo="http://www.baidu.com";
        $scope.shopTitle = '关于我们';

        doService.aboutUsDo({}).then(function(suc){
            $scope.aboutUs = suc;
        },function(err){
            console.log(err);
        })

        var getVersionCode = function(){
            if(ionic.Platform.isAndroid()){
                var versionStr=webapp.getVersionName();
                var version=angular.fromJson(versionStr);
                $scope.versionCode = version.versionCode;
                $scope.versionName = version.versionName;
                return;
            }
            if(ionic.Platform.isIOS()){
                var callback = function(bridge) {
                    if(bridge)
                        bridge.callHandler('getversionNum',  function(response) {
                            var responeObj = angular.fromJson(response);
                            $scope.versionName=responeObj.versionNum;
                        });
                }
                if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
                if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
                window.WVJBCallbacks = [callback];
                var WVJBIframe = document.createElement('iframe');
                WVJBIframe.style.display = 'none';
                WVJBIframe.src = 'wvjbscheme://__BRIDGE_LOADED__';
                document.documentElement.appendChild(WVJBIframe);
                setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0);
                return;
            }
        };
        $scope.$on("$ionicView.beforeEnter", function(event, data){
            getVersionCode();
        });

        }])

    //设置-服务协议
    .controller('serviceAgreementCtrl', ['$scope','$state','storage','doService', function($scope,$state,storage,doService){
            //$scope.myText={};
            doService.serviceAgreement({}).then(function(suc){
                $scope.myText = suc;
            },function(err){
                console.log(err);
            })
        }])

    //我的订单列表
    .controller('orderListCtrl', ['$scope','$state','doService','$stateParams','$ionicPopup','$ionicScrollDelegate',
        function($scope,$state,doService,$stateParams,$ionicPopup,$ionicScrollDelegate){
            $scope.showScroll = true;
            $scope.isInLoad = true;
            $scope.recommendGoods = [];
            $scope.isFirst = true;
            var shopId = $stateParams.shopId;
            var startIndex = 1;
            var pageSize = 12;
            var goTopPos = function(){
                $ionicScrollDelegate.$getByHandle('mainScroll').scrollTop(true);
            };

            $scope.getOrderInfo = function(orderId){
                $state.go('orderInfo',{orderId: orderId});
            };
            $scope.showTabView = 'all';
            $scope.showTab = function(tabName){
                $scope.showTabView = tabName;
                refreshList();
            };

            $scope.nextPage = function(){
                $scope.isInLoad = false;
                startIndex++;
                doService.getOrderList(createParam()).then(function(data){
                    if(data && data.length < pageSize){
                        $scope.showScroll = false;
                    }
                    if($scope.isFirst){
                        if(data && data.length > 0){
                            $scope.isShow = false;
                        } else{
                            $scope.isShow = true;
                        }
                        $scope.isFirst = false;
                    }
                    if($scope.showTabView == 'all'){
                        Array.prototype.push.apply($scope.recommendGoods, data);
                    } else if($scope.showTabView == 'onPay'){
                        Array.prototype.push.apply($scope.onPayBill, data);
                    } else if($scope.showTabView == 'alPay'){
                        Array.prototype.push.apply($scope.alPayBill, data);
                    }
                },function(err){
                    console.log(err);
                }).finally(function () {
                    setTimeout(function(){
                        $scope.$broadcast('scroll.infiniteScrollComplete');
                        $scope.isInLoad = true;
                    },3000);
                });
            };

            var createParam = function(){
                var param = new Map();
                param["shopId"] = shopId;
                param["pageNum"] = startIndex;
                param["pageSize"] = pageSize;
                if($scope.showTabView == 'all'){
                    param["status"] = 0;
                }if($scope.showTabView == 'onPay'){
                    param["status"] = 1;
                }if($scope.showTabView == 'alPay'){
                    param["status"] = 2;
                }
                return param;
            };

            var refreshList = function (){
                startIndex = 0;
                goTopPos();
                $scope.showScroll = false;
                setTimeout(function(){
                    doService.getOrderList(createParam()).then(function(data){
                        console.log(data);
                        if(data && data.length < pageSize){
                            $scope.showScroll = false;
                        } else{
                            $scope.showScroll = true;
                        }
                        if(data && data.length > 0){
                            $scope.isShow = false;
                        } else{
                            $scope.isShow = true;
                        }
                        if($scope.showTabView == 'all'){
                            $scope.recommendGoods = data;
                        } else if($scope.showTabView == 'onPay'){
                            $scope.onPayBill = data;
                        } else if($scope.showTabView == 'alPay'){
                            $scope.alPayBill = data;
                        }
                    },function(err){
                        console.log(err);
                    }).finally(function () {
                        $scope.$broadcast('scroll.refreshComplete');
                    });
                },50);
            };
            $scope.titleWidth = window.screen.width - 103;
            //refreshList();
            //$scope.$on("$ionicView.beforeEnter", function(event, data){
                refreshList();
            //});
        }])

    //我的订单-详情
    .controller('orderInfoCtrl', ['$scope','$state','storage','doService','$stateParams','$ionicPopup',
        function($scope,$state,storage,doService,$stateParams,$ionicPopup){
            var freshOrder = function(){
                doService.getOrderDetail({'id':$stateParams.orderId}).then(function(suc){
                    console.log(suc);
                    $scope.order = suc;

                    //计算商品总额
                    var totalMoney = 0;
                    var indentList = suc.indentList;
                    for(var i=0; i<indentList.length; i++){
                        totalMoney = totalMoney + (indentList[i].finalAmount * indentList[i].number);
                    }
                    $scope.totalMoney = totalMoney;
                },function(err){
                    console.log(err);
                })
            }
            freshOrder();
        }])
    //登录
    .controller('loginCtrl',['$scope','$state','doService','ionicToast','md5','$rootScope',
        function($scope,$state,doService,ionicToast,md5,$rootScope){
        // return ionicToast.show('请填写内容后在发送', 'middle', false, 2000);
            $scope.$on("$ionicView.beforeEnter", function(event, data){
                $rootScope.clearHistory();
            });
        $scope.unlogin=true;
        var shopId=-1;
        $scope.loginInfo={
            userName:'',
            passWord:'',
            isReadTK:true
        };
        $scope.shopInfo={
            name:''
        };
         $scope.goFuWuTk=function(){
             $state.go("serviceAgreement");
         };
        $scope.setShopName=function(){
            if($scope.shopInfo.name==''||$scope.shopInfo.name==null){
                ionicToast.show('请输入店铺名称', 'middle', false, 2000);
                return;
            }
            var param = {
                shopId:shopId,
                name:$scope.shopInfo.name
            };
            doService.saveShopName({"param":param}).then(function(data){
                if(data.code==1){
                    ionicToast.show(data.msg, 'middle', false, 2000);
                }else if(data.code==0){
                    $state.go("home",{shopId:shopId});
                }
            },function(err){

            });
        };
        $scope.loginUser=function(){
            if($scope.loginInfo.isReadTK==false){
                ionicToast.show('请先阅读服务条款', 'middle', false, 2000);
                return;
            }
            if(!$scope.loginInfo.userName||$scope.loginInfo.userName==''){
                ionicToast.show('请输入用户名', 'middle', false, 2000);
                return;
            }
            if(!$scope.loginInfo.passWord||$scope.loginInfo.passWord==''){
                ionicToast.show('请输入密码', 'middle', false, 2000);
                return;
            }
            var passwordMd5 = md5.createHash($scope.loginInfo.passWord);
            console.log( passwordMd5);
            doService.loginDo({
                loginName:$scope.loginInfo.userName,
                password:passwordMd5
            }).then(function(data){
                if(data.code==1){
                    ionicToast.show(data.msg, 'middle', false, 2000);
                }else{
                    if(!data.msg.name){
                        shopId=data.msg.id;
                        //$scope.unlogin=false;
                        $state.go("shopInfo",{shopId : data.msg.id});
                        // $state.go("home",{shopId:data.msg.id});
                    }else{
                        if(ionic.Platform.isAndroid()){
                            webapp.loginBind(data.msg.id,$scope.loginInfo.userName ,"token");
                            if(!data.msg.name){
                                $state.go("shopInfo",{shopId : data.msg.id});
                            } else{
                                $state.go("home",{shopId:data.msg.id});
                            }
                        }else if(ionic.Platform.isIOS()){
                            if(native)
                                native.send({action:'loginBind','shopId':data.msg.id ,'userName':$scope.loginInfo.userName,'token':'token'},function(response) {
                                    if(!data.msg.name){
                                        $state.go("shopInfo",{shopId : data.msg.id});
                                    } else{
                                        $state.go("home",{shopId:data.msg.id});
                                    }
                            });
                        }
                    }
                }
            },function(err){
                console.log( err);
            });
        };
        $scope.goReSetPassword=function(){
            $state.go("updatePassword");
        };
    }])
    //完善店铺信息
    .controller('shopInfoCtrl', ['$scope','$state','doService','$window','$rootScope','ionicToast','$stateParams','$ionicLoading',
        function($scope,$state,doService,$window,$rootScope,ionicToast,$stateParams,$ionicLoading) {
            $scope.pageShow = true;
            var cityNum = {
                province : '',
                city : '',
                country : ''
            };
            $scope.city = {
                citydata : '',
                cityNum : cityNum,
                address : ''
            };

            $scope.$on("$ionicView.beforeEnter", function(event, data){
                doService.getShopInfo({shopId : $stateParams.shopId}).then(
                    function(suc){
                        $scope.shopInfo = suc;
                        cityNum.province = suc.province;
                        cityNum.city = suc.city;
                        cityNum.country = suc.country;
                        if(cityNum.reset){
                            cityNum.reset();
                        }
                        $scope.city.address = suc.address;
                    },function(err){
                        console.log(err);
                    });

                doService.getAccountBankMsg({shopId:$stateParams.shopId}).then(
                    function(suc){
                        console.log(suc);
                        if(suc == null || suc == '' || suc.length == 0){
                            $scope.bank ={};
                        }else{
                            $scope.bank = suc[0];
                        }
                    },function(err){
                        console.log(err);
                    });
            });

            var tempUrl=$rootScope.qiNiu;
            var saveShopImage = function(url){
                var param = {
                    shopId : $stateParams.shopId,
                    headUrl : url
                };
                doService.saveShopName({"param":param}).then(
                    function(suc){
                        if(suc.code == 0){
                            $scope.shopInfo.headImgUrl=tempUrl;
                        }
                    },function(err){
                        console.log(err);
                    }).finally(function(){
                    $scope.hide();
                    tempUrl=$rootScope.qiNiu;
                });
            };
            window.setHeadImg=function(type){
                tempUrl+=type;
                $scope.show();
                saveShopImage(tempUrl);
            };
            $scope.show = function() {
                $ionicLoading.show({
                    template: '头像上传中'
                });
            };
            $scope.hide = function(){
                $ionicLoading.hide();
            };
            $scope.choosePic = function(){
                if(ionic.Platform.isAndroid()){
                    webapp.imageChoose("1");
                }else if(ionic.Platform.isIOS()){
                    if(native)
                        native.send({action:'imageChoose', type: 1},function() {});
                }
            };

            $scope.getNext = function(){
                $scope.pageShow = false;
            };

            $scope.goBack = function(){
                $scope.pageShow = true;
            };

            $scope.saveShopInfo = function(){
                var cityData = $scope.city.citydata.split("-");
                var countryName = '';
                if(cityData.length > 2){
                    countryName = cityData[2];
                }
                var param = {
                    shopId : $stateParams.shopId,
                    province : $scope.city.cityNum.province,
                    city : $scope.city.cityNum.city,
                    addres : $scope.city.address,
                    provinceName : cityData[0],
                    cityName : cityData[1],
                    name : $scope.shopInfo.name,
                    phone : $scope.shopInfo.phone
                };
                if(countryName){
                    param['countryName'] = countryName;
                    param['country'] = $scope.city.cityNum.country;
                }

                if(!$scope.shopInfo.headImgUrl){
                    return ionicToast.show('门店照片不能为空', 'middle', false, 2000);
                } else if(!param.name){
                    return ionicToast.show('店铺名称不能为空', 'middle', false, 2000);
                } else if(param.name.length > 10){
                    return ionicToast.show('很抱歉，店铺名称不能超过10个字', 'middle', false, 2000);
                } else if(!param.provinceName){
                    return ionicToast.show('地区不能为空', 'middle', false, 2000);
                } else if(!param.addres){
                    return ionicToast.show('门店地址不能为空', 'middle', false, 2000);
                } else if(!param.phone){
                    return ionicToast.show('门店电话不能为空', 'middle', false, 2000);
                }
                var re = /^[0-9]*]*$/;
                if(!re.test(param.phone)){
                    return ionicToast.show('请输入正确的门店电话', 'middle', false, 2000);
                } else if(!$scope.bank.name){
                    return ionicToast.show('开户人姓名不能为空', 'bottom', false, 2000);
                } else if(!$scope.bank.card){
                    return ionicToast.show('身份证号码不能为空', 'bottom', false, 2000);
                } else if(!$scope.bank.bankCard){
                    return ionicToast.show('银行卡号不能为空', 'bottom', false, 2000);
                } else if(!$scope.bank.bankAddress){
                    return ionicToast.show('开户行不能为空', 'bottom', false, 2000);
                } else if(!$scope.bank.bankName){
                    return ionicToast.show('所属银行不能为空', 'bottom', false, 2000);
                } else if(!$scope.bank.mobile){
                    return ionicToast.show('手机号码不能为空', 'bottom', false, 2000);
                } else{
                    doService.saveShopName({"param" : param}).then(
                        function(suc){
                            if(suc.code == 0){
                            	$scope.bank.shopId=$stateParams.shopId;
                                doService.saveOrUpdateBankMsg({param : $scope.bank}).then(
                                    function(suc){
                                        if(suc.code == 0){
                                            ionicToast.show('保存成功！', 'middle', false, 2000);
                                            //$state.go("home",{shopId : $stateParams.shopId});
                                            $state.go("login");
                                        } else{
                                            ionicToast.show(suc.msg, 'middle', false, 2000);
                                        }
                                    }, function(err){
                                        ionicToast.show(suc.msg, 'middle', false, 2000);
                                    });
                            } else{
                                ionicToast.show(suc.msg, 'middle', false, 2000);
                            }
                        },function(err){
                            console.log(err);
                        });
                }
            };

        }])
    //更多
	.controller('companyShowCtrl', ['$scope','$state','doService','$window','$rootScope','$stateParams',
	 function($scope,$state,doService,$window,$rootScope,$stateParams) {
        $scope.limit=6;
        $scope.start=0;
        $scope.showScroll=true;
        $scope.companyList=[];
        $scope.doRefresh=function(){
        	$scope.start=0;
        	$scope.showScroll=true;
			doService.getCompanyShow({start:$scope.start,limit:$scope.limit}).then(
					function(suc){
						if(suc==null||suc==''||suc.resultList==null||suc.resultList==''||suc.resultList.length<1){
							 $scope.companyDataT=true;
						}else{
							$scope.companyList=suc.resultList;
							$scope.companyDataT=false;
						}
                        if(suc==null||suc.resultList==null||suc.resultList.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
						console.log(suc);
					},function(err){
						$scope.companyDataT=false;
						console.log(err);
					}).finally(function () {
                        $scope.$broadcast('scroll.refreshComplete');
                    });
        }
        $scope.loadMore = function() {
            if($scope.companyList.length>0){
                $scope.start=$scope.start+$scope.limit;
            }
			doService.getCompanyShow({start:$scope.start,limit:$scope.limit}).then(
					function(suc){
						Array.prototype.push.apply($scope.companyList, suc.resultList);
						if($scope.companyList==null||$scope.companyList==''||$scope.companyList.length<1){
							 $scope.companyDataT=true;
						}else{
							$scope.companyList=suc.resultList;
							$scope.companyDataT=false;
						}
                        if(suc==null||suc.resultList==null||suc.resultList.length<$scope.limit){
                            $scope.showScroll=false;
                        }else{
                            $scope.showScroll=true;
                        }
						console.log(suc);
					},function(err){
						$scope.companyDataT=false;
						console.log(err);
					}).finally(function () {
                        setTimeout(function(){
                            $scope.$broadcast('scroll.infiniteScrollComplete');
                        },3000);
                    });
        }
        $scope.$on('$stateChangeSuccess', function() {
            $scope.loadMore();
        });
	}])
;
