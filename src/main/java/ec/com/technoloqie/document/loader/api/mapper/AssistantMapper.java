package ec.com.technoloqie.document.loader.api.mapper;

import ec.com.technoloqie.document.loader.api.dto.AssistantDto;
import ec.com.technoloqie.document.loader.api.model.Assistant;

public class AssistantMapper {
	
	private AssistantMapper() {
		
	}
	
	public static AssistantDto mapToAssistantDto(Assistant savedFile) {
		AssistantDto assistantDto = new AssistantDto();
		assistantDto.setId(savedFile.getId());
		assistantDto.setAssistantTypeId(savedFile.getAssistantTypeId());
		return assistantDto;
	}
	
	public static Assistant mapToAssistant(AssistantDto assistantdto) {
		Assistant assistant = new Assistant();
		assistant.setId(assistantdto.getId());
		assistant.setName(assistantdto.getName());
		assistant.setLabel(assistantdto.getLabel());
		assistant.setAssistantTypeId(assistantdto.getAssistantTypeId());
		assistant.setLanguageId(assistantdto.getLanguageId());
		assistant.setCreatedBy(assistantdto.getCreatedBy());
		assistant.setCreatedDate(assistantdto.getCreatedDate());
		return assistant ;
	}

}
