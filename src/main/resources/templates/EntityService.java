package ${commPackage}.${databaseName}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzfh.base.entity.PageBean;
import com.hzfh.base.utils.ResultUtil;
import com.hzfh.base.entity.RestResult;
import java.util.List;
import ${commPackage}.${databaseName}.entity.${className}Bean;
import ${commPackage}.${databaseName}.api.entity.${className}Param;
/**
 * <p>Description: Service</p>
 * <p>Company:hzjk</p>
 * @author ${author}
 * @date $!{time}
 */
public interface ${className}Service {

    int addEntity(${className}Bean entity)throws Exception;

    void editEntity(${className}Bean entity)throws Exception;

    void deleteEntity(int id)throws Exception;

    ${className}Bean findById(int id)throws Exception;

    PageBean findPageList(${className}Param entity)throws Exception;

    List<${className}Bean> findList(${className}Param entity)throws Exception;
}
