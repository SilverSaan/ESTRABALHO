package pt.estgp.es.services;

import java.util.List;

public interface ServicesInterface<T> {

    public List<T> findAll();

    public void create(T incmng);

    public void update(long id, T incmng);

    public void remove(long id);


}
