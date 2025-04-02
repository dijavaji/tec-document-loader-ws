package ec.com.technoloqie.document.loader.api.service;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.AssistantDto;

public interface IAssistantService {
	
	AssistantDto getAssistantByName(String code) throws DocumentLoaderException;
	
	AssistantDto getAssistantById(Integer code) throws DocumentLoaderException;

}
