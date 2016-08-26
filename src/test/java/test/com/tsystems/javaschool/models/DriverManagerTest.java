package test.com.tsystems.javaschool.models;

import com.tsystems.javaschool.entities.Driver;
import com.tsystems.javaschool.services.DriverManager;
import com.tsystems.javaschool.repos.DriverRepository;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by Igor Avdeev on 8/24/16.
 */
public class DriverManagerTest {

    @Test
    public void canAddDriver()
    {
        DriverRepository mockRepo = mock(DriverRepository.class);
        DriverManager manager = new DriverManager(mockRepo);

        Driver driver = new Driver();
        driver.setFirstName("John");
        driver.setLastName("Williams");
        driver.setHoursWorked(12);

        manager.addDriver(driver);

        verify(mockRepo).add(driver);
    }
}
