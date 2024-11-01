package ec.com.technoloqie.document.loader.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import ec.com.technoloqie.document.loader.api.service.IIntentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("${ec.com.technoloqie.document.loader.api.prefix}/intent")
@Slf4j
public class IntentRestController {
	
	@Autowired
	private IIntentService intentService;

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
			IntentDto intent = this.intentService.createIntent(intentDto);
			response.put("message", "Archivo subido correcto");
			response.put("data", intent);
			response.put("success", Boolean.TRUE);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		}catch(Exception e) {
			log.error("Error servicio de guardado de intencion.",e );
			response.put("message", "Error servicio de guardado de intencion.");
			response.put("error", e.getMessage() +" : " + e.getStackTrace());
			response.put("success", Boolean.FALSE);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.EXPECTATION_FAILED);
		}
		 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getIntentById(@PathVariable Integer id) {
		IntentDto intentDto = this.intentService.getIntentById(id);
		return ResponseEntity.ok(intentDto); 
	}
	
	@GetMapping
	public ResponseEntity<List<?>> getAllIntents() {
		List<IntentDto> intents = this.intentService.getListIntents();
		return ResponseEntity.ok(intents); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteIntent(@Valid @PathVariable Integer id) {
		this.intentService.deleteIntent(id);
		return ResponseEntity.ok("Intencion eliminada correctamente"); 
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateIntent(@RequestBody IntentDto intentDto, @PathVariable Integer id) {
		IntentDto intent = this.intentService.updateIntent(intentDto, id);
		return ResponseEntity.ok(intent);
	}

}
