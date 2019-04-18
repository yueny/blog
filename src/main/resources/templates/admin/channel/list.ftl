<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>栏目管理</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">栏目管理</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">栏目列表</h3>
                    <div class="box-tools">
                        <a class="btn btn-primary btn-sm" href="${base}/admin/channel/view">添加栏目</a>
                    </div>
                </div>
                <div class="box-body">
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="80">#</th>
                                <th width="80">预览图</th>
                                <th width="80">名称</th>
                                <th width="80">上级分类</th>
                                <th>对外释义链接</th>
                                <th>状态</th>
                                <th>权重</th>
                                <th width="50">节点</th>
                                <th width="140">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list list as row>
                                <tr>
                                    <td>${row.id}</td>
                                    <td>
                                        <#if (row.thumbnail == '#') ||  (row.thumbnail == null) ||  (row.thumbnail == '')>

                                        <#else>
                                            <img src="<@resource src=row.thumbnail/>" style="width: 80px;">
                                        </#if>
                                    </td>
                                    <td><span title="${row.nodeType}">${row.name}</span></td>
                                    <td>${row.parentChannelVo.name}</td>
                                    <td>${row.flag}</td>
                                    <td>
                                        <#if (row.status == 0)>
                                            显示
                                        <#else>
                                            隐藏
                                        </#if>
                                    </td>
                                    <td>
                                        <span title="排序值,值越大越靠前">${row.weight}</span>
                                    </td>
                                    <td>
                                        <#if (row.nodeType.isLeaf == 1)>
                                            <#-- 叶子节点 -->
                                        <#else>
                                            <#-- 子节点 -->
                                            <a class="btn-channel-node btn btn-xs btn-warning"
                                               data-id="${row.id}">
                                                查看子节点<i class="fa fa-edit"></i>
                                            </a>
                                        </#if>
                                    </td>
                                    <td>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.id}" data-action="weight">置顶</a>
                                        <a href="view?id=${row.id}" class="btn btn-xs btn-success">修改</a>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-danger" data-id="${row.id}"
                                           data-action="delete">删除</a>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

         <#-- 模态框（Modal） -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">

                </div>
            </div>
        </div>

    </div>
</section>

<script type="text/javascript">
    $(".btn-channel-node").click(function(){
        var channelId = $(this).attr('data-id');

        // 打开模态框
        $("#myModal").modal({
            backdrop: 'static',     // 点击空白不关闭s
            keyboard: false,        // 按键盘esc也不会关闭
            remote: 'view/node?id=' + channelId   // 从远程加载内容的地址
        });
    });

    $('#myModal').on('shown.bs.modal', function (e) {
        // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
        $(this).css('display', 'block');
        var modalHeight=$(window).height() / 2 - $('#youModel .modal-dialog').height() / 2;
        $(this).find('.modal-dialog').css({
            'margin-top': modalHeight
        });
    });

    $("#myModal").on("hidden.bs.modal", function() {
        $(this).removeData("bs.modal");
    });
</script>

<script type="text/javascript">
    var J = jQuery;

    function ajaxReload(json) {
        if (json.code >= 0) {
            if (json.message != null && json.message != '') {
                layer.msg(json.message, {icon: 1});
            }
            window.location.reload();
        } else {
            layer.msg(json.message, {icon: 2});
        }
    }

    function doUpdateWeight(id, weight) {
        J.getJSON('${base}/admin/channel/weight', J.param({'id': id, 'weight': weight}, true), ajaxReload);
    }

    $(function () {
        $('#dataGrid a[data-action="weight"]').bind('click', function(){
            var that = $(this);
            layer.confirm('确定将该项排序在第一位吗?', {
                btn: ['确定','取消'], //按钮
                shade: false //不显示遮罩
            }, function(){
                doUpdateWeight(that.attr('data-id'), 1);
            }, function(){
            });
            return false;
        });

        // 删除
        $('#dataGrid a[data-action="delete"]').bind('click', function () {
            var that = $(this);

            layer.confirm('确定删除此项吗?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/channel/delete', {id: that.attr('data-id')}, ajaxReload);
            }, function () {
            });
            return false;
        });

    })
</script>
</@layout>