package com.example.govtpolytechnicambalacity.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.govtpolytechnicambalacity.R;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    private ViewPager viewPager;
    private BranchAdapter adapter;
    private List<BranchModel> list;
    private ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        viewPager = view.findViewById(R.id.view_pager);


        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();

            }
        });

        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_computer,"Computer Science","The department offers 3 year regular course leading to Diploma in Computer Engineering, awarded by Haryana State Board of Technical Education, Panchkula. The department was started in 2006 and it has been providing an excellent teaching and learning environment since its inception. The intake of the course is 60 students. The course is aimed to provide students excellent theoretical and practice knowledge of computer operation, maintenance and elementary design aspects of computer.\n"+"\n"+"The department has well trained, committed and experienced faculty who are capable to teach the various theoretical and practical aspects of computer by using advanced pedagogic methodologies. The department has all equipment & facilities for conducting various experiments, exercises and projects as per diploma curriculum of Haryana State Board of Technical Education, Panchkula. Students of final and pre final year are taken to various industrial visits to make them aware of the various activities of the World of Work. Expert lectures are arranged by calling experts from industry relating to computer and also from other organizations to make the students aware of the current activities of the world. Staff members are sent to attend the various seminars and training programs on continuous basis for upgrading their theoretical and practical knowledge of the latest technology. Staff members are also involved in designing & revision of curriculum. The diploma holders of this course finds employment in various Government, Public & Private Enterprises, Small scale & Large scale industries of Computer like Desktop & Laptop Assembly, Web Designing, Networking, Software Development and Testing etc."));
        list.add(new BranchModel(R.drawable.ic_mechanical,"Mechanical","The department of Mechanical Engineering offers a three years Diploma course with a sanctioned intake of 120 students per year under State Board of Technical Education Haryana, based on Semester System with the aim to impart Educational transfer the technology and to train the students & develop overall aptitude towards society and industry."));
        list.add(new BranchModel(R.drawable.ic_electrical,"Electrical","The department of Electrical Engineering is having all modern and updated facilities. The department has Electrical Machine Lab, Measurement Lab, and Electrical wiring lab & Computer Lab for conducting the various experiments / exercises and project as per diploma education program. The various projects (outdoor / indoor) are fabricated and developed as per the syllabus guidelines. This practice creates innovation and development attitude among the young students.\n" +
                "\n" +
                "In the project activity the students are trained to fabricate and assemble voltage stabilizer's power invertors, transformer winding, radio receivers using IC and digital clock etc. The deptt. have 62.5 KVA 3 Phase alternators also which supplies power in the institution at the event of power capacity. The two more alternators of 62.5 KVA of each have been procured under World bank Project which are being installed & to be used for workshop block. Electrical maintenance of the institution is also being done by the department. Above all there are excellent Computer facilities in the Electrical Engineering department. The computer centre of this department has 12 PC with latest features and version. The diploma holders of this department are found employment in various departments of State & Centre i.e. PWD B&R, HVPN, HUDA & Housing Board, MES, Indian Railways, CPWD, Telecom and Private sector. Many of them have become entrepreneurs also. All the cultural activities are also arranged by Electrical Engineering Department."));
        list.add(new BranchModel(R.drawable.ic_electronics,"Electronics","The department offers 3 year regular course leading to Diploma in Electronics & Communication Engineering awarded by Technical Education Haryana. The programme aims at providing the students with theoretical and practical base required for operation, maintenance & elementary design aspects of Electronics. The department has all equipment & facilities for conducting various experiments / exercise / projects as per diploma education programme.\n" +
                "\n" +
                "The students take part in development of various projects and other innovative & developmental activities. Students are taken various industrial visits so as to make them aware of the activities of the World of Work. Expert lectures are also arranged by calling experts from industry relating to Electronics industry, From other organization i.e. Red Cross, I.T, Banks, entrepreneurship Department, Employment Department etc. so as to make students aware of these activities and to develop an integrated personality and well adjusted individual in both society & in profession. Staffs are sent to training on continuous basis to upgrade knowledge. Staffs are also involved in designing & revision of curriculum. The diploma holders from this department found employment in various Government, Public & Private Enterprises, small scale & large scale industries e.g. Computer & Laptop Assembly Industry, Mobile Industry, Television Industry ,Telephone Industry etc. Students are also encouraged to become entrepreneurs."));
        list.add(new BranchModel(R.drawable.ic_arc,"Architecture","The Department of Architecture offers a three years Diploma course with a sanctioned intake of 60 students/year under state board of Technical Education, Haryana based on semester system with the aim of imparting architectural education and training of assistants to develop overall aptitude in architecture and well adjusted both in profession and society."));
        list.add(new BranchModel(R.drawable.ic_civil,"Civil","The department offers a 3 years course leading to Diploma in Civil Engineering awarded by Haryana State Board of technical Education. The department of Civil Engineering has been modernized with latest equipped labs under World Bank Project and provide consultancy in the field of Surveying, Soil, Brick, Cement/Concrete, Water Testing, Building Design & Construction. The department has well equipped Surveying Lab, Soil Engineering Lab, Construction Materials Lab, Concrete Technology Lab, Water Supply & Waste Water Engineering Lab, Building Construction Lab, Fluid Mechanics/Hydraulics Lab, Highway Engineering Lab, CACE/BIT Lab. The Private and Public enterprises such as PWD (B&R, Public Health Department, Irrigation Engineering Department), CPWD, MES, HUDA, BBMB, Pollution Control Board, Electricity Board, Municipal Corporation, Panchayti Raj, Police Housing Corporation, Housing Boards, Indian Railways, Metro Rail Corporations, BHEL, GAIL, NALCO, NHAI, NTPC, and Hydro Power Projects such as Satluj Jal Vidut Nigam etc. are the major source of the employment to the civil engineering diploma holders."));
        list.add(new BranchModel(R.drawable.ic_automobile,"Automobile","The department offers a 3 years course leading to Diploma in Automobile Engineering. The course aims at providing the students with theoretical & practical base required for operational & maintenance aspects of Auto Engineering. The department has fully developed auto shop. It has got latest machines like computerised scanner, computerised wheel alignment, computerised wheel balancing, starter test bench, alternator test bench, cranks half grinder, boring machine & many more. The department has got some self explanatory models of various automobile parts & various automobile circuits which help the students in learning very fast. The students of Automobile department became trained in repair & maintenance of various petrol & diesel vehicles.\n" +
                "\n" +
                "Our students also learn drawing as it is part of their curriculum. It is very encouraging for the students as it is the foremost requirement of the companies in which our students get job. The diploma holders from this department has found employment in various Government & private enterprises like Roadways & the company’s manufacturing cars, tractors & various other automobile parts. Even some have became surveyors in Insurance Companies & some became entrepreneurs in their own right."));
        adapter = new BranchAdapter(getContext(),list);
        viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        ImageView imageView = view.findViewById(R.id.college_image);
        Glide.with(getContext())
                .load("http://gpambala.ac.in/wp-content/uploads/2020/08/0001.jpg")
                .into(imageView);

        return view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q= Government Polytechnic Ambala City");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }
}