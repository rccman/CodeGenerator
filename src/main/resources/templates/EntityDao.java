package ${commPackage}.${databaseName}.dao;

import com.hzfh.base.entity.BaseDao;
import ${commPackage}.${databaseName}.entity.${className}Bean;
import ${commPackage}.${databaseName}.api.entity.${className}Param;
import org.springframework.stereotype.Repository;

/**
 * <p>Description: Dao</p>
 * <p>Company:hzjk</p>
 * @author ${author}
 * @date $!{time}
 */
@Repository
public interface ${className}Dao extends BaseDao<${className}Bean, ${className}Param> {
}