package com.HabitApp.HabitApp.Habit;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class HabitRepositoryAdditionalImpl implements HabitRepositoryAdditional{

    @PersistenceContext
    private EntityManager em;


    private static final Set<String> ALLOWED_FIELDS = Set.of(
            "name", "category", "equipment", "location",
            "normalDurationMinutes", "maxDurationMinutes", "minDurationMinutes",
            "recurrenceType", "recurrenceValue");

    @Override
    public List<Object> findByDistinctColValues(String colName) {

        if (!ALLOWED_FIELDS.contains(colName)) {
            throw new IllegalArgumentException("Invalid column name: " + colName);
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object> cq = cb.createQuery(Object.class);


        Root<Habit> root = cq.from(Habit.class);

        Path<?> colPath = root.get(colName);



        cq.select(colPath)
                .distinct(true)
                .where(cb.isNotNull(colPath));


        return  em.createQuery(cq).getResultList();
    }



}
