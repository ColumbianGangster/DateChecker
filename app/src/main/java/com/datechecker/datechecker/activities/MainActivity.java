package com.datechecker.datechecker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.datechecker.datechecker.R;
import com.datechecker.datechecker.utils.MyValidator;
import com.squareup.picasso.Picasso;

import net.danlew.android.joda.JodaTimeAndroid;
/**
 * Created by Bertrand Brompton.
 */
public class MainActivity extends AppCompatActivity {
    EditText editText;
    ImageView imageView;
    MyValidator myValidator = new MyValidator(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
        setContentView(configViews());
    }

    private LinearLayout configViews(){
        final TextView textView = new TextView(this);
        textView.setMinimumHeight(56);
        textView.setMinimumWidth(272);
        editText = new EditText(this);
        editText.setHint(R.string.date);
//        editText.setInputType(InputType.TYPE_CLASS_DATETIME);
        editText.addTextChangedListener(new TextWatcher() {
            private MyValidator myValidator = new MyValidator(editText.getContext());

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (myValidator.isValid(s.toString())) {
                    Picasso.with(getApplicationContext()).load(R.drawable.ic_circle_1).into(imageView);
                } else {
//                    editText.setError(getString(R.string.error));
                    Picasso.with(getApplicationContext()).load(R.drawable.ic_circle).into(imageView);
                }
            }
        });
//        InputMethodManager imm = (InputMethodManager)
//                    getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        Button button = new Button(this);
        button.setPadding(8, 8, 8, 8);
        button.setText(R.string.okay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString();
                if (myValidator.isValid(s)) {
                    textView.setText(s);
                } else { // do nothing
//                    textView.setError("Incorrect format. Use MM/dd/yyyy if US. Otherwise, use dd/MM/yyyy");
                }
            }
        });

        imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        Picasso.with(this).load(R.drawable.ic_circle).into(imageView);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        LinearLayout layout_inner = new LinearLayout(this);
        layout_inner.setOrientation(LinearLayout.HORIZONTAL);
        layout_inner.addView(editText);
        layout_inner.addView(imageView);
        layout.addView(layout_inner);
        layout.addView(textView);
        layout.addView(button);
        return layout;
    }
}
