package dma.app.controller;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import dma.app.model.Role;
import dma.app.model.Student;
import dma.app.model.User;
import dma.app.service.UserService;
import dma.app.service.ApplicationService;
import dma.app.service.StudentService;
import dma.app.service.SubjectService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockMvc
public class StudentControllerTest {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired                           
    private MockMvc mockMvc;
	
	@MockBean
	StudentService studentService;
	
	@MockBean
	UserService userService;
	
	
	@MockBean
	SubjectService subjectService;
	
	@MockBean
	ApplicationService applicationService;
	
	
	@Autowired
	StudentController studentController;
	
	
	@Mock
	private Authentication auth;

	
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	
	@Test
	void testStudentControllerIsNotNull() {
		Assertions.assertNotNull(studentController);
	}
	
	@Test
	void testMockMvcIsNotNull() {
		Assertions.assertNotNull(mockMvc);
	}
	
	
	
	
	@Test 
	void testListAvailableSubjects() throws Exception {
		mockMvc.perform(get("/student/listofavailablesubjects")).
		andExpect(status().isOk()). // adds result matchers that check some property
		andExpect(view().name("student/availablesubjects")); // adds view matchers that check some property		
	}
	
	
	@Test
	void testShowDashboard() throws Exception {

		User user = new User(3,"Nikoleta", "1", Role.STUDENT);
		
		Student student = new Student(2,"Nikoleta", 4, 7.0, 10, user);
	    
		when(studentService.findById(2)).thenReturn(student);
		doNothing().when(studentService).saveProfile(student);
		
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(student.getId()));
	    multiValueMap.add("fullName", student.getFullName());
	    multiValueMap.add("yearOfStudies", Integer.toString(student.getYearOfStudies()));
	    multiValueMap.add("currentAverageGrade", Double.toString(student.getCurrentAverageGrade()));
	    multiValueMap.add("remainingCourses", Integer.toString(student.getRemainingCourses()));
    
	    
		mockMvc.perform(
				post("/student/save")
			    .params(multiValueMap)) 
				.andExpect(status().isOk())
				.andExpect(view().name("student/dashboard"));
						
	}
	

	
	
	/*
	@Test
	void testSaveStudent() throws Exception {
		User user = new User(3,"Nikoleta", "1", Role.STUDENT);
		
		Student student = new Student(2,"Nikoleta", 4, 7.0, 10, user);
	    
		when(studentService.findByUserId(3)).thenReturn(new Student(2,"Nikoleta", 4, 7.0, 10, user));
		doNothing().when(studentService).saveProfile(student);
		
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Integer.toString(2));
	    
		mockMvc.perform(
				get("/student/profile")
				.params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("/student/profile"));
	}
	
	
	
	@Test
	void testShowDetails() throws Exception {
		
		User user = new User(2,"Nikoleta", "1", Role.STUDENT);
		
		Student student = new Student(2,"Nikoleta", 4, 7.0, 10, user);
	    
		//when(userService.findByUsername("Nikoleta")).thenReturn(new Student(2,"Nikoleta", 4, 7.0, 10, user));
		when(studentService.findById(2)).thenReturn(new Student(2,"Nikoleta", 4, 7.0, 10, user));
		//doNothing().when(studentService).saveProfile(student);
		
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    
	    multiValueMap.add("studentId", Integer.toString(2));
	    
	    
	    multiValueMap.add("id", Integer.toString(student.getId()));
	    multiValueMap.add("fullName", student.getFullName());
	    multiValueMap.add("yearOfStudies", Integer.toString(student.getYearOfStudies()));
	    multiValueMap.add("currentAverageGrade", Double.toString(student.getCurrentAverageGrade()));
	    multiValueMap.add("remainingCourses", Integer.toString(student.getRemainingCourses()));
	    
		mockMvc.perform(
				post("/student/listofavailablesubjects/details")
				.params(multiValueMap))
				.andExpect(status().isOk())
				.andExpect(view().name("student/subjectdetails"));
	}
	
	
	
	
	
	
	@Test
    public void testShowDetails() {
        // Given
        int subjectId = 13;
        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setTitle("Math");
        
        //when(studentService.findById(2)).thenReturn(new Student(2,"Nikoleta", 4, 7.0, 10, user));
        User user = new User(13,"Nikoleta", "1", Role.STUDENT);
        
        Model theModel = Mockito.mock(Model.class);
        
        Mockito.when(studentService.findById(subjectId)).thenReturn(new Student(13,"Nikoleta", 4, 7.0, 10, user));
 
        // When
        String viewName = studentController.showDetails(subjectId, theModel);
 
        // Then
        assertEquals("student/subjectdetails", viewName);
        Mockito.verify(studentService).findById(subjectId);
        Mockito.verify(theModel).addAttribute("subject", subject);
    }
	
	@WithMockUser(value = "maria1998")
	@Test
	void testApplyForSubject() throws Exception {
		Subject subject = new Subject(1, "math", null, null, null, true);
		
		Student student = new Student(2,"Nikoleta", 4, 7.0, 10, null);
		
		ArrayList<Application> applications  = new ArrayList<Application>();
		
		for (int i = 0; i < 5; i++)
			applications.add(new Application(1+i, null, null));

		when(subjectService.findById(1)).thenReturn(subject);
		
		//when(studentService.findById(2)).thenReturn(applications.get(subjectService.findById(1).getId())); /// lathos
			
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("subjectId", Integer.toString(1));
	    mockMvc.perform(get("/student/application").params(multiValueMap)).
		andExpect(status().isOk()).
		andExpect(view().name("student/successfulapply"));
		
	}*/
	
	
}
