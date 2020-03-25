import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import org.junit.Test;

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

    static class JField extends JsonField {
        @Override
        public String toString() {
            if (value.equals("001")) return "ABC";
            else return "NOTKNOWN";
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    static class JObject {
        JField field = new JField();
    }

    @Test
    public void testAdapter2() {
        var gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(JsonField.class, new JsonField.Adapter()).create();
        var obj = new JObject();
        obj.field.setValue("001");
        var json = gson.toJson(obj);
        System.out.println(json);
        var obj2 = gson.fromJson(json, JObject.class);
        System.out.println(obj2.field);
    }
}
