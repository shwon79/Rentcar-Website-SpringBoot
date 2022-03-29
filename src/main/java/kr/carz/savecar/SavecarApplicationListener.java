package kr.carz.savecar;

import kr.carz.savecar.domain.DateTime;
import kr.carz.savecar.domain.ExpectedDay;
import kr.carz.savecar.service.ExpectedDayService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SavecarApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	private final ExpectedDayService expectedDayService;

	public SavecarApplicationListener(ExpectedDayService expectedDayService) {
		this.expectedDayService = expectedDayService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		List<ExpectedDay> expectedDayList = expectedDayService.findAll();
		new DateTime(expectedDayList.get(0).getExpectedDay());
	}
}
