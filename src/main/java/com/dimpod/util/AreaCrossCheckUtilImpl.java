package com.dimpod.util;


import com.dimpod.model.Coordinates;
import com.dimpod.model.Feature;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AreaCrossCheckUtilImpl implements AreaCrossCheckUtil{
    private List<Feature> features = new ArrayList<>();


    @Override
    public boolean addFeature(Feature feature) {
        List<Coordinates> coordListNew = feature.getCoordinates();
        boolean ret = false;
        //Смотрим список всех ранее записаных feature и проверяем на пересечение
        for(Feature ft : features){
            List<Coordinates> coordList = ft.getCoordinates();
            boolean isCross = false;
            for(int i=0; i < coordListNew.size(); i++){
                for(int j = 0; j < coordList.size(); j++){
                    int indC1=i;
                    int indC2 = (i < coordListNew.size()-1)? i+1: 0;
                    int indC3=j;
                    int indC4 = (j < coordList.size()-1)? j+1: 0;

                    BigDecimal x1 = new BigDecimal(coordListNew.get(indC1).getX());
                    BigDecimal y1 = new BigDecimal(coordListNew.get(indC1).getY());
                    BigDecimal x2 = new BigDecimal(coordListNew.get(indC2).getX());
                    BigDecimal y2 = new BigDecimal(coordListNew.get(indC2).getY());
                    BigDecimal x3 = new BigDecimal(coordListNew.get(indC3).getX());
                    BigDecimal y3 = new BigDecimal(coordListNew.get(indC3).getY());
                    BigDecimal x4 = new BigDecimal(coordListNew.get(indC4).getX());
                    BigDecimal y4 = new BigDecimal(coordListNew.get(indC4).getY());
                    // Берем каждый отрезок для обоих объектов и проверяем на пересечение.
                    isCross  = crossSegment(x1, y1,x2, y2,x3, y3, x4, y4);
                    if(isCross){
                        System.out.println("New feature with ID: " +
                                feature.getId() + "  cross with feature ID: " + ft.getId());
                        break;
                    }
                }
                if(isCross) break;
            }

        }
        features.add(feature);
        return ret;
    }

    @Override
    public void addAllFeature(List<Feature> featureList) {
        if(featureList != null && featureList.size() > 0) features.addAll(featureList);
    }

    @Override
    public List crossCheck(Feature feature) {
        return null;
    }

    private boolean inPoly(Coordinates cr, List<Coordinates> coordList) {

        BigDecimal x = new BigDecimal(cr.getX());
        BigDecimal y = new BigDecimal(cr.getX());;

        int  npol = coordList.size();
        boolean c = false;
        for (int i = 0, j = npol - 1; i < npol; j = i++){
            BigDecimal ypi = new BigDecimal(coordList.get(i).getY());
            BigDecimal ypj = new BigDecimal(coordList.get(j).getY());
            BigDecimal xpi = new BigDecimal(coordList.get(i).getX());
            BigDecimal xpj = new BigDecimal(coordList.get(j).getX());

            BigDecimal vyr1 = ypj.subtract(ypi).multiply(x.subtract(xpi));
            BigDecimal vyr2 = xpj.subtract(xpi).multiply(y.subtract(ypi));
            BigDecimal vyr5 = ypj.subtract(ypi).multiply(x.subtract(xpi));
            BigDecimal vyr6 = xpj.subtract(xpi).multiply (y.subtract(ypi));
            if ((
                    (ypi.compareTo(ypj) < 0) && (ypi.compareTo(y) <= 0)
                            && (y.compareTo(ypj) <= 0) &&
                            (vyr1.compareTo(vyr2) > 0)
            ) || (
                    (ypi.compareTo(ypj) > 0) && (ypj.compareTo(y) <= 0)
                            && (y.compareTo(ypi) <= 0) &&
                            (vyr5.compareTo(vyr6) < 0)
            ))
                c = !c;
        }
        return c;
    }

    //Функция для проверки пересечения двух отрезков.
    public static boolean crossSegment(BigDecimal x1, BigDecimal y1,
                                       BigDecimal x2, BigDecimal y2,
                                       BigDecimal x3, BigDecimal y3,
                                       BigDecimal x4, BigDecimal y4) {

        BigDecimal Ua, Ub, Uc, Ud, numerator_a, numerator_b, denominator;
        BigDecimal zero = new BigDecimal(0);
        BigDecimal one = new BigDecimal(1);
        denominator=(y4.subtract(y3).multiply(x1.subtract(x2))
                .subtract(x4.subtract(x3).multiply(y1.subtract(y2))));

        Ua = (x1.multiply(y2).subtract(x2.multiply(y1))).multiply(x4.subtract(x3));
        Ub = (x3.multiply(y4).subtract(x4.multiply(y3))).multiply(x2.subtract(x1));
        Uc = (x1.multiply(y2).subtract(x2.multiply(y1))).multiply(y4.subtract(y3));
        Ud = (x3.multiply(y4).subtract(x4.multiply(y3))).multiply(y2.subtract(y1));
        if (denominator.compareTo(zero) == 0) {
            if ( Ua.subtract(Ub).compareTo(zero) == 0 &&
                    Uc.subtract(Ud).compareTo(zero) == 0) {
                //System.out.println("Отрезки пересекаются (совпадают)");
                return true;
            }else {
                //System.out.println("Отрезки не пересекаются (параллельны)");
                return false;
            }
        }else{
            numerator_a=((x4.subtract(x2)).multiply(y4.subtract(y3)))
                    .subtract(x4.subtract(x3)).multiply(y4.subtract(y2));
            //numerator_a=(x4-x2)*(y4-y3)-(x4-x3)*(y4-y2);
            numerator_b=((x1.subtract(x2)).multiply(y4.subtract(y2)))
                    .subtract(x4.subtract(x2)).multiply(y1.subtract(y2));

            //numerator_b=(x1-x2)*(y4-y2)-(x4-x2)*(y1-y2);
            Ua=numerator_a.divide(denominator, 1, RoundingMode.HALF_UP);
            Ub=numerator_b.divide(denominator, 1, RoundingMode.HALF_UP);

            if((Ua.compareTo(zero) >=0 && Ua.compareTo(one) <=0
                    && Ub.compareTo(zero) >=0 && Ub.compareTo(one) <=0)) {
                //System.out.println("Отрезки пересекаются");
                return true;
            }else{
                //System.out.println("Отрезки не пересекаются");
                return false;
            }
        }
    }


}
