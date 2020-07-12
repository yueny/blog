<footer class="footer">
    <div class="container">
        <div class="footer-row">
            <nav class="footer-nav">
                <a class="footer-nav-item footer-nav-logo" href="${base}/">
                    <img src="<@resource src=options['site_logo']/>" alt="mblog"/>
                </a>
                <span class="footer-nav-item">${options['site_copyright']}</span>
                <span class="footer-nav-item">${options['site_icp']}</span>
            </nav>
            <div class="gh-foot-min-back hidden-xs hidden-sm">
                <#-- 请保留此处标识-->
                <span class="footer-nav-item">Powered by
                    <#--
                    <a href="https://github.com/langhsu/mblog" target="_blank">mblog</a>
                    -->
                    mblog
                </span>

                <#--  控制台  href="${base}/admin" target="_blank" -->
                <a>
                    <i class="glyphicon glyphicon-font" title="控制台"></i>
                </a>
                <i class="tooltip-icon-version glyphicon glyphicon-gbp"></i>
            </div>
        </div>
    </div>
</footer>

<a href="#" class="site-scroll-top">
    <i class="icon-arrow-up"></i>
</a>

<script type="text/javascript">
    seajs.use('main', function (main) {
        main.init();
    });
</script>

<#-- 版本号展示的控制 -->
<script>
    $(function(){
        var tips;
        $('i.tooltip-icon-version').on({
            mouseenter:function(){
                var that = this;
                tips =layer.tips(
                        "<span style='color:#000;'>${siterProfile.version}</span>",
                        that,
                        {tips:[2,'#fff'],time:0,area: 'auto',maxWidth:500}
                );
            },
            mouseleave:function(){
                layer.close(tips);
            }
        });
    });
</script>


<#-- 流量统计 -->
<#include "/utils/traffic_statistics.ftl"/>