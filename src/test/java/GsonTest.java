import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import cn.yhsb.cjb.service.Data;
import cn.yhsb.cjb.service.JsonAdapter;
import cn.yhsb.cjb.service.JsonField;

public class GsonTest {

    static class BigNumber {
        public BigDecimal decimal;
        public BigInteger integer;
    }

    @Test
    public void testBigNumber() {
        var bigNum = new BigNumber();
        bigNum.decimal = new BigDecimal("3.33");
        bigNum.integer = new BigInteger("12344532234545");

        var json = new GsonBuilder().serializeNulls().create().toJson(bigNum);
        System.out.println(json);

        var bigNum2 = new Gson().fromJson(json, BigNumber.class);
        System.out.println(bigNum2.decimal);
        System.out.println(bigNum2.integer);
    }

    static class MyField {
        String code;
    }

    static class MyObject {
        MyField field = new MyField();
    }

    static class MyFieldAdapter implements JsonAdapter<MyField> {
        @Override
        public JsonElement serialize(MyField src, Type typeOfSrc,
                JsonSerializationContext context) {
            return new JsonPrimitive(src.code);
        }

        @Override
        public MyField deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
            var field = new MyField();
            field.code = json.getAsString();
            return field;
        }
    }

    @Test
    public void testAdapter() {
        var gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(MyField.class, new MyFieldAdapter()).create();
        var obj = new MyObject();
        obj.field.code = "001";
        var json = gson.toJson(obj);
        System.out.println(json);
        var obj2 = gson.fromJson(json, MyObject.class);
        System.out.println(obj2.field.code);
    }

    public static class JField extends JsonField {
        @Override
        public String toString() {
            if (value.equals("001")) return "ABC";
            else return "NOTKNOWN";
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class JObject extends Data {
        JField field = new JField();
    }

    @Test
    public void testAdapter2() {
        var obj = new JObject();
        obj.field.setValue("001");
        var json = Data.getGson().toJson(obj);
        System.out.println(json);
        var obj2 = Data.getGson().fromJson(json, JObject.class);
        System.out.println(obj2.field);
    }

    static class JWrapper<T extends Data> extends Data {
        ArrayList<T> datas = new ArrayList<T>();
    }

    public static class CbState2 extends JsonField {
        @Override
        public String getName() {
            switch (value) {
                case "0":
                    return "未参保";
                case "1":
                    return "正常参保";
                case "2":
                    return "暂停参保";
                case "4":
                    return "终止参保";
                default:
                    return "未知值: " + value;
            }
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Cbxx2 {
        // @SerializedName("aac008")
        CbState2 cbState;
    }

    @Test
    public void testAdapter3() {
        var wrapper = new JWrapper<JObject>();
        var obj = new JObject();
        obj.field.setValue("001");
        wrapper.datas.add(obj);
        var json = Data.getGson().toJson(wrapper);
        System.out.println(json);
        
        var type = TypeToken.getParameterized(JWrapper.class, JObject.class).getType();
        var obj2 = Data.getGson().fromJson(json, type);
        System.out.println(obj2.getClass());
        System.out.println(obj2);

        // json = "{\"aac008\":\"4\"}";
        json = "{\"cbState\":\"4\"}";
        var cbxx = new Cbxx2();
        var cbState = new CbState2();
        cbState.setValue("3");
        cbxx.cbState = cbState;
        json = Data.getGson().toJson(cbxx);
        System.out.println(json);
        json = "{\"cbState\":\"4\"}";
        cbxx = Data.getGson().fromJson(json, Cbxx2.class);
        System.out.println(cbxx.cbState);
    }
}
