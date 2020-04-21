var buildingIndex_obj = {

    urlConfig: {
        // 列表
        buildingList: "/building/list",
        buildingHide: "/building/hide",
        buildingShow: "/building/show",
        buildingDelete: "/building/delete",
        buildingUnDelete: "/building/undelete",
        buildingFindById: "/building/findById",
        buildingCopy: "/building/copy",
        dictionary: '/setting/dictionary',
    },
    communityId: layui.util.urlParam('communityId'),
    tableIns: '',
    /**
     * 界面js入口
     */
    init: function () {
        var _this = this;
        _this._initTable();
        // 绑定倒出按钮
        _this._initExportBtn()
        // 绑定搜索按钮
        _this._initSearch();
        //加载下拉菜单数据
        _this._initSelect();
    },

    /**
     * 初始化表格
     * @private
     */
    _initTable: function () {
        var _this = this;
        _this._displayTable();
    },

    /**
     * 初始化导出按钮
     * @private
     */
    _initExportBtn: function () {
        var _this = this;
        $('#buildingIndex_wrapper #exportBuilding').on('click', function () {
            layui.layer.open({
                type: 2,
                title: '导出条件',
                content: 'export.html?estateId=' + _this.communityId,
                area: ['97%', '97%']
            });
            return false;
        });
    },

    /**
     * 初始化搜索按钮
     * @private
     */
    _initSearch: function () {
        var _this = this;
        $('#buildingIndex_wrapper #searchBtn').on('click', function () {

           var buildingName = $("#buildingIndex_wrapper #buildingName").val();
           var buildingType = $("#buildingIndex_wrapper #buildingType").val();
           var isDeleted = $("#buildingIndex_wrapper #isDeleted").val();
           var visibility = $("#buildingIndex_wrapper #visibility").val();
           _this.tableIns.reload({
               where: {
                   communityId: _this.communityId,
                   buildingName: buildingName,
                   buildingType: buildingType,
                   isDeleted: isDeleted,
                   visibility: visibility
               }
           });
       });
    },

    /**
     * 初始化加载下拉菜单数据
     *
     */
    _initSelect: function () {
        // 建筑类别
        var _this = this;
        _this._initAdvanceSelect(_this.urlConfig.dictionary, {category: '建筑类别'}, 'buildingType', 'item');
    },

    /**
     * 根据res展示表格
     * @param res
     * @param keyword
     * @private
     */
    _displayTable: function (res, name, address, quality, visibility) {
        var _this = this;
        var _table = layui.table;
        // 刷新表格
        _this.tableIns = layui.table.render({
            elem: '#buildingIndex_wrapper #buildingTable',
            toolbar: '#buildingToolbar',
            title: '楼栋数据表',
            url: _this.urlConfig.buildingList,
            method: 'GET',
            where: {
                communityId: _this.communityId,
                buildingName: $("#buildingIndex_wrapper #buildingName").val(),
                buildingType: $("#buildingIndex_wrapper #buildingType").val(),
                isDeleted: $("#buildingIndex_wrapper #isDeleted").val(),
                visibility: $("#buildingIndex_wrapper #visibility").val()
            },
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {
                    field: 'buildingName', title: '楼栋名称', templet: function (d) {
                        return '<a href="javascript:;" style="color: blue;" onclick="buildingIndex_obj._gotoDetail(\'' + d.id + '\',\'' + d.buildingName + '\')">' + d.buildingName + '</a>'
                    }
                },
                {
                    field: 'totalFloor', title: '楼层（地上/总）', templet: function (d) {
                        return d.overGroundFloor + "/" + d.totalFloor
                    }
                },
                {field: 'address', title: '详细地址'},
                {field: 'builtDate', title: '竣工日期',templet:function (dd) {
                        if(dd.builtDate>0){

                            var date = new Date(dd.builtDate);
                            var year = date.getFullYear();
                            var month = date.getMonth()+1;
                            month = month < 10 ? "0"+month:month;
                            return year+"-"+month;
                        }
                        return  "";
                    }
                },
                {field: 'priceCoe', title: '价格系数'},
                {
                    field: 'roomNo', title: '房号总数', templet: function (d) {
                        if (d.roomNo) {
                            return '<a href="#" style="color: blue;" onclick="buildingIndex_obj._gotoRoom(\'' + d.id + '\',\'' + d.unitNo + '\',\'' + d.roomNoPerFloor + '\',\'' + d.buildingName + '\')"> ' + d.roomNo + '</a>'
                        } else {
                            return '<a href="#" style="color: blue;" onclick="buildingIndex_obj._gotoRoom(\'' + d.id + '\',\'' + d.unitNo + '\',\'' + d.roomNoPerFloor + '\',\'' + d.buildingName + '\')">0</a>'
                        }

                    }
                },
                {field: 'dataFrom', title: '来源'},
                {
                    field: 'isDeleted', title: '状态',templet:function (d) {
                        if (d.isDeleted==1) return'<a style="color:red;">删除</a>';
                        else return '<a style="color:green;">正常</a>';
                    }
                },
                {fixed: 'right', title: '操作', toolbar: '#buildingBar'}
            ]],
            id: 'buildingTable',
            page: true,
            done: function () {
                // 工具栏事件 表头按钮触发事件
                layui.table.on('toolbar(buildingTable)', function (obj) {
                    var checkStatus = layui.table.checkStatus(obj.config.id);
                    switch (obj.event) {
                        case 'newbuilding'://新增楼栋
                            _this._gotoDetail("newId", "新增楼栋", _this.communityId);
                            break;
                        case 'batchhidebuilding'://批量隐藏
                            var ids = _this._getCheckedValue();
                            _this._updateBuildingState(ids,_this.urlConfig.buildingHide);
                            break;
                        case 'batchshowbuilding'://批量取消隐藏
                            var ids = _this._getCheckedValue();
                            _this._updateBuildingState(ids,_this.urlConfig.buildingShow);
                            break;
                        case 'batchtransferbuilding'://批量转移
                            var data = checkStatus.data;
                            if (data.length <= 0) {
                                layer.msg("请选择楼栋")
                                return false;
                            }
                            var dataIds = [];
                            for (var i = 0; i < data.length; i++) {
                                dataIds.push(data[i].id)
                            }
                            layer.open({
                                type: 2,
                                title: '楼栋详情',
                                content: 'searchEstate.html?datas=' + dataIds,
                                area: ['90%', '90%'],
                            });
                            break;
                        case 'fillbuilding'://填充
                            var data = checkStatus.data;
                            if (data.length <= 0) {
                                layer.msg("请选择楼栋")
                                return false;
                            }
                            var dataNames = [];
                            var dataIds = [];
                            for (var i = 0; i < data.length; i++) {
                                dataNames.push(data[i].name);
                                dataIds.push(data[i].id);
                            }
                            layer.open({
                                type: 2,
                                title: '楼栋详情',
                                content: 'fill.html?buildingNames=' + dataNames + "&dataIds=" + dataIds + "&esId=" + data[0].estateId,
                                area: ['90%', '90%'],
                            });
                            break;
                        case 'deletebuilding'://批量删除
                            var ids = _this._getCheckedValue();
                            _this._updateBuildingState(ids,_this.urlConfig.buildingDelete);
                            break;
                        case 'undeletebuilding'://批量取消删除
                            var ids = _this._getCheckedValue();
                            _this._updateBuildingState(ids,_this.urlConfig.buildingUnDelete);
                            break;
                    }
                    ;
                });

                /**
                 * 行工具栏事件,绑定隐藏按钮
                 */
                _table.on('tool(buildingTable)', function (obj) {

                    if (obj.event === 'copybuilding') {
                        $.post({
                            url: _this.urlConfig.buildingCopy,
                            data: JSON.stringify(obj.data),
                            contentType: "application/json",
                            success: function () {
                                layer.msg('复制成功');
                                _this._initTable();
                            }
                        });
                    } else if (obj.event === 'islookbuilding') {//隐藏1
                        var data = obj.data;
                        var url = '';
                        if ($(this).find('i').attr('class').indexOf('layui-icon-ok-circle') > -1) {
                            url = _this.urlConfig.buildingHide
                        } else {
                            url = _this.urlConfig.buildingShow
                        }
                        _this._updateBuildingState(data.id,url);
                    }
                });
            }
        });
    },
    _getCheckedValue: function () {
        var checkStatus = layui.table.checkStatus('buildingTable');
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
     * 隐藏/可见
     * @param ids
     * @param visibility
     * @private
     */
    _updateBuildingState: function (ids, url) {
        var _this=this;
        if (ids.length === 0) {
            layer.msg('请首先选择楼盘');
        } else {
            $.ajax({
                url: url,
                type: 'POST',
                data: {ids: ids},
                success: function (res) {
                    if (res.code === 0) {
                        // 刷新当前页
                        _this._initTable();
                    }
                }
            });
        }
    },

    /**
     * 跳转到楼栋详情
     * @param id
     * @param name
     * @private
     */
    _gotoDetail: function (id, name, esId) {
        parent.homeIndex_obj._addTab(name, '../building/edit.html?id=' + id + "&communityId=" + esId, false, '');
    },
    /**
     * 跳转到房号
     * @param id
     * @private
     */
    _gotoRoom: function (id, unit, roomNoPerFloor, name) {
        // 新增一个Tab项
        parent.layui.element.tabAdd('tabs', {
            title: '房号管理',
            content: '<iframe id="房号管理" src="../room/index.html?buildId=' + id + '&unit=' + unit + '&roomNoPerFloor=' + roomNoPerFloor + '"></iframe>',
            id: '房号管理'
        });
        // 切换到新增的Tab
        parent.layui.element.tabChange('tabs', '房号管理');
    },
    /**
     * 加载下拉菜单的函数
     * @private
     */
    _initAdvanceSelect: function (url, data, id, key) {
        var _this = this;
        $.get({
            url: url,
            data: data,
            success: function (res) {
                $.each(res.data, function (index, item) {
                    // 本项目中，这种情况下数据库都是存的id (用两个==，不判断类型)
                    $('#buildingIndex_wrapper #' + id).append('<option value="' + item.id + '">' + item[key] + '</option>');
                });
                layui.form.render('select');
            }
        });
    }
};

buildingIndex_obj.init();