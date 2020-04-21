var estateHistory_obj = {

    urlConfig: {
        mergeHistory: '/estate/mergeHistory',
        undoMerge: '/estate/undoMerge',
        virtualHistory1: '/estate/virtualHistory1',
        virtualHistory2: '/estate/virtualHistory2',
        undoVirtual: '/estate/undoVirtual'
    },

    init: function () {
        this._initMergeHistoryTable();
        // 虚拟至其他楼盘
        this._initVirtualHistoryTable(this.urlConfig.virtualHistory1, 'virtualTable1', "virtualFromLineBar");
        // 虚拟到该楼盘
        this._initVirtualHistoryTable(this.urlConfig.virtualHistory2, 'virtualTable2', "virtualToLineBar");
    },

    mergeTable: null,

    /**
     * 合并历史
     * @private
     */
    _initMergeHistoryTable: function () {
        var _this = this;
        $.get({
            url: _this.urlConfig.mergeHistory,
            data: {id: layui.util.urlParam('id')},
            success: function (res) {
                // 填充表格
                _this.mergeTable = layui.table.render({
                    elem: '#estateHistory_wrapper #mergeTable',
                    title: '合并历史',
                    data: res.data,
                    cols: [[
                        {field: 'name', title: '名称'},
                        {field: 'districtName', title: '行政区'},
                        {field: 'sr', title: '来源'},
                        {field: 'address', title: '地址'},
                        {fixed: 'right', title: '', toolbar: '#mergeLineBar', width: 100}
                    ]],
                    id: 'mergeTable',
                    page: false
                });
                // 行工具条
                _this._initMergeLineBar();
            }
        });
    },

    /**
     * 解除合并
     * @private
     */
    _initMergeLineBar: function () {
        var _this = this;
        layui.table.on('tool(mergeTable)', function (obj) {
            var data = obj.data;
            //询问框
            layer.confirm('确认解除楼盘' + data.name, {
                btn: ['确定', '取消'] //按钮
            }, function () {
                if (obj.event === 'undoMerge') {
                    $.ajax({
                        url: _this.urlConfig.undoMerge,
                        type: "PUT",
                        data: {fromId: data.id, toId: layui.util.urlParam('id')},
                        success: function () {
                            layer.close(layer.index);
                            _this._initMergeHistoryTable();
                        }
                    });
                }
            }, function () {
                console.log("精神小伙。取消操作")
            });

        });
    },

    /**
     * 虚拟历史
     * @private
     */
    _initVirtualHistoryTable: function (url, virtualTable, virtualLineBar) {
        var _this = this;
        $.get({
            url: url,
            data: {id: layui.util.urlParam('id')},
            success: function (res) {
                // 填充表格
                layui.table.render({
                    elem: '#estateHistory_wrapper #' + virtualTable,
                    title: '虚拟历史',
                    data: res.data,
                    cols: [[
                        {field: 'name', title: '名称'},
                        {field: 'districtName', title: '行政区'},
                        {field: 'sr', title: '来源'},
                        {field: 'address', title: '地址'},
                        {fixed: 'right', title: '', toolbar: '#' + virtualLineBar, width: 100}
                    ]],
                    id: virtualTable,
                    page: false
                });
                // 行工具条
                _this._initVirtualLineBar();
            }
        });
    },

    /**
     * 解除虚拟
     * @private
     */
    _initVirtualLineBar: function () {
        var _this = this;
        layui.table.on('tool(virtualTable1)', function (obj) {
            var data = obj.data;
            layer.confirm('确认解除虚拟 ' + data.name, {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    url: _this.urlConfig.undoVirtual,
                    type: "PUT",
                    data: {type: 'from', id:layui.util.urlParam('id')},
                    success: function () {
                        layer.close(layer.index);
                        _this._initVirtualHistoryTable(_this.urlConfig.virtualHistory1, 'virtualTable1', 'virtualFromLineBar');
                    }
                });
            }, function () {
                console.log("精神小伙。取消操作")
            });
        });

        layui.table.on('tool(virtualTable2)', function (obj) {
            var data = obj.data;
            layer.confirm('确认解除虚拟 ' + data.name, {
                btn: ['确定', '取消'] //按钮
            }, function () {
                $.ajax({
                    url: _this.urlConfig.undoVirtual,
                    type: "PUT",
                    data: {type: 'to', id: data.id},
                    success: function () {
                        layer.close(layer.index);
                        _this._initVirtualHistoryTable(_this.urlConfig.virtualHistory2, 'virtualTable2', 'virtualToLineBar');
                    }
                });
            }, function () {
                console.log("精神小伙。取消操作")
            });
        });
    }
};

estateHistory_obj.init();