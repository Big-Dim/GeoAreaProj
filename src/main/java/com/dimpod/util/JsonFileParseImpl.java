package com.dimpod.util;

import com.dimpod.dto.FeatureCollectionDto;
import com.dimpod.dto.PropertyDto;
import com.dimpod.model.FeatureCollection;
import com.google.gson.*;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsonFileParseImpl implements JsonFileParse{
    //парсинг json файла
    public FeatureCollectionDto fileParse(String inputFile){
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(inputFile));
            JsonObject parser = JsonParser.parseReader(reader).getAsJsonObject();

            FeatureCollectionDto featureCollection = gson.fromJson(parser, FeatureCollectionDto.class);

            JsonArray jarr = parser.getAsJsonArray("features");
            for (int i = 0; i < jarr.size(); i++){
                JsonElement je = jarr.get(i);
                JsonObject properties = je.getAsJsonObject().getAsJsonObject("properties");

                Set<PropertyDto> propDtoSet = new HashSet<>();
                for (Map.Entry<String, JsonElement> je1 : properties.entrySet()){
                    JsonElement o = je1.getValue();
                    String val = (o.isJsonNull()) ? null  : o.getAsString();
                    propDtoSet.add(new PropertyDto(je1.getKey(), val));
                }
                featureCollection.getFeatures().get(i).setPropDtoList(propDtoSet);

            }
            reader.close();
            System.out.println("input File was parsed" );
            return featureCollection;
        }catch(Exception e){
            System.out.println("File ERROR: " + e.getMessage());
            return null;
        }

    }

}
