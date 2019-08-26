<#include "/admin/utils/ui.ftl"/>
<@layout>
<section class="content-header">
    <h1>${title}</h1>
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
                    <h3 class="box-title">${title}</h3>
                </div>
                <div class="box-body">
                    <div id="toolbar" class="box-tools btn-group">
                        <a class="btn btn-default btn-sm" onclick="doAdd('添加菜单');">
                            <span class="glyphicon glyphicon-plus-sign"></span>
                            添加菜单
                        </a>
                    </div>

                    <#-- 查询条件 -->
                    <form id="qForm" class="form-inline search-row">

                        <div class="form-group">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    url(<span class="text-danger">模糊</span>)
                                </button>
                                <input type="text" class="form-control" name="url"
                                       placeholder='链接地址，模糊查询, sql(%a%)' title='链接地址，模糊查询'>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    name(<span class="text-danger">模糊</span>)
                                </button>
                                <input type="text" class="form-control" name="name"
                                       placeholder='菜单名，模糊查询, sql(%a%)' title='菜单名，模糊查询'>
                            </div>
                        </div>

                        <div class="pull-right btn-group">
                            <button id="btn_search" onclick="searchInfo()" type="button"
                                    class="btn btn-primary btn-space">
                                <span class="fa fa-search" aria-hidden="true" class="btn-icon-space"></span>
                                查询(暂未开启)
                            </button>
                            <button id="btn_reset" onclick="resetSearch()" type="button"
                                    class="btn btn-default btn-space">
                                <span class="fa fa-eraser" aria-hidden="true" class="btn-icon-space"></span>
                                重置
                            </button>
                            <#--
                            <button id="btn_refresh" onclick="refresh()" type="button"
                                    class="btn btn-default btn-space">
                                <span class="fa fa-refresh" class="btn-icon-space"></span>
                            </button>
                            -->
                        </div>
                    </form>

                    <#-- table -->
                    <table id="table" data-click-to-select="true" class="table table-bordered">
                        <thead>
                            <tr >
                                <th data-checkbox="true" data-visible="true" data-field="ck"></th>
                                <th data-field="name" width="100">菜单名</th>
                                <th data-visible="false" data-field="parentId">parentId</th>
                                <th data-field="url">链接地址</th>
                                <th data-field="weight">权重</th>
                                <th data-field="icon" data-formatter="iconFormatter">图标</th>
                                <th data-field="permission.name" data-formatter="nameFormatter">分配资源权限值</th>
                                <th data-field="updated">更新时间</th>
                                <th data-field="button" data-events="operateEvents" data-formatter="editorFormatter">操作</th>
                            </tr>
                        </thead>

                        <tbody>
                        </tbody>
                    </table>
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
                url: '${ctx}/admin/authority/menu/list.json',
                toolbar : "#toolbar" , //自定义工具按钮容器， 额外追加
                cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
                striped: true,  //表格显示条纹，默认为false
                showRefresh: true,     //是否显示刷新按钮
                sidePagination: 'server', // 分页方式：设置为服务器端分页
                clickToSelect: true,                //是否启用点击选中行
                showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                showColumns: true,
                search: false,           //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                searchOnEnterKey: true, // 回车触发搜索
                searchAlign: 'right',
                buttonsAlign: 'right', // 按钮位置
                /* 得到查询的参数 */
                queryParam : function (params) {
                    // 获取自定义查询条件
                    var temp = queryParams();
                    temp["sort"] = params.sort;                         //排序列名
                    temp["sortOrder"] = params.order;                   //排位命令（desc，asc）
                    return temp;
                },
                // rowStyle: function (row, index) {//row 表示行数据，object,index为行索引，从0开始
                //     var style = "";
                //     if (row.weight >  0) {
                //         style = { css: { 'color': 'red' } };
                //     }
                //     return  style;
                // },
                // columns: [
                //     {
                //         field: 'ck',
                //         checkbox: true,
                //         visible: true
                //     },
                //     {
                //         field: 'name',
                //         title: '菜单名',
                //         width: 100
                //     },
                //     {
                //         field: 'parentId',
                //         title: 'parentId',
                //         visible: false
                //     },
                //     {
                //         field: 'url',
                //         title: '链接地址'
                //     },
                //     {
                //         field: 'weight',
                //         title: '权重'
                //     },
                //     {
                //         field: 'icon',
                //         title: '图标',
                //         formatter:'iconFormatter'
                //     },
                //     {
                //         field: 'permission.name',
                //         title: '资源权限值',
                //         formatter:'nameFormatter'
                //     },
                //     {
                //         field: 'updated',
                //         title: '更新时间'
                //     },
                //     {
                //         field:'button',
                //         title:'操作',
                //         events:'operateEvents',
                //         formatter:'editorFormatter'
                //     }
                // ],
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
                    $.showDialog("${base}/admin/authority/menu/view?id="+row.id, "GET",
                        "菜单「" + row.name + "」明细",
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
    </script>

    <script>
        var J = jQuery;

        //添加
        function doAdd(title) {
            $.showDialog("${base}/admin/authority/menu/view", "GET",
                title, refresh);
        }
        // 删除
        function doDelete(ids) {
            J.post('${base}/admin/authority/menu/delete.json',
                J.param({'id': ids}, true),
                function(result){
                    if(tools.success(result.code)){
                        refresh.call();
                    }else{
                        layer.msg('数据删除失败：'+ result.message, {icon: 5});
                    }
                }, "json");
        }

        // 重置
        function resetSearch() {
            $('#qForm').find('[name]').each(function () {
                $(this).val('');
            });
        }

        /*
        * 查询
        */
        function searchInfo() {
            refresh();
        }

        /*
        * 刷新
        */
        function refresh() {
            //刷新Table，Bootstrap Table 会自动执行重新查询
            $table.bootstrapTable('refresh');
        }

        // 显示title
        function nameFormatter(value,row,index) {
            return '<span title="' + row.permission.description + '">' +
                '<a href="${ctx}/admin/authority/permission/index.html">'+ value +'</a>' +
                '</span>';
        }

        //操作列
        function editorFormatter(value,row,index) {
            return[
                '<a id="tableEditor" href="javascript:void(0);" class="btn btn-xs btn-success">编辑</button>',
                '<a id="tableDelete" href="javascript:void(0);" class="btn btn-xs btn-danger">删除</button>'
            ].join("");
        }

        window.operateEvents={
            "click #tableEditor":function (e,value,row,index){
                $.showDialog("${base}/admin/authority/menu/view?id="+row.id, "GET",
                    "菜单「" + row.name + "」明细", refresh);
            },
            "click #tableDelete":function (e,value,row,index){
                var ids = [];
                ids.push(row.id);

                if (ids.length == 0) {
                    layer.msg("请至少选择一项", {icon: 2});
                    return false;
                }

                layer.confirm('确定删除这' + ids.length + '条数据吗?', {
                    btn: ['确定','取消'], //按钮
                    shade: false //不显示遮罩
                }, function(){
                    doDelete(ids);
                }, function(){
                    //.
                });
            }
        }
    </script>

</@layout>
