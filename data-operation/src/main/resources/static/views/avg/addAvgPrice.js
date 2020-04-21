var addAvgPrice = {
    /**
     * 后台链接
     * @type {{}}
     */
    urlConfig : {
        buildingType: '/avgPrice/buildingType',
        addAvgPrice: '/avgPrice/addAvgPrice',
        estateList: '/estate/list',
        estateBuildingType: '/avgPrice/estateBuildingType',
        areasSection: '/avgPrice/areasSection'
    },

    /**
     * 初始化方法
     */
     init:function() {
         let t = this;
        //日期
        t.initDate();
        //提交事件
        t.submitAdd();
        //加载楼盘
        t.searchEstateName();
        //楼盘选择后
        t.afterSelectEstate();
        //建筑类型选择后
        t.afterSelectBuildType();

        $("#estateNameInput").blur(function () {
            let state = $("#estateNameInput").attr("has");
            let dd = $("#communityId").next().find("dd[class ='layui-this']").first();
            if(state === '0' && !dd){
                $("#estateNameInput").val("");
                //let text = $("#communityId").next().find("dd[class ='layui-this']").first().text();
            }
        });
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
     * 楼盘被选中后去查询建筑类型
     */
    afterSelectEstate:function() {
        let t = this;
        layui.form.on('select(communityId)', function (data) {
            let text = $("#communityId option:selected").text();//获取文本
            if(text){
                $("#estateNameInput").val(text.split("-")[0]);
                $("#estateNameInput").attr("has","1");
            }
            $("#areasSection").text("");
            t.createBuildingSelect(data.value);
            let communityId = data.value;
            let buildingType = $("#buildingType").val();
            t.loadAreas(communityId,buildingType);
        });
    },

    /**
     * 楼盘被选中后去查询建筑类型
     */
    afterSelectBuildType:function() {
        let t = this;
        layui.form.on('select(buildingType)', function (data) {
            $("#areasSection").text("");
            let communityId = $("#communityId").val();
            let buildingType = data.value;
            t.loadAreas(communityId,buildingType);
        })
    },

    /**
     * 根据楼盘id创建建筑类型下拉
     * @param communityId 楼盘id
     */
    createBuildingSelect:function(communityId) {
        let t = this;
        $.post({
            url: t.urlConfig.estateBuildingType,
            data: JSON.stringify({id:communityId}),
            contentType: "application/json",
            async: false,
            success: function (res) {
                $('#buildingType').html("");
                if (res.code === 0) {
                    $.each(res.data, function (index, item) {
                        $('#buildingType').append('<option value="' + (item.id || item.type) + '">' + item.name + '</option>');
                    });
                    layui.form.render('select');
                }else{
                    layer.msg(res.msg);
                    $common.initBaseSelect(t.urlConfig.buildingType,null,'buildingType','建筑类型');
                }
            }
        });
    },



    /**
     * 根据 楼盘id、建筑类型加载面积段
     * @param communityId 楼盘id
     * @param buildingType 建筑类型
     */
    loadAreas:function(communityId,buildingType){
        let t = this;
        $("#areasSection").text("");
        if(!communityId || !buildingType){
            return;
        }
        $.post({
            url: t.urlConfig.areasSection,
            data: JSON.stringify({
                id: communityId,
                buildingCategoryCode: buildingType,
                //buildingCategory: buildingTypeName
            }),
            contentType: "application/json",
            async: false,
            success: function (res) {
                if (res.code === 0) {
                    let text = "";
                    let data = res.data;
                    $.each(data,function (i,ite) {
                        text += ite+",";
                    });
                    $("#areasSection").text(text.substring(0,text.length-1));
                } else {
                    layer.msg("没有查询到相应面积段");
                }
            }
        });
    },


    /**
     *日期选择插件
     */
    initDate:function() {
        //年月范
        layui.laydate.render({
            elem: '#calDate', //指定元素
            type: 'month',
            format: 'yyyyMM',
        });
    },

    /**
     * 提交新增表单数据
     */
    submitAdd:function() {
        let t = this;
        layui.form.on('submit(submitAvgPrice)', function (data) {
            $.post({
                url: t.urlConfig.addAvgPrice,
                data: JSON.stringify(data.field),
                contentType: "application/json",
                async: false,
                success: function (res) {
                    if (res.code === 0) {
                        parent.avgPriceIndex_obj._closeAdd("新增均价成功");
                    }
                }
            });
        });
    },

    /**
     * 检索楼盘名
     */
    searchEstateName:function() {
        let t = this;
        $("#estateNameInput").keyup(function (event) {
            $("#estateNameInput").attr("has","0");
            let code = event.keyCode;
            if(code === 37 || code === 38 || code === 39){
                return;
            } else if(code === 40){
                $("#communityId").next().find('input').first().focus();
                $("#communityId").next().find("dd").first().addClass("layui-this");
               /* let text = $("#communityId").next().find("dd[class ='layui-this']").first().text();
                $("#estateNameInput").val(text.split("-")[0]);*/
                return;
            } else if(code === 38){
                let dd = $("#communityId").next().find("dd").first();
                let boo = $(dd).hasClass("layui-this");
                if(boo){
                    $("#estateNameInput").focus();
                    $(dd).removeClass("layui-this");
                }
                return;
            }
            let val = $("#estateNameInput").val();
            if(!val){
                return;
            }
            let cityId = t.getUrlParam("cityId");
            let districtId = t.getUrlParam("districtId");
            $.post({
                url: t.urlConfig.estateList,
                data: {page: 1, limit: 10, name: val, cityId:cityId, districtId:districtId},
                //contentType: "application/json",
                success: function (val) {
                    let res = JSON.parse(val);
                    $('#communityId').html("");
                    $.each(res.data, function (index, item) {
                        let v = "";
                        if (item.name) {
                            v += item.name + "-";
                        }
                        if (item.cityName) {
                            v += item.cityName + "-";
                        }
                        if (item.districtName) {
                            v += item.districtName;
                        }
                        $('#communityId').append('<option value="' + item.id + '">' + v + '</option>');
                        console.log(v);
                    });
                    layui.form.render('select');
                    $("#communityId").next().addClass("layui-form-selected");
                    $("#communityId").next().find("dd").first().removeClass("layui-this");
                }
            });
        });
    }
};

addAvgPrice.init();
