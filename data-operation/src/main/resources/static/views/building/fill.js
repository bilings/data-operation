var fillIndex_obj = {

    urlConfig: {
        dictionary: '/setting/dictionary',
        boolOption: '/setting/boolOption',
        liftOption: '/setting/liftOption',
        buildingList: '/building/list',
        buildingFindById: "/building/findById",
        fillSave: "/building/fillSave",
    },
    init: function () {
        this._initFillBuilding();
        this._initChange();
        this._initSubmit();
        this._initOnBtn()
        this._initSelectAll()
    },


    /**
     * 全选按钮
     * @private
     */
    _initSelectAll: function () {
        layui.form.on('checkbox(c_all)', function (data) {
            var a = data.elem.checked;
            if (a === true) {
                $('input[data-name="column"]').each(function () {
                    $(this).prop("checked", true);
                    $(this).attr("name",$(this).attr("id"));
                });
                layui.form.render('checkbox');
            } else {
                $('input[data-name="column"]').each(function () {
                    $(this).prop("checked", false);
                    $(this).removeAttr("name");
                });
                layui.form.render('checkbox');
            }
        });
    },
    /**
     * 初始化导出按钮
     * @private
     */
    _initOnBtn: function () {
        layui.form.on('checkbox(check_c)', function (data) {
            $(this).attr("name",$(this).attr("id"));
        })
    },
    /**
     * 获取编辑的数据
     * @private
     */
    _initFillBuilding: function () {
        var _this = this;
        var buildingNames = getQueryString('buildingNames')
        // 根据empId获取对象，自动填充表单
        $.post({
            url: _this.urlConfig.buildingList,
            data: {
                communityId: layui.util.urlParam('esId'),
                name:null,
                address:null,
                quality:null
               },
            success: function (res) {
                // 自动填充表单
                _this._initBuildingName(res.data,buildingNames);
            }
        });
    },

    _initBuildingName:function(res,buildingNames){
        $('#fillIndex_wrapper #bulidingNames').text(buildingNames);
            $('#listNames').empty();   //清空resText里面的所有内容
            var html = '';
            html += '<option class="comment" value=""> 请选择</option>';
            $.each(res, function(commentIndex, comment){
                html += '<option class="comment" value='+ comment['id']+'>' + comment['name']
                    + '</option>';
            });
            $('#listNames').html(html);
            layui.form.render();
    },

    /**
     * 根据编辑的数据自动填充表单
     * @param emp
     * @private
     */
    _initInput: function (res) {
        $('#fillIndex_wrapper #alias').val(res.alias);
        $('#fillIndex_wrapper #address').val(res.address);
        $('#fillIndex_wrapper #floorTotalNo').val(res.floorTotalNo);
        $('#fillIndex_wrapper #floorOverGroundNo').val(res.floorOverGroundNo);
        $('#fillIndex_wrapper #floorUnderGroundNo').val(res.floorUnderGroundNo);
        $('#fillIndex_wrapper #floorTotalNo').val(res.floorTotalNo);
        $('#fillIndex_wrapper #builtDate').val(res.builtDate);
        $('#fillIndex_wrapper #structure').val(res.structure);
        $('#fillIndex_wrapper #buildingType').val(res.buildingType);
        $('#fillIndex_wrapper #landArea').val(res.landArea);
        $('#fillIndex_wrapper #avgPrice').val(res.avgPrice);
        $('#fillIndex_wrapper #priceCoe').val(res.priceCoe);
        $('#fillIndex_wrapper #quality').val(res.quality);
        $('#fillIndex_wrapper #liftNo').val(res.liftNo);
        $('#fillIndex_wrapper #nonStandDesc').val(res.nonStandDesc);
        $('#fillIndex_wrapper #priceDesc').val(res.priceDesc);
        $('#fillIndex_wrapper #wallDecoration').val(res.wallDecoration);
        $('#fillIndex_wrapper #publicDecoration').val(res.publicDecoration);
        $('#fillIndex_wrapper #buildingType').val(res.buildingType);
        $('#fillIndex_wrapper #structure').val(res.structure);
        $('#fillIndex_wrapper #liftBrand').val(res.liftBrand);
        $('#fillIndex_wrapper #canEvaluate').val(res.canEvaluate);
        $('#fillIndex_wrapper #isPerfect').val(res.isPerfect);
        $('#fillIndex_wrapper #haveLift').val(res.haveLift);
        // 更新的时候不允许修改username和密码，只能修改基本信息
        layui.form.render();
    },

    /**
     * 初始化选择楼栋（填充数据）
     * @private
     */
    _initChange: function () {
        var _this = this;
        layui.form.on('select(listNames)', function (data) {
            $.post({
                url: _this.urlConfig.buildingFindById,
                data: {id:data.value},
                success: function (res) {
                    _this._initInput(res.data)
                }
            });
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
                value = value + $(this).val() + ',';
            }
        });
        return value;
    },
    /**
     * 提交按钮
     * @private
     */
    _initSubmit: function () {
        var _this = this;
        layui.form.on('submit(save)', function (data) {
            var listName = $("#fillIndex_wrapper #listNames").val();
            console.log(listName)
            if(listName=='' || listName==null){
                layer.msg("选择模板楼栋")
                return false;
            }
            var myArray=new Array()
            $('input[data-name="column"]').each(function () {
                if ($(this).prop("checked")) {
                    myArray.push($(this).attr("name"))
                }
            });
            if(myArray.length<1){
                layer.msg("选择填充字段")
                return false;
            }
            var fillData = data.field;
            fillData['fillIds'] = getQueryString('dataIds');
            fillData['colName'] = myArray;
            $.post({
                url: _this.urlConfig.fillSave,
                contentType: "application/json",
                data: JSON.stringify(fillData),
                success: function (res) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);//关闭当前页
                    parent.buildingIndex_obj._initTable();
                    layer.msg(res.msg)
                }
            });
            return false;
        });
    }
};
function getQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURI(r[2]);
    }
    return null;
}
fillIndex_obj.init();