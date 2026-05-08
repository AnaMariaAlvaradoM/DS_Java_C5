package shopSystem.Clase3.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import shopSystem.Clase3.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryCImpl implements ProductoRepositoryC {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Producto> buscarConFiltros(String nombre, Double precioMin, Double precioMax) {
        //! Paso 1
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        //! Paso 2
        CriteriaQuery<Producto> query = cb.createQuery(Producto.class);
        //! Paso 3
        Root<Producto> root = query.from(Producto.class);

        List<Predicate> predicates = new ArrayList<>();

        //? Condición 1: Si llegó
        if(nombre != null && !nombre.isBlank()){
            predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }
        //? Condición 2: precio min, solo si llegó
        if(precioMin != null){
            //* greaterThanOrEqualTo() → >=
            predicates.add(cb.greaterThanOrEqualTo(root.get("precio"), precioMin));
        }
        //? Condición 3: precio max, solo si llegó
        if(precioMax != null){
            //* greaterThanOrEqualTo() → >=
            predicates.add(cb.lessThanOrEqualTo(root.get("precio"), precioMax));
        }

        //! Paso 5:
        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
