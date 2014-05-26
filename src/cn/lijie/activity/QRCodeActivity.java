package cn.lijie.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.lijie.R;
import cn.lijie.utils.QRCodeUtils;

public class QRCodeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode_layout);
		findViewById(R.id.startCreate).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((ImageView)findViewById(R.id.disPlayImage)).setImageBitmap(QRCodeUtils.createImage("ÄãºÃ Éµ±ÆÄãºÃ Éµ±ÆÄãºÃ Éµ±ÆÄãºÃ Éµ±ÆÄãºÃ Éµ±ÆÄãºÃ Éµ±ÆÄãºÃ Éµ±ÆÄãºÃ Éµ±Æ"));
				
			}
		});
	}

}
