package org.chenyu.task;

import org.chenyu.dao.ArticleDao;
import org.chenyu.entity.Article;
import org.chenyu.entity.Category;
import org.chenyu.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ArticleTask
 * @Description TODO
 * @Author chenyu
 * @Date 2019/2/20 14:59
 **/
@Component
public class ArticleTask {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 每天0点删除状态为0的文章
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteArticleByStatus() {
        long size = articleDao.deleteArticleByStatus();

        System.err.println("已将" + size + "篇文章删除");
    }

    //@Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "0 0 0 * * ?")
    public void refreshArticleInRedis() {
        redisTemplate.delete(ArticleService.articleList);
        /*redisTemplate.delete("firstPage");
        redisTemplate.delete("webPage");
        redisTemplate.delete("linuxPage");
        redisTemplate.delete("frontPage");
        redisTemplate.delete("noCategoryPage");*/
        List<Category> categories = articleDao.getCategories();
        List<Article> articles;

        for (Category category : categories) {
            String name = category.getName();
            Long id = category.getId();

            //首页
            if (id == 1L) {
                articles = articleDao.getFirst10Article();
            }
            // 非首页
            else {
                articles = articleDao.getArticlesByCategoryId(id);
            }

            redisTemplate.boundHashOps(ArticleService.articleList).put(name, articles);
        }
        System.out.println("已同步文章到缓存");
    }
}
