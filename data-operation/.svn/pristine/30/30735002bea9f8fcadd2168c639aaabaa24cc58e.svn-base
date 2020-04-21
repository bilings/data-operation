var homeLogin_obj = {

    urlConfig: {
        // 登录
        login: '/login'
    },

    init: function () {
        this._initLoginButton();
    },

    _initLoginButton: function () {
        var _this = this;
        layui.form.on('submit(login)', function (data) {
            $.post({
                url: _this.urlConfig.login,
                data: data.field,
                dataType: 'json',
                success: function (res) {
                    if (res.code === 0) {
                        location.href = "../home/index.html";
                    } else {
                        layer.msg('登录名或密码错误');
                    }
                }
            });
            return false;
        })
    }
};

homeLogin_obj.init();