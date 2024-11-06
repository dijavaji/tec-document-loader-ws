package ec.com.technoloqie.document.loader.api.mapper;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import ec.com.technoloqie.document.loader.api.dto.PhraseDto;
import ec.com.technoloqie.document.loader.api.model.Phrase;

public final class PhraseMapper {
	
	public static Phrase mapToPhrase(PhraseDto phrasedto) {
		Phrase phrase = new Phrase();
		phrase.setPhrase(phrasedto.getPhrase());
		phrase.setCreatedBy(phrasedto.getCreatedBy());
		phrase.setCreatedDate(new Date());
		return phrase;
	}
	
	public static PhraseDto mapToPhraseDto(Phrase phrase ) {
		PhraseDto phraseDto = new PhraseDto();
		phraseDto.setId(phrase.getId());
		phraseDto.setPhrase(phrase.getPhrase());
		phraseDto.setCreatedBy(phrase.getCreatedBy());
		return phraseDto;
	}
	
	public static Set <PhraseDto> mapToPhraseDtols(Set<Phrase> phrases){
		Set <PhraseDto> phraseDtos = phrases.stream().map(PhraseMapper::mapToPhraseDto).collect(Collectors.toSet());
		return phraseDtos;
	}
}
