// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic','ionic-ratings','ionic-toast','ionicLazyLoad','ymgc', 'starter.controllers', 'starter.services','starter.factories','ionic-citydata','ionic-citypicker', 'angular-md5'])

    .run(function($rootScope,$ionicHistory,$ionicPlatform,storage,$state){
        $rootScope.platform = ionic.Platform.platform();
        if(ionic.Platform.isIOS()){
            function connectWebViewJavascriptBridge(callback) {
                if (window.WebViewJavascriptBridge) {
                    callback(WebViewJavascriptBridge)
                } else {
                    document.addEventListener('WebViewJavascriptBridgeReady', function() {
                        callback(WebViewJavascriptBridge)
                    }, false)
                }
            }
            connectWebViewJavascriptBridge(function(native) {
                /* Init your app here */
                native.init(function(message, responseCallback) {
                    eval(message);
                    //alert('Received message: ' + message)
                    //if(message.Bar){
                    //    alert(message.Bar);
                    //}
                    //if (responseCallback) {
                    //    responseCallback("Right back atcha");
                    //}
                });
                window.native = native;
                native.send({action:"iosStart"},function(){});
            });

        }
        $rootScope.clearHistory=function(){
            $ionicHistory.clearHistory();
        };
        $rootScope.clearCache=function(){
            $ionicHistory.clearCache();
        };
        $rootScope.qiNiu = "http://o7o0uv2j1.bkt.clouddn.com/";
        $rootScope.myGoBack =function(){
            $ionicHistory.goBack();
        };
        window.goBack =function(){
            $rootScope.myGoBack();
        };
        $ionicPlatform.ready(function() {
            // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
            // for form inputs)
            /*if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
             cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
             cordova.plugins.Keyboard.disableScroll(true);
             }
             if (window.StatusBar) {
             // org.apache.cordova.statusbar required
             StatusBar.styleDefault();
             }*/
        });
    })

    .config(function($stateProvider, $urlRouterProvider,$ionicConfigProvider) {
        $ionicConfigProvider.platform.ios.tabs.style('standard');
        $ionicConfigProvider.platform.ios.tabs.position('bottom');
        $ionicConfigProvider.platform.android.tabs.style('standard');
        $ionicConfigProvider.platform.android.tabs.position('standard');
        $ionicConfigProvider.platform.ios.navBar.alignTitle('center');
        $ionicConfigProvider.platform.android.navBar.alignTitle('center');
        $ionicConfigProvider.platform.ios.backButton.previousTitleText('').icon('ion-ios-arrow-thin-left');
        $ionicConfigProvider.platform.android.backButton.previousTitleText('').icon('ion-android-arrow-back');
        $ionicConfigProvider.platform.ios.views.transition('ios');
        $ionicConfigProvider.platform.android.views.transition('android');
        //$ionicConfigProvider.views.maxCache(10);
        // Each state's controller can be found in controllers.js
        $stateProvider

            //首页
            .state('home',{
                url:'/home/{shopId}',
                templateUrl:"templates/home.html",
                controller:"homeCtrl"
            })
            //个人中心首页
            .state('personCenter',{
                url:'/personCenter/{shopId}',
                templateUrl:"templates/personal/personal-center.html",
                controller:"pcCtrl"
            })
            //个人信息修改
            .state('personInfo',{
                url:'/personInfo/{shopId}',
                templateUrl:"templates/personal/personal-info.html",
                controller:"pcInfoCtrl"
            })
            //编辑个人信息
            .state('editShopName',{
                url:'/editShopName/{shopId}/{columnVal}/{columnName}',
                templateUrl:"templates/personal/personal-info-edit.html",
                controller:"editShopNameCtrl"
            })
            //编辑个人信息-门店地址
            .state('editShopAddress',{
                url:'/editShopAddress/{shopId}/{province}/{city}/{country}/{address}',
                templateUrl:"templates/personal/personal-info-editShopAddress.html",
                controller:"editShopAddressController"
            })
            //活动部落
            .state('activityList',{
                url:'/activityList/{shopId}',
                templateUrl:"templates/activity/list.html",
                controller:"activitylistCtrl"
            })
            //活动报名列表
            .state('activitySignUp',{
                url:'/activitySignUp/{shopId}/{activityId}/{userId}',
                templateUrl:"templates/activity/activitySignUp.html",
                controller:"activitySignUpCtrl"
            })
            //活动评论列表
            .state('activityComment',{
                url:'/activityComment/{shopId}/{activityId}',
                templateUrl:"templates/activity/activityComment.html",
                controller:"activityCommentCtrl"
            })
            //创建活动
            .state('addActicity',{
                url:'/addActicity/{shopId}',
                templateUrl:"templates/activity/addActicity.html",
                controller:"addActicityCtrl"
            })
            //活动编辑
            .state('editActicity',{
                url:'/editActicity/{shopId}/{activityId}',
                templateUrl:"templates/activity/editActicity.html",
                controller:"editActicityCtrl"
            })
            //活动详情
            .state('acticityDetail',{
                url:'/acticityDetail/{shopId}/{activityId}',
                templateUrl:"templates/activity/detailActivity.html",
                controller:"detailActicityCtrl"
            })
            //活动详情-微信
            .state('weixinActivityDetail',{
                url:'/weixinActivityDetail/{activityId}/{shopId}/{userId}',
                templateUrl:"templates/activity/weixinDetailActivity.html",
                controller:"weixinActivityDetailCtrl"
            })
            //活动报名列表-微信
            .state('weixinActivitySignUp',{
                url:'/weixinActivitySignUp/{shopId}/{activityId}',
                templateUrl:"templates/activity/weixinActivitySignUp.html",
                controller:"weixinActivitySignUpCtrl"
            })
            //活动评论列表-微信
            .state('weixinActivityComment',{
                url:'/weixinActivityComment/{shopId}/{activityId}',
                templateUrl:"templates/activity/weixinActivityComment.html",
                controller:"weixinActivityCommentCtrl"
            })
            //店铺二维码
            .state('shopQrcode', {
                url: '/shop/qrcode/{shopId}',
                templateUrl: "templates/shopManage/qrcode.html",
                controller: "shopQrcodeCtrl"
            })
            //店铺管理-分类2016.6.29修改
            .state('shopGoodClassify',{
                url:'/shop/goodClassify/{shopId}',
                templateUrl:"templates/shopManage/good-classify.html",
                controller:"shopGoodClassifyCtrl"
            })
            //店铺管理
            .state('shopGoodList', {
                url: '/shop/goodList/{shopId}/{brandId}/{brandName}/{classifyId}/{classifyName}',
                templateUrl: "templates/shopManage/good-list.html",
                controller: "shopGoodListCtrl"
            })
            //批量操作
            .state('shopGoodManage', {
                url: '/shop/goodManage/{shopId}/{sortType}/{brandId}/{classifyId}/{goodName}',
                templateUrl: "templates/shopManage/good-manage.html",
                controller: "shopGoodManageCtrl"
            })
            //商品详情
            .state('shopGoodInfo', {
                url: '/shop/goodInfo/{shopId}/{goodId}',
                templateUrl: "templates/shopManage/good-info.html",
                controller: "shopGoodInfoCtrl"
            })
            //推广列表
            .state('shareList',{
                url:'/shareList/{shopId}',
                templateUrl:"templates/share/shareList.html",
                controller:"shareListCtrl",
                //cache:false
            })
            //个人中心-粉丝管理
            .state('fansList',{
                url:'/fansList/{shopId}',
                templateUrl:"templates/personal/fans_list.html",
                controller:"fansListCtrl"
            })
            //个人中心-创建的活动
            .state('activityPersonList',{
                url:'/activityPersonList/{shopId}/{statu}',
                templateUrl:"templates/personal/act_person_list.html",
                controller:"activityPersonListCtrl"
            })
            //个人中心-活动详情
            .state('activityPersonDetail',{
                url:'/activityPersonDetail/{shopId}/{activityId}',
                templateUrl:"templates/personal/act_person_detail.html",
                controller:"activityPersonDetailCtrl"
            })
            //个人中心-卡券兑换
            .state('vouchList',{
                url:'/vouchList/{shopId}',
                templateUrl:"templates/personal/vouch_list.html",
                controller:"vouchListCtrl"
            })
            //个人中心-卡券明细兑换
            .state('vouchDetailList',{
                url:'/vouchDetailList/{shopId}/{vocherId}/{statu}',
                templateUrl:"templates/personal/vouch_detail_list.html",
                controller:"vouchDetailListCtrl"
            })
            //个人中心-卡券搜索兑换
            .state('searchVouchList',{
                url:'/searchVouchList/{shopId}',
                templateUrl:"templates/personal/vouch_detail_sear_list.html",
                controller:"searchVouchListCtrl"
            })
            //个人中心-我的账户
            .state('accountMsg',{
                url:'/accountMsg/{shopId}',
                templateUrl:"templates/personal/account_msg.html",
                controller:"accountMsgCtrl"
            })
            //个人中心-我的账户-添加修改银行卡
            .state('buildBankMsg',{
                url:'/buildBankMsg/{shopId}/{bankId}',
                templateUrl:"templates/personal/account_bank_msg.html",
                controller:"buildBankMsgCtrl"
            })
            //个人中心-我的账户-店铺协议
            .state('getShopProtol',{
                url:'/getShopProtol/{shopId}',
                templateUrl:"templates/personal/shop_protocol.html",
                controller:"getShopProtolCtrl"
            })
            //推广详情
            .state('spreadDetail',{
                url:'/spreadDetail/{spreadId}/{shopId}/{url}',
                templateUrl:"templates/share/spread_detail.html",
                controller:"spreadDetailCtrl",
                //cache:false
            })
            //推广分享中间页
            .state('shareDetail',{
                url:'/shareDetail/{spreadId}/{shopId}/{goodId}',
                templateUrl:"templates/share/shareDetail.html",
                controller:"shareDetailCtrl",
                //cache:false
            })
            //学习乐园列表
            .state('studyList',{
                url:'/studyList/{shopId}',
                templateUrl:"templates/study/studyList.html",
                controller:"studyListCtrl",
            })
            //学习乐园詳情
            .state('studyDetail',{
                url:'/studyDetail/{studyId}/{shopId}',
                templateUrl:"templates/study/studyDetail.html",
                controller:"studyDetailCtrl",
                //cache:false
            })
            //设置
            .state('setting',{
                url:'/setting/{shopId}',
                templateUrl:"templates/personal/setting.html",
                controller:"settingCtrl"
            })
            //设置-修改密码
            .state('updatePassword',{
                url:'/updatePassword/{mobile}/{shopId}',
                templateUrl:"templates/personal/update-password.html",
                controller:"updatePasswordCtrl"
            })
            //设置-修改密码-下一步
            .state('updatePasswordNext',{
                url:'/updatePasswordNext/{mobile}/{verifyCode}/{shopId}',
                templateUrl:"templates/personal/update-password2.html",
                controller:"updatePasswordNextCtrl"
            })
            //意见反馈
            .state('opinion',{
                url:'/opinion/{bizId}/{shopId}/{mobile}',
                templateUrl:"templates/personal/opinion-add.html",
                controller:"opinionCtrl"
            })
            //关于我们
            .state('aboutUs',{
                url:'/aboutUs',
                templateUrl:"templates/personal/about-us.html",
                controller:"aboutUsCtrl"
            })
            //服务协议
            .state('serviceAgreement',{
                url:'/serviceAgreement',
                templateUrl:"templates/personal/service-agreement.html",
                controller:"serviceAgreementCtrl"
            })
            //我的订单列表
            .state('orderList',{
                url:'/orderList/{shopId}',
                templateUrl:"templates/personal/order-list.html",
                controller:"orderListCtrl"
            })
            //订单详情
            .state('orderInfo',{
                url:'/orderInfo/{orderId}',
                templateUrl:"templates/personal/order-info.html",
                controller:"orderInfoCtrl"
            })
            //登录
            .state('login',{
                url:'/login',
                templateUrl:'templates/login.html',
                controller:"loginCtrl"
            })
            //完善店铺信息
            .state('shopInfo',{
                url:'/shopInfo/{shopId}',
                templateUrl:'templates/personal/shop-info.html',
                controller:"shopInfoCtrl"
            })
            //更多
            .state('companyShow',{
            	url:'/more',
            	templateUrl:'templates/more/more.html',
            	controller:"companyShowCtrl"
            })
            .state('test', {
                url: '/test',
                templateUrl: "templates/modal/modal-test.html",
                controller: "ModalTestController"
            })
        // if none of the above states are matched, use this as the fallback
        $urlRouterProvider.otherwise('/home');

    });
