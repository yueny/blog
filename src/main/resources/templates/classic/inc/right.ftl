<#-- 每日箴言 -->
<div class="panel panel-default widget">
    <div class="panel-heading">
        <h4 class="panel-info">
            <i class="fa fa-at"></i>每日箴言<i class="fa fa-at"></i>
        </h4>
    </div>
    <div class="panel-body">
        <a disabled>优于别人，并不高贵，真正的高贵应该是优于过去的自己</a>
    </div>

	<#-- 时钟 -->
    <div>
    	<#include "/classic/inc/clock.ftl"/>
    </div>
</div>

<div class="panel panel-default widget">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-area-chart"></i> 热门文章
			<#--
			<span class="badge">21</span>
			-->
		</h3>
	</div>
	<div class="panel-body">
		<@sidebar method="hottest_posts">
		<ul class="list">
			<#list results as row>
            <li>${row_index + 1}. <a href="${base}/article/${row.articleBlogId}.html">${row.title}</a></li>
			</#list>
		</ul>
		</@sidebar>
	</div>
</div>

<div class="panel panel-default widget">
	<div class="panel-heading">
		<h3 class="panel-title"><i class="fa fa-bars"></i> 最新发布</h3>
	</div>
	<div class="panel-body">
		<@sidebar method="latest_posts">
			<ul class="list">
				<#list results as row>
					<li>${row_index + 1}. <a href="${base}/article/${row.articleBlogId}.html">${row.title}</a></li>
				</#list>
			</ul>
		</@sidebar>
	</div>
</div>
<@controls name="comment">
<div class="panel panel-default widget">
    <div class="panel-heading">
        <h3 class="panel-title"><i class="fa fa-comment-o"></i> 最新评论</h3>
    </div>
    <div class="panel-body">
		<@sidebar method="latest_comments">
			<ul class="list">
				<#list results as row>
					<li><a href="${base}/article/${row.articleBlogId}.html">${row.content}</a></li>
				</#list>
			</ul>
		</@sidebar>
    </div>
</div>
</@controls>