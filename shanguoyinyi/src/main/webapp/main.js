require([
    'app.js',
    'config.js',
    'ZeroClipboard.js'
], function (app, config,ZeroClipboard) {
    club.View.configure({manage: true});

    // var cssArray = [];
    // for (var key in config) {
    //     var json = config[key];
    //     if (json.cssFilePath) {
    //         cssArray.push("css!" + json.cssFilePath);
    //     }
    // }
    // portal.callService("GetLocalInfo", {}, function(data) {
    //     if (data) {
    //         if (data.charset) {
    //             portal.appGlobal.set("charset", data.charset);
    //         }
    //         if (data.version) {
    //             portal.appGlobal.set("version", data.version);
    //         }
    //         if (data.webroot) {
    //             portal.appGlobal.set("webroot", data.webroot);
    //         }
    //         if (data.language) {
    //             portal.appGlobal.set("language", data.language);
    //         }
    //         if (data.shortLanguage) {
    //             portal.appGlobal.set("shortLanguage", data.shortLanguage);
    //         }
    //     }
    //     club.language = portal.appGlobal.get('language');
    //     portal.require(cssArray, function() {
    //         app.run();
    //     });
    // });
    app.run();
});