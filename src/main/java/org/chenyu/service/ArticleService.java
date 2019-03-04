package org.chenyu.service;

import org.chenyu.dao.ArticleDao;
import org.chenyu.entity.Article;
import org.chenyu.entity.Category;
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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Article getArticleById(Long id) {
        Article article = articleDao.getArticleById(id);
        article.setCategory(articleDao.getCategoryById(article.getCategoryId()).getDisplayName());
        return article;
    }

    public List<Article> getFirst10Article() {
        return articleDao.getFirst10Article();
    }

    public List<Category> getCategories() {
        return articleDao.getCategories();
    }

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
        articleDao.writeBlog(article);
    }

    public void deleteArticleById(Long id) {
        articleDao.deleteArticleById(id);
    }

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
        articleDao.updateArticleById(article);
    }

    public List<Article> getArticlesByCategoryName(String name) {
        Long categoryId = articleDao.getCategoryIdByName(name);
        List<Article> articles = articleDao.getArticlesByCategoryId(categoryId);
        return articles;
    }

    public List<Article> getAllArticle() {
        return articleDao.getAllArticle();
    }

    public List<Article> getArticleByText(String searchText) {
        return articleDao.getArticleByText(searchText);
    }
}
