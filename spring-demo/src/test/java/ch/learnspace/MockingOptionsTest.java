package ch.learnspace;

import ch.learnspace.spring.demo.data.entity.City;
import ch.learnspace.spring.demo.data.repositiory.CityRepository;
import lombok.var;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class MockingOptionsTest {

    private final City city1 = new City(1L, "TestCity1", "TST", "DSTRCT", 123456);
    private final City city2 = new City(2L, "TestCity2", "TST", "DSTRCT", 234567);
    private final City city3 = new City(3L, "TestCity3", "TST", "DSTRCT", 345678);

    // No Field for Repository Mock Variant 1: Mockito.mock()
    @Test
    @DisplayName("Repository Mock Variant 1: Mockito.mock()")
    void howToMock_Variant1_Mockito_mock() {
        final CityRepository mockCityRepository = Mockito.mock(CityRepository.class);
        Mockito.when(mockCityRepository.findById(1L)).thenReturn(Optional.of(city1));

        var result = mockCityRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(city1, result.orElse(null));
        Mockito.verify(mockCityRepository).findById(1L);
    }

    // Fields for Repository Mock Variant 2: @Mock Annotation
    @Mock
    private CityRepository mockCityRepository;

    @PostConstruct
    private void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Repository Mock Variant 2: @Mock Annotation")
    void howToMock_Variant2_Mock_Annotation() {
        Mockito.when(mockCityRepository.findById(2L)).thenReturn(Optional.of(city2));
        var result = mockCityRepository.findById(2L);

        assertTrue(result.isPresent());
        assertEquals(city2, result.orElse(null));
        Mockito.verify(mockCityRepository).findById(2L);
    }

    // Fields for Repository Mock Variant 3: @MockBean Annotation
    @MockBean
    private CityRepository mockBeanCityRepository;

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("Repository Mock Variant 3: @MockBean Annotation")
    void howToMock_Variant2_MockBean_Annotation() {
        Mockito.when(mockBeanCityRepository.findById(3L)).thenReturn(Optional.of(city3));
        var repository = context.getBean(CityRepository.class);

        assertNotNull(repository);
        var result = repository.findById(3L);

        assertTrue(result.isPresent());
        assertEquals(city3, result.orElse(null));
        Mockito.verify(mockBeanCityRepository).findById(3L);
    }
}