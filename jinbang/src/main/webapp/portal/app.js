constans={};//全局常量声明
requirejs.config({
    baseUrl:webRoot+'portal/lib',
    paths: {
        app: webRoot+'portal/app'
    }
});
requirejs(['app/main']);
