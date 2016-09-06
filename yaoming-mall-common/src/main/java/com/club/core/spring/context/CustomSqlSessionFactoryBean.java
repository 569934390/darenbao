package com.club.core.spring.context;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

/**
 * Created by lyhcn on 15/6/19.
 */
public class CustomSqlSessionFactoryBean extends SqlSessionFactoryBean {

    @Override
    public void setMapperLocations(Resource[] mapperLocations) {
        for (Resource resource : mapperLocations){
            System.out.println(resource.getFilename());
        }
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] fileRources = new Resource[0];
        try {
//            fileRources = patternResolver.getResources("com/club/web/privilege/db/dao/xml/StaffTMapper.xml");
            fileRources = patternResolver.getResources("classpath*:com/club/**/*.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Resource r : fileRources){
            System.out.println(r.getFilename());
        }
        super.setMapperLocations(mapperLocations);
    }

}
