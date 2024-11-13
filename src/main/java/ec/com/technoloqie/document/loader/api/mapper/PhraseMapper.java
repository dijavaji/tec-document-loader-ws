package ec.com.technoloqie.document.loader.api.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

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
		phraseDto.setResponses(ResponseMapper.mapToResponseDtols(phrase.getResponses()));
		phraseDto.setCreatedBy(phrase.getCreatedBy());
		return phraseDto;
	}
	
	public static Collection <PhraseDto> mapToPhraseDtols(Collection<Phrase> phrases){
		Collection <PhraseDto> phraseDtos = null;
		if(!CollectionUtils.isEmpty(phrases)) {
			phraseDtos = phrases.stream().map(PhraseMapper::mapToPhraseDto).collect(Collectors.toList());
		}
		return phraseDtos;
	}
}
