package md.maib.retail.usecase;

public class UseCaseProblemConflict {
    private final String message;

    public UseCaseProblemConflict(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}