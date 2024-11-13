package ec.com.technoloqie.document.loader.api.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.dto.PhraseDto;
import ec.com.technoloqie.document.loader.api.dto.ResponseDto;
import ec.com.technoloqie.document.loader.api.mapper.IntentMapper;
import ec.com.technoloqie.document.loader.api.mapper.PhraseMapper;
import ec.com.technoloqie.document.loader.api.mapper.ResponseMapper;
import ec.com.technoloqie.document.loader.api.model.Intent;
import ec.com.technoloqie.document.loader.api.model.Phrase;
import ec.com.technoloqie.document.loader.api.model.Response;
import ec.com.technoloqie.document.loader.api.repository.IIntentRepository;
import ec.com.technoloqie.document.loader.api.repository.IPhraseRepository;
import ec.com.technoloqie.document.loader.api.repository.IResponseRepository;
import ec.com.technoloqie.document.loader.api.repository.dao.IIntentDao;
import ec.com.technoloqie.document.loader.api.service.IIntentService;
import ec.com.technoloqie.document.loader.api.service.IPhraseService;
import ec.com.technoloqie.document.loader.api.service.IResponseService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IntentServiceImpl implements IIntentService{
	
	@Autowired
	private IIntentRepository intentRepository;
	@Autowired
	private IIntentDao intentDao;
	@Autowired
	private IPhraseRepository phraseRepository;
	@Autowired
	private IPhraseService phraseService;
	@Autowired
	private IResponseService responseService;
	@Autowired
	private IResponseRepository responseRepository;

	@Override
	@Transactional
	public IntentDto createIntent(IntentDto intentdto) throws DocumentLoaderException {
		IntentDto newIntent = null;
		try {
			Intent intent = IntentMapper.mapToIntent(intentdto);
			Intent savedIntent = this.intentRepository.save(intent);
			newIntent = IntentMapper.mapToIntentDto(savedIntent);
		}catch(Exception e) {
			log.error("Error al momento de guardar intencion",e);
			throw new DocumentLoaderException("Error al momento de guardar intencion",e);
		}
		return newIntent;
	}

	@Override
	@Transactional
	public IntentDto updateIntent(IntentDto intentdto, int id) throws DocumentLoaderException {
		Intent existIntent = this.intentRepository.findById(id).orElseThrow(()-> new DocumentLoaderException("Error la intencion no existe")); //tenemos que comprobar si con la identificación dada existe en la db o no
		existIntent.setName(intentdto.getName());
		existIntent.setPhrases(PhraseMapper);
		existIntent.setModifiedBy(intentdto.getModifiedBy());
		existIntent.setModifiedDate(new Date());
		//existIntent.setStatus(intentdto.getStatus());
		this.intentRepository.save(existIntent);
		return IntentMapper.mapToIntentDto(existIntent);
	}

	@Override
	@Transactional
	public void deleteIntent(Integer code) throws DocumentLoaderException {
		try {
			Intent searchIntent = this.intentRepository.findById(code).orElseThrow(()-> new DocumentLoaderException("Error la intencion no existe"));
			//Intent searchIntent = intentDao.getIntentById(code);	
			/*Collection<Phrase> deletePhrases = searchIntent.getPhrases();
			log.info("borro respuestas {}", deletePhrases.size());
			deletePhrases.stream().map(phrase -> {
				log.info("borrando {}",phrase.getId(), phrase.getResponses());
				this.responseService.deleteResponseByList(phrase.getResponses());
				return phrase;
			}).collect(Collectors.toList());
			
			log.info("borro preguntas {}", deletePhrases.size());
			this.phraseService.deletePhraseByList(deletePhrases);
			*/
			this.intentRepository.delete(searchIntent);
		}catch(Exception e) {
			log.error("Error al momento de eliminar intencion",e);
			throw new DocumentLoaderException("Error al momento de eliminar intencion",e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<IntentDto> getListIntents() throws DocumentLoaderException {
		List <Intent> intents = this.intentRepository.findAll();
		return intents.stream().map((intent)-> IntentMapper.mapToIntentDto(intent)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public IntentDto getIntentById(Integer code) throws DocumentLoaderException {
		Intent intent = this.intentRepository.findById(code).orElseThrow(()-> new DocumentLoaderException("Error la intencion no existe"));
		//Intent intent = this.intentDao.getIntentById(code);
		return IntentMapper.mapToIntentDto(intent);
	}

	@Override
	@Transactional
	public IntentDto createIntentKnowledge(IntentDto intentDto) throws DocumentLoaderException {
		IntentDto newIntentDto = null;
		try {
			log.info("creo intencion {}", intentDto.getPhrases());
			Intent intent = IntentMapper.mapToIntent(intentDto);
			Intent savedIntent = this.intentRepository.save(intent);
			
			Set <PhraseDto> phrases = intentDto.getPhrases().stream().map((phraseDto) -> {
					Phrase phrase = PhraseMapper.mapToPhrase(phraseDto);
					phrase.setIntent(savedIntent);
					Phrase phraseSaved = this.phraseRepository.save(phrase);
					PhraseDto phraseDtoSaved = PhraseMapper.mapToPhraseDto(phraseSaved);
					//phraseDtoSaved.setResponses(phraseDto.getResponses());
					Set <ResponseDto> responsesDto = phraseDto.getResponses().stream().map((responseDto)->{
						Response response = ResponseMapper.mapToResponse(responseDto);
						response.setPhrase(phraseSaved);
						return ResponseMapper.mapToResponseDto(this.responseRepository.save(response));
					}).collect(Collectors.toSet());
					phraseDtoSaved.setResponses(responsesDto);
					return phraseDtoSaved;
				}).collect(Collectors.toSet());
			
			/*phrases.stream().map((phraseDto)->{
				log.info("creo respuestas {}", phraseDto.getId());
				 Collection <ResponseDto> responsesDto = phraseDto.getResponses().stream().map((response)->{
					 response.setPhrase(phraseDto);
					 return this.responseService.createResponse(response);
					 }).collect(Collectors.toSet());
				 phraseDto.setResponses(responsesDto);
				 return phraseDto;
			}).collect(Collectors.toSet());*/
			newIntentDto = IntentMapper.mapToIntentDto(savedIntent);
			newIntentDto.setPhrases(phrases);
		}catch(Exception e) {
			log.error("Error al momento de guardar intencion",e);
			throw new DocumentLoaderException("Error al momento de guardar intencion",e);
		}
		return newIntentDto;
	}
	
}
