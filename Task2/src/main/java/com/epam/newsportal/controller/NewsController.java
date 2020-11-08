package com.epam.newsportal.controller;

import com.epam.newsportal.model.entity.News;
import com.epam.newsportal.model.transfer.NewsTransfer;
import com.epam.newsportal.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Transactional
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news/list")
    public String viewAllNews(Model model) {
        model.addAttribute("newsList", newsService.findAllOrdered());
        return "newsList";
    }

    @GetMapping("/news/show/{id}")
    public String viewNews(@PathVariable Long id, Model model) {
        model.addAttribute("news", newsService.find(id));
        return "newsShow";
    }

    @GetMapping("/news/add")
    public String addNews(Model model) {
        model.addAttribute("news", new NewsTransfer());
        return "newsAdd";
    }

    @PostMapping("/news/add/process")
    public String addNewsProcess(@ModelAttribute NewsTransfer newsTransfer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("news", newsTransfer);
            return "newsAdd";
        } else {
            newsService.create(newsTransfer);
            return "redirect:/news/show/" + newsTransfer.getId();
        }
    }

    @GetMapping("/news/edit/{id}")
    public String editNews(@PathVariable Long id, Model model) {
        model.addAttribute("news", newsService.find(id));
        return "newsEdit";
    }

    @PostMapping("/news/edit/process")
    public String editNewsProcess(@ModelAttribute NewsTransfer newsTransfer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("news", newsTransfer);
            return "newsEdit";
        } else {
            newsService.update(newsTransfer);
            return "redirect:/news/show/" + newsTransfer.getId();
        }
    }

    @PostMapping("/news/delete/process")
    public String deleteNewsProcess(@RequestParam Long id) {
        newsService.remove(newsService.find(id));
        return "redirect:/news/list";
    }
}
