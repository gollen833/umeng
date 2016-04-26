package com.mll.umeng.message;

import com.mll.umeng.message.exception.UmengException;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author walter
 * @Time 2016/3/2
 * 友盟推送客户端
 */
public class Client {
    private String appKey;
    private String appMasterSecret;

    private Client(String appKey,String appMasterSecret){
        this.appKey = appKey;
        this.appMasterSecret = appMasterSecret;
    }

    public static Client newClient(String appKey,String appMasterSecret){
        return new Client(appKey,appMasterSecret);
    }

    public PushResult push(String json) throws UmengException {
        try{

            JSONObject jsonObject = new JSONObject(json);
            JSONObject responseJson = commonRequest(jsonObject,"http://msg.umeng.com/api/send");

            PushResult result = new PushResult();
            if (!responseJson.getJSONObject("data").isNull("task_id")){
                result.setTaskid(responseJson.getJSONObject("data").getString("task_id"));
            }
            if (!responseJson.getJSONObject("data").isNull("msg_id")){
                result.setMsgid(responseJson.getJSONObject("data").getString("msg_id"));
            }
            return result;
        }catch (JSONException e){
            throw UmengException.newSysError(e);
        }
    }

    public String upload(List<String> content) throws UmengException {
        try{
            JSONObject jsonObject = new JSONObject();
            StringBuffer sb = new StringBuffer();
            if (content!=null){
                for (String ct:content){
                    sb.append(ct);
                    sb.append("\n");
                }
            }
            jsonObject.put("content",sb.toString());
            JSONObject responseJson = commonRequest(jsonObject,"http://msg.umeng.com/upload");
            return responseJson.getJSONObject("data").getString("file_id");
        }catch (JSONException e){
            throw UmengException.newSysError(e);
        }
    }

    public TaskStatusQueryResult taskStatus(String taskid) throws UmengException {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("task_id",taskid);
            JSONObject responseJson = commonRequest(jsonObject,"http://msg.umeng.com/api/status");

            TaskStatusQueryResult result = new TaskStatusQueryResult(
                    responseJson.getJSONObject("data").isNull("task_id")?taskid:responseJson.getJSONObject("data").getString("task_id"),
                    TaskStatusEnum.getInstanceByStatusValue(responseJson.getJSONObject("data").getInt("status")),
                    responseJson.getJSONObject("data").getInt("total_count"),
                    responseJson.getJSONObject("data").getInt("accept_count"),
                    responseJson.getJSONObject("data").getInt("sent_count"),
                    responseJson.getJSONObject("data").getInt("open_count"),
                    responseJson.getJSONObject("data").getInt("dismiss_count"));
            return  result;
        }catch (JSONException e){
            throw UmengException.newSysError(e);
        }
    }

    public void cancelTask(String taskid) throws UmengException {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("task_id",taskid);
            commonRequest(jsonObject,"http://msg.umeng.com/api/cancel");
        }catch (JSONException e){
            throw UmengException.newSysError(e);
        }
    }

    private JSONObject commonRequest(JSONObject initJson, String reqPath) throws UmengException{
        try{
            initJson.put("appkey",appKey);
            initJson.put("timestamp",new Date().getTime());
            String body = initJson.toString();
            String sign = DigestUtils.md5Hex(("POST" + reqPath + body + appMasterSecret).getBytes("utf8"));
            javax.ws.rs.client.Client client = ClientBuilder.newClient();
            Response response = client.target(reqPath).queryParam("sign", sign)
                    .request(MediaType.APPLICATION_JSON).buildPost(Entity.json(body)).invoke();
            String entity = response.readEntity(String.class);
            response.close();
            JSONObject responseJson = new JSONObject(entity);
            if ("FAIL".equals(responseJson.getString("ret"))){
                throw UmengException.newApiError(responseJson.getJSONObject("data").getString("error_code"));
            }
            return responseJson;
        }catch (JSONException e){
            throw UmengException.newSysError(e);
        }catch(UnsupportedEncodingException e){
            throw UmengException.newSysError(e);
        }
    }
}
