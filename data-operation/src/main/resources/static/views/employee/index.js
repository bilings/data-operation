var employeeIndex_obj = {

    urlConfig: {
        // 员工列表
        employeeList: "/emp/list",
        deleteEmployee: "/emp/delete"

        // 仅用于测试事务
        , testTransaction: "/emp/testTransaction"
    },

    /**
     * 界面js入口
     */
    init: function () {
        this._initTable();
        this._initTestTransaction();
    },

    /**
     * 初始化表格
     * @private
     */
    _initTable: function () {
        var _this = this;
        _this._displayTable( );

    },

    /**
     * 初始化测试事务按钮
     * @private
     */
    _initTestTransaction: function () {
        var _this = this;
        $('#employeeIndex_wrapper #test').on('click', function () {
            $.post({
                url: _this.urlConfig.testTransaction,
                success: function (res) {
                    layer.msg(res.msg);
                }
            });
        });
    },

    /**
     * 初始化搜索按钮
     * @private
     */
    _initSearch: function () {
        var _this = this;
        $('#employeeIndex_wrapper #searchButton').on('click', function () {
            _this._displayTable();
        });
    },

    /**
     * 根据res展示表格
     * @param res
     * @param keyword
     * @private
     */
    _displayTable: function () {
        var _this = this;
        // 刷新表格
        layui.table.render({
            elem: '#employeeIndex_wrapper #employeeTable',
            url: _this.urlConfig.employeeList,
            toolbar: '#employeeToolbar',
            title: '用户数据表',
            where: {
                keyword : $('#employeeIndex_wrapper #keyword').val(),
            },
            cols: [[
                // {type: 'checkbox', fixed: 'left'},
                {field: 'id', title: 'ID', width: 100},
                {field: 'username', title: '用户名'},
                {field: 'name', title: '名称'},
                {field: 'tel', title: '电话'},
                {field: 'createTime', title: '创建时间'},
                {fixed: 'right', title: '操作', toolbar: '#employeeBar'}
            ]],
            id: 'employeeTable',
            page: true,
            done: function () {
                // 保留上次搜索关键词
                $('#employeeIndex_wrapper #keyword').val( $('#employeeIndex_wrapper #keyword').val());
                // 绑定搜索按钮
                _this._initSearch();

                // 绑定新增按钮
                $('#employeeIndex_wrapper #newEmployee').on('click', function () {
                    layer.open({
                        type: 2,
                        title: '用户详情',
                        content: 'edit.html',
                        area: ['90%', '90%']
                    });
                });

                // 绑定编辑按钮
                layui.table.on('tool(employeeTable)', function (obj) {
                    var data = obj.data;
                    if (obj.event === 'deleteEmployee') {
                        layer.confirm('真的删除用户吗？', function (index) {
                            $.post({
                                url: _this.urlConfig.deleteEmployee,
                                data: {id: data.id},
                                success: function () {
                                    layer.msg('删除成功');
                                    layer.close(index);
                                    _this._initTable();
                                }
                            });
                        });
                    } else if (obj.event === 'editEmployee') {
                        layer.open({
                            type: 2,
                            title: '用户详情',
                            content: 'edit.html?empId=' + data.id,
                            area: ['90%', '90%']
                        });
                    }
                });

                // 绑定删除按钮

                // 绑定测试事务按钮
                _this._initTestTransaction();
            }
        });
    }
};

employeeIndex_obj.init();