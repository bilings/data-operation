const $common = {
    /**
     * 通用初始化下拉
     * @param url 请求地址
     * @param data 请求数据
     * @param id 页面id
     * @param _default 默认
     */
    initBaseSelect:function (url, data, id, _default) {
        $.get({
            url: url,
            data: data,
            async: false,
            success: function (res) {
                $('#' + id).html('<option value="" selected="selected">'+_default+'</option>');
                $.each(res.data, function (index, item) {
                    $('#' + id).append('<option value="' + (item.id || item.type) + '">' + item.name + '</option>');
                });
                layui.form.render('select');
            }
        });
    },
};