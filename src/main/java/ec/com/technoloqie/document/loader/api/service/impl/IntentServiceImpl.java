package ec.com.technoloqie.document.loader.api.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.dto.PhraseDto;
import ec.com.technoloqie.document.loader.api.mapper.IntentMapper;
import ec.com.technoloqie.document.loader.api.mapper.PhraseMapper;
import ec.com.technoloqie.document.loader.api.model.Intent;
import ec.com.technoloqie.document.loader.api.model.Phrase;
import ec.com.technoloqie.document.loader.api.repository.IIntentRepository;
import ec.com.technoloqie.document.loader.api.repository.IPhraseRepository;
import ec.com.technoloqie.document.loader.api.repository.IResponseRepository;
import ec.com.technoloqie.document.loader.api.service.IIntentService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IntentServiceImpl implements IIntentService{
	
	@Autowired
	private IIntentRepository intentRepository;
	@Autowired
	private IPhraseRepository phraseRepository;
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
		Intent existAccount = this.intentRepository.findById(id).orElseThrow(()-> new DocumentLoaderException("Error la cuenta no existe")); //tenemos que comprobar si con la identificación dada existe en la db o no
		//existAccount.setBalance(intentdto.getBalance());
		//existAccount.setModifiedBy(intentdto.getModifiedBy());
		//existAccount.setModifiedDate(LocalDate.now());
		//existAccount.setStatus(intentdto.getStatus());
		this.intentRepository.save(existAccount);
		return IntentMapper.mapToIntentDto(existAccount);
	}

	@Override
	public void deleteIntent(Integer code) throws DocumentLoaderException {
		getIntentById(code);
		this.intentRepository.deleteById(code);
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
		Intent intent = this.intentRepository.findById(code).orElseThrow(()-> new DocumentLoaderException("Error la cuenta no existe"));
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
					return PhraseMapper.mapToPhraseDto(phraseSaved) ;
				}).collect(Collectors.toSet());
			//savedIntent.setPhrases(phrases);
			newIntentDto = IntentMapper.mapToIntentDto(savedIntent);
			newIntentDto.setPhrases(phrases);
		}catch(Exception e) {
			log.error("Error al momento de guardar intencion",e);
			throw new DocumentLoaderException("Error al momento de guardar intencion",e);
		}
		return newIntentDto;
	}
	
}
