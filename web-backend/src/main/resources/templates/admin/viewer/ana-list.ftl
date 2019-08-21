<#include "/admin/utils/ui.ftl"/>
<@layout>

<section class="content-header">
    <h1>访问行为分析</h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li class="active">访问行为分析</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">访问行为分析</h3>
                </div>
                <div class="box-body">
                    <input type="hidden" id="currentDayer" value="${nowDate}">

                    <#-- 查询条件 -->
                    <form id="qForm" class="form-inline search-row">
                        <div class="form-group">
                            <div class="input-group date" data-provide="datepicker">
                                 <span class="input-group-addon">
                                     <span class="glyphicon glyphicon-th"></span>
                                 </span>
                                <input type="text" class="form-control datepicker"
                                       id="createDate" name="createDate" value="${nowDate}"
                                       placeholder='日期' title='日期'>
                            </div>
                        </div>

                        <div class="pull-right btn-group">
                            <button id="btn_search" onclick="searchInfo()" type="button"
                                    class="btn btn-primary btn-space">
                                <span class="fa fa-search" aria-hidden="true" class="btn-icon-space"></span>
                                查询
                            </button>
                            <button id="btn_reset" onclick="resetSearch()" type="button"
                                    class="btn btn-default btn-space">
                                <span class="fa fa-eraser" aria-hidden="true" class="btn-icon-space"></span>
                                重置
                            </button>
                        </div>
                    </form>

                    <#-- table -->
                    <table id="table" data-toolbar="#toolbar"
                           style="word-break:break-all; word-wrap:break-all;"></table>
                </div>

            </div>
        </div>
    </div>

</section>
    <script type="text/javascript">
        $(function () {
            $.fn.datepicker.defaults.format = "yyyy-mm-dd";
            $.fn.datepicker.defaults.autoclose = true;//选择日期后自动关闭日期选择框
            $.fn.datepicker.defaults.todayBtn = true;//显示今天按钮
            $.fn.datepicker.defaults.todayHighlight = true;//当天高亮显示
            $.fn.datepicker.defaults.pickerPosition="bottom-left",
            <#-- 只显示一年的日期365天, or "-120d"; -->
            // $.fn.datepicker.defaults.startDate = new Date(new Date()-1000 * 60 * 60 * 24 * 365);
            $.fn.datepicker.defaults.endDate = new Date();
        });
    </script>

<#--    <script type="text/javascript">-->
<#--        $(function() {-->
<#--            $table = $("#table").bootstrapTable({ // 对应table标签的id-->
<#--                method: "get",-->
<#--                url: '${ctx}/admin/viewer/get/list.json', // 获取慢服务表格数据的url-->

<#--                toolbar: '#toolbar',    //工具按钮用哪个容器-->
<#--                cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true-->
<#--                striped: true,  //表格显示条纹，默认为false-->
<#--                showRefresh: true,     //是否显示刷新按钮-->
<#--                pagination: true, // 在表格底部显示分页组件，默认false-->
<#--                pageList: [10, 25, 50], // 设置页面可以显示的数据条数-->
<#--                pageNumber: 1,      //初始化加载第一页，默认第一页-->
<#--                pageSize: 10,      //每页的记录行数（*）-->
<#--                sidePagination: 'server', // 分页方式：设置为服务器端分页-->

<#--                //sortName: 'pushTime', // 要排序的字段(建议为数据库字段)-->
<#--                sortable: true,        // 是否启用排序-->
<#--                sortOrder: "desc",      // 排序方式-->
<#--                search: false,                      //是否显示表格搜索-->
<#--                strictSearch: true,-->
<#--                clickToSelect: true,                //是否启用点击选中行-->
<#--                uniqueId: "id",                     //每一行的唯一标识，一般为主键列-->
<#--                showToggle: true,                   //是否显示详细视图和列表视图的切换按钮-->
<#--                cardView: false,                    //是否显示详细视图-->

<#--                /* 得到查询的参数 */-->
<#--                queryParams : function (params) {-->
<#--                    // 获取自定义查询条件-->
<#--                    var temp = queryParams();-->
<#--                    // 追加分页条件-->
<#--                    temp["pageSize"] = params.limit;                        //页面大小-->
<#--                    temp["pageNo"] = (params.offset / params.limit) + 1;  //页码-->
<#--                    temp["sort"] = params.sort;                         //排序列名-->
<#--                    temp["sortOrder"] = params.order;                   //排位命令（desc，asc）-->

<#--                    return temp;-->
<#--                },-->
<#--                columns: [-->
<#--                    {-->
<#--                        checkbox: true,-->
<#--                        visible: true                  //是否显示复选框-->
<#--                    },-->
<#--                    {-->
<#--                        field: 'clientIp',-->
<#--                        title: 'IP',-->
<#--                        align: 'left',-->
<#--                        valign: 'middle',-->
<#--                        width: 130-->
<#--                    },-->
<#--                    {-->
<#--                        field: 'resourcePath',-->
<#--                        title: '访问资源路径',-->
<#--                        align: 'left',-->
<#--                        valign: 'middle'-->
<#--                    },-->
<#--                    {-->
<#--                        field: 'method',-->
<#--                        title: '类型',-->
<#--                        align: 'center',-->
<#--                        valign: 'middle'-->
<#--                    },-->
<#--                    {-->
<#--                        field: 'parameterJson',-->
<#--                        title: '请求参数',-->
<#--                        align: 'center',-->
<#--                        valign: 'middle'-->
<#--                    },-->
<#--                    // {-->
<#--                    //     field: 'clientAgent',-->
<#--                    //     title: 'agent',-->
<#--                    //     align: 'center',-->
<#--                    //     valign: 'middle',-->
<#--                    //     formatter: function (value, row, index){-->
<#--                    //         return "<span class='text-muted'>" + value + "</span>";-->
<#--                    //     }-->
<#--                    // },-->
<#--                    {-->
<#--                        field: 'created',-->
<#--                        title: '访问时间',-->
<#--                        align: 'center',-->
<#--                        valign: 'middle',-->
<#--                        width: 100-->
<#--                    }-->
<#--                ],-->
<#--                onLoadSuccess: function(data){  //加载成功时执行-->
<#--                    layer.msg('加载成功', {time: 500});-->
<#--                },-->
<#--                onLoadError: function(){  //加载失败时执行-->
<#--                    layer.msg('加载数据失败', {icon: 5});-->
<#--                },-->
<#--                onDblClickRow: function(row){-->
<#--                    var $textAndPic = $('<div></div>');-->

<#--                    // // 带折叠-->
<#--                    // $textAndPic.append('<div id="accordionB">'+-->
<#--                    //     '<div>'+-->
<#--                    //         '<a data-toggle="collapse" data-parent="#accordionB" href="#collapseO">数据信息</a>'+-->
<#--                    //     '</div>'+-->
<#--                    //     '<div id="collapseO" class="panel-collapse collapse">'+-->
<#--                    //     '<div class="panel-body">'+-->
<#--                    //     '<span class="text-info">起始时间</span>'+-->
<#--                    //     '[<span class="text-danger">持续总时间</span> (<span class="text-success">自身消耗的时间</span>), <span class="text-primary">在父entry中所占的时间比例</span>, <span class="text-muted">在总时间中所占的时间比例</span>]'+-->
<#--                    //     '-'+-->
<#--                    //     '<span class="text-warning">entry信息</span>'+-->
<#--                    //     '<br/>'+-->
<#--                    //     'eg:'+-->
<#--                    //     '<span class="text-info">0</span>'+-->
<#--                    //     '[<span class="text-danger">739ms</span>(<span class="text-success">48ms</span>), <span class="text-primary">100%</span>, <span class="text-muted">100%</span>]'+-->
<#--                    //     '-'+-->
<#--                    //     '<span class="text-warning">DubboInvoke interface com.a.b.c.DxxxQueryService#query</span>'+-->
<#--                    //     '</div>'+-->
<#--                    //     '</div>'+-->
<#--                    //     '</div>');-->

<#--                    $textAndPic.append('<span>');-->
<#--                    $textAndPic.append('id: ');-->
<#--                    $textAndPic.append(row.id);-->
<#--                    $textAndPic.append('</span><br/>');-->

<#--                    $textAndPic.append('<span>');-->
<#--                    $textAndPic.append('clientIp: ');-->
<#--                    $textAndPic.append(row.clientIp);-->
<#--                    $textAndPic.append('</span><br/>');-->

<#--                    $textAndPic.append('<span>');-->
<#--                    $textAndPic.append('访问资源路径: ');-->
<#--                    $textAndPic.append('<br/>');-->
<#--                    $textAndPic.append('<span class="text-success">' + row.resourcePath + '</span>');-->
<#--                    $textAndPic.append('  <big><b>|</b></big>  ');-->
<#--                    $textAndPic.append('<span class="text-danger">' + row.method + '</span>');-->
<#--                    $textAndPic.append('</span><br/>');-->

<#--                    $textAndPic.append('<span>');-->
<#--                    $textAndPic.append('客户端: ');-->
<#--                    $textAndPic.append('<span class="text-muted">' + row.clientAgent + ' </span>');-->
<#--                    $textAndPic.append('</span><br/>');-->

<#--                    $textAndPic.append('<span>');-->
<#--                    $textAndPic.append('请求参数: ');-->
<#--                    $textAndPic.append('<pre>' + row.parameterJson + '</pre>');-->
<#--                    $textAndPic.append('</span>');-->

<#--                    $textAndPic.append('<span>');-->
<#--                    $textAndPic.append('访问时间: ');-->
<#--                    $textAndPic.append('<span class="text-danger">' + row.created + ' </span>');-->
<#--                    $textAndPic.append('</span><br/>');-->

<#--                    BootstrapDialog.show({-->
<#--                        title: row.id + "/" + row.resourcePath + " 访问明细",-->
<#--                        message: $textAndPic,-->
<#--                        size: BootstrapDialog.SIZE_WIDE,-->
<#--                        draggable: true, // Default value is false，可拖拽-->
<#--                        closable : true, // Default value is false，点击对话框以外的页面内容可关闭-->
<#--                        buttons: [-->
<#--                            // {-->
<#--                            //     label: '标记',-->
<#--                            //     icon: 'glyphicon glyphicon-star',-->
<#--                            //     cssClass: 'btn-warning',-->
<#--                            //     action: function(dialogRef){-->
<#--                            //         // 进行行为标记-->
<#--                            //         dialogRef.close();-->
<#--                            //     }-->
<#--                            // },-->
<#--                            {-->
<#--                                label: '我知道了',-->
<#--                                icon: 'fa fa-camera-retro',-->
<#--                                cssClass: 'btn-primary',-->
<#--                                action: function(dialogRef){-->
<#--                                    dialogRef.close();-->
<#--                                }-->
<#--                            }-->
<#--                        ]-->
<#--                    });-->
<#--                },-->
<#--                responseHandler: function(res) {-->
<#--                    return {-->
<#--                        &lt;#&ndash;-->
<#--                        res： PageListResponse-->
<#--                        res.list: PageListResponse 中的 PageList<T> list-->
<#--                        &ndash;&gt;-->
<#--                        "total": res.list.paginator.items,//总页数-->
<#--                        "rows": res.list.list   //数据-->
<#--                    };-->
<#--                }-->
<#--            })-->
<#--        });-->

<#--        // 获取自定义查询条件-->
<#--        function queryParams() {-->
<#--            var param = {};-->
<#--            $('#qForm').find('[name]').each(function () {-->
<#--                var value = $(this).val();-->
<#--                if (value != '') {-->
<#--                    param[$(this).attr('name')] = value;-->
<#--                }-->
<#--            });-->

<#--            return param;-->
<#--        }-->
<#--    </script>-->

<#--    <script>-->
<#--        /* 超链接格式化 */-->
<#--        function operateLinkFormatter(value, row, url){-->
<#--            return '<a target="_blank" href="' + url + '">' + value + '</a>';-->
<#--        }-->
<#--        //连接字段格式化-->
<#--        function linkFormatter(value, row, index) {-->
<#--            return "<a href='" + value + "' title='单击打开连接' target='_blank'>" + value + "</a>";-->
<#--        }-->
<#--        //Email字段格式化-->
<#--        function emailFormatter(value, row, index) {-->
<#--            return "<a href='mailto:" + value + "' title='单击打开连接'>" + value + "</a>";-->
<#--        }-->
<#--        //性别字段格式化-->
<#--        function sexFormatter(value) {-->
<#--            if (value == "女") { color = 'Red'; }-->
<#--            else if (value == "男") { color = 'Green'; }-->
<#--            else { color = 'Yellow'; }-->

<#--            return '<div  style="color: ' + color + '">' + value + '</div>';-->
<#--        }-->

<#--        // 重置-->
<#--        function resetSearch() {-->
<#--            $('#qForm').find('[name]').each(function () {-->
<#--                $(this).val('');-->
<#--            });-->
<#--            $("#createDate").val($("#currentDayer").val());-->
<#--        }-->

<#--        /*-->
<#--        * 查询-->
<#--        */-->
<#--        function searchInfo() {-->
<#--            refresh();-->
<#--        }-->

<#--        /*-->
<#--        * 刷新-->
<#--        */-->
<#--        function refresh() {-->
<#--            //请输入查询日期-->
<#--            var dateTime= $("#createDate").val();-->
<#--            //alert(dateTime);-->
<#--            if(dateTime == null || dateTime == ''){-->
<#--                layer.msg('请输入查询日期', {icon: 5});-->
<#--                return;-->
<#--            }-->

<#--            //刷新Table，Bootstrap Table 会自动执行重新查询-->
<#--            $("#table").bootstrapTable('refresh');-->
<#--        }-->
<#--    </script>-->
</@layout>
