package modele;

import java.util.List;

public interface InterfModele<T> {
    public void create(T o) throws Exception;
    public T read(int id) throws Exception;
    public void update(T o, T n) throws Exception;
    public void del(T o) throws Exception;
    public List<T> all();
    public void close();
}
