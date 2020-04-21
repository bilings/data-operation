var avgcoeIndex_obj = {

    urlConfig: {
        // 列表
        ztreeList: "/avg/list",
        ztreeDelete: "/setting/delete",
        ztreeShowTable: "/setting/table",
    },
    groupType: 'floorCoe',
    /**
     * 界面js入口
     */
    init: function () {
        var _this = this;
        _this._initZtree();
        // 绑定添加分组
        _this._addGroupBtn();
        //切换tab
        _this._queryCoe();

        _this._getCoeType();

        _this._addRow();
        _this._removeRow();
        _this._save();
    },

    /**
     *点击切换Tab
     */
    _queryCoe: function () {
        var _this = this;
        layui.use('element', function () {
            var _element = layui.element;
            _element.on('tab(tabCoe)', function () {
                _this.groupType = this.getAttribute('lay-id');
                console.log(_this.groupType)
                _this._initZtree();
                _this.init();
            });
            return false;
        });
    },
    /**
     * 添加城市系数配置
     * @private
     */
    _addGroupBtn: function () {
        var _this = this;
        var coeType = _this._getCoeType();
        $('#addCityCoeBtn').unbind().bind('click', function () {
            console.log(parent.homeIndex_obj.currentCity);
            console.log(parent.homeIndex_obj.currentCityMongoId);
            layui.layer.open({
                type: 2,
                title: '添加城市系数配置',
                content: 'addGroup.html?cityName=' + parent.homeIndex_obj.currentCity + "&cityId=" + parent.homeIndex_obj.currentCityMongoId + "&coeType=" + coeType,
                content: 'addGroup.html?cityName=' + parent.homeIndex_obj.currentCity + "&cityId=" + parent.homeIndex_obj.currentCityAdministrativeId + "&groupType=" + _this.groupType,
                area: ['440px', '340px']
            });
            return false;
        })
    },

    /**
     * 添加系数行
     * @private
     */
    _addRow: function () {
        $("#addRow").unbind().bind("click", function () {
            var box = $("div[name='coe-body']").find(".layui-show");

            var thead = box.find(".coeTable thead");
            var tbody = box.find(".coeTable tbody");

            var colCount = parseInt(box.find(".colCount").val());
            var input = '<input type="text" class="layui-input" autocomplete="off" spellcheck="false">';

            colCount++;
            console.log(colCount);
            thead.find("tr").append('<td>第' + colCount + '层</td>');
            tbody.find("tr").append('<td></td>');
            tbody.append('<tr><td>共' + colCount + '层</td></tr>');

            for (var i = 0; i < colCount; i++) {
                tbody.find("tr").last().append('<td>' + input + '</td>');
            }
            box.find(".colCount").val(colCount);
        });
    },
    /**
     * 删除系数行
     * @private
     */
    _removeRow: function () {
        $("#removeRow").unbind().bind("click", function () {
            var box = $("#body_floorCoe").find(".layui-show");
            var colCount = parseInt(box.find(".colCount").val());
            if (colCount > 0) {
                box.find(".coeTable tbody tr:last").remove();
                box.find(".coeTable tr").find("td:last").remove();
                colCount--;
                box.find(".colCount").val(colCount);
            }
        });
    },
    _save: function () {
        $("#save").unbind().bind("click", function () {
            var box = $("div[name='coe-body']").find(".layui-tab-item");
            var configId = $(".configId").val();

            if (configId==undefined||configId==""||configId==null){
                layer.msg("请选择左侧城市");
            }

            var errorMsg="";
            var floorCoefficient = new Object();
            var hasLifts = new Object();
            var noLifts = new Object();


            $(box).each(function (i, o) {
                var data = new Object();
                var lifts = new Array();
                var totalFloor = $(o).find(".colCount").val()

                data.floor = totalFloor;
                data.totalFloor = totalFloor;

                $(o).find("table tbody tr").each(function (j, tr) {//j:总楼层
                    $(tr).find("td input").each(function (k, input) {// k:楼层
                        var floor = k + 1;
                        var totalFloor = j + 1;
                        var keys = (j + 1)+"-"+(k + 1) ;
                        var Lift = new Object();

                        Lift.floor = floor;
                        Lift.totalFloor = totalFloor;
                        ////需验证数据的格式
                        var value=$(input).val() == "" ? "0" : $(input).val();
                        if(value!=undefined && !value.match(/^(:?(:?\d+.\d+)|(:?\d+))$/)){
                            errorMsg+="<p>"+keys+"：系数格式不正确</p>";
                        }
                        Lift.value = value;
                        Lift.name = keys;

                        var aa = new Object();
                        aa[keys] = Lift;

                        lifts.push(aa);
                    });
                });
                data.lifts = lifts;

                var liftType = $(o).data("type");
                if (liftType == "haveLift") hasLifts = (data);
                else noLifts = (data);
            });
            floorCoefficient.cid = configId;
            floorCoefficient.hasLifts = hasLifts;
            floorCoefficient.noLifts = noLifts;
            console.log(floorCoefficient);

            //有电梯 无电梯 均无数据 无需保存
            if(!(floorCoefficient.hasLifts.totalFloor>0||floorCoefficient.hasLifts.totalFloor>0)){
                return ;
            }

            if (errorMsg!=""){
                layer.open({
                    content: errorMsg,
                    yes: function(index, layero){
                        layer.close(index);
                    }
                });
                return;
            }
            $.ajax({
                type: "POST",
                url: "/coe/structure/add",
                contentType: 'application/json',
                data: JSON.stringify(floorCoefficient),
                success: function (res) {
                    layer.msg(res.msg);
                }
            });
        });
    }
    ,
    /**
     *
     * @returns {*}
     * @private
     */
    _getCoeType: function () {
        var _this = this;
        var coeType;
        var cityId = parent.homeIndex_obj.currentCityAdministrativeId;
        if (_this.groupType == 'floorCoe') {
            coeType = 1;
        } else if (_this.groupType == 'orientationCoe') {
            coeType = 2;
        } else if (_this.groupType == 'buildingStructureCoe') {
            coeType = 3;
        } else if (_this.groupType == 'AreaCoe') {
            coeType = 4;
        }
        return coeType
    },

    /**
     * 初始化ztree
     */
    _initZtree: function () {
        //获取节点
        var _this = this;
        var cityId = parent.homeIndex_obj.currentCityMongoId;
        var coeType = _this._getCoeType();
        var setting = {
            view: {
                showIcon:false,
                removeHoverDom: removeHoverDom,
            },
            edit: {
                enable: true,
                editNameSelectAll: false,
                showRenameBtn: false,
                showRemoveBtn: setRemoveBtn
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                beforeRemove: beforeRemove,
                onClick: zTreeOnClick
            }
        };

        //判断为顶级节点则不显示删除按钮
        function setRemoveBtn(treeId, treeNode) {
            if (treeNode.level == 0) {
                return false;
            } else {
                return true;
            }
        }

        function beforeRemove(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            //zTree.selectNode(treeNode);
            if (confirm("确认删除 节点 -- " + treeNode.name + "，同时将删除系数配置 ？")) {
                $.ajax({
                    type: 'delete',
                    url: "/coe/structure/delete?id=" + treeNode.id,
                    success: function (res) {
                        layer.msg(res.msg);
                    }
                });
                return true;
            } else {
                return false;
            }
        }

        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_" + treeNode.tId).unbind().remove();
        };

        /**
         * 加载右边系数表格
         * bxl 2020.4.3
         */
        function initTabel(res) {
            console.log(res);
            var data=res.data;
            var boxs = $("#body_floorCoe div");
            boxs.find(".colCount").val(0);
            boxs.each(function (i, o) {
                //清空表格数据
                var thead = $(o).find("thead");
                var tbody = $(o).find("tbody");
                var colCount = $(o).find(".colCount");

                //初始化表单
                thead.html("<tr><td>总楼层</td>");
                tbody.html("");

                //绑定数据库数据 进行展示

                var totalFloor=0;
                var lifts;
                try {
                    totalFloor=i==0?data.hasLifts.totalFloor:data.noLifts.totalFloor;
                    lifts=i==0?data.hasLifts.lifts:data.noLifts.lifts;
                }
                catch (e) {
                }

                colCount.val(totalFloor);
                for (var t=1;t<=totalFloor;t++){//行
                    thead.find("tr").append('<td>第' + t + '层</td>');
                    tbody.append('<tr><td>共' + t + '层</td></tr>');
                    for (var f=1;f<=totalFloor;f++){
                        var key=t+"-"+f;
                        var lift = _.find(lifts,function(lf){
                            for(var dataKey in lf){//列
                                if (dataKey==key) return lf;
                            }
                        });

                        if (f>t)  tbody.find("tr").last().append('<td></td>');
                        else {
                            var value=lift[key].value=="0"?"":lift[key].value;
                            var input = '<input type="text" class="layui-input" autocomplete="off" spellcheck="false" value="'+value+'">';
                            tbody.find("tr").last().append('<td>' + input + '</td>');
                        }
                    }
                }
            });
        }

        /**
         * 点击节点展示右边列表
         * @param event
         * @param treeId
         * @param treeNode
         */
        function zTreeOnClick(event, treeId, treeNode) {
            $(".configId").val(treeNode.id);
            $.ajax({
                url: "/coe/structure/getTree",
                data: {
                    configId: treeNode.id,
                },
                success: function (res) {
                    initTabel(res);
                }
            });
        };

        function getTree() {
            var tree;
            $.ajax({
                url: "/coe/group/getTree?coeType=" + coeType + "&cityId=" + cityId,
                timeout: 30000, //超时时间：30秒
                async: false,//false-同步（当这个ajax执行完后才会继续执行其他代码）；异步-与其他代码互不影响，一起运行。
                success: function (res) {
                    //console.log(res.data);
                    tree = res.data;
                }, error: function (res) {
                    console.log(res);
                }
            });
            return tree;
        }

        //初始化ztree
        $.fn.zTree.init($("#treeDemo_" + this.groupType), setting, getTree());
    }
}
avgcoeIndex_obj.init();