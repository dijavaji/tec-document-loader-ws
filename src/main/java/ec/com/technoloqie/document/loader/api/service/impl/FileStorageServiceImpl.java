package ec.com.technoloqie.document.loader.api.service.impl;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.dto.PhraseDto;
import ec.com.technoloqie.document.loader.api.dto.ResponseDto;
import ec.com.technoloqie.document.loader.api.service.IFileStorageService;
import ec.com.technoloqie.document.loader.api.service.IIntentService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageServiceImpl implements IFileStorageService{
	
	//private final Path root = Paths.get("uploads");
	@Autowired
	private IIntentService intentService;

	@Override
	public void save(MultipartFile file) {
		try {
			log.info("carga de intenciones masivo");
			//Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line;
			//IntentDto intentDto = null;
			//Collection <IntentDto> saveintents = new ArrayList<>();
			LinkedList<IntentDto> saveintents = new LinkedList<>();
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(";");
				String intentName = data[0];
				
				Optional<IntentDto> intentSearch = saveintents.stream().filter(intent -> StringUtils.equals(intent.getName(),intentName)).findFirst();
				IntentDto intentCreate = intentSearch.orElse(null);
				if(intentCreate != null) {
					
					
					Collection <PhraseDto> phrases = new ArrayList<>();
					PhraseDto phraseDto = new PhraseDto();
					phraseDto.setPhrase(data[1]);
					phraseDto.setCreatedBy("app-back");
					
					Collection <ResponseDto> responses = new ArrayList<>();
					ResponseDto responseDto = new ResponseDto();
					responseDto.setResponse(data[2]);
					responseDto.setCreatedBy("app-back");
					responses.add(responseDto);
					
					phraseDto.setResponses(responses);
					phrases.add(phraseDto);
					//intentCreate.setCreatedBy("app-back");
					intentCreate.getPhrases().add(phraseDto);
					
				}else {
					//this.intentService.createIntent(intentDto);
					IntentDto intentDto = new IntentDto();
					intentDto.setName(intentName);
					intentDto.setAssistantId(1);
					intentDto.setCreatedBy("app-back");
					Collection <PhraseDto> phrases = new ArrayList<>();
					PhraseDto phraseDto = new PhraseDto();
					phraseDto.setPhrase(data[1]);
					phraseDto.setCreatedBy("app-back");
					
					Collection <ResponseDto> responses = new ArrayList<>();
					ResponseDto responseDto = new ResponseDto();
					responseDto.setResponse(data[2]);
					responseDto.setCreatedBy("app-back");
					
					responses.add(responseDto);
					
					phraseDto.setResponses(responses);
					phrases.add(phraseDto);
					intentDto.setPhrases(phrases);
					
					saveintents.add(intentDto);
				}
					
			}
			
			//saveintents.remove(0);
			saveintents.removeFirst();
			log.info("intenciones {}", saveintents.size());
			
			saveintents.stream().map(intentD ->{
				IntentDto newIntentDto= this.intentService.createIntentKnowledge(intentD);
				return newIntentDto;
			}).collect(Collectors.toSet());
			
		} catch (Exception e) {
			if (e instanceof FileAlreadyExistsException) {
				throw new RuntimeException("A file of that name already exists.");
			}
			log.error("Error al momento de guardar conocimiento masivo",e);
			throw new DocumentLoaderException("Error al momento de guardar conocimiento masivo",e);
		}
	}

}
