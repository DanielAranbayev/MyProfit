package com.example.myprofit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BmiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BmiFragment extends Fragment implements View.OnClickListener {
    private EditText WeightET, HeightET;
    private TextView BmiTV, ResultTV;
    private TextView btnCalculate;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BmiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BmiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BmiFragment newInstance(String param1, String param2) {
        BmiFragment fragment = new BmiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bmi, container, false);//inflate




        WeightET= (EditText) rootView.findViewById(R.id.WeightET);
        HeightET= (EditText) rootView.findViewById(R.id.HeightET);
        BmiTV= (TextView) rootView.findViewById(R.id.BmiTV);
        ResultTV= (TextView) rootView.findViewById(R.id.ResultTV);
        btnCalculate= (TextView) rootView.findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(this);

        return rootView;
    }
    @Override
    public void onClick(View view) {
        if (view == btnCalculate) {
            calculateBMI();
        }

    }
    private void calculateBMI() {
        String weightStr = WeightET.getText().toString();
        String heightStr = HeightET.getText().toString();

        if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr) / 100; // Convert height to meters

            float bmi = weight / (height * height);

            displayResult(bmi);
        } else {
            ResultTV.setText("Please enter weight and height");
        }
    }
    private void displayResult(float bmi) {
        String result;
        if (bmi < 18.5) {
            result = "Underweight";
        } else if (bmi < 25) {
            result = "Normal weight";
        } else if (bmi < 30) {
            result = "Overweight";
        } else {
            result = "Obese";
        }
        String formattedBMI = String.format("%.2f", bmi);

        BmiTV.setText(String.format("BMI: " + formattedBMI));
        ResultTV.setText(String.format("Result: " + result));
    }

}