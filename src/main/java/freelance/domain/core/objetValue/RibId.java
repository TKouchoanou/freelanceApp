package freelance.domain.core.objetValue;

public record RibId(Long value) {
    @Override
    public String toString() {
        return value+"";
    }


}
