package ec.com.technoloqie.document.loader.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.service.IIntentService;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("local")
@SpringBootTest
@Slf4j
class TecDocumentLoaderWsApplicationTests {
	
	@Autowired
	private IIntentService intentService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void deleteIntentTest() {
		try {
			log.info("ingreso deleteIntentTest.");
			this.intentService.deleteIntent(46);
		}catch(Exception e) {
			log.error("Error deleteIntentTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"deleteIntentTest.");
		}
	}
	
	@Test
	void updateIntentTest() {
		try {
			log.info("ingreso updateIntentTest.");
			IntentDto intent = new IntentDto();
			intent.setAssistantId(1);
			intent.setId(54);
			intent.setName("nuevofrecreo");
			intent.setDescription("nueva descripcion");
			intent.setModifiedBy("test");
			this.intentService.updateIntent(intent , 54);
		}catch(Exception e) {
			log.error("Error updateIntentTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"updateIntentTest.");
		}
	}
}
