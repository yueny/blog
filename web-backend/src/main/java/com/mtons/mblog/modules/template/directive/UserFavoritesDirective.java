/**
 *
 */
package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.bo.FavoriteVO;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.service.atom.bao.UserService;
import com.mtons.mblog.service.atom.jpa.FavoriteService;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 根据作者取收藏列表
 *
 * @author landy
 * @since 3.0
 */
@Component
public class UserFavoritesDirective extends TemplateDirective {
    @Autowired
	private FavoriteService favoriteService;
    @Autowired
    private UserService userService;


	@Override
	public String getName() {
		return "user_favorites";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        long userId = handler.getInteger("userId", 0);
        Pageable pageable = wrapPageable(handler);

        UserBO userBO = userService.get(userId);
        Page<FavoriteVO> result = favoriteService.pagingByUserId(pageable, userBO.getUid());
        handler.put(RESULTS, result).render();
    }

}
