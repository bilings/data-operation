var correctAvgPrice = {
    urlConfig:{
        //pageCaseList
        pageCaseList: '/avgPrice/pageCaseList',
        avgPriceList: '/avgPrice/list',
        usedCaseList: '/avgPrice/usedCaseList',
        calculateAvgPrice: '/avgPrice/calculateAvgPrice',
        applyPrice: '/avgPrice/applyPrice'
    },
    cols : [[
        /* {type: 'checkbox', fixed: 'left'},*/
        {field: 'name', title: '楼盘名称'},
        {field: 'louDong', title: '楼栋'},
        {field: 'floor', title: '楼层'},
        {field: 'totalFloor', title: '总楼层'},
        {field: 'buildArea', title: '面积'},
        {field: 'roomType', title: '户型'},
        {field: 'directionType', title: '朝向'},
        {field: 'scenery', title: '景观'},
        {field: 'caseDate', title: '案例时间'},
        {field: 'siteName', title: '来源'},
    ]],

    /**
     * 初始化方法
     */
    init:function () {
        let t = this;
        t.initUsedCaseTable(t.getUsedCaseData(),t.cols);
        t.initDate();
        t.initData();
        t.correctSysPrice();
        t.countSysPrice();
        t.search();
        t.onUsedBox();
        t.onChooseBox();
        t.cancel();
        t.apply();
    },

    /**
     * 修正系统均价点击事件
     */
    correctSysPrice:function (){
        let t = this;
        $("#correctSysPrice").click(function () {
            $("#correctSysPrice").hide();
            $("#avgPriceSys").text("—元/m²");
            let area =  $("#areaSegment").text().split('-');
            $("#areaMin").val(area[0]);
            $("#areaMax").val(area[1]);
            //表格添加checkbox,重新加载;
            t.reloadUsedCaseTable(t.getUsedCaseData(),t.checkedCols());
            //加载选择表格数据
            t.initChooseCaseTable();
            $("#chooseDiv").show();
        });
    },

    /**
     * js对象copy
     * @param obj
     * @returns {any}
     */
    copy:function (obj) {
        return JSON.parse(JSON.stringify(obj));
    },

    /**
     * 获取get参数 url后面的参数
     * @param name 参数名
     * @returns {string|null}
     */
    getUrlParam:function(name) {
        let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        let r = window.location.search.substr(1).match(reg);
        if (r != null){
            return decodeURI(r[2]);
        }
        return null;
    },

    /**
     * 初始化页面数据第一行的数据查询
     */
    initData:function(){
        let t = this;
        $.ajax({
            url: t.urlConfig.avgPriceList,
            type: 'get',
            data: {id: t.getUrlParam("id")},
            success: function (res) {
                let d = JSON.parse(res);
                if (d.code === 0) {
                    let val = d.data[0];
                    $("#estate_Name").text(val.estateName);
                    $("#buildingTypeName").text(val.buildingTypeName);
                    let a = val.areasSection;
                    $("#areaSegment").text(a[0]+"-"+a[1]);
                    $("#cal_Date").text(val.calDate);
                    $("#avgPriceSys").text(val.avgPriceSys);
                }
            }
        });
    },

    /**
     * 日期工具
     */
    dateUtil:{
        beforeMonth: function(month){
            let dt = new Date();
            dt.setMonth( dt.getMonth()-month);
            return dt;
        },
    },

    /**
     *日期选择插件
     */
    initDate: function() {

        let _this = this;

        let dt = _this.dateUtil.beforeMonth(6);

        //年月范围
        layui.laydate.render({
            elem: '#caseDateStart',
            type: 'month',
            format: 'yyyyMM',
            value: dt,
            isInitValue: true //是否允许填充初始值，默认为 true
        });

        //年月范围
        layui.laydate.render({
            elem: '#caseDateEnd',
            type: 'month',
            format: 'yyyyMM',
            value: new Date(),
            isInitValue: true //是否允许填充初始值，默认为 true
        });
    },

    /**l
     * 加载使用案例数据
     */
    getUsedCaseData:function() {
        let t = this;
        let data = [];
        $.ajax({
            url: t.urlConfig.usedCaseList,
            type: 'post',
            contentType: 'application/json',
            async:false,
            data: JSON.stringify({id: t.getUrlParam("id")}),
            success: function (res) {
                if (res.code === 0) {
                    data = res.data;
                }
            }
        });
        return data;
    },


    /**
     * 初始化已用案例表格
     */
    reloadUsedCaseTable:function(data,cols) {
        layui.table.reload('usedCaseTable', {
            cols: cols,
            data: data
        });
    },

    /**
     * 初始化已用案例表格
     */
    initUsedCaseTable:function(data,cols) {
        layui.table.render({
            id: 'usedCaseTable',
            title: '已用案例列表',
            elem: '#usedCaseTable',
            cols: cols,
            data: data
        });
    },

    /**
     * 初始化社区案例表格
     */
    initChooseCaseTable:function () {
        let t = this;
        let c = t.copy(t.cols);
        c[0].unshift({type: 'checkbox', fixed: 'left'});
        // 刷新表格
        layui.table.render({
            id: 'chooseCaseTable',
            title: '均价案例表',
            method: 'post',
            //contentType: 'application/json',
            elem: '#chooseCaseTable',
            url: t.urlConfig.pageCaseList,
            where: {
                avgPriceId: t.getUrlParam("id"),
                areaMin: $("#areaMin").val(),
                areaMax: $("#areaMax").val(),
                caseDateStart: $("#caseDateStart").val(),
                caseDateEnd: $("#caseDateEnd").val()
            },
            cols: c,
            page: true,done: function(res, page, count){
                //可以自行添加判断的条件是否选中
                //这句才是真正选中，通过设置关键字LAY_CHECKED为true选中，这里只对第一行选中
                $.each(res.data, function (i,ite) {
                    if(t.hasChoose(ite)){
                        res.data[i]["LAY_CHECKED"]='true';
                        //下面三句是通过更改css来实现选中的效果
                        let index = res.data[i]['LAY_TABLE_INDEX'];
                        $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                        $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                    }
                });
            }
        });
    },

    /**
     * 判断数据是否是已经选中的案例
     * @param b 此行数据
     * @returns {boolean}
     */
    hasChoose:function (b) {
        let t = this;
        let data = t.allTableData("usedCaseTable");
        let boo = false;
        $.each(data, function (i,ite) {
            if(b.id === ite.id){
                boo = true;
                return false;
            }
        });
        return boo;
    },

    /**
     * 根据id属性对比，删除数组中指定对象
     * @param data 数组
     * @param one 数组中的一个对象
     */
    removeObj:function (data,one) {
        let j = -1;
        $.each(data,function (i,ite) {
            if(ite.id === one.id){
                j = i;
                return false;
            }
        });
        if(j !== -1){
            data.splice(j,1);
        }
    },

    /**
     * 返回默认全选的表头
     * @returns {any}
     */
    checkedCols:function () {
        let t = this;
        let c = t.copy(t.cols);
        c[0].unshift({type: 'checkbox', fixed: 'left', LAY_CHECKED:true});
        return c;
    },

    /**
     * 已用案例表格复选框选择事件
     */
    onUsedBox:function () {
        let t = this;
        layui.table.on('checkbox(usedCaseTable)', function(obj){
            let checkStatus = layui.table.checkStatus('usedCaseTable');
            t.reloadUsedCaseTable(checkStatus.data,t.checkedCols());
            t.reloadChooseTable();
        });
    },

    /**
     * 选择案例表格的复选框选择事件
     */
    onChooseBox:function () {
        let t = this;
        layui.table.on('checkbox(chooseCaseTable)', function(obj){
            console.log(obj);
            let usedData = t.allTableData("usedCaseTable");
            if(obj.checked){
                let chooseData = layui.table.checkStatus('chooseCaseTable').data;
                //去重复再计算
                $.each(usedData,function (i,ite) {
                    t.removeObj(chooseData,ite);
                });
                let count = usedData.length + chooseData.length;
                if(count > 5){
                    layer.msg("案例最多选择5条");
                    t.reloadChooseTable();
                }else{
                    t.reloadUsedCaseTable(usedData.concat(chooseData),t.checkedCols());
                }
            }else{
                let chooseData = [];
                //如果时全选取消
                if(obj.type === 'all'){
                    chooseData = t.allTableData("chooseCaseTable");
                }else{
                    chooseData.push(obj.data);
                }
                $.each(chooseData,function (i,ite) {
                    t.removeObj(usedData,ite);
                });
                t.reloadUsedCaseTable(usedData,t.checkedCols());
            }
        });
    },

    /**
     * 返回指定tableid的全部数据
     * @param tableId
     * @returns {*}
     */
    allTableData:function (tableId) {
        return layui.table.cache[tableId];
    },

    /**
     * 社区案例表格
     */
    search:function (){
        let t = this;
        $("#searchBtn").click(function () {
            t.reloadChooseTable();
        });
    },

    /**
     * 重载choosetable表数据
     */
    reloadChooseTable:function () {
        let t = this;
        layui.table.reload('chooseCaseTable',{
            where: {
                avgPriceId: t.getUrlParam("id"),
                areaMin: $("#areaMin").val(),
                areaMax: $("#areaMax").val(),
                caseDateStart: $("#caseDateStart").val(),
                caseDateEnd: $("#caseDateEnd").val()
            }
        });
    },

    /**
     * 计算系统价格
     */
    countSysPrice:function () {
        let t = this;
        $("#countSysPrice").click(function () {
            let data = t.allTableData("usedCaseTable");
            $.ajax({
                url: t.urlConfig.calculateAvgPrice,
                type: 'post',
                contentType: 'application/json',
                async: false,
                data: JSON.stringify(data),
                success: function (res) {
                    if(res.code === 0){
                        t.reloadUsedCaseTable(data,t.cols);
                        $("#chooseDiv").hide();
                        $("#cancel").show();
                        $("#apply").show();
                        $("#avgPriceSys").text(res.data);
                        layer.msg("计算均价成功");
                    }else{
                        layer.msg("计算均价失败");
                    }
                }
            });
        });
    },

    /**
     * 取消按钮绑定事件
     */
    cancel:function () {
        let t = this;
        $("#cancel").click(function () {
            $("#avgPriceSys").text("—");
            $("#cancel").hide();
            $("#apply").hide();
            let data = t.getUsedCaseData();
            t.initUsedCaseTable(data,t.checkedCols());
            t.reloadChooseTable();
            $("#chooseDiv").show();
        })
    },

    /**
     * 应用按钮绑定事件
     */
    apply:function () {
        let t = this;
        $("#apply").click(function () {
            let data = t.allTableData("usedCaseTable");
            let ids = [];
            $.each(data, function (i, ite) {
                ids.push(ite.id);
            });
            let price = $("#avgPriceSys").text();
            $.ajax({
                url: t.urlConfig.applyPrice,
                type: 'post',
                contentType: 'application/json',
                data: JSON.stringify({id:t.getUrlParam("id"), usedCaseIdList: ids, avgPriceSys: price}),
                success: function (res) {
                    if(res.code === 0){
                        layer.msg("采用均价成功");
                        $("#cancel").hide();
                        $("#apply").hide();
                        $("#correctSysPrice").show();
                        t.reloadUsedCaseTable(data,t.cols);
                        t.refreshPreTable();
                    }else{
                        layer.msg("采用均价失败");
                    }
                }
            });
        })
    },

    /**
     * 刷新主页面表数据
     */
    refreshPreTable:function () {
        parent.avgPriceIndex_obj._refreshTable();
    }
};

/**
 *初始化
 */
correctAvgPrice.init();
