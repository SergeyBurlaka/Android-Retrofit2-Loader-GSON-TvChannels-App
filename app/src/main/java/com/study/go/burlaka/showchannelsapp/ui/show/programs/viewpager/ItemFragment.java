package com.study.go.burlaka.showchannelsapp.ui.show.programs.viewpager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.study.go.burlaka.showchannelsapp.R;
import com.study.go.burlaka.showchannelsapp.data.repo.ProgramRepo;

import java.util.ArrayList;

/**
 * Created by Operator on 23.09.2016.
 */
public class ItemFragment  extends Fragment {

    public static final String ARG_TEXT = "item_text";
    public static final String ARG_POSITION = "item_position";
    public static final String ARG_COUNT = "item_count";
    private ListView lv;
    private View rootView;
    private Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
         rootView = inflater.inflate(
                R.layout.pager_item, container, false);
         args = getArguments();

        ((TextView) rootView.findViewById(R.id.text1)).setText(
                args.getString(ARG_TEXT));

        ((TextView) rootView.findViewById(R.id.txtCount)).setText(
                args.getInt(ARG_POSITION) + " / " + args.getInt(ARG_COUNT));
        lv = (ListView) rootView.findViewById(R.id.program_l_v);

        new CreateProgramAsyncTaskRunner().execute();

        return rootView;
    }



    private void createListView(ArrayList <String> al) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                rootView.getContext(),
                android.R.layout.simple_list_item_1,
                al );
        lv.setAdapter(arrayAdapter);
    }


    private ArrayList<String> getPrograms(String string) {
        ProgramRepo pr = new ProgramRepo();
        return pr.getPrograms4Channel( string);
    }

    private class CreateProgramAsyncTaskRunner extends AsyncTask<String, String , ArrayList <String>> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected  ArrayList <String> doInBackground(String... params) {
          ArrayList <String> programList =  getPrograms (args.getString(ARG_TEXT));
            // getPrograms (args.getString(ARG_TEXT));
           // createListView (getPrograms (args.getString(ARG_TEXT)));
            return programList ;
        }


        @Override
        protected void onPostExecute(ArrayList <String> prigramList) {
            createListView(prigramList);

        }


        @Override
        protected void onPreExecute() {


        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }
}










