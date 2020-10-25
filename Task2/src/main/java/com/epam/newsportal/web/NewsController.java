package com.epam.newsportal.web;

import com.epam.newsportal.domain.entity.News;
import com.epam.newsportal.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    private ModelAndView navigate(String id, String view) {
        News news = newsService.find(id);
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("news", news);
        return modelAndView;
    }

    @GetMapping("/news/list")
    public ModelAndView viewAllNews() {
        return new ModelAndView("newsList");
    }

    @GetMapping("/news/{id}")
    public ModelAndView viewNews(@PathVariable String id) {
        return navigate(id, "news");
    }

    @GetMapping("/news/add")
    public ModelAndView addNews() {
        return new ModelAndView("addNews");
    }

    @PostMapping("/news/add")
    public ModelAndView addNews(@RequestBody News news) {
        newsService.create(news);
        return viewNews(news.getId());
    }

    @GetMapping("/news/modify/{id}")
    public ModelAndView modifyNews(@PathVariable String id) {
        return navigate(id, "addNews");
    }

    @PostMapping("/news/modify")
    public ModelAndView modifyNews(@RequestBody News news) {
        newsService.update(news);
        return viewNews(news.getId());
    }

    @GetMapping("/news/delete/{id}")
    public ModelAndView deleteNews(@PathVariable String id) {
        newsService.remove(newsService.find(id));
        return viewAllNews();
    }
}
