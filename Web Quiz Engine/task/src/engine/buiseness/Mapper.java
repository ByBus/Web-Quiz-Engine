package engine.buiseness;

public interface Mapper<T, U> {
    U mapToEntity(T objFrom);

    T mapToDTO(U objFrom);
}
