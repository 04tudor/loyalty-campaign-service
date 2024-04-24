package md.maib.retail.model.exceptions;

public class CampaignActivationException extends Exception {
    public CampaignActivationException() {
        super();
    }

    public CampaignActivationException(String message) {
        super(message);
    }

    public CampaignActivationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CampaignActivationException(Throwable cause) {
        super(cause);
    }
}
