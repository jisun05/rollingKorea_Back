package history.traveler.springbootdeveloper.controller;

import history.traveler.springbootdeveloper.domain.Article;
import history.traveler.springbootdeveloper.dto.ArticleListViewResponse;
import history.traveler.springbootdeveloper.dto.ArticleViewResponse;
import history.traveler.springbootdeveloper.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RequiredArgsConstructor
@Controller
@Slf4j
public class LogViewController {

    private final LogService logService;
    @GetMapping("/articles")
    public String getArticles(Model model){
        List<ArticleListViewResponse> articles = logService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);
        log.debug("log test1111");
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = logService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if(id == null){
            model.addAttribute("article", new ArticleViewResponse());

        }else {
            Article article = logService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
