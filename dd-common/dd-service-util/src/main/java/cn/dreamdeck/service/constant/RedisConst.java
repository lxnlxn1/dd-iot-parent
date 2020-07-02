package cn.dreamdeck.service.constant;

/**
 * Redis常量配置类
 */
public class RedisConst {


    //单位：秒
    public static final long SKUKEY_TIMEOUT = 24 * 60 * 60;
    //单位：秒 尝试获取锁的最大等待时间
    public static final long SKULOCK_EXPIRE_PX1 = 1;
    //单位：秒 锁的持有时间
    public static final long SKULOCK_EXPIRE_PX2 = 1;
    public static final String SKULOCK_SUFFIX = ":lock";
    public static final long SKUKEY_TEMPORARY_TIMEOUT = 60 * 60;

    public static final String USER_KEY_PREFIX = "user:";
    public static final String USER_CART_KEY_SUFFIX = ":cart";
    public static final long USER_CART_EXPIRE = 60 * 60 * 24 * 7;

    //用户登录
    public static final String USER_LOGIN_KEY_PREFIX = "user:login:";
    public static final int USERKEY_TIMEOUT = 60 * 60 * 24 * 7;

    //前台项目用户数据
    public static final String PROJECT_USER_KEY_PREFIX = "project:user:";
    public static final int PROJECT_USER_TIMEOUT = 60 * 60 * 24 * 7;//过期时间

    //iot系统权限菜单
    public static final String PROJECT_MENU_KEY_PREFIX = "project:menu:";
    public static final int PROJECT_MENU_TIMEOUT = 60 * 60 * 24 * 7;//过期时间


    //语音垃圾桶
    public static final String DEVICE_TRASH_DATA = "trash:data:"; //语音垃圾桶
    public static final String DEVICE_TRASH_NUM = "trash:num:";
    public static final String DEVICE_TRASH_OPENNUM = "trash:opennum:";
    public static final String DEVICE_TRASH_IP = "trash:ip:";


}
