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
                        <@shiroRuleOpts permission="${shiroRuleMap.channelUpdate}">
                            <a id="tooltip2" class="btn btn-primary btn-sm" href="${base}/admin/channel/view.html">添加栏目</a>
                        </@shiroRuleOpts>
                    </div>
                </div>
                <div class="box-body">
                    <form class="form-inline search-row" method="get" action="${base}/admin/channel/list.html">
                        <div class="form-group">
                            <label class="col-lg-5 control-label">查询全部</label>
                            <div class="col-lg-3">
                                <select name="queryAll" class="form-control" data-select="${queryAll}">
                                    <option value="0">否</option>
                                    <option value="1">是</option>
                                </select>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-default">查询</button>
                    </form>

                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th title="序号">#</th>
                                <th width="80">预览图</th>
                                <th width="80">名称</th>
                                <th width="80">上级分类</th>
                                <th>对外释义链接</th>
                                <th>状态</th>
                                <th>权重</th>
                                <th width="50">节点树</th>
                                <th width="140">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list list as row>
                                <tr>
                                    <td>
                                        ${row_index + 1}
                                        <input type="hidden" name="id" value="${row.id}">
                                    </td>
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
                                            <a class="tree-panel-viewer btn btn-xs btn-warning"
                                               data-id="${row.id}" data-code="${row.channelCode}"
                                               data-toggle="modal" data-target="#myModal">
                                                查看子节点<i class="fa fa-edit"></i>
                                            </a>
                                        </#if>
                                    </td>
                                    <td>
<#--                                        <@shiroRuleOpts permission="${shiroRuleMap.channelWeight}">-->
                                            <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${row.channelCode}" data-action="weight.json">置顶</a>
<#--                                        </@shiroRuleOpts>-->
<#--                                        <@shiroRuleOpts permission="${shiroRuleMap.channelUpdate}">-->
                                            <a href="view/${row.channelCode}.html" class="btn btn-xs btn-success">修改</a>
<#--                                        </@shiroRuleOpts>-->
<#--                                        <@shiroRuleOpts permission="${shiroRuleMap.channelDelete}">-->
                                            <a href="javascript:void(0);" class="deletetor btn btn-xs btn-danger" data-id="${row.channelCode}"
                                               data-action="delete.json">删除</a>
<#--                                        </@shiroRuleOpts>-->
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
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">×
                        </button>
                        <h4 class="modal-title" id="myModalLabelTitle">
                        </h4>
                    </div>

                    <div class="modal-body" style="height: 200px">
                        <p id="tree-panel-nodetor"></p>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            关闭
                        </button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</section>

<script type="text/javascript">
    $('#tooltip2').bstip({
        title   : '新增栏目',
        html    : true,
        placement   : 'left',
        trigger : 'hover'
    });

    $('.deletetor').bstip({
        title   : '删除请慎重！已在使用的不可被删除',
        html    : true,
        placement   : 'left',
        trigger : 'hover'
    });

    $('.tree-panel-viewer').bspop({
        title   : '该栏目包含的所有节点清单',
        content : '该栏目包含的所有节点清单'
    });

    /* 菜单树加载 */
    $(function() {
        $('.tree-panel-viewer').bind('click', function(){
            var that = $(this);

            <#-- 设置标题 -->
            $('#myModalLabelTitle').text = '节点「' + that.data("code") + '」 -- 子节点列表';

            $('#tree-panel-nodetor').bstree(
                {
                    url : '${base}/admin/channel',
                    urlData : 'id=' + that.data("id") + '&code=' + that.data("code"),
                    edit:true
                }
            );
        });
    })

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

    function doUpdateWeight(channelCode, weight) {
        J.getJSON('${base}/admin/channel/weight.json', J.param({'channelCode': channelCode, 'weight': weight}, true), ajaxReload);
    }

    $(function () {
        $('#dataGrid a[data-action="weight.json"]').bind('click', function(){
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
        $('#dataGrid a[data-action="delete.json"]').bind('click', function () {
            var that = $(this);

            layer.confirm('确定删除此项吗?', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                J.getJSON('${base}/admin/channel/delete.json', {channelCode: that.attr('data-id')}, ajaxReload);
            }, function () {
            });
            return false;
        });

    })
</script>
</@layout>