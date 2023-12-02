package dma.app.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import dma.app.model.Application;

////////////////////////////////////////

// The methods in comments are tested and correct, but only for id that exists in database

///////////////////////////////////////


@SpringBootTest
@TestPropertySource(
locations = "classpath:application.properties")
class ApplicationDAOTest {
	
	@Autowired 
	ApplicationDAO applicationDAO;
		
	@Test
	void testAppDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(applicationDAO);
	}

	/*
	@Test
	void testFindById() {
		Application storedApplication = applicationDAO.findById(13);
		Assertions.assertNotNull(storedApplication);
		Assertions.assertEquals(13 , storedApplication.getId());
	}
	
	@Test
	void testFindBySubjectId() {
		Application storedApplication = applicationDAO.findBySubjectId(23);
		Assertions.assertNotNull(storedApplication);
		Assertions.assertEquals(23, storedApplication.getSubject().getId());
	}
	*/
}
