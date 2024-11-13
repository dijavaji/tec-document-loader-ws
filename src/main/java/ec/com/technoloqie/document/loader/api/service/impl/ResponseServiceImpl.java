package ec.com.technoloqie.document.loader.api.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.ResponseDto;
import ec.com.technoloqie.document.loader.api.mapper.ResponseMapper;
import ec.com.technoloqie.document.loader.api.model.Response;
import ec.com.technoloqie.document.loader.api.repository.IResponseRepository;
import ec.com.technoloqie.document.loader.api.service.IResponseService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ResponseServiceImpl implements IResponseService{
	
	@Autowired
	private IResponseRepository responseRepository;

	@Override
	@Transactional
	public ResponseDto createResponse(ResponseDto responseDto) throws DocumentLoaderException {
		ResponseDto newResponse = null;
		try {
			log.info("guarda respuesta {}", responseDto.getPhrase().getId());
			Response response = ResponseMapper.mapToResponse(responseDto);
			Response savedResponse = this.responseRepository.save(response);
			newResponse = ResponseMapper.mapToResponseDto(savedResponse);
		}catch(Exception e) {
			log.error("Error al momento de guardar respuesta",e);
			throw new DocumentLoaderException("Error al momento de guardar respuesta",e);
		}
		return newResponse;
	}

	@Override
	@Transactional
	public ResponseDto updateResponse(ResponseDto responsedto, int id) throws DocumentLoaderException {
		Response existResponse = this.responseRepository.findById(id).orElseThrow(()-> new DocumentLoaderException("Error la pregunta no existe")); //tenemos que comprobar si con la identificación dada existe en la db o no
		//existAccount.setBalance(intentdto.getBalance());
		//existAccount.setModifiedBy(intentdto.getModifiedBy());
		//existAccount.setModifiedDate(LocalDate.now());
		//existAccount.setStatus(intentdto.getStatus());
		this.responseRepository.save(existResponse);
		return ResponseMapper.mapToResponseDto(existResponse);
	}

	@Override
	@Transactional
	public void deleteResponse(Integer code) throws DocumentLoaderException {
		getResponseById(code);
		this.responseRepository.deleteById(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ResponseDto> getListResponse() throws DocumentLoaderException {
		List <Response> responses = this.responseRepository.findAll();
		return responses.stream().map((phrase)-> ResponseMapper.mapToResponseDto(phrase)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseDto getResponseById(Integer code) throws DocumentLoaderException {
		Response response = this.responseRepository.findById(code).orElseThrow(()-> new DocumentLoaderException("Error la respuesta no existe"));
		return ResponseMapper.mapToResponseDto(response);
	}

	@Override
	@Transactional
	public void deleteResponseByList(Collection<Response> responses) throws DocumentLoaderException {
		this.responseRepository.deleteAll(responses);
	}

}
