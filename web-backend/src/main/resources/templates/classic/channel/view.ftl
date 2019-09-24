<#include "/classic/inc/layout.ftl"/>

<#assign title = view.title + ' - ' + options['site_name'] />
<#assign keywords = view.keywords?default(options['site_keywords']) />
<#assign description = view.description?default(options['site_description']) />

<@layout title>
<div class="row main">
    <div class="col-xs-12 col-md-9 side-left topics-show">
        <#--
        导航条
        <ol class="breadcrumb">
            <li><a href="${base}/">首页</a></li>
            <li class="active">${view.title}</li>
        </ol>
        -->

        <!-- view show -->
        <div class="topic panel panel-default">
            <div class="infos panel-heading">
                <h1 class="panel-title topic-title">${view.title}</h1>
                <div class="meta inline-block">
                    <span>
                        <i class=" icon-list-2"></i>
                        <a class="author" href="${base}/users/${view.author.domainHack}">
                            ${view.author.name}
                        </a>
                    </span>

                    <#-- 标签等其他信息 -->
                    <span>
                        <i class="icon-list"></i>
                        <a href="https://www.waitsun.com/topics/company/omni-group">${view.tags}</a>
                    </span>
                </div>
                <div class="meta inline-block">
                    <span>
                        <i class="icon-clock"></i>
                        <abbr class="timeago">${timeAgo(view.created)}</abbr>
                    </span>
                    ⋅
                    <span>
                        <i class="icon-book-open"></i>
                        ${view.views} 阅读
                    </span>
                    ⋅
                    <#-- 评论数 -->
                    <span>
<#--                        <i class="icon-comment"></i>-->
                        <i class="icon-bubble"></i>
                        <a href="#chat">${view.comments}</a>
                    </span>
                </div>

                <div class="clearfix"></div>
            </div>

            <div class="content-body entry-content panel-body ">
                <div class="markdown-body">
                    ${view.content}
                </div>
            </div>
            <div class="panel-footer operate">
                <#list view.tagsArray as tag>
                    <span>
                        <a class="label label-default" href="${base}/tag/${tag}/">#${tag}</a>
                    </span>
                </#list>
            </div>
            <div class="panel-footer">
                <div class="hidden-xs">
                    <div class="social-share" data-sites="qq, weibo, wechat, qzone, facebook, twitter, google"></div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="more-box">
                <a class="btn btn-fulltext" data-toggle="fulltext">
                    <i class="icon icon-arrow-down" aria-hidden="true"></i> 阅读全部
                </a>
            </div>
        </div>

        <!-- Comments -->
        <@controls name="comment">
        <div id="chat" class="chats shadow-box">
            <div class="chat_header">
                <h4>
                    全部评论: <span id="chat_count">0</span> 条
                    <@controls name="commentAllowAnonymous">
                        <label class="small">允许匿名评论</label>
                    </@controls>
                </h4>
            </div>
            <ul id="chat_container" class="its"></ul>
            <div id="pager" class="text-center"></div>
            <div class="chat_post">
                <div class="cbox-title">我有话说: <span id="chat_reply" style="display:none;">@<i
                        id="chat_to"></i></span>
                </div>
                <div class="cbox-post">
                    <div class="cbox-input">
                        <textarea id="chat_text" rows="3" placeholder="请输入评论内容"></textarea>
                        <input type="hidden" value="0" name="chat_pid" id="chat_pid"/>
                    </div>
                    <div class="cbox-ats clearfix">
                        <div class="ats-func">
                            <div class="OwO" id="face-btn"></div>
                        </div>
                        <div class="ats-issue">
                            <button id="btn-chat" class="btn btn-success btn-sm bt">发送</button>
                        </div>
                    </div>
                </div>
                <div class="phiz-box" id="c-phiz" style="display:none">
                    <div class="phiz-list" view="c-phizs"></div>
                </div>
            </div>
        </div>
        </@controls>
        <!-- /view show -->
    </div>
    <div class="col-xs-12 col-md-3 side-right hidden-xs hidden-sm">
        <ul class="list-group about-user">
            <li class="list-group-item user-card" >
                <div class="user-avatar">
                    <@utils.showAva view.author "img-circle"/>
                </div>
                <div class="user-name">
                    <span>${view.author.name}</span>
                </div>
            </li>

            <li class="list-group-item">
                <div class="user-datas">
                    <ul>
                        <li><strong>${view.author.posts}</strong><span>发布</span></li>
                        <li class="noborder"><strong>${view.author.comments}</strong><span>评论</span></li>
                    </ul>
                </div>
            </li>
            <li class="list-group-item">
                <div class="text-center">
                    <#-- 是否区分登陆未登录 -->
                    <a class="btn btn-default btn-sm" disabled="true" readonly="true" href="javascript:void(0);" data-id="${view.articleBlogId}" rel="favor">
                        <i class="icon icon-star"></i> 收藏 <strong id="favors">${view.favors}</strong>
                    </a>
                </div>
            </li>
        </ul>
        <#include "/classic/inc/right.ftl"/>
    </div>
</div>

<#--
回复、删除均在此处。目前评论不允许删除。
0   data.author.domainHack
1   data.author.avatar
2   data.author.name
3   data.created
4   content
5   data.id, is pid
6   quoto
7   是否使用默认头像
 -->
<script type="text/plain" id="chat_template">
    <li id="chat{5}">
        <a class="avt fl" target="_blank" href="${base}/users/{0}">
            <img src="{1}">
        </a>
        <div class="chat_body">
            <h5>
                <div class="fl">
                    <a class="chat_name" href="${base}/users/{0}">{2}</a>
                    <span class="date">{3}</span>
                    <#-- 楼层
                    <span class="floor-num"> # {7}楼 </span>
                    -->
                </div>
                <div class="fr reply_this">
                    <a href="javascript:void(0);" onclick="goto('{5}', '{2}')">
                        <i class="icon icon-action-redo"></i>
                    </a>
                </div>
                <div class="clear"></div>
            </h5>
            <div class="chat_p">
                <div class="chat_pct">{4}</div> {6}
            </div>
        </div>
        <div class="clear"></div>
        <div class="chat_reply"></div>
    </li>
</script>

<#-- 默认头像的值取自  StorageConsts.AVATAR， 图片格式为 img-circle  -->
<script type="text/plain" id="guest_template">
    <li id="chat{5}">
        <a class="avt fl" disabled="true" readonly="true" href="javascript:void(0);">
            <img class="img-circle" src="{1}">
        </a>
        <div class="chat_body">
            <h5>
                <div class="fl">
                    <label class="small">匿名</label><a class="chat_name"  href="javascript:void(0);">{2}</a>
                    <span class="date">{3}</span>
                    <#-- 楼层
                    <span class="floor-num"> # {7}楼 </span>
                    -->
                </div>
                <div class="fr reply_this">
                    <a href="javascript:void(0);" onclick="goto('{5}', '{2}')">
                        <i class="icon icon-action-redo"></i>
                    </a>
                </div>
                <div class="clear"></div>
            </h5>
            <div class="chat_p">
                <div class="chat_pct">{4}</div> {6}
            </div>
        </div>
        <div class="clear"></div>
        <div class="chat_reply"></div>
    </li>
</script>

<script type="text/javascript">
    function goto(pid, user) {
        document.getElementById('chat_text').scrollIntoView();
        $('#chat_text').focus();
        $('#chat_text').val('');
        $('#chat_to').text(user);
        $('#chat_pid').val(pid);

        $('#chat_reply').show();
    }
    var container = $("#chat_container");
    var authoredTemplate = $('#chat_template')[0].text;
    var guestTemplate = $('#guest_template')[0].text;

    seajs.use(['comment', 'view'], function (comment) {
        comment.init({
            load: '${site.controls.comment}',
            // view.id 为 博文post.id
            load_url: '${base}/comment/list/${view.id}?articleBlogId=${view.articleBlogId}',
            post_url: '${base}/comment/submit',
            toId: '${view.id}',
            onLoad: function (i, data) {
                // 此处是 commentVo 对象
                var comment = data;

                // 留言内容  comment.clientAgent
                var content = comment.content;
                var quoto = '';
                if (comment.pid > 0 && !(comment.parent === null)) {
                    var pat = comment.parent;
                    var pcontent = pat.content;
                    
                    /* 回复的超链接和图标样式 */
                    if(pat.author.commitAuthoredType == 'AUTHORED'){
                        quoto = '<div class="quote"><a href="${base}/users/' + pat.author.domainHack + '">@' + pat.author.name + '</a>: ' + pcontent + '</div>';
                    }else{
                        quoto = '<div class="quote"><a href="javascript:void(0);"' + pat.author.domainHack + '>@' + pat.author.name + '</a>: ' + pcontent + '</div>';
                    }
                }

                var item;

                if(data.commitAuthoredType == 'AUTHORED'){
                    item = jQuery.format(authoredTemplate,
                            data.author.domainHack, // 0
                            data.author.avatar,     // 1
                            data.author.name,       // 2
                            data.created,           // 3
                            content,                // 4
                            data.id,                // 5, pid
                            quoto                  // 6
                    );
                }else{
                    item = jQuery.format(guestTemplate,
                            '',
                            '${siterProfile.userDefaultAvatar}',
                            data.author.name,
                            data.created,
                            content,
                            data.id,
                            quoto
                    );
                }

                return item;
            }
        });
    });

</script>
</@layout>
