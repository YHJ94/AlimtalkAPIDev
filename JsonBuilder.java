import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;
import java.util.Iterator;

class JsonBuilder {
    private StringBuilder sb;   // Json 데이터를 담을 인스턴스
    private int isBuilt;    // 이미 빌드된 Json 데이터인지 확인하기 위한 필드 변수

    public JsonBuilder() {
        sb = new StringBuilder();
        isBuilt = 0;
    }

    public String wrap(String data) {
        // 모든 input 데이터는 쌍따옴표로 래핑되어 있어야 함
        String doubleQuote = "\"";
        return doubleQuote + data + doubleQuote;
    }

    public void put(String field, String data) {
        sb.append(wrap(field));
        sb.append(" : ");
        sb.append(wrap(data));
        sb.append(", ");
    }

    public void put(String field, JsonBuilder data) {
        sb.append(wrap(field));
        sb.append(" : ");
        if(data.isDataBuilt())
            sb.append(data.getJsonData());  // 이미 build 된 Json 데이터라면 그대로 삽입 
        else
            sb.append(data.buildJsonData());  //  field : { field : data } 식으로 put하려면 build 된 Json 데이터여야 한다
        sb.append(", ");
    }

    public void putList(String field, List<String> list) {
        sb.append(wrap(field));
        sb.append(" : ");
        sb.append("[ ");

        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            sb.append(iterator.next());

            if(iterator.hasNext())      // 다음 요소가 존재하면 콤마를 삽입하고 다음 요소가 없으면 콤마 삽입 안한다
                sb.append(", ");
        }

        sb.append(" ]");
        sb.append(", ");
    }

    public String buildJsonData() {
        // 최종 Json 데이터 형식으로 생성하기 위한 메소드
        sb.deleteCharAt(sb.lastIndexOf(","));   // 마지막 콤마 삭제
        sb.deleteCharAt(sb.lastIndexOf(" "));   // 마지막 공백 삭제
        sb.insert(0, "{ "); // 양 끝에 대괄호로 래핑
        sb.insert(sb.length(), " }");
        isBuilt = 1;    // 빌드된 Json 데이터로 표시
        return sb.toString();
    }

    public String getJsonData() {
        // 빌드하지 않고 Json 데이터를 호출하기 위한 메소드
        return sb.toString();
    }

    public String printJsonData() {
        // 디버깅용 메소드
        return sb.toString();
    }
    
    public boolean isDataBuilt() {
        if(isBuilt == 0)
            return false;
        else
            return true;                
    }
}