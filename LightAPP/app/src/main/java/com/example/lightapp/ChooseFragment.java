package com.example.lightapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lightapp.data.Clazz;
import com.example.lightapp.data.Floor;
import com.example.lightapp.data.Tower;
import com.example.lightapp.util.HttpUtil;
import com.example.lightapp.util.Utility;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseFragment extends Fragment {
    public static final int LEVEL_TOWER = 0;
    public static final int LEVEL_FLOOR = 1;
    public static final int LEVEL_CLASS = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private Button titleMenu;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private boolean TF = false;
    /**
     * 楼栋列表
     */
    private List<Tower> towersList;
    /**
     * 楼层列表
     */
    private List<Floor> floorList;
    /**
     * 教室列表
     */
    private List<Clazz> clazzList;
    /**
     * 选中的楼栋
     */
    private Tower selectedTower;
    /**
     * 选中的楼层
     */
    private Floor selectedFloor;
    /**
     * 当前选中的级别
     */
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose, container, false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        titleMenu = (Button)view.findViewById(R.id.title_ht);
        backButton = (Button)view.findViewById(R.id.back_button);
        listView = (ListView)view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_TOWER){

                    selectedTower = towersList.get(position);
                    Log.d("position",selectedTower.toString());
                    queryFloor();
                }else if (currentLevel == LEVEL_FLOOR){
                    selectedFloor = floorList.get(position);
                    queryClass();
                }else if(currentLevel == LEVEL_CLASS){
                    int clazzId = clazzList.get(position).getCid();
                    Intent intent = new Intent(getActivity(),LightActivity.class);
                    intent.putExtra("class_id",clazzId);
                    startActivity(intent);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_CLASS){
                    queryFloor();
                }else if (currentLevel == LEVEL_FLOOR){
                    queryTower();
                }
            }
        });
        titleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ManagementActivity.class);

                startActivity(intent);
            }
        });
        queryTower();
    }
    /**
     * 查询所有楼栋
     */
    private void queryTower(){
        titleText.setText("诚信大");
        backButton.setVisibility(View.GONE);
        towersList = DataSupport.findAll(Tower.class);
        if (towersList.size() > 0 ){
            dataList.clear();
            for (Tower tower:towersList){
                dataList.add(tower.getTid()+"  "+tower.getTname()+"  "+tower.getTowerCode());
            }

            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_TOWER;
        }else {
            String address = "http://10.0.2.2:8080/APPServer/selectTowerServlet";

            queryFromServer(address,"tower");

        }
    }

    /**
     * 查询选中楼栋内的所有楼层，优先从数据库查询，如果没有查询到再去服务器查询
     */
    private void queryFloor(){
        titleText.setText(selectedTower.getTname());
        backButton.setVisibility(View.VISIBLE);
        floorList = DataSupport.where("tid = ?",String.valueOf(selectedTower.getTid())).find(Floor.class);
        if (floorList.size()>0){
            dataList.clear();
            for (Floor floor:floorList){
                dataList.add(floor.getFid()+"  "+floor.getFname()+"  "+floor.getFloorCode());
            }

            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_FLOOR;
        }else {
            int towerId = selectedTower.getTid();
            String address = "http://10.0.2.2:8080/APPServer/selectFloorServlet?tid="+towerId;

            queryFromServer(address,"floor");
        }
    }

    /**
     * 查询选中楼层内的所有教室，优先从数据库查询，如果没有查询到再去服务器查询
     */

    private void queryClass(){
        titleText.setText(selectedFloor.getFname());
        backButton.setVisibility(View.VISIBLE);
        clazzList = DataSupport.where("fid = ?",String.valueOf(selectedFloor.getFid())).find(Clazz.class);

        if (clazzList.size()>0){

            dataList.clear();
            for (Clazz clazz:clazzList){
                dataList.add(clazz.getClassCode());
            }

            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CLASS;
        }else {
            int floorId = selectedFloor.getFid();
            String adress = "http://10.0.2.2:8080/APPServer/selectClazzServlet?fid="+floorId;

            queryFromServer(adress,"clazz");
        }
//               titleText.setText(selectedFloor.getFname());
//               backButton.setVisibility(View.VISIBLE);
//               int floorId = selectedFloor.getFid();
//            String adress = "http://10.0.2.2:8080/APPServer/selectClazzServlet?fid="+floorId;
//            queryFromServer1(adress,"clazz");
//        clazzList = Utility.clazzList;
//        if (clazzList.size()>0) {
//
//            dataList.clear();
//            for (Clazz clazz : clazzList) {
//                dataList.add(clazz.getClassCode());
//            }
//
//            adapter.notifyDataSetChanged();
//            listView.setSelection(0);
//            currentLevel = LEVEL_CLASS;
//        }else {
//
//            queryFromServer1(adress,"clazz");
//        }
    }
    /**
     *根据输入的地址和类型从服务器上查询数据
     */

    private void queryFromServer(String address,final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responeText = response.body().string();
                boolean result = false;
                if ("tower".equals(type)){
                    result = Utility.handleTowerResponse(responeText);
                }else if ("floor".equals(type)){
                    result = Utility.handleFloorResponse(responeText,selectedTower.getTid());
                }else if ("clazz".equals(type)){

                    result = Utility.handleClassResponse(responeText,selectedFloor.getFid());
                }
                if (result) {
                    if (getActivity()!=null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                                if ("tower".equals(type)) {
                                    queryTower();
                                } else if ("floor".equals(type)) {
                                    queryFloor();
                                } else if ("clazz".equals(type)) {
                                    Log.d("----0000000","+++++++++++++++1111");
                                    queryClass();
                                }
                            }
                        });
                    }
                    if (getActivity()==null){
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
//    private void queryFromServer1(String address,final String type) {
//        showProgressDialog();
//        HttpUtil.sendOkHttpRequest(address, new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responeText = response.body().string();
//                boolean result = false;
//                if ("tower".equals(type)){
//                    result = Utility.handleTowerResponse(responeText);
//                }else if ("floor".equals(type)){
//                    result = Utility.handleFloorResponse(responeText,selectedTower.getTid());
//                }else if ("clazz".equals(type)){
//
//                    result = Utility.handleClassResponse1(responeText,selectedFloor.getFid());
//                }
//                if (result) {
//                    if (getActivity()!=null) {
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                closeProgressDialog();
//                                if ("tower".equals(type)) {
//                                    queryTower();
//                                } else if ("floor".equals(type)) {
//                                    queryFloor();
//                                } else if ("clazz".equals(type)) {
//                                    Log.d("----0000000","+++++++++++++++1111");
//                                   // queryClass();
//                                }
//                            }
//                        });
//                    }
//                    if (getActivity()==null){
//                        return;
//                    }
//                }
//            }

//            @Override
//            public void onFailure(Call call, IOException e) {
//                //通过runOnUiThread()方法回到主线程处理逻辑
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        closeProgressDialog();
//                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
