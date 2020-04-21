var estateProperty_obj = {
    urlConfig: {
        log: '/estate/log',
        mergeHistory: '/estate/mergeHistory'
    },

    init: function () {
        // 数据来源
        this._initProperty();
        // 其他来源
        this._initOtherProperty();
    },
    communityId:layui.util.urlParam('communityId'),
    type:layui.util.urlParam('type'),
    /**
     * 数据来源
     * @private
     */
    _initProperty: function () {
        var _this = this;
        var data={communityId:_this.communityId,type:_this.type}
        $.get({
            url: _this.urlConfig.log,
            data: data,
            success: function (res) {
                console.log(res)
                console.log(_this.type)
                console.log(_this.type=="nameIcon")
                if(_this.type=="nameIcon"){
                    $('#estateProperty_wrapper #estateProperty').text("楼盘名称:");
                    $('#estateProperty_wrapper #estateLastProperty').text("楼盘名称:");
                    $('#estateProperty_wrapper #name').text(res.data.currentName.name);
                    $('#estateProperty_wrapper #sr').text(res.data.currentName.sr);
                    $('#estateProperty_wrapper #time').text(res.data.currentName.time);
                    $('#estateProperty_wrapper #init').text(res.data.currentName.init);
                    $('#estateProperty_wrapper #nameLast').text(res.data.lastName.name);
                    $('#estateProperty_wrapper #srLast').text(res.data.lastName.sr);
                    $('#estateProperty_wrapper #timeLast').text(res.data.lastName.time);
                    $('#estateProperty_wrapper #initLast').text(res.data.lastName.init);
                }else if(_this.type=="blockIcon"){
                    $('#estateProperty_wrapper #estateProperty').text("片区板块:");
                    $('#estateProperty_wrapper #estateLastProperty').text("片区板块:");
                    $('#estateProperty_wrapper #name').text(res.data.currentBlock.name);
                    $('#estateProperty_wrapper #name').text(res.data.currentBlock.name);
                    $('#estateProperty_wrapper #sr').text(res.data.currentBlock.sr);
                    $('#estateProperty_wrapper #time').text(res.data.currentBlock.time);
                    $('#estateProperty_wrapper #init').text(res.data.currentBlock.init);
                    $('#estateProperty_wrapper #nameLast').text(res.data.lastBlock.name);
                    $('#estateProperty_wrapper #srLast').text(res.data.lastBlock.sr);
                    $('#estateProperty_wrapper #timeLast').text(res.data.lastBlock.time);
                    $('#estateProperty_wrapper #initLast').text(res.data.lastBlock.init);
                }else if(_this.type=="roadIcon"){
                    $('#estateProperty_wrapper #estateProperty').text("详细地址(路):");
                    $('#estateProperty_wrapper #estateLastProperty').text("详细地址(路):");
                    $('#estateProperty_wrapper #name').text(res.data.currentRoad.name);
                    $('#estateProperty_wrapper #sr').text(res.data.currentRoad.sr);
                    $('#estateProperty_wrapper #time').text(res.data.currentRoad.time);
                    $('#estateProperty_wrapper #init').text(res.data.currentRoad.init);
                    $('#estateProperty_wrapper #nameLast').text(res.data.lastRoad.name);
                    $('#estateProperty_wrapper #srLast').text(res.data.lastRoad.sr);
                    $('#estateProperty_wrapper #timeLast').text(res.data.lastRoad.time);
                    $('#estateProperty_wrapper #initLast').text(res.data.lastRoad.init);
                }else if(_this.type=="numberIcon"){
                    $('#estateProperty_wrapper #estateProperty').text("详细地址(号):");
                    $('#estateProperty_wrapper #estateLastProperty').text("详细地址(号):");
                    $('#estateProperty_wrapper #name').text(res.data.currentNumber.name);
                    $('#estateProperty_wrapper #name').text(res.data.currentNumber.name);
                    $('#estateProperty_wrapper #sr').text(res.data.currentNumber.sr);
                    $('#estateProperty_wrapper #time').text(res.data.currentNumber.time);
                    $('#estateProperty_wrapper #init').text(res.data.currentNumber.init);
                    $('#estateProperty_wrapper #nameLast').text(res.data.lastNumber.name);
                    $('#estateProperty_wrapper #srLast').text(res.data.lastNumber.sr);
                    $('#estateProperty_wrapper #timeLast').text(res.data.lastNumber.time);
                    $('#estateProperty_wrapper #initLast').text(res.data.lastNumber.init);
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