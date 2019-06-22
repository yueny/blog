<form id="qForm" class="form-horizontal" method="post" action="update">
    <div class="form-group">
        <label class="col-sm-2 control-label">SMTP地址</label>
        <div class="col-sm-6">
            <input type="text" name="mail_smtp_host" class="form-control"
                   value="${options['mail_smtp_host']}"
                   placeholder="只支持 smtp.126.com 和 smtp.qiye.aliyun.com">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">邮箱账号</label>
        <div class="col-sm-6">
            <input type="text" name="mail_smtp_username" class="form-control"
                   value="${options['mail_smtp_username']}" placeholder="请输入邮箱账户名">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">邮箱密码</label>
        <div class="col-sm-6">
            <input type="text" name="mail_smtp_password" class="form-control" value="${options['mail_smtp_password']}" placeholder="请输入邮箱账户密码">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">pwps</label>
        <div class="col-sm-6">
            <input type="text" name="mail_pw_ps" class="form-control" value="${options['mail_pw_ps']}" placeholder="pwps 加密钥">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">提交</button>
        </div>
    </div>

</form>