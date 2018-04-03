package implementation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class Url {

   static Connection con = null ;

    static {
        try {

            Class.forName("com.mysql.jdbc.Driver");
              con = DriverManager.getConnection("jdbc:mysql://192.168.1.5:3306/gupiao?characterEncoding=UTF-8", "root", "root");

        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException, SQLException {
        URL url = new URL(urlStr);


        URLConnection urlConnection =url.openConnection();


        InputStream inputStream = urlConnection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));


        JsonParser parser = new JsonParser();


        JsonObject object = (JsonObject) parser.parse(reader.readLine());   //创建JsonObject的对象

        JsonArray  jsonObject = object.getAsJsonArray("result") ;
        JsonObject jsonElement = (JsonObject)jsonObject.get(0);

        StringBuilder a = new StringBuilder(" INSERT into gsj (") ;

        StringBuilder b = new StringBuilder("(") ;

        Iterator iterator = jsonElement.getAsJsonObject("data").entrySet().iterator()  ;


        do{


            Map.Entry<?,?> stringMap = (Map.Entry<?,?>) iterator.next();
            a.append(stringMap.getKey()) ;


            b.append( stringMap.getValue()) ;

            if (iterator.hasNext()) {
                a.append(",");
                b.append(",") ;
            }
            else {
                b.append(")") ;
                a.append(")") ;

            }


        }while (iterator.hasNext()) ;

        a.append(" values" ).append(b) ;

      con.prepareStatement(a.toString()  ).executeUpdate() ;

//
//        System.out.println("gid=" + object.get("gid").getAsString());  //将Json数据转化为String型的数据
//        System.out.println("increPer=" + object.get("increPer").getAsString());
//        System.out.println("increase=" + object.get("increase").getAsString());
//        System.out.println("name=" + object.get("name").getAsString());
//        System.out.println("todayStartPri=" + object.get("todayStartPri").getAsString());
//        System.out.println("yestodEndPri=" + object.get("yestodEndPri").getAsString());
//        System.out.println("nowPri=" + object.get("nowPri").getAsString());
//        System.out.println("todayMax=" + object.get("todayMax").getAsString());
//        System.out.println("todayMin=" + object.get("todayMin").getAsString());
//        System.out.println("competitivePri=" + object.get("competitivePri").getAsString());
//        System.out.println("reservePri=" + object.get("reservePri").getAsString());
//        System.out.println("traNumber=" + object.get("traNumber").getAsString());
//        System.out.println("traAmount=" + object.get("traAmount").getAsString());
//        System.out.println("buyOne=" + object.get("buyOne").getAsString());
//        System.out.println("buyOnePri=" + object.get("buyOnePri").getAsString());
//        System.out.println("buyTwo=" + object.get("buyTwo").getAsString());
//        System.out.println("buyTwoPri=" + object.get("buyTwoPri").getAsString());
//        System.out.println("buyThree=" + object.get("buyThree").getAsString());
//        System.out.println("buyThreePri=" + object.get("buyThreePri").getAsString());
//        System.out.println("buyFour=" + object.get("buyFour").getAsString());
//        System.out.println("buyFourPri=" + object.get("buyFourPri").getAsString());
//        System.out.println("buyFive=" + object.get("buyFive").getAsString());
//        System.out.println("buyFivePri=" + object.get("buyFivePri").getAsString());
//        System.out.println("sellOne=" + object.get("sellOne").getAsString());
//        System.out.println("sellOnePri=" + object.get("sellOnePri").getAsString());
//        System.out.println("sellTwo=" + object.get("sellTwo").getAsString());
//        System.out.println("sellTwoPri=" + object.get("sellTwoPri").getAsString());
//        System.out.println("sellThree=" + object.get("sellThree").getAsString());
//        System.out.println("sellThreePri=" + object.get("sellThreePri").getAsString());
//        System.out.println("sellFour=" + object.get("sellFour").getAsString());
//        System.out.println("sellFourPri=" + object.get("sellFourPri").getAsString());
//        System.out.println("sellFive=" + object.get("sellFive").getAsString());
//        System.out.println("sellFivePri=" + object.get("sellFivePri").getAsString());
//        System.out.println("date=" + object.get("date").getAsString());
//        System.out.println("time=" + object.get("time").getAsString());

        if(inputStream!=null){
            inputStream.close();

        }

        System.out.println("info:"+url+"download success");
    }



    public static byte[] readInputstream(InputStream inputStream) throws IOException{
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer))!= -1){
            bos.write(buffer,0,len);
        }
        bos.close();
        return bos.toByteArray();
    }


    public static void main(String[] args){
        try{

            downLoadFromUrl("http://web.juhe.cn:8080/finance/stock/hs?gid=sh601012&key=7e04c15b3a9cef1930be2cb0d2735e8b","baidu","/home/mm/") ;

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
