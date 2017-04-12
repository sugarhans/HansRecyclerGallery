package com.example.hans.hansrecyclergallery;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Gallery;
import com.example.hans.hansrecyclergallery.recyclerview.base.ViewHolder;
import com.example.hans.hansrecyclergallery.recyclerview.wrapper.GalleryWrappter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final List<String> strings = new ArrayList<>();
    strings.add("1");
    strings.add("2");
    strings.add("3");
    strings.add("4");
    strings.add("5");
    strings.add("6");
    strings.add("7");
    strings.add("8");
    strings.add("9");

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);

    GalleryWrappter<String> wrappter = new GalleryWrappter<String>(getApplicationContext(),R.layout.item,strings) {
      @Override protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.tv,strings.get(position));
      }
    };

    new LinearSnapHelper().attachToRecyclerView(recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
    recyclerView.setAdapter(wrappter);



  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }














}
