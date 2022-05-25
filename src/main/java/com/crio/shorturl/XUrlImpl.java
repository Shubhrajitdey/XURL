package com.crio.shorturl;
import java.util.*;
import java.util.HashMap;

public class XUrlImpl implements XUrl{
    Map<String,String> urlTrackerLtoS = new HashMap<>();
    Map<String,String> urlTrackerStoL = new HashMap<>();
    Map<String,Integer> urlTrackerCount = new HashMap<>();
    public String getString(){
        String DATAPOINT ="ABCDEFGHIJK8523LMNOPQRSTWXYZ1234abcdefghijklmnopqrstwxyz9310";
        Random ran = new Random();
        StringBuilder sb = new StringBuilder();
        while(sb.length()<9){
            int indexP = ran.nextInt(DATAPOINT.length());
            sb.append(DATAPOINT.charAt(indexP));
        }
        return sb.toString();
    }
    public String updateContext(String longUrl,String key){
        urlTrackerLtoS.put(longUrl,key);
        urlTrackerStoL.put(key,longUrl);
        urlTrackerCount.put(longUrl,0);
        return "http://short.url/"+key;
    }
    @Override
    public String registerNewUrl(String longUrl){
       if(urlTrackerLtoS.containsKey(longUrl)){
           return "http://short.url/"+urlTrackerLtoS.get(longUrl);
       }else{
           return updateContext(longUrl, getString());
       }
    }
    @Override
    public String registerNewUrl(String longUrl, String shortUrl){
        String searchUrl = shortUrl.replace("http://short.url/", "");
        if(urlTrackerStoL.containsKey(searchUrl)){
            return null;
        }
        return updateContext(longUrl,searchUrl);
    }
    @Override
    public String getUrl(String shortUrl){
        String longUrl = urlTrackerStoL.get(shortUrl.replace("http://short.url/", ""));
        if(longUrl==null){
            return null;
        }
        urlTrackerCount.put(longUrl,urlTrackerCount.getOrDefault(longUrl,0)+1);
        return longUrl;
    }
    @Override
    public Integer getHitCount(String longUrl){
        //System.out.println("mapcount"+urlTrackerCount.keySet());
       // String searchUrl = longUrl.replace("http://", "");
        //System.out.println("Searchurl"+searchUrl);
        if(urlTrackerCount.get(longUrl)==null){
            return 0;
        }
        return urlTrackerCount.get(longUrl);
    }
    @Override
    public String delete(String longUrl){
        String ans = urlTrackerLtoS.remove(longUrl);
        String ans2 = urlTrackerStoL.remove(ans);
        return ans2;
    }

}