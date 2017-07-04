package tp.paw.khet.webapp.dto;

public class ExceptionDTO {

	private String message;
	
	public ExceptionDTO() {}
	
	public ExceptionDTO(final String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
