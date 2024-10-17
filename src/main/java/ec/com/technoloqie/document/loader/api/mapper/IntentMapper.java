package ec.com.technoloqie.document.loader.api.mapper;

import java.util.Date;

import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.model.Intent;

public class IntentMapper {

	public static Intent mapToIntent(IntentDto intentdto) {
		Intent intent = new Intent();
		intent.setId(intentdto.getId());
		intent.setName(intentdto.getName());
		intent.setDescription(intentdto.getDescription());
		intent.setCreatedDate(new Date());
		intent.setCreatedBy(intentdto.getCreatedBy());
		intent.setAssistantId(intentdto.getAssistantId());
		return intent;
	}

	public static IntentDto mapToIntentDto(Intent savedIntent) {
		IntentDto intentDto = new IntentDto();
		intentDto.setId(savedIntent.getId());
		intentDto.setName(savedIntent.getName());
		intentDto.setAssistantId(savedIntent.getAssistantId());
		intentDto.setDescription(savedIntent.getDescription());
		intentDto.setCreatedBy(savedIntent.getCreatedBy());
		return intentDto;
	}

}
