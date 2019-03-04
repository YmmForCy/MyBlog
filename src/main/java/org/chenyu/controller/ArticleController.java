package org.chenyu.controller;

import org.chenyu.entity.Article;
import org.chenyu.entity.Category;
import org.chenyu.entity.User;
import org.chenyu.service.ArticleService;
import org.chenyu.service.UserService;
import org.pegdown.PegDownProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by chenyu on 17-3-8.
 */
@Controller
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private UserService userService;

    //访问首页：localhost:8080，跳转到views/index.jsp
    @RequestMapping("/")
    public String index(Model model) {
        List<Article> articles = articleService.getFirst10Article();
        for (Article article : articles) {
            System.out.println("controller index, category：" + article.getCategory() + "\n");
        }
        model.addAttribute("articles", articles);
        return "views/index";
    }

    @RequestMapping("/column/{displayName}/{category}")
    public String column(@PathVariable("category") String category,Model model,@PathVariable("displayName") String displayName) {
        model.addAttribute("articles", articleService.getArticlesByCategoryName(category));
        model.addAttribute("displayName", displayName);
        return "views/columnPage";
    }

    @RequestMapping("/search")
    public String search(Model model, String searchText) {
        searchText = "%" + searchText + "%";
        List<Article> articles = articleService.getArticleByText(searchText);

        model.addAttribute("articles", articles);
        return "views/index";
    }

    @RequestMapping("/detail/{id}/{category}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Article article = articleService.getArticleById(id);
        //System.out.println(article.getContent());
        //将Markdown解析为HTML
        /*Markdown markdown = new Markdown();
        try {
            StringWriter out = new StringWriter();
            markdown.transform(new StringReader(article.getContent()), out);
            out.flush();
            article.setContent(out.toString());
            System.out.println("------------------");
            System.out.println(article.getContent());
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        article.setContent(pdp.markdownToHtml(article.getContent()));

        model.addAttribute("article", article);
        return "views/detail";
    }

    //访问后台首页：localhost:8080/chenyu，跳转到admin/index.jsp，如果没登录，拦截器需要先登录
    @RequestMapping("/chenyu")
    public String admin(Model model) {
        model.addAttribute("articlesForAdmin", articleService.getAllArticle());
        return "admin/index";
    }

    //跳转到后台登录页面，admin/login.jsp
    @RequestMapping("/chenyu/login")
    public String login() {
        return "admin/login";
    }

    //后台登录逻辑判断，登录失败跳转到admin/login.jsp，登录成功，重定向到/chenyu
    @RequestMapping(value = "/chenyu/dologin", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, User user, Model model, HttpSession session) {
        System.out.println("user.getUsername():" + user.getUsername() + ";user.getPassword():" + user.getPassword());
        if (userService.login(user.getUsername(), user.getPassword())) {
            request.getSession().setAttribute("user", user);
            model.addAttribute("user", user);

            List<Category> categories = articleService.getCategories();
            //移除首页分类
            categories.remove(0);
            Collections.reverse(categories);
            session.setAttribute("categories", categories);
            return "redirect:/chenyu";
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "admin/login";
        }
    }

//    private String returnAdminIndex(Model model) {
//        model.addAttribute("articles", articleService.getFirst10Article());
//        return "redirect:/chenyu";
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/chenyu/dologin")
    public String doLogin(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("user") == null) {
            return "admin/login";
        }
        return "redirect:/chenyu";
    }

    //点击写博客，GET请求，跳转到admin/write.jsp
    @RequestMapping("/chenyu/write")
    public String write(HttpSession session) {
        /*List<Category> categories = articleService.getCategories();
        //移除首页分类
        categories.remove(0);
        Collections.reverse(categories);
        session.setAttribute("categories", categories);*/
        return "admin/write";
    }

    //点击发表，POST请求
    @RequestMapping(value = "/chenyu/write", method = RequestMethod.POST)
    public String write(Article article) {
        //article.setDate(new Date().toString());
        if (article.getId() == 0l) {
            articleService.writeBlog(article);
        } else {
            articleService.updateBlog(article);
        }
        return "redirect:/chenyu";
    }

    //删除文章
    @RequestMapping("/chenyu/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        articleService.deleteArticleById(id);
        return "redirect:/chenyu";
    }

    //修改文章
    @RequestMapping("/chenyu/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "admin/write";
    }
}
