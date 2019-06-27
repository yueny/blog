/**
 *
 */
package com.mtons.mblog.modules.template.directive;

import com.google.common.collect.Sets;
import com.mtons.mblog.base.consts.Consts;
import com.mtons.mblog.base.utils.BeanMapUtils;
import com.mtons.mblog.bo.ChannelVO;
import com.mtons.mblog.bo.PostBO;
import com.mtons.mblog.service.atom.ChannelService;
import com.mtons.mblog.modules.service.PostService;
import com.mtons.mblog.modules.template.DirectiveHandler;
import com.mtons.mblog.modules.template.TemplateDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 文章内容查询
 * <p>
 * 示例：
 * 请求：http://mtons.com/index?order=newest&pageNo=2
 * 使用：@contents channelId=channelId channelCode=channel.channelCode pageNo=pageNo order=order
 * </p>
 *
 * channelId
 * pageNo 页数
 * order 排序
 * size 煤业数量
 *
 * @author langhsu
 */
@Component
public class ContentsDirective extends TemplateDirective {
    @Autowired
    private PostService postService;
    @Autowired
    private ChannelService channelService;

    @Override
    public String getName() {
        return "contents";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer channelId = handler.getInteger("channelId", 0);
        String order = handler.getString("order", Consts.order.NEWEST);
        String channelCode = handler.getString("channelCode", "-1");

        List<ChannelVO> children = channelService.findAll(0, channelCode);

        Set<Integer> channelIds = Sets.newHashSet(channelId);
        children.stream()
                .forEach(item -> {
                    channelIds.add(item.getId());
                });

        Set<Integer> excludeChannelIds = new HashSet<>();

        if (channelId <= 0) {
            List<ChannelVO> channels = channelService.findRootAll(Consts.STATUS_CLOSED);
            if (channels != null) {
                channels.forEach((c) -> excludeChannelIds.add(c.getId()));
            }
        }

        Pageable pageable = wrapPageable(handler, Sort.by(Sort.Direction.DESC, BeanMapUtils.postOrder(order)));
        Page<PostBO> result = postService.paging(pageable, channelIds, excludeChannelIds);
        handler.put(RESULTS, result).render();
    }
}
