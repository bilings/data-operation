var importIndex_obj = {

    urlConfig: {
        estateTemplate: '/estate/estateTemplate',
        estateUpload: '/upload/estate',
        buildingTemplate: '/building/downBuildingTemplate',
        buildingUpload: '/upload/building',
        roomTemplate: '',
        roomUpload:'',
        avgPriceTemplate: '/avg/downAvgPriceTemplate',
        avgPriceUpload: '/upload/avgPrice',
        logList: '/upload/logList',
        downloadFailed: '/upload/downloadFailed'
    },

    tableIns: null,

    init: function () {
        // 下载模板按钮
        this._initDownloadTemplate();
        // 上传楼盘按钮
        this._initUploadEstate();
        // 上传楼栋按钮
        this._initUploadBuilding();
        // 上传均价按钮
        this._initUploadAvgPrice();
        // 日志表格
        this._initTable();
        // 表头工具栏
        this._initToolbar();
    },

    /**
     * 下载模板按钮
     * @private
     */
    _initDownloadTemplate: function () {
        var _this = this;

        //楼盘模板下载
        $('#importIndex_wrapper #estateTemplate').on('click', function () {
            window.open(_this.urlConfig.estateTemplate);
        });

        // 楼栋模板下载
        $('#importIndex_wrapper #buildingTemplate').on('click', function () {
            window.open(_this.urlConfig.buildingTemplate);
        });

        // 房号模板下载
        $('#importIndex_wrapper #roomTemplate').on('click', function () {
            window.open(_this.urlConfig.roomTemplate);
        });

        // 楼盘均价模板下载
        $('#importIndex_wrapper #avgPriceTemplate').on('click', function () {
            window.open(_this.urlConfig.avgPriceTemplate);
        });
    },

    /**
     * 上传楼盘均价
     * @private
     */
    _initUploadAvgPrice: function () {
        var _this = this;
        layui.upload.render({
            elem: '#importIndex_wrapper #avgPriceUpload',
            url: _this.urlConfig.avgPriceUpload,
            exts: 'xlsx',
            method: 'post',
            done: function (res) {
                if (res.code !== 0) {
                    layui.layer.alert(res.msg);
                } else {
                    // _this.tableIns.reload();
                    _this._initTable();
                }
            }
        });
    },
    /**
     * 上传楼栋
     * @private
     */
    _initUploadBuilding: function () {
        var _this = this;
        layui.upload.render({
            elem: '#importIndex_wrapper #buildingUpload',
            url: _this.urlConfig.buildingUpload,
            exts: 'xlsx',
            method: 'post',
            done: function (res) {
                if (res.code !== 0) {
                    layui.layer.alert(res.msg);
                } else {
                    // _this.tableIns.reload();
                    _this._initTable();
                }
            }
        });
    },
    /**
     * 上传楼盘
     * @private
     */
    _initUploadEstate: function () {
        var _this = this;
        layui.upload.render({
            elem: '#importIndex_wrapper #estateUpload',
            url: _this.urlConfig.estateUpload,
            exts: 'xlsx',
            method: 'post',
            done: function (res) {
                if (res.code !== 0) {
                    layui.layer.alert(res.msg);
                } else {
                    // _this.tableIns.reload();
                    _this._initTable();
                }
            }
        });
    },

    /**
     * 展示上传历史记录
     * @private
     */
    _initTable: function () {
        var _this = this;
        var type = $('#importIndex_wrapper #type').val();
        var createName = $('#importIndex_wrapper #createName').val();
        $.post({
            url: _this.urlConfig.logList,
            method: 'post',
            data: {
                type: type,
                createName: createName
            },
            success: function (res) {
                _this.tableIns = layui.table.render({
                    elem: '#importIndex_wrapper #uploadLogTable',
                    data: res.data,
                    toolbar: '#toolbar',
                    title: '上传日志表',
                    cols: [[
                        {field: 'fileName', title: '文件名', width: 300},
                        {field: 'type', title: '类型'},
                        {field: 'total', title: '总数'},
                        {field: 'success', title: '成功'},
                        {
                            field: 'failed', title: '失败',
                            templet: function (d) {
                                return '<a href="#" onclick="importIndex_obj._downloadFailed(' + d.id + ')" style="color:blue">' + d.failed + '</a>';
                            }
                        },
                        {field: 'createName', title: '操作人'},
                        {field: 'createTime', title: '上传时间', width: 160}
                    ]],
                    page: true,
                    id: 'uploadLogTable'
                });
                // 保留之前的搜索条件
                if (type) {
                    $('#importIndex_wrapper #type').find('option[value=' + type + ']').prop("selected", true);
                }
                $('#importIndex_wrapper #createName').val(createName);
                layui.form.render();
            }
        });
    },

    /**
     * 表头工具栏
     * @private
     */
    _initToolbar: function () {
        var _this = this;
        layui.table.on('toolbar(uploadLogTable)', function (obj) {
            if (obj.event === 'searchBtn') {
                _this._initTable();
            }
        });
    },

    /**
     * 下载最近导入失败数据
     * @private
     */
    _downloadFailed: function (id) {
        window.open(this.urlConfig.downloadFailed + '?id=' + id);
    }
};

importIndex_obj.init();