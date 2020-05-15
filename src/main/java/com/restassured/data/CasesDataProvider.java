package com.restassured.data;

import com.restassured.utils.ReadExcelClass;
import jxl.read.biff.BiffException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CasesDataProvider {

    @DataProvider(name = "casesProvider")
    public static Object[][] caseProvider(){
        //  获取项目路径
        String projectDirectory = System.getProperty("user.dir") ;
        System.out.println("projectDirectory :  "+projectDirectory);
        String filePath = projectDirectory + File.separator + "src" +  File.separator + "main" + File.separator + "testCases" + File.separator + "testcases.xls";
        //  获取用例路径
        System.out.println("filePath : "+filePath);
        //  获取对象数组
        Object[][] cases = null;
        try {
            cases = ReadExcelClass.readCases(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        //  输出对象数组的内容
        int rowNum = cases.length;
        int colNum=0;
        if(rowNum>0)
            colNum = cases[0].length;
        for(int i=0;i<rowNum;i++){
            for(int j=0;j<colNum;j++){
//                if(cases[i][j]!=null)
                System.out.print("["+i+"]["+j+"]"+" = "+cases[i][j]+"   ");
//                else System.out.print("["+i+"]["+j+"]"+" ");
            }
        }

        //  为了与下一个模块隔开，输出下面的空行
        System.out.println();

        return cases;
    }


}

