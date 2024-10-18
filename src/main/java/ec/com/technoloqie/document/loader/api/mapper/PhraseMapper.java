package ec.com.technoloqie.document.loader.api.mapper;

import java.util.Date;

import ec.com.technoloqie.document.loader.api.dto.PhraseDto;
import ec.com.technoloqie.document.loader.api.model.Phrase;

public class PhraseMapper {
	
	public static Phrase mapToPhrase(PhraseDto phrasedto) {
		Phrase phrase = new Phrase();
		phrase.setPhrase(phrasedto.getPhrase());
		phrase.setCreatedBy(phrasedto.getCreatedBy());
		phrase.setCreatedDate(new Date());
		return phrase;
	}
	
	public static PhraseDto mapToPhraseDto(Phrase phrase ) {
		PhraseDto phraseDto = new PhraseDto();
		phraseDto.setPhrase(phrase.getPhrase());
		phraseDto.setCreatedBy(phrase.getCreatedBy());
		return phraseDto;
	}
}
