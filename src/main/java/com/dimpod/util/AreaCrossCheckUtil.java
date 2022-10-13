package com.dimpod.util;

import com.dimpod.model.Feature;

import java.util.List;

public interface AreaCrossCheckUtil {
    boolean addFeature(Feature feature);
    void addAllFeature( List<Feature> featureList);

    List crossCheck(Feature feature);


}
