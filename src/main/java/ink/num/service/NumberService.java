package ink.num.service;

import ink.num.model.Number;
import ink.num.util.RespBean;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface NumberService {
    RespBean getAll();

    void addData(Number number);

    void downExcl(HttpServletResponse resp,String stratTime,String endTime)throws IOException;

    RespBean search(String startTime, String endTime);
}
