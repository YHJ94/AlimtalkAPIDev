import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;

class Template {

    private String templateCode;
    private String plusFriendId;
    private List<String> subsFieldList; // 치환자 필드 리스트

    public Template(String plusFriendId, String templateCode) {
        this.templateCode = templateCode;
        this.plusFriendId = plusFriendId;
        subsFieldList = new ArrayList<String>();
    }

    public String getPlusFriendId() {
        return plusFriendId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setFields(String field) {
        subsFieldList.add(field);
    }

    public List<String> getFields() {
        return subsFieldList;
    }

    public int getFieldsCount() {
        return subsFieldList.size();
    }

    public void printTemplateStatus() {
        // 현재 템플릿의 status 출력. 디버깅 용 메소드이며 실제 서비스 사용 코드 아님
        int cnt = getFieldsCount();

        System.out.println("plusFriendId : " + plusFriendId);
        System.out.println("templateCode : " + templateCode);
        System.out.println("Subs fields : ");
        for(int i = 0; i < cnt; i++) {
            System.out.println(subsFieldList.get(i).toString());
        }
    }
}