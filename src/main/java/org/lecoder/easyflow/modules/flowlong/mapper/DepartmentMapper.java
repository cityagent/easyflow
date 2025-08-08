package org.lecoder.easyflow.modules.flowlong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lecoder.easyflow.modules.flowlong.dto.DepartmentDto;
import org.lecoder.easyflow.modules.flowlong.entity.Department;

import java.util.List;

/**
 * <p>
 * 企微部门
 * </p>
 *
 * @author lh
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<DepartmentDto> selectList(DepartmentDto department);
}
