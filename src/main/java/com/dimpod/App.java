package com.dimpod;

import com.dimpod.dto.FeatureCollectionDto;
import com.dimpod.dto.FeatureDto;
import com.dimpod.model.Feature;
import com.dimpod.model.FeatureCollection;
import com.dimpod.util.*;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import java.util.List;

public class App {
 
public static void main(String[] args) {

        if(args.length < 1) {
                System.out.println("Input file name is required");
                return;
        }
        String inputFile = args[0];

        JsonFileParse jsonParse = new JsonFileParseImpl();
        AreaCrossCheckUtil crossCheck = new AreaCrossCheckUtilImpl();

        FeatureCollectionDto dto = jsonParse.fileParse(inputFile);
        FeatureCollection featureCollection = new FeatureCollection(dto);

        try(Session session = HibernateUtil.getSessionFactory().openSession()) {

                CriteriaQuery<Feature> criteriaQuery = session.getCriteriaBuilder().createQuery(Feature.class);
                criteriaQuery.from(Feature.class);
                List<Feature> featureList = session.createQuery(criteriaQuery).getResultList();
                //Читаем из базы ранее записанные feature
                crossCheck.addAllFeature(featureList);

                session.beginTransaction();
                session.persist(featureCollection);
                for (FeatureDto ft : dto.getFeatures()) {
                        Feature feature = new Feature(ft);
                        feature = (Feature) session.merge(feature);
                        System.out.println("Saved feature ID : " + feature.getId());
                        //заппись нового feature с проверкой на пересечение
                        crossCheck.addFeature(feature);
                }

                session.getTransaction().commit();

                int s = featureList.size();
        }

    }
    
}
