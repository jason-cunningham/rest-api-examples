package expense.das;


import java.util.List;

/**
 * Created by jcunningham on 3/6/16.
 */
public interface DAO<T> {
    public String create(T data) throws Exception;
    public String update(String id, T data) throws Exception;
    public List<T> find(String id) throws Exception;
    public List<T> findAll() throws Exception;
    public void delete(String id) throws Exception;
}
