package euasepmigration;

import java.io.Serializable;

@FunctionalInterface
public interface Task<T, R> extends Serializable {
    R executeTask(T t);
}
