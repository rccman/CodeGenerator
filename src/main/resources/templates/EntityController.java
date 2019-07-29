package ${commPackage}.${databaseName}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author ${author}
 * @date $!{time}
 */
@RestController
@RequestMapping("/${lowerName}/")
public class ${className}Rest{

    private final Logger logger = LoggerFactory.getLogger(${className}Controller.class);

    @Autowired
    private ${className}Service ${lowerName}Service;


}
