package com.knoxtech.fliprhackathonxii;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    TextInputEditText name,email, pass,mobile,age,experience,capacity,truck_number,trans_name;
    RadioGroup identityGroup;
    RadioButton radioButton;
    FirebaseFirestore db;
    ProgressBar progressBar;
    ExtendedFloatingActionButton save,save2;
    FirebaseAuth mAuth;
    private String identify,name_signup,email_signup,pass_signup,mob_signup;
    public ArrayAdapter<Object> cities_arr;
    AutoCompleteTextView from1_auto,toAuto,from2_auto,to2Auto,from3_auto,to3Auto;
    private String from1_loc,from2_auto_loc,secondFrom,from3_auto_loc,to3Auto_loc,from223_auto_loc;
    private String from_1_lat, from_2_long, from_3_lat, from_4_long,from_5_lat, from_6_long,from22_auto_loc;

    TextInputEditText dealer_quantity, dealer_weight,dealer_nofm;
    AutoCompleteTextView dealer_state,dealer_city;
    String city,state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Object[] cities = new String[]{"Adilabad","Anantapur","Chittoor","Kakinada","Guntur","Hyderabad","Karimnagar","Khammam","Krishna","Kurnool","Mahbubnagar","Medak","Nalgonda","Nizamabad","Ongole","Hyderabad","Srikakulam","Nellore","Visakhapatnam","Vizianagaram","Warangal","Eluru","Kadapa","Anjaw","Changlang","East Siang","Kurung Kumey","Lohit","Lower Dibang Valley","Lower Subansiri","Papum Pare","Tawang","Tirap","Dibang Valley","Upper Siang","Upper Subansiri","West Kameng","West Siang","Baksa","Barpeta","Bongaigaon","Cachar","Chirang","Darrang","Dhemaji","Dima Hasao","Dhubri","Dibrugarh","Goalpara","Golaghat","Hailakandi","Jorhat","Kamrup","Kamrup”Metropolitan","Karbi Anglong","Karimganj","Kokrajhar","Lakhimpur","Marigaon","Nagaon","Nalbari","Sibsagar","Sonitpur","Tinsukia","Udalguri","Araria","Arwal","Aurangabad","Banka","Begusarai","Bhagalpur","Bhojpur","Buxar","Darbhanga","East Champaran","Gaya","Gopalganj","Jamui","Jehanabad","Kaimur","Katihar","Khagaria","Kishangan","Lakhisarai","Madhepura","Madhubani","Munger","Muzaffarpur","Nalanda","Nawada","Patna","Purnia","Rohtas","Saharsa","Samastipur","Saran","Sheikhpura","Sheohar","Sitamarhi","Siwan","Supaul","Vaishali","West Champaran","Chandigarh","Bastar","Bijapur","Bilaspur","Dantewada","Dhamtari","Durg","Jashpur","Janjgir-Champa","Korba","Koriya","Kanker","Kabirdham" ,"Mahasamund","Narayanpur","Raigarh","Rajnandgaon","Raipur","Surguja","Central Delhi","East Delhi","New Delhi","North Delhi","North East Delhi","North West Delhi","South Delhi","South West Delhi","West Delhi","North Goa","South Goa","Ahmedabad","Amreli district","Anand","Banaskantha","Bharuch","Bhavnagar","Dahod","The Dangs","Gandhinagar","Jamnagar","Junagadh","Kutch","Kheda","Mehsana","Narmada","Navsari","Patan","Panchmahal","Porbandar","Rajkot","Sabarkantha","Surendranagar","Surat","Vyara","Vadodara","Valsad","Ambala","Bhiwani","Faridabad","Fatehabad","Gurgaon","Hissar","Jhajjar","Jind","Karnal","Kaithal","Kurukshetra","Mahendragarh","Mewat","Palwal","Panchkula","Panipat","Rewari","Rohtak","Sirsa","Sonipat","Yamuna Nagar","Bilaspur","Chamba","Hamirpur","Kangra","Kinnaur","Kullu","Lahaul and Spiti","Mandi","Shimla","Sirmaur","Solan","Una","Anantnag","Badgam","Bandipora","Baramulla","Doda","Ganderbal","Jammu","Kargil","Kathua","Kishtwar","Kupwara","Kulgam","Leh","Poonch","Pulwama","Rajauri","Ramban","Reasi","Samba","Shopian","Srinagar","Udhampur","Bokaro","Chatra","Deoghar","Dhanbad","Dumka","East Singhbhum","Garhwa","Giridih","Godda","Gumla",        "Hazaribag","Jamtara","Khunti","Koderma","Latehar","Lohardaga","Pakur","Palamu","Ramgarh",
                "Ranchi","Sahibganj","Seraikela Kharsawan","Simdega","West Singhbhum","Bagalkot","Bangalore Rural","Bangalore Urban","Belgaum","Bellary","Bidar","Bijapur","Chamarajnagar","Chikkamagaluru","Chikkaballapur","Chitradurga","Davanagere","Dharwad","Dakshina Kannada","Gadag","Gulbarga","Hassan","Haveri district","Kodagu","Kolar","Koppal","Mandya","Mysore","Raichur","Shimoga","Tumkur","Udupi","Uttara Kannada","Ramanagara","Yadgir","Alappuzha","Ernakulam","Idukki","Kannur","Kasaragod","Kollam","Kottayam","Kozhikode","Malappuram","Palakkad","Pathanamthitta","Thrissur","Thiruvnanthapuram","Wayanad","Alirajpur","Anuppur","AshokNagar","Balaghat","Barwani","Betul","Bhind","Bhopal","Burhanpur","Chhatarpur","Chhindwara","Damoh","Datia","Dewas","Dhar","Dindori","Guna","Gwalior","Harda","Hoshangabad","Indore","Jabalpur","Jhabua","Katni","Khandwa","Khargone","Mandla","Mandsaur","Morena","Narsinghpur","Neemuch","Panna","Rewa","Rajgarh","Ratlam","Raisen","Sagar","Satna","Sehore","Seoni","Shahdol","Shajapur","Sheopur","Shivpuri","Sidhi","Singrauli","Tikamgarh","Ujjain","Umaria","Vidisha","Ahmednagar","Akola","Amravati","Aurangabad","Bhandara","Beed","Buldhana","Chandrapur","Dhule","Gadchiroli","Gondia","Hingoli","Jalgaon","Jalna","Kolhapur","Latur","Mumbai City","Mumbai suburban","Nandurbar","Nanded","Nagpur","Nashik","Osmanabad","Parbhani","Pune","Raigad","Ratnagiri","Sindhudurg","Sangli","Solapur","Satara","Thane","Wardha","Washim","Yavatmal","Bishnupur","Churachandpur","Chandel","Imphal East","Senapati","Tamenglong","Thoubal","Ukhrul","Imphal West","East Garo Hills","East Khasi Hills","Jaintia Hills","Ri Bhoi","South Garo Hills","West Garo Hills","West Khasi Hills","Aizawl","Champhai","Kolasib","Lawngtlai","Lunglei","Mamit","Saiha","Serchhip","Dimapur","Kohima","Mokokchung","Mon","Phek","Tuensang","Wokha","Zunheboto","Angul","Boudh","Bhadrak","Balangir","Bargarh","Balasore","Cuttack","Debagarh ","Dhenkanal","Ganjam","Gajapati","Jharsuguda","Jajpur","Jagatsinghpur","Khordha","Kendujhar","Kalahandi","Kandhamal","Koraput","Kendrapara","Malkangiri","Mayurbhanj","Nabarangpur","Nuapada","Nayagarh","Puri","Rayagada","Sambalpur","Subarnapur","Sundergarh","Karaikal","Mahe","Pondicherry",
                "Yanam","Andaman & Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadra & Nagar Haveli", "Daman & Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu & Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Pondicherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Tripura", "Uttar Pradesh", "Uttaranchal", "West Bengal"};
        cities_arr = new ArrayAdapter<>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, cities);
        identityGroup=findViewById(R.id.radioGroup);
        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.editPass);
        progressBar=findViewById(R.id.progress_horizontal);
        mobile = findViewById(R.id.editMob);
        findViewById(R.id.signUpBtn).setOnClickListener(v -> {
            radioButton = findViewById(identityGroup.getCheckedRadioButtonId());
            identify = radioButton.getText().toString();

            if (identify.equals("Driver")){
                name_signup = Objects.requireNonNull(name.getText()).toString();
                email_signup = Objects.requireNonNull(email.getText()).toString();
                pass_signup = Objects.requireNonNull(pass.getText()).toString();
                mob_signup = Objects.requireNonNull(mobile.getText()).toString();
                if (name_signup.isEmpty() || email_signup.isEmpty() || pass_signup.isEmpty() || mob_signup.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();

                } else {
                    showBottomSheetDialog();
                }
            }else{
                dealerBottomSheet();
            }

        });

        findViewById(R.id.signIpBtn).setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this,MainActivity.class)));
    }

    private void dealerBottomSheet(){
        final BottomSheetDialog bottomSheetDialog2 = new BottomSheetDialog(this);
        bottomSheetDialog2.setContentView(R.layout.dealer_layout);

        save2 = bottomSheetDialog2.findViewById(R.id.signUpBtn2);

        dealer_quantity=bottomSheetDialog2.findViewById(R.id.editQuantity);
        dealer_weight=bottomSheetDialog2.findViewById(R.id.editWeight);
        dealer_nofm=bottomSheetDialog2.findViewById(R.id.editNofM);
        dealer_state=bottomSheetDialog2.findViewById(R.id.state_dealer);
        dealer_city=bottomSheetDialog2.findViewById(R.id.dealer_city);
        assert save2!=null;

        dealer_quantity.addTextChangedListener(watcher2);
        dealer_weight.addTextChangedListener(watcher2);
        dealer_nofm.addTextChangedListener(watcher2);

        dealer_state.addTextChangedListener(watcher2);
        dealer_city.addTextChangedListener(watcher2);
        dealer_state.setAdapter(cities_arr);
        dealer_city.setAdapter(cities_arr);
        bottomSheetDialog2.show();
        dealer_city.setOnItemClickListener((parent, view, position, id) -> {
            city= parent.getItemAtPosition(position).toString();

        });
        dealer_state.setOnItemClickListener((parent, view, position, id) -> {
            state= parent.getItemAtPosition(position).toString();

        });
        save2.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(Objects.requireNonNull(email.getText()).toString(), Objects.requireNonNull(pass.getText()).toString())
                    .addOnCompleteListener(SignUpActivity.this, task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Map<String, Object> data = new HashMap<>();
                            data.put("name", Objects.requireNonNull(name.getText()).toString());
                            data.put("email", email.getText().toString());
                            data.put("mobile", Objects.requireNonNull(mobile.getText()).toString());
                            data.put("status", identify);
                            assert user != null;
                            data.put("uid", user.getUid());
                            data.put("quantity", Objects.requireNonNull(dealer_quantity.getText()).toString());
                            data.put("weight", Objects.requireNonNull(dealer_weight.getText()).toString());
                            data.put("nature_of_material", Objects.requireNonNull(dealer_nofm.getText()).toString());
                            data.put("city",city);
                            data.put("state", state);
                            db.collection("Users").document(user.getUid())
                                    .set(data, SetOptions.merge());
                            bottomSheetDialog2.dismiss();
                            Toast.makeText(SignUpActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(SignUpActivity.this, DealerActivity.class));
                            finish();

                            //});
                        } else {
                            bottomSheetDialog2.dismiss();
                            progressBar.setVisibility(View.GONE);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.driver_bottom_sheet);

        save = bottomSheetDialog.findViewById(R.id.signUpBtn);
        assert save!=null;
        trans_name = bottomSheetDialog.findViewById(R.id.editTransporter);
        age = bottomSheetDialog.findViewById(R.id.editAge);
        experience = bottomSheetDialog.findViewById(R.id.editExp);
        capacity = bottomSheetDialog.findViewById(R.id.editCapacity);
        truck_number = bottomSheetDialog.findViewById(R.id.editTruckNo);
        from1_auto = bottomSheetDialog.findViewById(R.id.from1_auto);
        toAuto = bottomSheetDialog.findViewById(R.id.toAuto);
        from2_auto = bottomSheetDialog.findViewById(R.id.from2_auto);
        to2Auto = bottomSheetDialog.findViewById(R.id.to2Auto);
        from3_auto = bottomSheetDialog.findViewById(R.id.from3_auto);
        to3Auto = bottomSheetDialog.findViewById(R.id.to3Auto);


        trans_name.addTextChangedListener(watcher);
        age.addTextChangedListener(watcher);
        experience.addTextChangedListener(watcher);
        capacity.addTextChangedListener(watcher);
        truck_number.addTextChangedListener(watcher);

        from1_auto.addTextChangedListener(watcher);
        toAuto.addTextChangedListener(watcher);
        from2_auto.addTextChangedListener(watcher);
        to2Auto.addTextChangedListener(watcher);
        to3Auto.addTextChangedListener(watcher);
        from3_auto.addTextChangedListener(watcher);
        toAuto.setAdapter(cities_arr);
        from2_auto.setAdapter(cities_arr);
        to2Auto.setAdapter(cities_arr);
        to3Auto.setAdapter(cities_arr);
        from1_auto.setAdapter(cities_arr);
        from3_auto.setAdapter(cities_arr);

        from2_auto.setOnItemClickListener((parent, view, position, id) -> {
            secondFrom= parent.getItemAtPosition(position).toString();
            Geocoder geocoder = new Geocoder(SignUpActivity.this, Locale.getDefault());
            try {
                List<Address>listAdd=geocoder.getFromLocationName(secondFrom,1);
                if (listAdd.size()>0){
                    from_3_lat = String.valueOf(listAdd.get(0).getLatitude());
                    from_4_long = String.valueOf(listAdd.get(0).getLongitude());
                    Log.e("LAT : ", String.valueOf(listAdd.get(0).getLatitude()));
                    Log.e("LONG : ", String.valueOf(listAdd.get(0).getLongitude()));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        to2Auto.setOnItemClickListener((parent, view, position, id) -> {
            from22_auto_loc= parent.getItemAtPosition(position).toString();
        });


        from1_auto.setOnItemClickListener((parent, view, position, id) -> {
            from1_loc= parent.getItemAtPosition(position).toString();
            Geocoder geocoder = new Geocoder(SignUpActivity.this, Locale.getDefault());
            try {
                List<Address>listAdd=geocoder.getFromLocationName(from1_loc,1);
                if (listAdd.size()>0){
                    from_1_lat = String.valueOf(listAdd.get(0).getLatitude());
                    from_2_long = String.valueOf(listAdd.get(0).getLongitude());
                    Log.e("LAT : ", String.valueOf(listAdd.get(0).getLatitude()));
                    Log.e("LONG : ", String.valueOf(listAdd.get(0).getLongitude()));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        toAuto.setOnItemClickListener((parent, view, position, id) -> {
            from2_auto_loc= parent.getItemAtPosition(position).toString();

        });

        from3_auto.setOnItemClickListener((parent, view, position, id) -> {
            from3_auto_loc= parent.getItemAtPosition(position).toString();
            Geocoder geocoder = new Geocoder(SignUpActivity.this, Locale.getDefault());
            try {
                List<Address>listAdd=geocoder.getFromLocationName(from3_auto_loc,1);
                if (listAdd.size()>0){
                    from_5_lat = String.valueOf(listAdd.get(0).getLatitude());
                    from_6_long = String.valueOf(listAdd.get(0).getLongitude());
                    Log.e("LAT : ", String.valueOf(listAdd.get(0).getLatitude()));
                    Log.e("LONG : ", String.valueOf(listAdd.get(0).getLongitude()));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        to3Auto.setOnItemClickListener((parent, view, position, id) -> {
            from223_auto_loc= parent.getItemAtPosition(position).toString();

        });
        bottomSheetDialog.show();


        save.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(Objects.requireNonNull(email.getText()).toString(), Objects.requireNonNull(pass.getText()).toString())
                    .addOnCompleteListener(SignUpActivity.this, task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Map<String, Object> data = new HashMap<>();
                            data.put("name", Objects.requireNonNull(name.getText()).toString());
                            data.put("email", email_signup);
                            data.put("mobile", Objects.requireNonNull(mobile.getText()).toString());
                            data.put("status", identify);
                            assert user != null;
                            data.put("uid", user.getUid());
                            data.put("age", Objects.requireNonNull(age.getText()).toString());
                            data.put("experience", Objects.requireNonNull(experience.getText()).toString());
                            data.put("transporter_name", Objects.requireNonNull(trans_name.getText()).toString());
                            data.put("capacity", Objects.requireNonNull(capacity.getText()).toString());
                            data.put("truck_no", Objects.requireNonNull(truck_number.getText()).toString());
                            db.collection("Users").document(user.getUid())
                                    .set(data, SetOptions.merge());
                                Map<String, Object> nestedData = new HashMap<>();
                                nestedData.put("from", from1_loc);
                                nestedData.put("from_lat", from_1_lat);
                                nestedData.put("from_long", from_2_long);
                                nestedData.put("to", from2_auto_loc);
                            nestedData.put("name", Objects.requireNonNull(name.getText()).toString());
                            nestedData.put("email", email_signup);
                            nestedData.put("status", identify);
                            nestedData.put("mobile", Objects.requireNonNull(mobile.getText()).toString());
                            nestedData.put("uid", user.getUid());
                            nestedData.put("age", Objects.requireNonNull(age.getText()).toString());
                            nestedData.put("experience", Objects.requireNonNull(experience.getText()).toString());
                            nestedData.put("transporter_name", Objects.requireNonNull(trans_name.getText()).toString());
                            nestedData.put("capacity", Objects.requireNonNull(capacity.getText()).toString());
                            nestedData.put("truck_no", Objects.requireNonNull(truck_number.getText()).toString());
                            db.collection("TRUCKS").document()
                                    .set(nestedData, SetOptions.merge());



                            Map<String, Object> nestedData2 = new HashMap<>();
                            nestedData2.put("from", secondFrom);
                            nestedData2.put("from_lat", from_3_lat);
                            nestedData2.put("from_long", from_4_long);
                            nestedData2.put("to", from22_auto_loc);
                            nestedData2.put("mobile", Objects.requireNonNull(mobile.getText()).toString());
                            nestedData2.put("name", Objects.requireNonNull(name.getText()).toString());
                            nestedData2.put("email", email_signup);
                            nestedData2.put("status", identify);
                            nestedData2.put("uid", user.getUid());
                            nestedData2.put("age", Objects.requireNonNull(age.getText()).toString());
                            nestedData2.put("experience", Objects.requireNonNull(experience.getText()).toString());
                            nestedData2.put("transporter_name", Objects.requireNonNull(trans_name.getText()).toString());
                            nestedData2.put("capacity", Objects.requireNonNull(capacity.getText()).toString());
                            nestedData2.put("truck_no", Objects.requireNonNull(truck_number.getText()).toString());
                            db.collection("TRUCKS").document()
                                    .set(nestedData2, SetOptions.merge());




                            Map<String, Object> nestedData3 = new HashMap<>();
                            nestedData3.put("from", from3_auto_loc);
                            nestedData3.put("from_lat", from_5_lat);
                            nestedData3.put("from_long", from_6_long);
                            nestedData3.put("to", from223_auto_loc);
                            nestedData3.put("mobile", Objects.requireNonNull(mobile.getText()).toString());
                            nestedData3.put("name", Objects.requireNonNull(name.getText()).toString());
                            nestedData3.put("email", email_signup);
                            nestedData3.put("status", identify);
                            nestedData3.put("uid", user.getUid());
                            nestedData3.put("age", Objects.requireNonNull(age.getText()).toString());
                            nestedData3.put("experience", Objects.requireNonNull(experience.getText()).toString());
                            nestedData3.put("transporter_name", Objects.requireNonNull(trans_name.getText()).toString());
                            nestedData3.put("capacity", Objects.requireNonNull(capacity.getText()).toString());
                            nestedData3.put("truck_no", Objects.requireNonNull(truck_number.getText()).toString());
                            db.collection("TRUCKS").document()
                                    .set(nestedData3, SetOptions.merge());




                                    bottomSheetDialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "Authentication success.",
                                        Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(SignUpActivity.this, DriverActivity.class));
                                    finish();

                            //});
                        } else {
                            bottomSheetDialog.dismiss();
                            progressBar.setVisibility(View.GONE);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        });

    }
    private final TextWatcher watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String age_edit = Objects.requireNonNull(age.getText()).toString().trim();
            String trans = Objects.requireNonNull(trans_name.getText()).toString().trim();
            String exp_edit = Objects.requireNonNull(experience.getText()).toString().trim();
            String capacity_edit = Objects.requireNonNull(capacity.getText()).toString().trim();
            String truck_edit = Objects.requireNonNull(truck_number.getText()).toString().trim();
            save.setEnabled(!age_edit.isEmpty() && !trans.isEmpty() && !exp_edit.isEmpty() && !capacity_edit.isEmpty() && !truck_edit.isEmpty() && !from1_auto.getText().toString().trim().isEmpty() && !toAuto.getText().toString().trim().isEmpty() && !from2_auto.getText().toString().trim().isEmpty() && !to2Auto.getText().toString().trim().isEmpty() && !from3_auto.getText().toString().trim().isEmpty() && !to3Auto.getText().toString().trim().isEmpty());

            //save2.setEnabled(!Objects.requireNonNull(dealer_quantity.getText()).toString().trim().isEmpty() && !Objects.requireNonNull(dealer_weight.getText()).toString().trim().isEmpty() && !Objects.requireNonNull(dealer_nofm.getText()).toString().trim().isEmpty() && !dealer_city.getText().toString().trim().isEmpty() && !dealer_state.getText().toString().trim().isEmpty() );

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final TextWatcher watcher2=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            save2.setEnabled(!Objects.requireNonNull(dealer_quantity.getText()).toString().trim().isEmpty() && !Objects.requireNonNull(dealer_weight.getText()).toString().trim().isEmpty() && !Objects.requireNonNull(dealer_nofm.getText()).toString().trim().isEmpty() && !dealer_city.getText().toString().trim().isEmpty() && !dealer_state.getText().toString().trim().isEmpty() );

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}