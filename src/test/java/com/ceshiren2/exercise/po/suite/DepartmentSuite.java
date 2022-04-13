package com.ceshiren2.exercise.po.suite;

import com.ceshiren2.exercise.po.BaseTest;
import com.ceshiren2.exercise.po.depart.AddDepartTest;
import com.ceshiren2.exercise.po.depart.DeleteDepartTest;
import com.ceshiren2.exercise.po.depart.GetDepartTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * @author wyl
 * @description suite测试套件
 * @create 2022-04-13 22:19
 */
@Suite
@SuiteDisplayName("部门相关suite套件测试")
@SelectClasses({
        AddDepartTest.class
        , GetDepartTest.class
        , DeleteDepartTest.class
})
//直接选择包未指定执行顺序
//@SelectPackages("com.ceshiren2.exercise.po.depart")
public class DepartmentSuite extends BaseTest {

}
