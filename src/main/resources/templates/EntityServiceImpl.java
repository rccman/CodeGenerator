package ${commPackage}.${databaseName}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.hzfh.base.utils.JsonUtils;
import com.hzfh.base.entity.BaseService;
import com.hzfh.base.entity.PageBean;
import com.hzfh.base.exception.RestException;
import java.util.List;
import ${commPackage}.${databaseName}.dao.${className}Dao;
import ${commPackage}.${databaseName}.service.${className}Service;
import ${commPackage}.${databaseName}.entity.${className}Bean;
import ${commPackage}.${databaseName}.api.entity.${className}Param;
/**
 * <p>Description: Model</p>
 * <p>Company:hzjk</p>
 * @author ${author}
 * @date $!{time}
 */
@Service
public class ${className}ServiceImpl extends BaseService<${className}Bean,${className}Param,${className}Dao> implements ${className}Service{

    private final static Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);
    @Autowired
    private ${className}Dao ${lowerName}Dao;

    @Override
    public int addEntity(${className}Bean ${lowerName}Bean)throws Exception {
        try {
            super.addEntity(${lowerName}Bean);
            return ${lowerName}Bean.getId();
        } catch (Exception e) {
            throw new RestException(990301,e);
        }
    }
    @Override
    public void editEntity(${className}Bean ${lowerName}Bean)throws Exception {
        try {
            super.editEntity(${lowerName}Bean);
        } catch (Exception e) {
            throw new RestException(990302,e);
        }
    }
    @Override
    public void deleteEntity(int id)throws Exception {
        try {
            super.deleteEntity(id);
        } catch (Exception e) {
            throw new RestException(990303,e);
        }
    }

    @Override
    public PageBean findPageList(${className}Param ${lowerName}Param)throws Exception {
        try {
            return super.getPageBean(${lowerName}Param);
        } catch (Exception e) {
            throw new RestException(990304,e);
        }
    }


    @Override
    public List<${className}Bean> findList(${className}Param ${lowerName}Param)throws Exception {
        try {
            return m.findByWhere(${lowerName}Param);
        } catch (Exception e) {
            throw new RestException(990304,e);
        }
    }

    @Override
    public ${className}Bean findById(int id)throws Exception {
       try {
            return m.findById(id);
       } catch (Exception e) {
            throw new RestException(990304,e);
       }
    }
}
