package com.atguigu.demo;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.DVALRecord;
import org.junit.Test;

import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringDemo {

    //设置APPID/AK/SK
    public static final String APP_ID = "你的 App ID";
    public static final String API_KEY = "GuLKSmj65qp1o4k2xkOiErxz";
    public static final String SECRET_KEY = "v5h147p5bC1WvpGiEW5GOpijscRoLLG1";
    public static final String BAIDU_SB = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";

    @Test
    public  void test1(){
        //String str ="nurse_1234_498576";
        String str ="nurse_498576";
        String[] split = str.split("_");

        Boolean b1 = split.length !=3;
        Boolean b2 = !StringUtils.equals("nurse",split[0]);
        Boolean b3 = StringUtils.isEmpty(split[1]);
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
        System.out.println(b1||b2);
        if(b1&&b2&&b3){
            System.out.println("1");
        }
        if(split.length != 3
                ||!StringUtils.equals("nurse",split[0])
                ||StringUtils.isEmpty(split[1])
                ||StringUtils.isEmpty(split[2])
        ){
            System.out.println("#####错误####");
        }
        /*for (String s1 : split) {
            System.out.println(s1);
        }*/
    }
    @Test
    public void sample() throws Exception {
/*        "file:/D:/xxxx.yml"
                "file:/C:/Users/86185/Desktop/test.jpg"*/
        String accessToken = FaceUtil.getAuth(API_KEY, SECRET_KEY);
        String image = "C:\\Users\\86185\\Desktop\\test.png";
        InputStream in = new FileInputStream(image);
        byte[] imgData = toByteArray(in);
        in.close();

        //String filePath = image;
        //byte[] imgData = FileUtil.readFileByBytes(filePath);
        String imgStr = Base64Util.encode(imgData);
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");
        String param = "image=" + imgParam;
        String json = HttpUtil.post(BAIDU_SB, accessToken, param);
        JSONObject jsonObject = JSONObject.parseObject(json);
        ResultVO resultVO = (ResultVO) JSONObject.toJavaObject(jsonObject,ResultVO.class);
        String words_result = resultVO.getWords_result();

        //String kv = words_result[0];
        System.out.println(words_result);


    }
    private byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
}
