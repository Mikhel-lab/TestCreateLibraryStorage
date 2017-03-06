package com.anncha.testcreatelibrarystorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.anncha.testcreatelibrarystorage.datamanager.DataManager;
import com.anncha.testcreatelibrarystorage.processor.Processable;
import com.anncha.testcreatelibrarystorage.processor.RSA;

public class MainActivity extends AppCompatActivity {

    private DataManager dataManager;
    private Processable processable;
    private Resource r,r2,r3,r4,r5;
    private InternalStorage internalStorage;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShow = (TextView)findViewById(R.id.tv_show);
        processable = new RSA();
        r = new Resource("Test");
        r2 = new Resource("I love YOU Baby ^&*((*(&**% สวัสดี ฉันชื่อนางสาวศรีสยาม รองรับทุกการทำงาน สีทนด้ายยยยยยยยยยยยย");
        r3 = new Resource(getResources().getString(R.string.content_test));
        r4 = new Resource(getResources().getString(R.string.content_test2));
        r5 = new Resource("ฉันชื่อ บุษบา ลาลาล้าาา ลาลา ลา ล้าาาาาาาาาาาาาาาาาาาาาาาาาาาาาาาาาาาาาาาา");
        internalStorage = new InternalStorage();

        //เขียนสตริงยาวๆต่อกับประโยคหนึ่งประโยค
//        internalStorage.createNewFile("/AnnSum1/testNovel/","testSum2.txt",processable,r4,null);
//        internalStorage.writeClose(null);
//
//        internalStorage.write("/AnnSum1/testNovel/","testSum2.txt",processable,r2,null);
//        internalStorage.writeClose(null);
//
////        internalStorage.read("/AnnSum1/testNovel/","testSum2.txt",processable,"UTF-8",null);
//        tv.setText(internalStorage.read("/AnnSum1/testNovel/","testSum2.txt",processable,"UTF-8",null));


        //เขียนต่อกันประโยคสั้นๆ
//        internalStorage.createNewFile("/AnnSum1/test4/","testSum3.txt",processable,r2,null);
//        internalStorage.writeClose(null);
//
//
//        internalStorage.write("/AnnSum1/test4/","testSum3.txt",processable,r4,null);
//        internalStorage.writeClose(null);


        for(int i = 0;i<=10;i++){
            internalStorage.createNewFile("/AnnSum1/test5/","testSum"+i+".txt",processable,r2,null);
            internalStorage.writeClose(null);
        }

//        for(int i = 0;i<=10;i++){
//            internalStorage.createNewFile("/AnnSum1/test6/","testSum"+i+".txt",processable,r3,null);
//            internalStorage.writeClose(null);
//        }

//        for (int i = 0; i<=100;i++){
//            tv.setText(internalStorage.read("/AnnSum1/test4/","testSum"+i+".txt",processable,"UTF-8",null));
//        }
//
//        internalStorage.read("/AnnSum1/test5/","testSum1.txt",processable,"UTF-8",null);
//        tvShow.setText(internalStorage.read("/AnnSum1/test5/","testSum1.txt",processable,"UTF-8",null));

//
////        internalStorage.clear("/AnnSum1/test4/","testSum1.txt",null);
//        internalStorage.remove("/AnnSum1/test4/","testSum1.txt",null);





//for project test
//        Processable processable = new RSA();
//        Resource r1 = new Resource("summary");
//        Resource r2 = new Resource("YEH Yeh!");
//
//        InternalStorage internalStorage = new InternalStorage();
//        internalStorage.createNewFile("/AnnSum2/testReadProject/","testSum1.txt",processable,r1,null);
//        internalStorage.writeClose(null);
//
//        internalStorage.write("/AnnSum2/testReadProject/","testSum1.txt",processable,r2,null);
//        internalStorage.writeClose(null);
//
//        internalStorage.read("/AnnSum2/testReadProject/","testSum1.txt",processable,"UTF-8",null);
//
//        internalStorage.remove("/AnnSum1/testReadProject/","testSum1.txt",null);

//
//        //write complete
//        try {
//            dataManager = new DataManager("/AnnSum1/test2/","testSum5.txt");
//            internalStorage.createNewFile(dataManager,processable,r, null);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("aaa","Exception");
//        }
//
//        //read complete
//        try {
//            dataManager = new DataManager("/AnnSum1/test2/","testSum5.txt");
//            internalStorage.read(dataManager,processable,"UTF-8",null);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //test continue
//        try {
//            dataManager = new DataManager("/AnnSum1/test2/","testSum1.txt");
//            internalStorage.write(dataManager,processable,r2,null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            dataManager = new DataManager("/AnnSum1/test2/","testSum1.txt");
//            internalStorage.read(dataManager,processable,"UTF-8",null);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Delete file complete
//        try {
//            dataManager = new DataManager("/AnnSum1/test/","testSum3.txt");
//            internalStorage.remove(dataManager,null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //Clear file complete
//        try {
//            dataManager = new DataManager("/AnnSum1/test/","testSum4.txt");
//            internalStorage.clear(dataManager,null);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //clear file original
//        try {
//            dataManager = new DataManager("/AnnSum/test/","testSum.txt");
//            dataManager.clearFile("testSum1.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//
//        try {
//            processable = new RSA();
//            testStorage = new TestStorage();
//            dataManager = new DataManager("/AnnSum/test/","testSum.txt");
//            testStorage.read(dataManager,processable,"UTF-8",null);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("aaa","Exception");
//        }


        //Write original OK
//        try {
//            DataManager dataManager = new DataManager("/Ann_test/test2/","testagain.txt");
//            dataManager.writeStringToFile("Test again14");
//            dataManager.writeStringToFile("ANNNNNNNNNNNNNNNNNNN");
//            dataManager.writeClosed();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("aaa","Exception : " + e);
//        }


    }
}
