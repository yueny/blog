<#macro posts_item row escape=true>
<li class="content">
    <#-- 有缩略图 -->
    <#if row.thumbnail?? && row.thumbnail?length gt 0>
        <div class="content-box">
            <div class="posts-item-img">
                <a href="${base}/article/${row.articleBlogId}.html" title="">
                    <div class="overlay"></div>
                    <img class="lazy thumbnail" src="<@resource src=row.thumbnail/>" style="display: inline-block;">
                </a>
            </div>
            <div class="posts-item posts-item-gallery">
                <h2><a href="${base}/article/${row.articleBlogId}.html">
                        <#if escape>${row.title?html}<#else>${row.title}</#if>
                    </a>
                </h2>
                <div class="item-text">${row.summary}</div>
                <div class="item-info pull-left">
                    <ul>
                        <li class="post-author hidden-xs">
                            <div class="avatar">
                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                            </div>
                            <#-- 创建者链接 -->
                            <a href="${base}/users/${row.author.domainHack}" target="_blank">${row.author.name}</a>
                        </li>
                        <li class="ico-cat"><@utils.showChannel row/></li>
                        <li class="ico-time" title="${row.created}"><i class="icon-clock"></i>${timeAgo(row.created)}</li>
                        <li class="ico-eye hidden-xs"><i class="icon-book-open"></i>${row.views}</li>
                        <li class="ico-like hidden-xs"><i class="icon-bubble"></i>${row.comments}</li>
                    </ul>
                </div>

                <span class="pull-right">
                    <a class="read-more" href="${base}/article/${row.articleBlogId}.html"
                       title="阅读全文">阅读全文 <i class="fa fa-chevron-circle-right"></i>
                    </a>
                </span>
            </div>
        </div>
    <#else>
        <#-- 无缩略图 -->
        <div class="content-box posts-aside">
            <div class="posts-item">
                <div class="item-title">
                    <h2><a href="${base}/article/${row.articleBlogId}.html"><#if escape>${row.title?html}<#else>${row.title}</#if></a></h2>
                </div>
                <div class="item-text">${row.summary}</div>
                <div class="item-info pull-left">
                    <ul>
                        <li class="post-author hidden-xs">
                            <div class="avatar">
                                <img src="<@resource src=row.author.avatar + '?t=' + .now?time/>" class="lazy avatar avatar-50 photo" height="50" width="50">
                            </div>
                            <a href="${base}/users/${row.author.domainHack}" target="_blank">${row.author.name}</a>
                        </li>
                        <li class="ico-cat"><@utils.showChannel row/></li>
                        <li class="ico-time" title="${row.created}"><i class="icon-clock"></i>${timeAgo(row.created)}</li>
                        <li class="ico-eye hidden-xs"><i class="icon-book-open"></i>${row.views}</li>
                        <li class="ico-like hidden-xs"><i class="icon-bubble"></i>${row.comments}</li>
                    </ul>
                </div>

                <span class="pull-right">
                    <a class="read-more" href="${base}/article/${row.articleBlogId}.html"
                       title="阅读全文">阅读全文 <i class="fa fa-chevron-circle-right"></i>
                    </a>
                </span>
            </div>
        </div>
    </#if>
</li>
</#macro>