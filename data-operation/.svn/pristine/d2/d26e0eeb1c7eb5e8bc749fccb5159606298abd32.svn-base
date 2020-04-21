var addGroupIndex_obj = {
    urlConfig: {
        // 列表
        estateList: '/estate/list',
        districtOfCity: '/setting/districtOfCity',
        blockOfCity: '/setting/blockOfCity',
        save: '/coe/group/add'
    },
     cityName:layui.util.getQueryString("cityName"),
     cityId:layui.util.getQueryString("cityId"),
     coeType:layui.util.getQueryString("coeType"),
    /**
     * 界面js入口
     */
    init: function () {
        var _this = this;
        // 搜索
        _this._initAutocomplete();
        //初始化行政区
        _this._initBasicSelect(_this.urlConfig.districtOfCity, {parentId: _this.cityId}, 'administrativeId');

        //初始化板块
        //_this._initBasicSelect(_this.urlConfig.blockOfCity, {cityName: _this.cityName}, 'areaId');
        _this._initCity();
        _this._initSaveBtn();

        _this.districtOfCityChange();
    },

    /**
     * 保存按钮
     * @private
     */
    _initSaveBtn: function () {
        var _this = this;
        layui.form.on('submit(saveBtn)', function (data) {
            var resData = data.field;
            console.log(resData)
            $.post({
                url: _this.urlConfig.save,
                data: JSON.stringify(resData),
                contentType: "application/json",
                success: function (res) {
                    if (res.code == 1) {
                        layer.msg('保存成功');
                    } else {
                        layer.msg(res.msg);
                    }
                }
            });
            return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        });
    },
    /**
     * 初始化城市
     * @private
     */
    _initCity: function () {
        var _this = this;
        $('#addGroupIndex_wrapper #cityName').val(_this.cityName);
        $('#addGroupIndex_wrapper #cityId').val(_this.cityId);
        //初始化系数类型
        $('#addGroupIndex_wrapper #coeType').val(_this.coeType);
    },
    /**
     * 加载行政区
     * value和text相同的下拉
     * @param url
     * @param data
     * @param id
     * @private
     */
    _initBasicSelect: function (url, data, id) {
        var _this = this;
        $.get({
            url: url,
            data: data,
            success: function (res) {
                $('#addGroupIndex_wrapper #' + id).html('<option value="">请选择</option>');
                $.each(res.data, function (index, item) {
                    $('#addGroupIndex_wrapper #' + id).append('<option value="' + item.id + '">' + item.name + '</option>');
                });

                if (id==""){


                }
                layui.form.render('select');


            }
        });
    },
    //区级联
    districtOfCityChange:function(){
        var _this=this;
        form.on('select(administrativeId)', function(data){
            var districtVal=data.value;
            _this._initBasicSelect(_this.urlConfig.blockOfCity, {cityId: districtVal}, 'areaId');
        });
    },
    _initAutocomplete: function () {
        var _this = this;
        $("#addGroupIndex_wrapper #name").autocomplete({
            source: function (request, response) {
                $.ajax({
                    method: 'post',
                    url: '/estate/list',
                    dataType: "json",
                    data: {
                        cityName: _this.cityName,
                        districtId:$("#administrativeId").val(),
                        blockId:$("#areaId").val(),
                        name: request.term,
                        page: 1,
                        limit: 10
                    },
                    success: function (res) {
                        response($.map(res.data, function (item) {
                            return {
                                id:item.id,
                                label: item.name,
                                value: item.name
                            }
                        }));
                    }
                });
            },
            minLength: 2,
            messages: {
                noResults: '',
                results: function () {
                }
            },
            focus: function( event, ui ) {
                $("#estateName").val( ui.item.value );
                $("#estateId").val( ui.item.id );
                return false;
            },
            select: function( event, ui ) {
                $("#estateName").val(ui.item.value );
                $("#estateId").val( ui.item.id );
                return false;
            }

        });
    }
}
addGroupIndex_obj.init();