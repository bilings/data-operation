var estateProperty_obj = {
    urlConfig: {
        log: '/building/log',
        mergeHistory: '/building/mergeHistory'
    },

    init: function () {
        // 数据来源
        this._initProperty();
        // 其他来源
        this._initOtherProperty();
    },
    buildingId:layui.util.urlParam('buildingId'),
    type:layui.util.urlParam('type'),
    /**
     * 数据来源
     * @private
     */
    _initProperty: function () {
        var _this = this;
        var data={buildingId:_this.buildingId,type:_this.type}
        $.get({
            url: _this.urlConfig.log,
            data: data,
            success: function (res) {
                console.log(res)
                console.log(_this.type)
                console.log(_this.type=="nameIcon")
                if(_this.type=="nameIcon"){
                    $('#estateProperty_wrapper #estateProperty').text("楼栋名称:");
                    $('#estateProperty_wrapper #estateLastProperty').text("楼栋名称:");
                    $('#estateProperty_wrapper #name').text(res.data.currentName.name);
                    $('#estateProperty_wrapper #sr').text(res.data.currentName.sr);
                    $('#estateProperty_wrapper #time').text(res.data.currentName.time);
                    $('#estateProperty_wrapper #init').text(res.data.currentName.init);
                    $('#estateProperty_wrapper #nameLast').text(res.data.lastName.name);
                    $('#estateProperty_wrapper #srLast').text(res.data.lastName.sr);
                    $('#estateProperty_wrapper #timeLast').text(res.data.lastName.time);
                    $('#estateProperty_wrapper #initLast').text(res.data.lastName.init);
                }

            }
        });
    },
    /**
     * 其他来源
     * @private
     */
    _initOtherProperty: function () {
        var _this = this;
        var data={id:this.communityId}
        $.get({
            url: this.urlConfig.mergeHistory,
            data: data,
            success: function (res) {
                $.each(res.data, function (index, item) {
                    var flag = true;
                     var html='<div class="layui-col-xs12"><label class="layui-form-label" name="label-key">' + item.sr + '</label><label class="layui-form-label" name="label-key">';
                       if(_this.type=="nameIcon"){
                           html +=  item.name
                       } else if(_this.type=="blockIcon"){
                           html +=  item.shangQuan
                           if(typeof(item.shangQuan) =="undefined" ||typeof(item.shangQuan) ==undefined ||typeof(item.shangQuan) ==null ||typeof(item.shangQuan) ==""){
                               flag = false;
                           }
                       }else if(_this.type=="roadIcon"){
                           html +=  item.address
                           if(typeof(item.address) =="undefined" ||typeof(item.address) ==undefined ||typeof(item.address) ==null ||typeof(item.address) ==""){
                               flag = false;
                           }
                       }else if(_this.type=="numberIcon"){
                           html +=  item.address
                           if(typeof(item.address) =="undefined" ||typeof(item.address) ==undefined ||typeof(item.address) ==null ||typeof(item.address) ==""){
                               flag = false;
                           }
                       }
                     html += '</label><label class="layui-form-label" name="label-key"><a href="#" style="color: blue;" >采用数据</a></label></div>'
                    if(flag){
                        $('#estateProperty_wrapper #otherSr').append(html);
                    }

                });
            }
        });
    }
};

estateProperty_obj.init();