package ink.num.service.impl;

import ink.num.dao.NumberDao;
import ink.num.model.Number;
import ink.num.service.NumberService;
import ink.num.util.RespBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/3/23 23:14:14
 */
@Service
public class NumberServiceImpl implements NumberService {

    @Autowired
    private NumberDao numberDao;


    @Autowired
    private HttpSession sess;

    @Override
    public RespBean getAll() {
        List<Number> list = numberDao.getAll();

        return RespBean.builder()
                .code("0")
                .msg("查询成功")
                .data(list)
                .alarm(RespBean.ALARM)
                .build();
    }

    @Override
    public void addData(Number number) {
        number.setTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        numberDao.addData(number);
    }

    @Override
    public void downExcl(HttpServletResponse resp,String startTime,String endTime) throws IOException {
        System.out.println("下载Excl");

        List<Number> list = null;
        if (startTime != null && endTime!= null &&startTime.length() > 0&&endTime.length() > 0 &&!"undefined".equals(startTime)&&!"undefined".equals(endTime)){
            list = numberDao.search(startTime, endTime);
        }else {
            list =  numberDao.getAll();
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("数据");
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue("编号");
        cell = row1.createCell(1);
        cell.setCellValue("nub");
        cell = row1.createCell(2);
        cell.setCellValue("high_1");
        cell = row1.createCell(3);
        cell.setCellValue("high_2");
        cell = row1.createCell(4);
        cell.setCellValue("high_3");
        cell = row1.createCell(5);
        cell.setCellValue("high_4");
        cell = row1.createCell(6);
        cell.setCellValue("maxHigh");
        cell = row1.createCell(7);
        cell.setCellValue("time");
        for (int i = 0; i < list.size(); i++) {
            Number number = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(number.getId());
            row.createCell(1).setCellValue(number.getNub());
            row.createCell(2).setCellValue(number.getHigh_1());
            row.createCell(3).setCellValue(number.getHigh_2());
            row.createCell(4).setCellValue(number.getHigh_3());
            row.createCell(5).setCellValue(number.getHigh_4());
            row.createCell(6).setCellValue(number.getMaxHigh());
            row.createCell(7).setCellValue(number.getTime());
        }
        System.out.println("下载excl");
        resp.setHeader("Content-disposition","attachment;data.xls");
        resp.setContentType("application/ms-excel");
        wb.write(resp.getOutputStream());

    }

    @Override
    public RespBean search(String startTime, String endTime) {
        startTime = startTime.replaceAll("T", " ");
        endTime = endTime.replaceAll("T", " ");
        List<Number> lists = numberDao.search(startTime,endTime);
        return RespBean.builder()
                .code("0")
                .msg("时间查询")
                .data(lists)
                .build();
    }
}
