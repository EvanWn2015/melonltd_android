package com.melonltd.naberc.view.seller.page;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Lists;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.constant.NaberConstant;
import com.melonltd.naberc.util.PhotoTools;
import com.melonltd.naberc.view.common.BaseCore;
import com.melonltd.naberc.view.factory.PageFragmentFactory;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.view.seller.SellerMainActivity;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class SellerMenuEditFragment extends Fragment {
    private static final String TAG = SellerMenuEditFragment.class.getSimpleName();
    public static SellerMenuEditFragment FRAGMENT = null;
    private Button newDemandBtn, saveBtn;
    private SimpleDraweeView menuIconImage;
    private LinearLayout editLayout;
    private ImageButton scopeAddBtn, optAddBtn;
    private LinearLayout scopeLayout, optLayout;
    private List<View> demandList = Lists.newArrayList();

    private static final int PICK_FROM_GALLERY = 9908;
    private static final int PICK_FROM_CAMERA = 9901;

    public SellerMenuEditFragment() {
    }

    public Fragment getInstance(Bundle bundle) {
        if (FRAGMENT == null) {
            FRAGMENT = new SellerMenuEditFragment();
        }
        FRAGMENT.setArguments(bundle);
        return FRAGMENT;
    }

    public Fragment newInstance(Object... o) {
        return new SellerMenuEditFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container.getTag(R.id.seller_menu_edit_page) == null) {
            View v = inflater.inflate(R.layout.fragment_seller_menu_edit, container, false);
            getViews(v);
            setListener();
            container.setTag(R.id.seller_menu_edit_page, v);
            return v;
        }
        return (View) container.getTag(R.id.seller_menu_edit_page);
    }

    private void getViews(View v) {
        menuIconImage = v.findViewById(R.id.menuIconImage);
        newDemandBtn = v.findViewById(R.id.newDemandBtn);
        editLayout = v.findViewById(R.id.editLayout);
        saveBtn = v.findViewById(R.id.saveBtn);
//        https://firebasestorage.googleapis.com/v0/b/naber-test.appspot.com/o/images%2Fmountains.jpg?alt=media&token=75e92b00-3d0e-4519-b1e6-11b00a38aa5f
        scopeAddBtn = v.findViewById(R.id.scopeAddBtn);
        scopeAddBtn.setVisibility(View.VISIBLE);
        scopeLayout = v.findViewById(R.id.scopeLayout);
        optAddBtn = v.findViewById(R.id.optAddBtn);
        optAddBtn.setVisibility(View.VISIBLE);
        optLayout = v.findViewById(R.id.demandLayout);
    }

    private void setListener() {
        newDemandBtn.setOnClickListener(new NewDemandListener());
        saveBtn.setOnClickListener(new SaveBtnListener());
        scopeAddBtn.setOnClickListener(new ScopeAddListener());
        optAddBtn.setOnClickListener(new OptAddListener());
        menuIconImage.setOnClickListener(new UpLoadMenuIcon());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;
            switch (requestCode) {
                case PICK_FROM_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
//                    menuIconImage.setImageBitmap(bitmap);
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

                final StorageReference ref = PhotoTools.getReference(NaberConstant.STORAGE_PATH, "food/", "food_uuid.jpg");
                ref.putBytes(bytes).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot task) {

                    }
                }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            menuIconImage.setImageURI(downloadUri);
                            Log.d(TAG, downloadUri.toString());
                        }
                    }
                });
            }else {
                // TODO 圖片上傳失敗
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SellerMainActivity.changeTabAndToolbarStatus();
        if (SellerMainActivity.toolbar != null) {
            SellerMainActivity.navigationIconDisplay(true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertView.Builder()
                            .setTitle("")
                            .setMessage("請確認您所編輯的產品是否已經儲存，\n否則離開後所編輯之數據將會清空！")
                            .setContext(getContext())
                            .setStyle(AlertView.Style.Alert)
                            .setOthers(new String[]{"確定離開", "取消"})
                            .setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {
                                    if (position == 0) {
                                        backToSellerCategoryListPage();
                                        SellerMainActivity.navigationIconDisplay(false, null);
                                    }
                                }
                            })
                            .build()
                            .setCancelable(true)
                            .show();
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        SellerMainActivity.navigationIconDisplay(false, null);
    }

    private void backToSellerCategoryListPage() {
        SellerCategoryListFragment.TO_MENU_EDIT_PAGE_INDEX = -1;
        BaseCore.FRAGMENT_TAG = PageType.SELLER_CATEGORY_LIST.name();
        Fragment f = PageFragmentFactory.of(PageType.SELLER_CATEGORY_LIST, null);
        getFragmentManager().beginTransaction().remove(this).replace(R.id.sellerFrameContainer, f).commit();
    }


    class UpLoadMenuIcon implements View.OnClickListener {
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
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
//                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, false);
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

    class NewDemandListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final View demandView = newDemandView();
            Button removeBtn = demandView.findViewById(R.id.demandRemoveBtn);
            removeBtn.setVisibility(View.VISIBLE);
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editLayout.removeView(demandView);
                    demandList.remove(demandView);
                }
            });
            demandList.add(demandView);
            editLayout.addView(demandView);
        }
    }

    class SaveBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            for (int i = 0; i < scopeLayout.getChildCount(); i++) {
                View cView = scopeLayout.getChildAt(i);
                if (cView instanceof ConstraintLayout) {
                    ConstraintLayout cLayout = (ConstraintLayout) cView;
                    for (int j = 0; j < cLayout.getChildCount(); j++) {
                        View v = cLayout.getChildAt(j);
                        if (R.id.nameEdit == v.getId()) {
                            Log.d(TAG, "name : " + ((EditText) v).getText().toString());
                        } else if (R.id.priceEdit == v.getId()) {
                            Log.d(TAG, "price : " + ((EditText) v).getText().toString());
                        }
                    }
                }
            }

            for (int i = 0; i < optLayout.getChildCount(); i++) {
                View cView = optLayout.getChildAt(i);
                if (cView instanceof ConstraintLayout) {
                    ConstraintLayout cLayout = (ConstraintLayout) cView;
                    for (int j = 0; j < cLayout.getChildCount(); j++) {
                        View v = cLayout.getChildAt(j);
                        if (R.id.nameEdit == v.getId()) {
                            Log.d(TAG, "name : " + ((EditText) v).getText().toString());
                        } else if (R.id.priceEdit == v.getId()) {
                            Log.d(TAG, "price : " + ((EditText) v).getText().toString());
                        }
                    }
                }
            }

            for (View dView : demandList) {
                EditText demandName = dView.findViewById(R.id.demandNameEdit);
                Log.d(TAG, "demand name : " + demandName.getText().toString());
                LinearLayout demandLayout = dView.findViewById(R.id.demandLayout);
                for (int i = 0; i < demandLayout.getChildCount(); i++) {
                    View cView = demandLayout.getChildAt(i);
                    if (cView instanceof ConstraintLayout) {
                        ConstraintLayout cLayout = (ConstraintLayout) cView;
                        for (int j = 0; j < cLayout.getChildCount(); j++) {
                            View v = cLayout.getChildAt(j);
                            if (R.id.nameEdit == v.getId()) {
                                Log.d(TAG, "name : " + ((EditText) v).getText().toString());
                            }
                        }
                    }
                }
            }
        }
    }

    class ScopeAddListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            scopeLayout.addView(addScopeEditView());
        }
    }

    class OptAddListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            optLayout.addView(addOptEditView());
        }
    }


    private View addScopeEditView() {
        final View v = LayoutInflater.from(getContext()).inflate(R.layout.seller_edit_menu_detail, null);
        TextView priceText = v.findViewById(R.id.priceText);
        priceText.setVisibility(View.VISIBLE);
        EditText priceEdit = v.findViewById(R.id.priceEdit);
        priceEdit.setVisibility(View.VISIBLE);
        ImageButton deleteBtn = v.findViewById(R.id.menuEditDeleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scopeLayout.removeView(v);
            }
        });

        return v;
    }


    private View addOptEditView() {
        final View v = LayoutInflater.from(getContext()).inflate(R.layout.seller_edit_menu_detail, null);
        TextView priceText = v.findViewById(R.id.priceText);
        priceText.setVisibility(View.VISIBLE);
        EditText priceEdit = v.findViewById(R.id.priceEdit);
        priceEdit.setVisibility(View.VISIBLE);
        ImageButton deleteBtn = v.findViewById(R.id.menuEditDeleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optLayout.removeView(v);
            }
        });
        return v;
    }

    private View newDemandView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.menu_detail_demand, null);
        ImageButton addBtn = view.findViewById(R.id.demandAddBtn);
        addBtn.setVisibility(View.VISIBLE);

        EditText demandNameEdit = view.findViewById(R.id.demandNameEdit);
        demandNameEdit.setVisibility(View.VISIBLE);
        final LinearLayout demandLayout = view.findViewById(R.id.demandLayout);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View v = LayoutInflater.from(getContext()).inflate(R.layout.seller_edit_menu_detail, null);
                ImageButton deleteBtn = v.findViewById(R.id.menuEditDeleteBtn);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        demandLayout.removeView(v);
                    }
                });
                demandLayout.addView(v);
            }
        });

        return view;
    }

}
