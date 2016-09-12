/**
 * 原理在此贴中http://rsj217.diandian.com/post/2012-09-18/40039914176
 * @吕衍辉 
 */
jQuery.macDock = {
    build: function (options) {
        return this.each(function () {
            var el = this;
            jQuery.macDock.el = el;
            el.config = {
                items: jQuery(options.items, this),
                container: jQuery(options.container, this),
                pos: jQuery.iUtil.getPosition(this),
                itemWidth: options.itemWidth,
                itemsText: options.itemsText,
                proximity: options.proximity,
                valign: options.valign,
                halign: options.halign,
                maxWidth: options.maxWidth
            };
            jQuery.macDock.positionContainer(el, 0);
            jQuery(window).bind('resize', function () {
                el.config.pos = jQuery.iUtil.getPosition(el);
                jQuery.macDock.positionContainer(el, 0);
                jQuery.macDock.positionItems(el)
            });
            jQuery.macDock.positionItems(el);
            
            jQuery(document).bind('mousemove',  jQuery.macDock.over);
            el.config.items.hover( function (e) {
            	jQuery(el.config.itemsText, this).get(0).style.display = 'block';
            },function(){
//            	jQuery.macDock.over.call(el,{clientX:0,clientY:0});
            	jQuery(el.config.itemsText, this).css({'display':'none'});
            });
//            el.clearOverTimer = null,el.over = false;
//            el.config.items.hover( function (e) {
//                if(el.clearOverTimer){
//                	clearTimeout(el.clearOverTimer);
//                }
//                jQuery(el.config.itemsText, this).get(0).style.display = 'block';
//        		jQuery(document).bind('mousemove', over);
//            },function () {
//                el.over = true;
//                jQuery(el.config.itemsText, this).css({'display':'none'});
//                el.clearOverTimer = setTimeout(function(){
//                	over({clientX:0,clientY:0});
//                	jQuery(document).unbind('mousemove');
//                	el.over = false;
//                },100);
//            });
        })
    },
    over:function (e) {
        var pointer = jQuery.iUtil.getPointer(e);
        var toAdd = 0,el = jQuery.macDock.el;
        if (el.config.halign && el.config.halign == 'center') var posx = pointer.x - el.config.pos.x - (el.offsetWidth - el.config.itemWidth * el.config.items.size()) / 2 - el.config.itemWidth / 2;
        else if (el.config.halign && el.config.halign == 'right') var posx = pointer.x - el.config.pos.x - el.offsetWidth + el.config.itemWidth * el.config.items.size();
        else var posx = pointer.x - el.config.pos.x;
        var posy = Math.pow(pointer.y - el.config.pos.y - el.offsetHeight / 2, 2);
        el.config.items.each(function (nr) {
            distance = Math.sqrt(Math.pow(posx - nr * el.config.itemWidth, 2) + posy);
            distance -= el.config.itemWidth / 2;
            distance = distance < 0 ? 0 : distance;
            distance = distance > el.config.proximity ? el.config.proximity : distance;
            distance = el.config.proximity - distance;
            extraWidth = el.config.maxWidth * distance / el.config.proximity;
            this.style.width = el.config.itemWidth + extraWidth + 'px';
            this.style.left = el.config.itemWidth * nr + toAdd + 'px';
            toAdd += extraWidth
        });
        jQuery.macDock.positionContainer(el, toAdd)
    },
    positionContainer: function (el, toAdd) {
        if (el.config.halign) if (el.config.halign == 'center') el.config.container.get(0).style.left = (el.offsetWidth - el.config.itemWidth * el.config.items.size()) / 2 - toAdd / 2 + 'px';
        else if (el.config.halign == 'left') el.config.container.get(0).style.left = -toAdd / el.config.items.size() + 'px';
        else if (el.config.halign == 'right') el.config.container.get(0).style.left = (el.offsetWidth - el.config.itemWidth * el.config.items.size()) - toAdd / 2 + 'px';
        el.config.container.get(0).style.width = el.config.itemWidth * el.config.items.size() + toAdd + 'px'
    },
    positionItems: function (el) {
        el.config.items.each(function (nr) {
            this.style.width = el.config.itemWidth + 'px';
            this.style.left = el.config.itemWidth * nr + 'px'
        })
    }
};
jQuery.fn.initMacDock = jQuery.macDock.build;