package ch.learnspace.spring.demo.data.repositiory;

import ch.learnspace.spring.demo.data.entity.Language;
import ch.learnspace.spring.demo.data.entity.LanguagePK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LanguageRepository extends CrudRepository<Language, LanguagePK> {
    
    @Query("select cl from Language cl where lower(cl.language) = :language")
    List<Language> findByLanguage(@Param("language") String language);
    
    List<Language> findByCountrycode(String countrycode);
}
