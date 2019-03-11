package org.chenyu.service;

import org.chenyu.dao.ArticleDao;
import org.chenyu.entity.Article;
import org.chenyu.entity.Category;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by chenyu on 17-3-10.
 */
@Service
public class ArticleService {
    @Resource
    private ArticleDao articleDao;

    @Resource
    private RedisTemplate redisTemplate;
    public static final String articleList = "ARTICLE_LIST";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Article getArticleById(Long id) {
        Article article = articleDao.getArticleById(id);
        article.setCategory(articleDao.getCategoryById(article.getCategoryId()).getDisplayName());
        return article;
    }

    //首页查询最新10篇文章
    public List<Article> getFirst10Article() {
        List<Article> articles = (List<Article>) redisTemplate.boundHashOps(articleList).get("firstPage");
        if (articles != null) {
            return articles;
        }

        articles = articleDao.getFirst10Article();
        redisTemplate.boundHashOps(articleList).put("firstPage", articles);
        return articles;
    }

    //根据分类查询所有文章
    public List<Article> getArticlesByCategoryName(String name) {
        List<Article> articles = (List<Article>) redisTemplate.boundHashOps(articleList).get(name);
        if (articles != null) {
            return articles;
        }

        Long categoryId = articleDao.getCategoryIdByName(name);
        articles = articleDao.getArticlesByCategoryId(categoryId);
        redisTemplate.boundHashOps(articleList).put(name, articles);
        return articles;
    }

    public List<Category> getCategories() {
        return articleDao.getCategories();
    }

    //写播客
    public void writeBlog(Article article) {
        Long categoryId = articleDao.getCategoryIdByName(article.getCategory());
        if (categoryId == null) {
            categoryId = 100L;
        }
        article.setCategoryId(categoryId);
        article.setDate(sdf.format(new Date()));
        article.setStatus("1");
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            if (article.getContent().length() > 20) {
                article.setSummary(article.getContent().substring(0, 20));
            } else {
                article.setSummary(article.getContent().substring(0, article.getContent().length()));
            }
        }

        //根据categoryId查询得到categoryName
        String name = articleDao.getCategoryById(categoryId).getName();
        //删除以同步缓存，首页缓存也要同步
        redisTemplate.boundHashOps(articleList).delete(name);
        redisTemplate.boundHashOps(articleList).delete("firstPage");
        articleDao.writeBlog(article);
    }

    //删除播客
    public void deleteArticleById(Long id) {
        //查询删除播客的categoryId
        long categoryId = articleDao.getArticleById(id).getCategoryId();
        //根据categoryId查询得到categoryName
        String name = articleDao.getCategoryById(categoryId).getName();

        //删除指定分类categoryName缓存
        redisTemplate.boundHashOps(articleList).delete(name);
        redisTemplate.boundHashOps(articleList).delete("firstPage");
        articleDao.deleteArticleById(id);
    }

    //修改播客
    public void updateBlog(Article article) {
        Long categoryId = articleDao.getCategoryIdByName(article.getCategory());
        if (categoryId == null) {
            //没有修改分类
            categoryId = articleDao.getArticleById(article.getId()).getCategoryId();
        }
        article.setCategoryId(categoryId);
        //article.setDate(sdf.format(new Date())); 日期不更新
        //article.setStatus("1"); 状态不更新
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            if (article.getContent().length() > 20) {
                article.setSummary(article.getContent().substring(0, 20));
            } else {
                article.setSummary(article.getContent().substring(0, article.getContent().length()));
            }
        }

        //根据categoryId查询得到categoryName
        String name = articleDao.getCategoryById(categoryId).getName();
        redisTemplate.boundHashOps(articleList).delete(name);
        redisTemplate.boundHashOps(articleList).delete("firstPage");
        articleDao.updateArticleById(article);
    }

    public List<Article> getAllArticle() {
        return articleDao.getAllArticle();
    }

    public List<Article> getArticleByText(String searchText) {
        return articleDao.getArticleByText(searchText);
    }
}
