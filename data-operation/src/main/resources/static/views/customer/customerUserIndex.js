var customerUserIndex_obj = {
    urlConfig: {
        findByPage: '/customerUser/findByPage',
        delete: '/customerUser/delete',
        resetPassword: '/customerUser/resetPassword'
    },
    init: function () {
        var _this = this;
        _this._initSearch();
        _this._displayTable();
        _this._initToolbar();
    },
    /**
     * 初始化搜索条按钮
     * @private
     */
    _initSearch: function () {
        var _this = this;
        $('#customerUserIndex_wrapper #searchBtn').on('click', function () {
            _this.tableIns.reload({
                where: {
                    username: $('#customerUserIndex_wrapper #username').val()
                }
            });
        });
    },
    /**
     * 初始化表格信息
     * @private
     */
    _displayTable: function () {
        var _this = this;
        // 表格初始化
        _this.tableIns = layui.table.render({
            elem: '#customerUserIndex_wrapper #customerUserIndexTable',
            url: _this.urlConfig.findByPage,
            method: 'get',
            where: {
                username: $('#customerUserIndex_wrapper #username').val()
            },
            toolbar: '#customerUserIndexBar',
            title: '用户信息表',
            cols: [[
                {type: 'checkbox', fixed: 'left', width: '2%'},
                {field: 'username', title: '用户账号', width: '19%'},
                {field: 'name', title: '姓名', width: '19%'},
                {
                    field: 'orgName', title: '所属机构', width: '20%',
                    templet: function (d) {
                        if (d.orgName) {
                            return d.orgName
                        } else {
                            return '——';
                        }
                    }
                },
                {field: 'times', title: '剩余查询次数', width: '20%'},
                {fixed: 'right', title: '操作', toolbar: '#lineBar', width: '20%'}
            ]],
            page: true,
            id: 'customerUserIndexTable'
        });
    },
    /**
     * 头部工具栏按钮
     * @private
     */
    _initToolbar: function () {
        var _this = this;
        layui.table.on('toolbar(customerUserIndexTable)', function (obj) {
            var ids = _this._getCheckedValue('customerUserIndexTable');
            var _ids = ids.split(",");
            if (obj.event === 'addBtn') {
                _this._openFrame({title: '新增用户', content: '../customer/customerUserEdit.html'});
            } else {
                if (_ids.length == 0 || _ids[0] == "") {
                    layer.alert('请选择用户。');
                    return false;
                }
                if (obj.event === 'editBtn') {
                    if (_ids.length > 1) {
                        layer.alert('每次最多选择一位用户。');
                        return false;
                    }
                    _this._openFrame({title: '编辑用户', content: '../customer/customerUserEdit.html?id=' + _ids[0]});
                } else if (obj.event === 'resetBtn') {
                    _this._reset(ids);
                } else if (obj.event === 'deleteBtn') {
                    _this._delete(ids);
                }
            }
        });
    },
    /**
     * 获取选中行id，以逗号分隔
     * @returns {string}
     * @private
     */
    _getCheckedValue: function (tableId) {
        var checkStatus = layui.table.checkStatus(tableId);
        var ids = '';
        $.each(checkStatus.data, function (index, item) {
            ids += item.id;
            if (index < checkStatus.data.length - 1) {
                ids += ',';
            }
        });
        return ids;
    },
    /**
     * 重置密码
     * @param ids
     * @private
     */
    _reset: function (ids) {
        var _this = this;
        $.ajax({
            url: _this.urlConfig.resetPassword,
            type: "PUT",
            data: {ids: ids},
            success: function (res) {
                layer.alert(res.msg);
            }
        });
    },
    /**
     * 删除用户信息
     * @param ids
     * @private
     */
    _delete: function (ids) {
        var _this = this;
        $.ajax({
            url: _this.urlConfig.delete,
            type: "PUT",
            data: {ids: ids},
            success: function (res) {
                layer.alert(res.msg);
                if (res.code === 0) {
                    _this._displayTable();
                }
            }
        });
    },
    /**
     * @param opt.title 页面标题
     * @param opt.content 弹出层的url
     * @param opt.area 弹出层页面比例 (['900px', '500px'])
     * @param opt.type 类型，解析url
     * @param opt.closeBtn 关闭按钮是否显示 1显示0不显示
     * @param opt.shadeClose 点击遮罩区域是否关闭页面
     * @param opt.shade 遮罩透明度
     * @private
     */
    _openFrame: function (opt) {
        var _this = this;
        var data = {
            title: "",
            content: "",
            type: 2,
            closeBtn: true,
            shadeClose: true,
            shade: 0.8,
            area: ['400px', '380px'],
            end: function () {
                _this._displayTable();
            },
            yes: function () {
                layer.closeAll();
            }
        };
        $.extend(true, data, opt);
        layer.open(data);
    }
};

customerUserIndex_obj.init();