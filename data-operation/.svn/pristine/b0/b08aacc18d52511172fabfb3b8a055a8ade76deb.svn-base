var datafeedIndex_obj = {

    urlConfig: {
        qryDatafeed: "/extOrganization/qryExtOrganization",
        saveDatafeed: "/extOrganization/saveDatafeed"
    },

    /**
     * 界面js入口
     */
    init: function () {
        this._initTable();
        this._initSubmit();
    },

    /**
     * 初始化数据
     * @private
     */
    _initTable: function () {
        var _this = this;
        var $ = layui.jquery, form = layui.form;
        $.post({
            url: _this.urlConfig.qryDatafeed,
            data: {keyword: ""},
            success: function (res) {
                $("input[name='times'][value='1']").attr("checked", res.data.subscribeInterval == 1 ? true : false);
                $("input[name='times'][value='2']").attr("checked", res.data.subscribeInterval == 2 ? true : false);
                $("input[name='publish'][value='1']").attr("checked", res.data.subscribeMethod == 1 ? true : false);
                $("input[name='publish'][value='2']").attr("checked", res.data.subscribeMethod == 2 ? true : false);
                form.render(); //更新全部
            }
        });
    },

    /**
     * 提交按钮
     * @private
     */
    _initSubmit: function () {
        var _this = this;
        layui.form.on('submit(save)', function () {
            var param = {
                subscribeInterval: $('#timesDiv input[name="times"]:checked ').val(),
                subscribeMethod: $('#publishDiv input[name="publish"]:checked ').val()
            };
            $.post({
                url: _this.urlConfig.saveDatafeed,
                data: JSON.stringify(param),
                contentType: "application/json",
                success: function (res) {
                    datafeedIndex_obj._initTable();
                    layer.msg(res.msg);
                }
            });
            return false;
        });
    }
};

function uploadSource(){
    var formData = new FormData(document.getElementById("upload-form"));
    $.ajax({
        url:  "/extOrganization/extOrganizationImport",
        method: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
        }
    });
}

datafeedIndex_obj.init();