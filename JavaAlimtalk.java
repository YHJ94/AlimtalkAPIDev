import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JavaAlimtalk {

    public static void main(String[] args) {
        Template template = new Template("", "");
        template.setFields("name");
        template.setFields("year");

        List<Recipient> rlist = new ArrayList<Recipient>();

        /// 이벤트 처리해서 list에 수신자 목록 추가
        Recipient rp = new Recipient(template, "");
        rp.setData("name", "");
        rp.setData("year", "1");
        rlist.add(rp);

        Recipient rp2 = new Recipient(template, "");
        rp2.setData("name", "");
        rp2.setData("year", "1");
        rlist.add(rp2);

        JavaAlimtalk program = new JavaAlimtalk();
        String body = program.generateJson(rlist, template);    // 최종 Json request body 생성
        program.callRequest(body);
    }

    public void callRequest(String body) {
        try {
            int returnCode = -1;

            URL url = new URL("https://api-alimtalk.cloud.toast.com/alimtalk/v1.2/appkeys/{AppKey}/messages");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json;charsets;utf-8");
            conn.setRequestProperty("X-Secret-Key","{SecretKey}");
            conn.setConnectTimeout(600);
            conn.setReadTimeout(1000);

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes("UTF-8"));
            os.flush();

            returnCode = conn.getResponseCode();
            if(returnCode == 200) {
                InputStream in = conn.getInputStream();
                byte[] buf = new byte[1024];
                in.read(buf);
                String output = new String(buf, "UTF-8");
                System.out.println(output);
            }
        }
        catch(MalformedURLException e) {
            System.out.println("url Connection error");
        }
        catch(Exception e) {
            System.out.println("io exception");
        }
    }

    public String generateJson(List<Recipient> rlist, Template template) {
        JsonBuilder reqBody = new JsonBuilder();

        List<String> recipientList = new ArrayList<String>();

        int cnt = rlist.size();
        for(int i = 0; i < cnt; i++) {
            // 치환 목록 json 생성
            JsonBuilder param = new JsonBuilder();
            param.put("name", rlist.get(i).getData("name"));
            param.put("year", "1");
            param.buildJsonData();
            // 수신자 목록 json 생성
            JsonBuilder recipient = new JsonBuilder();
            recipient.put("recipientNo", rlist.get(i).getRecipientNo());
            recipient.put("templateParameter", param); // 수신자는 치환 목록 json을 포함
            recipient.buildJsonData();

            recipientList.add(recipient.getJsonData()); // reqbody에 리스트 추가하기 위해 저장
        }

        reqBody.put("plusFriendId", template.getPlusFriendId());
        reqBody.put("templateCode", template.getTemplateCode());
        reqBody.putList("recipientList", recipientList); // 생성한 리스트 추가
        reqBody.buildJsonData();

        return reqBody.getJsonData();        
    }
}





