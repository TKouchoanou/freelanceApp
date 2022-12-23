package freelance.domain.models.objetValue;

public record RibId(Long value) {
    @Override
    public String toString() {
        return value+"";
    }


}
