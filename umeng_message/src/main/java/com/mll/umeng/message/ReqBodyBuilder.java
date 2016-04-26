package com.mll.umeng.message;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * author: walter
 * date:2016/4/7
 * comment:构造请求体
 */
public class ReqBodyBuilder {
    protected JSONObject rootJson = new JSONObject(new LinkedHashMap());
    private ReqBodyBuilder downReference;

    protected void setJsonProps(JSONObject jsonObject,String key,Object value){
        try{
            jsonObject.put(key,value);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public ReqBodyBuilder appKey(String appKey){
        setJsonProps(rootJson,"appkey",appKey);
        return this;
    }
    public ReqBodyBuilder unicast(String deviceToken){
        setJsonProps(rootJson,"type", CastTypeEnum.unicast.name());
        setJsonProps(rootJson,"device_tokens",deviceToken);
        return this;
    }
    public ReqBodyBuilder listcast(String[] deviceTokenArr){
        setJsonProps(rootJson,"type", CastTypeEnum.listcast.name());
        setJsonProps(rootJson,"device_tokens", StringUtils.join(deviceTokenArr,","));
        return this;
    }
    public ReqBodyBuilder filecast(String fileid){
        setJsonProps(rootJson,"type", CastTypeEnum.filecast.name());
        setJsonProps(rootJson,"file_id",fileid);
        return this;
    }
    public ReqBodyBuilder broadcast(){
        setJsonProps(rootJson,"type", CastTypeEnum.broadcast.name());
        return this;
    }
    public ReqBodyBuilder policy(Date startTime,Date expireTime,Integer maxSendNum){
        JSONObject policyJson = new JSONObject();
        if (startTime!=null){
            setJsonProps(policyJson,"start_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
        }
        if (expireTime!=null){
            setJsonProps(policyJson,"expire_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expireTime));
        }
        if (maxSendNum!=null){
            setJsonProps(policyJson,"max_send_num",maxSendNum);
        }
        setJsonProps(rootJson,"policy",policyJson);
        return this;
    }
    public ReqBodyBuilder productionMode(boolean productionMode){
        setJsonProps(rootJson,"production_mode",productionMode);
        return this;
    }
    public ReqBodyBuilder description(String description){
        setJsonProps(rootJson,"description",description);
        return this;
    }
    public ReqBodyBuilder thirdPartyId(String thirdPartyId){
        setJsonProps(rootJson,"thirdparty_id",thirdPartyId);
        return this;
    }
    /**
     * android message 只有android有message类型
     * @param custom
     * @return
     */
    public AndroidMessageBuilder message(String custom){
        JSONObject payloadJson = new JSONObject();
        setJsonProps(payloadJson,"display_type", DisplayTypeEnum.message.name());
        JSONObject bodyJson = new JSONObject();
        setJsonProps(bodyJson,"custom",custom);
        setJsonProps(payloadJson,"body",bodyJson);
        setJsonProps(rootJson,"payload",payloadJson);
        try{
            AndroidMessageBuilder androidMessage = new AndroidMessageBuilder(this,new JSONObject(rootJson.toString()));
            downReference = androidMessage;
            return androidMessage;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public AndroidNotificationBuilder androidNotification(String ticker, String title, String text){
        JSONObject payloadJson = new JSONObject();
        JSONObject bodyJson = new JSONObject();
        setJsonProps(bodyJson,"ticker",ticker);
        setJsonProps(bodyJson,"title",title);
        setJsonProps(bodyJson,"text",text);
        setJsonProps(payloadJson,"body",bodyJson);
        setJsonProps(rootJson,"payload",payloadJson);
        try{
            AndroidNotificationBuilder androidReqBody = new AndroidNotificationBuilder(this,new JSONObject(rootJson.toString()));
            downReference = androidReqBody;
            return androidReqBody;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public IosNotificationBuilder iosNotification(String alert){
        JSONObject payloadJson = new JSONObject();
        JSONObject apsJson = new JSONObject();
        setJsonProps(apsJson,"alert",alert);
        setJsonProps(payloadJson,"aps",apsJson);
        setJsonProps(rootJson,"payload",payloadJson);
        try{
            IosNotificationBuilder iosReqBody = new IosNotificationBuilder(this,new JSONObject(rootJson.toString()));
            downReference = iosReqBody;
            return iosReqBody;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public String build() {
        if (downReference==null)
            throw new RuntimeException("API使用错误,至少要指定android或ios通知类型");
        setJsonProps(downReference.rootJson, "timestamp", String.valueOf(new Date().getTime()));
        return downReference.rootJson.toString();
    }

    abstract public class AbstractReqBodyBuilder extends ReqBodyBuilder {
        protected  ReqBodyBuilder prototype;
        public AbstractReqBodyBuilder(ReqBodyBuilder prototype,JSONObject initJson){
            this.prototype = prototype;
            this.rootJson = initJson;
        }

        @Override
        public String build() {
            setJsonProps(rootJson, "timestamp", String.valueOf(new Date().getTime()));
            return rootJson.toString();
        }
    }
    public class AndroidMessageBuilder extends AbstractReqBodyBuilder {
        public AndroidMessageBuilder(ReqBodyBuilder prototype,JSONObject initJson){
            super(prototype,initJson);
        }
    }
    public class AndroidNotificationBuilder extends AbstractReqBodyBuilder {
        public AndroidNotificationBuilder(ReqBodyBuilder prototype,JSONObject initJson){
            super(prototype,initJson);
        }
        public AndroidNotificationBuilder icon(String icon){
            try{
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"icon",icon);
                return this;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        public AndroidNotificationBuilder largeIcon(String largeIcon){
            try{
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"largeIcon",largeIcon);
                return this;
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }
        public AndroidNotificationBuilder img(String img){
            try{
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"img",img);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            return this;
        }
        public AndroidNotificationBuilder sound(String sound){
            try{
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"sound",sound);
                return this;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        public AndroidNotificationBuilder play(boolean vibrate,boolean lights,boolean sound){
            try{
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"play_vibrate",vibrate);
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"play_lights",lights);
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"play_sound",sound);
                return this;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        public AndroidNotificationBuilder goApp(){
            try{
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"after_open",AfterOpenEnum.go_app.name());
                return this;
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }
        public AndroidNotificationBuilder goUrl(String url){
            try{
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"after_open",AfterOpenEnum.go_url.name());
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"url",url);
                return this;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        public AndroidNotificationBuilder goActivity(String activity){
            try{
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"after_open",AfterOpenEnum.go_activity.name());
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"activity",activity);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
            return this;
        }
        public AndroidNotificationBuilder goCustom(String custom){
            try{
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"after_open",AfterOpenEnum.go_custom.name());
                setJsonProps(rootJson.getJSONObject("payload").getJSONObject("body"),"custom",custom);
                return this;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        public AndroidNotificationBuilder extra(Map<String,String> extra){
            try{
                if (extra!=null){
                    JSONObject extraJson = new JSONObject();
                    Iterator<String> iterator = extra.keySet().iterator();
                    while (iterator.hasNext()){
                        String key = iterator.next();
                        setJsonProps(extraJson,key,extra.get(key));
                    }
                    setJsonProps(rootJson.getJSONObject("payload"),"extra",extraJson);
                }
                return this;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

    }
    public class IosNotificationBuilder extends AbstractReqBodyBuilder {
        public IosNotificationBuilder(ReqBodyBuilder prototype,JSONObject initJson){
            super(prototype,initJson);
        }
        public IosNotificationBuilder apsOptions(String badge, String sound, String contentAvailable,String category){
            return this;
        }
        public IosNotificationBuilder customProps(Map<String,String> kv){
            try{
                if (kv!=null){
                    Iterator<String> iterator = kv.keySet().iterator();
                    while (iterator.hasNext()){
                        String key = iterator.next();
                        setJsonProps(rootJson.getJSONObject("payload"),key,kv.get(key));
                    }
                }
                return this;
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
