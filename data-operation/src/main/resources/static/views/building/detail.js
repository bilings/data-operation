var estateDetail_obj = {
    urlConfig: {
        detail: '/estate/detail',
        dictList: '/setting/dictList',
        boolOption: '/setting/boolOption',
        liftOption: '/setting/liftOption'
    },

    estate: '',
    dictList: '',

    init: function () {
        this._showHeadInfo();
        this._initButton();
    },

    /**
     * 楼盘信息
     * @private
     */
    _showHeadInfo: function () {
        var _this = this;
        $.post({
            url: _this.urlConfig.detail,
            data: {id: layui.util.urlParam('id')},
            success: function (res) {
                // 设置楼盘对象
                _this.estate = res.data;
                // 页面顶的名称 + 地址
                $('#estateDetail_wrapper #nameSpan').html(res.data.name);
                $('#estateDetail_wrapper #addrDetailSpan').html(res.data.addrDetail);
                // 设置界面上的初始值（仅限于输入框，下拉通过下面操作）
                $.each($('#estateDetail_wrapper [name=label-value]'), function (index, item) {
                    var id = item.id;
                    if (res.data[id]) {
                        $('#estateDetail_wrapper #' + id).text(res.data[id]);
                    }
                });
                // 字典数据把id转换成值
                _this._dictionaryToText();
                // 把bool类型转换成文字
                _this._boolToText();
                // 把有无电梯转换成文字
                _this._liftToText();
            }
        });
    },

    /**
     * 字典数据把id转换成值
     * @private
     */
    _dictionaryToText: function () {
        var _this = this;
        $.post({
            url: _this.urlConfig.dictList,
            success: function (res) {
                _this.dictList = res.data;
                $('#estateDetail_wrapper #propertyRight').text(_this._getTextById(_this.estate.propertyRight));
                $('#estateDetail_wrapper #mainCategory').text(_this._getTextById(_this.estate.mainCategory));
                $('#estateDetail_wrapper #mainQuality').text(_this._getTextById(_this.estate.mainQuality));
                $('#estateDetail_wrapper #equipment').text(_this._getTextsById(_this.estate.equipment));
                $('#estateDetail_wrapper #buildingCategory').text(_this._getTextsById(_this.estate.buildingCategory));
                $('#estateDetail_wrapper #quality').text(_this._getTextsById(_this.estate.quality));
            }
        });
    },

    /**
     * 根据id获取字典表的text
     * @param id
     * @returns {string}
     * @private
     */
    _getTextById: function (id) {
        var _this = this;
        var text = '';
        $.each(_this.dictList, function (index, item) {
            if (id == item.id) {
                text = item.item;
            }
        });
        return text;
    },

    /**
     * 多选框的值
     * @param ids
     * @returns {string}
     * @private
     */
    _getTextsById: function (ids) {
        var _this = this;
        var text = '';
        var idArr = ids.split(',');
        $.each(idArr, function (index, id) {
            text = text + _this._getTextById(id) + ',';
        });
        return text;
    },

    /**
     * 把bool类型转换成文字
     * @private
     */
    _boolToText: function () {
        var _this = this;
        $.post({
            url: _this.urlConfig.boolOption,
            success: function (res) {
                $.each(res.data, function (index, item) {
                    if ($('#estateDetail_wrapper #canEvaluate').text() == item.id) {
                        $('#estateDetail_wrapper #canEvaluate').text(item.value);
                    }
                    if ($('#estateDetail_wrapper #isPerfect').text() == item.id) {
                        $('#estateDetail_wrapper #isPerfect').text(item.value);
                    }
                })
            }
        });
    },

    /**
     * 把是否有电梯转换成文字
     * @private
     */
    _liftToText: function () {
        var _this = this;
        $.post({
            url: _this.urlConfig.liftOption,
            success: function (res) {
                $.each(res.data, function (index, item) {
                    if ($('#estateDetail_wrapper #haveLift').text() == item.id) {
                        $('#estateDetail_wrapper #haveLift').text(item.value);
                    }
                })
            }
        });
    },

    /**
     * 按钮
     * @private
     */
    _initButton: function () {
        $('#estateDetail_wrapper #editBtn').on('click', function () {
            var tabId = $('#estateDetail_wrapper #name').text();
            var id = layui.util.urlParam('id');
            parent.document.getElementById(tabId).src = '../estate/edit.html?id=' + id;
        });
    }
};

estateDetail_obj.init();