var customerOrgIndex_obj = {
    urlConfig: {
        findUserByPage: '/customerUser/findByPage',
        detail: '/customerOrg/detail/',
        findByTree: '/customerOrg/findByTree',
        delete: '/customerOrg/delete'
    },
    init: function () {
        var _this = this;
        _this._initTree();
        _this._initSearch();
        _this._initToolBer();
    },
    /**
     * 初始化机构树
     * @private
     */
    _initTree: function () {
        var _this = this;
        $.get({
            url: _this.urlConfig.findByTree,
            data: {
                name: $('#customerOrgIndex_wrapper #name').val()
            },
            async: false,
            success: function (res) {
                $('#orgTree').empty();
                var nodes = res.data.children;
                if (nodes.length > 0) {
                    $('#customerOrgIndex_wrapper #orgId').val(nodes[0].id);
                    layui.tree({
                        elem: '#orgTree',
                        nodes: nodes,
                        skin: 'blank',
                        click: function (node) {
                            $('#customerOrgIndex_wrapper #orgId').val(node.id);
                            _this._initDetil();
                        }
                    });
                    $('#orgTree .layui-tree-branch,.layui-tree-leaf').empty();
                    $('#orgTree .layui-tree-branch,.layui-tree-leaf').removeClass('layui-icon');
                }
            }
        });
        _this._initDetil();
    },
    /**
     * 初始化机构信息
     * @private
     */
    _initDetil: function () {
        var _this = this;
        var id = $('#orgId').val();
        // 获取现有信息
        if (id) {
            $.get({
                url: _this.urlConfig.detail + id,
                success: function (res) {
                    $('#customerOrgIndex_wrapper #orgName').val(res.data.name);
                    $('#customerOrgIndex_wrapper #orgType').val(getDict(res.data.type));
                    $('#customerOrgIndex_wrapper #orgTimes').val((res.data.times === '' || res.data.times == null) ? '——' : res.data.times);
                }
            });
        }
        _this._displayTable();
    },
    /**
     * 初始化搜索条按钮
     * @private
     */
    _initSearch: function () {
        var _this = this;
        $('#customerOrgIndex_wrapper #searchBtn').on('click', function () {
            _this._initTree();
        });
    },
    _initToolBer: function () {
        var _this = this;
        _this._initAdd();
        _this._initEdit();
        _this._initDelete();
        _this._initAddChildren();
    },
    /**
     * 初始化添加机构按钮
     * @private
     */
    _initAdd: function () {
        var _this = this;
        $('#customerOrgIndex_wrapper #addBtn').on('click', function () {
            _this._openFrame({title: '新增机构', content: '../customer/customerOrgEdit.html'});
        });
    },
    /**
     * 初始化编辑按钮
     * @private
     */
    _initEdit: function () {
        var _this = this;
        $('#customerOrgIndex_wrapper #editBtn').on('click', function () {
            var id = $('#orgId').val();
            _this._openFrame({title: '编辑机构', content: '../customer/customerOrgEdit.html?id=' + id});
        });
    },
    /**
     * 初始化删除按钮
     * @private
     */
    _initDelete: function () {
        var _this = this;
        $('#customerOrgIndex_wrapper #deleteBtn').on('click', function () {
            var id = $('#orgId').val();
            _this._delete(id);
        });
    },
    /**
     * 初始化添加子机构按钮
     * @private
     */
    _initAddChildren: function () {
        var _this = this;
        $('#customerOrgIndex_wrapper #addChildrenBtn').on('click', function () {
            var id = $('#orgId').val();
            _this._openFrame({title: '新增子机构', content: '../customer/customerOrgEdit.html?parentId=' + id});
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
            elem: '#customerOrgIndex_wrapper #customerOrgIndexTable',
            url: _this.urlConfig.findUserByPage,
            method: 'get',
            where: {
                rank: $('#customerOrgIndex_wrapper #orgId').val()
            },
            toolbar: '#customerOrgIndexBar',
            title: '用户信息表',
            cols: [[
                {field: 'username', title: '用户账号', width: '32%'},
                {field: 'name', title: '姓名', width: '32%'},
                {field: 'orgName', title: '所属机构', width: '36%'}
            ]],
            page: true,
            id: 'customerOrgIndexTable'
        });
    },
    /**
     * 删除机构信息
     * @private
     */
    _delete: function (id) {
        var _this = this;
        if (id) {
            $.ajax({
                url: _this.urlConfig.delete,
                type: "PUT",
                data: {ids: id},
                success: function (res) {
                    layer.alert(res.msg);
                    if (res.code === 0) {
                        _this._initTree();
                    }
                }
            });
        }
    },
    /**
     * @param opt.title 页面标题
     * @param opt.content 弹出层的url
     * @param opt.area 弹出层页面比例 (['900px', '500px'])
     * @param opt.type 类型，解析url
     * @param opt.closeBtn 关闭按钮是否显示 1显示0不显示
     * @param opt.shadeClose 点击遮罩区域是否关闭页面
     * @param opt.shade 遮罩透明度
     * @private
     */
    _openFrame: function (opt) {
        var _this = this;
        var data = {
            title: "",
            content: "",
            type: 2,
            closeBtn: true,
            shadeClose: true,
            shade: 0.8,
            area: ['400px', '380px'],
            end: function () {
                _this._initTree();
            },
            yes: function () {
                layer.closeAll();
            }
        };
        $.extend(true, data, opt);
        layer.open(data);
    }
};

customerOrgIndex_obj.init();