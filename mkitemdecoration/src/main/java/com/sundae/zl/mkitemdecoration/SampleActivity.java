package com.sundae.zl.mkitemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {
	RecyclerView recyclerView;
	int mSize = 25;
	int baseIndex = 0;



	private class SampleItemDecoration extends RecyclerView.ItemDecoration {

		private Drawable divider;
		public SampleItemDecoration() {
			super();
			divider = new ColorDrawable(Color.parseColor("#525D97"));
		}

		@Override
		public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//			int left;
//			int right;
//			int top;
//			int bottom;
//
//			final int childCount = parent.getChildCount();
//			for (int i = 0; i < childCount; i++) {
//				final View view = parent.getChildAt(i);
//				int pos = parent.getChildAdapterPosition(view);
////				if (pos < baseIndex || pos == parent.getAdapter().getItemCount()-1) {
////					continue;
////				}
//
//				top = view.getBottom();
//				left = view.getPaddingLeft() + mSize;
//				right = view.getWidth() - view.getPaddingRight() - mSize ;
//				bottom = top + mSize;
//				divider.setBounds(left, top, right, bottom);
//				Log.d("sample", divider.getBounds().flattenToString());
//				divider.draw(c);
//			}

		}

		@Override
		public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

			int left;
			int right;
			int top;
			int bottom;

			final int childCount = parent.getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View view = parent.getChildAt(i);
				int pos = parent.getChildAdapterPosition(view);
//				if (pos < baseIndex || pos == parent.getAdapter().getItemCount()-1) {
//					continue;
//				}

				top = view.getBottom();
				left = view.getPaddingLeft() + mSize;
				right = view.getWidth() - view.getPaddingRight() - mSize ;
				bottom = top + mSize;
				divider.setBounds(left, top, right, bottom);
				Log.d("sample", divider.getBounds().flattenToString());
				divider.draw(c);
			}

//			int left;
//			int right;
//			int top;
//			int bottom;
//			left = parent.getPaddingLeft();
//			right = parent.getWidth() - parent.getPaddingRight();
//			top = parent.getPaddingTop();
//			bottom = top + mSize;
//
//
//			LinearLayoutManager lm = ((LinearLayoutManager) parent.getLayoutManager());
//			int firstViewPos = lm.findFirstVisibleItemPosition();
//			RecyclerView.ViewHolder firstView = parent.findViewHolderForLayoutPosition(firstViewPos);
//			int dy = firstView.itemView.getTop()-mSize*2 ;
//			boolean  flag = false;
//			if (dy <= 0) {
//				c.save();
//				c.translate(0, dy);
//				flag = true;
//			}
//			divider.setBounds(left, top, right, bottom);
//			divider.draw(c);
//			if (flag) {
//				c.restore();
//			}

		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			outRect.set(50, 50, 50, 50);
		}
	}

	private class Adapter extends RecyclerView.Adapter {
		List<String> data;

		Adapter(List<String> data) {
			super();
			this.data = data;
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new SampleVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_vh, parent, false));

		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
			((SampleVH) holder).onBind(data.get(position));
		}

		@Override
		public int getItemCount() {
			return data.size();
		}
	}

	private class SampleVH extends RecyclerView.ViewHolder {
		TextView textView;

		SampleVH(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.item_tv);
		}

		void onBind(String text) {
			textView.setText(text);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
		recyclerView = (RecyclerView) findViewById(R.id.sample_rv);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		final Adapter adapter = new Adapter(listData());
		DemoVM demoVM = new DemoVM(this,adapter.data, R.layout.demo_vm_layout);
		recyclerView.addItemDecoration(new MKItemDecoration.Builder()
				.height(50)
				.color(Color.parseColor("#525D97"))
				.textSize(30)
				.textColor(Color.WHITE)
				.itemOffset(0)
				.iHover(new IHover() {
					@Override
					public boolean isGroup(int position) {
						return position % 4 == 0;
					}

					@Override
					public String groupText(int position) {
						return adapter.data.get(4 * (position / 4));
					}
				})
				.viewModel(demoVM)
				.textAlign(MKItemDecoration.Builder.ALIGN_MIDDLE)
				.build());



		recyclerView.setAdapter(adapter);

		findViewById(R.id.rect_dec).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSize-=10;
				adapter.notifyDataSetChanged();
			}
		});
		findViewById(R.id.rect_inc).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSize+=10;
				adapter.notifyDataSetChanged();
			}
		});

	}

	private List<String> listData() {
		List<String> data = new ArrayList<>();
		int max = (int) (Math.random() * 20 + 20);
		for (int i = 0; i < max; i++) {
			data.add("item#" + i);
		}
		return data;
	}

	class DemoVM extends AbstractViewModel<String>{
		public DemoVM(List<String> data, View view) {
			super(data, view);
		}

		public DemoVM(Context context, List<String> data, @LayoutRes int resId) {
			super(context, data, resId);
		}

		@Override
		public void bindView(View view, int position) {
			TextView textView = (TextView) view.findViewById(R.id.demo_vm);
			textView.setText(data.get(position));

		}
	}
}
