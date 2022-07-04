package com.example.android_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;


public class ThongTin extends AppCompatActivity {
    public static ArrayList<InfoStudent> arrayList = new ArrayList<>();
    EditText Ten, Namsinh;
    RadioButton Nam, Nu;
    Button btnThem;
    TextView Sua;
    String sex;
    JavaCustomListview custom;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);
        getSupportActionBar().hide();
        //các bước setup ở trang recycler view
//        RecyclerView recyclerView = findViewById(R.id.id_listview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        ThongTinAdapter thongTinAdapter = new ThongTinAdapter(arrayList);
//        recyclerView.setAdapter(thongTinAdapter);


        arrayList.add(new InfoStudent("Trung Quân", R.drawable.emtwo, "2/3/2000", "Nam"));
        arrayList.add(new InfoStudent("Thị Tươi", R.drawable.emthree, "2/3/2000", "Nữ"));
        arrayList.add(new InfoStudent("Văn Việt", R.drawable.emone, "2/3/2000", "Nam"));

        //các bước setup ở trang listview
        list = findViewById(R.id.lv_listview);
        custom = new JavaCustomListview(ThongTin.this, arrayList);
        list.setAdapter(custom);


        ImageButton add_list = findViewById(R.id.imgButton_add);
        add_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogsheetview = LayoutInflater.from(ThongTin.this).inflate(R.layout.add_listview, null);
                BottomSheetDialog dialog = new BottomSheetDialog(ThongTin.this);
                dialog.setContentView(dialogsheetview);
                Ten = dialogsheetview.findViewById(R.id.etName_thongtin);
                Namsinh = dialogsheetview.findViewById(R.id.evNamsinh_thongtin);
                Nam = dialogsheetview.findViewById(R.id.rbNam);
                Nu = dialogsheetview.findViewById(R.id.rbNu);
                btnThem = dialogsheetview.findViewById(R.id.btnAdd_thongtin);

                //Xử lý radiobutton Nam Nữ
                Nam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Nu.setChecked(false);

                    }
                });
                Nu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Nam.setChecked(false);

                    }
                });


                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten = Ten.getText().toString();
                        String namsinh = Namsinh.getText().toString();
                        int image;
                        if (ten.isEmpty() || namsinh.isEmpty()) {
                            Toast.makeText(ThongTin.this, "NHẬP ĐỦ DỮ LIỆU!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (Nam.isChecked()) {
                                sex = "Nam";
                                image = R.drawable.emthree;
                            } else {
                                sex = "Nữ";
                                image = R.drawable.emtwo;
                            }
                            arrayList.add(new InfoStudent(ten, image, namsinh, sex));
                            custom.notifyDataSetChanged();
                        }
                    }
                });
                dialog.show();
            }
        });


// KHi án vào 1 item trong listview
       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(ThongTin.this, InfoListView.class);
               InfoStudent dataSend = arrayList.get(position);
               if (dataSend != null) {
                   Log.i("HTC", "ấn vào listview");
                   intent.putExtra("data", dataSend);
                   startActivity(intent);
               }
           }
       });

    }

    public void Sua(int posision) {
        InfoStudent infoStudent = arrayList.get(posision);
        View dialogsheetview = LayoutInflater.from(ThongTin.this).inflate(R.layout.edit_listview, null);
        BottomSheetDialog dialog = new BottomSheetDialog(ThongTin.this);
        dialog.setContentView(dialogsheetview);
        TextView ten_edit = dialogsheetview.findViewById(R.id.etName_thongtin_edit);
        TextView namsinh_edit = dialogsheetview.findViewById(R.id.evNamsinh_thongtin_edit);
        RadioButton nam_edit = dialogsheetview.findViewById(R.id.rbNam_edit);
        RadioButton nu_edit = dialogsheetview.findViewById(R.id.rbNu_edit);
        Button edit = dialogsheetview.findViewById(R.id.btnEdit_thongtin);
        ten_edit.setText(infoStudent.getName());
        namsinh_edit.setText(infoStudent.getNamsinh());
        if (arrayList.get(posision).getGioitinh().equalsIgnoreCase("Nữ")) {
            nu_edit.setChecked(true);
        }
        if (arrayList.get(posision).getGioitinh().equalsIgnoreCase("Nam")) {
            nam_edit.setChecked(true);
        }
        //Xử lý radiobutton Nam Nữ
        nam_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nu_edit.setChecked(false);

            }
        });
        nu_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nam_edit.setChecked(false);

            }
        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = ten_edit.getText().toString();
                String namsinh = namsinh_edit.getText().toString();
                int image;
                if (ten.isEmpty() || namsinh.isEmpty()) {
                    Toast.makeText(ThongTin.this, "NHẬP ĐỦ DỮ LIỆU!", Toast.LENGTH_SHORT).show();
                } else {
                    if (nam_edit.isChecked()) {
                        sex = "Nam";
                        image = R.drawable.emthree;
                    } else {
                        sex = "Nữ";
                        image = R.drawable.emtwo;
                    }
                    infoStudent.setName(ten);
                    infoStudent.setNamsinh(namsinh);
                    infoStudent.setGioitinh(sex);
                    infoStudent.setImage(image);
                    custom.notifyDataSetChanged();
                }
            }
        });
        dialog.show();

    }

    public void Xoa(int posision) {
        arrayList.remove(posision);
        custom.notifyDataSetChanged();

    }
}


