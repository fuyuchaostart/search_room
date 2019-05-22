package com.fycstart.bass;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/5下午 8:21
 */
public class ApiDataTablesResponse extends ApiResponse {

    private int draw;
    private long recordsTotal;
    private long recordsFiltered;


    public ApiDataTablesResponse(StatusEnum status) {
        this(status.getStatus(), status.getMessage(), null);
    }

    public ApiDataTablesResponse(Long code, String message, Object data) {
        super(code, message, data);
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
