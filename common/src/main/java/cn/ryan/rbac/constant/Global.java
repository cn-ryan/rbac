package cn.ryan.rbac.constant;

/**
 * 系统常量类
 * @author ryan
 * @create 2019-05-09 9:42
 **/
public class Global {
    /**
     * 是否 枚举 是
     */
    public static final Short IF_YES = 0;

    /**
     * 是否 枚举 否
     */
    public static final Short IF_NO = 1;

    /**
     * 是否保存日志
     */
    public static final Boolean SAVE_LOG = Boolean.TRUE;

    /**
     *日志记录切点 执行
     */
    public static final String OP_BEG = "执行";

    /**
     *日志记录切点 操作
     */
    public static final String OP_END = "操作";

    /**
     * 保存方法开头 save
     */
    public static final String SAVE = "save";

    /**
     * 修改方法开头 edit
     */
    public static final String EDIT = "edit";

    /**
     * 修改方法开头 update
     */
    public static final String UPDATE = "update";

    /**
     * 删除方法开头 del
     */
    public static final String DEL = "del";


}
