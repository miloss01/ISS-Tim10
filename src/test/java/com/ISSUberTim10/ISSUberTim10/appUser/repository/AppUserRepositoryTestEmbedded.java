package com.ISSUberTim10.ISSUberTim10.appUser.repository;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
 @ActiveProfiles("test")
// @ExtendWith(SpringExtension.class) - already included in @DataJpaTest
public class AppUserRepositoryTestEmbedded {

    @Autowired
    private AppUserRepository appUserRepository;


    @Test
    public void findById() {
        Optional<AppUser> test = appUserRepository.findById(1L);
        assertThat(test).isNotEmpty();
        System.out.println("--------------------" + test.get().getId());
    }

}
