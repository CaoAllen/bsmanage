
package com.spring.example.utils;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.thoughtworks.xstream.io.json.JettisonStaxWriter;

/**
 *
 * @author Allen Cao
 */
public final class DTOUtils {

    private static ModelMapper INSTANCE = new ModelMapper();
    
    private DTOUtils() {
        throw new InstantiationError( "Must not instantiate this class" );
    }

    public static <S, T> T map(S source, Class<T> targetClass) {
        return INSTANCE.map(source, targetClass);
    }

    public static <S, T> void mapTo(S source, T dist) {
        INSTANCE.map(source, dist);
    }

    public static <S, T> void mapTo(S source, T dist, PropertyMap<S,T> propertyMap) {
    	if(propertyMap != null){
    		INSTANCE = new ModelMapper();
            INSTANCE.addMappings(propertyMap);
            INSTANCE.validate();
    	}
        INSTANCE.map(source, dist);
    }

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
            T target = INSTANCE.map(source.get(i), targetClass);
            list.add(target);
        }

        return list;
    }

    public static <S, T> Page<T> mapPage(Page<S> source, Class<T> targetClass) {
        List<S> sourceList = source.getContent();

        List<T> list = new ArrayList<>();
        for (int i = 0; i < sourceList.size(); i++) {
            T target = INSTANCE.map(sourceList.get(i), targetClass);
            list.add(target);
        }

        return new PageImpl<>(list, new PageRequest(source.getNumber(), source.getSize(), source.getSort()),
                source.getTotalElements());
    }
}
