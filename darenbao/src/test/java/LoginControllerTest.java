import com.club.framework.exception.BaseAppException;
import com.club.framework.util.HttpClientUtils;
import com.club.web.works.constants.WorksCategoryEnum;
import com.club.web.works.constants.WorksTypeEnum;
import com.club.web.works.vo.PersonalWorksVo;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2016/9/11.
 */
public class LoginControllerTest {
    String serverPath="http://localhost:8080/darenbao";
    String phoneCode;
    String verifyCode;
    @Before
    public void setUp(){
          phoneCode="18978833622";
//        request = new MockHttpServletRequest();
//        request.setCharacterEncoding("UTF-8");
//        response = new MockHttpServletResponse();

    }
    @Test
    public void verifyPhone() throws BaseAppException {
        String url=serverPath+"/client/mobile/verifyPhone.do?phoneCode="+phoneCode;
        String result= HttpClientUtils.get(url);
        System.out.println(result);
    }
}
