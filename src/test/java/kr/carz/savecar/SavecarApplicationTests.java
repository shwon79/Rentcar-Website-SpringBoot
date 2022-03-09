package kr.carz.savecar;

import kr.carz.savecar.domain.CalendarDate;
import kr.carz.savecar.domain.DateCamping;
import kr.carz.savecar.service.CalendarDateService;
import kr.carz.savecar.service.DateCampingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SavecarApplicationTests {


	@Autowired
	CalendarDateService calendarDateService;
	DateCampingService dateCampingService;

	@Test
	void contextLoads() {

	}

}
