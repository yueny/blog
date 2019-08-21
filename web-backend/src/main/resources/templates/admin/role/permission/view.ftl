
    <div>
            <form method="post" action="${base}/admin/permission/update.json" role="form">
                    <#if permission??>
                        <input type="hidden" name="id" value="${permission.id}" />
                    </#if>

                    <div class="row form-group">
                        <div class="input-group">
                            <span class="input-group-addon">权限配置值</span>
                            <input type="text" class="form-control" name="name" value="${permission.name}">
                        </div>
                    </div>

                    <div class="row form-group">
                        <label class="control-label col-sm-1">版本号</label>
                        <div class="col-lg-5 col-md-2">
                            <p class="form-control-static">${permission.version}</p>
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
                        <label class="control-label col-sm-1">权重</label>
                        <div class="col-lg-5 col-md-2">
                            <input type="text" class="form-control" name="weight" value="${permission.weight}">
                        </div>
                    </div>
            </form>
    </div>
