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
		CalendarDate calendarDate = new CalendarDate();
		calendarDate.setDateId(Long.valueOf(1));
		calendarDate.setMonth("8");
		calendarDate.setDay("8");
		calendarDate.setSeason("성수기");
		calendarDate.setWDay("수");
		calendarDate.setYear("2021");

		calendarDateService.save(calendarDate);

		DateCamping dateCamping = new DateCamping();
		dateCamping.setDateId(calendarDate);
		dateCamping.setCarName("europe");

		calendarDate.addDateCamping(dateCamping);

		dateCampingService.save(dateCamping);




	}

}
