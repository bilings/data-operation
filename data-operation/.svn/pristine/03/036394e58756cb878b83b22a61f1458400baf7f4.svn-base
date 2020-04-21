//初始化查询询价单的全局对象
var allObj;
//初始化查询字典表全局对象
var allDictionaryObj = new Array();
//点击左边树的时候 临时的dictionaryId 保存时存到数据库
var allDictionaryId;


var inquirySheet_obj = {

    urlConfig: {
        logoUpload: "/busSetting/logoUpload",
        qryInquirySheet: "/busSetting/query",
        queryDictionary: "/busRoom/queryDictionary",
        save: "/busSetting/saveOrUpdate"
    },

    /**
     * 界面js入口
     */
    init: function () {
        this._initDictionary();
        this._initTable();
        this._initSubmit();
        this._initUpload();
        // this._initDictionaryTree();
    },

    /**
     * 初始化字典表数据
     * @private
     */
    _initDictionary: function () {
        var _this = this;
        var $ = layui.jquery, form = layui.form;
        $.get({
            url: _this.urlConfig.queryDictionary,
            data: {description: "客户分类"},
            async: false,
            success: function (res) {
                // var dictionaryList = new Array();
                dictionary = res.data;
                for (var i = 0; i < dictionary.length; i++) {
                    // var dictionaryNew = '{"id":"'+dictionary[i].id+'","name":"'+dictionary[i].item+'","href":""}';

                    dictionaryNew = new Object();
                    dictionaryNew.id = dictionary[i].id;
                    dictionaryNew.name = dictionary[i].item;
                    dictionaryNew.href = "javascript:queryBusSetting(" + dictionary[i].id + ");";
                    allDictionaryObj.push(dictionaryNew);
                    if (i == 0) {
                        allDictionaryId = dictionary[i].id;
                    }
                }
                //将查询的数据设置到树中
                dictionaryTree();
            }
        });
    },

    /**
     * 初始化询价单数据
     * @private
     */
    _initTable: function () {
        var _this = this;
        var $ = layui.jquery, form = layui.form;
        $.get({
            url: _this.urlConfig.qryInquirySheet,
            data: {dictionaryId: allDictionaryId},
            async: false,
            success: function (res) {
                //全局变量赋值
                allObj = res.data;
                if (res.data != null) {
                    //打印设置
                    $("input[name='print'][value='1']").prop("checked", res.data.print == 1 ? true : false);
                    $("input[name='print'][value='2']").prop("checked", res.data.print == 2 ? true : false);
                    $("input[name='print'][value='0']").prop("checked", res.data.print == 0 ? true : false);
                    //样式设置
                    $("#simplyName").val(res.data.simplyName);
                    $("#fileUrl").val(res.data.logo);
                    $("#inquirySheetName").val(res.data.inquirySheetName);
                    $("#evaluateDesc").html(res.data.evaluateDesc);
                    // var showOptions = res.data.showOptions.split(',');
                    // for (var i = 0; i < showOptions.length; i++) {
                    //     var thisOptionName = '#showOptions_' + showOptions[i];
                    //     $(thisOptionName).attr("checked", true);
                    // }
                    // $('#inquirySheetImg').attr("src", res.data.logo);
                    // form.render(); //更新全部

                    var showOptions;
                    if (!isEmpty(res.data.showOptions)) {
                        removeAllCheck();
                        showOptions = res.data.showOptions.split(',');
                        for (var i = 0; i < showOptions.length; i++) {
                            var thisOptionName = '#showOptions_' + showOptions[i];
                            $(thisOptionName).prop("checked", true);

                        }
                    } else {
                        removeAllCheck()
                    }

                    if (!isEmpty(res.data.logo)) {
                        $('#inquirySheetImg').prop("src", res.data.logo);
                    } else {
                        $('#inquirySheetImg').prop("src", "/resource/img/noImg.png");
                    }
                    form.render();
                } else {
                    $("input[name='print'][value='1']").prop("checked", false);
                    $("input[name='print'][value='2']").prop("checked", false);
                    $("input[name='print'][value='0']").prop("checked", false);
                    // var showOptions = res.data.showOptions.split(',');
                    // for (var i = 0; i < showOptions.length; i++) {
                    //     var thisOptionName = '#showOptions_' + showOptions[i];
                    //     $(thisOptionName).attr("checked", true);
                    // }
                    // $('#inquirySheetImg').attr("src", res.data.logo);
                    // form.render(); //更新全部

                    //样式设置
                    $("#simplyName").val("");
                    $("#fileUrl").val("");
                    $("#inquirySheetName").val("");
                    $("#evaluateDesc").html("");
                    // var showOptions = res.data.showOptions.split(',');
                    // for (var i = 0; i < showOptions.length; i++) {
                    //     var thisOptionName = '#showOptions_' + showOptions[i];
                    //     $(thisOptionName).attr("checked", true);
                    // }
                    // $('#inquirySheetImg').attr("src", res.data.logo);
                    // form.render(); //更新全部

                    removeAllCheck();
                    $('#inquirySheetImg').prop("src", "/resource/img/noImg.png");
                    form.render();
                }


            }
        });

    },


    /**
     * 提交按钮
     * @private
     */
    _initSubmit: function () {
        var _this = this;
        layui.form.on('submit(save)', function () {
            //获取checkbox[name='showOptions']的值
            var arr = new Array();
            $("input:checkbox[name='showOptions']:checked").each(function (i) {
                arr[i] = $(this).val();
            });
            var id;
            if (allObj == null) {
                id = "";
            } else {
                id = allObj.id;
            }
            var list = {
                id: id,
                inquirySheetName: $('#inquirySheetName').val(),
                simplyName: $('#simplyName').val(),
                evaluateDesc: $('#evaluateDesc').val(),
                logo: $('#fileUrl').val(),
                print: $('#printDiv input[name="print"]:checked ').val(),
                showOptions: arr.join(","),//将数组合并成字符串
                dictionaryId: allDictionaryId

            };
            $.post({
                url: _this.urlConfig.save,
                data: JSON.stringify(list),
                contentType: "application/json",
                success: function (res) {
                    inquirySheet_obj._initTable();
                    layer.msg(res.msg);
                }
            });
            return false;
        });
    },
    _initUpload: function () {
        var _this = this;
        layui.use('upload', function () {
            var $ = layui.jquery
                , upload = layui.upload;

            //普通图片上传
            var uploadInst = upload.render({
                elem: '#inquirySheetIndex_wrapper #inquirySheetButton'
                , url: _this.urlConfig.logoUpload
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        $('#inquirySheetImg').attr('src', result); //图片链接（base64）
                    });
                }
                , done: function (res) {
                    //如果上传失败
                    if (res.code > 0) {
                        return layer.msg('上传失败');
                    }
                    //上传成功
                    $("#fileUrl").val(res.data);
                }
                , error: function () {
                    //演示失败状态，并实现重传
                    var inquirySheetText = $('#inquirySheetText');
                    inquirySheetText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                    inquirySheetText.find('.demo-reload').on('click', function () {
                        uploadInst.upload();
                    });
                }
            });

            //指定允许上传的文件类型
            upload.render({
                elem: '#test3'
                , url: _this.urlConfig.logoUpload
                , accept: 'file' //普通文件
                , done: function (res) {
                }
            });
            upload.render({ //允许上传的文件后缀
                elem: '#test4'
                , url: _this.urlConfig.logoUpload
                , accept: 'file' //普通文件
                , exts: 'jpg|jpeg|gif|png|bmp' //只允许上传压缩文件
                , done: function (res) {
                }
            });
            //设定文件大小限制
            upload.render({
                elem: '#test7'
                , url: _this.urlConfig.logoUpload
                , size: 10240 //限制文件大小，单位 KB
                , done: function (res) {
                }
            });
        })
    },
    /*_initDictionaryTree: function () {
        layui.use(['tree', 'layer'], function () {
            console.log("测试："+allDictionaryObj);
            var layer = layui.layer
                , $ = layui.jquery;

            layui.tree({
                elem: '#demo1' //指定元素
                , target: '_blank' //是否新选项卡打开（比如节点返回href才有效）
                , click: function (item) { //点击节点回调
                }
                , nodes: allDictionaryObj/!*[ //节点
            {
                name: '金融机构'
                , id: 1
            },
            {
                name: '司法机构'
                , id: 2
            },
            {
                name: '公众'
                , id: 3
            },

        ]*!/
            });

        });
    }*/
    /**
     * 初始化搜索按钮
     * @private
     */
    _initSearch: function () {
        var _this = this;
        $('#inquirySheetIndex_wrapper #searchButton').on('click', function () {
            var keyword = $('#employeeIndex_wrapper #keyword').val();
            $.post({
                url: _this.urlConfig.employeeList,
                data: {keyword: keyword},
                success: function (res) {
                    _this._displayTable(res, keyword);
                }
            });
        });
    },
};

//查询询价单数据
function queryBusSetting(dictionaryId) {
    //设置当前点击的dictionaryId
    allDictionaryId = dictionaryId;
    /*var _this = this;
    var $ = layui.jquery, form = layui.form;
    //设置当前点击的dictionaryId
    allDictionaryId = dictionaryId;
    $.post({
        url: "/busSetting/query",
        data: {dictionaryId: dictionaryId},
        success: function (res) {
            //全局变量赋值
            allObj = res.data;
            //打印设置
            $("input[name='print'][value='1']").attr("checked", res.data.print == 1 ? true : false);
            $("input[name='print'][value='2']").attr("checked", res.data.print == 2 ? true : false);
            $("input[name='print'][value='0']").attr("checked", res.data.print == 0 ? true : false);

            //样式设置
            $("#simplyName").attr("value", res.data.simplyName);
            $("#inquirySheetName").attr("value", res.data.inquirySheetName);
            $("#evaluateDesc").html(res.data.evaluateDesc);
            var showOptions;
            if (!isEmpty(res.data.showOptions)) {
                removeAllCheck();
                showOptions = res.data.showOptions.split(',');
                for (var i = 0; i < showOptions.length; i++) {
                    var thisOptionName = '#showOptions_' + showOptions[i];
                    $(thisOptionName).attr("checked", true);
                }
            } else {
                removeAllCheck()
            }

            if (!isEmpty(res.data.logo)) {
                $('#inquirySheetImg').attr("src", res.data.logo);
            } else {
                $('#inquirySheetImg').attr("src", "/resource/img/noImg.png");
            }
            form.render();
        }
    });*/
    inquirySheet_obj._initTable();

}

//设置树
function dictionaryTree() {
    layui.use(['tree', 'layer'], function () {
        // console.log(allDictionaryObj);
        var layer = layui.layer
            , $ = layui.jquery;

        layui.tree({
            elem: '#demo1' //指定元素
            , target: '_blank' //是否新选项卡打开（比如节点返回href才有效）
            , click: function (item) { //点击节点回调
            }
            , nodes: allDictionaryObj/*[ //节点
            {
                name: '金融机构'
                , id: 1
            },
            {
                name: '司法机构'
                , id: 2
            },
            {
                name: '公众'
                , id: 3
            },

        ]*/
        });

    });
}

//非空判断
function isEmpty(obj) {
    if (obj == "" || obj == null || obj == undefined) { // "",null,undefined
        return true;
    } else {
        return false;
    }
}

//移除所有复选框选中样式
function removeAllCheck() {
    $('#showOptions_1').prop("checked", false);
    $('#showOptions_2').prop("checked", false);
    $('#showOptions_3').prop("checked", false);
    $('#showOptions_4').prop("checked", false);
}

inquirySheet_obj.init();