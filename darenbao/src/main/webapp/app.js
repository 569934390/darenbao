define(function() {
    var isRunning = false;
    return {
        run: function() {
            //这里配置一下语言，可以动态设置，真实的系统需要根据浏览器设置来获取语言然后设置给club
            club.language = "zh";
            if (!isRunning) {
                isRunning = true;
                require(['modules/common/views/MainView'], function(MainView) {
                    // var mainView = new MainView();
                    // $(document.body).append(mainView.render().el);
                    new MainView({
                        el: 'body'
                    }).render();
                });
            }
        }
    }
});
