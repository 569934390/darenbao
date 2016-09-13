/**
 * 原理在此贴中http://rsj217.diandian.com/post/2012-09-18/40039914176
 * @吕衍辉 
 */
jQuery.macDock = {
    build: function (options) {
        return this.each(function () {
            var el = this;
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
            function over(e,anmit) {
                var pointer = jQuery.iUtil.getPointer(e);
                if(!anmit)
                	pointer.y = el.config.container.offset().top;
                var toAdd = 0;
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
            }
            el.clearOverTimer = null,el.over = false;
            el.config.items.hover(function(){
            	var itemOver = this;
            	el.textTimer = setTimeout(function(){
            		jQuery(el.config.itemsText, itemOver).get(0).style.display = 'block';
            	},100);
            },function(){
            	if(el.textTimer){
            		clearTimeout(el.textTimer);
            	}
            	jQuery(el.config.itemsText, this).get(0).style.display = 'none';
            });
            el.config.container.mouseover( function (e) {
                if(el.clearOverTimer){
                	clearTimeout(el.clearOverTimer);
                }
                if(el.over){
            		return;
                }
                var overs = this,y = el.config.container.offset().top-el.config.proximity;
                el.anmter = setInterval(function(){
                	over({clientX:e.clientX,clientY:y+=15},true);
                	if(y>=e.pageY){
                		clearInterval(el.anmter);
                		jQuery(document).bind('mousemove', over);
                	}
                },20);
            });
            el.config.container.bind('mouseout',function (e) {
            	console.info(e.target.targetName,e.target.className,this)
                el.over = true;
                el.clearOverTimer = setTimeout(function(){
                	el.config.items.animate({width:el.config.itemWidth},100,function(){
                		over({clientX:0,clientY:0},true);
                	});
                	jQuery(document).unbind('mousemove');
                	el.over = false;
                },70);
            });
        })
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