<form id="qForm" class="form-horizontal" method="post" action="update">

    <div class="form-group">
        <label class="col-sm-2 control-label">默认头像</label>
        <div class="col-sm-6">
            <input type="text" name="user_avatar_default_path" class="form-control" value="${options['default_avatar_path']}">
        </div>
    </div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-primary">提交</button>
        </div>
    </div>
</form>