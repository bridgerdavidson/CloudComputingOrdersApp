package edu.bridgerdavidson.products;

import edu.bridgerdavidson.products.data.UsersRepository;
import edu.bridgerdavidson.products.models.Role;
import edu.bridgerdavidson.products.models.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void findByUsername_returnsUser() {
        UserEntity u = new UserEntity();
        u.setUsername("testuser");
        u.setPassword("hashed");
        u.setRole(Role.CUSTOMER);
        u.setEnabled(true);

        usersRepository.save(u);

        var found = usersRepository.findByUsername("testuser");
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser");
    }
}
