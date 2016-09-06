package com.club.web.util.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;


@Component("listenerScaner")
public class ListenerScaner {  
      
    protected final Logger logger = LoggerFactory.getLogger(ListenerScaner.class);
      
    private static final String RESOURCE_PATTERN = "/**/*.class";  
      
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();  
      
    private List<String> packagesList= new LinkedList<String>();  
     
    private List<TypeFilter> ignoreFilters = new ArrayList<>();
    
    private Map<Class<? extends IBaseListener>, List<TypeFilter>> typeFilters = new HashMap<>();
    
    private Map<Class<? extends IBaseListener>, Set<Class<? extends IBaseListener>>> classes = new HashMap<>();
      
    /** 
     * 构造函数 
     * @param packagesToScan 指定哪些包需要被扫描,支持多个包"package.a,package.b"并对每个包都会递归搜索 
     * @param annotationFilter 指定扫描包中含有特定注解标记的bean,支持多个注解 
     */  
	public ListenerScaner(String[] packagesToScan){  
        if (packagesToScan != null)
            for (String packagePath : packagesToScan)
                this.packagesList.add(packagePath);  
    }  
    
	@PostConstruct
    public void init() throws IOException, ClassNotFoundException{
		Set<ClassInfo> classes = loadClasses();
        initFilters(classes);
        initClasses(classes);
    }
	
	private Set<ClassInfo> loadClasses() throws IOException{
		Set<ClassInfo> set = new HashSet<>();
        for (String pkg : this.packagesList) {  
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +  
                    ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;  
            Resource[] resources = this.resourcePatternResolver.getResources(pattern);  
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);  
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);  
                    set.add(new ClassInfo(reader.getClassMetadata().getClassName(), reader, readerFactory));
                }  
            }  
        }
        return set;
	}
    
    @SuppressWarnings("unchecked")
	private void initFilters(Set<ClassInfo> classes) throws IOException, ClassNotFoundException{
    	TypeFilter filter = new AssignableTypeFilter(IBaseListener.class);
    	for (ClassInfo ci : classes) {
            if(filter.match(ci.reader, ci.readerFactory) && checkIgnore(ci.reader, ci.readerFactory) && !ci.className.equals(IBaseListener.class.getName())) {
            	Class<? extends IBaseListener> clazz = (Class<? extends IBaseListener>) Class.forName(ci.className);
            	try {
            		clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
			        if (logger.isInfoEnabled())
			        	logger.info("found listener: {}", clazz.getName());
	            	addTypeFilter(clazz, new AssignableTypeFilter(clazz));
				}
            }
		}
    }
    
    @SuppressWarnings("unchecked")
	private void initClasses(Set<ClassInfo> classes) throws IOException, ClassNotFoundException{
        this.ignoreFilters.add(new AnnotationTypeFilter(IgnoreListenerScaner.class));
        this.classes.clear();  
        for (ClassInfo ci : classes) {
            Class<? extends IBaseListener> key = matchesEntityTypeFilter(ci.reader, ci.readerFactory);
            if(key!=null && !ci.className.equals(key.getName())) {
                if (logger.isInfoEnabled())
                	logger.info("found class: {} --> {}", ci.className, key.getName());  
            	addClass(key, (Class<? extends IBaseListener>) Class.forName(ci.className));
            }
		}
    }
      
    /** 
     * 将符合条件的Bean以Class集合的形式返回 
     * @return 
     * @throws IOException 
     * @throws ClassNotFoundException 
     */  
    @SuppressWarnings("unchecked")
	public <T extends IBaseListener> Set<T> getClassSet(Class<T> listener) { 
    	Set<Class<? extends IBaseListener>> set = this.classes.get(listener);
    	Set<T> result = new HashSet<>();
    	if(set!=null)
        	for (Class<? extends IBaseListener> clz : set)
				try {
					result.add((T)clz.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
				}
        return result;  
    }  
    
    
    private void addTypeFilter(Class<? extends IBaseListener> listener, TypeFilter filter){
    	if(!typeFilters.containsKey(listener))
    		typeFilters.put(listener, new ArrayList<>());
		typeFilters.get(listener).add(filter);
    }
    
    private void addClass(Class<? extends IBaseListener> listener, Class<? extends IBaseListener> clazz){
    	if(!classes.containsKey(listener))
    		classes.put(listener, new HashSet<>());
    	classes.get(listener).add(clazz);
    }
      
    private boolean checkIgnore(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException{ 
    	for (TypeFilter ignoreFilter : this.ignoreFilters)
	    	if(ignoreFilter.match(reader, readerFactory))
	    		return false;
    	return true;
    }
  
    /** 
     * 检查当前扫描到的Bean含有任何一个指定的注解标记 
     * @param reader 
     * @param readerFactory 
     * @return 
     * @throws IOException 
     */  
    private Class<? extends IBaseListener> matchesEntityTypeFilter(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {  
    	if(!checkIgnore(reader, readerFactory))
    		return null;
        for (Entry<Class<? extends IBaseListener>, List<TypeFilter>> filters : this.typeFilters.entrySet())
        	for (TypeFilter filter : filters.getValue())
                if (filter.match(reader, readerFactory))
                    return filters.getKey();  
        return null;  
    }  
    
    private static class ClassInfo {
    	String className;
    	MetadataReader reader;
    	MetadataReaderFactory readerFactory;
		ClassInfo(String className, MetadataReader reader, MetadataReaderFactory readerFactory) {
			this.className = className;
			this.reader = reader;
			this.readerFactory = readerFactory;
		}
    }
  
}  