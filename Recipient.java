import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;

class Recipient {
    private String recipientNo;
    private Template template;
    private List<String> subsDataList; // 치환자 데이터 리스트

    public Recipient(Template template, String recipientNo) {
        this.recipientNo = recipientNo;
        this.template = template;
        subsDataList = new ArrayList<String>();
    }

    public String getRecipientNo() {
        return recipientNo;
    }

     public String getData(String field) {
        int index = template.getFields().indexOf(field);
        return subsDataList.get(index);
    }

    public boolean setData(String field, String data) {
        int index = template.getFields().indexOf(field);    // 치환자 데이터와 템플릿에 설정 된 치환자 필드의 유효성 검사
        if(index >= 0) {
            subsDataList.add(index, data);
            return true;
        }
        else {
            return false;
        }
    }

    public void printSubsStatus() {
        int cnt = template.getFieldsCount();

        for(int i = 0; i < cnt; i++) {
            System.out.print(template.getFields().get(i).toString() + ": " + subsDataList.get(i).toString());
            if(i != cnt-1)
                System.out.println(",");
        }
    }
}