var customerUserEdit_obj = {

    urlConfig: {
        dictionary: '/setting/dictionary',
        orgDetail: "/customerOrg/detail/",
        orgList: "/customerOrg/list",
        detail: '/customerUser/detail/',
        update: '/customerUser/update'
    },
    /**
     * 全局楼盘变量，新增时为空
     */
    customerUser: {},

    init: function () {
        this._showHeadInfo();
        this._initButton();
    },
    /**
     * 客户信息
     * @private
     */
    _showHeadInfo: function () {
        var _this = this;
        var id = layui.util.urlParam('id');
        // 获取现有信息
        if (id) {
            $.get({
                url: _this.urlConfig.detail + id,
                async: false,
                success: function (res) {
                    // 设置客户信息对象
                    _this.customerUser = res.data;
                    $('#customerUserEdit_wrapper #username').val(_this.customerUser.username);
                    $('#customerUserEdit_wrapper #name').val(_this.customerUser.name);
                    $('#customerUserEdit_wrapper #times').val(_this.customerUser.times);
                    if(_this.customerUser.orgId){
                        $('#customerUserEdit_wrapper').find(".js-show-times").addClass("hidden");
                    }
                }
            });
        }
        // 初始化下拉列表
        _this._initSelect();
    },
    /**
     * 下拉
     * @private
     */
    _initSelect: function () {
        var _this = this;
        // 机构
        _this._initAdvanceSelect(_this.urlConfig.orgList, {name: ""}, 'orgSelect', 'name', 'orgId');
        // 客户分类
        _this._initAdvanceSelect(_this.urlConfig.dictionary, {category: '客户分类'}, 'typeSelect', 'item', 'type');

        layui.use(['layer', 'jquery', 'form'], function () {
            layui.form.on('select(orgSelect)', function () {
                var orgId = $('#customerUserEdit_wrapper #orgSelect').val();
                if (orgId) {
                    $('#customerUserEdit_wrapper #typeSelect').attr("disabled", true);
                    $('#customerUserEdit_wrapper').find(".js-show-times").addClass("hidden");
                    $.get({
                        url: _this.urlConfig.orgDetail + orgId,
                        async: false,
                        success: function (res) {
                            // 根据机构分类重新初始化分类下拉
                            _this.customerUser.type = res.data.type;
                            _this._initAdvanceSelect(_this.urlConfig.dictionary, {category: '客户分类'}, 'typeSelect', 'item', 'type');
                            form.render('select');
                        }
                    });
                } else {
                    $('#customerUserEdit_wrapper #typeSelect').removeAttr("disabled");
                    $('#customerUserEdit_wrapper #typeSelect').val("");
                    $('#customerUserEdit_wrapper').find(".js-show-times").removeClass("hidden");
                    form.render('select');
                }
            });
        });
    },
    /**
     * value和text不同的下拉
     * @param url
     * @param data
     * @param id 控件的id
     * @param key 返回的数据中作为值的属性名
     * @param keyId 判断的属性
     * @private
     */
    _initAdvanceSelect: function (url, data, id, key, keyId) {
        var _this = this;
        $.get({
            url: url,
            data: data,
            success: function (res) {
                $('#customerUserEdit_wrapper #' + id).html('<option></option>');
                $.each(res.data, function (index, item) {
                    // 本项目中，这种情况下数据库都是存的id (用两个==，不判断类型)
                    if (_this.customerUser && item.id == _this.customerUser[keyId]) {
                        $('#customerUserEdit_wrapper #' + id).append('<option value="' + item.id + '" selected="">' + item[key] + '</option>');
                    } else {
                        $('#customerUserEdit_wrapper #' + id).append('<option value="' + item.id + '">' + item[key] + '</option>');
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
                username: $('#customerUserEdit_wrapper #username').val(),
                name: $('#customerUserEdit_wrapper #name').val(),
                type: $('#customerUserEdit_wrapper #typeSelect').val(),
                tel: $('#customerUserEdit_wrapper #username').val(),
                orgId: $('#customerUserEdit_wrapper #orgSelect').val(),
                times: $('#customerUserEdit_wrapper #times').val()
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
                    parent.customerUserIndex_obj._displayTable();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }
            });
        });
    }
};

customerUserEdit_obj.init();