<#macro selectIterator nodes>
    <#-- 循环节点-->
    <#list nodes as row>
        <optgroup label="${row.description}">
            <option value="${row.id}"
                    <#if (permission.parentId == row.id)> selected </#if>>
                ${row.description} / ${row.name}
            </option>

            <#if (row.items)?? && ((row.items)?size > 0) >
                <#list row.items as item>
                    <option value="${item.id}"
                            <#if (permission.parentId == item.id)> selected </#if>>
                        ${item.description} / ${item.name}
                    </option>

                    <#-- 判断是否有子集 -->
                    <#-- 权限树上不会存在3级菜单目录，因此不做遍历。且插件也不支持
                    <#if item.items??>
                        <@selectIterator nodes=item.items />
                    </#if>
                     -->
                </#list>
            </#if>
        </optgroup>
    </#list>
</#macro>

    <div>
            <form method="post" action="${base}/admin/authority/permission/update.json" role="form">
                    <#if permission??>
                        <input type="hidden" name="id" value="${permission.id}" />
                    </#if>

                    <div class="row form-group">
                        <label class="control-label col-sm-1">版本号</label>
                        <div class="col-lg-5 col-md-2">
                            <p class="form-control-static">${permission.version}</p>
                        </div>
                    </div>

                    <div class="form-group input-group">
                        <span class="input-group-addon">父级目录</span>
                        <select class="selectpicker show-tick" name="parentId"
                                data-live-search="true" data-live-search-placeholder="搜索"
                                data-selected-text-format="count"
                                data-style="btn-primary"
                                <#-- Select/deselect all options -->
                                data-actions-box="true">
                            <option value="0" title="/">/</option>
                            <#-- 循环下拉列表 -->
                            <@selectIterator nodes=permissionList />
<#--                            <#list permissionList as permissionRoot>-->
<#--                                <option value="${permissionRoot.id}"-->
<#--                                        <#if (permission.parentId == permissionRoot.id)> selected </#if>>-->
<#--                                    ${permissionRoot.description} / ${permissionRoot.name}-->
<#--                                </option>-->
<#--                            </#list>-->
                        </select>
                    </div>


                    <#-- 类型， 0菜单，1功能 -->
                    <div class="form-group input-group">
                        <label for="status" class="col-lg-2 control-label">功能</label>
                        <select class="selectpicker show-tick" name="funcType" required>
                            <#list funcTypeList as funcTypeNode>
                                <option value="${funcTypeNode}"
                                        <#if (funcTypeNode == permission.funcType)> selected </#if>>
                                    ${funcTypeNode.desc}「${funcTypeNode}」
                                </option>
                            </#list>
                        </select>
                        <a class="plus">
                            <i class="tooltip-icon-info glyphicon glyphicon-exclamation-sign"></i>
                        </a>
<#--                        <span>功能只能挂在菜单下， 菜单只能挂在菜单下</span>-->
                    </div>

                    <div class="row form-group">
                        <label class="control-label col-sm-1">权限配置值</label>
                        <div class="col-lg-5 col-md-3">
                            <input type="text" class="form-control" name="name"
                                   value="${permission.name}">
                        </div>
                    </div>

                    <div class="row form-group">
                        <label class="control-label col-sm-1" for="name">权限描述</label>
                        <div class="col-lg-5 col-md-6">
                            <input type="text" name="description" class="form-control"
                                   value="${permission.description}">
                        </div>
                    </div>

                    <div class="row form-group">
                        <label class="control-label col-sm-1" for="name">权重</label>
                        <div class="input-group spinner">
                            <input type="number" class="form-control" min="0" max="128"
                                   name="weight" value="${permission.weight}">
                            <div class="input-group-btn-vertical">
                                <button class="btn btn-default" type="button"><i class="fa fa-caret-up"></i></button>
                                <button class="btn btn-default" type="button"><i class="fa fa-caret-down"></i></button>
                            </div>
                        </div>
                    </div>

            </form>
    </div>


    <link rel="stylesheet" href="${base}/dist/js/comp/number-spinner.css">
    <link rel="stylesheet" href="${base}/static/dist/vendors/bootstrap-select/1.13.7/css/bootstrap-select.css">
    <script src="${base}/static/dist/vendors/bootstrap-select/1.13.7/js/bootstrap-select.js"></script>
    <script src="${base}/static/dist/vendors/bootstrap-select/1.13.7/js/i18n/defaults-zh_CN.js"></script>
    <script src="${base}/dist/js/comp/number-spinner.js"></script>

    <script type="text/javascript">
        $(function () {
            $('.selectpicker').selectpicker({
                noneSelectedText : '请选择'
            });

            var tips;
            $('i.tooltip-icon-info').on({
                mouseenter:function(){
                    var that = this;
                    tips =layer.tips(
                        "<span style='color:#000;'>功能只能挂在菜单下， 菜单只能挂在菜单下</span>",
                        that,
                        {tips:[2,'#fff'],time:0,area: 'auto',maxWidth:500}
                    );
                },
                mouseleave:function(){
                    layer.close(tips);
                }
            });

        });
    </script>