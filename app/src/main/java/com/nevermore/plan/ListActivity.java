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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
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
    @Bind(R.id.ll_c)
    LinearLayout llC;
    private Typeface luoli;
    private Random random;
    private ItemDialog dialog;
    private RecordAdapter madapter;
    private RecordAdapter1 recordAdapter1;
    private BaseMultiItemQuickAdapter adapter;
    PopupWindow popupWindow;


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

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new Decor());

        madapter = new RecordAdapter(list);

        adapter = madapter;

        recordAdapter1 = new RecordAdapter1(list);

        recyclerView.setAdapter(recordAdapter1);

        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.tv_content:
                        deleteRecord(adapter, position, recordDao);
                        break;
                    case R.id.iv_record:
                        LayoutInflater from = LayoutInflater.from(ListActivity.this);
                        View view1 = from.inflate(R.layout.broswer_im, null);
                        ImageView bv = (ImageView) view1.findViewById(R.id.iv_img);

                        Record recodr = (Record) adapter.getData().get(position);

                        Glide.with(ListActivity.this).load("file://" + recodr.getImgpath()).error(R.mipmap.zanwu).into(bv);
                        bv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popupWindow != null) {
                                    popupWindow.dismiss();
                                }
                            }
                        });
                        popupWindow = new PopupWindow(view1, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
                        popupWindow.setAnimationStyle(R.style.poStyle);
                        popupWindow.showAtLocation(llC, Gravity.CENTER, 0, 0);


                        break;
                }
            }
        });


        luoli = Typeface.createFromAsset(getAssets(), "font/luoli.ttc");

    }

    private void deleteRecord(final BaseQuickAdapter adapter, final int position, final RecordDao recordDao) {
        final Record record = (Record) adapter.getData().get(position);
        dialog.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordDao.delete(record);
                adapter.remove(position);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_borwse_way, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        RecyclerView.LayoutManager manager;

        switch (item.getItemId()) {
            case R.id.br_line:
                manager = new LinearLayoutManager(this);
                adapter = this.madapter;
                break;
            case R.id.br_line_horzontal:

                LinearLayoutManager m = new LinearLayoutManager(this);
                m.setOrientation(LinearLayoutManager.HORIZONTAL);
                adapter = this.madapter;
                manager = m;
                break;
            case R.id.br_pubu:

                manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                adapter = this.recordAdapter1;
                break;
            case R.id.br_pubu_horizontal:
                manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                adapter = this.madapter;
                break;
            default:
                manager = new LinearLayoutManager(this);
                adapter = this.madapter;
                break;

        }

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


        return true;

    }

    class RecordAdapter extends BaseMultiItemQuickAdapter<Record, BaseViewHolder> {
        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public RecordAdapter(List<Record> data) {
            super(data);
            addItemType(0, R.layout.item_record);
            addItemType(1, R.layout.item_record_1);

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
            helper.setText(R.id.tv_time, s);
            helper.addOnClickListener(R.id.tv_content);

            int itemViewType = helper.getItemViewType();
            if (1 == itemViewType) {
                ImageView imageView = helper.getView(R.id.iv_record);
                Glide.with(ListActivity.this).load("file://" + item.getImgpath()).error(R.mipmap.zanwu).into(imageView);
                helper.addOnClickListener(R.id.iv_record);
            }
        }
    }

    class RecordAdapter1 extends BaseMultiItemQuickAdapter<Record, BaseViewHolder> {
        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public RecordAdapter1(List<Record> data) {
            super(data);
            addItemType(0, R.layout.item_record);
            addItemType(1, R.layout.item_record_2);

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
            helper.setText(R.id.tv_time, s);
            helper.addOnClickListener(R.id.tv_content);

            int itemViewType = helper.getItemViewType();
            if (1 == itemViewType) {
                ImageView imageView = helper.getView(R.id.iv_record);
                Glide.with(ListActivity.this).load("file://" + item.getImgpath()).error(R.mipmap.zanwu).into(imageView);
                helper.addOnClickListener(R.id.iv_record);
            }
        }
    }


    class Decor extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = 15;
            outRect.top = 15;
            outRect.left = 15;
            outRect.right = 15;
        }
    }
}
