package com.mtons.mblog.modules.service.impl;

import com.mtons.mblog.service.util.BeanMapUtils;
import com.mtons.mblog.bo.PostBo;
import com.mtons.mblog.service.BaseService;
import com.mtons.mblog.service.aspect.PostStatusFilter;
import com.mtons.mblog.bo.UserBO;
import com.mtons.mblog.entity.bao.Post;
import com.mtons.mblog.modules.service.PostSearchService;
import com.mtons.mblog.service.atom.jpa.UserJpaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : langhsu
 * @version : 1.0
 * @date : 2019/1/18
 */
@Slf4j
@Service
@Transactional
public class PostSearchServiceImpl extends BaseService implements PostSearchService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserJpaService userJpaService;

    @Override
    @PostStatusFilter
    public Page<PostBo> search(Pageable pageable, String term) throws Exception {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder builder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Post.class).get();

        Query luceneQuery = builder
                .keyword()
                .fuzzy()
                .withEditDistanceUpTo(1)
                .withPrefixLength(1)
                .onFields("title", "summary", "tags")
                .matching(term).createQuery();

        FullTextQuery query = fullTextEntityManager.createFullTextQuery(luceneQuery, Post.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style='color:red;'>", "</span>");
        QueryScorer scorer = new QueryScorer(luceneQuery);
        Highlighter highlighter = new Highlighter(formatter, scorer);

        List<Post> list = query.getResultList();
        List<PostBo> rets = list.stream().map(po -> {
            //PostBO post = map(po, PostBO.class);
            PostBo post = BeanMapUtils.copy(po);

            try {
                // 处理高亮
                String title = highlighter.getBestFragment(analyzer, "title", post.getTitle());
                String summary = highlighter.getBestFragment(analyzer, "summary", post.getSummary());

                if (StringUtils.isNotEmpty(title)) {
                    post.setTitle(title);
                }
                if (StringUtils.isNotEmpty(summary)) {
                    post.setSummary(summary);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            return post;
        }).collect(Collectors.toList());

        buildUsers(rets);

        return new PageImpl<>(rets, pageable, query.getResultSize());
    }

    @Override
    public void resetIndexes() {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer(Post.class).start();
    }

    private void buildUsers(List<PostBo> list) {
        if (null == list) {
            return;
        }

        HashSet<Long> uids = new HashSet<>();
        list.forEach(n -> uids.add(n.getAuthorId()));
        Map<Long, UserBO> userMap = userJpaService.findMapByIds(uids);

        list.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
    }
}
