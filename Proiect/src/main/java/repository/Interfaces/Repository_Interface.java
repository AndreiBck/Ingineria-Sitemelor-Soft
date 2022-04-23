package repository.Interfaces;

// jdbc:sqlite:C:\Users\Andrei 0920\MPP\DataBasesMPP\ClassProjectMPP

import java.util.Collection;

public interface Repository_Interface<Tid,T> {
        void add(T elem);
        void delete(T elem);
        void update(T elem, Tid id);
        T findById(Tid id);
        Iterable<T> findAll();
        Collection<T> getAll();
}
