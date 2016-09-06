angular.module('starter.services', [])
.service('webService',['$http',function($http){
  this.do = function(url,params){
    console.log("url:"+url);
    //params = params == null ? '' : JSON.stringify(params);
    return $http({
      method : 'post',
      url : url,
      timeout : 8000,
      //params : {params:params}
        params:params
    });
  }
}])
.service('doService',['webService','$q','$ionicLoading',function(webService,$q,$ionicLoading){
//    var hostUrl="http://sp.xiangshanzx.com/shanguoyinyi";
//	  var hostUrl="http://test.cha2u.com/shanguoyinyi";
    var hostUrl="${server.address}";
//    var hostUrl="http://localhost:8080/shanguoyinyi";
    var getVerificationUrl=hostUrl+"/weixin/weixinConfig/verification.do";
    var loginUrl=hostUrl+"/mobile/user/login.do";
    var getVerificationUrl=hostUrl+"/weixin/weixinConfig/verification.do";
    var indexUrl=hostUrl+"/mobile/user/getOrderTotalMsg.do";
    var shopInfoUrl=hostUrl+"/mobile/store/subbranch/loadInfo.do";
    var getFansListUrl=hostUrl+"/mobile/fans/getFansList.do";
    var getVouchListUrl=hostUrl+"/mobile/voucher/getVoucherList.do";
    var getActivityListUrl=hostUrl+"/event/eventActivity/mobile/findEventActivityForMobile.do";
    var getActivitySignUpListUrl=hostUrl+"/event/eventActivitySignUp/mobile/eventActivityUserPage.do";
    var getActivityCommentListUrl=hostUrl+"/event/eventActivityComment/mobile/eventActivityCommentPage.do";
    var getActivityTypeListUrl=hostUrl+"/event/activityType/mobile/findEventActivityTypeListForMobile.do";
    var saveActivityDataUrl=hostUrl+"/event/eventActivity/mobile/saveForMobile.do";
    var getActivityDetailDataUrl=hostUrl+"/event/eventActivity/mobile/findEventActivityByIdForMobile.do";
    var activityPointUrl=hostUrl+"/event/eventActivityPoint/weixin/point.do";
    var activityCanclePointUrl=hostUrl+"/event/eventActivityPoint/weixin/cancelPoint.do";
    var activitySignUpUrl=hostUrl+"/event/eventActivitySignUp/weixin/signUp.do";
    var activityCommentUrl=hostUrl+"/event/eventActivityComment/weixin/comment.do";
    var getVoucherDetailListUrl=hostUrl+"/mobile/voucher/getVoucherDetailList.do";
    var setVoucherStatusUrl=hostUrl+"/mobile/voucher/setVoucherStatus.do";
    var getSearchVouchListUrl=hostUrl+"/mobile/voucher/searchVoucherDetailList.do";
    var getDeleteActByIdUrl=hostUrl+"/event/eventActivity/mobile/delete.do";
    var getChangeActStatusUrl=hostUrl+"/event/eventActivity/mobile/stop.do";
    var spreadClassifyUrl=hostUrl+"/mobile/findAllSpreadClassifyOnStatus.do";
    var spreadListUrl=hostUrl+"/mobile/getSpreadList.do";
    var saveShopNameUrl=hostUrl+"/mobile/user/updateUserMsg.do";
    var spreadDetailUrl=hostUrl+"/mobile/getSpreadDetail.do";
    var getVerifyPhoneUrl = hostUrl + "/mobile/user/verifyPhone.do";
    var getResetPasswordUrl = hostUrl + "/mobile/user/resetPassword.do";
    var getStudyListUrl=hostUrl+"/event/onlineStudy/mobile/findAllForMobile.do";
    var studyDetailUrl=hostUrl+"/event/onlineStudy/mobile/findOneById.do";
    var addOpinionUrl=hostUrl + "/module/opinion/mobile/add.do";
    var aboutUsUrl = hostUrl + "/module/text/mobile/our.do";
    var myUrl = hostUrl + "/module/text/mobile/register.do";
    var getOrderListUrl = hostUrl + "/deal/indent/mobile/getShopOrderList.do";
    var getOrderDetailUrl = hostUrl + "/deal/indent/mobile/getOrderDetail.do";
    var getAccountMsgUrl = hostUrl + "/mobile/user/getUserMsg.do";
    var getAccountBankMsgUrl = hostUrl + "/mobile/user/getBankCardList.do";
    var saveOrUpdateBankMsgUrl = hostUrl + "/mobile/user/saveBankMsg.do";
    var getBankCardObjUrl = hostUrl + "/mobile/user/getBankCardObj.do";
    var getShopProtolTextUrl = hostUrl + "/mobile/shop/getShopProtocol.do";
    var getCompanyShowUrl = hostUrl + "/mobile/getMoreColumnImg.do";
    var lunboUrl = hostUrl + "/weixin/common/getImg.do?category=0";
    var classifyColumnUrl = hostUrl + "/stock/classifyColumn/mobile/list.do";
    var brandList = hostUrl + "/weixin/cargo/brand/findAll.do";

    //获取商品列表
    var getGoodList = hostUrl+"/mobile/good/getGoodList.do";
    //商品上架
    var goodPutaway = hostUrl+"/mobile/good/setGoodShelve.do";
    //商品下架
    var goodSoldOut = hostUrl+"/mobile/good/setGoodOutShelve.do";
    //商品详情
    var getGoodUrl = hostUrl+"/mobile/good/getGoodDetail.do";
    //店铺信息
    var getShareShopInfoUrl = hostUrl+"/mobile/selectSubbranchByPrimaryKey.do";
    //学习乐园一级分类
    var getStudyFirstClassifyUrl=hostUrl+"/event/onlineStudyType/mobile/getMobileParentList.do";
    //学习乐园二级分类
    var getStudySecondClassifyUrl=hostUrl+"/event/onlineStudyType/mobile/getMobileListByParentId.do";

    //获取hostURl
    this.getHostUrl=function(){
      return hostUrl;
    }

    //店铺轮播广告
    this.getLunbo=function(params){
        var deferred = $q.defer();
        webService.do(lunboUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //店铺分类栏目
    this.getClassifyColumnList=function(params){
        var deferred = $q.defer();
        webService.do(classifyColumnUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //店铺品牌列表
    this.getBrandList=function(params){
        var deferred = $q.defer();
        webService.do(brandList,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //微信地址授权
    this.weixinVerification=function(params){
        var deferred = $q.defer();
        webService.do(getVerificationUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //微信活动分享
    this.weixinShare=function(verificationData,shareTitle,shareUrl,shareImage){
        wx.config({
            debug: true,
            appId: verificationData.appId,
            timestamp: verificationData.timestamp,
            nonceStr:verificationData.nonceStr,
            signature: verificationData.signature,
            jsApiList: [
                "onMenuShareTimeline",
                "onMenuShareAppMessage"
            ]
        });
        wx.ready(function(){
            wx.onMenuShareTimeline({
                title: shareTitle, // 分享标题
                link: hostUrl+"/weixin/weixinClient/weixinEventIndex.do?"+shareUrl, // 分享链接
                imgUrl:shareImage==null||shareImage==''?hostUrl+'/www/img/detail-default.jpg':shareImage, // 分享图标
                success: function () {
                   alert("分享成功");
                },
                cancel: function () {
                    alert("分享失败");
                }
            });

            wx.onMenuShareAppMessage({
                title: shareTitle, // 分享标题
                desc: '', // 分享描述
                link: hostUrl+"/weixin/weixinClient/weixinEventIndex.do?"+shareUrl, // 分享链接
                imgUrl:shareImage==null||shareImage==''?hostUrl+'/www/img/detail-default.jpg':shareImage, // 分享图标填默认为link
                type: '', // 分享类型,music、video或link，不填默认为link
                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                success: function () {
                    alert("分享成功");
                },
                cancel: function () {
                    alert("分享失败");
                }
            });
        });
        wx.error(function(res){
            alert(res);
        });
    };

    //APP登录
    this.loginDo=function(params){
        var deferred = $q.defer();
        webService.do(loginUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //微信地址授权
    this.weixinVerification=function(params){
        var deferred = $q.defer();
        webService.do(getVerificationUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //微信活动分享
    this.weixinShare=function(verificationData,shareTitle,shareUrl,shareImage){
        wx.config({
            debug: false,
            appId: verificationData.appId,
            timestamp: verificationData.timestamp,
            nonceStr:verificationData.nonceStr,
            signature: verificationData.signature,
            jsApiList: [
                "onMenuShareTimeline",
                "onMenuShareAppMessage"
            ]
        });
        wx.ready(function(){
            wx.onMenuShareTimeline({
                title: shareTitle, // 分享标题
                link: hostUrl+"/weixin/weixinClient/weixinEventIndex.do?"+shareUrl, // 分享链接
                imgUrl:shareImage, // 分享图标
                success: function () {
                   alert("分享成功");
                },
                cancel: function () {
                    alert("分享失败");
                }
            });

            wx.onMenuShareAppMessage({
                title: shareTitle, // 分享标题
                desc: '', // 分享描述
                link: hostUrl+"/weixin/weixinClient/weixinEventIndex.do?"+shareUrl, // 分享链接
                imgUrl:shareImage, // 分享图标
                type: '', // 分享类型,music、video或link，不填默认为link
                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                success: function () {
                    alert("分享成功");
                },
                cancel: function () {
                    alert("分享失败");
                }
            });
        });
        wx.error(function(res){

        });
    };
    //获取商品
    this.getGoodById=function(params){
        var deferred = $q.defer();
        webService.do(getGoodUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //获取商品列表
    this.getShopGoodList=function(params){
        var deferred = $q.defer();
        webService.do(getGoodList,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //商品操作(status 0:下架 1上架)
    this.operateGood=function(status,params){
        var url = status == 0?goodSoldOut:goodPutaway;
        var deferred = $q.defer();
        $ionicLoading.show({
            content: 'Loading',
            animation: 'fade-in',
            showBackdrop: true,
            maxWidth: 200,
            showDelay: 0
        });
        setTimeout(function(){
            webService.do(url,params).success(function(data){
                deferred.resolve(data);
            }).error( function(data,status){
                deferred.reject(status);
            }).finally(function(){
                $ionicLoading.hide();
            });
        },500);
        return deferred.promise;
    };
    //获取首页订单汇总
    this.getTotalOrderData=function(params){
        var deferred = $q.defer();
        webService.do(indexUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //获取获取登录店铺信息
    this.getShopInfo=function(params){
        var deferred = $q.defer();
        webService.do(shopInfoUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //获取粉丝列表
    this.getFansList=function(params){
    	var deferred = $q.defer();
    	webService.do(getFansListUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //获取兑换券列表
    this.getVouchList=function(params){
    	var deferred = $q.defer();
    	webService.do(getVouchListUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };

    //获取兑换券明细列表
    this.getVoucherDetailList=function(params){
    	var deferred = $q.defer();
    	webService.do(getVoucherDetailListUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //设置卡券的禁用
    this.setVoucherStatus=function(params){
    	var deferred = $q.defer();
    	webService.do(setVoucherStatusUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //搜索卡券
    this.getSearchVouchList=function(params){
    	var deferred = $q.defer();
    	webService.do(getSearchVouchListUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //获取活动部落列表
    this.geActivityListData=function(params){
    	var deferred = $q.defer();
    	webService.do(getActivityListUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //获取活动部落报名列表
    this.geActivitySignUpListData=function(params){
    	var deferred = $q.defer();
    	webService.do(getActivitySignUpListUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //获取活动部落评论列表
    this.geActivityCommentListData=function(params){
    	var deferred = $q.defer();
    	webService.do(getActivityCommentListUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //获取活动分类列表
    this.getActivityTypeListData=function(params){
    	var deferred = $q.defer();
    	webService.do(getActivityTypeListUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //保存活动信息
    this.saveActivityData=function(params){
    	var deferred = $q.defer();
    	webService.do(saveActivityDataUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //获取活动信息详情
    this.getActivityDetailData=function(params){
    	var deferred = $q.defer();
    	webService.do(getActivityDetailDataUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //活动点赞
    this.activityPoint=function(params){
    	var deferred = $q.defer();
    	webService.do(activityPointUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //活动取消点赞
    this.activityCanclePoint=function(params){
    	var deferred = $q.defer();
    	webService.do(activityCanclePointUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //活动报名
    this.activitySignUp=function(params){
    	var deferred = $q.defer();
    	webService.do(activitySignUpUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //活动评论
    this.activityComment=function(params){
    	var deferred = $q.defer();
    	webService.do(activityCommentUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };

    //获取推广分类
    this.getSpreadClassify=function(params){
        var deferred = $q.defer();
        webService.do(spreadClassifyUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //获取推广列表
    this.getSpreadList=function(params){
        var deferred = $q.defer();
        webService.do(spreadListUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //保存个人信息
    this.saveShopName=function(params){
        var deferred = $q.defer();
        webService.do(saveShopNameUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //读取城市信息
    this.readCityData = function(){
        var deferred = $q.defer();
        webService.do('lib/cityData.json').success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //获取推广详情
    this.getSpreadDetail=function(params){
        var deferred = $q.defer();
        webService.do(spreadDetailUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //个人中心-活动删除
    this.deleteActById=function(params){
    	var deferred = $q.defer();
    	webService.do(getDeleteActByIdUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //个人中心-活动禁用
    this.changeActStatus=function(params){
    	var deferred = $q.defer();
    	webService.do(getChangeActStatusUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //个人中心-我的账户
    this.getAccountMsg=function(params){
    	var deferred = $q.defer();
    	webService.do(getAccountMsgUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //个人中心-查询银行卡
    this.getAccountBankMsg=function(params){
    	var deferred = $q.defer();
    	webService.do(getAccountBankMsgUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //个人中心-查询银行卡根据Id
    this.getBankCardObj=function(params){
    	var deferred = $q.defer();
    	webService.do(getBankCardObjUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //个人中心-修改添加银行卡
    this.saveOrUpdateBankMsg=function(params){
    	var deferred = $q.defer();
    	webService.do(saveOrUpdateBankMsgUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
    //个人中心-查看店铺协议
    this.getShopProtolText=function(params){
    	var deferred = $q.defer();
    	webService.do(getShopProtolTextUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };

    //发送短信验证码
    this.getVerifyPhone = function(params){
        var deferred = $q.defer();
        webService.do(getVerifyPhoneUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    this.addOpinionDo = function(params){
        var deferred = $q.defer();
        webService.do(addOpinionUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //关于我们
    this.aboutUsDo = function(params){
        var deferred = $q.defer();
        webService.do(aboutUsUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //服务协议
    this.serviceAgreement = function(params){
        var deferred = $q.defer();
        webService.do(myUrl,params).success(function(data){
            deferred.resolve(data);
        }).error(function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //修改密码
    this.getResetPassword = function(params){
        var deferred = $q.defer();
        webService.do(getResetPasswordUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //查询订单列表
    this.getOrderList = function(params){
        var deferred = $q.defer();
        webService.do(getOrderListUrl, params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //查询订单列表-详情
    this.getOrderDetail = function(params){
        var deferred = $q.defer();
        webService.do(getOrderDetailUrl, params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //学习乐园列表
    this.getStudyList = function(params){
        var deferred = $q.defer();
        webService.do(getStudyListUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //获取学习乐园详情
    this.getStudyDetail=function(params){
        var deferred = $q.defer();
        webService.do(studyDetailUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //学习乐园一级分类
    this.getStudyFirstClassify=function(params){
        var deferred = $q.defer();
        webService.do(getStudyFirstClassifyUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //学习乐园二级分类
    this.getStudySecondClassify=function(params){
        var deferred = $q.defer();
        webService.do(getStudySecondClassifyUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };

    //获取店铺信息
    this.getShareShopInfo=function(params){
        var deferred = $q.defer();
        webService.do(getShareShopInfoUrl,params).success(function(data){
            deferred.resolve(data);
        }).error( function(data,status){
            deferred.reject(status);
        });
        return deferred.promise;
    };
    //更多
    this.getCompanyShow=function(params){
    	var deferred = $q.defer();
    	webService.do(getCompanyShowUrl,params).success(function(data){
    		deferred.resolve(data);
    	}).error( function(data,status){
    		deferred.reject(status);
    	});
    	return deferred.promise;
    };
}]);
angular.module('starter.factories', [])
    .factory('storage',[function(){
        return {
            get : function(key){
                return angular.fromJson(localStorage.getItem(key));
            },
            put : function(key,value){
                value = typeof value == 'object' ? JSON.stringify(value) : value;
                localStorage.setItem(key,value);
				//alert(localStorage);
            },
            remove : function(key){
                localStorage.removeItem(key);
            }
        }
    }])
    .factory('PopupCustom',['$ionicPopup','$timeout',function($ionicPopup,$timeout){
        var alertPopup=function($scope, options){
            options.cssClass="alert-css";
            var popup = $ionicPopup.show(options);
            $scope.$on("$ionicView.beforeLeave", function(event, data){
                if(popup)
                    popup.close();
            });
            return popup;

        };
        var toastPopup=function(options,times){
            options.cssClass="toast-css";
            var toast=$ionicPopup.show(options);
            $timeout(function() {
                toast.close(); //close the popup after 3 seconds for some reason
             }, times);
        };
        return {
            alertPopup:alertPopup,
            toastPopup:toastPopup
        }
    }])
    .factory('ModalShare', function ($ionicModal) {
        /**
         * data:
         *      sharedClose: true
         */
        var initModal = function ($scope, data) {
            data = data || {};
            data.sharedClose = data.sharedClose===false ? false : true;
            var _title, _url, _content, _imageUrl;
            $scope._sharelist = [{
                id: 1,
                name: '微信',
                img: 'img/fx_wx.png',
                callback: function(title, url,content,imageUrl){
                    if(ionic.Platform.isAndroid()){
                        webapp.wxShareJS(title, content, url, imageUrl);
                    }else if(ionic.Platform.isIOS()){
                        if(native)
                            native.send({
                                action:'wxShareJS',
                                title: title,
                                content: content,
                                url: url,
                                imageUrl: imageUrl
                            },function(){});
                    }
                }
            }, {
                id: 2,
                name: '朋友圈',
                img: 'img/fx_pyq.png',
                callback: function(title, url,content,imageUrl){
                    if(ionic.Platform.isAndroid()){
                        webapp.wxShareCircleJS(title, content, url, imageUrl);
                    }else if(ionic.Platform.isIOS()){
                        if(native)
                            native.send({
                                action:'wxShareCircleJS',
                                title: title,
                                content: content,
                                url: url,
                                imageUrl: imageUrl
                            },function(){});
                    }
                }
            }];
            var modal = $ionicModal.fromTemplateUrl('templates/modal/modal-share.html',{
                scope:$scope,
                animation:'slide-in-up'
            }).then(function (modal) {
                $scope.shareModal = modal;
                return modal
            });
            modal.show = function (title, url, content, imageUrl) {
                _title = title;
                _url = url;
                _content = content||"";
                _imageUrl = imageUrl||"";
                if(!$scope.shareModal.isShown())
                    $scope.shareModal.show();
            };
            $scope.openShareModal = function(title, url, content, imageUrl){
                modal.show(title, url, content, imageUrl);
            };
            $scope._share = function(item) {
                if(item.callback)
                    item.callback(_title, _url, _content, _imageUrl);
                if(data.sharedClose)
                    $scope.shareModal.hide();
            };
            $scope.closeShareModal = function () {
                $scope.shareModal.hide();
            };
            $scope.removeShareModal = function () {
                $scope.shareModal.remove();
            };
            $scope.$on('$destroy', function () {
                $scope.shareModal.remove();
            });
            $scope.$on("$ionicView.beforeLeave", function(event, data){
                if($scope.shareModal)
                    $scope.shareModal.hide();
            });
            return modal;
        };
        return {
            initModal : initModal
        }
    })
    .factory('ModalTabMenu', function ($ionicPopover) {
        /**
         * data:
         *      name,
         *      open,
         *      close
         */
        var initModal = function ($scope, data) {
            data = data || {};
            data.data = data.data || [];
            var deep = data.deep || 1;
            var selected = [];
            var menu;

            var style = function(index){
                return {
                    width: 100/deep+"%",
                    //left: 100/deep*index+"%",
                    'background-color': index%2==0?'#f5f5f5':'#ffffff'
                }
            }
            var itemStyle = function(item, panelMark){
                var result = {color: item.selected?'#f55':'#000'};
                if(item.selected)
                    result['background-color'] = '#ffffff';
                return result;
            }

            var select = function(item, panelMark, autoClose) {
                autoClose = autoClose===false?false:true;
                // 清除已选择
                for(var i=selected.length-1; i>=panelMark; i--) {
                    if(selected[i]) {
                        selected[i].selected = undefined;
                        selected[i] = undefined;
                    }
                }
                // 删除以后级联
                for(var i=panelMark+1; i<menu.panel.length; i++)
                    if(menu.panel[panelMark+1])
                        menu.panel[panelMark+1] = undefined;
                // 选择当前
                item.selected = true;
                selected[panelMark] = item;
                // 下级级联
                if(item.children)
                    if((menu.panel[panelMark+1]=item.children).length>0)
                        select(item.children[0], panelMark+1, false);
                if(panelMark==deep-1 && autoClose)
                    $scope[data.name].hide();
            }

            var getSelectItem = function(){
                for(var i=selected.length-1; i>=0; i--) {
                    if(selected[i].id<0)
                        continue;
                    return selected[i];
                }
            }

            var setSelectItem = function(selectId) {
                var target, target2;
                OUT:
                for(var i=0; i<menu.data.length; i++) {
                    if(menu.data[i].id==selectId) {
                        target = menu.data[i];
                        break OUT;
                    }
                    if(menu.data[i].children) {
                        for(var j=0; j<menu.data[i].children.length; j++)
                            if(menu.data[i].children[j].id==selectId) {
                                target = menu.data[i];
                                target2 = menu.data[i].children[j];
                                break OUT;
                            }
                    }
                }
                if(target)
                    select(target, 0, target2?false:true);
                if(target2)
                    select(target2, 1);
            }

            var init = function(tabMenu) {
                menu = tabMenu;
                $scope[data.name] = tabMenu;
                tabMenu.data = data.data;
                tabMenu.panel = [tabMenu.data];
                tabMenu.style = style;
                tabMenu.itemStyle = itemStyle;
                tabMenu.select = select;

                for(var i=0; i<tabMenu.data.length; i++)
                    if(tabMenu.data[i].children) {
                        for(var j=0; j<tabMenu.data[i].children.length; j++)
                            tabMenu.data[i].children[j].parent = tabMenu.data[i];
                        deep = 2;
                    }
                tabMenu.deep = deep;
                if(deep==1)
                    tabMenu.panel = [tabMenu.data];
                else
                    tabMenu.panel = [tabMenu.data, []];
                setSelectItem(data.selected || 0);
            }
            init($ionicPopover.fromTemplate(
                '<style>'+
                    'ion-popover-view.popover-tab-menu {'+
                        'margin-top: 0;'+
                        'width: 100%;'+
                        'left: 0 !important;'+
                        'border-radius: 0 !important;'+
                        'height: auto;'+
                    '}'+
                    'ion-popover-view.popover-tab-menu > .popover-arrow {'+
                        'display: none;'+
                    '}'+
                    'ion-popover-view.popover-tab-menu > ion-content {'+
                        'right: initial;'+
                        'margin: 0 !important;'+
                        'border-radius: 0 !important;'+
                        'max-height: 13.7rem;'+
                        'height: auto;'+
                        'position: relative;'+
                        'float: left;'+
                    '}'+
                    'ion-popover-view.popover-tab-menu > ion-content ._item {'+
                        'font-size: 0.7rem;'+
                        'padding: 0 1rem;'+
                        'height: 1.8rem;'+
                        'line-height: 1.8rem;'+
                    '}'+
                '</style>'+
                '<ion-popover-view class="popover-tab-menu">'+
                    '<ion-content ng-style="'+data.name+'.style($index)" ng-repeat="_list in '+data.name+'.panel">'+
                        '<div style="display: none;" ng-bind="_panelMark=$index"></div>'+
                        '<div class="_item" ng-repeat="_item in _list track by $index" ng-style="'+data.name+'.itemStyle(_item, _panelMark)" ng-click="'+data.name+'.select(_item, _panelMark);">'+
                            '{{_item.name}}'+
                        '</div>'+
                    '</ion-content>'+
                '</ion-popover-view>'
              ,{
                scope: $scope
               }));
            $scope[data.open] = function ($event) {
                $scope[data.name].show($event);
            };
            $scope[data.close] = function ($event, params) {
                if($scope[data.name].isShown())
                    $scope[data.name].hide();
            };
            $scope.$on('popover.hidden', function(obj, target) {
                if(target==menu && menu.onselect)
                    menu.onselect(getSelectItem());
            });
            $scope.$on('$destroy', function (obj, target) {
                if(target==$scope[data.name])
                    $scope[data.name].remove();
            });
            $scope.$on("$ionicView.beforeLeave", function(event, d){
                if($scope[data.name])
                    $scope[data.name].hide();
            });

            return {
                selected: getSelectItem,
                setSelectItem: function(selectId){
                    menu.isToggle = false;
                    setTimeout(setSelectItem, 0, selectId);
                }
            }
        };
        return {
            initModal : initModal
        }
    })
;
angular.module('ymgc',[])
    .directive('errSrc',[function(){
        return{
            link:function(scope,element,attrs){
                if(attrs.ngSrc=='' || attrs.ngSrc== undefined){
                    attrs.$set('src',attrs.errSrc);
                }
                element.bind('error',function(){
                   // if(attrs.src != attrs.errSrc){
                       attrs.$set('src',attrs.errSrc);
                   // }
                });
            }
        }
    }])
    .directive("wminfo",[function () {
        return {
            restrict: 'AE',
            replace: true,
            scope: {
                infostr: '@',
                size: '@',
                color: '@'
            },
            template: "<div id='qrcanvas'></div>",
            link: function (scope, element, attrs) {
                (function ($) {
                    var q = element[0];
                    var draw = function () {
                        var colorIn = '#000000';//'#191970';
                        var colorOut = '#000000';//'#cd5c5c';
                        var colorFore = scope.color;
                        var options = {
                            cellSize: Number(scope.size),
                            foreground: [
                                // 背景颜色
                                { style: colorFore },
                                // outer squares of the positioner
                                { row: 0, rows: 7, col: 0, cols: 7, style: colorOut },
                                { row: -7, rows: 7, col: 0, cols: 7, style: colorOut },
                                { row: 0, rows: 7, col: -7, cols: 7, style: colorOut },
                                // inner squares of the positioner
                                { row: 2, rows: 3, col: 2, cols: 3, style: colorIn },
                                { row: -5, rows: 3, col: 2, cols: 3, style: colorIn },
                                { row: 2, rows: 3, col: -5, cols: 3, style: colorIn }
                            ],
                            data: scope.infostr,
                            typeNumber: 1
                        };
                        q.innerHTML = '';
                        q.appendChild(qrgen.canvas(options));
                    };
                    draw();//自动调用画图方法
                })(document.querySelector.bind(document));
            }
        };
    }])
    .directive("wmPic",['$compile',function ($compile) {
        return {
            restrict: 'AE',
            replace: true,
            scope: {
                infostr: '@',
                size: '@',
                color: '@'
            },
            template: "<div id='qrcanvas'></div>",
            link: function (scope, element, attrs) {
                (function ($) {
                    var q = element[0];
                    var draw = function () {
                        var colorIn = '#000000';//'#191970';
                        var colorOut = '#000000';//'#cd5c5c';
                        var colorFore = scope.color;
                        var options = {
                            cellSize: Number(scope.size),
                            foreground: [
                                // 背景颜色
                                { style: colorFore },
                                // outer squares of the positioner
                                { row: 0, rows: 7, col: 0, cols: 7, style: colorOut },
                                { row: -7, rows: 7, col: 0, cols: 7, style: colorOut },
                                { row: 0, rows: 7, col: -7, cols: 7, style: colorOut },
                                // inner squares of the positioner
                                { row: 2, rows: 3, col: 2, cols: 3, style: colorIn },
                                { row: -5, rows: 3, col: 2, cols: 3, style: colorIn },
                                { row: 2, rows: 3, col: -5, cols: 3, style: colorIn }
                            ],
                            data: scope.infostr,
                            typeNumber: 1
                        };
                        q.innerHTML = '';
                        var imgage=qrgen.canvas(options).toDataURL();
                        var test = '<img src="'+imgage+'" style="width:100%;">';
                        element.html(test);
                        console.log(imgage);
                        // q.appendChild(qrgen.canvas(options));
                    };
                    draw();//自动调用画图方法
                })(document.querySelector.bind(document));
            }
        };
    }])
	.directive('ymTabMenu', [function() {
        return {
            restrict: 'AE',
            replace: true,
            template:
                '<div class="ym-tab-menu">'+
                    '<div class="ytm-item" ng-repeat="item in data" ng-style="item.style" ng-click="_itemToggle(item, $event);">'+
                        '<div class="ytm-name">{{item.name}}</div>'+
                        '<div ng-show="item.icon" class="ytm-icon">'+
                            '<img ng-src="{{item.icon}}" />'+
                        '</div>'+
                    '</div>'+
                '</div>',
            scope: {
                /**
                 *  data: [{
                 *      name: string
                 *      scope?: $scope
                 *      tabMenu?: string
                 *      onselect?: function
                 *      toggles?: [{
                 *          style?: {},
                 *          icon?: string,
                 *          callback?: function,
                 *          param?: {}
                 *      }, ...]
                 *  }, ...]
                 */
                data:'=data'
                //onselect:'=onselect'
            },
            link: function(scope, element, attrs) {
                scope.data = scope.data || [];
                angular.forEach(scope.data, function(item){
                    if(item && item.toggles && item.toggles.length>0) {
                        var toggle = item.toggles[item.index=0];
                        item.style = toggle.style || {};
                        item.icon = toggle.icon || "";
                        item.params = toggle.params || {};
                    }
                    if(item && item.scope && item.scope[item.tabMenu]) {
                        item.scope[item.tabMenu].onselect = function(selectItem){
                            if(selectItem && item.params && item.params.setData!==false)
                                item.name = selectItem.name;
                            if(item.scope[item.tabMenu].isToggle !== false)
                                scope._itemToggle(item);
                            else
                                item.scope[item.tabMenu].isToggle = true;
                            if(item.onselect)
                                item.onselect(selectItem);
                        }
                    }
                });
                scope._itemToggle = function(item, $event){
                    if(item && item.toggles && item.toggles.length>0) {
                        var toggle = item.toggles[item.index=(item.index+1)%item.toggles.length];
                        item.style = toggle.style || {};
                        item.icon = toggle.icon || "";
                        item.params = toggle.params || {};
                        if(toggle.callback && $event)
                            toggle.callback($event, item.params);
                        //if(item.scope && item.tabMenu && item.scope[item.tabMenu] && !item.tabMenuHidden) {
                        //    item.scope.$on('popover.hidden', item.tabMenuHidden=function(obj, target) {
                        //        if(target==item.scope[item.tabMenu])
                        //            scope._itemToggle($event, item, false);
                        //    });
                        //}
                    }
                }
            }
        }
    }])
    .directive("countdown",['$interval',function($interval){
        return {
            restrict: 'AE',
            replace: true,
            scope: {
                startDate: '@',
                endDate: '@',
                templateClass: '@'
            },
            template:'<div class="{{templateClass}}">'+
                            '<div ng-show="unStart" class="un-start" >'+
                                 '<span class="date-text">距开始:</span>'+
                                 '<span class="date-block">{{showDateTime.hours}}</span>'+
                                 '<span class="date-split">:</span>'+
                                 '<span class="date-block">{{showDateTime.minutes}}</span>'+
                                 '<span class="date-split">:</span>'+
                                 '<span class="date-block">{{showDateTime.seconds}}</span>'+
                            '</div>'+
                            '<div ng-show="onSales" class="on-sales">'+
                                 '<span class="date-text">距结束:</span>'+
                                 '<span class="date-block">{{showDateTime.hours}}</span>'+
                                 '<span class="date-split">:</span>'+
                                 '<span class="date-block">{{showDateTime.minutes}}</span>'+
                                 '<span class="date-split">:</span>'+
                                 '<span class="date-block">{{showDateTime.seconds}}</span>'+
                            '</div>'+
                            '<div ng-show="endSales" class="end-sales" >活动已结束</div>'+
                          '</div>',
            link: function (scope, element, attrs) {
                var startDate ,endDate,timer;
                scope.unStart=false;
                scope.onSales=false;
                scope.endSales=false;
                var isShouldTimer=false;
                var buZero=function(val){
                    if(val<10)
                        return '0'+val;
                    return val;
                };
                var dateLevel=function(startDate,endDate){
                    //时间差的毫秒数
                    var dateTotal=endDate.getTime()-startDate.getTime();
                    //计算出相差天数
                    var days=Math.floor(dateTotal/(24*3600*1000));
                    //计算出小时数
                    var leave1=dateTotal%(24*3600*1000);    //计算天数后剩余的毫秒数
                    var hours=Math.floor(leave1/(3600*1000))+days*24;//计算相差分钟数
                    var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
                    var minutes=Math.floor(leave2/(60*1000));//计算相差秒数
                    var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
                    var seconds=Math.round(leave3/1000);
                    var timeObj={
                        hours:buZero(hours),
                        minutes:buZero(minutes),
                        seconds:buZero(seconds)
                    };
                    return timeObj;
                };

                var date =new Date();
                if(scope.endDate){
                	   var dateEndFormat=scope.endDate.replace(/\-/g,"/");
                    endDate=new Date(dateEndFormat);
                    if(Date.parse(date) > Date.parse(endDate)){
                        scope.unStart=false;
                        scope.onSales=false;
                        scope.endSales=true;
                        isShouldTimer=false;
                    }else{
                        isShouldTimer=true;
                    }
                }
                if(scope.startDate){
                	  var dateFormat=scope.startDate.replace(/\-/g,"/");
                    startDate=new Date(dateFormat);
                }
                if(startDate&&endDate&&isShouldTimer){
                    timer=$interval(function () {
                        var curDate =new Date();
                        if(Date.parse(curDate) > Date.parse(startDate)&&Date.parse(curDate) < Date.parse(endDate)){
                            scope.unStart=false;
                            scope.onSales=true;
                            scope.endSales=false;
                            scope.showDateTime=dateLevel(curDate,endDate);
                        }else if(Date.parse(curDate) < Date.parse(startDate)){
                            scope.unStart=true;
                            scope.onSales=false;
                            scope.endSales=false;
                            scope.showDateTime=dateLevel(curDate,startDate);
                        }else if(Date.parse(curDate) >  Date.parse(endDate)){
                            scope.unStart=false;
                            scope.onSales=false;
                            scope.endSales=true;
                            $interval.cancel(timer);
                        }
                    }, 1000);
                }
                scope.$on('$destroy', function () {
                    timer&&$interval.cancel(timer);
                });
            }
        };
    }])
    .directive('ymSearch', [function() {
        return {
            restrict: 'AE',
            replace: true,
            template:
                '<div class="ym-search" ng-style="style()">'+
                    '<div class="ysb-btn-o" ng-if="show(0);" ng-click="open();">' +
                        '<img ng-if="data.icon" ng-src="{{data.icon}}" />{{data.text}}'+
                    '</div>'+
                    '<div class="ysb-open" ng-if="show(1);">' +
                        '<img class="ysb-open-icon-search" ng-if="data.iconOpen" ng-src="{{data.iconOpen}}" ng-click="search();" />'+
                        '<input class="ysb-open-input" placeholder="{{data.placeholder}}" type="text" ng-model="data.value" ng-keypress="change($event);" />' +
                        '<img class="ysb-open-icon-clear" ng-src="img/home_yxdc.png" ng-click="clear();" />'+
                        '<div class="ysb-btn-c" ng-click="close();">' +
                            '取消' +
                        '</div>'+
                    '</div>'+
                '</div>',
            scope: {
                data: "=data"
            },
            link: function(scope, element, attrs) {
                scope.data.text = scope.data.text || "";
                /** status: 1-close, 2-open, 4-slide-in */
                scope.status = 1;

                scope.style = function(){
                    switch(scope.status){
                        case 1: {
                            return {
                                display: "inline-block"
                            }
                        }
                        case 2: {
                            return {
                                width: "100%",
                                position: "absolute",
                                top: "0",
                                left: "0",
                                height: "inherit",
                                "z-index": "1000"
                            }
                        }
                        case 4: {
                            return {
                                width: "100%",
                                position: "absolute",
                                top: "0",
                                left: "100%"
                            }
                        }
                    }
                };

                scope.show = function(type){
                    return (scope.status+type)%2==1;
                };

                scope.open = function(){
                    scope.status = 4;
                    setTimeout(function(){
                        scope.status = 2;
                    }, 0);
                };

                scope.close = function(){
                    scope.status = 1;
                    if(scope.data.autoClear===true)
                        scope.data.value="";
                    if(scope.data.onclose)
                        scope.data.onclose(scope.data.value);
                };

                scope.clear = function(){
                    scope.data.value = "";
                }

                scope.change = function($event){
                    if($event && ($event.charCode==13 || $event.code=='Enter'))
                        scope.search();
                }

                scope.search = function(){
                    if(scope.data.callback)
                        scope.data.callback(scope.data.value);
                }
            }
        }
    }])
;


