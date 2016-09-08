import com.club.framework.exception.BaseAppException;
import com.club.framework.util.BeanUtils;
import com.club.framework.util.HttpClientUtils;
import com.club.framework.util.JsonUtil;
import com.club.web.util.IdGenerator;
import com.club.web.works.constants.WorksCategoryEnum;
import com.club.web.works.constants.WorksTypeEnum;
import com.club.web.works.vo.PersonalWorksVo;
import org.junit.Before;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by lifei on 2016/9/4.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath*:/config/spring-*.xml","classpath*:/config/security/application-context*.xml"})
public class PersonalWorksControllerTest {
    // 模拟request,response
//    private MockHttpServletRequest request;
//    private MockHttpServletResponse response;
    private PersonalWorksVo personalWorksVo;
//    @Autowired
//    private PersonalWorksController personalWorksController;
    @Before
    public void setUp(){
//        request = new MockHttpServletRequest();
//        request.setCharacterEncoding("UTF-8");
//        response = new MockHttpServletResponse();
        personalWorksVo=new PersonalWorksVo();
        personalWorksVo.setWorksId(1);
        personalWorksVo.setWorksCategory(WorksCategoryEnum.音乐.getName());
        personalWorksVo.setWorksTitle("测试" + System.currentTimeMillis());
        personalWorksVo.setWorksCover("http://o7o0uv2j1.bkt.clouddn.com/14684866748522e1d494.jpg");
        personalWorksVo.setWorksLink("http://o7o0uv2j1.bkt.clouddn.com/14684866748522e1d494.jpg");
        personalWorksVo.setWorksType(WorksTypeEnum.视频.getName());
    }
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
    @Test
     public void testCreate() throws Exception {
        String url="http://localhost:8080/darenbao/personalWorks/mobile/create.do";
        Map<String,Object> params=JsonUtil.toMap(personalWorksVo);
        String result=HttpClientUtils.post(url, params);
        System.out.println(result);
    }
    @Test
     public void testUpdate() throws BaseAppException {
        String url="http://localhost:8080/darenbao/personalWorks/mobile/update.do";
        Map<String,Object> params=JsonUtil.toMap(personalWorksVo);
        String result=HttpClientUtils.post(url, params);
        System.out.println(result);
    }
    @Test
    public void testDelete() throws BaseAppException {
        String url="http://localhost:8080/darenbao/personalWorks/mobile/delete.do";
        Map<String,Object> params=JsonUtil.toMap(personalWorksVo);
        String result=HttpClientUtils.post(url, params);
        System.out.println(result);
    }
    @Test
    public void select() throws BaseAppException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String url="http://localhost:8080/darenbao/personalWorks/mobile/select.do";
        personalWorksVo=new PersonalWorksVo();
        personalWorksVo.setWorksId(1);
        Map<String,Object> params= BeanUtils.convertBeanNotNull(personalWorksVo);
        String result=HttpClientUtils.get(url, params);
        System.out.println(result);
    }
}
