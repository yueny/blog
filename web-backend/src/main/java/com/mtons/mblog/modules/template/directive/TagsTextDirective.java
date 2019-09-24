///**
// *
// */
//package com.mtons.mblog.modules.template.directive;
//
//import com.mtons.mblog.bo.TagBO;
//import com.mtons.mblog.modules.template.DirectiveHandler;
//import com.mtons.mblog.modules.template.TemplateDirective;
//import com.mtons.mblog.service.atom.bao.TagService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Component;
//
///**
// * 标签文本框加载
// *
// * <p>
// * 示例：
// * 请求：http://mtons.com/index?order=newest&pageNo=2
// * 使用：@textTag channelId=channelId channelCode=channel.channelCode pageNo=pageNo order=order
// * </p>
// *
// * @author yueny09 <deep_blue_yang@163.com>
// *
// * @DATE 2019/7/17 下午11:11
// *
// */
//@Component
//public class TagsTextDirective extends TemplateDirective {
//    @Autowired
//    private TagService tagService;
//
//    @Override
//    public String getName() {
//        return "textTag";
//    }
//
//    @Override
//    public void execute(DirectiveHandler handler) throws Exception {
////        Sort sort = Sort.by(
////                // 按创建时间降序
////                new Sort.Order(Sort.Direction.DESC, "created"),
////                // posts 按使用数量降序
////                new Sort.Order(Sort.Direction.DESC, "posts")
////        );
//        Sort sort = Sort.by(Sort.Direction.DESC, "created", "posts");
//
//        Page<TagBO> tagList = tagService.pagingQueryTags(PageRequest.of(0, 10, sort));
//        String[] tags = new String[tagList.getNumberOfElements()];
//        for (int i = 0; i<tagList.getNumberOfElements(); i++){
//            TagBO tagBo = tagList.getContent().get(i);
//
//            tags[i] = tagBo.getName();
//        }
//
//        handler.put(RESULTS, tags).render();
//    }
//}
