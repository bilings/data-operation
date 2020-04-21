var builtMergeIndex_wrapper = {
    urlConfig: {
        // 获取楼盘数据
        estateList: '/built/list',
        // 行政区下拉
        districtOfCity: '/setting/districtOfCity',
        // 合并
        mergeCommunity: '/built/merge',
        //获取楼盘对比数据
        waitMergeList: '/built/waitMergeList',
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
        var cityMongoId = parent.parent.homeIndex_obj.currentCityMongoId;
        // 行政区
        $common.initBaseSelect(_this.urlConfig.districtOfCity, {parentId: cityMongoId}, 'districtSelect', '行政区');
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
                $('#builtRecommendMergeIndex_wrapper #' + id).html('<option value="">选择行政区</option>');
                $.each(res.data, function (index, item) {
                    if (_this.estate && item.mongoId === _this.estate[areaId]) {
                        $('#builtRecommendMergeIndex_wrapper #' + id).append('<option value="' + item.mongoId + '" selected="">' + item.name + '</option>');
                    } else {
                        $('#builtRecommendMergeIndex_wrapper #' + id).append('<option value="' + item.mongoId + '">' + item.name + '</option>');
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
            elem: '#builtRecommendMergeIndex_wrapper #builtMergeTable',
            url: _this.urlConfig.waitMergeList,
            method: 'get',
            where: {
                cityId: parent.parent.homeIndex_obj.currentCityMongoId,
                districtId: $('#builtRecommendMergeIndex_wrapper #districtSelect').val(),
                communityName: $('#builtRecommendMergeIndex_wrapper #name').val()
            },
            title: '楼盘信息表',
            even: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'wName', title: '待合并楼盘', width: 210},
                {field: 'wDistrictName', title: '行政区',width: 100},
                {field: 'wAddress', title: '参考地址', },
                {field: 'wResource', title: '来源'},
                {field: 'wCaseNo', title: '案例数'},
                {field: 'tName', title: '目标楼盘', width: 210},
                {field: 'tDistrictName', title: '行政区',width: 100},
                {field: 'tAddress', title: '参考地址' },
                {field: 'tResource', title: '来源'},
                {field: 'tCaseNo', title: '案例数'},
                {field: 'nameScore', title: '楼盘名推荐分'},
                {field: 'addressScore', title: '地址推荐分'},
                {fixed: 'right', title: '操作', toolbar: '#lineBar'}
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
        layui.use('table', function(){
            var table = layui.table;
            //监听表格复选框选择
            // 绑定按钮
            layui.table.on('tool(builtTableFilter)', function (obj) {
                var data = obj.data;
                if (obj.event === 'mergeCommunity') {
                    _this._mergeCommunity(data);
                }
            });
            table.on('checkbox(builtTableFilter)', function(obj){

            });
            var $ = layui.$, active = {
                mergeBtnBatch: function(){ //合并楼盘
                    let checkStatus = layui.table.checkStatus('builtTable');
                    if (checkStatus.data.length === 0) {
                        layer.msg('请首先选择楼盘');
                    }else{
                        let data = checkStatus.data;
                        let bl = [];
                        $.each(data,function(i,ite){
                            let b = {};
                            b.targetId = ite.tId;
                            b.waitId = ite.wId;
                            bl.push(b);
                        });

                        $.ajax({
                            url: _this.urlConfig.mergeCommunity,
                            type: 'POST',
                            contentType: 'application/json;',
                            dataType: "json",
                            data: JSON.stringify(bl),
                            success: function (res) {
                                if (res.code === 0) {
                                    // 刷新当前页
                                    $(".layui-laypage-btn")[0].click();
                                }
                            }
                        });
                    }
                    }
                ,searchBtn: function(){ //所搜
                    _this.tableIns.reload({
                        where: {
                            cityId: parent.homeIndex_obj.currentCityMongoId,
                            districtId: $('#builtRecommendMergeIndex_wrapper #districtSelect').val(),
                            nameScore: $('#builtRecommendMergeIndex_wrapper #name').val(),
                            addressScore: $('#builtRecommendMergeIndex_wrapper #address').val(),
                        }
                    });
                }
            };
            $('.demoTable .layui-btn').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
        });
    },

    /**
     * 合并
     * @private
     */
    _mergeCommunity: function (data) {
        var _this = this;
        if (data.length === 0) {
            layer.msg('请首先选择楼盘');
        } else {
            console.log(data);
            let param = [];
            let b = {};
            b.targetId = ite.tId;
            b.waitId = ite.wId;
            param.push(b);
            $.ajax({
                url: _this.urlConfig.mergeCommunity,
                type: 'POST',
                contentType: 'application/json;',
                dataType: "json",
                data: JSON.stringify(param),
                success: function (res) {
                    if (res.code === 0) {
                        // 刷新当前页
                        $(".layui-laypage-btn")[0].click();
                    }
                }
            });
        }
    },

};

builtMergeIndex_wrapper.init();