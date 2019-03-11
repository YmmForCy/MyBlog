package org.chenyu.dao;


import org.apache.ibatis.annotations.Param;
import org.chenyu.entity.Article;
import org.chenyu.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by chenyu on 17-3-10.
 */
@Repository
public interface ArticleDao {
    public Article getArticleById(@Param("id") Long id);

    //首页查询，只查询10篇文章
    public List<Article> getFirst10Article();

    //根据分类查询所有
    public List<Article> getArticlesByCategoryId(Long categoryId);

    public List<Category> getCategories();

    public void writeBlog(Article article);

    public Long getCategoryIdByName(String name);

    public void deleteArticleById(Long id);

    public void updateArticleById(Article article);

    public Category getCategoryById(Long id);

    List<Article> getAllArticle();

    List<Article> getArticleByText(String searchText);

    long deleteArticleByStatus();
}
