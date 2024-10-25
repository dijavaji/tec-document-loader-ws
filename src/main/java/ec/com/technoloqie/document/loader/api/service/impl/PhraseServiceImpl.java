package ec.com.technoloqie.document.loader.api.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.PhraseDto;
import ec.com.technoloqie.document.loader.api.mapper.PhraseMapper;
import ec.com.technoloqie.document.loader.api.model.Phrase;
import ec.com.technoloqie.document.loader.api.repository.IPhraseRepository;
import ec.com.technoloqie.document.loader.api.service.IPhraseService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PhraseServiceImpl implements IPhraseService{
	
	@Autowired
	private IPhraseRepository phraseRepository;
	
	@Override
	@Transactional
	public PhraseDto createPhrase(PhraseDto phraseDto) throws DocumentLoaderException {
		PhraseDto newPhrase = null;
		try {
			Phrase phrase = PhraseMapper.mapToPhrase(phraseDto);
			Phrase savedPhrase = this.phraseRepository.save(phrase);
			newPhrase = PhraseMapper.mapToPhraseDto(savedPhrase);
		}catch(Exception e) {
			log.error("Error al momento de guardar question",e);
			throw new DocumentLoaderException("Error al momento de guardar pregunta",e);
		}
		return newPhrase;
	}

	@Override
	@Transactional
	public PhraseDto updatePhrase(PhraseDto phrasedto, int id) throws DocumentLoaderException {
		Phrase existPhrase = this.phraseRepository.findById(id).orElseThrow(()-> new DocumentLoaderException("Error la pregunta no existe")); //tenemos que comprobar si con la identificación dada existe en la db o no
		//existAccount.setBalance(intentdto.getBalance());
		//existAccount.setModifiedBy(intentdto.getModifiedBy());
		//existAccount.setModifiedDate(LocalDate.now());
		//existAccount.setStatus(intentdto.getStatus());
		this.phraseRepository.save(existPhrase);
		return PhraseMapper.mapToPhraseDto(existPhrase);
	}

	@Override
	@Transactional
	public void deletePhrase(Integer code) throws DocumentLoaderException {
		getPhraseById(code);
		this.phraseRepository.deleteById(code);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PhraseDto> getListPhrases() throws DocumentLoaderException {
		List <Phrase> phrases = this.phraseRepository.findAll();
		return phrases.stream().map((phrase)-> PhraseMapper.mapToPhraseDto(phrase)).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public PhraseDto getPhraseById(Integer code) throws DocumentLoaderException {
		Phrase phrase = this.phraseRepository.findById(code).orElseThrow(()-> new DocumentLoaderException("Error la pregunta no existe"));
		return PhraseMapper.mapToPhraseDto(phrase);
	}

	@Override
	@Transactional
	public void deletePhraseByList(Collection<Phrase> phrases) throws DocumentLoaderException {
		//List <Integer> ids = phrases.stream().map(phrase -> phrase.getId()).collect(Collectors.toList());
		this.phraseRepository.deleteAll(phrases);
	}

}
