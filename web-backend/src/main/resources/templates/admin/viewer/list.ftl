<#include "/admin/utils/ui.ftl"/>
<@layout>

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
                    <form id="qForm" class="form-inline search-row">
                        <input type="hidden" name="pageNo" value="${page.number + 1}"/>
<#--                        <div class="form-group">-->
<#--                            <select class="selectpicker show-tick form-control" name="channelId">-->
<#--                                <option value="0">所有栏目</option>-->
<#--                                <#list channels as channel>-->
<#--                                    <option value="${channel.id}" <#if (channelId == channel.id)> selected </#if>>${channel.name}</option>-->
<#--                                </#list>-->
<#--                            </select>-->
<#--                        </div>-->
<#--                        <div class="form-group">-->
<#--                            <input type="text" name="title" class="form-control"-->
<#--                                   value="${title}" placeholder="请输入标题关键字">-->
<#--                        </div>-->
                        <button type="submit" class="btn btn-default">查询</button>
                    </form>
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered"
                               style="word-break:break-all; word-wrap:break-all;">
                            <thead>
                            <tr>
                                <th title="序号">#</th>
                                <th width="30"><input type="checkbox" class="checkall"></th>
                                <th>agent</th>
                                <th width="80">IP</th>
                                <th>访问资源路径</th>
                                <th>请求方式</th>
                                <th>请求参数</th>
                                <th width="130">访问时间</th>
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
                                        ${viewLog.clientAgent}
                                    </td>
                                    <td>
                                        ${viewLog.clientIp}
                                    </td>
                                    <td>
                                        ${viewLog.resourcePath}
                                    </td>
                                    <td>
                                        ${viewLog.method}
                                    </td>
                                    <td>
                                        ${viewLog.parameterJson}
                                    </td>
                                    <td>${viewLog.created?datetime}</td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="box-footer">
                    <@pager "list" page 8 />
                </div>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
    var J = jQuery;

    function ajaxReload(json){
        if(json.code >= 0){
            if(json.message != null && json.message != ''){
                layer.msg(json.message, {icon: 1});
            }
            $('#qForm').submit();
        }else{
            layer.msg(json.message, {icon: 2});
        }
    }

    function doDelete(ids) {
        J.getJSON('${base}/admin/viewer/delete', J.param({'id': ids}, true), ajaxReload);
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
    })
</script>
</@layout>
