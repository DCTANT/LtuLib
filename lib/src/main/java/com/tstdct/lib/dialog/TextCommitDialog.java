package com.tstdct.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tstdct.lib.DensityUtil;
import com.tstdct.lib.R;


public class TextCommitDialog extends Dialog
{
	private TextView textView_title;
	private TextView textView_content;
	private Button button_commit;
	
	private TextCommitDialogListener textCommitDialogListener;
	public TextCommitDialog(Context context)
	{
		super(context, R.style.MMTheme_DataSheet);
		init(context);
	}

	private void init(Context context)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_textcommit, null);
		textView_title = (TextView) layout.findViewById(R.id.dialog_title);
		textView_content = (TextView) layout.findViewById(R.id.dialog_content);
		button_commit = (Button) layout.findViewById(R.id.dialog_commit);

//		Window w = getWindow();
//		WindowManager.LayoutParams lp = w.getAttributes();
//		lp.x = 0;
//		lp.y = 0;
//		lp.gravity = Gravity.CENTER;
//		onWindowAttributesChanged(lp);


//		getWindow().setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
//		WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//		layoutParams.x = (int) (DensityUtil.getScreenWidth(getContext()) * 0.132); // right margin
//		layoutParams.y = 0; // top margin
//		getWindow().setAttributes(layoutParams);
		// e.g. bottom + left margins:
//		getWindow().setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
//		WindowManager.LayoutParams layoutParams1 = getWindow().getAttributes();
//		layoutParams1.x = (int) (DensityUtil.getScreenWidth(getContext()) * 0.132); // left margin
//		layoutParams1.y = 0; // bottom margin
//		getWindow().setAttributes(layoutParams1);
//
		getWindow().setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT | Gravity.RIGHT);
		int padding = (int) (DensityUtil.getScreenWidth(getContext()) * 0.132);
		layout.setPadding(padding, 0, padding, 0);
		setCanceledOnTouchOutside(false);
		setCancelable(false);
		setContentView(layout);
		
		setListener();
	}

	private void setListener()
	{
		button_commit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dismiss();
				
				if(textCommitDialogListener!=null)
				{
					textCommitDialogListener.onCommit();
				}
			}
		});
	}

	public interface TextCommitDialogListener
	{
		void onCommit();
	}

	public void setTitle(CharSequence title)
	{
		if(TextUtils.isEmpty(title))
		{
			textView_title.setVisibility(View.GONE);
		}
		else
		{
			textView_title.setVisibility(View.VISIBLE);
			textView_title.setText(title);
		}
	}

	public void setContent(CharSequence content)
	{
		if(TextUtils.isEmpty(content))
		{
			textView_content.setVisibility(View.GONE);
		}
		else
		{
			textView_content.setVisibility(View.VISIBLE);
			textView_content.setText(content);
		}
	}

	public void setCommit(CharSequence commit)
	{
		button_commit.setText(commit);
	}

	public TextCommitDialogListener getTextCommitDialogListener()
	{
		return textCommitDialogListener;
	}

	public void setTextCommitDialogListener(TextCommitDialogListener textCommitDialogListener)
	{
		this.textCommitDialogListener = textCommitDialogListener;
	}

	public TextView getTextView_title()
	{
		return textView_title;
	}

	public void setTextView_title(TextView textView_title)
	{
		this.textView_title = textView_title;
	}

	public TextView getTextView_content()
	{
		return textView_content;
	}

	public void setTextView_content(TextView textView_content)
	{
		this.textView_content = textView_content;
	}

	public Button getButton_commit()
	{
		return button_commit;
	}

	public void setButton_commit(Button button_commit)
	{
		this.button_commit = button_commit;
	}

	@Override
	public void show() {
		if (textView_title.getText().length() == 0) {
			textView_title.setVisibility(View.GONE);
			textView_content.setPadding(DensityUtil.dip2px(getContext(), 5),
					DensityUtil.dip2px(getContext(), 15),
					DensityUtil.dip2px(getContext(), 5),
					DensityUtil.dip2px(getContext(), 5));
		} else if (textView_content.getText().length() == 0) {
			textView_content.setVisibility(View.GONE);
		}

		super.show();
	}

	@Override
	public void onBackPressed() {
	}
}
