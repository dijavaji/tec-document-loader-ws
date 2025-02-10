package ec.com.technoloqie.document.loader.api.mapper;

import java.util.Collection;
import java.util.Date;

import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.dto.IntentKnowlegeDto;
import ec.com.technoloqie.document.loader.api.model.Assistant;
import ec.com.technoloqie.document.loader.api.model.Intent;
import ec.com.technoloqie.document.loader.api.model.Response;

public class IntentMapper {
	
	private IntentMapper() {}

	public static Intent mapToIntent(IntentDto intentdto) {
		Intent intent = new Intent();
		intent.setId(intentdto.getId());
		intent.setName(intentdto.getName());
		intent.setDescription(intentdto.getDescription());
		intent.setCreatedDate(new Date());
		intent.setCreatedBy(intentdto.getCreatedBy());
		Assistant assistant = new Assistant();
		assistant.setId(intentdto.getAssistantId());
		intent.setAssistant(assistant);
		return intent;
	}

	public static IntentDto mapToIntentDto(Intent savedIntent) {
		IntentDto intentDto = new IntentDto();
		intentDto.setId(savedIntent.getId());
		intentDto.setName(savedIntent.getName());
		intentDto.setAssistantId(savedIntent.getAssistant().getId());
		intentDto.setDescription(savedIntent.getDescription());
		intentDto.setCreatedBy(savedIntent.getCreatedBy());
		intentDto.setPhrases(PhraseMapper.mapToPhraseDtols(savedIntent.getPhrases()));
		return intentDto;
	}
	
	public static Collection <IntentKnowlegeDto> mapToListIntentKnowlegeDto(Intent intent) {
		return intent.getPhrases().stream().map(phrase ->{
			IntentKnowlegeDto intentKnowlege = new IntentKnowlegeDto();
			intentKnowlege.setId(intent.getId());
			intentKnowlege.setName(intent.getName());
			intentKnowlege.setQuery(phrase.getPhrase());
			Response response = phrase.getResponses().iterator().next();
			intentKnowlege.setResponse(response.getResponse());
			return intentKnowlege;
			}).toList();
	}

}
