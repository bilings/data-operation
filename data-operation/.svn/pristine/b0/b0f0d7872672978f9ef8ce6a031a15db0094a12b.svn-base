var addCoeGroupIndex_obj = {
    urlConfig: {
        // 列表
        coeAdd: '/estate/list',
        conditionType:''
    },

    /**
     * 界面js入口
     */
    init: function () {
        var _this = this;
        // 搜索
        _this._initAutocomplete();
        //初始化选择条件类型
        _this._initBasicSelect(_this.urlConfig.districtOfCity, {}, 'conditionType');

       ;
    },

    /**
     * 保存按钮
     * @private
     */
    _initSaveBtn: function () {
        var _this = this;
        layui.form.on('submit(saveBtn)', function (data) {
            var resData = data.field;
            console.log(resData)
            $.post({
                url: _this.urlConfig.save,
                data: JSON.stringify(resData),
                contentType: "application/json",
                success: function (res) {
                    if (res.code == 1) {
                        layer.msg('保存成功');
                    } else {
                        layer.msg(res.msg);
                    }
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    },
    /**
     * 加载行政区
     * value和text相同的下拉
     * @param url
     * @param data
     * @param id
     * @private
     */
    _initBasicSelect: function (url, data, id) {
        $.post({
            url: url,
            data: data,
            success: function (res) {
                $('#addGroupIndex_wrapper #' + id).html('<option value="">请选择</option>');
                $.each(res.data, function (index, item) {
                    $('#addGroupIndex_wrapper #' + id).append('<option value="' + item.id + '">' + item.name + '</option>');
                });
                layui.form.render('select');
            }
        });
    },
}
addCoeGroupIndex_obj.init();