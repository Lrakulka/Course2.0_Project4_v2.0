package configuration;

import com.course.configuration.DatabaseConfiguration;
import com.course.configuration.SpringSecurityConfig;
import com.course.repository.BillRepository;
import com.course.repository.CardRepository;
import com.course.repository.UserRepository;
import com.course.service.*;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.course.repository"})
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
@Import(DatabaseConfiguration.class)
public class TestConfig extends DatabaseConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = (ComboPooledDataSource) super.dataSource();
        dataSource.setJdbcUrl(environment.getRequiredProperty("jdbc.url.test"));
        return dataSource;
    }

    @Bean
    protected Properties hibernateProperties() {
        Properties properties = super.hibernateProperties();
        properties.put("hibernate.hbm2ddl.auto",
                environment.getRequiredProperty("hibernate.hbm2ddl.auto.test"));
       return properties;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return SpringSecurityConfig.passwordEncoder();
    }

    @Bean
    UserService userService(UserRepository userRepository) {
        return new UserServiceImp(userRepository);
    }

    @Bean
    CardService cardService(CardRepository cardRepository) {
        return new CardServiceImp(cardRepository);
    }

    @Bean
    BillService billService(BillRepository billRepository) {
        return new BillServiceImp(billRepository, passwordEncoder(), environment);
    }
}