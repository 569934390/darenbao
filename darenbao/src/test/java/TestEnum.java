import com.club.framework.util.JsonUtil;
import com.club.web.works.constants.WorksCategoryEnum;
import com.club.web.works.vo.PersonalWorksVo;

/**
 * Created by lifei on 2016/9/4.
 */
public class TestEnum {
    public static void main(String[] args) {
        PersonalWorksVo personalWorksVo=new PersonalWorksVo();
        personalWorksVo.setWorksCategory(WorksCategoryEnum.音乐.getName());
        System.out.println(JsonUtil.toJson(personalWorksVo));
    }
}

