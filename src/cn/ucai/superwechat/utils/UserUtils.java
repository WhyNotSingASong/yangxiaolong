package cn.ucai.superwechat.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cn.ucai.superwechat.DemoHXSDKHelper;
import cn.ucai.superwechat.I;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.SuperWeChatApplication;
import cn.ucai.superwechat.applib.controller.HXSDKHelper;
import cn.ucai.superwechat.bean.UserAvatar;
import cn.ucai.superwechat.domain.User;

public class UserUtils {
	final static String TAG = UserUtils.class.getSimpleName();
    /**
     * 根据username获取相应user，由于demo没有真实的用户数据，这里给的模拟的数据；
     * @param username
     * @return
     */
    public static User getUserInfo(String username){
        User user = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().get(username);
        if(user == null){
            user = new User(username);
        }
            
        if(user != null){
            //demo没有这些数据，临时填充
        	if(TextUtils.isEmpty(user.getNick()))
        		user.setNick(username);
        }
        return user;
    }
    
    /**
	 * 设置用户头像
	 * @param username
	 */
	public static void setUserAvatar(Context context, String username, ImageView imageView){
		User user = getUserInfo(username);
		if(user != null && user.getAvatar() != null){
			Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
		}else{
			Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
		}
	}
	/**
	 * 设置用户头像
	 * @param username
	 */
	public static void setAppUserAvatar(Context context, String username, ImageView imageView){
		String path ="";
		if(username != null){
			path =getUserAvatarPath(username);
			Log.e(TAG,"path="+path);
			Picasso.with(context).load(path).placeholder(R.drawable.default_avatar).into(imageView);
		}else{
			Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
		}
	}
	public static String getUserAvatarPath(String username){
		StringBuilder path = new StringBuilder(I.SERVER_ROOT);
		path.append(I.QUESTION).append(I.KEY_REQUEST)
				.append(I.EQUALS).append(I.REQUEST_DOWNLOAD_AVATAR)
				.append(I.AND)
				.append(I.NAME_OR_HXID).append(I.EQUALS).append(username)
				.append(I.AND)
				.append(I.AVATAR_TYPE).append(I.EQUALS).append(I.AVATAR_TYPE_USER_PATH);
		return path.toString();
	}
    
    /**
     * 设置当前用户头像
     */
	public static void setCurrentUserAvatar(Context context, ImageView imageView) {
		User user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
		if (user != null && user.getAvatar() != null) {
			Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
		} else {
			Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
		}
	}

	/**
	 * 设置当前用户头像
	 */
	public static void setAppCurrentUserAvatar(Context context, ImageView imageView) {
//		EMChatManager.getInstance().getCurrentUser();
		String username = SuperWeChatApplication.getInstance().getUserName();
		setAppUserAvatar(context,username,imageView);
	}
    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
    	User user = getUserInfo(username);
    	if(user != null){
    		textView.setText(user.getNick());
    	}else{
    		textView.setText(username);
    	}
    }

	/**
	 * 根据username获取相应user；
	 * @param username
	 * @return
	 */
	public static UserAvatar getAppUserInfo(String username){
		UserAvatar user = SuperWeChatApplication.getInstance().getUserMap().get(username);
		if(user == null){
			user = new UserAvatar(username);
		}
		return user;
	}

	/**
	 * 设置好友的用户昵称
	 */
	public static void setAppUserNick(String username,TextView textView){
		UserAvatar user = getAppUserInfo(username);
		setAppUserNick(user,textView);
	}
	/**
	 * 设置用户昵称
	 */
	public static void setAppUserNick(UserAvatar user,TextView textView){
		if(user != null){
			if(user.getMUserNick()!=null){
				textView.setText(user.getMUserNick());
			}else {
				textView.setText(user.getMUserName());
			}
		}
	}
    
    /**
     * 设置当前用户昵称
     */
    public static void setCurrentUserNick(TextView textView){
    	User user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
    	if(textView != null){
    		textView.setText(user.getNick());
    	}
    }
	/**
	 * 设置当前用户昵称
	 */
	public static void setAppCurrentUserNick(TextView textView){
		UserAvatar user = SuperWeChatApplication.getInstance().getUser();
		if(textView != null && user!=null){
			if(user.getMUserNick()!=null){
				textView.setText(user.getMUserNick());
			}else{
				textView.setText(user.getMUserName());
			}

		}
	}
    
    /**
     * 保存或更新某个用户
     * @param newUser
     */
	public static void saveUserInfo(User newUser) {
		if (newUser == null || newUser.getUsername() == null) {
			return;
		}
		((DemoHXSDKHelper) HXSDKHelper.getInstance()).saveContact(newUser);
	}
    
}
