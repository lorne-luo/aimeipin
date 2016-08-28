package com.meidi.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Constructor;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luanpeng on 16/3/18.
 */
public class MdCommon {

    /**
     * 判断是否为空
     *
     * @param object
     * @return
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String && "".equals(object)) {
            return true;
        } else if (object instanceof Collection && ((Collection) object).isEmpty()) {
            return true;
        } else if (object instanceof Map && ((Map) object).isEmpty()) {
            return true;
        } else if (object instanceof Object[] && ((Object[]) object).length == 0) {
            return true;
        }

        return false;
    }

    /**
     * 对象转换字符串
     *
     * @param object
     * @return
     */
    public static String null2String(Object object) {
        return object == null ? "" : object.toString();
    }

    /**
     * 获得当前日期字符串
     *
     * @return
     */
    public static String getNowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 获得当前时间字符串
     *
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMDDhhmmss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 获取一个随机数字串
     *
     * @param length
     * @return
     */
    public static String getRandomNum(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);//0~61
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }

    /**
     * 获取一个随机字符串
     *
     * @param length 要获取的长度
     * @return
     */
    public static String getRandomString(int length) {

        String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sf.append(str.charAt(number));
        }
        return sf.toString();
    }

    /**
     * 删除单个文件
     *
     * @param filepath
     * @throws IOException
     */
    public static void delOneFile(String filepath) throws IOException {
        File f = new File(filepath);// 定义文件路径
        if (f.exists()) {
            f.delete();
        }
    }


    /**
     * 生成唯一图片名
     *
     * @param oldFileName
     * @return
     * @throws IOException
     */
    public static String getNewFileName(String flag, String oldFileName) throws IOException {
        int start = oldFileName.lastIndexOf(".");
        String suffix = oldFileName.substring(start);
        String newName = flag + getNowTime() + getRandomString(8) + suffix;
        return newName;
    }


    /**
     * 复制文件
     *
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile, false));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 验证是否为手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^0{0,1}1(3|4|5|7|8)[0-9]{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static Map json2Map(String jsonText) throws Exception {
        JsonFactory jsonFactory = new JsonFactory();
        // Json解析器
        JsonParser jsonParser = jsonFactory.createJsonParser(jsonText);
        // 跳到结果集的开始
        jsonParser.nextToken();
        // 接受结果的HashMap
        HashMap<String, String> map = new HashMap<String, String>();
        // while循环遍历Json结果
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            // 跳转到Value
            jsonParser.nextToken();
            // 将Json中的值装入Map中
            map.put(jsonParser.getCurrentName(), jsonParser.getText());
        }
        return map;
    }

    public static Map<String, Object> json2MapPlus(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //最外层解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Iterator<JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = it.next();
                    list.add(json2MapPlus(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }


    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    public static String createASC2Sort(SortedMap<String, String> packageParams) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                if (i == 0) {
                    sb.append(k + "=" + v);
                } else {
                    sb.append("&" + k + "=" + v);
                }
                i++;
            }
        }
        return sb.toString();

    }

    /**
     * MD5运算
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes("UTF-8"));
        byte[] result = md.digest();

        StringBuffer sb = new StringBuffer();

        for (byte b : result) {
            int i = b & 0xff;
            if (i < 0xf) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }

    /**
     * SHA1 运算
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getSHA1(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes("UTF-8"));
        byte[] result = md.digest();

        StringBuffer sb = new StringBuffer();

        for (byte b : result) {
            int i = b & 0xff;
            if (i <= 0xf) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(i));
        }

        return sb.toString();
    }

    /**
     * 获取客户端IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(!isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)){
            if(ip.indexOf(",") > 0){
                ip = ip.split(",")[0];
            }
        }


        if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前请求路径
     *
     * @param request
     * @return
     */
    public static String getUrl(HttpServletRequest request) {
        String serverName = "http://" + request.getServerName(); // 服务器地址
        String contextPath = request.getContextPath(); // 项目名称
        String servletPath = request.getServletPath(); // 请求页面或其他地址
        String queryString = request.getQueryString(); // 参数

        String url = serverName + contextPath + servletPath;
        if (!MdCommon.isEmpty(queryString)) {
            url = url + "?" + queryString;
        }

        return url;
    }

    /**
     * 解析XML
     *
     * @param XML
     * @return
     */
    public static Map parseXML(String XML) {
        InputStream in = null;
        Map result = new HashMap<>();
        //关闭流
        try {
            in = new ByteArrayInputStream(XML.getBytes());
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(in);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();

            for (Element element : list) {
                result.put(element.getName(), element.getText());
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!isEmpty(in)) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 微信昵称特殊字符转义
     *
     * @param nickname
     * @return
     */
    public static String nicknameEscape(String nickname) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = nickname.length();
        for (int i = 0; i < nickname.length(); i++) {
            char c = nickname.charAt(i);
            if (isEmojiCharacter(c)) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) ||
                ((codePoint == 0x20) && (codePoint == 0xD7FF)) || ((codePoint == 0xE000) && (codePoint == 0xFFFD)) ||
                ((codePoint == 0x10000) && (codePoint == 0x10FFFF));
    }


    /**
     * 微信交易  使用证书
     * @return
     * @throws Exception
     */
    public static CloseableHttpClient clientSSL() throws Exception {
        //指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(new File(MdConstants.KEY_STORE));
        try {
            //指定PKCS12的密码(商户ID)
            keyStore.load(instream, MdConstants.MCH_ID.toCharArray());
        } finally {
            instream.close();
        }
//        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MdConstants.MCH_ID.toCharArray()).build();


        //指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        //设置httpclient的SSLSocketFactory
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        return httpclient;
    }


    /**
     * 创建分页请求.
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize, Object sortType) {
        Sort sort = null;

        if (sortType == MdConstants.SORT_BY.ID_ASC ) {
            sort = new Sort(Sort.Direction.ASC, "id");
        } else if (sortType == MdConstants.SORT_BY.ID_DESC ) {
            sort = new Sort(Sort.Direction.DESC, "id");
        } else if (sortType == MdConstants.SORT_BY.UPDATE_TIME_ASC ) {
            sort = new Sort(Sort.Direction.ASC, "updateTime");
        } else if (sortType == MdConstants.SORT_BY.UPDATE_TIME_DESC ) {
            sort = new Sort(Sort.Direction.DESC, "updateTime");
        } else if (sortType == MdConstants.SORT_BY.CREATE_TIME_ASC ) {
            sort = new Sort(Sort.Direction.ASC, "createTime");
        } else if (sortType == MdConstants.SORT_BY.CREATE_TIME_DESC ) {
            sort = new Sort(Sort.Direction.DESC, "createTime");
        } else {
            //先对item排序 如果相同 再时间排序
            sort = new Sort(Sort.Direction.DESC, "item").and(new Sort(Sort.Direction.DESC, "createTime"));
        }

        return new PageRequest(pageNumber - 1, pageSize, sort);
    }


    /**
     * 通用实体转换方法,将JPA返回的数组转化成对应的实体集合,这里通过泛型和反射实现
     * @param <T>
     * @param list
     * @param clazz 需要转化后的类型
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) throws Exception {
        List<T> returnList = new ArrayList<T>();
        Object[] co = list.get(0);
        Class[] c2 = new Class[co.length];

        try{
            //确定构造方法
            for(int i = 0; i < co.length; i++){
                c2[i] = co[i].getClass();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        for(Object[] o : list){
            Constructor<T> constructor = clazz.getConstructor(c2);
            returnList.add(constructor.newInstance(o));
        }

        return returnList;
    }



}
