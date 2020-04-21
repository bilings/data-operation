var buildingEdit_obj = {
    urlConfig: {
        findById: '/building/findById',
        dictionary: '/setting/dictionary',
        boolOption: '/setting/boolOption',
        liftOption: '/setting/liftOption',
        save: '/building/save'
    },

    building: '',
    id:layui.util.urlParam('id'),
    init: function () {
        this._showHeadInfo();
        this._initIcon();
    },

    /**
     * 楼盘信息
     * @private
     */
    _showHeadInfo: function () {
        var _this = this;
        //如果id等于newId 说明是新增楼栋。其他为修改楼栋
        if(layui.util.urlParam('id')=="newId"){
            $('#buildingEdit_wrapper #communityId').val(layui.util.urlParam('communityId'));
            // 保存按钮
            _this._initButton();
            // 下拉框填充
            _this._initSelect();
            // 多选框填充
            _this._initCheckbox();
        }else{
            $.post({
                url: _this.urlConfig.findById,
                data: {id: layui.util.urlParam('id')},
                success: function (res) {
                    // 设置楼盘对象
                    _this.building = res.data;
                    // 页面顶的名称 + 地址
                    $('#buildingEdit_wrapper #nameSpan').html(res.data.name);
                    $('#buildingEdit_wrapper #addrDetailSpan').html(res.data.addrDetail);
                    // 设置界面上的初始值（仅限于输入框，下拉通过下面操作）
                    $.each($('#buildingEdit_wrapper [data-name=label-value]'), function (index, item) {
                        var id = item.id;
                        if (res.data[id]) {
                            $('#buildingEdit_wrapper #' + id).val(res.data[id]);
                        }
                    });
                    // 保存按钮
                    _this._initButton();
                    // 下拉框填充
                    _this._initSelect();
                    // 多选框填充
                    _this._initCheckbox();

                    _this._roomListClick();

                }
            });
        }

    },

    /**
     * 保存按钮
     * @private
     */
    _initButton: function () {
        var _this = this;
        layui.form.on('submit(saveBtn)', function(data){
            var resData = data.field;
            var quality = _this._getCheckboxValue('quality');
            resData["quality"] = quality;
            $.post({
                url: _this.urlConfig.save,
                data: JSON.stringify(resData),
                contentType: "application/json",
                success: function (res) {
                    if(res.code==1){
                        layer.msg('保存成功');
                    }else{
                        layer.msg(res.msg);
                    }

                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    },
    _roomListClick:function(){
      var _this=this;
      $("#roomList").on("click",function () {
          _this._gotoRoom(_this.building.id,_this.building.unitNo,_this.building.roomNoPerFloor,_this.building,buildingName);
      });
    },

    /**
     * 初始化搜索条按钮
     * @private
     */
    _initIcon: function () {
        var _this = this;
        $('#buildingEdit_wrapper #nameIcon').on('click', function () {
            layer.open({
                type: 2,
                title: '数据来源',
                content: 'log.html?buildingId='+_this.id+"&type=nameIcon",
                area: ['97%', '97%']
            });
        });

    },
    /**
     * 回到详细信息页
     * @private
     */
    _backToDetail: function () {
        var tabId = $('#buildingEdit_wrapper #nameSpan').text();
        var id = layui.util.urlParam('id');
        parent.document.getElementById(tabId).src = '../estate/edit.html?id=' + id;
    },

    /**
     * 下拉
     * @private
     */
    _initSelect: function () {
        var _this = this;
        // 外墙装饰
        _this._initAdvanceSelect(_this.urlConfig.dictionary, {category: '外墙装饰'}, 'wallDecoration', 'item');
        // 公共装修档次
        _this._initAdvanceSelect(_this.urlConfig.dictionary, {category: '公共装修档次'}, 'publicDecoration', 'item');
        // 建筑类别
        _this._initAdvanceSelect(_this.urlConfig.dictionary, {category: '建筑类别'}, 'buildingType', 'item');
        // 电梯品牌
        _this._initAdvanceSelect(_this.urlConfig.dictionary, {category: '电梯品牌'}, 'liftBrand', 'item');
        // 建筑结构
        _this._initAdvanceSelect(_this.urlConfig.dictionary, {category: '建筑类型'}, 'structure', 'item');
        // 是否可估
        _this._initAdvanceSelect(_this.urlConfig.boolOption, {category: 'bool'}, 'canEvaluate', 'value');
        // 是否完善
        _this._initAdvanceSelect(_this.urlConfig.boolOption, {}, 'isPerfect', 'value');
        // 有无电梯
        _this._initAdvanceSelect(_this.urlConfig.liftOption, {}, 'haveLift', 'value');

    },
    /**
     * value和text不同的下拉
     * @param url
     * @param data
     * @param id 控件的id
     * @param key 返回的数据中作为值的属性名
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
                    if (item.id == _this.building[id]) {
                        $('#buildingEdit_wrapper #' + id).append('<option value="' + item.id + '" selected="">' + item[key] + '</option>');
                    } else {
                        $('#buildingEdit_wrapper #' + id).append('<option value="' + item.id + '">' + item[key] + '</option>');
                    }
                });
                layui.form.render('select');
            }
        });
    },

    /**
     * 初始化单选
     * @private
     */
    _initCheckbox: function () {
        var _this = this;
        // 住宅品质
        _this._initAdvanceCheckbox(_this.urlConfig.dictionary, {category: '住宅品质'}, 'quality');
    },

    /**
     * 单选住宅品质
     * @param url
     * @param data
     * @param id
     * @private
     */
    _initAdvanceCheckbox: function (url, data, id) {
        var _this = this;
        $.get({
            url: url,
            data: data,
            success: function (res) {
                $.each(res.data, function (index, item) {
                    var checkbox;
                    if (_this.building[id] && _this.building[id].indexOf(item.id) !== -1) {
                        checkbox = '<input type="radio" name="' + id + '" value="' + item.id + '" lay-skin="primary" title="' + item.item + '" checked=""">';
                    } else {
                        checkbox = '<input type="radio" name="' + id + '" value="' + item.id + '" lay-skin="primary" title="' + item.item + '">';
                    }
                    $('#buildingEdit_wrapper #' + id).append(checkbox);
                });
                layui.form.render();
            }
        });
    },

    /**
     * 获取checkbox的值
     * @param name
     * @returns {string}
     * @private
     */
    _getCheckboxValue: function (name) {
        var value = '';
        $('input[name="' + name + '"]').each(function () {
            if ($(this).prop("checked")) {
                value = value + $(this).val();
            }
        });
        return value;
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
};
//时间
layui.laydate.render({
    elem: '#builtDate'
    ,type: 'month'
});
buildingEdit_obj.init();