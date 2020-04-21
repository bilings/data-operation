var builtIndex_obj = {
    urlConfig: {
        // 获取楼盘数据
        estateList: '/built/list',
        // 行政区下拉
        districtOfCity: '/setting/districtOfCity',
        // 新增
        addCommunity: '/built/add',
        // 删除
        deleteCommunity: '/built/delete'
    },
    tableIns: '',
    /**
     * 页面初始化入口
     */
    init: function () {
        this._displayTable();
        this._initBar();
        this._initSelect();
    },

    /**
     * 下拉
     * @private
     */
    _initSelect: function () {
        var _this = this;
        var cityMongoId = parent.homeIndex_obj.currentCityMongoId;
        // 行政区
        _this._initBasicSelect(_this.urlConfig.districtOfCity, {parentId: cityMongoId}, 'districtSelect', 'districtId');
    },

    /**
     * 区划下拉处理
     *
     * @param url
     * @param data
     * @param id
     * @param areaId
     * @private
     */
    _initBasicSelect: function (url, data, id, areaId) {
        var _this = this;
        $.get({
            url: url,
            data: data,
            success: function (res) {
                $('#builtIndex_wrapper #' + id).html('<option value="">选择行政区</option>');
                $.each(res.data, function (index, item) {
                    if (_this.estate && item.mongoId === _this.estate[areaId]) {
                        $('#builtIndex_wrapper #' + id).append('<option value="' + item.mongoId + '" selected="">' + item.name + '</option>');
                    } else {
                        $('#builtIndex_wrapper #' + id).append('<option value="' + item.mongoId + '">' + item.name + '</option>');
                    }
                });
                layui.form.render('select');
            }
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
            elem: '#builtIndex_wrapper #builtTable',
            url: _this.urlConfig.estateList,
            method: 'get',
            where: {
                cityId: parent.homeIndex_obj.currentCityMongoId,
                districtId: $('#builtIndex_wrapper #districtSelect').val(),
                communityName: $('#builtIndex_wrapper #name').val()
            },
            title: '楼盘信息表',
            defaultToolbar: ['filter', 'exports'],
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'name', title: '名称', width: 210},
                {field: 'districtName', title: '行政区', width: 100},
                {field: 'address', title: '参考地址', width: 350},
                {field: 'sr', title: '来源'},
                {field: 'caseNo', title: '案例数'},
                {field: 'buildType', title: '建筑类别'},
                {fixed: 'right', title: '操作', toolbar: '#lineBar', width: 200}
            ]],
            page: true,
            id: 'builtTable'
        });
    },

    /**
     * 行工具栏按钮
     * @private
     */
    _initBar: function () {
        var _this = this;
        layui.use('table', function () {
            var table = layui.table;
            //监听表格复选框选择
            // 绑定按钮
            layui.table.on('tool(builtTableFilter)', function (obj) {
                var data = obj.data;
                if (obj.event === 'addCommunity') {
                    _this._addToCommunity(data);
                } else if (obj.event === 'mergeCommunity') {
                    _this._mergeCommunity(data);
                } else if (obj.event === 'deleteCommunity') {
                    _this._deleteCommunity(data);
                }
            });
            table.on('checkbox(builtTableFilter)', function (obj) {

            });
            var $ = layui.$, active = {
                addBtnBatch: function () { //新增楼盘
                    var checkStatus = table.checkStatus('builtTable');
                    if (checkStatus.data.length === 0) {
                        layer.msg('请首先选择楼盘');
                    } else {
                        $.ajax({
                            url: _this.urlConfig.addCommunity,
                            type: 'POST',
                            contentType: 'application/json;',
                            dataType: "json",
                            data: JSON.stringify(checkStatus.data, function (key, val) {
                                if (key == 'stdAddress') {
                                    val = JSON.parse(val);
                                }
                                return val;
                            }),
                            success: function (res) {
                                if (res.code === 0) {
                                    // 刷新当前页
                                    $(".layui-laypage-btn")[0].click();
                                }else {
                                    layer.msg(res.msg);
                                }
                            }
                        });
                    }
                }
                , deleteBtnBatch: function () { //删除楼盘
                    var checkStatus = table.checkStatus('builtTable');
                    if (checkStatus.data.length === 0) {
                        layer.msg('请首先选择楼盘');
                    } else {
                        $.ajax({
                            url: _this.urlConfig.deleteCommunity,
                            type: 'POST',
                            contentType: 'application/json;',
                            dataType: "json",
                            data: JSON.stringify(checkStatus.data),
                            success: function (res) {
                                if (res.code === 0) {
                                    // 刷新当前页
                                    $(".layui-laypage-btn")[0].click();
                                }
                            }
                        });
                    }
                }
                , mergeBtnBatch: function () { //合并楼盘
                    var checkStatus = table.checkStatus('builtTable');
                    var dataIds = "";
                    var names = "";
                    if (checkStatus.data.length === 0) {
                        layer.msg('请首先选择楼盘');
                    } else {
                        for (var key in checkStatus.data) {
                            dataIds += checkStatus.data[key].id + ","
                            names += checkStatus.data[key].name + ","
                        }
                        dataIds = (dataIds.substring(dataIds.length - 1) == ',') ? dataIds.substring(0, dataIds.length - 1) : dataIds;
                        names = (names.substring(names.length - 1) == ',') ? names.substring(0, names.length - 1) : names;
                        layer.open({
                            type: 2,
                            title: '合并楼盘',
                            content: 'builtMerge.html?ids=' + dataIds + "&names=" + names,
                            area: ['75%', '75%']
                        });
                    }
                }
                , searchBtn: function () { //所搜
                    _this.tableIns.reload({
                        where: {
                            cityId: parent.homeIndex_obj.currentCityMongoId,
                            districtId: $('#builtIndex_wrapper #districtSelect').val(),
                            communityName: $('#builtIndex_wrapper #name').val(),
                        }
                    });
                }
            };
            $('.demoTable .layui-btn').on('click', function () {
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
        });
    },

    /**
     * 新增
     * @param ids
     * @param visibility
     * @private
     */
    _addToCommunity: function (data) {
        var _this = this;
        var arr = [];
        if (data.length === 0) {
            layer.msg('请首先选择楼盘');
        } else {
            arr.push(data);
            $.ajax({
                url: _this.urlConfig.addCommunity,
                type: 'POST',
                contentType: 'application/json;',
                dataType: "json",
                data: JSON.stringify(arr, function (key, val) {
                    if (key == 'stdAddress') {
                        val = JSON.parse(val);
                    }
                    return val;
                }),
                success: function (res) {
                    if (res.code === 0) {
                        // 刷新当前页
                        $(".layui-laypage-btn")[0].click();
                    }else {
                        layer.msg(res.msg);
                    }
                }
            });
        }
    },
    /**
     * 合并
     * @private
     */
    _mergeCommunity: function (data) {
        if (data.length === 0) {
            layer.msg('请首先选择楼盘');
        } else {
            layer.open({
                type: 2,
                title: '合并楼盘',
                content: 'builtMerge.html?ids=' + data.id + "&names=" + data.name,
                area: ['75%', '75%']
            });
        }
    },

    /**
     * 删除
     * @param ids
     * @private
     */
    _deleteCommunity: function (data) {
        var _this = this;
        var arr = []
        if (data.length === 0) {
            layer.msg('请首先选择楼盘');
        } else {
            arr.push(data)
            $.ajax({
                url: _this.urlConfig.deleteCommunity,
                type: 'POST',
                contentType: 'application/json;',
                dataType: "json",
                data: JSON.stringify(arr),
                success: function (res) {
                    if (res.code === 0) {
                        // 刷新当前页
                        $(".layui-laypage-btn")[0].click();
                    }
                }
            });
        }
    }
};

builtIndex_obj.init();