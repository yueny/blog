<#-- style -->
<#macro style>
    <!DOCTYPE html>
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title> - ${options['site_name']}</title>

        <#-- 0、Base -->
        <link rel="apple-touch-icon-precomposed" href="https://static.codealy.com/favicon.ico"/>
        <link rel="shortcut icon" href="https://static.codealy.com/favicon.ico"/>

        <#-- Font Awesome -->
        <link href="${base}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">

        <#-- 1、jQuery -->
        <#-- 2、Bootstrap -->
        <link href="${base}/dist/vendors/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <link href="${base}/dist/vendors/bootstrap/3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">

        <link href="${base}/dist/vendors/bootstrap3-dialog/dist/css/bootstrap-dialog.min.css" type="text/css" rel="stylesheet">

        <link href="${base}/dist/vendors/bootstrap-datepicker/dist/css/bootstrap-datepicker3.css" type="text/css" rel="stylesheet">

        <link rel="stylesheet" href="${base}/dist/vendors/bootstrap-select/1.13.7/css/bootstrap-select.css">
<#--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css">-->

        <link rel="stylesheet" href="${base}/dist/vendors/bootstrap-switch/css/bootstrap3/bootstrap-switch.css">

        <#-- 3、bootstrap-table -->
        <#--    <link href="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.css" type="text/css" rel="stylesheet">-->
        <link href="https://unpkg.com/bootstrap-table@1.15.4/dist/bootstrap-table.min.css" rel="stylesheet">
        <link href="${base}/dist/vendors/jquery-treegrid/0.3.0/css/jquery.treegrid.css" rel="stylesheet">

        <#-- 4、etc -->
        <link rel="stylesheet" href="${base}/dist/css/checkbox.css">
        <link rel="stylesheet" href="${base}/dist/js/comp/number-spinner/number-spinner.css">

        <link href="${base}/dist/vendors/layer/skin/layer.css" rel="stylesheet">
        <link rel="stylesheet" href="${base}/dist/vendors/alertify.js/themes/alertify.core.css" />
        <link rel="stylesheet" href="${base}/dist/vendors/alertify.js/themes/alertify.default.css" id="toggleCSS" />
        <link href="${base}/dist/vendors/b.dialog/b.dialog.bootstrap3.css" rel="stylesheet">

        <#-- Theme Style -->
        <link href="${base}/theme/admin/dist/css/site.css" rel="stylesheet">
        <link href="${base}/theme/admin/dist/css/site.addons.css" rel="stylesheet">
        <link href="${base}/theme/admin/dist/css/skins/skin-blue.css" rel="stylesheet">

        <#-- 0、Base -->
        <script type="text/javascript">
            var _MTONS = _MTONS || {};
            _MTONS.BASE_PATH = '${base}';
            _MTONS.LOGIN_TOKEN = '${profile.id}';

            <#--  未登录是否允许评论 BaseInterceptor    -->
            _MTONS.ALLOW_COMMENT_WITHOUT_LOGIN = '${siterProfile.commentAllowAnonymous}';
        </script>

        <#-- 1、jQuery -->
        <script src="${base}/dist/vendors/jquery/v2.1.4/jquery-2.1.4.min.js"></script>
        <script src="${ctx}/dist/vendors/jquery/jquery-ui.min.js" type="text/javascript"></script>

        <#-- 2、Bootstrap -->
        <script src="${base}/dist/vendors/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script src="${base}/dist/vendors/bootstrap-switch/js/bootstrap-switch.js"></script>

        <script type="text/javascript" src="${base}/dist/vendors/bootstrap3-dialog/dist/js/bootstrap-dialog.min.js"></script>

        <script src="${base}/dist/vendors/bootstrap-select/1.13.7/js/bootstrap-select.js"></script>
        <script src="${base}/dist/vendors/bootstrap-select/1.13.7/js/i18n/defaults-zh_CN.js"></script>
        <#--        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>-->
        <#--        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/i18n/defaults-zh_CN.js"></script>-->

        <script type="text/javascript" src="${base}/dist/vendors/bootstrap-datepicker/dist/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${base}/dist/vendors/bootstrap-datepicker/dist/locales/bootstrap-datepicker.zh-CN.min.js"></script>

        <#-- 3、bootstrap-table -->
        <script src="https://unpkg.com/bootstrap-table@1.15.4/dist/bootstrap-table.min.js"></script>
        <#--    <script type="text/javascript" src="https://cdn.bootcss.com/bootstrap-table/1.11.1/bootstrap-table.min.js"></script>-->
        <#--    <script type="text/javascript" src="https://cdn.bootcss.com/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.min.js"></script>-->
        <script src="${base}/dist/vendors/jquery-treegrid/0.3.0/js/jquery.treegrid.js"></script>
        <script src="https://unpkg.com/bootstrap-table@1.15.4/dist/extensions/treegrid/bootstrap-table-treegrid.min.js"></script>
        <script src="${base}/dist/js/comp/bootstrap-table/formatter-tools.js"></script>

        <#-- 4、etc -->
        <script src='${base}/dist/vendors/bootstrap-hover/twitter-bootstrap-hover-dropdown.js'></script>

        <script src="${base}/dist/js/comp/number-spinner/number-spinner.js"></script>
        <script src="${base}/dist/vendors/qiaoJs/qiao.js"></script>
        <script src="${base}/dist/vendors/qiaoJs/qiao.config.js"></script>

        <script src="${base}/dist/js/permission.js"></script>
        <script src="${base}/dist/js/plugins.js"></script>
        <script src="${base}/dist/js/crypto/js-base64.js"></script>
        <script src="${base}/dist/js/crypto/js-cryptoJS.js"></script>
        <script src="${base}/dist/js/public.js"></script>
        <script src="${base}/dist/js/tools.js"></script>
        <script src="${base}/dist/js/comp/bootstrap-dialog/bootstrap-dialog-tools.js"></script>

        <script src='${base}/dist/vendors/jquery-validation/jquery.validate.min.js'></script>
        <script src='${base}/dist/vendors/jquery-validation/additional-methods.js'></script>
        <script src='${base}/dist/vendors/jquery-validation/localization/messages_zh.min.js'></script>

        <script src="${base}/dist/vendors/layer/layer.js"></script>
        <script src="${base}/dist/vendors/alertify.js/alertify.js"></script>
        <script src="${base}/dist/vendors/b.dialog/b.dialog.min.js"></script>

        <script src="${base}/theme/admin/dist/js/site.js"></script>
        <script src="${base}/theme/admin/dist/js/site.base.js"></script>

        <script src="${base}/dist/js/sea.js"></script>
        <script src="${base}/dist/js/sea.config.js"></script>
    </head>

    <body class="hold-transition skin-blue sidebar-mini">

        <#nested/>


        <#-- 脚本区.
         <select class="selectpicker... 组件初始化
         -->
        <script type="text/javascript">
            $(function () {
                $('.selectpicker').selectpicker({
                    noneSelectedText : '请选择'
                });
                //$('.selectpicker').selectpicker();
                //$('select').selectpicker();

                // 刷新
                // $('.selectpicker').selectpicker('refresh');

                $(".switch").bootstrapSwitch();
            });
        </script>

        <#-- 流量统计 -->
        <#include "/utils/traffic_statistics.ftl"/>
    </body>
    </html>
</#macro>

<#-- layout -->
<#macro layout>
    <@style>
        <div class="wrapper">
            <!-- Main Header -->
            <header class="main-header">
                <a href="${base}/index" class="logo">
                    <span class="logo-mini">muzinuo</span>
                    <span class="logo-lg"><b>C</b>odealy</span>
                </a>
                <nav class="navbar navbar-static-top">
                    <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                        <span class="sr-only">Toggle navigation</span>
                    </a>
                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">
                            <li><a href="/" title="跳转到前台" target="_blank"><i class="fa fa-television"></i></a></li>
                            <li class="messages-menu">
                                <a href="${base}/users/${profile.domainHack}/messages">
                                    <i class="fa fa-envelope-o"></i>
                                    <#if (profile.badgesCount.messages > 0)>
                                        <span class="label label-success">${profile.badgesCount.messages}</span>
                                    </#if>
                                </a>
                            </li>
                            <li class="dropdown user user-menu">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <img src="<@resource src=profile.avatar/>" class="user-image" alt="User Image">
                                    <span class="hidden-xs">${profile.username}</span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li class="user-header">
                                        <img src="<@resource src=profile.avatar/>" class="img-circle" alt="User Image">
                                        <p>${profile.username}</p>
                                    </li>
                                    <li class="user-footer">
                                        <div class="pull-left">
                                            <a href="${base}/settings/profile" class="btn btn-default btn-flat">个人资料</a>
                                        </div>
                                        <div class="pull-right">
                                            <a href="${base}/logout" class="btn btn-default btn-flat">退出登录</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>

            <!-- Left side column -->
            <aside class="main-sidebar">
                <section class="sidebar">
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="<@resource src=profile.avatar/>" class="img-circle" alt="User Image">
                        </div>
                        <div class="pull-left info">
                            <p>${profile.username}</p>
                            <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
                        </div>
                    </div>

                    <!-- Sidebar Menu -->
                    <ul class="sidebar-menu" data-widget="tree">
                        <li class="header">菜单</li>

<#--                        <li>-->
<#--                            <a href="${base}/admin" class="active">-->
<#--                                <i class="fa fa-dashboard"></i>-->
<#--                                <span>仪表盘</span>-->
<#--                            </a>-->
<#--                        </li>-->

                        <#-- 数据来自于菜单宏， @getName() -->
                        <@menus>
                            <#list results as menu>
                                <#-- 含子菜单: 不为空且长度不为0 -->
                                <#if menu.children?? && (menu.children)?size>
                                    <li class="dropdown">
                                        <a href="${menu.url}" data-toggle="dropdown"
                                           aria-haspopup="true" aria-expanded="false"
                                                <#if menu_index == 0>
                                                    class="active"
                                                </#if>>
                                            <i class="fa fa-fw fa-plus"> </i>
                                            ${menu.name}
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" role="menu">
                                            <#list menu.children as children>
                                            <#--                                    <li class="disabled">-->
                                                <li>
                                                    <a href="${base}/${children.url}">
                                                        <i class="${children.icon}"></i>
                                                        <span>${children.name}</span>
                                                    </a>
                                                </li>
                                            </#list>
                                        </ul>
                                    </li>
                                <#else>
                                    <#-- 不含子菜单，一级菜单 -->
                                    <li>
                                        <a href="${base}/${menu.url}"
                                            <#if menu_index == 0>
                                                class="active"
                                            </#if>>
                                            <i class="${menu.icon}"></i>
                                            <span>${menu.name}</span>
                                        </a>
                                    </li>
                                </#if>

                            </#list>
                        </@menus>
                    </ul>
                </section>
            </aside>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <#nested/>
            </div>

            <!-- Main Footer -->
            <footer class="main-footer">
                <!-- To the right -->
                <div class="pull-right hidden-xs">${site.version}</div>
                <!-- Default to the left -->
                <strong>Copyright &copy; 2019 <a href="#">codealy</a>.</strong> All rights reserved.
                <a onclick="reciprocate()" class="text-primary"><i class="icon-qrcode"></i>捐赠</a>
            </footer>

        </div>
    </@style>
</#macro>

<#-- 构造分页插件 -->
<#macro pager url p spans>
    <#local span = (spans - 3)/2 />
    <#local pageNo = p.number + 1 />

    <#-- 构造查询条件 -->
    <#if (url?index_of("?") != -1)>
        <#local cURL = (url + "&pageNo=") />
    <#else>
        <#local cURL = (url + "?pageNo=") />
    </#if>


    总计: ${p.totalElements} 条

    <ul class="pagination no-margin pull-right">
        <#if (pageNo > 1)>
            <#local prev = pageNo - 1 />
            <li><a class="prev" href="${cURL}${prev}" pageNo="1">&nbsp;<i class="fa fa-angle-left"></i>&nbsp;</a></li>
        </#if>

        <#local totalNo = span * 2 + 3 />
        <#local totalNo1 = totalNo - 1 />
        <#if (p.totalPages > totalNo)>
            <#if (pageNo <= span + 2)>
                <#list 1..totalNo1 as i>
                    <@pagelink pageNo, i, cURL/>
                </#list>
                <@pagelink 0, 0, "#"/>
                <@pagelink pageNo, p.totalPages, cURL />
            <#elseif (pageNo > (p.totalPages - (span + 2)))>
                <@pagelink pageNo, 1, cURL />
                <@pagelink 0, 0, "#"/>
                <#local num = p.totalPages - totalNo + 2 />
                <#list num..p.totalPages as i>
                    <@pagelink pageNo, i, cURL/>
                </#list>
            <#else>
                <@pagelink pageNo, 1, cURL />
                <@pagelink 0 0 "#" />

                <#local num = pageNo - span />
                <#local num2 = pageNo + span />
                <#list num..num2 as i>
                    <@pagelink pageNo, i, cURL />
                </#list>
                <@pagelink 0, 0, "#"/>
                <@pagelink pageNo, p.totalPages, cURL />
            </#if>
        <#elseif (p.totalPages > 1)>
            <#list 1..p.totalPages as i>
                <@pagelink pageNo, i, cURL />
            </#list>
        <#else>
            <@pagelink 1, 1, cURL/>
        </#if>

        <#if (pageNo lt p.totalPages)>
            <#local next = pageNo + 1/>
            <li><a href="${cURL}${next}" pageNo="${next}">&nbsp;<i class="fa fa-angle-right"></i>&nbsp;</a></li>
        </#if>
    </ul>
</#macro>

<#macro pagelink pageNo idx url>
    <#if (idx == 0)>
    <li><span>...</span></li>
    <#elseif (pageNo == idx)>
    <li class="active"><a href="javascript:void(0);"><span>${idx}</span></a></li>
    <#else>
    <li><a href="${url}${idx}">${idx}</a></li>
    </#if>
</#macro>
