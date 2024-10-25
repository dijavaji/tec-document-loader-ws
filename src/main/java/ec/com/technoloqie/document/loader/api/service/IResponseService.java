package ec.com.technoloqie.document.loader.api.service;

import java.util.Collection;
import java.util.List;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.ResponseDto;
import ec.com.technoloqie.document.loader.api.model.Response;

public interface IResponseService {
	
	ResponseDto createResponse(ResponseDto response) throws DocumentLoaderException;
	ResponseDto updateResponse(ResponseDto responsedto, int id)throws DocumentLoaderException;
	void deleteResponse(Integer code)throws DocumentLoaderException;
	List<ResponseDto> getListResponse()throws DocumentLoaderException;
	ResponseDto getResponseById(Integer code) throws DocumentLoaderException;
	
	void deleteResponseByList(Collection<Response> responses) throws DocumentLoaderException;

}
