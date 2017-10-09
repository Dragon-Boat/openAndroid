package com.sundae.zl.mkitemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
	int mSize = 10;
	int baseIndex = 2;

	boolean isDrawEnd = true;

	private LinearGradient linearGradient = new LinearGradient(
			0, 0, 0, 100, new int[]{Color.YELLOW, 0}, null, Shader.TileMode.CLAMP
	);

	private class SampleItemDecoration extends RecyclerView.ItemDecoration {

		private Drawable divider;
		public SampleItemDecoration() {
			super();
			divider = new ColorDrawable(Color.parseColor("#525D97"));
		}

		@Override
		public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
			int left;
			int right;
			int top;
			int bottom;

			final int childCount = parent.getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View view = parent.getChildAt(i);
				int pos = parent.getChildAdapterPosition(view);
				if (pos <= baseIndex || pos == parent.getAdapter().getItemCount()-1) {
					continue;
				}
				top = view.getBottom();
				left = parent.getPaddingLeft();
				right = parent.getWidth() - parent.getPaddingRight();
				bottom = top + mSize;
				divider.setBounds(left, top, right, bottom);
				Log.d("sample", divider.getBounds().flattenToString());
				divider.draw(c);
			}

		}

		private Paint mPaint = new Paint();
		@Override
		public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

//			int pos = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
//			View child = parent.findViewHolderForLayoutPosition(pos).itemView;
//			divider.setBounds(child.getPaddingLeft(), parent.getPaddingTop(), child.getPaddingLeft() + child.getWidth() - child.getPaddingRight(),
//					parent.getPaddingTop() + mSize);
//			divider.draw(c);
//			mPaint.setShader(linearGradient);
//			c.drawRect(0, 0, parent.getRight(), 100, mPaint);

			int left;
			int right;
			int top;
			int bottom;
			left = parent.getPaddingLeft();
			right = parent.getWidth() - parent.getPaddingRight();
			top = parent.getPaddingTop();
			bottom = top + mSize;


			LinearLayoutManager lm = ((LinearLayoutManager) parent.getLayoutManager());
			int firstViewPos = lm.findFirstVisibleItemPosition();
			RecyclerView.ViewHolder firstView = parent.findViewHolderForLayoutPosition(firstViewPos);
			int dy = firstView.itemView.getTop()-mSize*2 ;
			boolean  flag = false;
			if (dy <= 0) {
				c.save();
				c.translate(0, dy);
				flag = true;
			}
			divider.setBounds(left, top, right, bottom);
			divider.draw(c);
			if (flag) {
				c.restore();
			}


//			final int childCount = parent.getChildCount();
//			for (int i = 0; i < childCount; i++) {
//				final View view = parent.getChildAt(i);
//				int pos = parent.getChildAdapterPosition(view);
//				if (pos <= baseIndex || pos == parent.getAdapter().getItemCount()-1) {
//					continue;
//				}
//				top = view.getBottom();
//				bottom = top + mSize;
//				divider.setBounds(left, top, right, bottom);
//				Log.d("sample", divider.getBounds().flattenToString());
//				divider.draw(c);
//			}
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

			int pos = parent.getChildAdapterPosition(view);
			if (pos <= baseIndex || pos == parent.getAdapter().getItemCount() - 1) {
				outRect.set(0, 0, 0, 0);
			} else {
				outRect.set(0, 0, 0, mSize);
			}
			Log.d("demoZl", "getItemOffsets: ");

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
		recyclerView.addItemDecoration(new MKItemDecoration.Builder()
				.height(50)
				.color(Color.RED)
				.textSize(30)
				.textColor(Color.WHITE)
				.itemOffset(1)
				.build());

		recyclerView.setAdapter(adapter);

		findViewById(R.id.rect_dec).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSize--;
				adapter.notifyDataSetChanged();
			}
		});
		findViewById(R.id.rect_inc).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSize++;
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
}
