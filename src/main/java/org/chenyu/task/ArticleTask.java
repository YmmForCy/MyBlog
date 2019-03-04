package org.chenyu.task;

import org.chenyu.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    /**
     * 每天0点删除状态为0的文章
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void refreshSeckillGoods() {
        long size = articleDao.deleteArticleByStatus();

        System.err.println("已将" + size + "篇文章删除");
    }
}
