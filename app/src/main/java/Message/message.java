package Message;

/**
 * Created by 张恒 on 2020/11/29 0029.
 */
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class message {
    private String phonenum;
    private String nums;
    public message(String phonenum,String nums){
        this.phonenum=phonenum;
        this.nums=nums;
    }

    public String MessageMain(){
        int i=Integer.valueOf(nums);
        String str="";
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G2pJWTtgZdy2CbGnZj5", "rQ1UbT094HNUbFXS36fPDTHlQXZAkC");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phonenum);
        request.putQueryParameter("SignName", "星曦向荣App");
        request.putQueryParameter("TemplateCode", "SMS_205606727");
        request.putQueryParameter("TemplateParam", "{\"code\":"+i+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            //System.out.println(response.getData());
            str=response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
            str=String.valueOf(e);
        } catch (ClientException e) {
            e.printStackTrace();
            str=String.valueOf(e);
        }
        String messageString="";
        if (str.indexOf("\"Message\":\"OK\"")>1||str.indexOf("\"Code\":\"OK\"")>1) {
            messageString="发送成功";
        }else if (str.indexOf("\"Message\":\"触发分钟级流控Permits:1\"")>1) {
            messageString="发送频繁，请稍后再试";
        }
        else {
            messageString="发送失败";
        }
        return messageString;
    }
}
