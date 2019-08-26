/*
 * bootstrap-dialog 的进一步封装
 */
/**
 * 弹出错误提示的登录框
 *
 * 需要弹出error警告框的时候直接使用$.showErr("当日没有资金日报")即可
 *
 * @param str 警告字符串
 * @param func 对话框关闭时带入callback方法
 */
$.showErr = function(str, func) {
    // 调用show方法
    BootstrapDialog.show({
        type : BootstrapDialog.TYPE_DANGER,
        title : '错误 ',
        message : str,
        size : BootstrapDialog.SIZE_SMALL,//size为小，默认的对话框比较宽
        buttons : [ {// 设置关闭按钮
            label : '关闭',
            action : function(dialogItself) {
                dialogItself.close();
            }
        } ],
        // 对话框关闭时带入callback方法
        onhide : func
    });
};

/**
 * confirm确认选择框
 *
 * 通过$.showConfirm(title, _doPost);进行调用。
 *
 * @param str 确认字符串
 * @param funcok 对话框确认的时候执行方法
 * @param funcclose 对话框关闭的时候执行方法
 */
$.confirm = function(str, funcok, funcclose) {
    BootstrapDialog.confirm({
        title : '确认',
        message : str,
        type : BootstrapDialog.TYPE_WARNING, // <-- Default value is
        // BootstrapDialog.TYPE_PRIMARY
        closable : true, // <-- Default value is false，点击对话框以外的页面内容可关闭
        draggable : true, // <-- Default value is false，可拖拽
        btnCancelLabel : '取消', // <-- Default value is 'Cancel',
        btnOKLabel : '确定', // <-- Default value is 'OK',
        btnOKClass : 'btn-warning', // <-- If you didn't specify it, dialog type
        size : BootstrapDialog.SIZE_SMALL,
        // 对话框关闭的时候执行方法
        onhide : funcclose,
        callback : function(result) {
            // 点击确定按钮时，result为true
            if (result) {
                // 执行方法
                funcok.call();
            }
        }
    });
};

/**
 * Success dialog 提示框， 3000ms自动关闭
 *
 * @param str
 * @param func
 */
$.showSuccessTimeout = function(str, func) {
    BootstrapDialog.show({
        type : BootstrapDialog.TYPE_SUCCESS,
        title : '成功 ',
        message : str,
        size : BootstrapDialog.SIZE_SMALL,
        buttons : [ {
            label : '确定',
            action : function(dialogItself) {
                dialogItself.close();
            }
        } ],
        // 指定时间内可自动关闭
        onshown : function(dialogRef) {
            setTimeout(function() {
                dialogRef.close();
            }, 3000);
        },
        onhide : func
    });
};

/**
 *  dialog 提示框
 *
 * @param url 请求地址
 * @param method 请求方法类型
 * @param title 标题
 * @param funcSubmitSuccess 对话框提交后的执行方法
 */
$.showDialog = function(url, method, title, funcSubmitSuccess) {
    BootstrapDialog.show({
        size: BootstrapDialog.SIZE_WIDE,
        draggable: true, // Default value is false，可拖拽
        closable : true, // Default value is false，点击对话框以外的页面内容可关闭
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            //加载弹出页面
            $message.load(pageToLoad);

            return $message;
        },
        data: {
            'pageToLoad': url
        },
        title: title,
        // 找到自定义页面上x号进行绑定close事件
        onshown : function(dialogRef) {
            var $button = dialogRef.getModalContent().find('button[data-widget="remove"]');
            $button.on('click', {
                    dialogRef : dialogRef
                }, function(event) {
                    event.data.dialogRef.close();
                });
        },
        buttons: [{
                label: '提交',
                icon: 'fa fa-check-circle',
                cssClass: 'btn-primary',
                action: function(dialogRef) {
                    var $button = this;
                    $button.disable();

                    var form = dialogRef.getModalContent().find('form');
                    var method = form.attr("method");
                    var action = form.attr("action");
                    $.ajax({
                        type: method,
                        url: action,
                        data: form.serialize(),//序列化表格内容为字符串
                        cache:false,
                        dataType:'json',
                        success:function(data){
                            // After the form is submitted successfully, release hold.
                            //$(form).holdSubmit(false);
                            $button.enable();

                            if(tools.success(tools.resp_code(data))){
                                layer.msg('提交成功：' + tools.resp_text(data), {time: 500});
                                dialogRef.close();

                                if(funcSubmitSuccess){
                                    funcSubmitSuccess.call();
                                }
                            }else{
                                dialogRef.close();
                                layer.msg('提交失败:' + tools.resp_text(data), {icon: 5});
                            }
                        },
                        error:function(error){
                            $button.enable();
                            layer.msg('提交失败了:' + error, {icon: 5});
                        }
                    });
                }
            },
            {
                label : '关闭',
                icon: 'fa fa-close',
                // icon: 'fa fa-camera-retro',
                action: function(dialogRef) {
                    dialogRef.close();
                }
            }]
    });
};

/**
 * ajax加载远程页面弹出框
 */
// TODO 暂时无效， 未调试
$.fn.extend({
    ajaxTodialog : function() {
        // 为超链接绑定 click 事件
        return this.click(function (event) {
            var $this = $(this);

            // 取标题，如果标题未设置，则取链接内容
            var title = $this.attr("title") || $this.text();

            var url = $this.attr("data-url");
            // url 的提交方式
            var method = $this.attr('data-method');
            alert(method);

            $.showDialog(url, "GET", title);
        });
    }
});
