var customerOrgEdit_obj = {

    urlConfig: {
        dictionary: '/setting/dictionary',
        detail: '/customerOrg/detail/',
        update: '/customerOrg/update'
    },
    /**
     * 全局楼盘变量，新增时为空
     */
    customerOrg: {},

    init: function () {
        this._showHeadInfo();
        this._initButton();
    },
    /**
     * 机构信息
     * @private
     */
    _showHeadInfo: function () {
        var _this = this;
        // 获取现有信息
        var id = layui.util.urlParam('id');
        // 添加子机构
        var parentId = layui.util.urlParam('parentId');
        if (parentId) {
            $('#customerOrgEdit_wrapper').find('.js-children-show').removeClass('hidden');
        } else {
            $('#customerOrgEdit_wrapper').find('.js-parent-show').removeClass('hidden');
        }
        if (id || parentId) {
            if (parentId) {
                id = parentId;
            }
            $.get({
                url: _this.urlConfig.detail + id,
                async: false,
                success: function (res) {
                    // 设置机构信息对象
                    _this.customerOrg = res.data;
                    if (parentId) {
                        $('#customerOrgEdit_wrapper #orgName').val(_this.customerOrg.name);
                    } else {
                        $('#customerOrgEdit_wrapper #name').val(_this.customerOrg.name);
                        $('#customerOrgEdit_wrapper #times').val(_this.customerOrg.times);
                    }
                }
            });
        }
        // 初始化下拉列表
        _this._initSelect(parentId);
    },
    /**
     * 下拉
     * @private
     */
    _initSelect: function (parentId) {
        var _this = this;
        // 客户分类
        _this._initAdvanceSelect(_this.urlConfig.dictionary, {category: '客户分类'}, 'typeSelect', 'item', 'type', parentId);
    },
    /**
     * value和text不同的下拉
     * @param url
     * @param data
     * @param id 控件的id
     * @param key 返回的数据中作为值的属性名
     * @param keyId 判断的属性
     * @param parentId 有为新增
     * @private
     */
    _initAdvanceSelect: function (url, data, id, key, keyId, parentId) {
        var _this = this;
        $.get({
            url: url,
            data: data,
            success: function (res) {
                $('#customerOrgEdit_wrapper #' + id).html('<option></option>');
                $.each(res.data, function (index, item) {
                    // 本项目中，这种情况下数据库都是存的id (用两个==，不判断类型)
                    if (!parentId && _this.customerOrg && item.id == _this.customerOrg[keyId]) {
                        $('#customerOrgEdit_wrapper #' + id).append('<option value="' + item.id + '" selected="">' + item[key] + '</option>');
                    } else {
                        $('#customerOrgEdit_wrapper #' + id).append('<option value="' + item.id + '">' + item[key] + '</option>');
                    }
                });
                layui.form.render('select');
            }
        });
    },
    /**
     * 按钮
     * @private
     */
    _initButton: function () {
        var _this = this;
        layui.form.on('submit(updateBtn)', function () {
            var data = {
                id: layui.util.urlParam('id'),
                name: $('#customerOrgEdit_wrapper #name').val(),
                type: $('#customerOrgEdit_wrapper #typeSelect').val(),
                times: $('#customerOrgEdit_wrapper #times').val(),
                rank: _this.customerOrg.rank,
                parentId: layui.util.urlParam('parentId')
            };
            $.ajax({
                contentType: 'application/json',
                url: _this.urlConfig.update,
                async: false,
                type: "PUT",
                data: JSON.stringify(data),
                error: function (res) {
                    layer.alert(res.msg);
                    return false;
                },
                success: function (res) {
                    layer.alert(res.msg);
                    parent.customerOrgIndex_obj._initTree();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }
            });
        });
    }
};

customerOrgEdit_obj.init();