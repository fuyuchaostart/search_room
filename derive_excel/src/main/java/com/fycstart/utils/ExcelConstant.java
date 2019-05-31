package com.fycstart.utils;


import java.util.ArrayList;

/**
 * @author fyc
 * @description: 常量类
 * @date 2019/5/22上午 10:38
 */
public class ExcelConstant {

    public static final ArrayList<String> REPAYMENT_TITLE;
    public static String REPAYMENT_TITLE_STRING = "";

    static {
        REPAYMENT_TITLE = new ArrayList();
        REPAYMENT_TITLE.add("ID pesanan");
        REPAYMENT_TITLE.add("Jenis pinjaman");
        REPAYMENT_TITLE.add("Nama pengguna");
        REPAYMENT_TITLE.add("NIK");
        REPAYMENT_TITLE.add("Ponsel");
        REPAYMENT_TITLE.add("Jumlah pembayaran jatuh tempo");
        REPAYMENT_TITLE.add("Waktu pencairan");
        REPAYMENT_TITLE.add("Tanggal perkiraan pembayaran");
        REPAYMENT_TITLE.add("Waktu pembayaran");
        REPAYMENT_TITLE.add("Jumlah pembayaran sebenar");
        REPAYMENT_TITLE.add("Status pembayaran");
        REPAYMENT_TITLE.add("hari jatuh tempo");
        REPAYMENT_TITLE.add("Jumlah tunggakan");
        REPAYMENT_TITLE.add("Jumlah pinjaman");
        REPAYMENT_TITLE.add("Periode pinjaman");
        REPAYMENT_TITLE.add("loanDay");
        for (String s : REPAYMENT_TITLE) {
            REPAYMENT_TITLE_STRING += s + ",";
        }
        REPAYMENT_TITLE_STRING = REPAYMENT_TITLE_STRING.substring(0, REPAYMENT_TITLE_STRING.length() - 1);
    }


}
