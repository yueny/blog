<form id="qForm" class="form-horizontal" method="post" action="update">
    <div class="form-group">
        <label class="col-sm-2 control-label">存储方式</label>
        <div class="col-sm-3">
            <select class="form-control" name="storage_scheme" data-select="${options['storage_scheme']}">
                <option value="native">本地存储/NATIVE</option>
                <option value="image">自有图片服务器/IMAGE</option>
                <option value="upyun">又拍云/UPYUN</option>
                <option value="aliyun">阿里云/ALIYUN</option>
                <option value="qiniu">七牛云/QINIU</option>
            </select>
        </div>
    </div>

    <#--&lt;#&ndash; image_server_force_local &ndash;&gt;-->
    <#--<div class="form-group">-->
        <#--<label class="col-sm-2 form-check-label">附加本地存储</label>-->
        <#--<div class="col-sm-3">-->
            <#--<select class="form-control" name="image_server_force_local"-->
                    <#--data-select="${options['image_server_force_local']}">-->
                <#--<option value="1">附加</option>-->
                <#--<option value="0">不附加</option>-->
            <#--</select>-->
        <#--</div>-->
    <#--</div>-->

    <div class="scheme" data-scheme="native">
        <div class="form-group">
            <label class="col-sm-2 control-label">本地图片根目录</label>
            <div class="col-sm-6">
                <input type="text" name="native_server_location" class="form-control" value="${options['native_server_location']}" placeholder="本地图片根目录, 非必填">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">本地图片uri</label>
            <div class="col-sm-6">
                <input type="text" name="native_server_uri" class="form-control" value="${options['native_server_uri']}" placeholder="本地图片uri, 非必填">
            </div>
        </div>
    </div>
    <div class="scheme" data-scheme="image">
        <div class="form-group">
            <label class="col-sm-2 control-label">图片服务器根目录</label>
            <div class="col-sm-6">
                <input type="text" name="image_server_location" class="form-control" value="${options['image_server_location']}" placeholder="图片服务器根目录">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">图片服务器uri</label>
            <div class="col-sm-6">
                <input type="text" name="image_server_uri" class="form-control" value="${options['image_server_uri']}" placeholder="图片服务器uri">
            </div>
        </div>
    </div>
    <div class="scheme" data-scheme="upyun">
        <div class="form-group">
            <label class="col-sm-2 control-label">空间名称</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_bucket" class="form-control" value="${options['upyun_oss_bucket']}" placeholder="又拍云bucket名称">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">操作员名称</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_operator" class="form-control" value="${options['upyun_oss_operator']}" placeholder="又拍云operator">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">操作员密码</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_password" class="form-control" value="${options['upyun_oss_password']}" placeholder="又拍云operator password">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">域名绑定</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_domain" class="form-control" value="${options['upyun_oss_domain']}" placeholder="示例: http://mtons.b0.upaiyun.com">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">文件目录</label>
            <div class="col-sm-6">
                <input type="text" name="upyun_oss_src" class="form-control" value="${options['upyun_oss_src']}" placeholder="示例: /static/">
            </div>
        </div>
    </div>
    <div class="scheme" data-scheme="aliyun">
        <div class="form-group">
            <label class="col-sm-2 control-label">空间名称</label>
            <div class="col-sm-6">
                <input type="text" name="aliyun_oss_bucket" class="form-control" value="${options['aliyun_oss_bucket']}" placeholder="阿里oss bucket名称">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">AccessKey</label>
            <div class="col-sm-6">
                <input type="text" name="aliyun_oss_key" class="form-control" value="${options['aliyun_oss_key']}" placeholder="AccessKeyId">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">AccessSecret</label>
            <div class="col-sm-6">
                <input type="text" name="aliyun_oss_secret" class="form-control" value="${options['aliyun_oss_secret']}" placeholder="AccessKeySecret">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">EndPoint</label>
            <div class="col-sm-6">
                <input type="text" name="aliyun_oss_endpoint" class="form-control" value="${options['aliyun_oss_endpoint']}" placeholder="示例: oss-cn-beijing.aliyuncs.com">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">文件目录</label>
            <div class="col-sm-6">
                <input type="text" name="aliyun_oss_src" class="form-control" value="${options['aliyun_oss_src']}" placeholder="示例: static/images/">
            </div>
        </div>
    </div>
    <div class="scheme" data-scheme="qiniu">
        <div class="form-group">
            <label class="col-sm-2 control-label">空间名称</label>
            <div class="col-sm-6">
                <input type="text" name="qiniu_oss_bucket" class="form-control" value="${options['qiniu_oss_bucket']}" placeholder="七牛云bucket名称">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">AccessKey</label>
            <div class="col-sm-6">
                <input type="text" name="qiniu_oss_key" class="form-control" value="${options['qiniu_oss_key']}" placeholder="AccessKey">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">SecretKey</label>
            <div class="col-sm-6">
                <input type="text" name="qiniu_oss_secret" class="form-control" value="${options['qiniu_oss_secret']}" placeholder="SecretKey">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">域名绑定</label>
            <div class="col-sm-6">
                <input type="text" name="qiniu_oss_domain" class="form-control" value="${options['qiniu_oss_domain']}" placeholder="示例: http://qiniu.mtons.com">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">文件目录</label>
            <div class="col-sm-6">
                <input type="text" name="qiniu_oss_src" class="form-control" value="${options['qiniu_oss_src']}" placeholder="示例: /static/">
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">提交</button>
        </div>
    </div>
</form>
<script>
    $(function () {
        $('select[name=storage_scheme]').change(function () {
            var value = $(this).val();
            $('.scheme').each(function () {
                if ($(this).data('scheme') === value) {
                    $(this).show();
                } else {
                    $(this).hide();
                }
            });
        }).trigger('change');
    });
</script>