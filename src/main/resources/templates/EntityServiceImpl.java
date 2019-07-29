package ${commPackage}.${databaseName}.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @Description:
 * @author ${author}
 * @date $!{time}
 */
@Service
public class ${className}ServiceImpl implements ${className}Service{

    private static Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);
    @Autowired
    private ${className}Mapper ${lowerName}Mapper;


}
