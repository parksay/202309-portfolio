package simple.myboard.myprac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simple.myboard.myprac.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {


}
