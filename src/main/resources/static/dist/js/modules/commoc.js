/*
 +--------------------------------------------------------------------------
 |   Mblog [#RELEASE_VERSION#]
 |   ========================================
 |   Copyright (c) 2014, 2015 mtons. All Rights Reserved
 |   http://www.mtons.com
 |
 +---------------------------------------------------------------------------
 */

define(function(require, exports, module) {
    J = jQuery;

    var Commoc = {
        // 是否允许匿名评论(未登录是否允许评论)
        isCommentWithoutLogin : function () {
            return (typeof(_MTONS.ALLOW_COMMENT_WITHOUT_LOGIN) !== 'undefined' && _MTONS.ALLOW_COMMENT_WITHOUT_LOGIN == 'true');
        }
    };

    module.exports = Commoc;
});