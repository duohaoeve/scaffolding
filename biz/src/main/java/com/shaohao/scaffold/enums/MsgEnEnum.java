package com.shaohao.scaffold.enums;


public enum MsgEnEnum {


    START("START", "\uD83D\uDE80 TwDataBot: Your Gateway to MeMe DeFi \uD83E\uDD16  \n" +
            "       [Twitter](https://x.com/shaohaoxz) | [Support](https://t.me/shaohao_gem)   \n" +
            "\n" +
            "⬩ Balance: %s times  \n" +
            "\n" +
            "Your referral link: [https://t.me/twdata_bot?start=%s](https://t.me/twdata_bot?start=%s)\n" +
            "\n" +
            "Please send a Twitter username, format:'@username', get Twitter username history,for example:  \n" +
            "@shaohaoxz" +
            "\n" +
            "\n" +
            "Send /help get help."),

    SIGNIN_FAIL("SIGNIN_FAIL", "You have already sign in today!"),

    SIGNIN_SUCCESS("SIGNIN_SUCCESS", "Sign in successful! Times+1"),

    DEPOSIT("DEPOSIT", "Deposite solana address:\n" +
            " %s" +
            "\n" +
            "\n" +
            "Price: 0.1SOL=100times.\n" +
            "\n" +
            "Your balance: %s times \n" +
            "\n" +
            "Tip: Minimum deposit amount 0.1sol, please transfer the SOL to this address, send transaction ID after successful transfer."),

    REFERRAL("REFERRAL", "\uD83D\uDD17 Invitation link: https://t.me/twdata_bot?start=%s\n" +
            "\uD83D\uDCB5 Withdrawable: %s SOL\n" +
            "\uD83D\uDCB0 Accumulated withdrawal: %s SOL\n" +
            "\uD83D\uDC65 Cumulative invitations: %s people\n" +
            "\uD83D\uDCD6 Rule:\n" +
            "1. Inviting others to use can earn them %s of their deposit permanently\n" +
            "2. Withdrawals can be made after reaching 0.1, and can only be applied for once every 24 hours. Trigger automatic payment to the receiving address at 8:00 am (UTC+8) every day, and the payment will be received within 24 hours after triggering."),

    HELP("HELP", "Support commands:\n" +
            "/start - Query your account\n" +
            "/qd - Daily sign-in\n" +
            "/deposit- Deposite for your account\n" +
            "/twdata- Search Twitter data\n" +
            "/referral - Referral rewards\n" +
            "/help - Tutorial & Help\n" +  "\n" +
            " [Twitter](https://x.com/shaohaoxz) | [Support](https://t.me/shaohao_gem)") ,

    FIRST_SEND("FIRST_SEND", "Please send /start first."),
    BALANCE_ZERO("BALANCE_ZERO", "Sorry, your credit is running zero, please /deposit first."),
    DM("DM", "Sorry, there is a problem here, please dm Admin."),

    TWDATA("TWDATA", ( "Please send a Twitter username, for example:  " +
            "\n" +
            "@shaohaoxz")),

    TWTEXT("TWTEXT", ( "Number of Twitter name changes: %s times。  " +
            "\n" +
            "%s")),
    TWFAIL("TWFAIL", ( "Exception in obtaining historical username request.")),

    TW_NO_DATA("TW_NO_DATA", ( "No valid Twitter users were obtained.")),

    WITHDRAWAL_SOL("WITHDRAWAL_SOL", ( "WITHDRAWAL_SOL")),

    DO_WITHDRAWAL("DO_WITHDRAWAL", ( "Your available balance is insufficient." + "\n" +
            "[Support](https://t.me/shaohao_gem)."))
    ,
    DEPOSIT_SUCCESS("DEPOSIT_SUCCESS", ( "Deposit Success,Amount= %s SOL,times+ %s.")),

    VERIFY_FAILED("VERIFY_FAILED", ( "Verify Failed.")),

    WAIT("WAIT", ( "Please wait a seconds."))

            ;

    private String action;
    private String text;

    MsgEnEnum(String action, String text) {
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
