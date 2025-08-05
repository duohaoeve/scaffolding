package com.shaohao.scaffold.enums.crypto;


public enum DbotEnum {

    DBOT_SIM_URL("SIM_SWAP_URL",
            "https://api-bot-v1.dbotx.com/simulator/sim_swap_order"
    ),
    DBOT_SWAP_URL("DBOT_SWAP_URL",
            "https://api-bot-v1.dbotx.com/automation/swap_order"
    ),
    DBOT_POOL_URL("DBOT_POOL_URL",
            "https://api-bot-v1.dbotx.com/dex/poolinfo?chain="
    ),


    GLOBAL_Warning1("GLOBAL_Warning1", ( "一级全局预警，\n5分钟内全局%s次内部%s次。\n%s")),
    GLOBAL_Warning2("GLOBAL_Warning2", ( "二级全局预警，\n5分钟内全局%s次内部%s次。\n%s")),
    GLOBAL_Warning3("GLOBAL_Warning3", ( "三级全局预警，\n5分钟内全局%s次内部%s次。\n%s")),
    INTERNAL_WARNING1("INTERNAL_WARNING1", ( "一级关注预警，\n5分钟内全局%s次内部%s次。\n%s")),
    INTERNAL_WARNING2("INTERNAL_WARNING2", ( "二级关注预警，\n5分钟内全局%s次内部%s次。\n%s")),
    INTERNAL_WARNING3("INTERNAL_WARNING3", ( "三级关注预警，\n5分钟内全局%s次内部%s次。\n%s"))

            ;

    private String action;
    private String text;

    DbotEnum(String action, String text) {
        this.action = action;
        this.text = text;
    }

    public String getAction() {
        return action;
    }

    public String getText() {
        return text;
    }

}
