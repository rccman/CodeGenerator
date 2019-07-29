package ${commPackage}.${databaseName}.mapper;

import java.util.List;

/**
 * @Description: Mapper
 * @author ${author}
 * @date $!{time}
 */

public interface ${className}Mapper {

    int insert(${className}Po record);

    int updateByPrimaryKey(${className}Po entity);

    List<${className}Po> selectByWhere(${className}Po entity);

    ${className}Po selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);
}