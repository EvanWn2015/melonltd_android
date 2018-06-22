package com.melonltd.naber.view.user.page;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.common.base.Strings;
import com.melonltd.naber.model.api.ApiManager;
import com.melonltd.naber.model.api.ThreadCallback;
import com.melonltd.naber.model.constant.NaberConstant;
import com.melonltd.naber.model.service.SPService;
import com.melonltd.naber.model.type.Identity;
import com.melonltd.naber.util.LoadingBarTools;
import com.melonltd.naber.util.PhotoTools;
import com.melonltd.naber.util.UpLoadCallBack;
import com.melonltd.naber.view.common.BaseCore;
import com.melonltd.naber.view.factory.PageType;
import com.melonltd.naber.view.user.UserMainActivity;
import com.melonltd.naber.vo.AccountInfoVo;
import com.melonltd.naber.vo.ReqData;
import com.melonltd.naber.R;

import java.io.ByteArrayOutputStream;

public class AccountDetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = AccountDetailFragment.class.getSimpleName();
    public static AccountDetailFragment FRAGMENT = null;
    public static int TO_RESET_PASSWORD_INDEX = -1;
    private static final int PICK_FROM_CAMERA = 9902;
    private static final int PICK_FROM_GALLERY = 9909;
    private ViewHolder holder;

    public AccountDetailFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new AccountDetailFragment();
            TO_RESET_PASSWORD_INDEX = -1;
        }
        if (bundle != null) {
            FRAGMENT.setArguments(bundle);
        }
        return FRAGMENT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.user_account_detail_page) == null) {
            View v = inflater.inflate(R.layout.fragment_account_detail, container, false);
            getView(v);
            container.setTag(R.id.user_account_detail_page, v);
            return v;
        } else {
            return (View) container.getTag(R.id.user_account_detail_page);
        }
    }

    private void getView(View v) {
        holder = new ViewHolder(v);
        Button logoutBtn = v.findViewById(R.id.logoutBtn);
        Button toResetPasswordBtn = v.findViewById(R.id.toResetPasswordBtn);
        logoutBtn.setOnClickListener(this);
        toResetPasswordBtn.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
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
        } else {
            holder.accountInfo = (AccountInfoVo) getArguments().getSerializable(NaberConstant.ACCOUNT_INFO);
            Log.d(TAG, holder.accountInfo.toString());
            if (holder.accountInfo != null) {
                holder.nameText.setText(holder.accountInfo.name);
                holder.phoneText.setText(holder.accountInfo.phone);
                holder.emailText.setText(holder.accountInfo.email);
                holder.birthdayText.setText(holder.accountInfo.birth_day);
                holder.bonusText.setText(holder.accountInfo.bonus);

                holder.identityText.setText(Identity.of(holder.accountInfo.identity).name);
                if (!Strings.isNullOrEmpty(holder.accountInfo.photo)) {
                    holder.avatarImage.setImageURI(Uri.parse(holder.accountInfo.photo));
                } else {
                    ImageRequest request = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo).build();
                    holder.avatarImage.setImageURI(request.getSourceUri());
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case PICK_FROM_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    break;
                case PICK_FROM_GALLERY:
                    Uri pickedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), pickedImage);
                    } catch (Exception e) {
                        // Manage exception ...
                    }
                    break;
            }

            if (bitmap != null && bitmap.getByteCount() != 0){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap = PhotoTools.sampleBitmap(bitmap, 120);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                byte[] bytes = out.toByteArray();
                final AlertDialog dialog = LoadingBarTools.newLoading(getContext());
                PhotoTools.upLoadImage(bytes, NaberConstant.STORAGE_PATH_USER, holder.accountInfo.account_uuid + ".jpg", new UpLoadCallBack() {
                    @Override
                    public void getUri(final Uri uri) {
                        ReqData req = new ReqData();
                        req.date = uri.toString();
                        req.uuid = holder.accountInfo.account_uuid;
                        req.type = "USER";
                        ApiManager.uploadPhoto(req, new ThreadCallback(getContext()) {
                            @Override
                            public void onSuccess(String responseBody) {
                                holder.accountInfo.photo = uri.toString();
                                holder.avatarImage.setImageURI(uri);
                                dialog.dismiss();
                            }

                            @Override
                            public void onFail(Exception error, String msg) {
                                dialog.dismiss();
                            }
                        });
                    }
                    @Override
                    public void failure(String errMsg) {
                        ImageRequest request = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.naber_icon_logo).build();
                        holder.avatarImage.setImageURI(request.getSourceUri());
                    }
                });
            }else {
                // TODO 圖片上傳失敗
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        UserMainActivity.navigationIconDisplay(false, null);
    }

    private void backToSetUpPage() {
        SetUpFragment.TO_ACCOUNT_DETAIL_INDEX = -1;
        UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.SET_UP, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoutBtn:
                SPService.removeAll();
                getActivity().finish();
                UserMainActivity.clearAllFragment();
                break;
            case R.id.toResetPasswordBtn:
                toResetPassword(1);
                break;

        }
    }

    private void toResetPassword(int i) {
        TO_RESET_PASSWORD_INDEX = i;
        UserMainActivity.removeAndReplaceWhere(FRAGMENT, PageType.RESET_PASSWORD, null);
    }


    class UpLoadAccountImage implements View.OnClickListener {
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
                            } else if (position == 0) {
                                // 相機權限
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(getActivity(), BaseCore.CAMERA, BaseCore.CAMERA_CODE);
                                } else {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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


    class ViewHolder {
        private AccountInfoVo accountInfo;
        private SimpleDraweeView avatarImage;
        private TextView nameText, phoneText, emailText, birthdayText, identityText, bonusText;

        ViewHolder(View v) {
            this.avatarImage = v.findViewById(R.id.avatarImage);
            this.nameText = v.findViewById(R.id.nameText);
            this.phoneText = v.findViewById(R.id.phoneText);
            this.emailText = v.findViewById(R.id.emailText);
            this.birthdayText = v.findViewById(R.id.birthdayText);
            this.identityText = v.findViewById(R.id.identityText);
            this.bonusText = v.findViewById(R.id.bonusText);
            this.avatarImage.setOnClickListener(new UpLoadAccountImage());
        }
    }
}
