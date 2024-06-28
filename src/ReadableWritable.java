import java.util.Map;

public interface ReadableWritable<T extends ATM, J extends Map<String, Card>> {
    void read(T t);
    void read(J j);
    void write(T t);
    void write(J j);
}
