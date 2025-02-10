package ec.com.technoloqie.document.loader.api.service.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.dto.IntentKnowlegeDto;
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
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IntentServiceImpl implements IIntentService{
	
	private IIntentRepository intentRepository;
	private IPhraseRepository phraseRepository;
	private IResponseRepository responseRepository;
	private IIntentDao intentDao;
	
	public IntentServiceImpl(IIntentRepository intentRepository, IPhraseRepository phraseRepository, IResponseRepository responseRepository, IIntentDao intentDao){
		this.intentRepository = intentRepository;
		this.phraseRepository = phraseRepository;
		this.responseRepository = responseRepository;
		this.intentDao = intentDao;
	}

	@Override
	@Transactional
	public IntentDto createIntent(IntentDto intentdto) throws DocumentLoaderException {
		IntentDto newIntent = null;
		try {
			//TODO buscar asistente por id
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
		existIntent.setDescription(intentdto.getDescription());
		//existIntent.setPhrases(PhraseMapper);
		existIntent.setModifiedBy(intentdto.getModifiedBy());
		existIntent.setModifiedDate(new Date());
		
		//Predicate predicado = new Predicate<T>() {
			
		//};
		Collection <Phrase> modifiPhrases = intentdto.getPhrases().stream().map(dto -> {
			Optional<Phrase> updatePhrase = existIntent.getPhrases().stream().filter(phrase -> phrase.getId()==dto.getId()).findFirst();
			Phrase upPhrase = updatePhrase.get();
			upPhrase.setPhrase(dto.getPhrase());
			upPhrase.setModifiedBy(intentdto.getModifiedBy());
			upPhrase.setModifiedDate(new Date());
			upPhrase.setResponses(searchUpdateResponses(dto.getResponses(),upPhrase.getResponses(),intentdto.getModifiedBy()));
			return upPhrase;
		}).collect(Collectors.toList());
		
		existIntent.setPhrases(modifiPhrases);
		//Optional<Integer> found = list.stream().filter(i -> i >= 1 && i <= 5).findAny();
		//CollectionUtils.filter(existIntent.getPhrases(), phrase-> phrase.);
		//existIntent.setCreatedBy(e);
		//existIntent.setStatus(intentdto.getStatus());
		this.intentRepository.save(existIntent);
		return IntentMapper.mapToIntentDto(existIntent);
	}
	
	private Collection <Response> searchUpdateResponses(Collection <ResponseDto> dtoresponses, Collection <Response> responses, String modifiby) {
		log.info("actualizo respuestas {}", responses.size());
		return dtoresponses.stream().map(dto ->{
			Optional<Response> optResponse = responses.stream().filter(response -> response.getId()==dto.getId()).findFirst();
			Response updateResponse = optResponse.get();
			updateResponse.setResponse(dto.getResponse());
			updateResponse.setModifiedBy(modifiby);
			updateResponse.setModifiedDate(new Date());
			return updateResponse;
		}).collect(Collectors.toList());
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
			//TODO buscar asistente por id
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
			log.error("Error al momento de guardar intenciones",e);
			throw new DocumentLoaderException("Error al momento de guardar intenciones",e);
		}
		return newIntentDto;
	}

	@Override
	public List<IntentDto> getListIntents(Integer assistantId) throws DocumentLoaderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<IntentKnowlegeDto> getListIntentKnowlege(Integer assistantId) throws DocumentLoaderException {
		List<IntentKnowlegeDto> intentKnowleges = new ArrayList<>();
		List <Intent> intents = intentDao.findIntentsKnowlegeByAssistant(assistantId);
		for (Intent intent : intents) {
			intentKnowleges.addAll(IntentMapper.mapToListIntentKnowlegeDto(intent)) ;
		}
		return intentKnowleges;
	}
	
}
