package com.mgforge.MGForge.bootstrap;

import com.mgforge.MGForge.entity.TenantEntity;
import com.mgforge.MGForge.enums.TenantStatus;
import com.mgforge.MGForge.repository.TenantRepository;
import com.mgforge.MGForge.repository.UserRepository;
import com.mgforge.MGForge.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DataSeeder implements CommandLineRunner {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(TenantRepository tenantRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        TenantEntity tenant = tenantRepository.findBySlug("demo-gym").orElseGet(()->{
            TenantEntity t = new TenantEntity();
            t.setSlug("demo-gym");
            t.setName("Demo gym");
            t.setStatus(TenantStatus.ACTIVE);
            return tenantRepository.save(t);
        });

        // userRepository.findByMobile()
    }
}
