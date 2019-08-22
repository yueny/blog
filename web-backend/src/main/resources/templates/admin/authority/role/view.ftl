<#include "/admin/utils/ui.ftl"/>
<@layout>

<link href="${base}/dist/vendors/treetable/css/jquery.treetable.css" rel="stylesheet" type="text/css"/>
<link href="${base}/dist/vendors/treetable/css/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css"/>
<script src="${base}/dist/vendors/treetable/jquery.treetable.js"></script>

<#macro treeIterator nodes>
<#-- 循环节点-->
    <#list nodes as row>
        <tr data-tt-id="${row.id}"
                <#if (row.parentId??) > data-tt-parent-id="${row.parentId}" </#if>>
            <td>
                <input type="checkbox" name="perms"
                       id="${row.name}-${row.id}"
                       value="${row.id}"> ${row.description}「${row.name}」
            </td>
        </tr>

        <#-- 判断是否有子集 -->
        <#if row.items??>
            <@treeIterator nodes=row.items />
        </#if>
    </#list>
</#macro>

<section class="content-header">
    <h1>编辑角色</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li><a href="${base}/admin/authority/role/list">角色管理</a></li>
        <li class="active">编辑角色</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post" action="update">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">编辑角色</h3>
                    </div>
                    <div class="box-body">
                        <#include "/admin/message.ftl">
                        <#if view?? && (view.id > 0)>
                            <input type="hidden" name="id" value="${view.id}"/>
                        </#if>

                        <div class="form-group">
                            <label for="name" class="col-lg-2 control-label">角色名称：</label>
                            <div class="col-lg-3">
                                <input type="text" class="form-control" placeholder="请输入角色名称" name="name"
                                       value="${view.name}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="description" class="col-lg-2 control-label">角色描述：</label>
                            <div class="col-lg-3">
                                <input type="text" class="form-control" placeholder="请输入角色描述" name="description"
                                       value="${view.description}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="status" class="col-lg-2 control-label">角色状态：</label>
                            <div class="col-lg-3">
                                <input type="text" class="form-control" name="status"
                                       value="${view.status}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="item" class="col-lg-2 control-label">分配菜单：</label>
                            <div class="col-lg-6" id="perms">
                                <table id="dataGrid" class="table table-border table-bordered table-bg">
                                    <caption>
                                        <a href="#" onclick="jQuery('#dataGrid').treetable('expandAll'); return false;">展开所有</a>
                                        <a href="#" onclick="jQuery('#dataGrid').treetable('collapseAll'); return false;">收起所有</a>
                                        <a href="#" onclick="return false;" disabled="disabled">全选</a>
                                        <a href="#" onclick="return false;" disabled="disabled">全清</a>
                                    </caption>
                                    <tbody>
                                        <@treeIterator nodes=permissions />
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>
<script type="text/javascript">
    var perm = [];
        <#list view.permissions as p>
        perm.push('${p.id}');
            <#if p.children??>
                <#list p.children as c>
                perm.push('${c.id}');
                    <#if c.children??>
                        <#list c.children as a>
                        perm.push('${a.id}');
                        </#list>
                    </#if>

                </#list>
            </#if>
        </#list>

    $(function () {
        // initialize treeTable
        $("#dataGrid").treetable({
            expandable: true,
            initialState:"expanded",
            clickableNodeNames:false,//点击节点名称也打开子节点.
            indent : 30//每个分支缩进的像素数。
        });

        // $('#dataGrid input[type=checkbox]').click(function () {
        //     if ($(this).prop("checked")) {
        //         var tr = $(this).closest("tr");
        //         var parent = tr.attr("data-tt-parent-id");
        //
        //         if (typeof(parent) != 'undefined') {
        //             var parentArray = parent.split('.');
        //
        //             var temp = '';
        //             for (var i = 0; i < parentArray.length; i++) {
        //                 if (i > 0) {
        //                     temp += '.' + parentArray[i];
        //                 } else {
        //                     temp += parentArray[i];
        //                 }
        //
        //                 $('tr[data-tt-id="' + temp + '"]>td>input:checkbox').prop("checked", $(this).prop("checked"));
        //             }
        //         }
        //     }
        // })


        // Highlight a row when selected
        $("#dataGrid tbody").on("mousedown", "tr", function() {
            $(".selected").not(this).removeClass("selected");
            $(this).toggleClass("selected");
        });

        // 选中根，则选中子
        $("#dataGrid input[type=checkbox]").click(function(e){
            checkboxClickFn(this);
        });

        function checkboxClickFn(_this, autoFlag, parentSelectedFlag){
            var checked = $(_this).attr("checked");
            var menuId = $(_this).parent().parent().attr("data-tt-id");
            var parentMenuId = $(_this).parent().parent().attr("data-tt-parent-id");
            var childCount = $("#dataGrid").find("[data-tt-parent-id='"+menuId+"']").find("input[type=checkbox]").length;
            if(autoFlag){//自动触发
                if(parentSelectedFlag){//如果是需要选中其父节点
                    //将其直接的父节点置为选中状态
                    $("#dataGrid").find("[data-tt-id='"+parentMenuId+"']").find("input[type=checkbox]").each(function(){
                        $(this).attr("checked",true).prop("checked",true);
                        if(parentMenuId == "0"){
                            return;//已经到根节点，直接返回
                        }
                        //自动将该节点的父节点的父节点选中
                        checkboxClickFn(this,true,true);
                    });
                    return;
                }
                if(checked){//如果是已经选中，则其子菜单全部选中
                    if(childCount == 0){
                        return;
                    }
                    $("#dataGrid").find("[data-tt-parent-id='"+menuId+"']").find("input[type=checkbox]").each(function(){
                        $(this).attr("checked",true).prop("checked",true);
                        checkboxClickFn(this,true);
                    });
                }else{//如果是取消选中，则其子菜单全部取消选中
                    if(childCount == 0){
                        return;
                    }
                    $("#dataGrid").find("[data-tt-parent-id='"+menuId+"']").find("input[type=checkbox]").each(function(){
                        $(this).prop("checked",false).removeAttr("checked");
                        checkboxClickFn(this,true);
                    });
                }
                return;
            }
            //手动触发
            if(!checked){
                $(_this).attr("checked",true);
                $(_this).prop("checked",true);
                //将其直接的父节点置为选中状态
                if(menuId != "0"){//选中的不是根节点
                    $("#dataGrid").find("[data-tt-id='"+parentMenuId+"']").find("input[type=checkbox]").each(function(){
                        $(this).attr("checked",true).prop("checked",true);
                        //自动将该节点的父节点的父节点选中
                        checkboxClickFn(this,true,true);
                    });
                }
                if(childCount == 0){
                    return;
                }
                $("#dataGrid").find("[data-tt-parent-id='"+menuId+"']").find("input[type=checkbox]").each(function(){
                    $(this).attr("checked",true).prop("checked",true);
                    checkboxClickFn(this,true);
                });
            }else{
                $(_this).prop("checked",false).removeAttr("checked");
                if(childCount == 0){
                    return;
                }
                $("#dataGrid").find("[data-tt-parent-id='"+menuId+"']").find("input[type=checkbox]").each(function(){
                    $(this).prop("checked",false).removeAttr("checked");
                    checkboxClickFn(this,true);
                });
            }
        }


        if (perm.length > 0) {
            $('#perms :checkbox').each(function () {
                this.checked = (jQuery.inArray(this.value, perm) != -1);
            });
        }
    });
</script>
</@layout>