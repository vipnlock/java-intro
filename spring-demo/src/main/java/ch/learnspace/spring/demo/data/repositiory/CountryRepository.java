package ch.learnspace.spring.demo.data.repositiory;

import ch.learnspace.spring.demo.data.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, String> {
    
    @Query("select c from Country c "
                   + "where lower(c.name) like concat('%', lower(:nameSnippet), '%')"
                   + "  or lower(c.localname) like concat('%', lower(:nameSnippet), '%')")
    List<Country> findAllLike(@Param("nameSnippet") String nameSnippet);
    
}
