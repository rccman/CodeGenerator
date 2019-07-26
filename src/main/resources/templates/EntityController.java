package ${commPackage}.${databaseName}.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import com.hzfh.base.entity.RestResult;
import com.hzfh.base.entity.PageBean;
import com.hzfh.base.utils.ResultUtil;
import io.swagger.annotations.*;
import ${commPackage}.${databaseName}.service.${className}Service;
import ${commPackage}.${databaseName}.entity.${className}Bean;
import ${commPackage}.${databaseName}.api.entity.${className}Param;
/**
 * <p>Description: </p>
 * <p>Company:hzjk</p>
 * @author ${author}
 * @date $!{time}
 */
@Api(tags = "${className}控制器")
@Controller
@RequestMapping("/${lowerName}/")
public class ${className}Controller{

    private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);

    @Autowired
    private ${className}Service ${lowerName}Service;

    /**
     * @Description: 分页条件查询
     * @author ${author}
     */
    @ApiOperation(value = "分页条件查询${className}列表", tags = {"${className}控制器"}, notes = "分页条件查询${className}列表", response = ${className}Bean.class, code = 0)
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = ${className}Bean.class)})
    @ResponseBody
    @RequestMapping(value="findPageList", method = RequestMethod.POST)
    public RestResult findPageList(@RequestBody @Validated @ApiParam(value = "条件对象", name = "json格式")
                                               ${className}Param entity )throws Exception{
        PageBean pageList = ${lowerName}Service.findPageList(entity);
        return ResultUtil.success(pageList);
    }
    /**
     * @Description: 根据ID查询数据
     * @author ${author}
     */
    @ApiOperation(value = "根据ID查询数据", tags = {"${className}控制器"}, notes = "根据ID查询数据", response = ${className}Bean.class, code = 0)
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = ${className}Bean.class)})
    @ResponseBody
    @RequestMapping(value="findById", method = RequestMethod.POST)
    public RestResult findPageList(@RequestBody @Validated @ApiParam(value = "条件对象", name = "json格式")
                                               ${className}Bean entity )throws Exception{
        return ResultUtil.success(${lowerName}Service.findById(entity.getId()));
    }
    /**
     * @Description: 新增
     * @author ${author}
     */
    @ApiOperation(value = "新增", tags = {"${className}控制器"}, notes = "新增", response = RestResult.class, code = 0)
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = RestResult.class)})
    @ResponseBody
    @RequestMapping(value="addEntity", method = RequestMethod.POST)
    public RestResult addEntity(@RequestBody @Validated @ApiParam(value = "条件对象", name = "json格式")
                                               ${className}Bean entity )throws Exception{
        return ResultUtil.success(${lowerName}Service.addEntity(entity));
    }
    /**
     * @Description: 编辑
     * @author ${author}
     */
    @ApiOperation(value = "编辑", tags = {"${className}控制器"}, notes = "编辑", response = RestResult.class, code = 0)
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = RestResult.class)})
    @ResponseBody
    @RequestMapping(value="editEntity", method = RequestMethod.POST)
    public RestResult editEntity(@RequestBody @Validated @ApiParam(value = "条件对象", name = "json格式")
                                               ${className}Bean entity )throws Exception{
        ${lowerName}Service.editEntity(entity);
        return ResultUtil.success();
    }
    /**
     * @Description: 删除
     * @author ${author}
     */
    @ApiOperation(value = "删除", tags = {"${className}控制器"}, notes = "删除", response = RestResult.class, code = 0)
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = RestResult.class)})
    @ResponseBody
    @RequestMapping(value="deleteEntity", method = RequestMethod.POST)
    public RestResult deleteEntity(@RequestBody @Validated @ApiParam(value = "条件对象", name = "json格式")
                                               ${className}Bean entity )throws Exception{
        ${lowerName}Service.deleteEntity(entity.getId());
        return ResultUtil.success();
    }

}
