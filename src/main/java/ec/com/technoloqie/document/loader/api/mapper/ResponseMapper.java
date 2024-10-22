package ec.com.technoloqie.document.loader.api.mapper;

import java.util.Date;

import ec.com.technoloqie.document.loader.api.dto.ResponseDto;
import ec.com.technoloqie.document.loader.api.model.Response;

public final class ResponseMapper {

	public static Response mapToResponse(ResponseDto responseDto) {
		Response response = new Response();
		response.setResponse(responseDto.getResponse());
		response.setCreatedBy(responseDto.getCreatedBy());
		response.setCreatedDate(new Date());
		//response.setPhrase(PhraseMapper.mapToPhrase(responseDto.getPhrase()));
		return response;
	}

	public static ResponseDto mapToResponseDto(Response savedResponse) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setResponse(savedResponse.getResponse());
		responseDto.setCreatedBy(savedResponse.getCreatedBy());
		responseDto.setPhrase(PhraseMapper.mapToPhraseDto(savedResponse.getPhrase()));
		return responseDto;
	}

	
	
}
