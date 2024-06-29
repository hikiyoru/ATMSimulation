import java.util.Map;

public interface ReadableWritable<T extends ATM> {
    void read(T t);
    void write(T t);
}
