package ec.com.technoloqie.document.loader.api.service.impl;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.FileDto;
import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.dto.PhraseDto;
import ec.com.technoloqie.document.loader.api.dto.ResponseDto;
import ec.com.technoloqie.document.loader.api.model.FileEntity;
import ec.com.technoloqie.document.loader.api.model.KnowledgeData;
import ec.com.technoloqie.document.loader.api.repository.dao.IFileStorageDao;
import ec.com.technoloqie.document.loader.api.service.IFileService;
import ec.com.technoloqie.document.loader.api.service.IFileStorageService;
import ec.com.technoloqie.document.loader.api.service.IIntentService;
import ec.com.technoloqie.document.loader.api.service.IKnowledgeDataService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageServiceImpl implements IFileStorageService{
	
	private static final String UPLOAD_DIR = "uploads/";
	
	//private final Path root = Paths.get("uploads");
	private IIntentService intentService;
	private IFileStorageDao fileStorageDao;
	private IKnowledgeDataService knowledgeDataService;
	private IFileService fileService; 
	
	public FileStorageServiceImpl(IIntentService intentService, IFileStorageDao fileStorageDao, IKnowledgeDataService knowledgeDataService, IFileService fileService) {
		this.intentService = intentService;
		this.fileStorageDao = fileStorageDao;
		this.knowledgeDataService = knowledgeDataService;
		this.fileService =fileService;
	}

	@Override
	public void saveCsv(MultipartFile file) {
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

	@Override
	public Collection<String> storeFiles(Collection<MultipartFile> files) throws DocumentLoaderException{
		return files.stream().map(file -> this.fileStorageDao.storeFile(file)).collect(Collectors.toList());
	}

	@Override
	public List<KnowledgeData> saveDocuments(Collection<MultipartFile> files, int fileId) throws DocumentLoaderException{
		if (org.springframework.util.CollectionUtils.isEmpty(files)) {
	            throw new DocumentLoaderException("Error los archivos no pueden estar vacios");
	        }
		List<Document> documents = convertMultipartFiletoDocuments(files);
		return this.knowledgeDataService.createVectorKnowledgeData(documents,fileId);
	}
	
	private List<Document> convertMultipartFiletoDocuments(Collection<MultipartFile> files) {
		List<Document> documents = new ArrayList<>();
		try {
			for (MultipartFile file : files) {
				
				String fileType = file.getContentType();
				if(fileType.equals("text/plain")) {
					documents.addAll(convertTxt(file.getResource()));
				}
				if(fileType.equals("application/pdf")) {
					documents.addAll(convertPdf(file.getResource()));
				}
				//mime type para .doc
				if(fileType.equals("application/msword") || fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
					documents.addAll(convertMsword(file.getResource()));
				}
				if(fileType.equals("text/csv")) {
					documents.addAll(convertCsv(file.getResource()));
				}
				
			}
		}catch(Exception e) {
			log.error("Error al momento de transformar documentos",e);
			throw new DocumentLoaderException("Error al momento de transformar documentos",e);
		}
		return documents;
	}
	
	
	private List<Document> convertCsv(Resource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Document> convertMsword(Resource resource) {
		TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        return tikaDocumentReader.read();
	}

	private List<Document> convertPdf(Resource resource) {
		PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource,
				PdfDocumentReaderConfig.builder()
					.withPageTopMargin(0)
					.withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
						.withNumberOfTopTextLinesToDelete(0)
						.build())
					.withPagesPerDocument(1)
					.build());
		List<Document> documentls = pdfReader.get();
        log.info("nro hojas {}", documentls.size());
		return documentls;
	}

	private List<Document> convertTxt(Resource resource) {
		TextReader textReader = new TextReader(resource);
        textReader.getCustomMetadata().put("file_name", resource.getFilename());
		return textReader.get();
	}

	@Override
	public FileDto saveFile(MultipartFile file, Integer assistantId, String createdBy) throws DocumentLoaderException {
		String fileName = file.getOriginalFilename();
		assert fileName != null;

		String fileType = file.getContentType();
		String filePath = UPLOAD_DIR + String.valueOf(assistantId) + File.separator + fileName;
		
		// Validar extensiones permitidas
        if (!fileType.equals("application/pdf") && 
            !fileType.equals("text/plain") && 
            !fileType.equals("application/msword")) {
            throw new IllegalArgumentException("Tipo de archivo no soportado");
        }
        
        try {
			this.fileStorageDao.storeFile(file.getInputStream(), fileName, fileType, filePath);
		} catch (Exception e) {
			log.error("Error en guardado de archivo fisico",e);
		}
     // Guardar detalles en la base de datos
        FileDto fileDto = fileService.createFile(fileName, fileType, filePath, assistantId, createdBy);
        
        //guardado vectorial
        Collection<MultipartFile> files = Arrays.asList(file); 
        this.saveDocuments(files, fileDto.getId());
		
		return fileDto;
	}

	@Override
	public List<FileEntity> listFiles() {
		// TODO Auto-generated method stub
		return null;
	}

}
