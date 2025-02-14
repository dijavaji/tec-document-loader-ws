package ec.com.technoloqie.document.loader.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.dto.IntentKnowlegeDto;
import ec.com.technoloqie.document.loader.api.service.IIntentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://127.0.0.1:3000")
@RestController
@RequestMapping("${ec.com.technoloqie.document.loader.api.prefix}/intents")
@Slf4j
public class IntentRestController {
	
	private IIntentService intentService;
	
	public IntentRestController(IIntentService intentService) {
		this.intentService = intentService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> addIntent(@Valid @RequestBody IntentDto intentDto, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			IntentDto intent = this.intentService.createIntentKnowledge(intentDto);
			response.put("message", "Intencion guardada correctamente");
			response.put("data", intent);
			response.put("success", Boolean.TRUE);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
		}catch(Exception e) {
			log.error("Error servicio de guardado de intencion.",e );
			response.put("message", "Error servicio de guardado de intencion.");
			response.put("error", e.getMessage() +" : " + e);
			response.put("success", Boolean.FALSE);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getIntentById(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		try {
			IntentDto intentDto = this.intentService.getIntentById(id);
			response.put("message", "Consulta intencion correctamente");
			response.put("data", intentDto);
			response.put("success", Boolean.TRUE);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}catch(Exception e) {
			log.error("Error consulta de intencion.",e );
			response.put("message", "Error consulta de intencion.");
			response.put("error", e.getMessage() +" : " + e);
			response.put("success", Boolean.FALSE);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<?>> getAllIntents() {
		List<IntentDto> intents = this.intentService.getListIntents();
		return ResponseEntity.ok(intents); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIntent(@Valid @PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		log.info("elimino intencion.{}", id);
		try {
			this.intentService.deleteIntent(id);
			response.put("message", "Intencion eliminada correctamente");
			response.put("data", id);
			response.put("success", Boolean.TRUE);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}catch(Exception e) {
			log.error("Error borrado de intencion.",e );
			response.put("message", "Error borrado de intencion.");
			response.put("error", e.getMessage() +" : " + e);
			response.put("success", Boolean.FALSE);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateIntent(@RequestBody IntentDto intentDto, @PathVariable Integer id) {
		IntentDto intent = this.intentService.updateIntent(intentDto, id);
		return ResponseEntity.ok(intent);
	}
	
	@GetMapping("/knowlege/{assistantId}")
	public ResponseEntity<?> getIntentKnowlege(@PathVariable Integer assistantId) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<IntentKnowlegeDto> intents = this.intentService.getListIntentKnowlege(assistantId);
			response.put("message", "Consulta correcta");
			response.put("data", intents);
			response.put("success", Boolean.TRUE);
			return ResponseEntity.ok(response); 
		}catch(Exception e) {
			log.error("Error busqueda de intencion conocimiento.",e );
			response.put("message", "Error busqueda de intencion conocimiento.");
			response.put("error", e.getMessage() +" : " + e);
			response.put("success", Boolean.FALSE);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
