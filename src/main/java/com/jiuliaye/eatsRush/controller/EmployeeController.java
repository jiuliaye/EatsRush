package com.jiuliaye.eatsRush.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiuliaye.eatsRush.common.R;
import com.jiuliaye.eatsRush.entity.Employee;
import com.jiuliaye.eatsRush.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {    //这种写法支持直接把请求数据封装到Employee实例中

        //1、将页面提交（传入）的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());  //构造一个equal条件，即SELECT * FROM employee WHERE username = ?;语句，Employee::getUsername最终会定位到employee表中的username字段，employee.getUsername()为?的值
        Employee emp = employeeService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果，便于后续进行判断放行资源
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);  //重定向是前端的任务，只要前端接收到R.success中的code=1，就会进行重定向，并且，这里return的是查询出来的emp，而不是传入的请求数据employee
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");   //同样的，重定向是前端做的
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());

        //根据产品原型描述：设置初始密码123456，需要进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        /*
        被 @TableField(fill = FieldFill.INSERT) 代替：
            employee.setCreateTime(LocalDateTime.now());
            employee.setUpdateTime(LocalDateTime.now());
            Long empId = (Long) request.getSession().getAttribute("employee");    //获得当前登录用户的id
            employee.setCreateUser(empId);
            employee.setUpdateUser(empId);
        */

        employeeService.save(employee); //实际上调用的是 this.getBaseMapper().insert(entity)

        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询（以分页的方式来展示列表数据）
     *
     * @param page     //当前页码
     * @param pageSize //分页大小
     * @param name     //模糊查询
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        //1、构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //2、构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //3、添加过滤条件（模糊匹配）
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //4、添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //5、执行查询
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo); //object为一个Page实例。前端会自己去取pageInfo.records()，就是当前页的数据。pageInfo.total()，就是总记录数。或者这样说，根据前端需求，我们需要返回一个包含records和total的对象，而刚好MyBatisPlus的Page对象就封装了这两个属性，我们可以直接返回这个Page对象
    }

    /**
     * 根据id修改员工信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为：{}", id);

        //Long empId = (Long)request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId);

        employeeService.updateById(employee);   //例如，将某个员工的status=1修改为0，就是传入一个employee，这个employee包含id和新的status，由employeeService根据ID进行update

        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }
}
