package ec.com.technoloqie.document.loader.api.service;

import java.util.List;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.IntentDto;

public interface IIntentService {
	
	IntentDto createIntent(IntentDto intent) throws DocumentLoaderException;
	IntentDto updateIntent(IntentDto intentdto, int id)throws DocumentLoaderException;
	void deleteIntent(Integer code)throws DocumentLoaderException;
	List<IntentDto> getListIntents()throws DocumentLoaderException;
	//AccountDto findOneByAccNumber(Integer code);
	IntentDto getIntentById(Integer code) throws DocumentLoaderException;
	
}
