package com.example.mesh_test_task;

import com.example.mesh_test_task.repository.AdminRepository;
import com.example.mesh_test_task.service.AdminService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
class MeshTestTaskApplicationTests {

    @Autowired
    AdminRepository adminRepository;

    @Test
	void contextLoads() {
//        new HttpGet
        System.out.println(adminRepository.findByLoginAndPassword("user", DigestUtils.sha1Hex("pas")).getToken());
	}
}
