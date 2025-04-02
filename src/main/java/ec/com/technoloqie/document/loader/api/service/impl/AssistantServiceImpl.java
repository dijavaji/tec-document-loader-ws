package ec.com.technoloqie.document.loader.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.AssistantDto;
import ec.com.technoloqie.document.loader.api.mapper.AssistantMapper;
import ec.com.technoloqie.document.loader.api.model.Assistant;
import ec.com.technoloqie.document.loader.api.repository.IAssistantRepository;
import ec.com.technoloqie.document.loader.api.service.IAssistantService;

@Service
public class AssistantServiceImpl implements IAssistantService{
	
	private IAssistantRepository assistantRepository;
	
	public AssistantServiceImpl(IAssistantRepository assistantRepository){
		this.assistantRepository = assistantRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public AssistantDto getAssistantByName(String code) throws DocumentLoaderException {
		return AssistantMapper.mapToAssistantDto(assistantRepository.findOneByName(code));
	}

	@Override
	@Transactional(readOnly = true)	
	public AssistantDto getAssistantById(Integer code) throws DocumentLoaderException {
		Assistant assistant = this.assistantRepository.findById(code).orElseThrow(()-> new DocumentLoaderException("Error el asistente no existe"));
		return AssistantMapper.mapToAssistantDto(assistant);
	}

}
