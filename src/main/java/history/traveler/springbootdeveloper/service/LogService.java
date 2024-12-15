package history.traveler.springbootdeveloper.service;


import history.traveler.springbootdeveloper.domain.Article;
import history.traveler.springbootdeveloper.dto.AddArticleRequest;
import history.traveler.springbootdeveloper.dto.UpdateArticleRequest;
import history.traveler.springbootdeveloper.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class LogService {
    private final LogRepository logRepository;

    public Article save(AddArticleRequest request){
        return logRepository.save(request.toEntity());

    }

    public List<Article> findAll(){
        return logRepository.findAll();
    }

    public Article findById(long id){
        return logRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {

        Article article = logRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeArticleAuthor(article);
        logRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request){
        Article article = logRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;

    }

    private static void authorizeArticleAuthor(Article article){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }


    public Article save(AddArticleRequest request, String userName){
        log.debug("log test33333");
        return logRepository.save(request.toEntity(userName));
    }

}
