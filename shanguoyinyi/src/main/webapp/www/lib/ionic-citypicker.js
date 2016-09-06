"use strict";
var app = angular.module('ionic-citypicker', ['ionic']);
app.directive('ionicCityPicker', ['$ionicPopup', '$timeout','CityPickerService','$ionicScrollDelegate','$ionicModal', 
    function ($ionicPopup, $timeout,CityPickerService, $ionicScrollDelegate,$ionicModal) {
  return {
    restrict: 'AE',
    scope: {
      citydata: '=',
      citynum: '=',
      backdrop:'@',
      backdropClickToClose:'@',
      buttonClicked: '&'
    },
    template:  '<input type="text"  placeholder={{vm.placeholder}} ng-model="citydata"  class={{vm.cssClass}} readonly>',
    link: function (scope, element, attrs) {
        var vm=scope.vm={},citypickerModel=null;
        //根据城市数据来 设置Handle。
        vm.provinceHandle="provinceHandle"+attrs.citydata;
        vm.cityHandle="cityHandle"+attrs.citydata;
        vm.countryHandle="countryHandle"+attrs.citydata;
        vm.placeholder=attrs.placeholder || "请选择城市";
        vm.okText=attrs.okText || "确定";
        vm.cssClass=attrs.cssClass;
        vm.barCssClass=attrs.barCssClass || "bar-dark";
        vm.backdrop=scope.$eval(scope.backdrop) || false;
        vm.backdropClickToClose=scope.$eval(scope.backdropClickToClose) || false;
        vm.cityData=CityPickerService.cityList;
        vm.tag=attrs.tag || "-";
        vm.wait = 50;
        vm.trigger = {};
        vm.returnOk=function(){
          citypickerModel && citypickerModel.hide();
          scope.buttonClicked && scope.buttonClicked();
        };
        vm.clickToClose=function(){
          vm.backdropClickToClose && citypickerModel && citypickerModel.hide();
        };
        vm.getProvince=function(){
            $timeout.cancel(vm.proScrolling);//取消之前的scrollTo.让位置一次性过渡到最新
            $timeout.cancel(vm.proDating);//取消之前的数据绑定.让数据一次性过渡到最新
            if (!vm.cityData) return false;
            var length=vm.cityData.length,Handle=vm.provinceHandle,HandleChild=vm.cityHandle;
            var top= $ionicScrollDelegate.$getByHandle(Handle).getScrollPosition().top;//当前滚动位置
            console.log(top);
            var off_y=0;
            if(vm.curProvince){
                off_y=vm.curProvince.index;
                // top+=off_y*36;
            }
            var index = Math.round(top / 36);
            var curIndex=index +off_y;
            if (index < 0 ) index =0;//iOS bouncing超出头
            if (index >length-1 ) index =length-1;//iOS bouncing超出尾
            if (top===index*36 ) {
                vm.proDating=$timeout(function () {
                     vm.province=vm.cityData[index];
                     vm.city=vm.province.sub[0];
                     vm.country={};
                     vm.city && vm.city.sub && (vm.country=vm.city.sub[0]);//处理省市乡联动数据
                     $ionicScrollDelegate.$getByHandle(HandleChild).resize();
                     HandleChild && $ionicScrollDelegate.$getByHandle(HandleChild).scrollTop();//初始化子scroll top位
                    //数据同步
                    (vm.city.sub && vm.city.sub.length>0) ? (scope.citydata=vm.province.name +vm.tag+  vm.city.name+vm.tag+vm.country.name ) :(scope.citydata=vm.province.name +vm.tag+  vm.city.name);
                    if(vm.city.sub && vm.city.sub.length>0) {
                        scope.citynum.province=vm.province.id;
                        scope.citynum.city=vm.city.id;
                        scope.citynum.country=vm.country.id
                    }else{
                        scope.citynum.province=vm.province.id;
                        scope.citynum.city=vm.city.id;
                    }
                    if(vm.trigger && vm.trigger.provinceScrollEndEvent) {
                        var timeout = $timeout(function(){
                            vm.trigger && vm.trigger.provinceScrollEndEvent();
                            $timeout.cancel(timeout);
                        }, 0);
                    }
                }, vm.wait);
            }else{
                vm.proScrolling=$timeout(function () {
                    $ionicScrollDelegate.$getByHandle(Handle).scrollTo(0,index*36,true);
                }, vm.wait);
            }
        };
        vm.getCity=function(){
            $timeout.cancel(vm.cityScrolling);//取消之前的scrollTo.让位置一次性过渡到最新
            $timeout.cancel(vm.cityDating);//取消之前的数据绑定.让数据一次性过渡到最新
            if (!vm.province.sub) return false;
            var length=vm.province.sub.length,Handle=vm.cityHandle,HandleChild=vm.countryHandle;
            var top= $ionicScrollDelegate.$getByHandle(Handle).getScrollPosition().top;//当前滚动位置
            // console.log(top);
            var index = Math.round(top / 36);
            if (index < 0 ) index =0;//iOS bouncing超出头
            if (index >length-1 ) index =length-1;//iOS bouncing超出尾
            if (top===index*36 ) {
                vm.cityDating=$timeout(function () {
                    vm.city=vm.province.sub[index];
                    vm.country={};
                    vm.city && vm.city.sub && (vm.country=vm.city.sub[0]);//处理省市乡联动数据
                    $ionicScrollDelegate.$getByHandle(HandleChild).resize();
                    HandleChild && $ionicScrollDelegate.$getByHandle(HandleChild).scrollTop();//初始化子scroll top位
                    //数据同步
                    (vm.city.sub && vm.city.sub.length>0) ? (scope.citydata=vm.province.name +vm.tag+  vm.city.name+vm.tag+vm.country.name ) :(scope.citydata=vm.province.name +vm.tag+  vm.city.name);
                    if(vm.city.sub && vm.city.sub.length>0) {
                        scope.citynum.province=vm.province.id;
                        scope.citynum.city=vm.city.id;
                        scope.citynum.country=vm.country.id
                    }else{
                        scope.citynum.province=vm.province.id;
                        scope.citynum.city=vm.city.id;
                    }
                    if(vm.trigger && vm.trigger.cityScrollEndEvent) {
                        var timeout = $timeout(function(){
                            vm.trigger && vm.trigger.cityScrollEndEvent();
                            $timeout.cancel(timeout);
                        }, 0);
                    }
                }, vm.wait);
            }else{
                vm.cityScrolling=$timeout(function () {
                    $ionicScrollDelegate.$getByHandle(Handle).scrollTo(0,index*36,true);
                }, vm.wait);
            }
        };
        vm.getCountry=function(){
            $timeout.cancel(vm.couScrolling);//取消之前的scrollTo.让位置一次性过渡到最新
            $timeout.cancel(vm.couDating);//取消之前的数据绑定.让数据一次性过渡到最新
            if (!vm.city.sub) return false;
            var length=vm.city.sub.length,Handle=vm.countryHandle;
            var top= $ionicScrollDelegate.$getByHandle(Handle).getScrollPosition().top;//当前滚动位置
            // console.log(top);
            var index = Math.round(top / 36);
            if (index < 0 ) index =0;//iOS bouncing超出头
            if (index >length-1 ) index =length-1;//iOS bouncing超出尾
            if (top===index*36 ) {
                vm.couScrolling=$timeout(function () {
                    vm.country=vm.city.sub[index];
                    //数据同步
                    (vm.city.sub && vm.city.sub.length>0) ? (scope.citydata=vm.province.name +vm.tag+  vm.city.name+vm.tag+vm.country.name ) :(scope.citydata=vm.province.name +vm.tag+  vm.city.name);
                    if(vm.city.sub && vm.city.sub.length>0) {
                        scope.citynum.province=vm.province.id;
                        scope.citynum.city=vm.city.id;
                        scope.citynum.country=vm.country.id
                    }else{
                        scope.citynum.province=vm.province.id;
                        scope.citynum.city=vm.city.id;
                    }
                }, vm.wait);
            }else{
                vm.couDating=$timeout(function () {
                    $ionicScrollDelegate.$getByHandle(Handle).scrollTo(0,index*36,true);
                }, vm.wait);
            }
        };
        var getCurPart=function(id,obj){
            if(id&& id.replace(/\s+/g,"")!=""){
                for(var i=0;i<obj.length;i++){
                    if (id==obj[i].id){
                        var index=i;
                        var newObj={
                            index:i,
                            obj:obj[i]
                        };
                        return newObj;
                    }
                }
            }
            return null;
        };
        var initName=function(){
            var cityListStr=scope.citynum;
            if(cityListStr && cityListStr.province) {
                var province=getCurPart(cityListStr.province,vm.cityData);
                vm.curProvince=province;
                if(province !=null)
                    scope.citydata=province.obj.name;
            };
            if(cityListStr && cityListStr.city) {
                if(vm.curProvince&&vm.curProvince.obj){
                    var city=getCurPart(cityListStr.city,vm.curProvince.obj.sub);
                    vm.curCity=city;
                    if(city !=null)
                        scope.citydata +=vm.tag+city.obj.name;
                }
            };
            if(cityListStr && cityListStr.country) {
                if(vm.curCity && vm.curCity.obj){
                    var country = getCurPart(cityListStr.country, vm.curCity.obj.sub);
                    vm.curCountry = country;
                    if(country !=null)
                        scope.citydata +=vm.tag+country.obj.name;
                }
            };
        };

        scope.citynum["reset"] = function(){
            initName();
        }
        initName();
        var setData = function(province, Handle){
            if(province && province !=null)
                $ionicScrollDelegate.$getByHandle(Handle).scrollTo(0,province.index*36,true);
            else
                $ionicScrollDelegate.$getByHandle(Handle).scrollTo(0,0,true);
        };

        var initTemp=function(){
            var cityListStr=scope.citynum;
            if(cityListStr && cityListStr.country) {
                vm.trigger.cityScrollEndEvent = function() {
                    // var country = getCurPart(cityNoList[2], vm.curCity.obj.sub);
                    setData(vm.curCountry, vm.countryHandle);
                    vm.trigger.cityScrollEndEvent = undefined;
                }
            }
            if(cityListStr && cityListStr.city) {
                vm.trigger.provinceScrollEndEvent = function(){
                    // var city=getCurPart(cityNoList[1],vm.curProvince.obj.sub);
                    setData(vm.curCity, vm.cityHandle);
                    vm.trigger.provinceScrollEndEvent = undefined;
                }
            }
            if(cityListStr && cityListStr.province){
                // var province=getCurPart(cityNoList[0],vm.cityData);
                setData(vm.curProvince, vm.provinceHandle);
            }
            console.log( vm);
        };

        element.on("click", function () {
            //零时处理 点击过之后直接显示不再创建
            if (!attrs.checked) {
              citypickerModel && citypickerModel.remove();
            }else{
              citypickerModel && citypickerModel.show();  
              return;
            }
            attrs.checked=true;
            $ionicModal.fromTemplateUrl('lib/ionic-citypicker/src/city-picker-modal.html', {
              scope: scope,
              animation: 'slide-in-up',
              backdropClickToClose:vm.backdropClickToClose
            }).then(function(modal) {
                citypickerModel = modal;
                initTemp();
                citypickerModel.show();
            })
        });
        //销毁模型
        scope.$on('$destroy', function() {
          citypickerModel && citypickerModel.remove();
        });
    }
  }
}]);
