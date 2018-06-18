package com.melonltd.naberc.view.user.page.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.helper.okhttp.ApiCallback;
import com.melonltd.naberc.model.helper.okhttp.ApiManager;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.common.factory.PageFragmentFactory;
import com.melonltd.naberc.view.common.type.PageType;
import com.melonltd.naberc.view.user.UserMainActivity;

import java.io.ByteArrayOutputStream;

public class AccountDetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = AccountDetailFragment.class.getSimpleName();
    public static AccountDetailFragment FRAGMENT = null;
    private Button logoutBtn, toResetPasswordBtn;
    private SimpleDraweeView avatarImage;
    public static int TO_RESET_PASSWORD_INDEX = -1;
    private static final int PICK_FROM_CAMERA = 9902;
    private static final int PICK_FROM_GALLERY = 9909;

    public AccountDetailFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new AccountDetailFragment();
            FRAGMENT.setArguments(bundle);
            TO_RESET_PASSWORD_INDEX = -1;
        }
        return FRAGMENT;
    }

    public Fragment newInstance(Object... o) {
        return new AccountDetailFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_account_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_account_detail, container, false);
            getView(v);
            setListener();
            container.setTag(R.id.user_account_detail_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_account_detail_page);
        }
    }

    private void getView(View v) {
        avatarImage = v.findViewById(R.id.avatarImage);
        logoutBtn = v.findViewById(R.id.logoutBtn);
        toResetPasswordBtn = v.findViewById(R.id.toResetPasswordBtn);
    }

    private void setListener() {
        logoutBtn.setOnClickListener(this);
        toResetPasswordBtn.setOnClickListener(this);
        avatarImage.setOnClickListener(new UpLoadAccountImage());
    }

    @Override
    public void onResume() {
        super.onResume();
//        UserMainActivity.toolbar.setTitle(getResources().getString(PageType.equalsIdByName(BaseCore.FRAGMENT_TAG)));
        UserMainActivity.changeTabAndToolbarStatus();
        if (UserMainActivity.toolbar != null) {
            UserMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backToSetUpPage();
                    UserMainActivity.navigationIconDisplay(false, null);
                }
            });
        }
        if (TO_RESET_PASSWORD_INDEX >= 0) {
            toResetPassword(1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_FROM_CAMERA:
                    Bitmap mbmp = (Bitmap) data.getExtras().get("data");
//                    avatarImage.setImageBitmap(mbmp);
                    FirebaseApp api = FirebaseApp.getInstance();
                    FirebaseStorage storage = FirebaseStorage.getInstance("gs://naber-test.appspot.com");
                    StorageReference storageRef = storage.getReference();
                    StorageReference mountainsRef = storageRef.child("users/mountains.jpg");
//                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    mbmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] datas = baos.toByteArray();
                    UploadTask uploadTask = mountainsRef.putBytes(datas);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful uploads
                            Log.e(TAG, "" + e.getMessage());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...3
                            Log.d(TAG, "");
                        }
                    });
                    break;
                case PICK_FROM_GALLERY:
                    Uri uri = data.getData();
                    avatarImage.setImageURI(uri);
                    break;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
    }

    private void backToSetUpPage() {
        BaseCore.FRAGMENT_TAG = PageType.SET_UP.name();
        SetUpFragment.TO_ACCOUNT_DETAIL_INDEX = -1;
        Fragment f = PageFragmentFactory.of(PageType.SET_UP, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoutBtn:
                getActivity().finish();
//                ApiManager.test(new ApiCallback(getContext()) {
//                    @Override
//                    public void onSuccess(String responseBody) {
////                        UserMainActivity.toLoginPage();
//                        getActivity().finish();
//                    }
//
//                    @Override
//                    public void onFail(Exception error, String msg) {
//
//                    }
//                });
                break;
            case R.id.toResetPasswordBtn:
                toResetPassword(1);
                break;

        }
    }

    private void toResetPassword(int i) {
        TO_RESET_PASSWORD_INDEX = i;
        BaseCore.FRAGMENT_TAG = PageType.RESET_PASSWORD.name();
        Fragment f = PageFragmentFactory.of(PageType.RESET_PASSWORD, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.frameContainer, f).commit();
    }


    class UpLoadAccountImage implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            new AlertView.Builder()
                    .setContext(getContext())
                    .setStyle(AlertView.Style.ActionSheet)
                    .setCancelText("返回")
                    .setOthers(new String[]{"相機", "相簿"})
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position == 1) {
                                // 確認寫入權限
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(getActivity(), BaseCore.IO_STREAM, BaseCore.IO_STREAM_CODE);
                                } else {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(intent, PICK_FROM_GALLERY);
                                }
                            } else if (position ==0) {
                                // 相機權限
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(getActivity(), BaseCore.CAMERA, BaseCore.CAMERA_CODE);
                                } else {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, false);
                                    startActivityForResult(intent, PICK_FROM_CAMERA);
                                }
                            }
                        }
                    })
                    .build()
                    .setCancelable(true)
                    .show();
        }
    }
}
