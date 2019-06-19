    <form method="POST" name="myform" action="<c:url value='/admin/channel/readerUpdateSave.htm' />"
          target="_self" class="form-horizontal" enctype="multipart/form-data">
        <#if view??>
                            <input type="hidden" name="id" value="${view.id}" />
        </#if>
        <input type="hidden" name="weight" value="${view.weight!0}">
        <input type="hidden" id="thumbnail" name="thumbnail" value="${view.thumbnail}">
        <input type="hidden" id="thumbnailCode" name="thumbnailCode" value="${view.thumbnailCode}">

        <div class="form-group">
            <label class="col-lg-2 control-label">名称</label>
            <div class="col-lg-3">
                <input type="text" name="name" class="form-control" value="${view.name}" required>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-2 control-label">渠道编号</label>
            <div class="col-lg-3">
                <input type="hidden" name="channelCode" class="form-control" value="${view.channelCode}">
                <label class="control-label">${view.channelCode}</label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">父渠道名称</label>
            <div class="col-lg-3">
            <#-- 此处后续会修改 -->
                <input type="hidden" name="parentChannelCode" class="form-control" value="${view.parentChannelCode}">
                <label class="control-label">
                    <a <#if view.parentChannelVo?? && view.parentChannelVo.id??>
                                            href="${base}/admin/channel/view/${view.parentChannelVo.channelCode}.html"
                    </#if>
                    >${view.parentChannelVo.name}</a>
                </label>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-2 control-label">唯一标识</label>
            <div class="col-lg-3">
                <input type="hidden" name="key" class="form-control" value="${view.key}" readonly>
                <label class="control-label">
                                    <#if view.key??>
                                        ${view.key}
                                    <#else>
                                        系统生成...
                                    </#if>
                </label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label"><a title="如果是节点，则存在链接" disabled>是否为节点</a></label>
            <div class="col-lg-3">
                                <#list nodeTypeList as nodeType>
                                    <div class="radio radio-danger">
                                        <input type="radio" name="nodeType" value="${nodeType}" required
                                            <#if view.nodeType??>
                                            <#-- 修改 -->
                                                <#if (view.nodeType == '' || view.nodeType == nodeType)>
                                                    checked
                                                </#if>
                                            <#else>
                                            <#-- 新增 -->
                                                <#if nodeType_index == 0>
                                                    checked
                                                </#if>
                                            </#if>>
                                        </input>
                                        <label for="nodeType">
                                            ${nodeType} / ${nodeType.desc}
                                        </label>
                                    </div>
                                </#list>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">下挂节点</label>
            <div class="col-lg-3">

            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2 control-label">对外释义链接</label>
            <div class="col-lg-3">
                <input type="text" name="flag" class="form-control" value="${view.flag}"
                                   <#if view.flag??>readonly</#if><#-- 如果view.flag有值, 则为修改, 对外释义不允许再次修改-->
                >
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-2 control-label">导航栏状态</label>
            <div class="col-lg-3">
                <select name="status" class="form-control" data-select="${view.status}">
                    <option value="0">显示</option>
                    <option value="1">隐藏</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-lg-2 control-label">缩略图</label>
            <div class="col-lg-3">
                <div class="thumbnail-box">
                    <div class="convent_choice" id="thumbnail_image" <#if view.thumbnail?? && view.thumbnail?length gt 0> style="background: url(${base + view.thumbnail}) no-repeat scroll top;" </#if>>
                        <div class="upload-btn">
                            <label>
                                <span>点击选择一张图片</span>
                                <input id="upload_btn" type="file" name="file" accept="image/*" title="点击添加图片">
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </form>