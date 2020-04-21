var fieldIndex_obj = {

    urlConfig: {
        categoryList: '/field/category_list',
        categorySave: '/field/category_save'
    },
    init: function () {
        this._initSelectAll();
        this._initFrom();
        this._initSave();
    },

    /**
     * 填充表单字段
     */
    _initFrom: function () {
        var _this = this;
        $.get({
            url: _this.urlConfig.categoryList,
            success: function (res) {
                var field = ""
                //字典大类的循环
                $.each(res.data, function (key, item) {
                    field += '<div style="padding: 20px; background-color: #F2F2F2;">'
                    field += '<div class="layui-row layui-col-space15">'
                    field += ' <div class="layui-col-md12">'
                    field += '<div class="layui-card">'
                    field += '<div class="layui-card-header">' + key + '</div>'
                    field += '<div class="layui-card-body">'
                    field += '<input style= "padding: 20px;"type="checkbox" lay-skin="primary" id="c_all" lay-filter="c_all" title="全选">'
                    field += '<div class="layui-row">'
                    $.each(item, function (index, val) {
                        field += '<div class="layui-col-xs2">'
                        field += '<input type="checkbox" lay-skin="primary" name="column" title="' + val['item'] + '" value="'+val['cid']+'-'+val['did']+'" lay-filter="column">'
                        field += ' </div>'
                    })
                    field += '</div>'
                    field += '</div>'
                    field += '</div>'
                    field += '</div>'
                    field += '</div>'
                    field += '</div>'

                });
                field += '  <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;"></fieldset>'
                field += '<div class="layui-form-item">'
                field += '<div class="layui-input-block">'
                field += ' <button type="reset" class="layui-btn layui-btn-sm layui-btn-primary">重置</button>'
                field += '  <button class="layui-btn layui-btn-sm layui-btn-primary" lay-submit lay-filter="saveBtn" >保存</button>'
                field += ' </div>'
                field += ' </div>'
                $('#fieldIndex_wrapper #fieldId').html(field)
                layui.form.render();
            }
        });
    },

    /**
     * 全选按钮
     * @private
     */
    _initSelectAll: function () {
        layui.form.on('checkbox(c_all)', function (data) {
            var a = data.elem.checked;
            if (a === true) {
                $(this).next().next().children().find('input[name="column"]').each(function () {
                    $(this).prop("checked", true);
                });
                layui.form.render('checkbox');
            } else {
                $(this).next().next().children().find('input[name="column"]').each(function () {
                    $(this).prop("checked", false);
                });
                layui.form.render('checkbox');
            }
        });
    },

    /**
     * 保存按钮
     * @private
     */
    _initSave: function () {
        var _this = this;
        layui.form.on('submit(saveBtn)', function(data){
            var value = '';
            $('input[name="column"]').each(function () {
                if ($(this).prop("checked")) {
                    value = value + $(this).val() + ',';
                }
            });
            $.post({
                url: _this.urlConfig.categorySave,
                data: {
                    value: value
                },
                success: function (res) {
                   layui.msg(res.msg)
                }
            });
        });
    },
};

$(function () {
    fieldIndex_obj.init();
})