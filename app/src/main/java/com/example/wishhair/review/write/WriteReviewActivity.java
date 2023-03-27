package com.example.wishhair.review.write;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.wishhair.CustomTokenHandler;
import com.example.wishhair.R;
import com.example.wishhair.sign.UrlConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WriteReviewActivity extends AppCompatActivity {
    private static final String TAG = "WriteReviewActivity";

    private Button btn_del, btn_addPicture, btn_back, btn_submit;
    private EditText editText_content;

    private RecyclerView recyclerView;
    private WriteReviewAdapter writeReviewAdapter;

    private ArrayList<Uri> items = new ArrayList<>();

    private RatingBar ratingBar;

    private final WriteRequestData writeRequestData = new WriteRequestData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity_write);
//        accessToken
        CustomTokenHandler customTokenHandler = new CustomTokenHandler(this);
        String accessToken = customTokenHandler.getAccessToken();

//        back
        btn_back = findViewById(R.id.toolbar_btn_back);
        btn_back.setOnClickListener(view -> finish());

        recyclerView = findViewById(R.id.write_review_picture_recyclerView);
        btn_del = findViewById(R.id.review_item_write_btn_delete);

//        RatingBar
        ratingBar = findViewById(R.id.write_review_ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float choice, boolean fromUser) {
                writeRequestData.setRating(choice);
                Log.d("setRating", writeRequestData.getRating());
            }
        });

//        addPicture
        btn_addPicture = findViewById(R.id.write_review_addPicture);
        btn_addPicture.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2222);
        });

//        delete
        btn_del = findViewById(R.id.review_item_write_btn_delete);

//        content
        editText_content = findViewById(R.id.write_review_content);

//        submit
        btn_submit = findViewById(R.id.write_review_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volleyer(accessToken);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
        } else {
            if (data.getClipData() == null) { //이미지를 하나만 선택한경우
                Uri imageUri = data.getData();
                items.add(imageUri);

                writeReviewAdapter = new WriteReviewAdapter(items, getApplicationContext());
                recyclerView.setAdapter(writeReviewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
            } else { // 이미지 여러장
                ClipData clipData = data.getClipData();
//                이미지 선택 갯수 제한
//                !TODO : 이미지 여러장 나눠서 첨부하면 4장이상 들어감 >> items maxsize 설정해서 하면 될 것 같음
                if (clipData.getItemCount() > 4) {
                    Toast.makeText(this, "사진은 4장까지만 선택 가능합니다", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageURI = clipData.getItemAt(i).getUri();
                        try {
                            items.add(imageURI);
                        } catch (Exception e) {
                            Log.e(TAG, "file select error", e);
                        }
                    }
                    writeReviewAdapter = new WriteReviewAdapter(items, getApplicationContext());
                    recyclerView.setAdapter(writeReviewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true));
                }
            }
        }
    }

//    private JSONObject writeContent() {    }

    private void writeRequest(ArrayList<Uri> imageUris, String accessToken) {
        String URL_WRITE = UrlConst.URL + "/api/review";

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_WRITE, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Log.d("upload ", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("upload error", "뚜뚜뚜뚜뚜뚜뚜뚜뚜뚜");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap();
                params.put("Authorization", "bearer" + accessToken);
                return params;
            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("hairStyleId", "11");
                params.put("score", "S3");
                params.put("contents", "당근");
                return params;
            }

            @Override
            public Map<String, DataPart> getByteData() {
                Map<String, DataPart> files = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                Log.d("items size", String.valueOf(items.size()));
                for (int i = 0; i < items.size(); i++) {
                    InputStream inputStream = null;
                    Drawable drawable = null;
                    try {
                        inputStream = getContentResolver().openInputStream(items.get(i));
                        drawable = Drawable.createFromStream(inputStream, items.get(i).toString());
                        Log.d("send drawable", drawable.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    files.put("files", new DataPart("images" + i, AppHelper.getFileDataFromDrawable(getBaseContext(), drawable), "image/jpeg"));
                }

                Log.d("files size", String.valueOf(files.size()));

                return files;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(volleyMultipartRequest);
    }

    private void volleyer(String accessToken) {

        String URL_WRITE = UrlConst.URL + "/api/review";
        String boundary = "apiclient-" + System.currentTimeMillis(); // boundary 값 생성

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL_WRITE, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                // 파일 업로드 성공 처리
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String message = result.getString("message");
                    Log.d("volleyer suc", message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: volleyer fail");
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("hairStyleId", "11");
                params.put("score", "S3");
                params.put("contents", "당근");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                for (int i = 0; i < items.size(); i++) {
                    Uri fileUri = items.get(i); // 갤러리에서 선택한 이미지 Uri
                    String fileName = getFileName(fileUri); // 선택한 이미지 파일 이름

                    try {
                        InputStream iStream = getContentResolver().openInputStream(fileUri);
                        byte[] inputData = getBytes(iStream);
                        params.put("files", new DataPart(fileName, inputData));
                        Log.d("input files", params.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer" + accessToken);
                headers.put("Content-Type", "multipart/form-data;boundary=" + boundary);
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(multipartRequest);
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (index != -1) {
                    result = cursor.getString(index);
                } else {
                    throw new RuntimeException("Column not found");
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    /*private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }*/

    public byte[] getBytes(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
