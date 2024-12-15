package history.traveler.springbootdeveloper.controller;


import history.traveler.springbootdeveloper.domain.Article;
import history.traveler.springbootdeveloper.dto.AddArticleRequest;
import history.traveler.springbootdeveloper.dto.ArticleResponse;
import history.traveler.springbootdeveloper.dto.UpdateArticleRequest;
import history.traveler.springbootdeveloper.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LogApiController {

    private final LogService logService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal){
        Article savedArticle = logService.save(request, principal.getName());
        log.debug("log test22222");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);

    }
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = logService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);

    }


    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = logService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        logService.delete(id);

        return ResponseEntity.ok()
                .build();

    }
   @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle = logService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
   }



}
