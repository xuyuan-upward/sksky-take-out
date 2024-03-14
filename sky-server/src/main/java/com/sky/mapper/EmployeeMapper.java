package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Insert("insert into employee(name,username, password, phone, sex, id_number, create_time, update_time, create_user, update_user,status)"
            + "valueS"
            + "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    void insert(Employee employee);

    /**
     * 分页查询
     *
     * @param employeeMapper
     * @return
     */

    Page<Employee> pageQuery(EmployeePageQueryDTO employeeMapper);

    /**
     * 员工的启用和禁用
     * @param status
     * @param id
     */


    /**
     * 根据ID进行员工信息查询
     * @param id
     * @return
     */
    @Select("select * from sky_take_out.employee where id=#{id};")
    Employee getById(Long id);


    void update(Employee employee);
}
