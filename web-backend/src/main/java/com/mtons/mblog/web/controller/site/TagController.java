/**
 *
 */
package com.mtons.mblog.web.controller.site;

import com.mtons.mblog.bo.PostTagVO;
import com.mtons.mblog.bo.TagBO;
import com.mtons.mblog.service.atom.jpa.TagService;
import com.mtons.mblog.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 标签
 * @author langhsu
 *
 */
@Controller
public class TagController extends BaseController {
    @Autowired
    private TagService tagService;

    @RequestMapping("/tags")
    public String index(ModelMap model) {
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "updated"));
        Page<TagBO> page = tagService.pagingQueryTags(pageable);
        model.put("results", page);
        return view(Views.TAG_INDEX);
    }

    @RequestMapping("/tag/{name}")
    public String tag(@PathVariable String name, ModelMap model) {
        Pageable pageable = wrapPageable(Sort.by(Sort.Direction.DESC, "weight"));
        Page<PostTagVO> page = tagService.pagingQueryPosts(pageable, name);
        model.put("results", page);

        model.put("name", name);
        return view(Views.TAG_VIEW);
    }

    /**
     * 获取标签列表
     *
     * @param q 正在输入的标签名， like q%
     */
    @RequestMapping("/query/tags")
    @ResponseBody
    public String[] tags(String q) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created", "posts");

        List<TagBO> tagList = tagService.findPagingTagsByNameLike(
                PageRequest.of(0, 10, sort), q);

        String[] tags = new String[tagList.size()];
        for (int i = 0; i<tagList.size(); i++){
            TagBO tagBo = tagList.get(i);

            tags[i] = tagBo.getName();
        }

        return tags;
    }
}
