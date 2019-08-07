/**
 *
 */
package com.mtons.mblog.modules.template.directive;

import com.mtons.mblog.bo.PostBO;
import com.mtons.mblog.service.atom.jpa.PostService;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import com.mtons.mblog.service.manager.PostManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * 根据作者取文章列表
 *
 * @author langhsu
 *
 */
@Component
public class UserContentsDirective extends TemplateDirective {
    @Autowired
    private PostManagerService postManagerService;
    @Autowired
    private PostService postService;

	@Override
	public String getName() {
		return "user_contents";
	}

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        long userId = handler.getInteger("userId", 0);
        Pageable pageable = wrapPageable(handler);

        Page<PostBO> result = postService.findAllByAuthorId(pageable, userId);
        handler.put(RESULTS, result).render();
    }

}
