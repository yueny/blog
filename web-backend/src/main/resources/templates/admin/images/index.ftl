<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>>${title}</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">${title}</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">>${title}列表</h3>
                </div>
                <div class="box-body">
                    <#-- table -->
                    <table id="table" data-toolbar="#toolbar"
                           style="word-break:break-all; word-wrap:break-all;"></table>
                </div>
            </div>
        </div>
    </div>
</section>

    <script type="text/javascript">
        $(function() {
            $table = $("#table").bootstrapTable({ // 对应table标签的id
                method: "get",
                url: '${ctx}/admin/authority/role/list.json', // 获取慢服务表格数据的url

                toolbar: '#toolbar',    //工具按钮用哪个容器
                cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
                striped: true,  //表格显示条纹，默认为false
                showRefresh: true,     //是否显示刷新按钮
                pagination: true, // 在表格底部显示分页组件，默认false
                pageList: [10, 25, 50], // 设置页面可以显示的数据条数
                pageNumber: 1,      //初始化加载第一页，默认第一页
                pageSize: 10,      //每页的记录行数（*）
                sidePagination: 'server', // 分页方式：设置为服务器端分页

                //sortName: 'pushTime', // 要排序的字段(建议为数据库字段)
                sortable: true,        // 是否启用排序
                sortOrder: "desc",      // 排序方式
                search: false,                      //是否显示表格搜索
                strictSearch: true,
                clickToSelect: true,                //是否启用点击选中行
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图

                /* 得到查询的参数 */
                queryParams : function (params) {
                    // 获取自定义查询条件
                    var temp = queryParams();
                    // 追加分页条件
                    temp["pageSize"] = params.limit;                        //页面大小
                    temp["pageNo"] = (params.offset / params.limit) + 1;  //页码
                    temp["sort"] = params.sort;                         //排序列名
                    temp["sortOrder"] = params.order;                   //排位命令（desc，asc）

                    return temp;
                },
                columns: [
                    {
                        checkbox: true,
                        visible: true                  //是否显示复选框
                    },
                    {
                        field: 'no',
                        title: '#',
                        width: 40,
                        formatter: function (value, row, index) {
                            //序号正序排序从1开始
                            return index + 1;}
                    },
                    {
                        field: 'name',
                        title: '用户名',
                        align: 'left',
                        valign: 'middle'
                    },
                    {
                        field: 'description',
                        title: '描述',
                        align: 'center',
                        valign: 'middle'
                    },
                    {
                        field: 'status',
                        title: '状态',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value,row,index) {
                            return row.status;
                        }
                    },
                    {
                        field: 'side',
                        title: '是否为超级管理员',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value,row,index) {
                            if (value == "Y") {
                                return '<div class="text-danger">是/' + value + '</div>';
                            }
                            return '<div class="text-info">否/' + value + '</div>';
                        }
                    },
                    {
                        field: 'permissions',
                        title: '资源权限数',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value,row,index) {
                            return '<a class="disabled" disabled="">'+ row.permissions.length +'</a>';
                        }
                    },
                    {
                        field:'button',
                        title:'操作',
                        events:'operateEvents',
                        formatter:'editorFormatter'
                    }
                ],
                onLoadSuccess: function(data){  //加载成功时执行
                    layer.msg('加载成功', {time: 500});
                },
                onLoadError: function(e){  //加载失败时执行
                    layer.msg('加载数据失败:' + e, {icon: 5});
                },
                onDblClickRow: function(row){
                    var $textAndPic = $('<div></div>');

                    $textAndPic.append('<span>');
                    $textAndPic.append('name: ');
                    $textAndPic.append(row.name);
                    $textAndPic.append('</span><br/>');

                    BootstrapDialog.show({
                        title: row.name + "/" + " 明细",
                        message: $textAndPic,
                        size: BootstrapDialog.SIZE_WIDE,
                        draggable: true, // Default value is false，可拖拽
                        closable : true, // Default value is false，点击对话框以外的页面内容可关闭
                        buttons: [
                            {
                                label: '我知道了',
                                icon: 'fa fa-camera-retro',
                                cssClass: 'btn-primary',
                                action: function(dialogRef){
                                    dialogRef.close();
                                }
                            }
                        ]
                    });
                },
                responseHandler: function(res) {
                    return {
                        "total": res.list.paginator.items,//总页数
                        "rows": res.list.list   //数据
                    };
                }
            });
        });
    </script>

    <script>
        // 获取自定义查询条件
        function queryParams() {
            var param = {};
            $('#qForm').find('[name]').each(function () {
                var value = $(this).val();
                if (value != '') {
                    param[$(this).attr('name')] = value;
                }
            });

            return param;
        }

        /*
        * 刷新
        */
        function refresh() {
            //刷新Table，Bootstrap Table 会自动执行重新查询
            $("#table").bootstrapTable('refresh');
        }
    </script>

    <script type="text/javascript">
        var J = jQuery;

        function doDelete(ids) {
            J.getJSON('${base}/admin/authority/role/delete.json',
                J.param({'id': ids}, true),
                function(result){
                    if(result.code == 0){
                        refresh.call();
                    }else{
                        layer.msg('数据删除失败：'+ result.message, {icon: 5});
                    }
                });
        }

        window.operateEvents={
            "click a[data-evt=trash]":function (e,value,row,index){
                var ids = [];
                ids.push(row.id);

                layer.confirm('确定删除此项吗?', {
                    btn: ['确定', '取消'], //按钮
                    shade: false //不显示遮罩
                }, function () {
                    doDelete(ids);
                }, function () {
                });
                return false;
            }
        }

        //操作列
        function editorFormatter(value,row,index) {
            <#-- 超级管理员数据不可被修改，其他的均可被修改 -->
                    return[
                        '<a href="javascript:void(0);" data-evt="trash" class="btn btn-xs btn-primary act_delete" data-id="' + row.id + '">' +
                        '   <i class="fa fa-bitbucket"></i> 删除' +
                        '</a>'
                    ].join("");
        }
    </script>
</@layout>