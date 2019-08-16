<#include "/admin/utils/ui.ftl"/>
<@layout>
    <#-- 引入bootstrap-dialog样式 -->
    <link href="${base}/dist/vendors/bootstrap3-dialog/dist/css/bootstrap-dialog.min.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="${base}/dist/vendors/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css">

    <script type="text/javascript" src="${base}/dist/vendors/bootstrap3-dialog/dist/js/bootstrap-dialog.min.js"></script>
    <script type="text/javascript" src="${base}/dist/vendors/bootstrap-datepicker/dist/js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${base}/dist/vendors/bootstrap-datepicker/dist/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<section class="content-header">
    <h1>访问记录管理</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">访问记录管理</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">访问记录列表</h3>
                    <div class="box-tools">
                        <a class="btn btn-default btn-sm" href="javascrit:;" data-action="batch_del">批量删除</a>
                    </div>
                </div>
                <div class="box-body">
                    <input type="hidden" id="currentDayer" value="${nowDate}">
                    <form id="query-form-earch" class="form-inline search-row">
                        <input type="hidden" name="pageNo" value="${page.number + 1}"/>
<#--                        <div class="form-group">-->
<#--                            <select class="selectpicker show-tick form-control" name="channelId">-->
<#--                                <option value="0">所有栏目</option>-->
<#--                                <#list channels as channel>-->
<#--                                    <option value="${channel.id}" <#if (channelId == channel.id)> selected </#if>>${channel.name}</option>-->
<#--                                </#list>-->
<#--                            </select>-->
<#--                        </div>-->

                        <div class="form-group">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    Agent(<span class="text-danger">模糊</span>)
                                </button>
                                <input type="text" class="form-control" name="clientAgent" value="${condition.clientAgent}"
                                       placeholder='Agent，左模糊查询, sql(a%)' title='Agent，左模糊查询'>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="input-group date" data-provide="datepicker">
                                     <span class="input-group-addon">
                                         <span class="glyphicon glyphicon-th"></span>
                                     </span>
                                <input type="text" class="form-control datepicker" data-format="yyyy-MM-dd"
                                       id="createDate" name="createDate" value="${condition.createDate}"
                                       placeholder='日期' title='日期'>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-default">查询</button>
                        <button id="btn_reset" onclick="resetSearch()" type="button"
                                class="btn btn-default btn-space">
                            <span class="fa fa-eraser" aria-hidden="true" class="btn-icon-space"></span>重置
                        </button>
                    </form>
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered"
                               style="word-break:break-all; word-wrap:break-all;">
                            <thead>
                            <tr>
                                <th title="序号">#</th>
                                <th width="30"><input type="checkbox" class="checkall"></th>
                                <th width="80">IP</th>
                                <th>访问资源路径</th>
                                <th>请求方式</th>
                                <th>请求参数</th>
                                <th>agent</th>
                                <th width="150">访问时间</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list page.content as viewLog>
                                <tr>
                                    <td>${viewLog_index + 1}</td>
                                    <td>
                                        <input type="checkbox" name="id" value="${viewLog.id}">
                                    </td>
                                    <td>
                                        ${viewLog.clientIp}
                                    </td>
                                    <td>
                                        <a href="javascript:void(0);" data-id="${viewLog.id}" data-action="viewLink">
                                            ${viewLog.resourcePath}
                                        </a>
                                    </td>
                                    <td>
                                        <span class="label label-default
                                            <#if (viewLog.method == 'POST')>bg-green</#if>">
                                            ${viewLog.method}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="text-info" title="${viewLog.parameterJson}">${viewLog.parameterJson}</span>

                                    </td>
                                    <td>
                                        <span class="text-muted">${viewLog.clientAgent}</span>
                                    </td>
                                    <td>${viewLog.created?datetime}</td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="box-footer">
                    <@pager "list.html" page 8 />
                </div>
            </div>
        </div>
    </div>

</section>

<script type="text/javascript">
    $(function () {
        $.fn.datepicker.defaults.format = "yyyy-MM-dd";
        <#-- 只显示一年的日期365天, or "-120d"; -->
        $.fn.datepicker.defaults.startDate = new Date(new Date()-1000 * 60 * 60 * 24 * 365);
        $.fn.datepicker.defaults.endDate = new Date();
        //$.fn.datepicker.defaults.language = "zh-CN";//汉化
        $.fn.datepicker.defaults.todayBtn = true;//显示今天按钮
        $.fn.datepicker.defaults.todayHighlight = true;//当天高亮显示
        $.fn.datepicker.defaults.autoclose = true;//选择日期后自动关闭日期选择框

        <#--
        -->
        $('.datepicker').datepicker({
            format:"yyyy-MM-dd",
            language:"zh-CN", //汉化
            autoclose : true,   //选择日期后自动关闭日期选择框
            todayBtn : "true",  //显示今天按钮
            todayHighlight : true,   //当天高亮显示
            minView: "month",   //不显示时分秒
            showMeridian: 1,
            pickerPosition: "bottom-left",
            startDate : new Date(new Date()-1000 * 60 * 60 * 24 * 365),
            endDate : new Date()
        });
    });
</script>

<script type="text/javascript">
    var J = jQuery;

    function ajaxReload(json){
        if(json.code >= 0){
            if(json.message != null && json.message != ''){
                layer.msg(json.message, {icon: 1});
            }
            $('#query-form-earch').submit();
        }else{
            layer.msg(json.message, {icon: 2});
        }
    }

    // 重置
    function resetSearch() {
        $('#query-form-earch').find('[name]').each(function () {
            $(this).val('');
        });
        $("#createDate").val($("#currentDayer").val());
    }

    function doDelete(ids) {
        J.getJSON('${base}/admin/viewer/delete.json', J.param({'id': ids}, true), ajaxReload);
    }

    $(function() {
        // 批量删除
        $('a[data-action="batch_del"]').click(function () {
            var check_length=$("input[type=checkbox][name=id]:checked").length;

            if (check_length == 0) {
                layer.msg("请至少选择一项", {icon: 2});
                return false;
            }

            var ids = [];
            $("input[type=checkbox][name=id]:checked").each(function(){
                ids.push($(this).val());
            });

            layer.confirm('确定删除此项吗?', {
                btn: ['确定','取消'], //按钮
                shade: false //不显示遮罩
            }, function(){
                doDelete(ids);
            }, function(){
            });
        });

        // 查看
        $('a[data-action="viewLink"]').click(function () {
            var that = $(this);

            jQuery.ajax({
                url: '${base}/admin/viewer/get.json',
                data: {'id': that.attr('data-id')},
                dataType: "json",
                type :  "POST",
                cache : false,
                async: false,
                error : function(i, g, h) {
                    layer.msg('发送错误', {icon: 2});
                },
                success: function(resp){
                    // 返回对象为 NormalResponse
                    if(resp){
                        if (resp.code== '00000000') {
                            popup(resp.data);
                        } else {
                            layer.msg(resp.message, {icon: 5});
                        }
                    }
                }
            });
        });
    })

    /**
     * 弹出窗口
     * @param vo 数据对象
     */
    function popup(row) {
        var $textAndPic = $('<div></div>');

        // 带折叠
        // $textAndPic.append('<div id="accordionB">'+
        //     '<div>'+
        //     '<a data-toggle="collapse" data-parent="#accordionB" href="#collapseO">格式介绍</a>'+
        //     '</div>'+
        //     '<div id="collapseO" class="panel-collapse collapse">'+
        //     '<div class="panel-body">'+
        //     '<span class="text-info">起始时间</span>'+
        //     '[<span class="text-danger">持续总时间</span> (<span class="text-success">自身消耗的时间</span>), <span class="text-primary">在父entry中所占的时间比例</span>, <span class="text-muted">在总时间中所占的时间比例</span>]'+
        //     '-'+
        //     '<span class="text-warning">entry信息</span>'+
        //     '<br/>'+
        //     'eg:'+
        //     '<span class="text-info">0</span>'+
        //     '[<span class="text-danger">739ms</span>(<span class="text-success">48ms</span>), <span class="text-primary">100%</span>, <span class="text-muted">100%</span>]'+
        //     '-'+
        //     '<span class="text-warning">DubboInvoke interface com.a.b.c.DxxxQueryService#query</span>'+
        //     '</div>'+
        //     '</div>'+
        //     '</div>');

        $textAndPic.append('<span>');
        $textAndPic.append('clientIp: ');
        $textAndPic.append(row.clientIp);
        $textAndPic.append('</span><br/>');

        $textAndPic.append('<span>');
        $textAndPic.append('访问资源路径: ');
        $textAndPic.append('<br/>');
        $textAndPic.append('<span class="text-success">' + row.resourcePath + '</span>');
        $textAndPic.append('  <big><b>|</b></big>  ');
        $textAndPic.append('<span class="text-danger">' + row.method + '</span>');
        $textAndPic.append('</span><br/>');

        $textAndPic.append('<span>');
        $textAndPic.append('客户端: ');
        $textAndPic.append('<span class="text-success">' + row.clientAgent + ' </span>');
        $textAndPic.append('</span><br/>');

        $textAndPic.append('<span>');
        $textAndPic.append('请求参数: ');
        $textAndPic.append('<pre>' + row.parameterJson + '</pre>');
        $textAndPic.append('</span>');

        $textAndPic.append('<span>');
        $textAndPic.append('访问时间: ');
        $textAndPic.append('<span class="text-danger">' + row.created + ' </span>');
        $textAndPic.append('</span><br/>');

        <#--$textAndPic.append('<span>');-->
        <#--$textAndPic.append('系统环境: ' + row.appProfileEnv);-->
        <#--$textAndPic.append('</span><br/>');-->

        <#--$textAndPic.append('<span>');-->
        <#--$textAndPic.append('上报服务器: ' + row.networkAddress);-->
        <#--$textAndPic.append('</span><br/>');-->

        <#--$textAndPic.append('<span title="系统上报时间">');-->
        <#--$textAndPic.append('上报时间: ' + operateTimeFormatter(row.pushTime));-->
        <#--$textAndPic.append('</span><br/>');-->

        <#--$textAndPic.append('<span>');-->
        <#--$textAndPic.append('入库时间: ' + operateTimeFormatter(row.createDate));-->
        <#--$textAndPic.append('</span><br/>');-->


        BootstrapDialog.show({
            title: row.resourcePath + " 访问明细",
            message: $textAndPic,
            size: BootstrapDialog.SIZE_WIDE,
            draggable: true, // Default value is false，可拖拽
            // closable : false, // Default value is false，点击对话框以外的页面内容可关闭
            buttons: [
                // {
                //     label: '标记',
                //     icon: 'glyphicon glyphicon-star',
                //     cssClass: 'btn-warning',
                //     action: function(dialogRef){
                //         // 进行行为标记
                //         dialogRef.close();
                //     }
                // },
                {
                    label: '我知道了',
                    icon: 'fa fa-camera-retro',
                    cssClass: 'btn-primary',
                    action: function(dialogRef){
                        dialogRef.close();
                    }
                }
            ]
        });
    }
</script>
</@layout>
