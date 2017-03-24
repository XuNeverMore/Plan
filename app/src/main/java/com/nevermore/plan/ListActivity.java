package com.nevermore.plan;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.nevermore.base.BaseActivity;
import com.nevermore.bean.Record;
import com.nevermore.bean.RecordDao;
import com.nevermore.common.MyApp;
import com.nevermore.dialog.ItemDialog;
import com.nevermore.util.DateUtil;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tool_bar)
    Toolbar toolBar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.activity_list_activity)
    LinearLayout activityListActivity;
    private Typeface luoli;
    private Random random;
    private ItemDialog dialog;
    private RecordAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activity);
        ButterKnife.bind(this);

        random = new Random();
        setTitle("所有记录");

        dialog = new ItemDialog(this);


        final RecordDao recordDao = MyApp.getInstance().getRecordDao();
        final List<Record> list = recordDao.queryBuilder().build().list();

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new Decor());

        madapter = new RecordAdapter( list);

        recyclerView.setAdapter(madapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, final int position) {
                final Record record = (Record) adapter.getData().get(position);
                dialog.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recordDao.delete(record);
                        madapter.remove(position);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        luoli = Typeface.createFromAsset(getAssets(), "font/luoli.ttc");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_borwse_way,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        RecyclerView.LayoutManager manager;

        switch (item.getItemId()){
            case R.id.br_line:
                 manager = new LinearLayoutManager(this);
                break;
            case R.id.br_line_horzontal:

                LinearLayoutManager m = new LinearLayoutManager(this);
                m.setOrientation(LinearLayoutManager.HORIZONTAL);

                manager = m;
                break;
            case R.id.br_pubu:

                 manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

                break;
            case R.id.br_pubu_horizontal:
                 manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);

                break;
            default:
                manager = new LinearLayoutManager(this);
                break;

        }

        recyclerView.setLayoutManager(manager);
        madapter.notifyDataSetChanged();


        return true;

    }

    class RecordAdapter extends BaseMultiItemQuickAdapter<Record,BaseViewHolder> {

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public RecordAdapter(List<Record> data) {
            super(data);
            addItemType(0,R.layout.item_record);
            addItemType(1,R.layout.item_record_1);

        }

        @Override
        protected void convert(BaseViewHolder helper, Record item) {

            TextView tv = helper.getView(R.id.tv_content);
            tv.setTypeface(luoli);
            tv.setText(item.getText());
            CardView cv = helper.getView(R.id.card);
            cv.setCardElevation(random.nextInt(20));
            cv.setCardBackgroundColor(Color.parseColor(item.getColor()));

            String s = DateUtil.getTimeString(item.getTime());
            helper.setText(R.id.tv_time,s);

            int itemViewType = helper.getItemViewType();
            if(1==itemViewType){
                ImageView imageView = helper.getView(R.id.iv_record);
                Glide.with(ListActivity.this).load("file://"+item.getImgpath()).into(imageView);
            }
        }
    }



    class Decor extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom =15;
            outRect.top =15;
            outRect.left =15;
            outRect.right =15;
        }
    }
}
