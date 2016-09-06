/**
 * Created by Administrator on 2015/11/11.
 */

//布局初始化函数
function xz_BjInit(){
    var winW=0;
    var winH=0;
    var oHtml=$('html');

    //设置 html元素 的字体大小为  可视区宽/6.4
    function xz_Bj(){
        winW=$(window).innerWidth();

        oHtml.css('fontSize',winW/6.4);

        //console.log(oHtml.css('fontSize'))
    }
    //加载完即执行
    xz_Bj();

    //当窗口的大小改变时
    $(window).on('resize',xz_Bj);
}
//加载完即执行
xz_BjInit();
