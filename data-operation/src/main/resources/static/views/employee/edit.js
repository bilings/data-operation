var employeeEdit_obj = {

    urlConfig: {
        findById: '/emp/findById',
        getModules: '/common/getModules',
        save: '/emp/save',
        cityList: '/city/listByOpen'
    },

    empId: layui.util.urlParam('empId'),
    cityLists:"",
    init: function () {
        this._initEmployee();
        this._initCheckbox();
        this._initSubmit();
        this._initCityList();
    },

    /**
     * 获取编辑的数据
     * @private
     */
    _initCityList: function () {
        var _this = this;
         cityLists = xmSelect.render({
            el: '#cityLists',
            autoRow: true,
            toolbar: { show: true },
            filterable: true,
            paging: true,
            remoteSearch: true,
            remoteMethod: function(val, cb, show){
                var data = {key: val,empId:_this.empId}
                console.log(val)
                //这里如果val为空, 则不触发搜索
                // if(!val){
                //     return cb([]);
                // }
                $.get({
                    url: _this.urlConfig.cityList,
                    data: data,
                    success: function (res) {
                        console.log(res)
                        cb(res.data)
                    },
                    error:function () {
                        cb([]);
                    }
                });
            },
        })
    },



    /**
     * 获取编辑的数据
     * @private
     */
    _initEmployee: function () {
        var _this = this;
        // 根据empId获取对象，自动填充表单
        $.get({
            url: _this.urlConfig.findById,
            data: {id: layui.util.urlParam('empId')},
            success: function (res) {
                // 自动填充表单
                _this._initInput(res.data);
            }
        });
    },

    /**
     * 根据编辑的数据自动填充表单
     * @param emp
     * @private
     */
    _initInput: function (emp) {
        this.empId = emp.id;
        $('#employeeEdit_wrapper #username').val(emp.username);
        $('#employeeEdit_wrapper #name').val(emp.name);
        $('#employeeEdit_wrapper #tel').val(emp.tel);
        // 更新的时候不允许修改username和密码，只能修改基本信息
        if (emp.password) {
            $('#employeeEdit_wrapper #username').prop('disabled', true);
            $('#employeeEdit_wrapper #password').val('******');
            $('#employeeEdit_wrapper #password').prop('disabled', true);
        }
        $('input[name="module"]').each(function () {
            // 新增的时候，不自动勾选
            if (!emp.moduleCodes) {
                return true;
            }
            // 更新时，没有权限，不自动勾选
            if (emp.moduleCodes.indexOf($(this).val()) === -1) {
                return true;
            }
            $(this).prop("checked", true);
        });
        layui.form.render();
    },

    /**
     * 初始化复选框（权限）的点击事件
     * @private
     */
    _initCheckbox: function () {
        layui.form.on('checkbox(permission)', function (data) {
            var start = data.value.substr(0, 1);
            // 如果是父对象，那么子对象和它一起勾选或者取消
            if (data.value.substr(1, 2) === '00') {
                $('input[name="module"]').each(function () {
                    if ($(this).val().substr(0, 1) === start) {
                        $(this).prop("checked", data.elem.checked);
                    }
                });
            }
            // 如果是子对象，并且是选择，那么父对象要一并选中
            else {
                if (data.elem.checked) {
                    $('input[name="module"]').each(function () {
                        if ($(this).val() === start + "00") {
                            $(this).prop("checked", data.elem.checked);
                        }
                    });
                }
            }
            layui.form.render();
        });
    },

    /**
     * 提交按钮
     * @private
     */
    _initSubmit: function () {
        var _this = this;
        layui.form.on('submit(save)', function () {
            // 验证成功，获取权限值
            var permission = '';
            $('input[name="module"]').each(function () {
                if ($(this).prop("checked")) {
                    permission = permission + $(this).val() + ',';
                }
            });
            if (permission === '') {
                layer.msg('请为用户选择权限.');
                return false;
            }
            // 获取城市列表
            var citys = cityLists.getValue('value');
           var data= {
                id: _this.empId,
                    username: $('#employeeEdit_wrapper #username').val(),
                    password: $('#employeeEdit_wrapper #password').val(),
                    name: $('#employeeEdit_wrapper #name').val(),
                    tel: $('#employeeEdit_wrapper #tel').val(),
                    moduleCodes: permission,
                    citys:citys
            };
            $.post({
                url: _this.urlConfig.save,
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (res) {
                    parent.employeeIndex_obj._initTable();
                    parent.layer.closeAll();
                }
            });
            return false;
        });
    }
};

employeeEdit_obj.init();