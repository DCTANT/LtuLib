package com.tstdct.lib;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Class :
 * Create : Chisalsoft-Dechert
 * Company: www.chisalsoft.com
 * Time : 2017/8/17
 */
public class EditTextUtil {
	private static final String TAG=EditTextUtil.class.getName();

	public static  void limitWord(final TextView editText, final int limitWords, final OnFailListener onFailListener) {
		final boolean[] isTip = {false};
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				int start = editText.getSelectionStart();
				if (s.length() > limitWords) {
					s.delete(start - 1, start);
					if (!isTip[0]) {
						isTip[0] = true;
						onFailListener.onFail();
//						Toast.makeText(context, "意见反馈最多200字", Toast.LENGTH_SHORT).show();
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								isTip[0] = false;
							}
						}, 2000);
					}
				}
			}
		});
	}

	public static boolean isEmpty(TextView textView,OnFailListener onFailListener){
		if(textView.getText().toString().trim().length()==0){
			onFailListener.onFail();
			return true;
		}else{
			return false;
		}
	}

	public static boolean isEmpty(TextView editText){
		if(editText.getText().toString().trim().length()==0){
			return true;
		}else{
			return false;
		}
	}

	public static void limitDecimal(final TextView editText, final int limitDecimalLength, final OnFailListener onFailListener) {
		final boolean[] isTip = {false};
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String input = s.toString();
				int dot = s.toString().indexOf(".");
				if (dot != -1) {
					if ((input.length() - dot - 1) > limitDecimalLength) {
						int start = editText.getSelectionStart();
						s.delete(start - 1, start);
						if (!isTip[0]) {
							isTip[0] = true;
							onFailListener.onFail();
//						Toast.makeText(context, "意见反馈最多200字", Toast.LENGTH_SHORT).show();
							new Handler().postDelayed(new Runnable() {
								@Override
								public void run() {
									isTip[0] = false;
								}
							}, 2000);
						}
					}
				}
			}
		});
	}

	public static void limitInputName(final TextView editText, final OnFailListener onFailListener) {
		final boolean[] isTip = {false};
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String input = s.toString();
//				Log.e(TAG,"目前的字符串为："+s.toString());
				char[] inputChar=input.toCharArray();
				for(int i=0;i<inputChar.length;i++){
					char c=inputChar[i];
					if(isLowCaseChar(c) || isUpperCaseChar(c) || isChineseCharacters(c)){
						//通过
					}else{//不通过姓名验证
						int start = editText.getSelectionStart();
						s.delete(start - 1, start);
//						inputChar[i]=' ';
						if (!isTip[0]) {
							isTip[0] = true;
							onFailListener.onFail();
							new Handler().postDelayed(new Runnable() {
								@Override
								public void run() {
									isTip[0] = false;
								}
							}, 2000);
						}
						break;
					}

				}
			}
		});
	}

	public static void setOnWordLengthListener(TextView editText, final int length, final OnWordLengthListener onWordLengthListener) {
		final boolean[] isTip = {false};
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String input = s.toString();
				if(input.length()==length){
					onWordLengthListener.onWordLengthListener(input);
				}
			}
		});
	}

    public static void setOnEditTextNotEmptyListener(TextView editText, final OnEditTextNotEmptyListener onEditTextNotEmptyListener) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if(input.length()>0){
                    onEditTextNotEmptyListener.listener();
                }

            }
        });
    }

    public static void setOnEditTextEmptyListener(TextView editText, final OnEditTextEmptyListener onEditTextEmptyListener) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if(input.length()==0){
                    onEditTextEmptyListener.listener();
                }

            }
        });
    }

    public static void compositeJudgeInEditText(EditText editText,int compositeType){


	}

	/**
	 * 对EditText列表来判断是否是空的EditText
	 * @param editTextList TextView的列表
	 * @param emptyWordList 如果为空的提示语，需要与View一一对应
	 * @return 只要有一个空的就是返回true，全部有值才为false
	 */
	public static  boolean isEmptyEditTextList(ArrayList<TextView> editTextList, ArrayList<String> emptyWordList,OnFailListener onFailListener){
    	for(int i=0;i<editTextList.size();i++){
    		if(EditTextUtil.isEmpty(editTextList.get(i),onFailListener)){
    			return true;
			}
		}
		return false;
	}

	public static void setOnEditTextLengthChangeListener(TextView editText, final OnEditTextLengthChangeListener onEditTextLengthChangeListener) {
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String input = s.toString();
				if(input.length()==0){
					onEditTextLengthChangeListener.onEmpty();
				}else{
					onEditTextLengthChangeListener.onSizeChange(input.length());
				}

			}
		});
	}

//	/**
//	 * 隐藏或者显示提示编辑框空的星星（一般为红色的）
//	 * @param editTexts
//	 * @param stars
//	 */
//	public static void showOrHideEmptyStar(ArrayList<TextView> editTexts, final ArrayList<TextView> stars) {
//		for (int i = 0; i < editTexts.size(); i++) {
//			final int finalI = i;
//			EditTextUtil.setOnEditTextLengthChangeListener(editTexts.get(i), new EditTextUtil.OnEditTextLengthChangeListener() {
//				@Override
//				public void onEmpty() {
//					if(stars.get(finalI)!=null){
//						stars.get(finalI).setVisibility(View.VISIBLE);
//					}
//
//				}
//
//				@Override
//				public void onSizeChange(int length) {
//					if(stars.get(finalI)!=null){
//						stars.get(finalI).setVisibility(View.GONE);
//					}
//				}
//			});
//		}
//	}

	public static void dynamicIsEditTextEmptyList(final ArrayList<TextView> inputViewList, final OnDynamicListEmptyListener onDynamicListEmptyListener){

		for(int i=0;i<inputViewList.size();i++){
			TextView textView=inputViewList.get(i);
			setOnEditTextLengthChangeListener(textView, new OnEditTextLengthChangeListener() {
				@Override
				public void onEmpty() {
					onDynamicListEmptyListener.onEmpty();
				}

				@Override
				public void onSizeChange(int length) {
					boolean isEmpty=false;
					for(int j=0;j<inputViewList.size();j++){
						if(isEmpty(inputViewList.get(j))){
							isEmpty=true;
							onDynamicListEmptyListener.onEmpty();
						}
					}
					if(!isEmpty){
						onDynamicListEmptyListener.onFull();
					}

				}
			});
		}

	}

	private static boolean isLowCaseChar(char c){
		int code=(int)c;
		if(code>=97 && code<=122){
			return  true;
		}
		return false;
	}

	private static boolean isUpperCaseChar(char c){
		int code=(int)c;
		if(code>=65 && code<=90){
			return  true;
		}
		return false;
	}

	private static boolean isChineseCharacters(char c){
		int code=(int)c;
		if(code>=0x4E00 && code<=0x9FA5){
			return true;
		}
		return false;
	}

	public interface OnWordLengthListener{
		void onWordLengthListener(String text);
	}

	public interface OnEditTextNotEmptyListener{
        void listener();
    }

    public interface OnEditTextEmptyListener{
        void listener();
    }

    public interface OnEditTextLengthChangeListener{
		void onEmpty();
		void onSizeChange(int length);
	}

	public interface OnDynamicListEmptyListener{
		void onEmpty();
		void onFull();
	}

	public interface OnFailListener {
		void onFail();
	}
}
