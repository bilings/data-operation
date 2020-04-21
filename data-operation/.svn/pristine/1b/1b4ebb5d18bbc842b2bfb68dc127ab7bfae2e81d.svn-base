/**
 * layui 自动定义校验
 */
var $ = layui.jquery, form = layui.form;
form.verify({
    username: function (value, item) { //value：表单的值、item：表单的DOM对象
        if (!new RegExp("^[a-zA-Z0-9\u4e00-\u9fa5\\s·]+$").test(value)) {

            var attr = $(item).attr("lay-verify-msg-username");
            if (typeof attr !== typeof undefined && attr !== false) {
                return attr;
            }

            attr = $(item).attr("lay-verify-msg");
            if (typeof attr !== typeof undefined && attr !== false) {
                return attr;
            }
            return "不匹配用户名规则";
        }
    }
    , mustradio: function (value, item) { //单选按钮必选
        var va = $(item).find("input[type='radio']:checked").val();
        if (typeof (va) == "undefined") {
            return $(item).attr("lay-verify-msg");
        }
    }
    , mustcheck: function (value, item) { //复选框必选
        var xname = $(item).attr("id")
        var va = $(item).find("input[type='checkbox']:checked").val();
        if (typeof (va) == "undefined") {
            return $(item).attr("lay-verify-msg");
        }
    }
    , muststar: function (value, item) { //value：表单的值、item：表单的DOM对象
        if (!new RegExp("^\\d+$").test(value)) {
            return $(item).attr("lay-verify-msg");
        }
    }
    , required: function (value, item) { //value：表单的值、item：表单的DOM对象
        if (!(/[\S]+/.test(value))) {

            var attr = $(item).attr("lay-verify-msg-required");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }

            attr = $(item).attr("lay-verify-msg");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }
            return '必填项不能为空';
        }
    }
    , phone: function (value, item) { //value：表单的值、item：表单的DOM对象
        if (!(/^1\d{10}$/.test(value))) {

            var attr = $(item).attr("lay-verify-msg-phone");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }

            attr = $(item).attr("lay-verify-msg");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }
            return '请输入正确的手机号';
        }
    }
    , email: function (value, item) { //value：表单的值、item：表单的DOM对象
        if (!(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value))) {

            var attr = $(item).attr("lay-verify-msg-email");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }

            attr = $(item).attr("lay-verify-msg");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }
            return '请输入正确的手机号';
        }
    }
    , url: function (value, item) { //value：表单的值、item：表单的DOM对象
        if (!(/(^#)|(^http(s*):\/\/[^\s]+\.[^\s]+)/.test(value))) {

            var attr = $(item).attr("lay-verify-msg-url");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }

            attr = $(item).attr("lay-verify-msg");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }
            return '请输入正确的手机号';
        }
    }
    , number: function (value, item) {
        if (!value || isNaN(value)) {
            var attr = $(item).attr("lay-verify-msg-number");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }

            attr = $(item).attr("lay-verify-msg");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }
            return '只能填写数字';
        }
    }
    , date: function (value, item) { //value：表单的值、item：表单的DOM对象
        if (!(/^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/.test(value))) {

            var attr = $(item).attr("lay-verify-msg-date");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }

            attr = $(item).attr("lay-verify-msg");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }
            return '日期格式不正确';
        }
    }
    , identity: function (value, item) { //value：表单的值、item：表单的DOM对象
        if (!(/(^\d{15}$)|(^\d{17}(x|X|\d)$)/.test(value))) {

            var attr = $(item).attr("lay-verify-msg-identity");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }

            attr = $(item).attr("lay-verify-msg");
            if (typeof attr !== typeof undefined && attr !== false && attr !== '') {
                return attr;
            }
            return '请输入正确的身份证号';
        }
    }
    , content: function (value) {
        return layedit.sync(index);
    }

});