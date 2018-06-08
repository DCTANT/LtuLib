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


public class TextDialog extends Dialog
{
	private TextView textView_title;
	private TextView textView_content;
	private Button button_commit;
	private Button button_cancel;
	private LinearLayout layout_content;

	private TextDialogListener textDialogListener;
	public TextDialog(Context context)
	{
		super(context, R.style.MMTheme_DataSheet);
		init(context);
	}

	private void init(Context context)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_text, null);
		textView_title = (TextView) layout.findViewById(R.id.dialog_title);
		textView_content = (TextView) layout.findViewById(R.id.dialog_content);
		button_commit = (Button) layout.findViewById(R.id.dialog_commit);
		button_cancel = (Button) layout.findViewById(R.id.dialog_cancel);
		layout_content = (LinearLayout) layout.findViewById(R.id.layout_content);

//		Window w = getWindow();
//		WindowManager.LayoutParams lp = w.getAttributes();
//		lp.x = 0;
//		lp.y = 0;
//		lp.gravity = Gravity.CENTER;
//		onWindowAttributesChanged(lp);

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
				if(textDialogListener!=null)
				{
					textDialogListener.onCommit();
				}
			}
		});
		button_cancel.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				dismiss();
				if(textDialogListener!=null)
				{
					textDialogListener.onCancel();
				}
			}
		});
	}

	public interface TextDialogListener
	{
		void onCommit();
		void onCancel();
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

	public void setCancel(CharSequence cancel)
	{
		button_cancel.setText(cancel);
	}

	public void setCommit(CharSequence commit)
	{
		button_commit.setText(commit);
	}

	public TextDialogListener getTextDialogListener()
	{
		return textDialogListener;
	}

	public void setTextDialogListener(TextDialogListener textDialogListener)
	{
		this.textDialogListener = textDialogListener;
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

	public Button getButton_cancel()
	{
		return button_cancel;
	}

	public void setButton_cancel(Button button_cancel)
	{
		this.button_cancel = button_cancel;
	}


	@Override
	public void show() {
		if (textView_title.getText().length() == 0) {
			textView_title.setVisibility(View.GONE);
			layout_content.setBackgroundResource(R.drawable.shape_circle_dialog_top);
			textView_content.setPadding(DensityUtil.dip2px(getContext(), 5),
					DensityUtil.dip2px(getContext(), 15),
					DensityUtil.dip2px(getContext(), 5),
					DensityUtil.dip2px(getContext(), 0));
		} else if (textView_content.getText().length() == 0) {
			textView_content.setVisibility(View.GONE);
		}

		super.show();
	}
}
