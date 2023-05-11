package com.gsquad.lunih;

import com.gsquad.lunih.dtos.accountDTO.AdminAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.CompanyAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.StudentAccountDTO;
import com.gsquad.lunih.dtos.accountDTO.UniversityAccountDTO;
import com.gsquad.lunih.dtos.categories.FacultyDTO;
import com.gsquad.lunih.dtos.categories.IndustryDTO;
import com.gsquad.lunih.dtos.categories.PostTypeDTO;
import com.gsquad.lunih.dtos.categories.ProgramDTO;
import com.gsquad.lunih.dtos.student.ApproveStudentDTO;
import com.gsquad.lunih.entities.categories.Industry;
import com.gsquad.lunih.repos.AccountRepo;
import com.gsquad.lunih.repos.CompanyRepo;
import com.gsquad.lunih.repos.StudentRepo;
import com.gsquad.lunih.repos.UniversityRepo;
import com.gsquad.lunih.repos.categories.FacultyRepo;
import com.gsquad.lunih.repos.categories.IndustryRepo;
import com.gsquad.lunih.repos.categories.PostTypeRepo;
import com.gsquad.lunih.repos.categories.ProgramRepo;
import com.gsquad.lunih.services.account.AccountService;
import com.gsquad.lunih.services.faculty.FacultyService;
import com.gsquad.lunih.services.industry.IndustryService;
import com.gsquad.lunih.services.post_type.PostTypeService;
import com.gsquad.lunih.services.program.ProgramService;
import com.gsquad.lunih.services.student.StudentService;
import com.gsquad.lunih.services.university.UniversityService;
import com.gsquad.lunih.utils.EnumCompanyType;
import com.gsquad.lunih.utils.EnumStudyLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableAsync
public class LunihApplication implements CommandLineRunner {

    private final AccountRepo accountRepo;
    private final AccountService accountService;

    private final StudentRepo studentRepo;
    private final StudentService studentService;

    private final CompanyRepo companyRepo;
//    private final CompanyService companyService;

    private final UniversityRepo universityRepo;
    private final UniversityService universityService;

    private final FacultyRepo facultyRepo;
    private final FacultyService facultyService;

    private final IndustryRepo industryRepo;
    private final IndustryService industryService;

    private final PostTypeRepo postTypeRepo;
    private final PostTypeService postTypeService;

    private final ProgramRepo programRepo;
    private final ProgramService programService;

    @Value("${default.password}")
    private String defaultPassword;

    public LunihApplication(AccountRepo accountRepo, AccountService accountService, StudentRepo studentRepo, StudentService studentService, CompanyRepo companyRepo, UniversityRepo universityRepo, UniversityService universityService, FacultyRepo facultyRepo, FacultyService facultyService, IndustryRepo industryRepo, IndustryService industryService, PostTypeRepo postTypeRepo, PostTypeService postTypeService, ProgramRepo programRepo, ProgramService programService) {
        this.accountRepo = accountRepo;
        this.accountService = accountService;
        this.studentRepo = studentRepo;
        this.studentService = studentService;
        this.companyRepo = companyRepo;
        this.universityRepo = universityRepo;
        this.universityService = universityService;
        this.facultyRepo = facultyRepo;
        this.facultyService = facultyService;
        this.industryRepo = industryRepo;
        this.industryService = industryService;
        this.postTypeRepo = postTypeRepo;
        this.postTypeService = postTypeService;
        this.programRepo = programRepo;
        this.programService = programService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LunihApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initData();
    }

    private void initData() {
        if (accountRepo.count() == 0) {
            createAdmin();
        }
        if (studentRepo.count() == 0) {
            createStudent();
        }

        if (facultyRepo.count() == 0) {
            createFaculty();
        }

        if (industryRepo.count() == 0) {
            createIndustry();
        }

        if (programRepo.count() == 0) {
            createProgram();
        }

        if (universityRepo.count() == 0) {
            createUniversity();
        }

        if (companyRepo.count() == 0) {
//            createCompany();
        }

        if (postTypeRepo.count() == 0) {
            createPostType();
        }

    }

    private void createPostType() {
        PostTypeDTO dto = new PostTypeDTO();
        dto.setNameEn("Job Offers");
        dto.setNameLv("Darba Piedāvājumi");
        postTypeService.create(dto);

        dto.setNameEn("Thesis Topic");
        dto.setNameLv("Diplomdarba tēma");
        postTypeService.create(dto);
    }

    private void createProgram() {
        ProgramDTO dto = new ProgramDTO();
        dto.setFacultyID(facultyService.listAll().get(0).getId());
        dto.setNameEn("Information technology");
        dto.setNameLv("Informācijas tehnoloģija");
        dto.setStudyLevel(EnumStudyLevel.LEVEL_BACHELOR.name());
        List<Integer> industryList = new ArrayList<>();
        industryList.add(industryService.listAll().get(0).getId());
        dto.setIndustryList(industryList);

        programService.create(dto);
    }

    private void createIndustry() {
        IndustryDTO dto = new IndustryDTO();
        dto.setNameEn("Information and communications technology");
        dto.setNameLv("Informācijas un komunikāciju tehnoloģijas");
        industryService.create(dto);

        dto.setNameEn("Agriculture");
        dto.setNameLv("Lauksaimniecība");
        industryService.create(dto);
    }

    private void createCompany() {
        CompanyAccountDTO dto = new CompanyAccountDTO();
        dto.setEmail("36owaf@gmail.com");
        dto.setPassword(defaultPassword);

        dto.setCompanyName("Google Inc");
        dto.setCompanyType(EnumCompanyType.COMPANY_PUBLIC.name());
        List<Integer> industryList = new ArrayList<>();
        industryList.add(industryService.listAll().get(0).getId());
        dto.setIndustryList(industryList);
        dto.setCompanyLogo("123");
        dto.setCompanyContactPersonName("Neil Ward");
        accountService.createNewCompany(dto);
    }

    private void createUniversity() {
        UniversityAccountDTO dto = new UniversityAccountDTO();
        dto.setEmail("university@university.com");
        dto.setPassword(defaultPassword);
        dto.setName("Bethan Johns");
        List<Integer> programList = new ArrayList<>();
        programList.add(programService.listAll().get(0).getId());
        dto.setProgramList(programList);

        accountService.createNewUniversity(dto);
    }

    private void createAdmin() {
        AdminAccountDTO dto = new AdminAccountDTO();
        dto.setEmail("admin@admin.com");
        dto.setPassword(defaultPassword);

        accountService.createNewAdmin(dto);
    }

    private void createStudent() {
        Date bday = new Date();
        StudentAccountDTO dto = new StudentAccountDTO();
        dto.setEmail("thienquoc98@gmail.com");
        dto.setPassword("123456");
        dto.setStudentID("22021P");
        dto.setFirstName("Thien Quoc");
        dto.setSurName("Nguyen");
        dto.setGender(false);
        dto.setBirthDay(bday);
        dto.setPhoneNumber("+37199999999");
        accountService.createNewStudent(dto);
        ApproveStudentDTO approveStudentDTO = new ApproveStudentDTO(true, "");
        studentService.approveStudent("22021P", approveStudentDTO);

        dto.setEmail("test@gmail.com");
        dto.setPassword("123456");
        dto.setStudentID("22022P");
        dto.setFirstName("Test Quoc");
        dto.setSurName("Nguyen");
        dto.setGender(true);
        dto.setBirthDay(bday);
        dto.setPhoneNumber("+37199999999");
        accountService.createNewStudent(dto);
    }

    private void createFaculty() {
        FacultyDTO dto = new FacultyDTO();
        dto.setNameEn("Faculty of Science and Engineering");
        dto.setNameLv("Dabas un inženierzinātņu fakultāte");
        facultyService.create(dto);

        dto.setNameEn("Faculty of Humanitarian sciences and Arts");
        dto.setNameLv("Humanitāro un mākslas zinātņu fakultāte");
        facultyService.create(dto);
    }
}
