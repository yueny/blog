<#include "/admin/utils/ui.ftl"/>
<@layout>
    <link href="${base}/dist/vendors/jquery-treegrid/0.3.0/css/jquery.treegrid.css" rel="stylesheet">

    <script src="${base}/dist/vendors/jquery-treegrid/0.3.0/js/jquery.treegrid.js"></script>
    <script src="https://unpkg.com/bootstrap-table@1.15.4/dist/extensions/treegrid/bootstrap-table-treegrid.min.js"></script>

<section class="content-header">
    <h1>权限管理</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">权限管理</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">权限管理</h3>
                </div>
                <div class="box-body">
                    <div id="toolbar" class="btn-group" >
                        <button id="playSound" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-play" aria-hidden="true"></span>进入录音库
                        </button>
                        <button id="downloadSound" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-cloud-download" aria-hidden="true"></span>下载录音
                        </button>
                        <button id="searchLog" type="button" class="btn btn-default">
                            <span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查看会议日志
                        </button>
                    </div>

                    <#-- table -->
                    <table id="table"></table>
                </div>

            </div>
        </div>
    </div>

</section>

    <script type="text/javascript">
        var $table = $("#table");

        $(function() {
            $table.bootstrapTable({
                method: "get",
                url: '${ctx}/admin/permission/list.json',
                toolbar : "#toolbar" , //工具按钮用哪个容器
                cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
                striped: true,  //表格显示条纹，默认为false
                showRefresh: true,     //是否显示刷新按钮
                sidePagination: 'server', // 分页方式：设置为服务器端分页
                clickToSelect: true,                //是否启用点击选中行
                showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                showColumns: true,
                search: true,           //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                searchOnEnterKey: true, // 回车触发搜索
                searchAlign: 'left',
                buttonsAlign: 'right', // 按钮位置
                columns: [
                    // {
                    //     field: 'no',
                    //     title: '#',
                    //     width: 40,
                    //     formatter: function (value, row, index) {
                    //         //序号正序排序从1开始
                    //         return index + 1;
                    //
                    //         // 即使翻页， 序号递增
                    //         // var pageSize=$('#tableId').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                    //         // var pageNumber=$('#tableId').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                    //         // return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
                    //     }
                    // },
                    {
                        field: 'ck',
                        checkbox: true,
                        visible: true
                    },
                    {
                        field: 'description',
                        title: '权限描述'
                    },
                    {
                        field: 'parentId',
                        title: 'parentId',
                        visible: false
                    },
                    {
                        field: 'name',
                        title: '权限配置值'
                    },
                    {
                        field: 'weight',
                        title: '权重'
                    },
                    {
                        field: 'version',
                        title: '版本'
                    },
                    {
                        field:'button',
                        title:'操作',
                        events:'operateEvents',
                        formatter:'editorFormatter'
                    }
                ],
                idField: 'id',
                uniqueId: "id",   //每一行的唯一标识，一般为主键列
                treeShowField: 'name',
                parentIdField: 'parentId',
                onLoadSuccess: function(data){  //加载成功时执行
                    $table.treegrid({
                        initialState: 'collapsed',//收缩
                        treeColumn: 1,//指明第几列数据改为树形
                        expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                        expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                        onChange: function() {
                            $table.bootstrapTable('resetWidth');
                        }
                    });
                    layer.msg('加载成功', {time: 500});
                },
                onLoadError: function(){  //加载失败时执行
                    layer.msg('加载数据失败', {icon: 5});
                },
                onDblClickRow: function(row){
                    $.showDialog("${base}/admin/permission/view?id="+row.id, "GET",
                        row.description + "「" + row.name + "」明细",
                        function(){
                            //刷新Table，Bootstrap Table 会自动执行重新查询
                            $table.bootstrapTable('refresh');
                        });
                },
                responseHandler: function(res) {
                    return {
                        "rows": res.data   //数据
                    };
                }
            })
        });
    </script>

    <script>
        $(function() {
            // 页面加载时，让a标签执行ajaxTodialog方法
            // dialogs
            if ($.fn.ajaxTodialog) {
                $("a[target=dialog]").ajaxTodialog();
            }
        });

        //操作列
        function editorFormatter(value,row,index) {
            return[
                '<a id="tableEditor" href="javascript:void(0);" class="btn btn-xs btn-success">编辑</button>',
                // '<a id="tableDelete" href="javascript:void(0);" class="deletetor btn btn-xs btn-danger">删除</button>'
            ].join("");
        }

        window.operateEvents={
            "click #tableEditor":function (e,value,row,index){
                $.showDialog("${base}/admin/permission/view?id="+row.id, "GET",
                    row.description + "「" + row.name + "明细",
                    function(){
                        //刷新Table，Bootstrap Table 会自动执行重新查询
                        $table.bootstrapTable('refresh');
                    });
            },
            // "click #tableDelete":function (e,value,row,index){
            //     //.
            // }
        }
    </script>

</@layout>
