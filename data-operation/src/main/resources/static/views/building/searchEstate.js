var searchEstate_obj = {


    urlConfig: {
        // 列表
        estateList: "/estate/list",
        buildingTransfer: "/building/transfer",
    },

    /**
     * 界面js入口
     */
    init: function () {
        this._initTable();
    },

    /**
     * 初始化表格
     * @private
     */
    _initTable: function () {
        var _this = this;
        $.post({
            url: _this.urlConfig.estateList,
            data: {
               cityName: "重庆市"
                ,name:null
                ,page:1
                ,limit:10
            },
            success: function (res) {
                _this._displayTable(res, '','');
            }
        });
    },

    /**
     * 初始化搜索按钮
     * @private
     */
    _initSearch: function () {
        var _this = this;
        $('#estateSearch_wrapper #searchEstateBtn').on('click', function () {
            var districtName = $('#estateSearch_wrapper #districtName').val();
            var estateName = $('#estateSearch_wrapper #estateName').val();
            $.post({
                url: _this.urlConfig.estateList,
                data: {
                   cityName: "重庆市"
                    ,name:estateName
                    ,districtName:districtName
                    ,page:1
                    ,limit:10
                },
                success: function (res) {
                    _this._displayTable(res, districtName,estateName);
                }
            });
        });
    },

    /**
     * 初始化确认按钮
     * @private
     */
    _initQuery: function () {
        var _this = this;
        $('#estateSearch_wrapper #queryBtn').on('click', function () {
            var checkStatus = layui.table.checkStatus('estateTable')
                ,checkData = checkStatus.data;
            var transferIds = layui.util.urlParam('datas');
            if(checkData.length<=0){
                layer.msg("请选择楼盘");
                return false;
            }
            var data = {
                     estateId: checkData[0].id
                    ,transferIds:transferIds
            }
            $.post({
                url: _this.urlConfig.buildingTransfer,
                data: JSON.stringify(data),
                contentType: "application/json",
                success: function (res) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);//关闭当前页
                    parent.buildingIndex_obj._initTable();
                    layer.msg(res.msg)
                }
            });
        });
    },
    /**
     * 根据res展示表格
     * @param res
     * @param keyword
     * @private
     */
    _displayTable: function (res, districtName,estateName) {
        res = JSON.parse(res)
        var _this = this;
        // 刷新表格
        layui.table.render({
            elem: '#estateSearch_wrapper #estateTable',
            title: '楼栋数据表',
            cols: [[
                {type: 'radio', fixed: 'left'},
                //{field: 'id', title: 'ID', width: 100},
                {field: 'name', title: '楼栋名称'},
                {field: 'address', title: '详细地址'},
                {field: 'quality', title: '物业品质'},
                {field: 'districtName', title: '行政区'}
            ]],
            id: 'estateTable',
            data: res.data,
            page: true,
            done: function () {
                // 保留上次搜索关键词
               $('#estateSearch_wrapper #districtName').val(districtName);
               $('#estateSearch_wrapper #estateName').val(estateName);
                // 绑定搜索按钮
                _this._initSearch();
                // 绑定确认按钮
                _this._initQuery();

            }
        });
    }
};

searchEstate_obj.init();