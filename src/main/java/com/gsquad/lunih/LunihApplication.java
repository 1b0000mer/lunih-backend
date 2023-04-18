package com.gsquad.lunih;

import com.gsquad.lunih.dtos.ProductDTO;
import com.gsquad.lunih.dtos.RoleDTO;
import com.gsquad.lunih.dtos.StudentAccountDTO;
import com.gsquad.lunih.repos.AccountRepo;
import com.gsquad.lunih.repos.ProductRepo;
import com.gsquad.lunih.repos.RoleRepo;
import com.gsquad.lunih.services.account.AccountService;
import com.gsquad.lunih.services.product.ProductService;
import com.gsquad.lunih.services.role.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LunihApplication implements CommandLineRunner {

    private final ProductRepo productRepo;

    private final ProductService productService;

    private final RoleRepo roleRepo;

    private final RoleService roleService;

    private final AccountRepo accountRepo;

    private final AccountService accountService;

    public LunihApplication(ProductRepo productRepo, ProductService productService, RoleRepo roleRepo, RoleService roleService, AccountRepo accountRepo, AccountService accountService) {
        this.productRepo = productRepo;
        this.productService = productService;
        this.roleRepo = roleRepo;
        this.roleService = roleService;
        this.accountRepo = accountRepo;
        this.accountService = accountService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LunihApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
    }

    private void initData() {
        if (productRepo.count() == 0) {
            ProductDTO dto = new ProductDTO();
            dto.setName("test1");
            dto.setPrice(500);
            productService.create(dto);
        }

        if (roleRepo.count() == 0) {
            RoleDTO dto = new RoleDTO();
            dto.setName("Student");
            roleService.create(dto);
        }

        if (accountRepo.count() == 0) {
            StudentAccountDTO dto = new StudentAccountDTO();
            dto.setEmail("thienquoc98@gmail.com");
            dto.setPassword("123456");
            dto.setRole_id(roleRepo.findAll().get(0).getId());
            dto.setStudentID("22021P");
            dto.setFirstName("Thien Quoc");
            dto.setSurName("Nguyen");
            accountService.createNewStudent(dto);
        }
    }
}
