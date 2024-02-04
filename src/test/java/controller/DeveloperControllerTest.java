package controller;

import org.example.controller.DeveloperController;
import org.example.pojo.Developer;
import org.example.repository.DeveloperRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;

public class DeveloperControllerTest {
    @Mock
    private DeveloperRepository mockRepository = Mockito.mock(DeveloperRepository.class);
    @InjectMocks
    private DeveloperController developerController = new DeveloperController(mockRepository);
    private Developer testDeveloper;
    private List<Developer> testDeveloperList;

    @BeforeEach
    public void setUp() {
        testDeveloper = new Developer();
        testDeveloperList = List.of(testDeveloper);
    }

    @Test
    public void get() {
        Mockito.when(mockRepository.get(anyLong())).thenReturn(testDeveloper);
        Developer developer = developerController.get(anyLong());
        assertNotNull(developer);
    }
}
