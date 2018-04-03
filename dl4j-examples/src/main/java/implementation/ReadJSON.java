package implementation;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadJSON {

    public static void main(String[] args) {
        try {
            JsonParser parser = new JsonParser();//创建JSON解析器
            JsonObject object = (JsonObject) parser.parse(new FileReader("/home/mm/TestReadJSON/test.json"));  //创建JsonObject对象
            System.out.println("gid=" + object.get("gid").getAsString());  //将Json数据转化为String型的数据
            System.out.println("increPer=" + object.get("increPer").getAsString());
            System.out.println("increase=" + object.get("increase").getAsString());
            System.out.println("name=" + object.get("name").getAsString());
            System.out.println("todayStartPri=" + object.get("todayStartPri").getAsString());
            System.out.println("yestodEndPri=" + object.get("yestodEndPri").getAsString());
            System.out.println("nowPri=" + object.get("nowPri").getAsString());
            System.out.println("todayMax=" + object.get("todayMax").getAsString());
            System.out.println("todayMin=" + object.get("todayMin").getAsString());
            System.out.println("competitivePri=" + object.get("competitivePri").getAsString());
            System.out.println("reservePri=" + object.get("reservePri").getAsString());
            System.out.println("traNumber=" + object.get("traNumber").getAsString());
            System.out.println("traAmount=" + object.get("traAmount").getAsString());
            System.out.println("buyOne=" + object.get("buyOne").getAsString());
            System.out.println("buyOnePri=" + object.get("buyOnePri").getAsString());
            System.out.println("buyTwo=" + object.get("buyTwo").getAsString());
            System.out.println("buyTwoPri=" + object.get("buyTwoPri").getAsString());
            System.out.println("buyThree=" + object.get("buyThree").getAsString());
            System.out.println("buyThreePri=" + object.get("buyThreePri").getAsString());
            System.out.println("buyFour=" + object.get("buyFour").getAsString());
            System.out.println("buyFourPri=" + object.get("buyFourPri").getAsString());
            System.out.println("buyFive=" + object.get("buyFive").getAsString());
            System.out.println("buyFivePri=" + object.get("buyFivePri").getAsString());
            System.out.println("sellOne=" + object.get("sellOne").getAsString());
            System.out.println("sellOnePri=" + object.get("sellOnePri").getAsString());
            System.out.println("sellTwo=" + object.get("sellTwo").getAsString());
            System.out.println("sellTwoPri=" + object.get("sellTwoPri").getAsString());
            System.out.println("sellThree=" + object.get("sellThree").getAsString());
            System.out.println("sellThreePri=" + object.get("sellThreePri").getAsString());
            System.out.println("sellFour=" + object.get("sellFour").getAsString());
            System.out.println("sellFourPri=" + object.get("sellFourPri").getAsString());
            System.out.println("sellFive=" + object.get("sellFive").getAsString());
            System.out.println("sellFivePri=" + object.get("sellFivePri").getAsString());
            System.out.println("date=" + object.get("date").getAsString());
            System.out.println("time=" + object.get("time").getAsString());

           /** System.out.println("pop=" + object.get("pop").getAsBoolean());  //将Json数据转化为Boolean型的数据
            JsonArray jsonArray = object.get("language").getAsJsonArray();    //得到为Json的数组
            for (int i = 0; i < jsonArray.size(); i++) {
                System.out.println("----------------");
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                System.out.println("id=" + jsonObject.get("id").getAsInt());
                System.out.println("ide=" + jsonObject.get("ide").getAsString());
                System.out.println("name=" + jsonObject.get("name").getAsString());

            }*/





        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
