/**
 * Created by Administrator on 2015/11/11.
 */

//���ֳ�ʼ������
function xz_BjInit(){
    var winW=0;
    var winH=0;
    var oHtml=$('html');

    //���� htmlԪ�� �������СΪ  ��������/6.4
    function xz_Bj(){
        winW=$(window).innerWidth();

        oHtml.css('fontSize',winW/6.4);

        //console.log(oHtml.css('fontSize'))
    }
    //�����꼴ִ��
    xz_Bj();

    //�����ڵĴ�С�ı�ʱ
    $(window).on('resize',xz_Bj);
}
//�����꼴ִ��
xz_BjInit();
