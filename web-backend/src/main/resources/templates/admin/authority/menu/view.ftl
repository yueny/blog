
    <div>
            <form method="post" action="${base}/admin/authority/menu/update.json" role="form">
                    <#if menuBo??>
                        <input type="hidden" name="id" value="${menuBo.id}" />
                    </#if>

                    <div class="form-group input-group">
                        <span class="input-group-addon">父级</span>
                        <select class="selectpicker show-tick" name="parentId"
                                data-live-search="true" data-live-search-placeholder="搜索">
                            <option value="0" title="根ROOT">/</option>
                            <#list menuRootList as menuRoot>
                                <option value="${menuRoot.id}"
                                        <#if (menuBo.parentId == menuRoot.id)> selected </#if>>
                                    ${menuRoot.name}
                                </option>
                            </#list>
                        </select>
                    </div>

                    <div class="row form-group">
                        <label class="control-label col-sm-1">菜单名</label>
                        <div class="col-lg-5 col-md-3">
                            <input type="text" class="form-control" name="name"
                                   value="${menuBo.name}">
                        </div>
                    </div>

                    <div class="row form-group">
                        <label class="control-label col-sm-1" for="icon">图标</label>
                        <div class="col-lg-5 col-md-6">
                            <input type="text" name="icon" class="form-control"
                                   value="${menuBo.icon}">
                            <i class="${menuBo.icon}"></i>
                        </div>
                    </div>

                    <div class="row form-group">
                        <label class="control-label col-sm-1" for="url">url地址</label>
                        <div class="col-lg-5 col-md-6">
                            <input type="text" name="url" class="form-control"
                                   value="${menuBo.url}">
                        </div>
                    </div>

                    <div class="form-group input-group">
                        <span class="input-group-addon">分配权限</span>
                        <select class="selectpicker show-tick" name="permissionId"
                                data-live-search="true" data-live-search-placeholder="搜索">
                            <#list permissionTree as permission>
                                <option value="${permission.id}"
                                        <#if (menuBo.permissionId == permission.id)> selected </#if>>
                                    ${permission.description} / ${permission.name}
                                </option>
                            </#list>
                        </select>
                    </div>

                    <#if (menuBo)??>
                        <div class="row form-group">
                            <label class="control-label col-sm-2">创建时间</label>
                            <div class="col-lg-5 col-md-2">
                                <p class="form-control-static">${(menuBo.created)?datetime!''}</p>
                            </div>

                            <label class="control-label col-sm-2">更新时间</label>
                            <div class="col-lg-5 col-md-2">
                                <p class="form-control-static">${(menuBo.updated)?datetime!''}</p>
                            </div>
                        </div>
                    </#if>
            </form>
    </div>


    <link rel="stylesheet" href="${base}/static/dist/vendors/bootstrap-select/1.13.7/css/bootstrap-select.css">
    <script src="${base}/static/dist/vendors/bootstrap-select/1.13.7/js/bootstrap-select.js"></script>
    <script src="${base}/static/dist/vendors/bootstrap-select/1.13.7/js/i18n/defaults-zh_CN.js"></script>

    <script type="text/javascript">
        $(function () {
            $('.selectpicker').selectpicker({
                noneSelectedText : '请选择'
            });
        });
    </script>