package com.epam.newsportal.repository;

import com.epam.newsportal.model.entity.News;
import com.epam.newsportal.model.entity.User;
import com.epam.newsportal.model.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional
public class DatabaseFiller {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private int count = 0;

    public void init() {
        if (count != 0) {
            return;
        }
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(Role.ADMIN);
        userRepository.create(admin);
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRole(Role.USER);
        userRepository.create(user);
        for (int i = 0; i < 50; i++) {
            News news = new News();
            news.setTitle("Title " + i);
            news.setBrief("Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief " +
                    "Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief " +
                    "Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief " +
                    "Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief " +
                    "Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief Brief " +
                    "");
            news.setContent("Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content " +
                    "Content Content Content Content Content Content Content Content Content Content Content Content Content ");
            news.setDate(new Date());
            newsRepository.create(news);
        }
        count++;
    }
}
