var builtMerge_obj = {
    urlConfig: {
        estateList: '/estate/list',
        estateDetail: '/estate/detail',
        districtOfCity: '/setting/districtOfCity',
        mergeCommunity: '/built/merge'

    },

    init: function () {
        this._initTable();
        this._initToolbar();
        this._initSelect();
    },

    /**
     * 表格全局变量
     */
    tableIns: null,


    /**
     * 下拉
     * @private
     */
    _initSelect: function () {
        var _this = this;
        var cityMongoId = parent.parent.homeIndex_obj.currentCityMongoId;
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
                $('#builtMerge_wrapper #' + id).html('<option></option>');
                $.each(res.data, function (index, item) {
                    if (_this.estate && item.id === _this.estate[areaId]) {
                        $('#builtMerge_wrapper #' + id).append('<option value="' + item.id + '" selected="">' + item.name + '</option>');
                    } else {
                        $('#builtMerge_wrapper #' + id).append('<option value="' + item.id + '">' + item.name + '</option>');
                    }
                });
                layui.form.render('select');
            }
        });
    },

    /**
     * 初始化表格
     * @private
     */
    _initTable: function () {
        this.tableIns = layui.table.render({
            elem: '#builtMerge_wrapper #estateTable',
            toolbar: '#toolbar',
            title: '选择楼盘',
            defaultToolbar: [],
            cols: [[
                {
                    field: 'radio', title: '', width: 50,
                    templet: function (d) {
                        return '<input type="radio" name="id" value="' + d.id + '">';
                    }
                },
                {
                    field: 'name', title: '名称',
                    templet: function (d) {
                        var a = d.name;
                        if (d.isVirtual) {
                            var badge = '&nbsp;<span class="layui-badge layui-bg-cyan">虚</span>';
                            a = a + badge;
                        }
                        return a;
                    }
                },
                {field: 'districtName', title: '行政区',width: 100},
                { field: 'address', title: '地址' },
                {field: 'totalNumberOfBuildings', title: '已建楼栋',width: 100},
                {field: "siteName", title: "数据来源",width: 200}
            ]],
            id: 'estateTable',
            page: true
        });
    },

    /**
     * 初始化toolbar
     * @private
     */
    _initToolbar: function () {
        var _this = this;
        // 默认在输入框填写所选的第一个楼盘的名称
        var ids = layui.util.urlParam('ids');
        var names = layui.util.getQueryString('names');
        console.log(names)
        var name = names.split(',')[0];
        $('#builtMerge_wrapper #keyword').val(name);
        _this.tableIns.reload({
            url: _this.urlConfig.estateList,
            where: {
                cityId: parent.parent.homeIndex_obj.currentCityMongoId,
                name: name
            },
            method: 'get',
            done: function () {
                // 保留搜索关键词
                $('#builtMerge_wrapper #keyword').val(name);
            }
        });
        // 绑定按钮点击
        layui.table.on('toolbar(estateTableFilter)', function (obj) {
            if (obj.event === 'searchBtn') {
                var keyword = $('#builtMerge_wrapper #keyword').val();
                var districtId = $('#builtMerge_wrapper #districtSelect').val();
                _this.tableIns.reload({
                    url: _this.urlConfig.estateList,
                    where: {
                        cityId: parent.parent.homeIndex_obj.currentCityMongoId,
                        name: keyword,
                        districtId: districtId
                    },
                    method: 'get',
                    done: function () {
                        // 行政区
                        _this._initBasicSelect(_this.urlConfig.districtOfCity, {parentId: _this.cityMongoId}, 'districtSelect', 'districtId',districtId);
                        // 保留搜索关键词
                        $('#builtMerge_wrapper #keyword').val(keyword);
                    }
                });
            } else if (obj.event === 'mergeBtn') {
                var toId = $('#builtMerge_wrapper input[name="id"]:checked ').val();
                if (!toId) {
                    layer.msg('请选择楼盘');
                    return;
                }
                $.ajax({
                    url: _this.urlConfig.mergeCommunity,
                    type: "POST",
                    data: {fromIds: ids, toId: toId},
                    success: function () {
                        _this._timerAndClose();
                    }
                });
            }
        });
    },

    _timerAndClose: function () {
        var i = 3;
        var interval;
        layer.confirm('合并完成', {
            btn: [i + 1 + 's后关闭'], //按钮
            skin: 'layui-layer-molv',
            success: function (a, b) {
                $(".layui-layer-btn0").css("backgroundColor", "#92B8B1");
                var fn = function () {
                    $(".layui-layer-btn0").text(i + 's后关闭');
                    i--;
                };
                interval = setInterval(function () {
                    fn();
                    if (i === 0) {
                        $(".layui-layer-btn0").css("backgroundColor", "#019F95");
                        clearInterval(interval);
                        // 刷新父页面表格
                        parent.builtIndex_obj._displayTable();
                        // 关闭本层
                        layer.closeAll();
                        // 关闭弹出层
                        parent.layer.closeAll();
                    }
                }, 1000);
            },
            end: function () {
                clearInterval(interval);
            }
        });
    }
};

builtMerge_obj.init();