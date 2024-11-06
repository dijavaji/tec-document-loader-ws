package ec.com.technoloqie.document.loader.api.service;

import java.util.List;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.ResponseDto;

public interface IResponseService {
	
	ResponseDto createResponse(ResponseDto response) throws DocumentLoaderException;
	ResponseDto updateResponse(ResponseDto responsedto, int id)throws DocumentLoaderException;
	void deleteResponse(Integer code)throws DocumentLoaderException;
	List<ResponseDto> getListResponse()throws DocumentLoaderException;
	ResponseDto getResponseById(Integer code) throws DocumentLoaderException;

}
