package com.xfdmao.fcat.coin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xfdmao.fcat.coin.entity.BtcAddress;
import com.xfdmao.fcat.coin.mapper.BtcAddressMapper;
import com.xfdmao.fcat.common.util.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Date;

/**
 * Created by fier on 2018/10/31.
 */
@Controller
public class BtcAddressController {
    @Autowired
    private BtcAddressMapper  btcAddressMapper;
    public static void main(String[] args) {
        System.out.println(getDocument("https://bitinfocharts.com/top-100-richest-bitcoin-addresses.html"));;
    }
    public void btcByBitinfocharts() {
        Document doc = getDocument("https://bitinfocharts.com/top-100-richest-bitcoin-addresses.html");
        // 获取目标HTML代码
        Element element1 = doc.selectFirst("[class=table table-condensed bb]");
        Elements elementTbody = element1.select("tbody");
        Elements elementsTr = elementTbody.get(0).select("tr");
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<elementsTr.size();i++){
            if(i==0)continue;
            Element element = elementsTr.get(i);
            Elements elementsTd = element.select("td");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("balance",elementsTd.get(0).text());
            jsonObject.put("addresses",elementsTd.get(1).text());
            String text3 = elementsTd.get(2).text();
            jsonObject.put("addressesRate",text3.substring(0,text3.indexOf("%")));
            jsonObject.put("addressesTotalRate",text3.substring(text3.indexOf("(")+1,text3.lastIndexOf("%")));
            jsonObject.put("coins",elementsTd.get(3).text().replaceAll(" BTC","").replaceAll(",",""));
            jsonObject.put("usd",elementsTd.get(4).text().replaceAll(" USD","").replaceAll(",",""));
            String text5 = elementsTd.get(5).text();
            jsonObject.put("coinsRate",text5.substring(0,text5.indexOf("%")));
            jsonObject.put("coinsTotalRate",text5.substring(text5.indexOf("(")+1,text5.lastIndexOf("%")));
            jsonArray.add(jsonObject);
            BtcAddress btcAddress =jsonObject.toJavaObject(BtcAddress.class);
            btcAddress.setCreateTime(new Date());
            btcAddressMapper.insert(btcAddress);
        }

    }


    public static void test1() {
        Document doc = getDocument("https://btc.com/stats/rich-list");
        // 获取目标HTML代码
        Element element1 = doc.selectFirst("[class=diff-history]");
        Elements elementsTr = element1.select("tr");
        JSONArray jsonArray = new JSONArray();
        Long  balanceTotal = 0l;
        for(int i=0;i<elementsTr.size();i++){
            if(i==0)continue;
            Element element = elementsTr.get(i);
            Elements elementsTd = element.select("td");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("index",elementsTd.get(0).text());
            jsonObject.put("url",elementsTd.get(1).selectFirst("a").attr("href"));
            jsonObject.put("address",elementsTd.get(1).selectFirst("a").text());
            jsonObject.put("balance",elementsTd.get(2).text().replaceAll(",",""));
       /*     String balance = getBalance(jsonObject.getString("url"));
            if(balance!=null && !balance.equals(jsonObject.getString("balance"))){
                System.out.println("更新balance");
                jsonObject.put("balance",balance);
            }*/
            balanceTotal += jsonObject.getDouble("balance").longValue();
            jsonObject.put("colloct_date", DateUtil.formatDate(new Date(),DateUtil.TIME_PATTERN_DISPLAY));
            jsonArray.add(jsonObject);
        }
        System.out.println(jsonArray.toString());
        System.out.println(balanceTotal);
    }

    public static String getBalance(String url)  {
        Document doc = getDocument(url);
        // 获取目标HTML代码
        String balance = "";
        try{
            balance = doc.selectFirst("[class=abstract-section]").select("dl").get(1).text().replaceAll(",","").replaceAll("BTC","").replace("Balance","").replace(" ","");
        }catch (Exception e){
            System.out.println(url);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        System.out.println(balance);
        return balance;
    }

    /**
     *
     * @param url 访问路径
     * @return
     */
    public static Document getDocument (String url){
        try {
            //5000是设置连接超时时间，单位ms
            return Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
                    .header("cookie", "__cfduid=d0d9aef9d4b33de47723257d9c4aa50641536467342; _ga=GA1.2.180297396.1536467333; __gads=ID=83539fec7fb04972:T=1536467346:S=ALNI_MaVs8c1TtnWXTGdlmk9ocJTIo-d1g; _xicah=aab9e6e0-b569332e; __utmc=128621471; __ssds=2; __ssuzjsr2=a9be0cd8e; __uzmbj2=1536468275; __uzmaj2=d11f93ef-1aeb-43e0-9e1a-bb25d08f7b422501; __uzmcj2=656541956566; __uzmdj2=1536475220; __utma=128621471.180297396.1536467333.1536500120.1536585537.5; cf_clearance=2e6da9f5705689a198da36b8afc36d61469ea4da-1556768247-10800-150; _gid=GA1.2.1583936575.1556768162")
                    .timeout(10000)
                    .followRedirects(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
