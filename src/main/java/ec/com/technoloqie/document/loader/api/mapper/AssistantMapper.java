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

}
