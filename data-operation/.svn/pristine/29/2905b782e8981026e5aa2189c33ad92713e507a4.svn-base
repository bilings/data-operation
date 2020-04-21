var homeIndex_obj = {

    urlConfig: {
        validate: '/emp/validate',
        myPermission: '/emp/myPermission',
        titleInfo: '/emp/titleInfo',
        cityList: '/emp/cityList',
        logout: '/logout'
    },

    currentCityAdministrativeId: '',
    currentCityMongoId: '',
    currentCity: '',

    init: function () {
        var _this = this;
        $.post({
            url: _this.urlConfig.validate,
            success: function (res) {
                if (res.code === 0) {
                    _this._renderSlideNav(homeIndex_menus);
                    _this._initSpread();
                    _this._initUser();
                    _this._initLogout();
                    _this._initEnv();
                    _this._initCity();
                } else {
                    location.href = "../home/login.html";
                }
            }
        });
    },

    /**
     * 环境变量
     * @private
     */
    _initEnv: function () {
        $.post('/getConfig', {}, function (back) {
            var data = back.data;
            var env = '--环境';
            if (data.profile === 'prod') {
                env = '正式环境';
            } else if (data.profile === 'test') {
                env = '测试环境';
            } else if (data.profile === 'dev') {
                env = '开发环境';
            }
            $('#homeIndex_wrapper #env').text(env);
        }, 'json');
    },

    _setCookie: function (name, value) {
        if (value) {
            var days = 1; //定义一天
            var exp = new Date();
            exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
           // 写入Cookie, toGMTString将时间转换成字符串
            document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString;
        }
    },

    /**
     * cookie中取值
     * */
     _getCookie :function(name) {
    var arr,reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)"); //匹配字段
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    } else {
        return null;
    }
},


    /**
     * 城市下拉
     * @private
     */
    _initCity: function () {
        var _this = this;
        $.get({
            url: _this.urlConfig.cityList,
            success: function (res) {
                $.each(res.data, function (i, item) {
                    var currentCity =  _this._getCookie("currentCity")

                    if (currentCity!=null ) {
                        // 设置当前城市
                        _this.currentCityMongoId = _this._getCookie ("currentCityMongoId");
                        _this.currentCity = _this._getCookie ("currentCity");
                        $('#homeIndex_wrapper #cityShow').attr('key',  _this.currentCityMongoId);
                        $('#homeIndex_wrapper #cityShow').text( _this.currentCity);
                        // 成功获取当前城市后初始化楼盘管理
                        _this._addTab('楼盘管理', '../estate/index.html', false, 'layui-icon-template-1');

                    }else{
                        if(i==0){
                            $('#homeIndex_wrapper #cityShow').attr('key_admin', item._id);
                            $('#homeIndex_wrapper #cityShow').attr('key', item._id);
                            $('#homeIndex_wrapper #cityShow').text(item.name);
                            // 放当前城市到cookie中
                            _this._setCookie ("currentCityMongoId", item._id);
                            _this._setCookie ("currentCity", item.name);
                            _this.currentCityAdministrativeId = item._id;
                            _this.currentCityMongoId = item._id;
                            _this.currentCity = item.name;
                            // 成功获取当前城市后初始化楼盘管理
                            _this._addTab('楼盘管理', '../estate/index.html', false, 'layui-icon-template-1');
                        }
                    }
                    $('#homeIndex_wrapper #cityList').append('<dd><a href="#" name="cityItem" key="'+item._id+'">' + item.name + '</a></dd>');
                });
                // 为每个选项绑定click事件
                $('#homeIndex_wrapper [name="cityItem"]').each(function () {
                    $(this).bind("click", function () {
                        // 下拉的值改为所选城市
                        $('#homeIndex_wrapper #cityShow').text($(this).text());
                        var cityId = $(this).attr("key")
                        // 删除所有激活选项卡
                        var tabs = $(".layui-tab-title li");
                        $.each(tabs, function () {
                            var id = $(this).attr("lay-id");
                            layui.element.tabDelete('tabs', id);
                        });
                        // 设置当前城市
                        _this._setCookie ("currentCityMongoId", $(this).attr('key'));
                        _this._setCookie ("currentCity", $(this).text());
                        _this.currentCityAdministrativeId = $(this).attr('key_admin');
                        _this.currentCityMongoId = $(this).attr('key');
                        _this.currentCity = $(this).text();
                        // 重新打开主页
                        _this._addTab('楼盘管理', '../estate/index.html', false, 'layui-icon-home');
                    });
                });
            }
        });
    },

    /**
     * 登录用户
     * @private
     */
    _initUser: function () {
        var _this = this;
        $.get(_this.urlConfig.titleInfo, {}, function (back) {
            $('#homeIndex_wrapper #loginUser').text(back.data);
        }, 'json');
    },

    /**
     * 登出系统
     * @private
     */
    _initLogout: function () {
        var _this = this;
        $('#homeIndex_wrapper #logout').on('click', function () {
            $.post({
                url: _this.urlConfig.logout,
                success: function () {
                    location.href = "../home/login.html";
                }
            });
        });
    },

    /**
     * 刷新当前tab
     * @private
     */
    _initRefresh: function () {
        $('#homeIndex_wrapper #refresh').on('click', function () {
            //找到当前激活的tab
            var $activeTitle = $('#homeIndex_wrapper>div.layui-tab>ul.layui-tab-title>li.layui-this');
            if ($activeTitle[0]) {
                var id = $activeTitle.attr('lay-id');
                var $iframe = $('#homeIndex_wrapper iframe#' + id);
                $iframe.attr('src', $iframe.attr('src'));
            }
        });
    },

    /**
     * 折叠收缩
     * @private
     */
    _initSpread: function () {
        $('#homeIndex_wrapper #spread').on('click', function () {
            $(this).toggleClass('layui-icon-shrink-right')
                .toggleClass('layui-icon-spread-left');
            $('#homeIndex_wrapper').toggleClass('homeIndex_spread');
        });
    },

    /**
     * 添加tab
     * refresh 当tab存在时，是否刷新
     * icon可以为空
     * @param text
     * @param url
     * @param refresh
     * @param icon
     * @private
     */
    _addTab: function (text, url, refresh, icon) {
        if (url === 'javascript:void(0);') {
            return false;
        }
        var _this = this;
        if (!$('#homeIndex_wrapper ul.layui-tab-title [lay-id=' + text + ']')[0]) {
            var loading = layer.msg('加载中...', {
                icon: 16,
                shade: false,
                time: 0
            });
            layui.element.tabAdd('tabs', {
                title: '<span class="layui-icon ' + (icon || 'layui-icon-flag') + '"></span>&nbsp;&nbsp;' + text,
                content: '<iframe id="' + text + '" src="' + url + '"></iframe>',
                id: text
            });
            //iframe加载完成事件
            var iframe = $('#homeIndex_wrapper #' + text)[0];
            if (iframe.attachEvent) {
                iframe.attachEvent("onload", function () {
                    layui.layer.close(loading);
                });
            } else {
                iframe.onload = function () {
                    layui.layer.close(loading);
                }
            }
        } else if (refresh) {
            var $iframe = $('#homeIndex_wrapper iframe#' + text);
            if (url !== $iframe.attr('src')) {
                $iframe.attr('src', url);
            }
        }
        layui.element.tabChange('tabs', text);
    },

    /**
     * 渲染侧边栏
     * @param menus
     * @private
     */
    _renderSlideNav: function (menus) {
        var _this = this;
        var html = [];
        // 获得当前权限
        $.get({
            url: _this.urlConfig.myPermission,
            success: function (res) {
                $.each(menus, function (index, menu) {
                    // 如果没有权限就跳过
                    if (menu.code && $.inArray(menu.code, res.data) === -1) {
                        return true;
                    }
                    html.push('<li class="layui-nav-item layui-nav-itemed">');
                    html.push(' <a data-url="' + menu.url + '" href="javascript:void(0);">');
                    if (menu.icon) {
                        html.push('<span class="layui-icon ' + menu.icon + '"><span>');
                    }
                    html.push(menu.text + '</a>');
                    if (menu.children && menu.children.length > 0) {
                        html.push('<dl class="layui-nav-child">');
                        $.each(menu.children, function (i, m) {
                            // 如果没有权限就跳过
                            if (m.code && $.inArray(m.code, res.data) === -1) {
                                return true;
                            }
                            html.push('<dd>');

                            html.push('<a data-url="');
                            if (m.url) {
                                html.push(m.url);
                            } else {
                                html.push('javascript:void(0);');
                            }
                            html.push('" href="javascript:void(0);">');

                            if (m.icon) {
                                html.push('<span class="layui-icon ' + m.icon + '"><span>');
                            }
                            html.push(m.text + '</a>');

                            // 子集循环开始
                            if (m.children && m.children.length > 0) {
                                html.push('<dl class="layui-nav-child">');
                                $.each(m.children, function (k, n) {
                                    // 如果没有权限就跳过
                                    if (n.code && $.inArray(n.code, res.data) === -1) {
                                        return true;
                                    }
                                    html.push('<dd style="padding-left: 10px;">');

                                    html.push('<a data-url="');
                                    if (n.url) {
                                        html.push(n.url);
                                    } else {
                                        html.push('javascript:void(0);');
                                    }
                                    html.push('" href="javascript:void(0);">');

                                    if (n.icon) {
                                        html.push('<span class="layui-icon ' + n.icon + '"><span>');
                                    }
                                    html.push(n.text + '</a>');

                                    //下级

                                    html.push('</dd>');
                                });
                                html.push('</dl>');
                            }
                            // 子集循环结束

                            html.push('</dd>');
                        });
                        html.push('</dl>');
                    }
                    html.push('</li>');
                });
                $('#homeIndex_wrapper>ul.layui-nav').html(html.join(''));
                //绑定tab添加事件
                $('#homeIndex_wrapper>ul.layui-nav a[data-url!=undefined]').on('click', function () {
                    var $icon = $(this).children('span.layui-icon');
                    var icon = null;
                    if ($icon[0]) {
                        icon = $icon.attr('class');
                    }
                    _this._addTab($(this).text(), $(this).attr('data-url'), false, icon);
                });
                //动态加载后需要重新渲染
                layui.element.init();
                //美化滚动条
                $('#homeIndex_wrapper>ul.layui-nav').niceScroll();
            }
        });
    }

};

$(function () {
    homeIndex_obj.init();
});