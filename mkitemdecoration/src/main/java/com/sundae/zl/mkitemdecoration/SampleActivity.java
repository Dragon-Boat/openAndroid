package com.sundae.zl.mkitemdecoration;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {
	RecyclerView recyclerView;
	Adapter adapter;
	DemoVM demoVM;
	Button left, middle, right;
	ViewGroup viewGroup;
	MKItemDecoration itemDecoration;

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

	class DemoVM extends AbstractViewModel<String> {

		public DemoVM(Context context, List<String> data, @LayoutRes int resId) {
			super(context, data, resId);
		}

		@Override
		public void bindView(MKItemDecoration.VHolder holder, final int position) {
			TextView textView = holder.getView(R.id.demo_vm);
			textView.setText(data.get(4 * (position / 4)));
			TextView textView2 = holder.getView(R.id.tv2);
			textView2.setText(data.get(4 * (position / 4)));
			TextView textView3 = holder.getView(R.id.tv3);
			textView3.setText(data.get(position));


			holder.getRootView().setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("simpleActivity", "onClick: ");
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.id_color_divider:
				MKItemDecoration decoration = new MKItemDecoration.Builder()
						.height(50)
						.color(Color.parseColor("#525D97"))
						.textSize(30)
						.textColor(Color.WHITE)
						.itemOffset(3)
						.build();
				if (itemDecoration != null) {
					recyclerView.removeItemDecoration(itemDecoration);

				}
				viewGroup.setVisibility(View.GONE);
				itemDecoration = decoration;
				recyclerView.addItemDecoration(itemDecoration);

				break;
			case R.id.id_text_hover:
				viewGroup.setVisibility(View.VISIBLE);

				MKItemDecoration decoration2 = new MKItemDecoration.Builder()
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
						.textAlign(MKItemDecoration.Builder.ALIGN_MIDDLE)
						.build();
				if (itemDecoration != null) {
					recyclerView.removeItemDecoration(itemDecoration);

				}
				itemDecoration = decoration2;
				recyclerView.addItemDecoration(itemDecoration);
				break;
			case R.id.id_custom_hover:
				viewGroup.setVisibility(View.GONE);

				MKItemDecoration decoration3 = new MKItemDecoration.Builder()
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
						.build();
				if (itemDecoration != null) {
					recyclerView.removeItemDecoration(itemDecoration);

				}
				itemDecoration = decoration3;
				recyclerView.addItemDecoration(itemDecoration);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
		recyclerView = (RecyclerView) findViewById(R.id.sample_rv);
		left = (Button) findViewById(R.id.left);
		middle = (Button) findViewById(R.id.middle_btn);
		right = (Button) findViewById(R.id.right);
		viewGroup = (ViewGroup) findViewById(R.id.text_control);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new Adapter(listData());
		demoVM = new DemoVM(this, adapter.data, R.layout.demo_vm_layout);
		recyclerView.setAdapter(adapter);

		left.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				itemDecoration.getBuilder().textAlign(MKItemDecoration.Builder.ALIGN_LEFT);
				recyclerView.invalidate();
			}
		});

		right.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				itemDecoration.getBuilder().textAlign(MKItemDecoration.Builder.ALIGN_RIGHT);
				recyclerView.invalidate();
			}
		});

		middle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				itemDecoration.getBuilder().textAlign(MKItemDecoration.Builder.ALIGN_MIDDLE);
				recyclerView.invalidate();
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
