package ec.com.technoloqie.document.loader.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.dto.FileDto;
import ec.com.technoloqie.document.loader.api.service.impl.FileStorageServiceImpl;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://127.0.0.1:3000")
@RestController
@RequestMapping("${ec.com.technoloqie.document.loader.api.prefix}/files")
@Slf4j
public class FileRestController {

	@Autowired
	private FileStorageServiceImpl storageService;

	@PostMapping("/upload-csv")
	public ResponseEntity<?> uploadFaqFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		Map<String, Object> response = new HashMap<>();

		/*if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}*/
		try {
			this.storageService.saveCsv(file);

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			response.put("message", "Archivo subido correcto");
			response.put("data", message);
			response.put("success", Boolean.TRUE);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error al momento de subir archivo.", e);
			response.put("message", "Error al momento de subir archivo.");
			response.put("error", "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage());
			response.put("success", Boolean.FALSE);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/{assistantId}/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Integer assistantId) {
		Map<String, Object> response = new HashMap<>();
		String createdBy = "be-app";	//TODO tomar de cabecera http y anotacion spring
		try {
            FileDto filePaths = storageService.saveFile(file, assistantId, createdBy);
            response.put("message", "Archivos guardados en: " );
			response.put("data", filePaths);
			response.put("success", Boolean.TRUE);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
        } catch (Exception e) {
        	log.error("Error al momento de subir archivos.",e);
			response.put("message", "Error al momento de subir archivos.");
			response.put("error", e.getMessage() +" : " + e);
			response.put("success", Boolean.FALSE);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

}
