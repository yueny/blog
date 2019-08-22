
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
                        <span class="input-group-addon">父级</span>
                        <select class="selectpicker show-tick" name="parentId">
                            <option value="0" title="根ROOT">/</option>
                            <#list permissionRootList as permissionRoot>
                                <option value="${permissionRoot.id}"
                                        <#if (permission.parentId == permissionRoot.id)> selected </#if>>
                                    ${permissionRoot.description} / ${permissionRoot.name}
                                </option>
                            </#list>
                        </select>
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
                /* 当设置为true，增加了一个搜索框的下拉selectpicker的顶部。 */
                liveSearch:true
            });
        });
    </script>