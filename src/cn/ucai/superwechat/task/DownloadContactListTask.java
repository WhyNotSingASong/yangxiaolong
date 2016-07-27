package cn.ucai.superwechat.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.Map;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.bean.Result;
import cn.ucai.superwechat.bean.UserAvatar;
import cn.ucai.superwechat.data.OkHttpUtils2;
import cn.ucai.superwechat.utils.Utils;

/**
 * Created by Administrator on 2016/7/26.
 */
public class DownloadContactListTask {
    private final String TAG =DownloadContactListTask.class.getSimpleName();
    Context mContext;
    String username;
    public  DownloadContactListTask(String username){
        this.username = username;
    }
    public DownloadContactListTask(Context Context,String username){
        mContext = Context;
        this.username = username;
    }
    public void execute() {
        final OkHttpUtils2<String> utils = new OkHttpUtils2<String>();
        utils.setRequestUrl(I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST)
                .addParam(I.Contact.USER_NAME,username)
                .targetClass(String.class)
                .execute(new OkHttpUtils2.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.e(TAG,"s="+s);
                        Log.e(TAG,"http://10.0.2.2:8080/SuperWeChatServer/Server?request=download_contact_all_list&m_contact_user_name=ucai003");
                        Result result = Utils.getListResultFromJson(s,UserAvatar.class);
                        Log.e(TAG,"result="+result);
                       List<UserAvatar> list = (List<UserAvatar>) result.getRetData();
                        Log.e(TAG,"list="+list.size());
                        if(list!=null&&list.size()>0){
                            SuperWeChatApplication.getInstance().setUserList(list);
                            mContext.sendStickyBroadcast(new Intent("update_contact_list"));
                            Map<String,UserAvatar> userMap = SuperWeChatApplication.getInstance().getUserMap();
                            for(UserAvatar u:list){
                                userMap.put(u.getMUserName(),u);
                                Log.e(TAG,"u="+u.toString());
                            }
                        }
                    }


                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
