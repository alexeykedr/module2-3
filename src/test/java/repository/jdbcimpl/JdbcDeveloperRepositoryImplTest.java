package repository.jdbcimpl;

import org.example.model.Developer;
import org.example.model.Status;
import org.example.repository.DeveloperRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;

public class JdbcDeveloperRepositoryImplTest {
    @Mock
    DeveloperRepository mockRepository = Mockito.mock(DeveloperRepository.class);


    @InjectMocks
    private Developer testDeveloper;
    private List<Developer> testDevelopers;

    @BeforeEach
    void setUp() {
        testDeveloper = new Developer(
                1l,
                "iv",
                "inov",
                null,
                null,
                Status.ACTIVE);
        testDevelopers = List.of(testDeveloper);
    }

    @Test
    public void create() {

        Mockito.when(mockRepository.create(testDeveloper)).thenReturn(testDeveloper);
        assertNotNull(testDeveloper);
        assertEquals(Status.ACTIVE, testDeveloper.getStatus());

    }


    @Test
    public void get() {
        Mockito.when(mockRepository.get(anyLong())).thenReturn(testDeveloper);
        assertEquals(Status.ACTIVE, testDeveloper.getStatus());
        assertEquals("iv",testDeveloper.getFirstName());
        assertEquals("inov",testDeveloper.getLastName());
        assertNotNull(testDeveloper);


    }
}



