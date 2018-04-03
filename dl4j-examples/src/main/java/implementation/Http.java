package implementation;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class Http  {

    private static  LinkedBlockingQueue<String> sqlBlockingQueue = new LinkedBlockingQueue<String>(100) ;
    private static  LinkedBlockingQueue<Properties> propertiesBlockingQueue = new LinkedBlockingQueue<Properties>(100) ;
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>() ;


    private String url;
    private Thread sqlThread = null ;
    private Thread httpThread = null ;



    public Http(String url){

        this.url = url ;

        httpThread = new Thread(httpRunnable(),"HTTP-T") ;
        httpThread.start();

        sqlThread = new Thread(innsterDb(),"SQL-T") ;
        sqlThread.start() ;


    }               //两个线程开始执行


    public  void stop(){


        httpThread.interrupt() ;
        sqlThread.interrupt() ;
    }  //中断线程指令

    public void addPropertie( Properties properties) throws InterruptedException {
        propertiesBlockingQueue.put(properties) ;  //在队列尾部插入指定元素，必要时等待可用空间
    }

    public Runnable innsterDb(){

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                Connection connection = null ;

                try {

                    while (!Thread.currentThread().isInterrupted())   //测试当前线程是否中断
                    {



                        String sql = sqlBlockingQueue.take() ;   //sql阻塞队列，使用容量限制队列
                        System.out.println("SQL插入开始................"+sql);
                        connection = connectionThreadLocal.get();  //返回当前线程的本地线程变量的值

                        if (connection == null && (connection = getConnection()) != null) {
                            connectionThreadLocal.set(connection);//将当前线程本地变量的值copy到指定的值
                        }


                        Statement statement = connection.createStatement();//创建用于发送的sql语句到数据库

                        int count = statement.executeUpdate(sql);  //执行给定的sql语句

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    System.out.println("服务停止通知................");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (connection != null)
                            connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } ;

        return runnable ;
    }

    private void doExprot(Properties properties){

        try {


//            String urlProperti = urlPropertiesPattern(properties) ;
//
//            if (urlProperti == null || urlProperti.length()<=0){
//                return;
//            }
//
//
//            URL url = new URL(this.url+urlProperti);
//
//            URLConnection urlConnection = url.openConnection();
//            InputStream inputStream = urlConnection.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
////            String message = "";
////            String line = null;
////            while((line = bufferedReader.readLine()) != null) {
////                message += line;
////            }
////


            sqlPatternAddQueue(null) ;      //添加sql队列

//            bufferedReader.close();
//            inputStreamReader.close();
//            inputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String urlPropertiesPattern(Properties properties)  {

        String awre = "?" ;
        String propertie = "" ;


        Set<String> strings = properties.stringPropertyNames() ; //返回此属性列表中的一组键，其中键及其对应的值是字符串，包括默认属性列表中的键
        Iterator<String> iterator = strings.iterator() ;  //返回该集合中元素上的迭代器

        do {


            String key =  iterator.next() ;

            propertie = propertie+awre+key+"="+properties.getProperty(key) ;
            awre = "&" ;

            properties.remove(key) ;

        }while (iterator.hasNext()) ;

        return propertie ;
    }


    private void sqlPatternAddQueue(String line) throws InterruptedException {
//
//        if (line == null)
//            return;
//
//        //{"resultcode":"202","reason":"not found!","result":[],"error_code":202102}
//        JsonParser parser = new JsonParser();
//
//
//        JsonObject object = (JsonObject) parser.parse(line);   //创建JsonObject的对象
//        String resultcode =  object.get("resultcode").getAsString() ;
//
//        if( !resultcode.equals("200")   ) {
//
//            return;
//        }
//
//        JsonArray jsonObject = object.getAsJsonArray("result") ;
//        JsonObject jsonElement = (JsonObject)jsonObject.get(0);
//
//        StringBuilder a = new StringBuilder(" INSERT into gsj (") ;
//
//        StringBuilder b = new StringBuilder("(") ;
//
//        Iterator iterator = jsonElement.getAsJsonObject("data").entrySet().iterator()  ;
//
//        do{
//
//
//            Map.Entry<?,?> stringMap = (Map.Entry<?,?>) iterator.next();
//            a.append(stringMap.getKey()) ;
//
//
//            b.append( stringMap.getValue()) ;
//
//            if (iterator.hasNext()) {
//                a.append(",");
//                b.append(",") ;
//            }
//            else {
//                b.append(")") ;
//                a.append(")") ;
//
//            }
//
//
//        }while (iterator.hasNext()) ;
//
//        a.append(" values" ).append(b) ;
//
        StringBuilder stringBuilder = new StringBuilder() ;
        stringBuilder.append("INSERT into\n" +
            "gsj (\n" +
            "buyFive,\n" +
            "buyFivePri,\n" +
            "buyFour,\n" +
            "buyFourPri,\n" +
            "buyOne,\n" +
            "buyOnePri,\n" +
            "buyThree,\n" +
            "buyThreePri,\n" +
            "buyTwo,\n" +
            "buyTwoPri,\n" +
            "competitivePri,\n" +
            "date,\n" +
            "gid,\n" +
            "increPer,\n" +
            "increase,\n" +
            "NAME,\n" +
            "nowPri,\n" +
            "reservePri,\n" +
            "sellFive,\n" +
            "sellFivePri,\n" +
            "sellFour,\n" +
            "sellFourPri,\n" +
            "sellOne,\n" +
            "sellOnePri,\n" +
            "sellThree,\n" +
            "sellThreePri,\n" +
            "sellTwo,\n" +
            "sellTwoPri,\n" +
            "time,\n" +
            "todayMax,\n" +
            "todayMin,\n" +
            "todayStartPri,\n" +
            "traAmount,\n" +
            "traNumber,\n" +
            "yestodEndPri \n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\"64600\",\n" +
            "\t\"3.540\",\n" +
            "\t\"133100\",\n" +
            "\t\"3.550\",\n" +
            "\t\"131900\",\n" +
            "\t\"3.580\",\n" +
            "\t\"171600\",\n" +
            "\t\"3.560\",\n" +
            "\t\"65948\",\n" +
            "\t\"3.570\",\n" +
            "\t\"3.580\",\n" +
            "\t\"2018-03-30\",\n" +
            "\t\"sh601010\",\n" +
            "\t\"0.00\",\n" +
            "\t\"0.000\",\n" +
            "\t\"文峰股份\",\n" +
            "\t\"3.580\",\n" +
            "\t\"3.590\",\n" +
            "\t\"101400\",\n" +
            "\t\"3.630\",\n" +
            "\t\"118400\",\n" +
            "\t\"3.620\",\n" +
            "\t\"194700\",\n" +
            "\t\"3.590\",\n" +
            "\t\"86700\",\n" +
            "\t\"3.610\",\n" +
            "\t\"196000\",\n" +
            "\t\"3.600\",\n" +
            "\t\"15:00:00\",\n" +
            "\t\"3.600\",\n" +
            "\t\"3.560\",\n" +
            "\t\""+(Math.random()*100)+"\",\n" +
            "\t\"10208808.000\",\n" +
            "\t\"28541\",\n" +
            "\t\"3.580\" \n" +
            "\t)") ;
        sqlBlockingQueue.put(stringBuilder.toString()) ;   //在队列的尾部插入指定元素，必要时等待可用空间
    }


    public Runnable httpRunnable() {

        return new Runnable() {
            @Override
            public void run() {

                try {

                    while (!Thread.currentThread().isInterrupted()) {

                        System.out.println(Thread.currentThread().getName() + "请求URL开始................");
                        Properties properties = propertiesBlockingQueue.take();
                        doExprot(properties);
                        System.out.println(Thread.currentThread().getName() + "请求URL完成................");
                    }
                } catch (InterruptedException e) {
                    System.out.println("服务停止通知................");
                }
            }
        } ;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public static Connection getConnection() throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.jdbc.Driver") ;
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.5:3306/gupiao?characterEncoding=UTF-8","root","root");

        return connection ;
    }
    public static void main(String as[]) throws SQLException, ClassNotFoundException, InterruptedException {



        Http http = new Http("http://web.juhe.cn:8080/finance/stock/hs") ;


        for (int i = 0; i <100000 ; i++) {

            Properties properties = new Properties() ;
            properties.setProperty("gid","sh60"+i) ;
            properties.setProperty("key","7e04c15b3a9cef1930be2cb0d2735e8b") ;

            http.addPropertie(properties) ;

        }


        /// Thread.sleep(1000000000) ;


        //http.stop();

    }
}
