package com.example.lightapp.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.lightapp.data.Clazz;
import com.example.lightapp.data.Floor;
import com.example.lightapp.data.Light;
import com.example.lightapp.data.Tower;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static List<Clazz> clazzList = new ArrayList<Clazz>();
    public static List<Light> lightList = new ArrayList<Light>();
    /**
     * 解析和处理服务器返回的楼栋数据
     */
    public static boolean handleTowerResponse(String response){
        if(!TextUtils.isEmpty(response)){

                try {
                    JSONArray allTower = new JSONArray(response);
                    for (int i = 0;i < allTower.length();i++){
                        JSONObject towerObject = allTower.getJSONObject(i);
                        Tower tower = new Tower();
                        tower.setTid(towerObject.getInt("tid"));
                        tower.setTname(towerObject.getString("tname"));
                        tower.setAddress(towerObject.getString("address"));
                        tower.setTowerCode(towerObject.getInt("towerCode"));
                        tower.save();
                    }
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


        }
        return false;
    }

    /**
     * 解析处理服务器返回的楼层信息
     */
    public static boolean handleFloorResponse(String response,int tid){
        if (!TextUtils.isEmpty(response)){
            Log.d("a----------------------","++++++++++++++++++++++++");
            try {
                JSONArray allFloor = new JSONArray(response);
                for (int i=0;i<allFloor.length();i++){
                    JSONObject floorObject = allFloor.getJSONObject(i);
                    Floor floor = new Floor();
                    floor.setFid(floorObject.getInt("fid"));
                    floor.setFname(floorObject.getString("fname"));
                    floor.setTid(tid);
                    floor.setFloorCode(floorObject.getInt("floorCode"));

                    floor.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 解析和处理返回的教室数据
     */
    public static boolean handleClassResponse(String response, int fid){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allClass = new JSONArray(response);
                for (int i=0;i<allClass.length();i++){
                    JSONObject classObject = allClass.getJSONObject(i);
                    Clazz clazz = new Clazz();
                    clazz.setCid(classObject.getInt("cid"));
                    clazz.setFid(fid);
                    clazz.setClassCode(classObject.getString("classCode"));
                    clazz.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static List<Light> handleLightResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allLight = new JSONArray(response);
                List<Light> lightList= new ArrayList<Light>();
                for (int i=0;i<allLight.length();i++){
                    JSONObject lightObject = allLight.getJSONObject(i);
                    Light light = new Light();
                    light.setCid(lightObject.getInt("cid"));
                    light.setLid(lightObject.getInt("lid"));
                    light.setLname(lightObject.getString("lname"));
                    light.setState(lightObject.getInt("state"));
                    lightList.add(light);
                }
                return lightList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
//    public static boolean handleClassResponse1(String response, int fid){
//        if (!TextUtils.isEmpty(response)){
//            clazzList.clear();
//            try {
//                JSONArray allClass = new JSONArray(response);
//                for (int i=0;i<allClass.length();i++){
//                    JSONObject classObject = allClass.getJSONObject(i);
//                    Clazz clazz = new Clazz();
//                    clazz.setCid(classObject.getInt("cid"));
//                    clazz.setFid(fid);
//                    clazz.setClassCode(classObject.getString("classCode"));
//                    clazzList.add(clazz);
//                }
//                return true;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//        return false;
//    }
    public static boolean handleLightResponse(String response,int cid){
        if (!TextUtils.isEmpty(response)){
            lightList.clear();
            try {
                JSONArray allLight = new JSONArray(response);
                for (int i = 0;i<allLight.length();i++){
                    JSONObject lightObject = allLight.getJSONObject(i);
                    Light light = new Light();
                    light.setLid(lightObject.getInt("lid"));
                    light.setCid(lightObject.getInt("cid"));
                    light.setLname(lightObject.getString("lname"));
                    light.setState(lightObject.getInt("state"));
                    lightList.add(light);
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}


