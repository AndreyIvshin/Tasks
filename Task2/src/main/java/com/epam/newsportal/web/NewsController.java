package com.epam.newsportal.web;

import com.epam.newsportal.persistence.DatabaseFiller;
import com.epam.newsportal.persistence.entity.News;
import com.epam.newsportal.security.Secured;
import com.epam.newsportal.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.epam.newsportal.persistence.enumeration.Role.*;

@Controller
@Transactional
@Secured(ADMIN)
public class NewsController extends AbstractController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private DatabaseFiller databaseFiller;

    @Secured({GUEST, USER, ADMIN})
    @GetMapping("/")
    public ModelAndView index() {
        databaseFiller.init();
        return new ModelAndView("redirect:/news/list");
    }

    @Secured({GUEST, USER, ADMIN})
    @GetMapping("/news/list")
    public ModelAndView viewAllNews() {
        ModelAndView modelAndView = new ModelAndView("newsList");
        modelAndView.addObject("newsList", newsService.findAllOrdered());
        return modelAndView;
    }

    @Secured({USER, ADMIN})
    @GetMapping("/news/{id}")
    public ModelAndView viewNews(@PathVariable Long id) {
        News news = newsService.find(id);
        ModelAndView modelAndView = new ModelAndView("news");
        modelAndView.addObject("news", news);
        return modelAndView;
    }

    @GetMapping("/news/add")
    public ModelAndView addNews() {
        return new ModelAndView("addNews");
    }

    @PostMapping("/news/add")
    public ModelAndView addNews(@ModelAttribute News news) {
        System.out.println(news);
        newsService.create(news);
        return new ModelAndView("redirect:/news/" + news.getId());
    }

    @GetMapping("/news/edit/{id}")
    public ModelAndView editNews(@PathVariable Long id) {
        News news = newsService.find(id);
        ModelAndView modelAndView = new ModelAndView("editNews");
        modelAndView.addObject("news", news);
        return modelAndView;
    }

    @PostMapping("/news/edit")
    public ModelAndView editNews(@ModelAttribute News news) {
        System.out.println(news);
        newsService.update(news);
        return new ModelAndView("redirect:/news/" + news.getId());
    }

    @GetMapping("/news/delete/{id}")
    public ModelAndView deleteNews(@PathVariable Long id) {
        newsService.remove(newsService.find(id));
        return new ModelAndView("redirect:/news/list");
    }
}
