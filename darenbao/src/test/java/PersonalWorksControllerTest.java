import com.club.framework.exception.BaseAppException;
import com.club.framework.util.HttpClientUtils;
import com.club.framework.util.JsonUtil;
import com.club.web.works.constants.WorksCategoryEnum;
import com.club.web.works.controller.PersonalWorksController;
import com.club.web.works.vo.PersonalWorksVo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lifei on 2016/9/4.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath*:/config/spring-*.xml","classpath*:/config/security/application-context*.xml"})
public class PersonalWorksControllerTest {
    // 模拟request,response
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
//    @Autowired
//    private PersonalWorksController personalWorksController;
//    @Before
//    public void setUp(){
//        request = new MockHttpServletRequest();
//        request.setCharacterEncoding("UTF-8");
//        response = new MockHttpServletResponse();
//    }
//    @Test
//    public void testSelectPageList(){
//        PersonalWorksVo personalWorksVo=new PersonalWorksVo();
//        personalWorksVo.setWorksCategory(WorksCategoryEnum.音乐.getName());
//        try {
//            List<PersonalWorksVo> list=personalWorksController.selectPersonalWorksList(personalWorksVo);
//            System.out.println(JsonUtil.toJson(list));
//        } catch (BaseAppException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IntrospectionException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
    @Test
    public void testSelectPageListByHttp() throws BaseAppException {
        String url="http://localhost:8080/darenbao/personalWorks/mobile/selectPersonalWorksPageList.do?worksCategory=music";
        String result=HttpClientUtils.get(url);
        System.out.println(result);
    }
}
