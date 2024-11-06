package ec.com.technoloqie.document.loader.api.service;

import java.util.List;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.PhraseDto;

public interface IPhraseService {
	
	PhraseDto createPhrase(PhraseDto phrase) throws DocumentLoaderException;
	PhraseDto updatePhrase(PhraseDto phrasedto, int id)throws DocumentLoaderException;
	void deletePhrase(Integer code)throws DocumentLoaderException;
	List<PhraseDto> getListPhrases()throws DocumentLoaderException;
	//AccountDto findOneByAccNumber(Integer code);
	PhraseDto getPhraseById(Integer code) throws DocumentLoaderException;

}
