<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>${tt}</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">${tt}</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">图片集</h3>
                    <div class="box-tools">
                        <a href="${base}/admin/post/view">上传</a>
                        <input id="default_avatar_btn" class="btn btn-default btn-sm" type="file"
                               name="file" accept="image/*" title="上传">
                        <a class="btn btn-default btn-sm" href="javascrit:;" data-action="batch_del">批量删除</a>
                    </div>
                </div>
                <div class="box-body">
                    <div class="table-responsive">
                        <table id="dataGrid" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="30"><input type="checkbox" class="checkall"></th>
                                <th width="80">#预览图</th>
                                <th width="80">路径</th>
                                <th width="100">上传日期</th>
                                <th width="60">是否生效</th>
                                <th width="180">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list page.content as article>
                                <tr>
                                    <td>
                                        <input type="hidden" name="id" value="">
                                    </td>
                                    <td>
                                        <img src="<@resource src=/>" style="width: 80px;">
                                    </td>
                                    <td>
                                        路径
                                    </td>
                                    <td>${article.created?string('yyyy-MM-dd')}</td>
                                    <td>
                                        有效
                                    </td>
                                    <td>
                                        <a href="javascript:void(0);" class="btn btn-xs btn-primary"
                                           data-id="" rel="delete">删除</a>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="box-footer">
                    <@pager "list" page 5 />
                </div>
            </div>
        </div>
    </div>
</section>

<script>
    $('#default_avatar_btn').change(function(){
        $(this).upload(_MTONS.BASE_PATH + '/post/upload?crop=thumbnail_post_size&uType=avatar', function(data){
            if (data.status == 200) {
                location.reload();
                // var path = data.path;
                // $("#default_avatar_image").css("background", "url(" + path + ") no-repeat scroll center 0 rgba(0, 0, 0, 0)");
                // $("#default_avatar_path").val(path);
            }
        });
    });

    $(function() {
        // 删除
        $('#dataGrid a[rel="delete"]').bind('click', function(){
            var that = $(this);
            layer.confirm('确定删除此项吗?', {
                btn: ['确定','取消'], //按钮
                shade: false //不显示遮罩
            }, function(){
                doDelete(that.attr('data-id'));
            }, function(){
            });
            return false;
        });
        // 批量删除
        $('a[data-action="batch_del"]').click(function () {
            var check_length=$("input[type=checkbox][name=articleBlogId]:checked").length;

            if (check_length == 0) {
                layer.msg("请至少选择一项", {icon: 2});
                return false;
            }

            var ids = [];
            $("input[type=checkbox][name=articleBlogId]:checked").each(function(){
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
