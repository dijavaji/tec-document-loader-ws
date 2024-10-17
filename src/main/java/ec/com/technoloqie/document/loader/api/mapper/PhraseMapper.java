package ec.com.technoloqie.document.loader.api.mapper;

import ec.com.technoloqie.document.loader.api.dto.PhraseDto;
import ec.com.technoloqie.document.loader.api.model.Phrase;

public class PhraseMapper {
	
	public static Phrase mapToPhrase(PhraseDto phrasedto) {
		Phrase phrase = new Phrase();
		return phrase;
	}
	
	public static PhraseDto mapToPhraseDto(Phrase phrase ) {
		PhraseDto phraseDto = new PhraseDto();
		return phraseDto;
	}
}
