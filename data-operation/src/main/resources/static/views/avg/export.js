var avgPriceExport_obj = {

    urlConfig: {
        download: '/avg/download'
    },
    init: function () {
        this._initSelectAll();
        this._initExport();
    },

    cityName:layui.util.getQueryString('cityName'),
    /**
     * 全选按钮
     * @private
     */
    _initSelectAll: function () {
        layui.form.on('checkbox(c_all)', function (data) {
            var a = data.elem.checked;
            if (a === true) {
                $('input[name="column"]').each(function () {
                    $(this).prop("checked", true);
                });
                layui.form.render('checkbox');
            } else {
                $('input[name="column"]').each(function () {
                    $(this).prop("checked", false);
                });
                layui.form.render('checkbox');
            }
        });
    },

    /**
     * 导出按钮
     * @private
     */
    _initExport: function () {
        var _this = this;
        $('#avgPriceExport_wrapper #export').on('click', function () {
            var value = '';
            $('input[name="column"]').each(function () {
                if ($(this).prop("checked")) {
                    value = value + $(this).val() + ',';
                }
            });
            window.open(_this.urlConfig.download + '?columns=' + value+ '&cityName='+ _this.cityName);
        });
    }
};

avgPriceExport_obj.init();