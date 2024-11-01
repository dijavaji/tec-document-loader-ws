package ec.com.technoloqie.document.loader.api.commons.exception;

public class DocumentLoaderException extends RuntimeException{
	
	public DocumentLoaderException() {
        super();
    }
    
	public DocumentLoaderException (String msg, Throwable nested) {
        super(msg, nested);
    }
    
	public DocumentLoaderException (String message) {
        super(message);
    }
    
	public DocumentLoaderException(Throwable nested) {
        super(nested);
	}
	
	private static final long serialVersionUID = 1L;

}